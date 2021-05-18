package com.bayside.app.opinion.manager.solrmatch.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.bayside.app.opinion.war.subject.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.subject.model.SubjectArticle;
import com.bayside.app.opinion.war.subject.model.SubjectMArticle;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.ComMethodUtil;
import com.bayside.app.util.DBUtil;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.RedisUtil;

import redis.clients.jedis.ShardedJedis;

public class SolrMatch extends Thread {
	private static final Logger logger = Logger.getLogger(SolrMatch.class);
	//private String url = "jdbc:mysql://rm-2ze69ok73j2aizl47o.mysql.rds.aliyuncs.com:3306/bayside?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false"; // 数据库地址
	private static String url = "jdbc:mysql://rm-2ze69ok73j2aizl47.mysql.rds.aliyuncs.com:3306/bayside?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false"; // 数据库地址
	private String username = "bayside"; // 数据库用户名
	private String password = "Bayside801"; // 数据库密码
	//private String ip = "47.93.114.131";
	private String ip = "10.30.132.223";
	private String redispassword = "bayside801";
	private int port = 6111;
	private String regx = ",|，|\\s+";
	private ObjectMapper mapper = new ObjectMapper();
	private Map<String, Object> subject = new HashMap<String, Object>();
	private Map<String, String> viewpointMap = new HashMap<String, String>();
	private Map<String, String> tradeMap = new HashMap<String, String>();
	private	String warnword;
	private String subjectid;
	Connection conn = null;
	public SolrMatch(String subjectid) {
		this.subjectid = subjectid;
	}
	@Override
	public void run() {
		try {
			conn = DBUtil.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "SELECT bs.*,bu.ownindustry FROM bs_subject bs INNER JOIN bs_user bu ON  bs.userid=bu.ID AND DATE(bu.expirydate)>=DATE(NOW())  AND DATE(bs.starttime) <= DATE(NOW()) AND DATE(bs.endtime) >= DATE(NOW())  AND bu.status != 0 AND bs.isdelete=0 AND bs.monitor = '1' AND bs.ID='"+subjectid+"'";
		List<Map<String, Object>> list = DBUtil.getResultMap(sql,conn);
		if(list==null||list.isEmpty()){
			DBUtil.close(null, null, conn);
			return;
		}
		subject = list.get(0);
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(ip, port, 0, redispassword);
		warnword = shardedJedis.hget("warnconfig", (String)subject.get("userid"));
		viewpointMap = shardedJedis.hgetAll("viewpointword");
		tradeMap = shardedJedis.hgetAll("trade");
		shardedJedis.close();
		HttpSolrClient solrClient=new HttpSolrClient(AppConstant.solrUrl.WEIBOPAGE);	
		String subject_word = (String) subject.get("subject_word");
		String [] words = subject_word.split(regx);
		 String q = "";
		for (String string : words) {
			q+="title_cn:\""+string+"\""+" OR content_cn:\""+string+"\" OR ";
		}
		q = q.substring(0, q.lastIndexOf("OR"));
		 String date = DateFormatUtil.dateFormatStringType(new Date(), "yyyy-MM-dd");
		 String fq = "pubdate:["+date+" TO *]";
		System.out.println(q);
		getSolrData(q,fq, 1, 100,solrClient);
		try {
			solrClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e);
		}
		DBUtil.close(null, null, conn);
	}
	
	public void getSolrData(String q,String fq,int pageNum,int pageSize,HttpSolrClient solrClient){
		String shards = AppConstant.solrUrl.WEIBOPAGE+","
        		+ AppConstant.solrUrl.METASEARCHPAGE+","
        		/*+ AppConstant.solrUrl.ARTICLE+","*/
        		+ AppConstant.solrUrl.TIEBAPAGE+","
        		+ AppConstant.solrUrl.WEIXINPAGE;
		 ModifiableSolrParams solrParams = new ModifiableSolrParams();  
		 solrParams.set("q", q);
		 solrParams.set("fq", fq);
		 //solrParams.set("fl", "id,title_cn,content_cn,pubdate,updateTime,url,formats,emotion,dataSource,repeatcount,commtcount,agreecount,negativeWord,positiveWord,readcount,logourl,imgUrl,author,tiebaname,nickname");//设置过滤  
		 solrParams.set("shards", shards);//设置shard  
		 solrParams.set("start", (pageNum-1)*pageSize);
	     solrParams.set("rows",pageSize);
	     solrParams.set("sort","pubdate desc");
	     QueryResponse response;
			try {
				  response = solrClient.query(solrParams);
				  SolrDocumentList list = response.getResults();  
				  System.out.println(list.getNumFound());
				  if(list==null||list.isEmpty()||list.getNumFound()==0){
					  return;
				  }
				  dataMatch(list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			pageNum++;
		getSolrData(q,fq, pageNum, pageSize,solrClient);
	}
	public void dataMatch(SolrDocumentList list){
		int mode = (int)subject.get("type");
		List<SubJectArticleBo> alist = new ArrayList<SubJectArticleBo>();
		List<SubjectMArticleBo> mlist = new ArrayList<SubjectMArticleBo>();
		Boolean warning = (Boolean) subject.get("warning");
		for (SolrDocument solrDocument : list) {
			try {
			String title = solrDocument.getFieldValue("title_cn")!=null?solrDocument.getFieldValue("title_cn").toString().replace("[", "").replace("]",""):"";
			String content = solrDocument.getFieldValue("content_cn")!=null?solrDocument.getFieldValue("content_cn").toString().replace("[", "").replace("]",""):"";
			List<String> wList = new ArrayList<String>();
			if(mode==1){
				wList = advancedMatch(title+content.replace("##B##", ""));//高级模式
			}else{
				wList = standardMatch(title+content.replace("##B##", ""));//普通模式
			}
			if(wList!=null&&!wList.isEmpty()){
				String id = solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"";
				String pubdate = solrDocument.getFieldValue("pubdate")!=null?solrDocument.getFieldValue("pubdate").toString().replace("[", "").replace("]",""):"";
				String formats = solrDocument.getFieldValue("formats")!=null?solrDocument.getFieldValue("formats").toString().replace("[", "").replace("]",""):"";
				String url = solrDocument.getFieldValue("url")!=null?solrDocument.getFieldValue("url").toString().replace("[", "").replace("]",""):"";
				String dataSource = solrDocument.getFieldValue("dataSource")!=null?solrDocument.getFieldValue("dataSource").toString().replace("[", "").replace("]",""):"";
				String emotion = solrDocument.getFieldValue("emotion")!=null?solrDocument.getFieldValue("emotion").toString().replace("[", "").replace("]",""):"";
				String author = solrDocument.getFieldValue("author")!=null?solrDocument.getFieldValue("author").toString().replace("[", "").replace("]",""):"";
				String readcount = solrDocument.getFieldValue("readcount")!=null?solrDocument.getFieldValue("readcount").toString().replace("[", "").replace("]",""):"";
				String commtcount = solrDocument.getFieldValue("commtcount")!=null?solrDocument.getFieldValue("commtcount").toString().replace("[", "").replace("]",""):"";
				String repeatcount = solrDocument.getFieldValue("repeatcount")!=null?solrDocument.getFieldValue("repeatcount").toString().replace("[", "").replace("]",""):"";
				String agreecount = solrDocument.getFieldValue("agreecount")!=null?solrDocument.getFieldValue("agreecount").toString().replace("[", "").replace("]",""):"";
				String negativeWord = solrDocument.getFieldValue("negativeWord")!=null?solrDocument.getFieldValue("negativeWord").toString().replace("[", "").replace("]",""):"";
				String positiveWord = solrDocument.getFieldValue("positiveWord")!=null?solrDocument.getFieldValue("positiveWord").toString().replace("[", "").replace("]",""):"";
				String updateTime = solrDocument.getFieldValue("updateTime")!=null?solrDocument.getFieldValue("updateTime").toString().replace("[", "").replace("]",""):"";
				String imgUrl = solrDocument.getFieldValue("imgUrl")!=null?solrDocument.getFieldValue("imgUrl").toString().replace("[", "").replace("]",""):"";
				String domain = solrDocument.getFieldValue("domain")!=null?solrDocument.getFieldValue("domain").toString().replace("[", "").replace("]",""):"";
				domain = ComMethodUtil.getDomain(domain, 1);
				String simhashcode = solrDocument.getFieldValue("simhashcode")!=null?solrDocument.getFieldValue("simhashcode").toString().replace("[", "").replace("]",""):"";
				String html = solrDocument.getFieldValue("html")!=null?solrDocument.getFieldValue("html").toString().replace("[", "").replace("]",""):"";
				SubJectArticleBo article = new SubJectArticleBo();
				Map<String, Object> emresult = ComMethodUtil.getEmotion(title, content.replace("##B##", ""));
				article.setEmotion((String)emresult.get("emotion"));
				Double negativeProbability = (Double) emresult.get("negativeProbability");
				article.setNegativeProbability(negativeProbability);
				if(negativeProbability>0.5){
					article.setEmotion("-2");
				}
				article.setId(id);
				article.setTittle(title);
				article.setContent(content);
				article.setPubdate(pubdate);
				article.setFormats(formats);
				article.setUrl(url);
				article.setDataSource(dataSource);
				article.setAuthor(author);
				article.setReadcount(readcount==null||"".equals(readcount)?0:Integer.parseInt(readcount));
				article.setCommtcount(commtcount==null||"".equals(commtcount)?0:Integer.parseInt(commtcount));
				article.setRepeatcount(repeatcount==null||"".equals(repeatcount)?0:Integer.parseInt(repeatcount));
				article.setAggreecount(agreecount==null||"".equals(agreecount)?0:Integer.parseInt(agreecount));
				article.setNegativeWord(negativeWord);
				article.setPositiveWord(positiveWord);
				article.setUpdatetime(DateFormatUtil.stringFormatDateType(updateTime, "yyyy-MM-dd HH:mm:ss"));
				article.setImgUrl(imgUrl);
				article.setSimhashcode(simhashcode);
				article.setDomain(domain);
				article.setHtml(html);
				SubjectMArticleBo subjectMArticle = new SubjectMArticleBo();
				double dependency =getDependency(content, wList);
				String ownindustry = (String) subject.get("ownindustry");
				String weidu = weiguMatch(content,ownindustry);
				String viewpoint = viewportMatch(content, ownindustry);
				subjectMArticle.setId(subject.get("ID")+id);
				subjectMArticle.setArticleid(id);
				subjectMArticle.setSubjectid(subjectid);
				subjectMArticle.setUserid((String)subject.get("userid"));
				subjectMArticle.setKeywordRule(mapper.writeValueAsString(wList));
				subjectMArticle.setUserid((String)subject.get("userid"));
				subjectMArticle.setEmotion(article.getEmotion());
				subjectMArticle.setPubdate(pubdate);
				subjectMArticle.setUpdatetime(updateTime);
				subjectMArticle.setDataSource(dataSource);
				subjectMArticle.setFormats(formats);
				subjectMArticle.setDependency(dependency);
				subjectMArticle.setWeidu(weidu);
				subjectMArticle.setViewpoint(viewpoint);
				subjectMArticle.setNegativeProbability(negativeProbability);
				System.out.println(warning+"\t"+subjectMArticle.getFormats());
				if(warning&&!"trade".equals(subjectMArticle.getFormats())&&warnword!=null&&!"".equals(warnword)){
					matchWarn(subjectMArticle, (title+content).replace("##B##", ""));
				}else{
					subjectMArticle.setWarning(false);
				}
				alist.add(article);
				mlist.add(subjectMArticle);
			}
			
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		if(alist!=null&&!alist.isEmpty()){
			saveArticle(alist);
		}
		if(mlist!=null&&!mlist.isEmpty()){
			saveSubMArticle(mlist, conn);
		}
	}
	/**
	 * 
	 * <p>方法名称：standardMatch</p>
	 * <p>方法描述：标准模式匹配</p>
	 * @param subject_word
	 * @param region_word
	 * @param event_word
	 * @param ambiguity_word
	 * @param content
	 * @return
	 * @author Administrator
	 * @since  2017年8月16日
	 * <p> history 2017年8月16日 Administrator  创建   <p>
	 */
	private List<String> standardMatch(String content) {
		List<String> wList = new ArrayList<String>();
		String keyword = "";
		String subject_word = (String) subject.get("subject_word");
		String region_word = (String) subject.get("region_word");
		String event_word = (String) subject.get("event_word");
		String ambiguity_word = (String)subject.get("event_word");
		String submatchWord = "";
		String regmatchWord = "";
		String evematchWord = "";
		boolean flag = false;
		if(subject_word!=null&&!"".equals(subject_word)){
			String[] subjectWords = subject_word.split(regx);
			for (String string : subjectWords) {
				if(content.contains(string)){
					keyword +=string; 
					submatchWord = string;
					flag = true;
					break;
				}
			}
		}
		if(!flag){
			return wList;
		}
		if(region_word!=null&&!"".equals(region_word)){
			flag = false;
			String [] regionWords = region_word.split(regx);
			for (String string : regionWords) {
				if(content.contains(string)){
					//keyword +=" "+string;
					flag = true;
					if(submatchWord.equals(string)){
						continue;
					}
					keyword+=" "+string;
					regmatchWord = string;
					break;
				}
			}
			
		}
		if(!flag){
			return wList;
		}
		if(event_word!=null&&!"".equals(event_word)){
			flag = false;
			String [] eventWords = event_word.split(regx);
			for (String string : eventWords) {
				if(content.contains(string)){
					//keyword +=" "+string;
					flag = true;
					if(submatchWord.equals(string)||regmatchWord.equals(string)){
						continue;
					}
					keyword+=" "+string;
					evematchWord = string;
					break;
				}
			}
		}
		if(!flag){
			return wList;
		}
		if(ambiguity_word!=null&&!"".equals(ambiguity_word)){
			String [] ambiguitywords = ambiguity_word.split(regx);
			for (String string : ambiguitywords) {
				if(content.contains(string)){
					flag = false;
					break;
				}
			}
		}
		if(!flag){
			return wList;
		}
		wList.add(keyword);
		return wList;
	}
	/**
	 * 
	 * <p>方法名称：advancedMatch</p>
	 * <p>方法描述：高级模式匹配匹配</p>
	 * @return
	 * @author Administrator
	 * @since  2017年8月11日
	 * <p> history 2017年8月11日 Administrator  创建   <p>
	 */
	public List<String> advancedMatch(String content){
		String combined_word = (String) subject.get("combined_word");
		String ambiguity_word = (String) subject.get("ambiguity_word");
		List<String> wList = new ArrayList<String>();
		if(combined_word==null||"".equals(combined_word))
			return wList;
		try {
			List<Map<String, String>> list = mapper.readValue(combined_word, ArrayList.class);
		ok:
		for (Map<String, String> map : list) {
			String keyword = "";
			boolean regionflag = true;
			boolean subjectflag = true;
			boolean eventflag = true;
			boolean ambiguityflag = true;
			String region_word = map.get("region_word");
			String subject_word = map.get("subject_word");
			String event_word = map.get("event_word");
			//String ambiguity_word = map.get("ambiguity_word");
			if(region_word!=null&&!"".equals(region_word)){
				if(content.contains(region_word)){
					regionflag = true;
					keyword+=region_word+" ";
				}else {
					regionflag =false;
				}
				
			}
			if(subject_word!=null&&!"".equals(subject_word)){
				if(content.contains(subject_word)){
					subjectflag = true;
					keyword+=subject_word+" ";
				}else{
					subjectflag = false;
				}
			}
			if(event_word!=null&&!"".equals(event_word)){
				if(content.contains(event_word)){
					eventflag = true;
					keyword+=event_word+" ";
				}else{
					eventflag =false;
				}
			}
			String []ambiguity_words ={};
			if(ambiguity_word!=null&&!"".equals(ambiguity_word)){
				ambiguity_words = ambiguity_word.split(",|，|\\s+");
			}
			for (int i = 0; i < ambiguity_words.length; i++) {
				if(content.contains(ambiguity_words[i])){
					ambiguityflag = false;
					break ;
				}
			}
			if(regionflag&&subjectflag&&eventflag&&ambiguityflag&&!"".equals(keyword)){
				String[] keywords = keyword.split(",|，|\\s+");
				keyword = StringUtils.join(keywords, " ");
				wList.clear();
				wList.add(keyword);
				if(subject_word.equals(region_word)||subject_word.equals(event_word)){
						continue;
				}else{
					break ok;
				}
			}
		}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wList;
	}
	/**
	 * 
	 * <p>方法名称：matchWarn</p>
	 * <p>方法描述：预警匹配</p> 
	 * @param mArticles文章数据
	 * @param warnMap预警数据
	 * @return
	 * @author Administrator
	 * @since 2016年12月16日
	 */
	public void matchWarn(SubjectMArticleBo subjectMArticle,String content) {
			boolean isWarning = false; 
			List<String> wList = new ArrayList<String>();
			if(warnword==null||"".equals(warnword)){
				subjectMArticle.setWarning(false);
			}
			String warnid = "";
			Ok:    //设置一个标志位当匹配到预警是跳出多层循环
					try {
						
						Map<String,String> comMap = mapper.readValue(warnword, HashMap.class);
						String combined_word = comMap.get("combined_word");
						List<Map<String,Object>> comList = mapper.readValue("["+combined_word+"]", ArrayList.class);
						for (Map<String, Object> combinedMap : comList) {
							List<Map<String, Object>> combinedList = (List<Map<String, Object>>) combinedMap.get("combined_word");
							String eliminate_word = (String) combinedMap.get("eliminate_word");
							warnid = (String) combinedMap.get("id");
							String []eliminate_words ={};
							if(eliminate_word!=null&&!"".equals(eliminate_word)){
								eliminate_words = eliminate_word.split(",|，|\\s+");
							}
							for (Map<String, Object> map : combinedList) {
								String keyword = "";
								boolean regionflag = true;
								boolean subjectflag = true;
								boolean eventflag = true;
								boolean eliminateflag = true;
								String region_word = (String) map.get("region_word");
								String subject_word = (String) map.get("subject_word");
								String event_word = (String) map.get("event_word");
								if(region_word!=null&&!"".equals(region_word)){
									if(content.contains(region_word)){
										regionflag = true;
										keyword+=region_word+" ";
									}else {
										regionflag =false;
									}
									}
									if(subject_word!=null&&!"".equals(subject_word)){
									if(content.contains(subject_word)){
										subjectflag = true;
										keyword+=subject_word+" ";
									}else{
										subjectflag = false;
									}
									}
									if(event_word!=null&&!"".equals(event_word)){
									if(content.contains(event_word)){
										eventflag = true;
										keyword+=event_word+" ";
									}else{
										eventflag =false;
									}
									}
									for (int i = 0; i < eliminate_words.length; i++) {
										if(eliminate_words[i]!=null&&!"".equals(eliminate_words[i])){
										if(content.contains(eliminate_words[i])){
											eliminateflag = false;
											break;
											}
										}
									}
									if(regionflag&&subjectflag&&eventflag&&eliminateflag&&!"".equals(keyword)){
										System.out.println(keyword);
										wList.add(keyword);
										isWarning=true;
										break Ok;
									}
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					} 
			try {
				if(wList!=null&&!wList.isEmpty()){
					subjectMArticle.setWarningWord(mapper.writeValueAsString(wList));
					subjectMArticle.setWarningid(warnid);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			subjectMArticle.setWarning(isWarning);
	}
	/**
	 * 
	 * <p>方法名称：getDependency</p>
	 * <p>方法描述：获取相关性</p>
	 * 词频（次数）=匹配总个数/组合个数 最大值为4改为5
	 * 密度=匹配总字节数/文章总字节数   最大值为2
	 * 词间距=各个词之间的间距最小值的和（|(A-b)|min+|(a-c)|min+|(b-c)|min）/组合词的个数  最小值为   20 
	 * 相关性=词频+密度*10+80/词间距 改为 相关性=词频+密度*10+60/词间距
	 * @param content文章的内容
	 * @param wList匹配词
	 * @return
	 * @author Administrator
	 * @since  2016年12月16日
	 * <p> history 2016年12月16日 Administrator  创建   <p>
	 */
	public double getDependency(String content,List<String> wList){
		String [] words = wList.get(0).split(",|，|\\s+");
		double matchCount = 0.0;//匹配词在文章中出现的总个数
		double matchBytes = 0.0;
		int [] indexarry1 ={}; 
		int [] indexarry2 ={}; 
		int [] indexarry3 ={}; 
		int mindiffSum = 0;//最小间距的和
		if(words.length>0){
			indexarry1 = ComMethodUtil.getWordIndexs(content, words[0]);
			matchCount+=indexarry1.length;
			matchBytes += words[0].getBytes().length*indexarry1.length;
			mindiffSum = ComMethodUtil.getMinDiff(indexarry1,new int[0]);  
		}
		
		if(words.length>1){
			indexarry2 = ComMethodUtil.getWordIndexs(content, words[1]);
			matchCount+=indexarry2.length;
			matchBytes += words[1].getBytes().length*indexarry2.length;
			mindiffSum = ComMethodUtil.getMinDiff(indexarry1,indexarry2);
		}
		if(words.length>2){
			indexarry3 = ComMethodUtil.getWordIndexs(content, words[2]);
			matchCount+=indexarry3.length;
			matchBytes += words[2].getBytes().length*indexarry3.length;
			mindiffSum = ComMethodUtil.getMinDiff(indexarry1,indexarry2)+ComMethodUtil.getMinDiff(indexarry1,indexarry3)+ComMethodUtil.getMinDiff(indexarry2,indexarry3);
		}
		double wordfre = 0.0;//频次
		if(words.length>0){
			wordfre =  matchCount/words.length;	
			if(wordfre>5){
				wordfre=5;
			}
		}
		double density = matchBytes/content.getBytes().length;//密度
		if(density>0.2){
			density = 0.2;
		}
		double wordspacing = 0.0;
		if(words.length==1){
			int length = indexarry1.length>5?5:indexarry1.length;
			if(length!=1){
				wordspacing = (indexarry1[length-1]-indexarry1[0])/length-1;//词间距
			}
		}else{
			wordspacing = mindiffSum/words.length;//词间距
		}
		if(wordspacing<20){
			wordspacing = 20;
		}
	
		System.out.println("词频"+wordfre+"密度"+density+"词间距"+wordspacing);
		double dependency = wordfre+density*10+(60/wordspacing);//相关性
		return dependency;
	}
	public String weiguMatch(String content,String ownindustry){
		for (Entry<String, String> entry : tradeMap.entrySet()) {
			try {
				Map<String,String> map = mapper.readValue(entry.getValue(), HashMap.class);
				String weidu = map.get("weiduName");
				String trade = map.get("tradename");
				if(ownindustry!=null&&ownindustry.equals(trade)){
					String negativeWord = map.get("negativeWord");
					String[] negativeWords = negativeWord.split(",|，|\\s+");
					int wcount = 0;
					for (String string : negativeWords) {
						if(content.contains(string)&&!"".equals(string)){
							wcount++;
							if(wcount>=3){
								return weidu;
							}
						}
					}	
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				continue;
			} 
		}
	return "";
}
	public String viewportMatch(String content,String ownindustry){
		for (Entry<String, String> entry : viewpointMap.entrySet()) {
			try {
				Map<String,Object> map = mapper.readValue(entry.getValue(), HashMap.class);
				String view = map.get("viewpoint")+"";
				String trade = map.get("tradename")+"";
				if(ownindustry!=null&&ownindustry.equals(trade)){
					if(content.contains(view)&&!"".equals(view)){
						return view;
					}
					String word = map.get("word")+"";
					String[] words = word.split(",|，|\\s+");
					int count = 0;
					for (String string : words) {
						if(content.contains(string)&&!"".equals(string)){
							if(count>2){
								return view;
							}
							count++;
						}
					}	
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				continue;
			} 
		}
		return "";
	}
	public int saveArticle(List<SubJectArticleBo> list) {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		PreparedStatement psts = null;
		int count = 0;
		try {
			String sql = "insert into bs_subject_article " + "(ID,tittle,content,pubdate,data_source,url,author,"
					+ "emotion,formats,readcount,commtcount,repeatcount,aggreecount,"
					+ "score,negative_word,positive_word,updatetime,imgUrl,domain,simhashcode,content_type,view_point,html) "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			psts = conn.prepareStatement(sql);
			for (SubJectArticleBo subjectArticle : list) {
				System.out.println("文章的id=================" + subjectArticle.getId());
				psts.setString(1, subjectArticle.getId());
				psts.setString(2, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getTittle())));
				psts.setString(3, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getContent())));
				psts.setString(4, "".equals(subjectArticle.getPubdate()) ? null : subjectArticle.getPubdate());
				psts.setString(5, subjectArticle.getDataSource());
				psts.setString(6, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getUrl())));
				psts.setString(7, subjectArticle.getAuthor());
				psts.setString(8, subjectArticle.getEmotion());
				psts.setString(9, subjectArticle.getFormats());
				psts.setInt(10, subjectArticle.getReadcount() == null ? 0 : subjectArticle.getReadcount());
				psts.setInt(11, subjectArticle.getCommtcount() == null ? 0 : subjectArticle.getCommtcount());
				psts.setInt(12, subjectArticle.getRepeatcount() == null ? 0 : subjectArticle.getRepeatcount());
				psts.setInt(13, subjectArticle.getAggreecount() == null ?0 : subjectArticle.getAggreecount());
				psts.setInt(14, subjectArticle.getScore() == null ? 0 : subjectArticle.getScore());
				psts.setString(15, subjectArticle.getNegativeWord());
				psts.setString(16, subjectArticle.getPositiveWord());
				psts.setString(17, DateFormatUtil.dateFormatStringType(subjectArticle.getUpdatetime(), "yyyy-MM-dd HH:mm:ss"));
				psts.setString(18, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getImgUrl())));
				psts.setString(19, subjectArticle.getDomain());
				psts.setString(20, subjectArticle.getSimhashcode());
				psts.setString(21, subjectArticle.getContentType());
				psts.setString(22, subjectArticle.getViewPoint());
				psts.setString(23, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getHtml())));
				psts.addBatch();
			}
			//System.out.println(psts.toString());
			int[] results = psts.executeBatch(); // 执行批量处理
			for (int i = 0; i < results.length; i++) {
				if (results[i] >= 0) {
					count++;
				}
			}
			System.out.println("文章表添加了:" + count + "条数据");
		} catch (Exception e) {
			String sql = "insert into bs_subject_article " + "(ID,tittle,content,pubdate,data_source,url,author,"
					+ "emotion,formats,readcount,commtcount,repeatcount,aggreecount,"
					+ "score,negative_word,positive_word,updatetime,imgUrl,domain,simhashcode,content_type,view_point,html) "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				for (SubJectArticleBo subjectArticle : list) {
					psts = conn.prepareStatement(sql);
					try {
						psts.setString(1, subjectArticle.getId());
						psts.setString(2, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getTittle())));
						psts.setString(3, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getContent())));
						psts.setString(4, "".equals(subjectArticle.getPubdate()) ? null : subjectArticle.getPubdate());
						psts.setString(5, subjectArticle.getDataSource());
						psts.setString(6, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getUrl())));
						psts.setString(7, subjectArticle.getAuthor());
						psts.setString(8, subjectArticle.getEmotion());
						psts.setString(9, subjectArticle.getFormats());
						psts.setInt(10, subjectArticle.getReadcount() == null ? 0 : subjectArticle.getReadcount());
						psts.setInt(11, subjectArticle.getCommtcount() == null ? 0 : subjectArticle.getCommtcount());
						psts.setInt(12, subjectArticle.getRepeatcount() == null ? 0 : subjectArticle.getRepeatcount());
						psts.setInt(13, subjectArticle.getAggreecount() == null ? 0 : subjectArticle.getAggreecount());
						psts.setInt(14, subjectArticle.getScore() == null ? 0 : subjectArticle.getScore());
						psts.setString(15, subjectArticle.getNegativeWord());
						psts.setString(16, subjectArticle.getPositiveWord());
						psts.setString(17, DateFormatUtil.dateFormatStringType(subjectArticle.getUpdatetime(), "yyyy-MM-dd HH:mm:ss"));
						psts.setString(18, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getImgUrl())));
						psts.setString(19, subjectArticle.getDomain());
						psts.setString(20, subjectArticle.getSimhashcode());
						psts.setString(21, subjectArticle.getContentType());
						psts.setString(22, subjectArticle.getViewPoint());
						psts.setString(23, StringEscapeUtils.escapeSql(filterEmoji(subjectArticle.getHtml())));
						psts.executeUpdate();
						//System.out.println("异常处理保存成功1条");
					} catch (Exception e2) {
						logger.info(e2.getMessage());
						continue;
					}
				}
			} catch (SQLException e1) {
				logger.info(e1.getMessage());
			}
			logger.info(e.getMessage());
		} finally {
			DBUtil.close(null, psts, null);
		}
		return count;
	}
	/**
	 * 保存中间表
	 * <p>方法名称：saveSubMArticle</p>
	 * <p>方法描述：</p>
	 * @param list
	 * @param conn
	 * @return
	 * @author Administrator
	 * @since  2017年8月17日
	 * <p> history 2017年8月17日 Administrator  创建   <p>
	 */
	public int saveSubMArticle(List<SubjectMArticleBo> list, Connection conn) {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		int count = 0;
		PreparedStatement psts = null;
		try {
			String sql = "insert into bs_subject_m_article "
					+ "(ID,subjectid,articleid,userid,keyword_rule,emotion,data_source,formats,pubdate,updatetime,warning,warning_word,dependency,weidu,viewpoint,warningid) "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			psts = conn.prepareStatement(sql);
			for (SubjectMArticleBo subjectMArticle : list) {
				//mtemp++;
				//String mapid = subjectMArticle.getSubjectid() + "#" + subjectMArticle.getFormats();
				//EmotionModel tempnumber = mmap.get(mapid);
				//mmap.put(mapid, (tempnumber == null ? sumEmotionNumber(new EmotionModel(), subjectMArticle.getEmotion())
				//		: sumEmotionNumber(tempnumber, subjectMArticle.getEmotion())));		
				psts.setString(1, subjectMArticle.getId());
				psts.setString(2, subjectMArticle.getSubjectid());
				psts.setString(3, subjectMArticle.getArticleid());
				psts.setString(4, subjectMArticle.getUserid());
				psts.setString(5, subjectMArticle.getKeywordRule());
				psts.setString(6, subjectMArticle.getEmotion());
				psts.setString(7, subjectMArticle.getDataSource());
				psts.setString(8, subjectMArticle.getFormats());
				psts.setString(9, "".equals(subjectMArticle.getPubdate()) ? null : subjectMArticle.getPubdate());
				psts.setString(10, subjectMArticle.getUpdatetime());
				psts.setBoolean(11, subjectMArticle.getWarning());
				psts.setString(12, subjectMArticle.getWarningWord());
				psts.setDouble(13, subjectMArticle.getDependency());
				psts.setString(14, subjectMArticle.getWeidu());
				psts.setString(15, subjectMArticle.getViewpoint());
				psts.setString(16, subjectMArticle.getWarningid());
				psts.addBatch();
			}
			count = psts.executeBatch().length; // 执行批量处理
			System.out.println("专题文章中间表添加了:" + count + "条数据");
		} catch (Exception e) {
			String sql = "insert into bs_subject_m_article "
					+ "(ID,subjectid,articleid,userid,keyword_rule,emotion,data_source,formats,pubdate,updatetime,warning,warning_word,dependency,weidu,viewpoint,warningid) "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			for (SubjectMArticleBo subjectMArticle : list) {
				try {
					psts = conn.prepareStatement(sql);
					psts.setString(1, subjectMArticle.getId());
					psts.setString(2, subjectMArticle.getSubjectid());
					psts.setString(3, subjectMArticle.getArticleid());
					psts.setString(4, subjectMArticle.getUserid());
					psts.setString(5, subjectMArticle.getKeywordRule());
					psts.setString(6, subjectMArticle.getEmotion());
					psts.setString(7, subjectMArticle.getDataSource());
					psts.setString(8, subjectMArticle.getFormats());
					psts.setString(9, "".equals(subjectMArticle.getPubdate()) ? null : subjectMArticle.getPubdate());
					psts.setString(10, subjectMArticle.getUpdatetime());
					psts.setBoolean(11, subjectMArticle.getWarning());
					psts.setString(12, subjectMArticle.getWarningWord());
					psts.setDouble(13, subjectMArticle.getDependency());
					psts.setString(14, subjectMArticle.getWeidu());
					psts.setString(15, subjectMArticle.getViewpoint());
					psts.setString(16, subjectMArticle.getWarningid());
					psts.executeUpdate();
				} catch (Exception e1) {
					logger.info(e1.getMessage());
					continue;
				}
			}
			logger.info(e.getMessage());
		} finally {
			DBUtil.close(null, psts, null);
		}
		return count;
	}
	public static String filterEmoji(String source) {
		if (StringUtils.isNotBlank(source)) {
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
		} else {
			return source;
		}
	}
}

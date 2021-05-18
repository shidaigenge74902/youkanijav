package com.bayside.app.opinion.war.subject.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.power.service.impl.PowerServiceImpl;
import com.bayside.app.opinion.war.region.dao.RegionMapper;
import com.bayside.app.opinion.war.region.model.Region;
import com.bayside.app.opinion.war.subject.bo.MetaSearch;
import com.bayside.app.opinion.war.subject.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.subject.dao.SubjectArticleBoMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectArticleMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectMArticleMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectMapper;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectArticle;
import com.bayside.app.opinion.war.subject.model.SubjectMArticle;
import com.bayside.app.opinion.war.subject.service.SubjectArticleService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.ComMethodUtil;
import com.bayside.app.util.CommenMethod;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.ExportExcelUtil;
import com.bayside.app.util.Html2Text;
import com.bayside.app.util.HttpRequest;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.Solrhelper;
import com.bayside.app.util.UuidUtil;
import com.bayside.article.emotion.BaysideEmotionUtil;

import redis.clients.jedis.ShardedJedis;
@Service("subjectArticleServiceImpl")
@Transactional
@PropertySource("classpath:server.properties")
public class SubjectArticleServiceImpl implements SubjectArticleService {
	@Autowired
    private SubjectArticleMapper subjectArticleMapper;
	@Autowired
	private SubjectMArticleMapper subjectMArticleMapper;
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private SubjectArticleBoMapper subjectArticleBoMapper;
	@Resource
	private Environment environment;
	@Autowired
	private RegionMapper regionMapper;
	private static Logger Log = LoggerFactory.getLogger(SubjectArticleServiceImpl.class);
	@Override
	public Boolean insertSubjectArticle(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		String time = formats.format(new Date());
		  // 文章表里添加数据、
		  SubJectArticleBo sa = new SubJectArticleBo();
		  if(record!=null){
			  BeanUtils.copyProperties(record, sa);
		  }
		  sa.setId(UuidUtil.getUUID());
		  sa.setUpdatetime(new Date());
		  if(null!=record){
			  sa.setPubdate(record.getPubtime());
			  sa.setDataSource(record.getDataSource());
			  sa.setAuthor(record.getAuthor());
			  sa.setFormats(record.getFormats());
			  sa.setEmotion(record.getEmotion());
			  sa.setReadcount(record.getReadcount());
			  sa.setRepeatcount(record.getRepeatcount());
			  sa.setCommtcount(record.getCommtcount());
			  sa.setContent(record.getContent());
			  sa.setUrl(record.getUrl());
			  sa.setDomain(ComMethodUtil.getDomain(record.getUrl(), 1));
		  }
		 
		  Map<String, Object> map =  new HashMap<String,Object>();
		  //解析正面词 负面词
		  if(null!=record.getContent()){
			  map =  BaysideEmotionUtil.AriticleEmotion(record.getContent());
		  }
		  if(null!=map.get("positiveword") && !"".equals(map.get("positiveword"))){
		      sa.setPositiveWord(map.get("positiveword").toString());
		  }
		  if(null!=map.get("negativeword") && !"".equals(map.get("negativeword"))){
			  sa.setNegativeWord(map.get("negativeword").toString());
		  }
		  if(null!=map.get("simhash") && !"".equals(map.get("simhash"))){
			  sa.setSimhashcode((String) map.get("simhash"));
		  } 
		  int num = subjectArticleMapper.insertSelective(sa);
		  //添加中间表数据
		  SubjectMArticleBo sma = new SubjectMArticleBo();
		  if(record!=null){
			  BeanUtils.copyProperties(record, sma);
		  }
		  
		  List<String> wlist = new ArrayList<String>();
		  wlist.add(record.getKeywordRule());
		  //计算相关性
		Double dependency = this.getDependency(record.getContent(), wlist);
		  sma.setId(UuidUtil.getUUID());
		  sma.setArticleid(sa.getId());
		 // sma.setKeywordRule("["+""+record.getKeywordRule().replace(",", " ")  +""+"]");
		  sma.setPubdate(record.getPubtime());
		  sma.setUpdatetime(time);
          sma.setDataSource(record.getDataSource());
          sma.setFormats(record.getFormats());
          sma.setEmotion(record.getEmotion());
         sma.setDependency(dependency);
		 int n = this.insertSubjectMArticle(sma);
		  
		  //solr 里添加数据
		  Solrhelper addsolr = new Solrhelper(AppConstant.solrUrl.METASEARCHPAGE);
		  MetaSearch ms = new MetaSearch();
		  ms.setId(sa.getId());
		  ms.setTitle_cn(record.getTittle());
		  ms.setContent_cn(record.getContent());
		  ms.setPubdate(record.getPubtime());
		  ms.setUpdateTime(time);
		  ms.setUrl(record.getUrl());
		  ms.setDataSource(record.getDataSource());
		  ms.setAuthor(record.getAuthor());
		  ms.setFormats(record.getFormats());
		  ms.setEmotion(record.getEmotion());
		  ms.setDomain(ComMethodUtil.getDomain(record.getUrl(), 1));
		  if(null!=record.getReadcount()){
			  ms.setReadcount(record.getReadcount().toString());
		  }
		  if(null!=record.getRepeatcount()){
			  ms.setReadcount(record.getRepeatcount().toString());
		  }
		  if(null!=record.getCommtcount()){
			  ms.setReadcount(record.getCommtcount().toString());
		  }
		  if(!"".equals(map.get("simhash")) && map.get("simhash")!=null){
			  ms.setSimhashcode(map.get("simhash").toString());
		  }
		
		  addsolr.addIndexForObj(ms);
		 
		//
		  
		return true;
	}
	@Override
	public int insertSubjectMArticle(SubjectMArticleBo record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.insertSelective(record);
	}
	@Override
	public List<Subject> selectSubjectByUserid(String userid) {
		// TODO Auto-generated method stub
		return subjectMapper.selectByUserId(userid);
	}
	/**
	 * 
	 * <p>方法名称：getDependency</p>
	 * <p>方法描述：获取相关性</p>
	 * @param content
	 * @param wList
	 * @return
	 * @author Administrator
	 * @since  2016年12月16日
	 * <p> history 2016年12月16日 Administrator  创建   <p>
	 */
	public double getDependency(String content,List<String> wList){
		String [] words = wList.get(0).split(",|，|\\s+");
		double matchCount = 0.0;//匹配词总个数
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
			if(wordfre>4){
				wordfre=4;
			}
		}
		double density = matchBytes/content.getBytes().length;//密度
		if(density>0.2){
			density = 0.2;
		}
		double wordspacing = 0.0;
		if(words.length==1){
			int length = indexarry1.length>4?4:indexarry1.length;
			if(length!=1 && length!=0){
				wordspacing = (indexarry1[length-1]-indexarry1[0])/length-1;//词间距
			}
			
		}else{
			wordspacing = mindiffSum/words.length;//词间距
		}
		if(wordspacing<20){
			wordspacing = 20;
		}
	
		System.out.println("词频"+wordfre+"密度"+density+"词间距"+wordspacing);
		double dependency = wordfre+density*10+(80/wordspacing);//相关性
		return dependency;
	}
	@Override
	public MetaSearch alisUrl(String titleLinks,String userid,String subjectid) {
		// TODO Auto-generated method stub
		Document doc = null;
		Document tempdoc = null;
		String content = null;
		String pubdate = null;
		String title=null;
		String emotion=null;
		MetaSearch model = new MetaSearch();
		try {
			doc = Jsoup.connect(titleLinks).get();
		} catch (Exception e) {
			Log.error(e.getMessage(),e);
		}
		try {
			//自动解析地址
			tempdoc = Jsoup.connect(titleLinks)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101").get();
			titleLinks = tempdoc.baseUri();
			pubdate = Html2Text.getPublishDate(tempdoc.body().toString());
			if (pubdate == null) {
				System.out.println("无法获取发布时间");
				
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(null!=pubdate){
				if (pubdate.compareTo(sdf.format(new Date())) < 0) {
					System.out.println("不是今天的数据！");
				}
			}
			
		} catch (Exception e) {
			Log.error(e.getMessage(),e);
		}
		String domain = ComMethodUtil.getDomain(titleLinks, 1);
	  if(null!=tempdoc){
		content = Html2Text.getContentCN(Html2Text.getContentText(tempdoc));
		title = Html2Text.getTitle(tempdoc.outerHtml());
		model.setTitle(Html2Text.getTitle(tempdoc.outerHtml()));
	  }
		
		model.setPubdate(pubdate);
		//model.setContent(Html2Text.getContentCN(tempdoc.body().toString()));
		model.setContent(content);
		//从 redis 获取 emotion 监测词组  来源 
		emotion = getEmotion(title,content);
		model.setEmotion(emotion);
		//媒体类型
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));	
		int count = 0;
		while(shardedJedis==null){
			shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
			count++;
			if(count>10){
				break;
			}
		}
	 String type = shardedJedis.hget(domain, "siteType");
	 String dataSource = shardedJedis.hget(domain, "siteName");
	 System.out.println(type+"xccv"+dataSource);
	 model.setFormats(type);
	 model.setDataSource(dataSource);
	 //监测词组
	 SubjectMArticle sm = new SubjectMArticle();
	 sm.setUserid(userid);
	 sm.setSubjectid(subjectid);
	 List<SubjectMArticle> listm = subjectMArticleMapper.selectBySubjectid(sm);
	 String keywordRule = "";
	 if(listm.size() > 0){
		 keywordRule = listm.get(0).getKeywordRule();
	 }
	 System.out.println(keywordRule);
	 model.setKeywordRule(keywordRule);
		return  model;
	}
	
	
	public static String getEmotion(String title,String content){
		String emotion = "";
		try {
			String param ="verificatcode=bayside&title="+title
					+ "&content="+URLEncoder.encode(content);
			String s = HttpRequest.sendPost("http://47.93.122.237:8080/emotionBycontent", param);
			 ObjectMapper mapper = new ObjectMapper();
				Response response = mapper.readValue(s,Response.class);
				if(response.isSucceed()){
					Map<String, Object> map = (Map<String, Object>) response.getObject();
					System.out.println(map.get("name")+"\t"+map.get("value"));
					emotion = map.get("value")+"";
					if("-1".equals(emotion)){
						emotion = "-2";
					}
					return emotion;
				}
			} catch (Exception e) {
				Log.error(e.getMessage(),e);
				return emotion;
			} 
		return emotion;
	}

	@Override
	public List<SubJectArticleBo> filterCom(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.filterCom(record);
	}
	@Override
	public int updateSMArticle(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.updateSMArticle(record);
	}
	@Override
	public List<SubjectArticle> selectAllById(List<String> id) {
		// TODO Auto-generated method stub
		return subjectArticleMapper.selectAllById(id);
	}
	public Map<String,Object> liantongExel(SubJectArticleBo record, HttpServletResponse response, HttpServletRequest request){
		
		return null;
	}
	@Override
	public Map<String, Object> dowloadExcel(SubJectArticleBo record, HttpServletResponse response, HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<String> selected = record.getIds();
		List<String> list0 = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (selected != null) {
			for (String media : selected) {
				media = media.replace("\"", "").replace("[", "").replace("]", "");
				list0.add(media);
			}
		}
		
		//List<SubjectArticle> list = subjectArticleMapper.selectAllById(list1);
		List<SubJectArticleBo> list = subjectArticleBoMapper.filterComlist(list0);
		 List<Region> regionlist = regionMapper.selectByPrimaryKey(record.getUserid());
		if (list == null || list.isEmpty()) {
			map.put("flag", false);
			map.put("result", "没有查询到数据");
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 50 * 256);
		sheet.setColumnWidth(2, 50 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 25 * 256);
		sheet.setColumnWidth(6, 25 * 256);
		sheet.setColumnWidth(7, 25 * 256);
		sheet.setColumnWidth(8, 25 * 256);
		sheet.setColumnWidth(9, 25 * 256);
		sheet.setColumnWidth(10, 25 * 256);
		
		/*sheet.setColumnWidth(8, 60 * 256);
		sheet.setColumnWidth(9, 25 * 256);*/
		OutputStream out = null;
		String loadurl = "";
		try {
			HSSFRow rowm = sheet.createRow(0); // 标题行
			//String top[] = { "序号", "标题", "简介", "媒体类型","来源", "原文地址" ,"发布时间","情感","关键词","预警" };
			
			String top1[] = {"序号", "网站名称", "标题", "链接", "信息类型", "关键词", "发表日期","作者","地区","预警","媒体类型"};
			for (int i = 0; i < top1.length; i++) {
				HSSFCell cellm = rowm.createCell(i);
				cellm.setCellValue(top1[i]);
				cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
			}
			int rownum = 1;
			HSSFCellStyle style =ExportExcelUtil.getStyle(workbook);
			if(null!=list){
				for (SubJectArticleBo model: list) {
					HSSFRow row = sheet.createRow(rownum);
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(rownum);
					cell.setCellStyle(style);
					cell = row.createCell(1);
					
					if(null !=model.getDataSource()){
						if(!"".equals(model.getDataSource())){
							cell.setCellValue(model.getDataSource());
						}else{
							cell.setCellValue("..");
						}
					
					}else{
						cell.setCellValue("..");
					}
					////
					
					
					cell.setCellStyle(style);
					cell = row.createCell(2);
					if(null !=model.getTittle()){
						if(!"".equals(model.getTittle())){
							cell.setCellValue(model.getTittle());
						}else{
							cell.setCellValue("..");
						}
						
					}else{
						cell.setCellValue("..");
					}
					
					cell.setCellStyle(style);
					cell = row.createCell(3);
					if(null !=model.getUrl()){
						if(!"".equals(model.getUrl())){
							cell.setCellValue(model.getUrl());
						}else{
							cell.setCellValue("..");
						}
					
					}else{
						cell.setCellValue("..");
					}
					
					
				
					cell.setCellStyle(style);
					cell = row.createCell(4);
					
					if(null !=model.getEmotion()){
						if(!"".equals(model.getEmotion())){
							if("1".equals(model.getEmotion())){
								cell.setCellValue("正面");
							}
							if("0".equals(model.getEmotion())){
								cell.setCellValue("中性");
							}
							if("-2".equals(model.getEmotion())){
								cell.setCellValue("负面");
							}
						}else{
							cell.setCellValue("..");
						}
					}else{
						cell.setCellValue("..");
					}
					///
					
					//
					cell.setCellStyle(style);
					cell = row.createCell(5);
					
					if(null !=model.getKeywordRule()){
						if(!"".equals(model.getKeywordRule())){
							 int length = model.getKeywordRule().length();
							 if(length> 2){
								 cell.setCellValue(model.getKeywordRule().substring(2,length-2));
							 }else{
								 cell.setCellValue("..");
							 }
							
						}else{
							cell.setCellValue("..");
						}
					
					}else{
						cell.setCellValue("..");
					}
					
					cell.setCellStyle(style);
					cell = row.createCell(6);
					if(null!=model.getFabuTime()){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
						cell.setCellValue(format.format(model.getFabuTime()));
						System.out.println(model.getFabuTime());
					}else{
						cell.setCellValue("..");
					}
					
					 cell.setCellStyle(style);
						cell = row.createCell(7);
		                if(null!=model.getAuthor()){
							if(!"".equals(model.getAuthor())){
								cell.setCellValue(model.getAuthor());
							}
						}else{
							cell.setCellValue("..");
						}
		                
		                cell.setCellStyle(style);
		                cell.setCellStyle(style);
						cell = row.createCell(8);
		                if(null!=model.getAuthor()){
							if(!"".equals(model.getAuthor())){
								//cell.setCellValue(model.getAuthor());
								if(model.getAuthor().contains("、")){
									model.setAuthor(model.getAuthor().replace("、", " "));
								}
								String[] malist=model.getAuthor().split("\\s+");
								if(regionlist.size()>0){
									for(int i=0;i<regionlist.size();i++){
										if(regionlist.get(i).getAuthor().contains(",")){
											String[] arr = regionlist.get(i).getAuthor().split(",");
											for(int j=0;j<arr.length;j++){
												
												if(arr[j].equals(malist[0])){
													cell.setCellValue(regionlist.get(i).getRegion());
												}
											}
										}else{
											if(malist[0].equals(regionlist.get(i).getAuthor())){
												cell.setCellValue(regionlist.get(i).getRegion());
											}
										}
									}
								}else{
									cell.setCellValue("..");
								}
							}
						}else{
							cell.setCellValue("..");
						}
		            	cell.setCellStyle(style);
						cell = row.createCell(9);
							if(model.getWarning()){
								cell.setCellValue("已预警");
							}else{
								cell.setCellValue("未预警");
							}
							cell.setCellStyle(style);
							cell = row.createCell(10);
							if(!model.getFormats().equals("") && model.getFormats()!=null){
								cell.setCellValue(AppConstant.covent.enToCn(model.getFormats()));
							}else{
								cell.setCellValue("..");
							}
						
					
					rownum++;
				}
			}
		
         
			String date = DateFormatUtil.getCurrentTime("yyyyMMddHHmmss");
			String fileName = date + ".xls";
			String headStr = "attachment; filename=" + fileName;
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", headStr);
			String userid = (String) request.getSession().getAttribute("userid");
			String folderName = userid;
			String path = request.getSession().getServletContext()
					.getRealPath("/upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd"));
			File targetFile = new File(path, fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			if (!targetFile.exists()) {
				targetFile.createNewFile();
				// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
			}
			path = path + "/" + fileName;
			loadurl = "upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd") + "/" + fileName;
			out = new FileOutputStream(new File(path));
			workbook.write(out);
			map.put("flag", true);
			map.put("result", loadurl);
		} catch (IOException e) {
			map.put("flag", false);
			map.put("result", e.getMessage());
			Log.error(e.getMessage(),e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					Log.error(e.getMessage(),e);
				}
			}
		}
		return map;
	}
	@Override
	public Map<String, Object> dowloadfilterCom(SubJectArticleBo record, HttpServletResponse response, HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<String> selected = record.getIds();
		List<String> list1 = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (selected != null) {
			for (String media : selected) {
				media = media.replace("\"", "").replace("[", "").replace("]", "");
				list1.add(media);
			}
		}
		List<SubJectArticleBo> list = subjectArticleBoMapper.dowloadfilterCom(list1);
		
		//
		if (list == null || list.isEmpty()) {
			map.put("flag", false);
			map.put("result", "没有查询到数据");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 50 * 256);
		sheet.setColumnWidth(2, 50 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 45 * 256);
		sheet.setColumnWidth(6, 45 * 256);
		sheet.setColumnWidth(7, 45 * 256);
		sheet.setColumnWidth(8, 60 * 256);
		sheet.setColumnWidth(9, 45 * 256);
		OutputStream out = null;
		String loadurl = "";
		try {
			HSSFRow rowm = sheet.createRow(0); // 标题行
			String top[] = { "序号", "标题", "简介", "媒体类型", "来源", "原文地址", "发布时间","情感","关键词","预警" };
			for (int i = 0; i < top.length; i++) {
				HSSFCell cellm = rowm.createCell(i);
				cellm.setCellValue(top[i]);
				cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
			}
			int rownum = 1;
			HSSFCellStyle style =ExportExcelUtil.getStyle(workbook);
			if(null!=list){
				for (SubJectArticleBo model: list) {
					HSSFRow row = sheet.createRow(rownum);
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(rownum);
					cell.setCellStyle(style);
					cell = row.createCell(1);
					if(!model.getTittle().equals("") && model.getTittle()!=null){
						cell.setCellValue(model.getTittle());
					}else{
						cell.setCellValue("..");
					}
					
					cell.setCellStyle(style);
					cell = row.createCell(2);
					if(!model.getContent().equals("") && model.getContent()!=null){
						cell.setCellValue(model.getContent());
					}else{
						cell.setCellValue("..");
					}
					cell.setCellStyle(style);
					cell = row.createCell(3);
					if(!model.getFormats().equals("") && model.getFormats()!=null){
						cell.setCellValue(AppConstant.covent.enToCn(model.getFormats()));
					}else{
						cell.setCellValue("..");
					}
				
					cell.setCellStyle(style);
					cell = row.createCell(4);
					if(!model.getDataSource().equals("") && model.getDataSource()!=null){
						cell.setCellValue(model.getDataSource());
					}else{
						cell.setCellValue("..");
					}
					//
					cell.setCellStyle(style);
					cell = row.createCell(5);
					if(!model.getUrl().equals("") && model.getUrl()!=null){
						cell.setCellValue(model.getUrl());
					}else{
						cell.setCellValue("..");
					}
					//
					cell.setCellStyle(style);
					cell = row.createCell(6);
					if(model.getFabuTime()!=null){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
						cell.setCellValue(format.format(model.getFabuTime()));
						System.out.println(model.getFabuTime());
					}else{
						cell.setCellValue("..");
					}
					//
					cell.setCellStyle(style);
					cell = row.createCell(7);
					if(!model.getEmotion().equals("") && model.getEmotion()!=null){
						if("1".equals(model.getEmotion())){
							cell.setCellValue("正面");
						}
						if("0".equals(model.getEmotion())){
							cell.setCellValue("中性");
						}
						if("-2".equals(model.getEmotion())){
							cell.setCellValue("负面");
						}
						
					}else{
						cell.setCellValue("..");
					}
					
					//
					cell.setCellStyle(style);
					cell = row.createCell(8);
					System.out.println(rownum);
					System.out.println(model.getKeywordRule());
					System.out.println(model.getWarning());
					if(!model.getKeywordRule().equals("") && model.getKeywordRule()!=null){
						cell.setCellValue(model.getKeywordRule().substring(2, model.getKeywordRule().length()-2));
					}else{
						cell.setCellValue("..");
					}
					cell.setCellStyle(style);
					cell = row.createCell(9);
						if(model.getWarning()){
							cell.setCellValue("已预警");
						}else{
							cell.setCellValue("未预警");
						}
					rownum++;
				}
			}
		
			String date = DateFormatUtil.getCurrentTime("yyyyMMddHHmmss");
			String fileName = date + ".xls";
			String headStr = "attachment; filename=" + fileName;
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", headStr);
			String userid = (String) request.getSession().getAttribute("userid");
			String folderName = "excel";
			String path = request.getSession().getServletContext()
					.getRealPath("/upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd"));
			File targetFile = new File(path, fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			if (!targetFile.exists()) {
				targetFile.createNewFile();
				// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
			}
			path = path + "/" + fileName;
			loadurl = "upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd") + "/" + fileName;
			out = new FileOutputStream(new File(path));
			workbook.write(out);
			map.put("flag", true);
			map.put("result", loadurl);
		} catch (IOException e) {
			map.put("flag", false);
			map.put("result", e.getMessage());
			Log.error(e.getMessage(),e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					Log.error(e.getMessage(),e);
				}
			}
		}
		return map;
	}
	@Override
	public Map<String, Object> alldowloadExcel(SubJectArticleBo record, HttpServletResponse response,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		List<SubJectArticleBo> list = subjectArticleBoMapper.filterCom(record);
		
	    List<Region> regionlist = regionMapper.selectByPrimaryKey(record.getUserid());
		System.out.println(list.size()+"ddddd");
		if (list == null || list.isEmpty()) {
			map.put("flag", false);
			map.put("result", "没有查询到数据");
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		 workbook.setSheetName(0, "文章列表");  
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 50 * 256);
		sheet.setColumnWidth(2, 50 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 45 * 256);
		sheet.setColumnWidth(6, 45 * 256);
		sheet.setColumnWidth(7, 45 * 256);
	    //////
		sheet.setColumnWidth(8, 45 * 256);
		sheet.setColumnWidth(9, 45 * 256);
		sheet.setColumnWidth(10, 45 * 256);
	
		
		OutputStream out = null;
		String loadurl = "";
		try {
			HSSFRow rowm = sheet.createRow(0); // 标题行
	/*		String top[] = {"序号", "标题", "简介", "媒体类型", "来源","原文地址","发布时间","情感","预警","媒体类型"};*/
			String top1[] = {"序号", "网站名称", "标题", "链接", "信息类型", "关键词", "发表日期", "媒体类型","作者","地区","预警"};
			for (int i = 0; i < top1.length; i++) {
				HSSFCell cellm = rowm.createCell(i);
				cellm.setCellValue(top1[i]);
				cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
			}
			int rownum = 1;
			HSSFCellStyle style =ExportExcelUtil.getStyle(workbook);
			for (SubJectArticleBo model: list) {
				if(rownum>60000){
				  break;
				}
				HSSFRow row = sheet.createRow(rownum);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(rownum);
				cell.setCellStyle(style);
				cell = row.createCell(1);
				
				if(null !=model.getDataSource()){
					if(!"".equals(model.getDataSource())){
						cell.setCellValue(model.getDataSource());
					}else{
						cell.setCellValue("..");
					}
				
				}else{
					cell.setCellValue("..");
				}
				////
				
				
				cell.setCellStyle(style);
				cell = row.createCell(2);
				if(null !=model.getTittle()){
					if(!"".equals(model.getTittle())){
						cell.setCellValue(model.getTittle());
					}else{
						cell.setCellValue("..");
					}
					
				}else{
					cell.setCellValue("..");
				}
				
				cell.setCellStyle(style);
				cell = row.createCell(3);
				if(null !=model.getUrl()){
					if(!"".equals(model.getUrl())){
						cell.setCellValue(model.getUrl());
					}else{
						cell.setCellValue("..");
					}
				
				}else{
					cell.setCellValue("..");
				}
				
				
			
				cell.setCellStyle(style);
				cell = row.createCell(4);
				
				if(null ==model.getEmotion() || "".equals(model.getEmotion())){
					cell.setCellValue("..");
				}else{
					if("1".equals(model.getEmotion())){
						cell.setCellValue("正面");
					}
					if("0".equals(model.getEmotion())){
						cell.setCellValue("中性");
					}
					if("-2".equals(model.getEmotion())){
						cell.setCellValue("负面");
					}
				}
				cell.setCellStyle(style);
				cell = row.createCell(5);
				
				if(null !=model.getKeywordRule()){
					if(!"".equals(model.getKeywordRule())){
						 int length = model.getKeywordRule().length();
						 if(length>2){
							 cell.setCellValue(model.getKeywordRule().substring(2,length-2));
						 }else{
							 cell.setCellValue("..");
						 }
						
					}else{
						cell.setCellValue("..");
					}
				
				}else{
					cell.setCellValue("..");
				}
				
				cell.setCellStyle(style);
				cell = row.createCell(6);
				if(null!=model.getFabuTime()){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
					cell.setCellValue(format.format(model.getFabuTime()));
					
				}else{
					cell.setCellValue("..");
				}
				
				cell.setCellStyle(style);
				
			
                cell.setCellStyle(style);
				cell = row.createCell(7);
				
                if(null!=model.getFormats()){
					if(!"".equals(model.getFormats())){
						cell.setCellValue(AppConstant.covent.enToCn(model.getFormats()));
					}
				}else{
					cell.setCellValue("..");
				}
                
                ////////
                cell.setCellStyle(style);
				cell = row.createCell(8);
                if(null!=model.getAuthor()){
					if(!"".equals(model.getAuthor())){
						cell.setCellValue(model.getAuthor());
					}
				}else{
					cell.setCellValue("..");
				}
                
                cell.setCellStyle(style);
				cell = row.createCell(9);
				if(null!=model.getAuthor()){
					if(!"".equals(model.getAuthor())){
						//cell.setCellValue(model.getAuthor());
						if(model.getAuthor().contains("、")){
							model.setAuthor(model.getAuthor().replace("、", " "));
						}
						String[] malist=model.getAuthor().split("\\s+");
						if(regionlist.size()>0){
							for(int i=0;i<regionlist.size();i++){
								if(regionlist.get(i).getAuthor().contains(",")){
									String[] arr = regionlist.get(i).getAuthor().split(",");
									for(int j=0;j<arr.length;j++){
										
										if(arr[j].equals(malist[0])){
											cell.setCellValue(regionlist.get(i).getRegion());
										}
									}
								}else{
									if(malist[0].equals(regionlist.get(i).getAuthor())){
										cell.setCellValue(regionlist.get(i).getRegion());
									}
								}
							}
						}else{
							cell.setCellValue("..");
						}
					}
				}else{
					cell.setCellValue("..");
				}
				cell.setCellStyle(style);
				cell = row.createCell(10);
					if(model.getWarning()){
						cell.setCellValue("已预警");
					}else{
						cell.setCellValue("未预警");
					}
			
				rownum++;
			}
           //新闻
			record.setFormats("news");
			
			List<SubJectArticleBo> list1 = new ArrayList<SubJectArticleBo>();
			List<SubJectArticleBo> list2 = new ArrayList<SubJectArticleBo>();
			List<SubJectArticleBo> list3 = new ArrayList<SubJectArticleBo>();
			List<SubJectArticleBo> list4 = new ArrayList<SubJectArticleBo>();
			// 贴吧
			List<SubJectArticleBo> list5 = new ArrayList<SubJectArticleBo>();
			
			System.out.println(list.size()+"所有的文章列表");
			for(int i=0;i<list.size();i++){
				if("news".equals(list.get(i).getFormats())){
					list1.add(list.get(i));
				}else if("blog".equals(list.get(i).getFormats())){
					if(null !=list.get(i).getDataSource() && !"".equals(list.get(i).getDataSource())){
						if(list.get(i).getDataSource().contains("博客")){
							list2.add(list.get(i));
						}
					}
					
				}else if("bbs".equals(list.get(i).getFormats())){
					if(null !=list.get(i).getDataSource() && !"".equals(list.get(i).getDataSource())){
						if(list.get(i).getDataSource().contains("论坛") || list.get(i).getDataSource().contains("社区")){
							list3.add(list.get(i));
						}
					}
				}else if("print_media".equals(list.get(i).getFormats())){
					list4.add(list.get(i));
				}else if("tieba".equals(list.get(i).getFormats())){
					list5.add(list.get(i));
				}
			}
			
			HSSFSheet sheet1 = workbook.createSheet();
			workbook.setSheetName(1, "新闻文章列表");
			sheet1.setColumnWidth(0, 10 * 256);
			sheet1.setColumnWidth(1, 50 * 256);
			sheet1.setColumnWidth(2, 50 * 256);
			sheet1.setColumnWidth(3, 20 * 256);
			sheet1.setColumnWidth(4, 20 * 256);
			sheet1.setColumnWidth(5, 45 * 256);
			sheet1.setColumnWidth(6, 45 * 256);
			sheet1.setColumnWidth(7, 45 * 256);
			sheet1.setColumnWidth(8, 45 * 256);
			
		    HSSFRow rowm1= sheet1.createRow(0); // 标题行
			String top[] = {"序号", "网站名称", "标题", "链接", "信息类型", "关键词", "发表日期", "作者","地区"};
				for (int i = 0; i < top.length; i++) {
					HSSFCell cellm = rowm1.createCell(i);
					cellm.setCellValue(top[i]);
					cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
		       }
				int rownum1 = 1;
				HSSFCellStyle style1 =ExportExcelUtil.getStyle(workbook);
				for (SubJectArticleBo model: list1) {
					if(rownum1>60000){
						  break;
						}
					HSSFRow row = sheet1.createRow(rownum1);
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(rownum1);
					cell.setCellStyle(style1);
					cell = row.createCell(1);
					
					if(null !=model.getDataSource()){
						if(!"".equals(model.getDataSource())){
							cell.setCellValue(model.getDataSource());
						}else{
							cell.setCellValue("..");
						}
					
					}else{
						cell.setCellValue("..");
					}
					cell.setCellStyle(style);
					cell = row.createCell(2);
					if(null !=model.getTittle()){
						if(!"".equals(model.getTittle())){
							cell.setCellValue(model.getTittle());
						}else{
							cell.setCellValue("..");
						}
					}else{
						cell.setCellValue("..");
					}
					cell.setCellStyle(style);
					cell = row.createCell(3);
					if(null !=model.getUrl()){
						if(!"".equals(model.getUrl())){
							cell.setCellValue(model.getUrl());
						}else{
							cell.setCellValue("..");
						}
					
					}else{
						cell.setCellValue("..");
					}
				
					cell.setCellStyle(style);
					cell = row.createCell(4);
					if(null !=model.getEmotion()){
						if(!"".equals(model.getEmotion())){
							if("1".equals(model.getEmotion())){
								cell.setCellValue("正面");
							}
							if("0".equals(model.getEmotion())){
								cell.setCellValue("中性");
							}
							if("-2".equals(model.getEmotion())){
								cell.setCellValue("负面");
							}
						}else{
							cell.setCellValue("..");
						}
					}else{
						cell.setCellValue("..");
					}
					cell.setCellStyle(style);
					cell = row.createCell(5);
					
					if(null !=model.getKeywordRule()){
						if(!"".equals(model.getKeywordRule())){
							 int length = model.getKeywordRule().length();
							 if(length> 2){
								 cell.setCellValue(model.getKeywordRule().substring(2,length-2));
							 }else{
								 cell.setCellValue(".."); 
							 }
							
						}else{
							cell.setCellValue("..");
						}
					
					}else{
						cell.setCellValue("..");
					}
					
					cell.setCellStyle(style);
					cell = row.createCell(6);
					if(null!=model.getFabuTime()){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
						cell.setCellValue(format.format(model.getFabuTime()));
						
					}else{
						cell.setCellValue("..");
					}
					cell.setCellStyle(style);
					cell = row.createCell(7);
	                if(null!=model.getAuthor()){
						if(!"".equals(model.getAuthor())){
							cell.setCellValue(model.getAuthor());
						}
					}else{
						cell.setCellValue("..");
					}
	                cell.setCellStyle(style);
					cell = row.createCell(8);
	                if(null!=model.getAuthor()){
						if(!"".equals(model.getAuthor())){
							//cell.setCellValue(model.getAuthor());
							if(model.getAuthor().contains("、")){
								model.setAuthor(model.getAuthor().replace("、", " "));
							}
							String[] malist=model.getAuthor().split("\\s+");
							if(regionlist.size()>0){
								for(int i=0;i<regionlist.size();i++){
									if(regionlist.get(i).getAuthor().contains(",")){
										String[] arr = regionlist.get(i).getAuthor().split(",");
										for(int j=0;j<arr.length;j++){
											
											if(arr[j].equals(malist[0])){
												cell.setCellValue(regionlist.get(i).getRegion());
											}
										}
									}else{
										if(malist[0].equals(regionlist.get(i).getAuthor())){
											cell.setCellValue(regionlist.get(i).getRegion());
										}
									}
								}
							}else{
								cell.setCellValue("..");
							}
						}
					}else{
						cell.setCellValue("..");
					}
					
					
					rownum1++;
				}
			//博客
				//record.setFormats("blog");
				//List<SubJectArticleBo> list2 = new ArrayList<SubJectArticleBo>();
				
				System.out.println(list2.size()+"fffffffffffffffffffff");
				HSSFSheet sheet2 = workbook.createSheet();
				workbook.setSheetName(2, "博客文章列表");
				sheet2.setColumnWidth(0, 10 * 256);
				sheet2.setColumnWidth(1, 50 * 256);
				sheet2.setColumnWidth(2, 50 * 256);
				sheet2.setColumnWidth(3, 20 * 256);
				sheet2.setColumnWidth(4, 20 * 256);
				sheet2.setColumnWidth(5, 45 * 256);
				sheet2.setColumnWidth(6, 45 * 256);
				sheet2.setColumnWidth(7, 45 * 256);
				sheet2.setColumnWidth(8, 45 * 256);
			    HSSFRow rowm2= sheet2.createRow(0); // 标题行
				String top2[] = {"序号", "网站名称", "标题", "链接", "信息类型", "关键词", "发表日期","作者","地区"};
					for (int i = 0; i < top2.length; i++) {
						HSSFCell cellm = rowm2.createCell(i);
						cellm.setCellValue(top2[i]);
						cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
			       }
					int rownum2 = 1;
					HSSFCellStyle style2 =ExportExcelUtil.getStyle(workbook);
					for (SubJectArticleBo model: list2) {
						if(rownum2>60000){
							  break;
							}
						HSSFRow row = sheet2.createRow(rownum2);
						HSSFCell cell = row.createCell(0);
						cell.setCellValue(rownum2);
						cell.setCellStyle(style2);
						cell = row.createCell(1);
						
						if(null !=model.getDataSource()){
							if(!"".equals(model.getDataSource())){
								cell.setCellValue(model.getDataSource());
							}else{
								cell.setCellValue("..");
							}
						
						}else{
							cell.setCellValue("..");
						}
						cell.setCellStyle(style);
						cell = row.createCell(2);
						if(null !=model.getTittle()){
							if(!"".equals(model.getTittle())){
								cell.setCellValue(model.getTittle());
							}else{
								cell.setCellValue("..");
							}
						}else{
							cell.setCellValue("..");
						}
						cell.setCellStyle(style);
						cell = row.createCell(3);
						if(null !=model.getUrl()){
							if(!"".equals(model.getUrl())){
								cell.setCellValue(model.getUrl());
							}else{
								cell.setCellValue("..");
							}
						
						}else{
							cell.setCellValue("..");
						}
						cell.setCellStyle(style);
						cell = row.createCell(4);
						if(null !=model.getEmotion()){
							if(!"".equals(model.getEmotion())){
								if("1".equals(model.getEmotion())){
									cell.setCellValue("正面");
								}
								if("0".equals(model.getEmotion())){
									cell.setCellValue("中性");
								}
								if("-2".equals(model.getEmotion())){
									cell.setCellValue("负面");
								}
							}else{
								cell.setCellValue("..");
							}
						}else{
							cell.setCellValue("..");
						}
						cell.setCellStyle(style);
						cell = row.createCell(5);
						
						if(null !=model.getKeywordRule()){
							if(!"".equals(model.getKeywordRule())){
								 int length = model.getKeywordRule().length();
								cell.setCellValue(model.getKeywordRule().substring(2,length-2));
							}else{
								cell.setCellValue("..");
							}
						
						}else{
							cell.setCellValue("..");
						}
						
						cell.setCellStyle(style);
						cell = row.createCell(6);
						if(null!=model.getFabuTime()){
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
							cell.setCellValue(format.format(model.getFabuTime()));
							
						}else{
							cell.setCellValue("..");
						}
						
						cell.setCellStyle(style);
						
						cell.setCellStyle(style);
						cell = row.createCell(7);
		                if(null!=model.getAuthor()){
							if(!"".equals(model.getAuthor())){
								cell.setCellValue(model.getAuthor());
							}
						}else{
							cell.setCellValue("..");
						}
		                cell.setCellStyle(style);
						cell = row.createCell(8);
		                if(null!=model.getAuthor()){
							if(!"".equals(model.getAuthor())){
								//cell.setCellValue(model.getAuthor());
								if(model.getAuthor().contains("、")){
									model.setAuthor(model.getAuthor().replace("、", " "));
								}
								String[] malist=model.getAuthor().split("\\s+");
								if(regionlist.size()>0){
									for(int i=0;i<regionlist.size();i++){
										if(regionlist.get(i).getAuthor().contains(",")){
											String[] arr = regionlist.get(i).getAuthor().split(",");
											for(int j=0;j<arr.length;j++){
												
												if(arr[j].equals(malist[0])){
													cell.setCellValue(regionlist.get(i).getRegion());
												}
											}
										}else{
											if(malist[0].equals(regionlist.get(i).getAuthor())){
												cell.setCellValue(regionlist.get(i).getRegion());
											}
										}
									}
								}else{
									cell.setCellValue("..");
								}
							}
						}else{
							cell.setCellValue("..");
						}
						rownum2++;
					}
					//平媒
					//record.setFormats("print_media");
					//List<SubJectArticleBo> list4= subjectArticleBoMapper.filterCom(record);
					System.out.println(list4.size()+"fffffffffffffffffffff");
					HSSFSheet sheet4 = workbook.createSheet();
					workbook.setSheetName(3, "平媒列表");
					sheet4.setColumnWidth(0, 10 * 256);
					sheet4.setColumnWidth(1, 50 * 256);
					sheet4.setColumnWidth(2, 50 * 256);
					sheet4.setColumnWidth(3, 20 * 256);
					sheet4.setColumnWidth(4, 20 * 256);
					sheet4.setColumnWidth(5, 45 * 256);
					sheet4.setColumnWidth(6, 45 * 256);
					sheet4.setColumnWidth(7, 45 * 256);
					sheet4.setColumnWidth(8, 45 * 256);
				    HSSFRow rowm4= sheet4.createRow(0); // 标题行
					String top4[] = {"序号", "网站名称", "标题", "链接", "信息类型", "关键词", "发表日期","作者","地区"};
						for (int i = 0; i < top4.length; i++) {
							HSSFCell cellm = rowm4.createCell(i);
							cellm.setCellValue(top4[i]);
							cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
				       }
						int rownum4 = 1;
						HSSFCellStyle style4 =ExportExcelUtil.getStyle(workbook);
						for (SubJectArticleBo model: list4) {
							if(rownum4>60000){
								  break;
								}
							HSSFRow row = sheet4.createRow(rownum4);
							HSSFCell cell = row.createCell(0);
							cell.setCellValue(rownum4);
							cell.setCellStyle(style1);
							cell = row.createCell(1);
							
							if(null !=model.getDataSource()){
								if(!"".equals(model.getDataSource())){
									cell.setCellValue(model.getDataSource());
								}else{
									cell.setCellValue("..");
								}
							
							}else{
								cell.setCellValue("..");
							}
							cell.setCellStyle(style);
							cell = row.createCell(2);
							if(null !=model.getTittle()){
								if(!"".equals(model.getTittle())){
									cell.setCellValue(model.getTittle());
								}else{
									cell.setCellValue("..");
								}
							}else{
								cell.setCellValue("..");
							}
							cell.setCellStyle(style);
							cell = row.createCell(3);
							if(null !=model.getUrl()){
								if(!"".equals(model.getUrl())){
									cell.setCellValue(model.getUrl());
								}else{
									cell.setCellValue("..");
								}
							
							}else{
								cell.setCellValue("..");
							}
						
							cell.setCellStyle(style);
							cell = row.createCell(4);
							if(null !=model.getEmotion()){
								if(!"".equals(model.getEmotion())){
									if("1".equals(model.getEmotion())){
										cell.setCellValue("正面");
									}
									if("0".equals(model.getEmotion())){
										cell.setCellValue("中性");
									}
									if("-2".equals(model.getEmotion())){
										cell.setCellValue("负面");
									}
								}else{
									cell.setCellValue("..");
								}
							}else{
								cell.setCellValue("..");
							}
							cell.setCellStyle(style);
							cell = row.createCell(5);
							
							if(null !=model.getKeywordRule()){
								if(!"".equals(model.getKeywordRule())){
									 int length = model.getKeywordRule().length();
									cell.setCellValue(model.getKeywordRule().substring(2,length-2));
								}else{
									cell.setCellValue("..");
								}
							
							}else{
								cell.setCellValue("..");
							}
							
							cell.setCellStyle(style);
							cell = row.createCell(6);
							if(null!=model.getFabuTime()){
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
								cell.setCellValue(format.format(model.getFabuTime()));
								
							}else{
								cell.setCellValue("..");
							}
							cell.setCellStyle(style);
							cell = row.createCell(7);
			                if(null!=model.getAuthor()){
								if(!"".equals(model.getAuthor())){
									cell.setCellValue(model.getAuthor());
								}
							}else{
								cell.setCellValue("..");
							}
			                cell.setCellStyle(style);
							cell = row.createCell(8);
			                if(null!=model.getAuthor()){
								if(!"".equals(model.getAuthor())){
									//cell.setCellValue(model.getAuthor());
									if(model.getAuthor().contains("、")){
										model.setAuthor(model.getAuthor().replace("、", " "));
									}
									String[] malist=model.getAuthor().split("\\s+");
									if(regionlist.size()>0){
										for(int i=0;i<regionlist.size();i++){
											if(regionlist.get(i).getAuthor().contains(",")){
												String[] arr = regionlist.get(i).getAuthor().split(",");
												for(int j=0;j<arr.length;j++){
													
													if(arr[j].equals(malist[0])){
														cell.setCellValue(regionlist.get(i).getRegion());
													}
												}
											}else{
												if(malist[0].equals(regionlist.get(i).getAuthor())){
													cell.setCellValue(regionlist.get(i).getRegion());
												}
											}
										}
									}else{
										cell.setCellValue("..");
									}
								}
							}else{
								cell.setCellValue("..");
							}
							rownum4++;
						}
						//论坛
					
						System.out.println(list3.size()+"fffffffffffffffffffff");
						HSSFSheet sheet3 = workbook.createSheet();
						workbook.setSheetName(4, "论坛列表");
						sheet3.setColumnWidth(0, 10 * 256);
						sheet3.setColumnWidth(1, 50 * 256);
						sheet3.setColumnWidth(2, 50 * 256);
						sheet3.setColumnWidth(3, 20 * 256);
						sheet3.setColumnWidth(4, 20 * 256);
						sheet3.setColumnWidth(5, 45 * 256);
						sheet3.setColumnWidth(6, 45 * 256);
						sheet3.setColumnWidth(7, 45 * 256);
						sheet3.setColumnWidth(8, 45 * 256);
					    HSSFRow rowm3= sheet3.createRow(0); // 标题行
						String top3[] = {"序号", "网站名称", "标题", "链接", "信息类型", "关键词", "发表日期","作者","地区"};
							for (int i = 0; i < top3.length; i++) {
								HSSFCell cellm = rowm3.createCell(i);
								cellm.setCellValue(top3[i]);
								cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
					       }
							int rownum3 = 1;
							HSSFCellStyle style3 =ExportExcelUtil.getStyle(workbook);
							for (SubJectArticleBo model: list3) {
								if(rownum3>60000){
									  break;
									}
								HSSFRow row = sheet3.createRow(rownum3);
								HSSFCell cell = row.createCell(0);
								cell.setCellValue(rownum3);
								cell.setCellStyle(style1);
								cell = row.createCell(1);
								
								if(null !=model.getDataSource()){
									if(!"".equals(model.getDataSource())){
										cell.setCellValue(model.getDataSource());
									}else{
										cell.setCellValue("..");
									}
								
								}else{
									cell.setCellValue("..");
								}
								cell.setCellStyle(style);
								cell = row.createCell(2);
								if(null !=model.getTittle()){
									if(!"".equals(model.getTittle())){
										cell.setCellValue(model.getTittle());
									}else{
										cell.setCellValue("..");
									}
								}else{
									cell.setCellValue("..");
								}
								cell.setCellStyle(style);
								cell = row.createCell(3);
								if(null !=model.getUrl()){
									if(!"".equals(model.getUrl())){
										cell.setCellValue(model.getUrl());
									}else{
										cell.setCellValue("..");
									}
								
								}else{
									cell.setCellValue("..");
								}
								cell.setCellStyle(style);
								cell = row.createCell(4);
								if(null !=model.getEmotion()){
									if(!"".equals(model.getEmotion())){
										if("1".equals(model.getEmotion())){
											cell.setCellValue("正面");
										}
										if("0".equals(model.getEmotion())){
											cell.setCellValue("中性");
										}
										if("-2".equals(model.getEmotion())){
											cell.setCellValue("负面");
										}
									}else{
										cell.setCellValue("..");
									}
								}else{
									cell.setCellValue("..");
								}
								cell.setCellStyle(style);
								cell = row.createCell(5);
								
								if(null !=model.getKeywordRule()){
									if(!"".equals(model.getKeywordRule())){
										 int length = model.getKeywordRule().length();
										cell.setCellValue(model.getKeywordRule().substring(2,length-2));
									}else{
										cell.setCellValue("..");
									}
								
								}else{
									cell.setCellValue("..");
								}
								cell.setCellStyle(style);
								cell = row.createCell(6);
								if(null!=model.getFabuTime()){
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
									cell.setCellValue(format.format(model.getFabuTime()));
									
								}else{
									cell.setCellValue("..");
								}
								cell.setCellStyle(style);
								cell = row.createCell(7);
				                if(null!=model.getAuthor()){
									if(!"".equals(model.getAuthor())){
										cell.setCellValue(model.getAuthor());
									}
								}else{
									cell.setCellValue("..");
								}
				                cell.setCellStyle(style);
								cell = row.createCell(8);
				                if(null!=model.getAuthor()){
									if(!"".equals(model.getAuthor())){
										//cell.setCellValue(model.getAuthor());
										if(model.getAuthor().contains("、")){
											model.setAuthor(model.getAuthor().replace("、", " "));
										}
										String[] malist=model.getAuthor().split("\\s+");
										if(regionlist.size()>0){
											for(int i=0;i<regionlist.size();i++){
												if(regionlist.get(i).getAuthor().contains(",")){
													String[] arr = regionlist.get(i).getAuthor().split(",");
													for(int j=0;j<arr.length;j++){
														
														if(arr[j].equals(malist[0])){
															cell.setCellValue(regionlist.get(i).getRegion());
														}
													}
												}else{
													if(malist[0].equals(regionlist.get(i).getAuthor())){
														cell.setCellValue(regionlist.get(i).getRegion());
													}
												}
											}
										}else{
											cell.setCellValue("..");
										}
									}
								}else{
									cell.setCellValue("..");
								}
								rownum3++;
							}
							
							
							HSSFSheet sheet5 = workbook.createSheet();
							workbook.setSheetName(5, "贴吧列表");
							sheet5.setColumnWidth(0, 10 * 256);
							sheet5.setColumnWidth(1, 50 * 256);
							sheet5.setColumnWidth(2, 50 * 256);
							sheet5.setColumnWidth(3, 20 * 256);
							sheet5.setColumnWidth(4, 20 * 256);
							sheet5.setColumnWidth(5, 25 * 256);
							sheet5.setColumnWidth(6, 25 * 256);
							sheet5.setColumnWidth(7, 25 * 256);
							sheet5.setColumnWidth(8, 25 * 256);
							sheet5.setColumnWidth(9, 25 * 256);
							sheet5.setColumnWidth(10, 25 * 256);
						    HSSFRow rowm5= sheet5.createRow(0); // 标题行
							String top5[] = {"序号", "网站名称", "标题", "链接", "信息类型", "关键词", "发表日期","作者","地区"};
								for (int i = 0; i < top5.length; i++) {
									HSSFCell cellm = rowm5.createCell(i);
									cellm.setCellValue(top5[i]);
									cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
						       }
								int rownum5 = 1;
								HSSFCellStyle style5 =ExportExcelUtil.getStyle(workbook);
								for (SubJectArticleBo model: list5) {
									if(rownum5>60000){
										  break;
										}
									HSSFRow row = sheet5.createRow(rownum5);
									HSSFCell cell = row.createCell(0);
									cell.setCellValue(rownum5);
									cell.setCellStyle(style1);
									cell = row.createCell(1);
									
									if(null !=model.getDataSource()){
										if(!"".equals(model.getDataSource())){
											cell.setCellValue(model.getDataSource());
										}else{
											cell.setCellValue("..");
										}
									
									}else{
										cell.setCellValue("..");
									}
									cell.setCellStyle(style);
									cell = row.createCell(2);
									if(null !=model.getTittle()){
										if(!"".equals(model.getTittle())){
											cell.setCellValue(model.getTittle());
										}else{
											cell.setCellValue("..");
										}
									}else{
										cell.setCellValue("..");
									}
									cell.setCellStyle(style);
									cell = row.createCell(3);
									if(null !=model.getUrl()){
										if(!"".equals(model.getUrl())){
											cell.setCellValue(model.getUrl());
										}else{
											cell.setCellValue("..");
										}
									
									}else{
										cell.setCellValue("..");
									}
								
									cell.setCellStyle(style);
									cell = row.createCell(4);
									if(null !=model.getEmotion()){
										if(!"".equals(model.getEmotion())){
											if("1".equals(model.getEmotion())){
												cell.setCellValue("正面");
											}
											if("0".equals(model.getEmotion())){
												cell.setCellValue("中性");
											}
											if("-2".equals(model.getEmotion())){
												cell.setCellValue("负面");
											}
										}else{
											cell.setCellValue("..");
										}
									}else{
										cell.setCellValue("..");
									}
									cell.setCellStyle(style);
									cell = row.createCell(5);
									
									if(null !=model.getKeywordRule()){
										if(!"".equals(model.getKeywordRule())){
											 int length = model.getKeywordRule().length();
											cell.setCellValue(model.getKeywordRule().substring(2,length-2));
										}else{
											cell.setCellValue("..");
										}
									
									}else{
										cell.setCellValue("..");
									}
									
									cell.setCellStyle(style);
									cell = row.createCell(6);
									if(null!=model.getFabuTime()){
										SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
										cell.setCellValue(format.format(model.getFabuTime()));
										
									}else{
										cell.setCellValue("..");
									}
									cell.setCellStyle(style);
									cell = row.createCell(7);
					                if(null!=model.getAuthor()){
										if(!"".equals(model.getAuthor())){
											cell.setCellValue(model.getAuthor());
										}
									}else{
										cell.setCellValue("..");
									}
					                cell.setCellStyle(style);
									cell = row.createCell(8);
					                if(null!=model.getAuthor()){
										if(!"".equals(model.getAuthor())){
											//cell.setCellValue(model.getAuthor());
											if(model.getAuthor().contains("、")){
												model.setAuthor(model.getAuthor().replace("、", " "));
											}
											String[] malist=model.getAuthor().split("\\s+");
											if(regionlist.size()>0){
												for(int i=0;i<regionlist.size();i++){
													if(regionlist.get(i).getAuthor().contains(",")){
														String[] arr = regionlist.get(i).getAuthor().split(",");
														for(int j=0;j<arr.length;j++){
															
															if(arr[j].equals(malist[0])){
																cell.setCellValue(regionlist.get(i).getRegion());
															}
														}
													}else{
														if(malist[0].equals(regionlist.get(i).getAuthor())){
															cell.setCellValue(regionlist.get(i).getRegion());
														}
													}
												}
											}else{
												cell.setCellValue("..");
											}
										}
									}else{
										cell.setCellValue("..");
									}
									rownum5++;
								}
			String date = DateFormatUtil.getCurrentTime("yyyyMMddHHmmss");
			String fileName = date + ".xls";
			String headStr = "attachment; filename=" + fileName;
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", headStr);
			String userid = (String) request.getSession().getAttribute("userid");
			String folderName = "excel";
			String path = request.getSession().getServletContext()
					.getRealPath("/upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd"));
			File targetFile = new File(path, fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			if (!targetFile.exists()) {
				targetFile.createNewFile();
				// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
			}
			path = path + "/" + fileName;
			loadurl = "upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd") + "/" + fileName;
			out = new FileOutputStream(new File(path));
			workbook.write(out);
			map.put("flag", true);
			map.put("result", loadurl);
		} catch (IOException e) {
			map.put("flag", false);
			map.put("result", e.getMessage());
			Log.error(e.getMessage(),e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					Log.error(e.getMessage(),e);
				}
			}
		}
		return map;
	}
	@Override
	public int deleteSMArticle(String id) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.deleteByPrimaryKey(id);
	}
	@Override
	public SubJectArticleBo selectArticleDetailById(String articleid) {
		// TODO Auto-generated method stub
		
		String shards = AppConstant.solrUrl.ARTICLE+","+AppConstant.solrUrl.METASEARCHPAGE+","+AppConstant.solrUrl.TIEBAPAGE+","+AppConstant.solrUrl.WEIBOPAGE+","+AppConstant.solrUrl.WEIXINPAGE;
		SolrQuery params = new SolrQuery();
		params.set("qt", "/select");
		params.set("q", "id"+":"+articleid);
		params.set("shards", shards);
		QueryResponse query;
		SubJectArticleBo sb = new SubJectArticleBo();
		HttpSolrClient solrServer = new HttpSolrClient(AppConstant.solrUrl.WEIBOPAGE);
		try {
			query = solrServer.query(params);
			SolrDocumentList results = query.getResults();
			if(results.getNumFound()!=0){
				SolrDocument solrDocument = results.get(0);
				String id =  solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"";
				String dataSource = solrDocument.getFieldValue("dataSource")!=null?solrDocument.getFieldValue("dataSource").toString().replace("[", "").replace("]",""):"";
				String pubdate = solrDocument.getFieldValue("updateTime")!=null?solrDocument.getFieldValue("updateTime").toString().replace("[", "").replace("]",""):"";
				String time = solrDocument.getFieldValue("pubdate")!=null?solrDocument.getFieldValue("pubdate").toString().replace("[", "").replace("]",""):"";
				String emotion = solrDocument.getFieldValue("emotion")!=null?solrDocument.getFieldValue("emotion").toString().replace("[", "").replace("]",""):"";
				String author = solrDocument.getFieldValue("author")!=null?solrDocument.getFieldValue("author").toString().replace("[", "").replace("]",""):"";
				String repeatcount = solrDocument.getFieldValue("repeatcount")!=null?solrDocument.getFieldValue("repeatcount").toString().replace("[", "").replace("]",""):"";
				String commtcount = solrDocument.getFieldValue("commtcount")!=null?solrDocument.getFieldValue("commtcount").toString().replace("[", "").replace("]",""):"";
				String readcount = solrDocument.getFieldValue("readcount")!=null?solrDocument.getFieldValue("readcount").toString().replace("[", "").replace("]",""):"";
				String url = solrDocument.getFieldValue("url")!=null?solrDocument.getFieldValue("url").toString().replace("[", "").replace("]",""):"";
				String title = solrDocument.getFieldValue("title_cn")!=null?solrDocument.getFieldValue("title_cn").toString().replace("[", "").replace("]",""):"";
				String content = solrDocument.getFieldValue("content_cn")!=null?solrDocument.getFieldValue("content_cn").toString().replace("[", "").replace("]",""):"";
				String negativeWord = solrDocument.getFieldValue("negativeWord")!=null?solrDocument.getFieldValue("negativeWord").toString().replace("[", "").replace("]",""):"";
			    String positiveWord = solrDocument.getFieldValue("positiveWord")!=null?solrDocument.getFieldValue("positiveWord").toString().replace("[", "").replace("]",""):"";
			    String formats = solrDocument.getFieldValue("formats")!=null?solrDocument.getFieldValue("formats").toString().replace("[", "").replace("]",""):"";
				sb.setId(id);
			   sb.setAuthor(author);
			   sb.setDataSource(dataSource);
			   sb.setTime(pubdate);
			   sb.setEdtime(time);
			   sb.setEmotion(emotion);
			   sb.setUrl(url);
			   sb.setTittle(title);
			   sb.setContent(content);
			   sb.setNegativeWord(negativeWord);
			   sb.setPositiveWord(positiveWord);
			   sb.setFormats(AppConstant.covent.enToCn(formats));
			   if(!"".equals(repeatcount) && null!=repeatcount){
				   int repeatco = Integer.parseInt(repeatcount);
				   sb.setRepeatcount(repeatco);
			   }else{
				   sb.setRepeatcount(-1);
			   }
			 
			   if(!"".equals(commtcount) && null!=commtcount){
				   int commt = Integer.parseInt(commtcount);
				   sb.setCommtcount(commt);
			   }else{
				   sb.setCommtcount(-1);
			   }
			   if(!"".equals(readcount) && null!=readcount){
				   int read= Integer.parseInt(readcount);
				   sb.setReadcount(read);
			   }else{
				   sb.setReadcount(-1);
			   }
			   
			   return sb;
			}else{
				SubjectArticle subArticle = subjectArticleMapper.selectByPrimaryKey(articleid);
				if(subArticle==null){
					return null;
				}
				BeanUtils.copyProperties(subArticle, sb);
				sb.setFormats(AppConstant.covent.enToCn(subArticle.getFormats()));
				   //sb.setTime(DateFormatUtil.dateFormatStringType(subArticle.getUpdatetime(), "yyyy-MM-dd HH:mm:ss"));
				  if(subArticle.getUpdatetime()!=null){
					  sb.setTime(DateFormatUtil.dateFormatStringType(subArticle.getUpdatetime(), "yyyy-MM-dd HH:mm:ss")); 
				   }
				   if(subArticle.getRepeatcount()!=null){
					   sb.setRepeatcount(subArticle.getRepeatcount());
				   }else{
					   sb.setRepeatcount(-1);
				   }
				 
				   if(subArticle.getCommtcount()!=null){
					   sb.setCommtcount(subArticle.getCommtcount());
				   }else{
					   sb.setCommtcount(-1);
				   }
				   if(subArticle.getReadcount()!=null){
					   sb.setReadcount(subArticle.getReadcount());
				   }else{
					   sb.setReadcount(-1);
				   }
				   return sb;
			}
			
		} catch (SolrServerException e) {
			Log.error(e.getMessage(),e);
		} catch (IOException e) {
			Log.error(e.getMessage(),e);
		}
		return sb;
	}
	@Override
	public SubjectMArticle selectSMById(String id) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateEmotion(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.updateSMArticle(record);
	}
	@Override
	public List<SubJectArticleBo> getSimArticle(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		List<SubJectArticleBo> boList = new ArrayList<SubJectArticleBo>();
		String simhashcode = subjectArticleMapper.selectSimhashcode(record.getArticleid());
		List<Map<String, Object>> list = subjectArticleMapper.selectArticleByUserid(record.getUserid());
		for (Map<String, Object> map : list) {
			String simhash = (String) map.get("simhashcode");
			if (simhash == null || "".equals(simhash)) {
				continue;
			}
			int dis = ComMethodUtil.getDistance(simhashcode, simhash);
			if (dis < 5 && dis >= 0) {
				SubJectArticleBo sArticleBo = new SubJectArticleBo();
				sArticleBo.setMid((String) map.get("mid"));
				sArticleBo.setArticleid((String) map.get("articleid"));
				sArticleBo.setTittle((String) map.get("tittle"));
				Date pubdate = (Date) map.get("updatetime");
				sArticleBo.setUpdatetime(pubdate);
				sArticleBo.setDataSource((String) map.get("data_source"));
				sArticleBo.setSimhashcode(((5 - dis) * 2 + 90)+"");
				boList.add(sArticleBo);
			}
		}
		return boList;
	}
	@Override
	public List<SubjectMArticle> selectBySubjectid(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int selectTotalByUserid(String userid) {
		SubjectMArticle sm = subjectMArticleMapper.selectTotalByUserid(userid);
		int days =0;
		if(null!=sm){
			int total = sm.getTotal();
			if(0!=total){
				if(total<=50000){
					  SubjectMArticle sa = subjectMArticleMapper.selectMinPundate(userid);
					  try {
						  if(null!=sa.getPubdate()){
							  days = DateFormatUtil.daysBetween(sa.getPubdate(), new Date());
							  days = 50000/(total/days);
						  }
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						Log.error(e.getMessage());
					} 
					}else{
						SubjectMArticle sma = subjectMArticleMapper.selectMinPundateBigData(userid);
						try {
							if(null!=sma.getPubdate()){
								days = DateFormatUtil.daysBetween(sma.getPubdate(),new Date());
							}
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							Log.error(e.getMessage());
						}
					}
			}else{
				days = -1;
			}
		
		}else{
			days = -1;
		}
		// TODO Auto-generated method stub
		return days;
	}
	@Override
	public int updateSubjectByUserid(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.updateSubjectByUserid(record);
	}
	


}

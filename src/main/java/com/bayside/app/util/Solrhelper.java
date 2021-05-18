package com.bayside.app.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;



/**
 * Solr 工具类, 对索引的处理
 */
public class Solrhelper {

	private static final Logger log = Logger.getLogger(Solrhelper.class);
	private SolrClient solrServer=null;
	   
	 /**
     * 构造方法，初始化solr服务
     */
	public Solrhelper(String url){
		   solrServer = new HttpSolrClient(url);
	 }
	
	 /**
     * 查询所用索引
     * @param start 文档开始的位置
     * @param row 返回文档的最大数目
	 * @throws SolrServerException 
     * @throws IOException
     */
	public SolrDocumentList selectIndex(int start,int row) throws SolrServerException, IOException  {
		
		SolrQuery params = new SolrQuery();
		params.set("q", "*:*");
		params.setStart(start);
		params.setRows(row);
		QueryResponse query = solrServer.query(params);
		SolrDocumentList results = query.getResults();
				
		return results;
	}
	
	/**
     * 自定义查询语句查询
	 * @throws SolrServerException 
     * @throws IOException
     */
	public SolrDocumentList selectby(String q) throws SolrServerException, IOException  {
		
		SolrQuery params = new SolrQuery();
		params.setQuery(q);
		QueryResponse query = solrServer.query(params);
		SolrDocumentList results = query.getResults();
		
		return results;
	}
	
	/**
     * 添加索引
	 * @param <E>
     * @param obj 添加的索引对象
     */
	public <E> void addIndexForObj(E obj){
		try {
			solrServer.addBean(obj);
			solrServer.commit();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (SolrServerException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
	
	/**
     * 批量添加索引
	 * @param <E>
     * @param obj 添加的索引对象
     */
	public <E> void batchAddIndex(List<E> obj){
		try {
			solrServer.addBeans(obj);
			solrServer.commit();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (SolrServerException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		
	}
	 
	/**
     * 删除索引
     * @param id 要删除的索引id
     */
	public void deleteIndex(String id){
		 try {
			solrServer.deleteById(id);
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}	
		 
	}

	/**
     * 批量删除索引
     * @param ids 要删除的索引id
     */
	public void batchDeleteIndex(List<String> ids){
			
		try {
			solrServer.deleteById(ids);
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
	
	/**
     * 清除所用索引
     */
	public void deleteAll(String q){
			
		try {
			solrServer.deleteByQuery(q);
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
	
   public  SolrClient getSolrServer() {
		return solrServer;
	}

	public void setSolrServer(SolrClient solrServer) {
		this.solrServer = solrServer;
	}
	/**
	 * <p>方法名称：getArticleId</p>
	 * <p>方法描述：此方法用于全景舆情的搜索功能</p>
	 * @return
	 * @author Administrator
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @since  2016年7月21日
	 * <p> history 2016年7月21日 Administrator  创建   <p>
	 */
	/*public List<String> getArticleId(String sql,String sqlTwo,String searchTall,String searchall, int start,int row,List<String> mysqlId,String emotion,String mediatype,String weidu,String starttime,String endtime){
		
		List<String> articleIdList=new ArrayList<String>();
		 String shards = "http://192.168.1.104:8983/solr/weibopage,"
	        		+ "http://192.168.1.104:8983/solr/metasearchpage,"
	        		+ "http://192.168.1.104:8983/solr/article,"
	        		+ "http://192.168.1.104:8983/solr/tiebapage,"
	        		+ "http://192.168.1.104:8983/solr/weixinpage";
		 String shards = AppConstant.solrUrl.ARTICLE+AppConstant.solrUrl.METASEARCHPAGE+AppConstant.solrUrl.TIEBAPAGE+AppConstant.solrUrl.WEIBOPAGE+AppConstant.solrUrl.WEIXINPAGE;
		//String shards = "http://192.168.1.104:8983/solr/weibopage";
		//List<String> mysqlId = subjectMArticleMapper.getArticleById(userId,subjectId);
		SolrQuery params = new SolrQuery();
		StringBuffer sid = new StringBuffer("id:(");
		for(int i=0;i<mysqlId.size();i++){
			sid.append(mysqlId.get(i)+" "); 
		}
		//查询条件
		sid.append(")");
		params.set("qt", "/select");
		params.set("q", "*:*");
    	params.set("q", sid.toString());
		params.set("q", sql);
		if(searchall!=null && !"".equals(searchall)){
			params.set("q", searchall);
		}
		params.set("fq", sqlTwo);
		if(searchTall!=null && !"".equals(searchTall)){
			params.set("q", searchTall);
		}
		
		if(emotion!=null && !"".equals(emotion)){
			params.set("q", "emotion:"+emotion);
		}
        if(mediatype!=null && !"".equals(mediatype)){
        	params.set("q", "formats:"+mediatype);
		}
		
       if(weidu!=null && !"".equals(weidu)){
    	   params.set("q", "weidu:"+weidu);
        }
        //时间查询
       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); 
       
       String startTime = df.format(new Date());
       
       SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");  
       SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");  
       sdf2.setTimeZone(TimeZone.getTimeZone("UTC"));  
    //   String result = sdf1.format(date) + "T" + sdf2.format(date) + "Z"; 
       if(starttime!=null && !"".equals(startTime)){
	   if(starttime.equals("current")){  
			      
			        Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, +1);
					String str = df.format(c.getTime());
					
					 startTime = sdf1.format(new Date()) + "T" + sdf2.format(new Date()) + "Z"; 
					 str = sdf1.format(c.getTime()) + "T" + sdf2.format(c.getTime()) + "Z"; 
					params.set("pubdate", "["+startTime+ "TO"+ str+"}");
	   }else if(starttime.equals("sun")){
		  
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, -7);
					String str=sdf1.format(c.getTime()) + "T" + sdf2.format(c.getTime()) + "Z"; 
					startTime = sdf1.format(new Date()) + "T" + sdf2.format(new Date()) + "Z"; 
					params.set("pubdate", "["+str+ "TO"+ startTime+"]");
       }else if(starttime.equals("month")){
    	  
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, -30);
				//	String str=df.format(c.getTime());
					String str=sdf1.format(c.getTime()) + "T" + sdf2.format(c.getTime()) + "Z"; 
					startTime = sdf1.format(new Date()) + "T" + sdf2.format(new Date()) + "Z"; 
					params.set("pubdate", "["+str+ "TO"+ startTime+"]");
	   }
	   if(endtime!=null && !"".equals(endtime)){
		   String end= df.format(endtime);
		   String st = df.format(starttime);
		   String stm = sdf1.format(starttime) + "T" + sdf2.format(starttime) + "Z"; 
		   String emdt = sdf1.format(endtime) + "T" + sdf2.format(endtime) + "Z"; 
		   System.out.println(stm +"::::" +emdt);
		   params.set("pubdate", "["+stm+ "TO"+ emdt+"]");
	   }
	  
       }
		params.set("shards",shards);
		params.setStart(start);
		params.setRows(row);
		QueryResponse query;
		try {
			query = solrServer.query(params);
			SolrDocumentList results = query.getResults();
			long numFound = results.getNumFound();
//			System.out.println("总数：" + numFound);
//			System.out.println(results.size());
			for(SolrDocument solrDocument:results){
				String id =  solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"";
//				 System.out.println("id：" +id);
				 articleIdList.add(id);
			}
//			solrServer.close();
		} catch (SolrServerException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return articleIdList;
	}

	// 查询文章详细内容
	public SolrDocument getArticleInfo(String articleid, int start, int row) {
		int num = 1;
		String shards = "http://222.186.42.7:8983/solr/weibopage," 
		        + "http://222.186.42.7:8983/solr/metasearchpage,"
				+ "http://222.186.42.7:8983/solr/article," 
		        + "http://222.186.42.7:8983/solr/tiebapage,"
				+ "http://222.186.42.7:8983/solr/weixinpage";
		SolrQuery params = new SolrQuery();
		params.set("qt", "/select");
		 params.set("q", "*:*"); 
		params.set("q", articleid);

		params.set("shards", shards);
		params.setStart((num - 1) * row);
		params.setRows(row);
		QueryResponse query;
		try {
			query = solrServer.query(params);
			SolrDocumentList results = query.getResults();
		     
			for(SolrDocument solrDocument:results){
				String id =  solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"";
//				 System.out.println("id：" +id);
				
			}
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
					}
					
				return null;
			}*/
	/**
	 * <p>方法名称：closeServer</p>
	 * <p>方法描述：关闭solr服务的连接</p>
	 * @author Administrator
	 * @since  2016年7月23日
	 * <p> history 2016年7月23日 Administrator  创建   <p>
	 */
	public void closeServer(){
		try {
			solrServer.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
}

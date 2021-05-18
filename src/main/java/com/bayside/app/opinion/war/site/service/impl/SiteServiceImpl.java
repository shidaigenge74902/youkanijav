package com.bayside.app.opinion.war.site.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.myuser.dao.UserMapper;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.site.bo.SiteBo;
import com.bayside.app.opinion.war.site.dao.SiteMapper;
import com.bayside.app.opinion.war.site.model.Site;
import com.bayside.app.opinion.war.site.service.SiteService;
import com.bayside.app.opinion.war.subject.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.subject.dao.SubjectArticleMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectMapper;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectArticle;
import com.bayside.app.util.CommenMethod;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.SolrPage;

import redis.clients.jedis.ShardedJedis;
@Service("siteServiceImpl")
@PropertySource("classpath:server.properties")
public class SiteServiceImpl implements SiteService {
	@Autowired
    private SiteMapper siteMapper;
	@Autowired
	private SubjectArticleMapper subjectArticleMapper; 
	@Resource
	private Environment environment;
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private UserMapper userMapper;
	@Override
	public int insertSite(SiteBo record) {
		// TODO Auto-generated method stub
		return siteMapper.insertSelectiveBo(record);
	}
	@Override
	public Site selectSiteById(String id) {
		// TODO Auto-generated method stub
		return siteMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateSiteById(SiteBo record) {
		// TODO Auto-generated method stub
		return siteMapper.updateByPrimaryKeySelectiveBo(record);
	}
	@Override
	public List<Site> selectSiteByTiaojian(Site record) {
		// TODO Auto-generated method stub
		return siteMapper.selectByTiaojian(record);
	}
	@Override
	public int deleteSiteById(String id) {
		// TODO Auto-generated method stub
		return siteMapper.deleteByPrimaryKey(id);
	}
	/**
	 * 
	 * <p>方法名称：getSiteScreen</p>
	 * <p>方法描述：获取网站甄别的文章</p>
	 * @return
	 * @author Administrator
	 * @since  2017年3月21日
	 * <p> history 2017年3月21日 Administrator  创建   <p>
	 */
	@Override
   public List<SubjectArticle> getSiteScreen(SubjectArticle subjectArticle){
	   return subjectArticleMapper.selectSiteScreen(subjectArticle);
   }
	/**
	 * 
	 * <p>方法名称：getSiteScreen</p>
	 * <p>方法描述：获取网站甄别的文章</p>
	 * @return
	 * @author Administrator
	 * @since  2017年3月21日
	 * <p> history 2017年3月21日 Administrator  创建   <p>
	 */
	@Override
	public SolrPage<SubjectArticle> getSiteScreenPage(SubjectArticle subjectArticle){
		SolrPage<SubjectArticle> solrpage = new SolrPage<SubjectArticle>();
		solrpage.setPageNum(subjectArticle.getSpageNum());
		solrpage.setPageSize(subjectArticle.getSpageSize());
		subjectArticle.setSpageNum((subjectArticle.getSpageNum()-1)*subjectArticle.getSpageSize());
		List<SubjectArticle> list =  subjectArticleMapper.selectSiteScreenPage(subjectArticle);
		
		solrpage.setTotal(63440);
		solrpage.setNavigatePages(10);
		solrpage.setDatas(list);
		return solrpage;
	}
	@Override
	public int updateSiteScreen(SubJectArticleBo subJectArticleBo) {
		String domain = subJectArticleBo.getDomain();
		String datasource = subJectArticleBo.getDataSource();
		String formats = subJectArticleBo.getFormats();
		Site site = siteMapper.selectsiteBydomain(domain);
		if(site!=null){
				site.setType(formats);
				site.setName(datasource);
				site.setScreening(true);
				siteMapper.updateByPrimaryKeySelective(site);
		}else{ 
				site = new Site();	
				site.setId("bayside"+DateFormatUtil.dateFormatStringType(new Date(), "yyyyMMddHHmmss"));
				site.setType(formats);
				if(datasource!=null&&!"".equals(datasource)){
					site.setName(datasource);
				}else{
					site.setName(domain);
				}
				site.setScreening(true);
				site.setUrl("http://"+CommenMethod.getDomainName(subJectArticleBo.getUrl())+"/");
				site.setDeep(1);
				site.setUpdateTime(new Date());
				site.setDomain(domain);
				site.setFrequency(1.00);
				siteMapper.insertSelective(site);
		}
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));	
		int count = 0;
		while(shardedJedis==null){
			shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
			count++;
			if(count>10){
				break;
			}
		}
		shardedJedis.hset("siteName",domain, site.getName());
		shardedJedis.hset("siteType", domain, formats);
		return 0;
	}
	@Override
	public List<Site> selectSiteByMain(String domain) {
		// TODO Auto-generated method stub
		return siteMapper.selectSiteByMain(domain);
	}
	@Override
	public List<Site> selectUpdateSiteByMain(Site record) {
		// TODO Auto-generated method stub
		return siteMapper.selectUpdateSiteByMain(record);
	}
	@Override
	public List<Subject> selectAllSubject() {
		// TODO Auto-generated method stub
		return subjectMapper.selectAllSubject();
	}
	@Override
	public List<User> selectAllUser() {
		// TODO Auto-generated method stub
		return userMapper.selectAllUser();
	}
	@Override
	public List<Site> selectAllSite(){
		// TODO Auto-generated method stub
		 //解析excel
		  String path ="E:/大众网监测网站.xlsx";
		  List<Site> sitelist = new ArrayList<Site>();
		  InputStream is;
		try {
			is = new FileInputStream(path);
			  XSSFWorkbook xssfWorkbook;
			try {
				xssfWorkbook = new XSSFWorkbook(is);
				 
		           // Read the Sheet
		          System.out.println(xssfWorkbook.getNumberOfSheets() +"ffffffff");
		          for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
		               XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
		               if (xssfSheet == null) 
		               {
		                   continue;
		               }
		               // Read the Row
		               for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
		                   XSSFRow xssfRow = xssfSheet.getRow(rowNum);
		                  if (xssfRow != null) {
		                	  Site sit= new Site();
		                      XSSFCell province = xssfRow.getCell(0);
		                       XSSFCell type = xssfRow.getCell(1);
		                       XSSFCell source = xssfRow.getCell(2);
		                       XSSFCell url = xssfRow.getCell(3);
		                       sit.setProvince(getValue(province));
		                       sit.setName(getValue(source));
		                       sit.setUrl(getValue(url));
		                       sitelist.add(sit);
		                  }
		               }
		           }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
	         
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		         
			int num =0;	
		List<Site> list = siteMapper.selectAllSite();
		List<Site> updatelist = new ArrayList<Site>();
		Map<String,Site> map = new HashMap<String,Site>();
		for(int k=0;k<sitelist.size();k++){
			 String s1 = String.valueOf(sitelist.get(k).getUrl());
			 String regStr="[0-9a-zA-Z]+((\\.com)|(\\.cn)|(\\.org)|(\\.net)|(\\.edu)|(\\.com.cn)|(\\.xyz)|(\\.xin)|(\\.club)|(\\.shop)|(\\.site)|(\\.wang)" +
				 		"|(\\.top)|(\\.win)|(\\.online)|(\\.tech)|(\\.store)|(\\.bid)|(\\.cc)|(\\.ren)|(\\.lol)|(\\.pro)|(\\.red)|(\\.kim)|(\\.space)|(\\.link)|(\\.click)|(\\.news)|(\\.news)|(\\.ltd)|(\\.website)" +
				 		"|(\\.biz)|(\\.help)|(\\.mom)|(\\.work)|(\\.date)|(\\.loan)|(\\.mobi)|(\\.live)|(\\.studio)|(\\.info)|(\\.pics)|(\\.photo)|(\\.trade)|(\\.vc)|(\\.party)|(\\.game)|(\\.rocks)|(\\.band)" +
				 		"|(\\.gift)|(\\.wiki)|(\\.design)|(\\.software)|(\\.social)|(\\.lawyer)|(\\.engineer)|(\\.org)|(\\.net.cn)|(\\.org.cn)|(\\.gov.cn)|(\\.name)|(\\.tv)|(\\.me)|(\\.asia)|(\\.co)|(\\.press)|(\\.video)|(\\.market)" +
				 		"|(\\.games)|(\\.science)|(\\.中国)|(\\.公司)|(\\.网络)|(\\.pub)" +
				 		"|(\\.la)|(\\.auction)|(\\.email)|(\\.sex)|(\\.sexy)|(\\.one)|(\\.host)|(\\.rent)|(\\.fans)|(\\.cn.com)|(\\.life)|(\\.cool)|(\\.run)" +
				 		"|(\\.gold)|(\\.rip)|(\\.ceo)|(\\.sale)|(\\.hk)|(\\.io)|(\\.gg)|(\\.tm)|(\\.com.hk)|(\\.gs)|(\\.us))";

	           Pattern p1 = Pattern.compile(regStr);
	           Matcher m1 = p1.matcher(s1);
	           String domain1 = "";
	           if(m1.find()){
	                // System.out.println(m.group());
	                 domain1 = m1.group();
	           }else{
	        	   domain1 = sitelist.get(k).getUrl();
	           } 
	           map.put(domain1, sitelist.get(k));
		}
		for(int i=0;i<list.size();i++){
			 String s1 = String.valueOf(list.get(i).getUrl());
			//定义好获取的域名后缀。如果还有要定义的	请添加 |(\\.域名的后缀) 。 
			 String regStr="[0-9a-zA-Z]+((\\.com)|(\\.cn)|(\\.org)|(\\.net)|(\\.edu)|(\\.com.cn)|(\\.xyz)|(\\.xin)|(\\.club)|(\\.shop)|(\\.site)|(\\.wang)" +
			 		"|(\\.top)|(\\.win)|(\\.online)|(\\.tech)|(\\.store)|(\\.bid)|(\\.cc)|(\\.ren)|(\\.lol)|(\\.pro)|(\\.red)|(\\.kim)|(\\.space)|(\\.link)|(\\.click)|(\\.news)|(\\.news)|(\\.ltd)|(\\.website)" +
			 		"|(\\.biz)|(\\.help)|(\\.mom)|(\\.work)|(\\.date)|(\\.loan)|(\\.mobi)|(\\.live)|(\\.studio)|(\\.info)|(\\.pics)|(\\.photo)|(\\.trade)|(\\.vc)|(\\.party)|(\\.game)|(\\.rocks)|(\\.band)" +
			 		"|(\\.gift)|(\\.wiki)|(\\.design)|(\\.software)|(\\.social)|(\\.lawyer)|(\\.engineer)|(\\.org)|(\\.net.cn)|(\\.org.cn)|(\\.gov.cn)|(\\.name)|(\\.tv)|(\\.me)|(\\.asia)|(\\.co)|(\\.press)|(\\.video)|(\\.market)" +
			 		"|(\\.games)|(\\.science)|(\\.中国)|(\\.公司)|(\\.网络)|(\\.pub)" +
			 		"|(\\.la)|(\\.auction)|(\\.email)|(\\.sex)|(\\.sexy)|(\\.one)|(\\.host)|(\\.rent)|(\\.fans)|(\\.cn.com)|(\\.life)|(\\.cool)|(\\.run)" +
			 		"|(\\.gold)|(\\.rip)|(\\.ceo)|(\\.sale)|(\\.hk)|(\\.io)|(\\.gg)|(\\.tm)|(\\.com.hk)|(\\.gs)|(\\.us))";

			
	           Pattern p1 = Pattern.compile(regStr);
	           Matcher m1 = p1.matcher(s1);
	           String domain = "";
	           if(m1.find()){
	                // System.out.println(m.group());
	                 domain = m1.group();
	           }
			
			if(null!=map.get(domain)){
				Site site = map.get(domain);
				list.get(i).setProvince(map.get(domain).getProvince());
				updatelist.add(list.get(i));
				num++;
			}
			/*for(int j=0;j<sitelist.size();j++){
				  
		           //
		           String s1 = String.valueOf(sitelist.get(j).getUrl());
		           Pattern p1 = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
		           Matcher m1 = p1.matcher(s1);
		           String domain1 = "";
		           if(m1.find()){
		                // System.out.println(m.group());
		                 domain1 = m1.group();
		           }else{
		        	   domain1 = sitelist.get(j).getUrl();
		           } 
				if(list.get(i).getDomain().equals(domain1)){
					list.get(i).setProvince(sitelist.get(j).getProvince());
					num++;
					updatelist.add(list.get(i));
				}
			}*/
		}
	   Site updatesite = new Site();
	   updatesite.setList(updatelist);
		//批量修改数据
		int n = siteMapper.bathSite(updatesite);
		
		//
		
		System.out.println(n);
		return null;
	}
	  private String getValue(XSSFCell xssfRow) {
          if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
             return String.valueOf(xssfRow.getBooleanCellValue());
           } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
               return String.valueOf(xssfRow.getNumericCellValue());
          } else {
             return String.valueOf(xssfRow.getStringCellValue());
          }
       }
   
       @SuppressWarnings("static-access")
      private String getValue(HSSFCell hssfCell) {
           if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
               return String.valueOf(hssfCell.getBooleanCellValue());
           } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
               return String.valueOf(hssfCell.getNumericCellValue());
     } else {
               return String.valueOf(hssfCell.getStringCellValue());
           }
       }
	@Override
	public List<Site> selectAllSiteInfo() {
		// TODO Auto-generated method stub
		return siteMapper.selectAllSite();
	}
}

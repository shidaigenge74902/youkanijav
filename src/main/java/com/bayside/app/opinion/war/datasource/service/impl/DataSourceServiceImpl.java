package com.bayside.app.opinion.war.datasource.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.datasource.dao.DataSourceMapper;
import com.bayside.app.opinion.war.datasource.model.DataSource;
import com.bayside.app.opinion.war.datasource.service.DataSourceService;
import com.bayside.app.opinion.war.wordinfo.model.WordInfo;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.CommenMethod;
import com.bayside.app.util.DBUtil;
import com.bayside.app.util.UuidUtil;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.demo.JxBrowserDemo;

@Service("dataSourceServiceImpl")
public class DataSourceServiceImpl implements DataSourceService {
	private static final Logger log = Logger.getLogger(DataSourceServiceImpl.class);
	 @Autowired
    private DataSourceMapper dataSourceMapper;
	private WebDriver driver;
	private Random rand = new Random();
	//private static String url = "jdbc:mysql://192.168.1.88:3306/baysidedevelop?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false"; // 数据库地址
	private static String url = AppConstant.database.url; // 数据库地址
	private static String username = AppConstant.database.username; // 数据库用户名
	private static String password = AppConstant.database.password; // 数据库密码
	
	//private Browser browser = JxBrowserDemo.initBrowser();
	@Override
	public int insertDateSource(DataSource record) {
		// TODO Auto-generated method stub
		return dataSourceMapper.insertSelective(record);
	}

	@Override
	public DataSource selectDataSourceById(String id) {
		// TODO Auto-generated method stub
		return dataSourceMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DataSource> selectByName(DataSource record) {
		// TODO Auto-generated method stub
		return dataSourceMapper.selectByName(record);
	}
	/**
	 * 自定义线程休眠时间
	 * <p>
	 * 方法名称：ownsleep
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param i
	 * @author sql
	 * @since 2016年6月14日
	 *        <p>
	 *        history 2016年6月14日 sql 创建
	 *        <p>
	 */
	public void ownsleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(),e);
		}
	}
	public String getSearchPeasonUrl(String query) {
		String url = "http://s.weibo.com/user/";
		try {
			query = java.net.URLEncoder.encode(query, "utf-8");
			//query = java.net.URLEncoder.encode(query, "gb2312");
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(),e);
		}
		url += query;
		return url;
	}
	/**
	 * 
	 * <p>方法名称：analysisSearchPerson</p>
	 * <p>方法描述：根据人名抓取微博账号</p>
	 * @param html
	 * @return
	 * @author liuyy
	 * @since  2017年2月17日
	 * <p> history 2017年2月17日 Administrator  创建   <p>
	 */
	public List<DataSource> analysisSearchPerson(String html) {
		
		Document doc = Jsoup.parse(html);
		Elements resultList = doc.select("div[class=list_person clearfix]");
		int length = 0;
		if (resultList == null || length < 1) {
			return null;
		}else{
			length = resultList.size();
		}
		List<DataSource> dslist = new ArrayList<DataSource>();
		for (int i = 0; i < length; i++) {
			DataSource ds = new DataSource();
			Element result = resultList.get(i);
			Elements person_pic = result.select("div[class=person_pic]");
			Elements person_detail = result.select("div[class=person_detail]");
			Elements person_name = person_detail.select("p[class=person_name]");
			Elements person_addr = person_detail.select("p[class=person_addr]");
			String image_url = person_pic.select("img[class=W_face_radius]").attr("src");// 头像地址
			String uid = person_pic.select("img[class=W_face_radius]").attr("uid");// uid
			String screen_name = person_name.select("a[class=W_texta W_fb]").attr("title");// 昵称
			String domain = person_name.select("a[class=W_texta W_fb]").attr("href");// 主页地址
			ds.setId(uid);
			ds.setName(screen_name);
			ds.setAddress(domain);
		    ds.setType("weibo");
			dslist.add(ds);
		}
		return dslist;
	}
	
	/**
	 * 通过名人抓取第一批账号
	 * <p>
	 * 方法名称：analyzeWeiboBySerch
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param service
	 * @param driver
	 * @param query
	 * @author sql
	 * @since 2016年6月15日
	 */
	@Override
	public List<DataSource> analyzeWeiboBySerch(String query,HttpServletRequest request) {
		List<DataSource> totaluser = new ArrayList<DataSource>();
		//抓取百度贴吧账号
		List<DataSource> tiebatotal = this.alistieba(query,request);
		if(tiebatotal!=null){
			totaluser.addAll(tiebatotal);
		}
		
		
		 
		//抓取微信账号
		List<DataSource> weixintotal = this.alisWeixin(query);
		if(weixintotal!=null){
			totaluser.addAll(weixintotal);
		}
		
		List<DataSource> weibototal = this.alisWeiBo(query);
		if(weibototal!=null){
			totaluser.addAll(weibototal);
		}		
    	PreparedStatement psts = null;
		Connection conn = null;
		int count = 0;
		if (totaluser == null || totaluser.size() < 1) {
			return null;
		}
		try {
			conn = DBUtil.getConnection(url, username, password);
			String sql ="insert into bs_datasource "
					+ "(ID,name,address,type,userid) "
					+ " values (?,?,?,?,?)";
			psts = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			String managerid = (String)request.getSession().getAttribute("managerid");
			for (DataSource opinionReport : totaluser) {
				System.out.println(opinionReport);
				System.out.println(opinionReport.getId());
				psts.setString(1, opinionReport.getId());
				if(opinionReport.getName()!=null){
					psts.setString(2, opinionReport.getName());
				}
				if(opinionReport.getAddress()!=null){
					psts.setString(3, opinionReport.getAddress());
				}
				if(opinionReport.getType()!=null){
					psts.setString(4, opinionReport.getType());
				}
				if(managerid!=null){
					psts.setString(5, managerid);
				}
			
				
				psts.addBatch();
			}
			System.out.println(psts.toString());
			int result = psts.executeBatch().length;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(),e);
		}finally{
			DBUtil.close(null, psts, conn);
		}
		return totaluser;
						
		
	}
	public static Browser  initBrower(Browser browser) {
		LoggerProvider.getBrowserLogger().setLevel(Level.WARNING);
		LoggerProvider.getIPCLogger().setLevel(Level.WARNING);
		LoggerProvider.getChromiumProcessLogger().setLevel(Level.WARNING);
		CommenMethod.DeleteFolder(
				"C:\\Users\\Administrator\\AppData\\Local\\Temp\\2\\jxbrowser-chromium-43.0.2357.52.6.2\\data\\Cookies");
		CommenMethod.DeleteFolder(
				"C:\\Users\\Administrator\\AppData\\Local\\Temp\\1\\jxbrowser-chromium-43.0.2357.52.6.2\\data\\Cookies");
		CommenMethod.DeleteFolder(
				"C:\\Users\\Administrator\\AppData\\Local\\Temp\\2\\jxbrowser-chromium-43.0.2357.52.6.2\\data\\Cookies-journal");
		CommenMethod.DeleteFolder(
				"C:\\Users\\Administrator\\AppData\\Local\\Temp\\1\\jxbrowser-chromium-43.0.2357.52.6.2\\data\\Cookies-journal");
		CommenMethod.DeleteFolder(
				"C:\\Users\\Administrator\\AppData\\Local\\Temp\\jxbrowser-chromium-43.0.2357.52.6.2\\data\\Cookies-journal");
		CommenMethod.DeleteFolder(
				"C:\\Users\\Administrator\\AppData\\Local\\Temp\\jxbrowser-chromium-43.0.2357.52.6.2\\data\\Cookies-journal");
		if (browser == null) {// "C:\\Users\\Administrator\\" + filename
			browser = JxBrowserDemo.initBrowser();
		} else {
			browser.dispose();
			browser = JxBrowserDemo.initBrowser();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("睡眠失败！");
				log.error(e.getMessage(),e);
			}
		}
		return browser;
	}
	public List<DataSource> alisWeiBo(String query){
		List<DataSource> totaluser= new ArrayList<DataSource>();
		System.getProperties().setProperty("webdriver.chrome.driver",  
        "c:\\chromedriver.exe"); 
		driver = new ChromeDriver();
		getSinaCookie("15866759297", "qwe1234");
		ownsleep(rand.nextInt(4) * 1500);
		driver.get(getSearchPeasonUrl(query));
		
		ownsleep(rand.nextInt(4) * 1000);
		String html = driver.getPageSource();
		Elements pl_personlist = Jsoup.parse(html).select("div[class=pl_personlist]");
		/*while (pl_personlist == null && pl_personlist.size() < 1)
			ownsleep(1000);*/
		if(null == pl_personlist){
			ownsleep(1000);
		}
		if(null!=pl_personlist){
			if(pl_personlist.size() < 1){
				ownsleep(1000);
			}
		}
		html = driver.getPageSource();
		
		List<DataSource> users = analysisSearchPerson( html);
		if (users != null && users.size() > 0) {
		
			System.out.println("合计保存无重复账号：" + users.size());
		}
		List<String> pageurls = getSearchPersonAllPages(html);
		System.out.println(query + "查询到页数" + pageurls.size());
		for (int i = 0; i < pageurls.size(); i++) {
			System.out.println(i+"抓取页数");
			driver.get(pageurls.get(i));
			ownsleep((rand.nextInt(2) + 1) * 1000);
			pl_personlist = Jsoup.parse(driver.getPageSource()).select("div[class=pl_personlist]");
			if(null == pl_personlist){
				ownsleep(1000);
			}
			if(null!=pl_personlist){
				if(pl_personlist.size() < 1){
					ownsleep(1000);
				}
			}
			html = driver.getPageSource();
			users = analysisSearchPerson(html);
			if (users != null && users.size() > 0) {
			  totaluser.addAll(users);
			}
	
        }
		System.out.println("微博合计：" + totaluser.size());
      return totaluser;

 
	}
	public List<DataSource> alisWeixin(String query){
		//抓取微信账号
				//调用浏览器过程、、C:\Users\Administrator\AppData\Local\Google\Chrome\Application\chrome.exe//C:/Program Files (x86)/Google/Chrome/Application/chrome.exe
			/*	System.getProperties().setProperty("webdriver.chrome.driver",  
				        "c:\\chromedriver.exe"); */
				
				//
				Browser browser = initBrower(null);
		
				//循环搜索微信号
				
				String keyword = query + "";
				System.out.println("开始检索：" + keyword);
				String url = "http://weixin.sogou.com/weixin?type=1&query=" + keyword;
				List<DataSource> totaluser= new ArrayList<DataSource>();
				List<DataSource> list = null;
				browser.loadURL(url);
				String html="";
				try {
					html = browser.getHTML();
				} catch (Exception e) {
					browser.dispose();
					log.error(e.getMessage(),e);
					return null;
				}
				if(html==null||"".equals(html)){
					browser.dispose();
					return null;
				}
				
				
				/*driver = new ChromeDriver();
				
					   // String html="";
						
							driver.get(url);
						
							ownsleep(rand.nextInt(4) * 1000);
							html = driver.getPageSource();*/
							if (html != null && !"".equals(html)) {
								
							Document doc = Jsoup.parse(html);
							if (doc.select("div[class=news-box]").size() < 1) {
								if (doc.select("div[class=no-sosuo]").size() > 0 || doc.select("div[class=illegal]").size() > 0) {
								return null;
								}
							//	ownsleep(rand.nextInt(4) * 1000);
								
							}
							Element total = doc.select("div[class=news-box]").first();
						
							list = getnicks(total);
							if(list!=null){
								totaluser.addAll(list);
							}
							
							Element link = doc.select("div[id=pagebar_container]").first();
							if (link != null) {
								Elements links = link.select("a[href]");
								//处理分页
								for (int i = 0; i < links.size() - 1; i++) {
									Element temp = links.get(i);
									url = "http://weixin.sogou.com/weixin" + temp.attr("href");
									browser.loadURL(url);
									//sleep(3000);
									html =browser.getHTML();
									if (html == null) {
										i--;
										continue;
									}
									doc = Jsoup.parse(html);
									if (doc.select("div[class=news-box]").size() < 1) {
									//	sleep(3000);
										browser.loadURL(url);
										i--;
										continue;
									}else{
										System.out.println(doc.select("div[class=news-box]").size());
									}
									total = doc.select("div[class=news-box]").first();
									if(getnicks(total)!=null){
										totaluser.addAll(getnicks(total));
									}
									
								}
							}
							}
							browser.dispose();
							
		return totaluser;
	}
	@Override
	public List<DataSource> alistieba(String name,HttpServletRequest request) {
		// TODO Auto-generated method stub
		String url = "http://tieba.baidu.com/f/search/fm?ie=UTF-8&qw="+name;
		List<DataSource> list = new ArrayList<DataSource>();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
			
			if (doc.select("div[class=search-forum-list]").size() < 1) {
				if (doc.select("div[class=search_noresult]").size() > 0) {
				return null;
				}
				
			}
			Element total = doc.select("div[class=search-forum-list]").first();
			list = getTieba(total);
			System.out.println(list.size());
			//分页处理  http://tieba.baidu.com/f/search/fm?kw=&qw=%BC%C3%C4%CF&rn=10&un=&sm=&sd=&ed=&pn=2、
			Element link = doc.select("div[class=pager pager-search]").first();
			if (link != null) {
				Elements links = link.select("a");
				for(int i = 0; i < links.size() - 1; i++){
					Element temp = links.get(i);
					String pageurl="http://tieba.baidu.com"+temp.attr("href");
					doc = Jsoup.connect(pageurl).get();
					Element pagetotal = doc.select("div[class=search-forum-list]").first();
					System.out.println(temp.text());
					if(pagetotal == null){
						continue;
					}
					if("下一页>".equals(temp.text())){
						continue;
					}
					if("尾页".equals(temp.text())){
						continue;
					}
					list.addAll(getTieba(pagetotal));
				}
			}
		System.out.println(list.size()+"贴吧个数");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(),e);
		}
		/*PreparedStatement psts = null;
		Connection conn = null;
		int count = 0;
		if (list == null || list.size() < 1) {
			return null;
		}
		try {
			conn = DBUtil.getConnection(url, username, password);
			String sql ="insert into bs_datasource "
					+ "(ID,name,address,type,userid) "
					+ " values (?,?,?,?,?)";
			psts = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			String managerid = (String)request.getSession().getAttribute("managerid");
			for (DataSource opinionReport : list) {
				System.out.println(opinionReport);
				System.out.println(opinionReport.getId());
				psts.setString(1, opinionReport.getId());
				if(opinionReport.getName()!=null){
					psts.setString(2, opinionReport.getName());
				}
				if(opinionReport.getAddress()!=null){
					psts.setString(3, opinionReport.getAddress());
				}
				if(opinionReport.getType()!=null){
					psts.setString(4, opinionReport.getType());
				}
				if(managerid!=null){
					psts.setString(5, managerid);
				}
			
				
				psts.addBatch();
			}
			System.out.println(psts.toString());
			int result = psts.executeBatch().length;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally{
			DBUtil.close(null, psts, conn);
		}*/
		return list;
	}
	public List<DataSource> getTieba(Element total){
		Elements results = total.select("div[class=forum-item clear-float]");
		List<DataSource> list = new ArrayList<DataSource>();
		for(int i=0;i<results.size();i++){
			DataSource tieba = new DataSource();
			Element temp = results.get(i);
			String name = temp.select("div[class=forum-name-wraper]").select("a").text();
			System.out.println(name+"name");
			String url = "http://tieba.baidu.com"+temp.select("div[class=forum-name-wraper]").select("a").attr("href");
			System.out.println(url+"url");
			tieba.setId(UuidUtil.getUUID());
			tieba.setName(name);
			tieba.setAddress(url);
			tieba.setType("tieba");
			list.add(tieba);
		}
		return list;
	}
	public  void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
	public  List<DataSource> getnicks(Element total) {
		List<DataSource> list = new ArrayList<DataSource>();
		if(total!=null){
			Elements results = total.select("ul[class=news-list2]").select("li").select("div[class=gzh-box2]");
			for (int i = 0; i < results.size(); i++) {
				DataSource nick = new DataSource();
				Element temp = results.get(i);
				Element txt_box = temp.select("div[class=txt-box]").first();
				String name = txt_box.select("p[class=tit]").select("a").text();
				System.out.println(name+"name");
			    String url = txt_box.select("p[class=tit]").select("a").attr("href");
			    System.out.println(url+"url");
				nick.setAddress(url);
				nick.setName(name);
				nick.setType("weixin");
				nick.setId(UuidUtil.getUUID());
				list.add(nick);
			}
		}
		
		return list;
	}
	
	/**
	 * <p>
	 * 方法名称：getAllPages
	 * </p>
	 * <p>
	 * 方法描述：获取除了首页之外所有分页的地址
	 * </p>
	 * 
	 * @param html
	 *            查询某个关键词的找人首页
	 * @return
	 * @author sql
	 * @since 2016年6月14日
	 * 
	 */
	public List<String> getSearchPersonAllPages(String html) {
		Document doc = Jsoup.parse(html);
		Elements pageList = doc.select("div[class=layer_menu_list W_scroll]");
		Elements pages = pageList.select("li");
		List<String> pageurls = new ArrayList<String>();
		for (int i = 1; i < pages.size(); i++) {// 默认加载第一页直接跳过
			StringBuffer url = new StringBuffer();
			url.append("http://s.weibo.com/").append(pages.get(i).select("a").attr("href"));
			pageurls.add(url.toString());
		}
		return pageurls;
	}
	public void getSinaCookie(String username, String password) {
		driver.get("http://weibo.com/login.php");
		ownsleep(6000);
		System.out.println("是否存在登录按钮：" + driver.getPageSource().contains("node-type=\"loginBtn\""));
		if (!driver.getPageSource().contains("node-type=\"loginBtn\"")) {
			ownsleep((rand.nextInt(4) + 3) * 1000);
			driver.close();
			driver.quit();
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
			ownsleep(5000);
			getSinaCookie(username, password);
			return;
			/* driver.get("http://weibo.com/login.php"); */
		}
		WebElement loginBtn = driver.findElement(By.cssSelector("a[node-type=loginBtn]"));
		loginBtn.click();
		ownsleep(5000);
		int flag = 0;
		while (!driver.getPageSource().contains("node-type=\"login_tab\"")) {
			loginBtn.click();
			ownsleep(5000);
			flag++;
			if (flag > 6) {
				break;
			}
		}
		System.out.println("是否存在登录弹出：" + driver.getPageSource().contains("node-type=\"login_tab\""));
		if (!driver.getPageSource().contains("node-type=\"login_tab\"")) {
			ownsleep((rand.nextInt(4) + 3) * 1000);
			driver.close();
			driver.quit();
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
			ownsleep(5000);
			getSinaCookie(username, password);
			return;
		}
		WebElement tab = driver.findElement(By.cssSelector("a[node-type=login_tab]"));
		tab.click();
		flag = 0;
		while (!driver.findElement(By.cssSelector("a[node-type=login_tab]")).getAttribute("class").equals("cur")) {
			driver.findElement(By.cssSelector("a[node-type=login_tab]")).click();
			flag++;
			ownsleep(1000);
			if(flag>10){
				break;
			}
		}
		ownsleep(1000);
		WebElement layer_login = driver.findElement(By.cssSelector("div[node-type=login_frame]"));
		WebElement mobile = layer_login.findElement(new By.ByCssSelector("input[name=username]"));
		mobile.sendKeys(username);
		WebElement pass = layer_login.findElement(new By.ByCssSelector("input[name=password]"));
		pass.sendKeys(password);
		WebElement rem = layer_login.findElement(new By.ByCssSelector("input[id=login_form_savestate]"));
		rem.click();
		WebElement submit = layer_login.findElement(new By.ByCssSelector("a[node-type=submitBtn]"));
		submit.click();
		ownsleep(3000);
		flag = 0;
//		System.out.println(driver.getCurrentUrl());
		while (driver.getCurrentUrl().contains("http://weibo.com/login.php#_loginLayer")) {
//			System.out.println(driver.getCurrentUrl());
			ownsleep(2000);
			flag++;
			if (flag > 10) {
				break;
			}
		}
		if (driver.getPageSource().contains("node-type=\"loginBtn\"")) {
			ownsleep((rand.nextInt(4) + 3) * 1000);
			driver.close();
			driver.quit();
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
			ownsleep(5000);
			getSinaCookie(username, password);
			return;
		}
		System.out.println("登录成功");
	}

	
}

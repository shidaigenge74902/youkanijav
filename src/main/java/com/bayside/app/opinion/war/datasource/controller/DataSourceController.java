package com.bayside.app.opinion.war.datasource.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.datasource.model.DataSource;
import com.bayside.app.opinion.war.datasource.service.DataSourceService;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.dependency.nnparser.util.Log;

@RestController
public class DataSourceController {
	private static final Logger log = Logger.getLogger(DataSourceController.class); 
   @Autowired
   private DataSourceService dataSourceServiceImpl;
   private WebDriver driver;
   @RequestMapping(value="/selectDataSourceByPage", method = RequestMethod.GET)
   public Response selectDataSourceByPage(PageInfo page,DataSource record,HttpServletRequest request){
	   String managerid = (String)request.getSession().getAttribute("managerid");
	   record.setUserid(managerid);
	   PageHelper.startPage(page.getPageNum(), page.getPageSize());
	   List<DataSource> list = dataSourceServiceImpl.selectByName(record);
	   for(int i=0;i<list.size();i++){
		   list.get(i).setType(AppConstant.covent.enToCn((list.get(i).getType())));
	   }
	   PageInfo<DataSource> info = new PageInfo<DataSource>(list);
	   if(info!=null){
		   return new Response(ResponseStatus.Success,info,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	   }
	   
   }
   @RequestMapping(value="/saveSource", method = RequestMethod.GET)
   public Response saveSource(String name,HttpServletRequest request){
	   List<DataSource> list = dataSourceServiceImpl.analyzeWeiboBySerch(name,request);
	   if(list!=null){
		   return new Response(ResponseStatus.Success,list,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	   }
	   
	 
   }
   public String getSearchPeasonUrl(String query) {
		String url = "http://s.weibo.com/user/";
		try {
			query = java.net.URLEncoder.encode(query, "utf-8");
			query = java.net.URLEncoder.encode(query, "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		url += query;
		return url;
	}

}

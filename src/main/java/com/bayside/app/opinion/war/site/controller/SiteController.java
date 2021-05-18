package com.bayside.app.opinion.war.site.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.site.bo.SiteBo;
import com.bayside.app.opinion.war.site.model.Site;
import com.bayside.app.opinion.war.site.service.SiteService;
import com.bayside.app.opinion.war.subject.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectArticle;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.ExportExcelUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.SolrPage;
import com.bayside.app.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lowagie.text.Font;

@RestController
public class SiteController {
  @Autowired
  private SiteService siteServiceImpl;
  @RequestMapping(value="/selectAllSubject",method=RequestMethod.GET)
  public Response selectAllSubject(){
	  List<Subject> list = siteServiceImpl.selectAllSubject();
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value="/selectAllUser",method=RequestMethod.GET)
  public Response selectAllUser(){
	  List<User> list = siteServiceImpl.selectAllUser();
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response();
	  }
  }
  @RequestMapping(value="/insertSite",method=RequestMethod.GET)
  public Response insertSite(SiteBo record){
	  record.setId(UuidUtil.getUUID());
	  record.setUpdateTime(new Date());
	  int num = siteServiceImpl.insertSite(record);
	  if(num >0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	  }
  }
  @RequestMapping(value="/selectSiteById",method=RequestMethod.GET)
  public Response selectSiteById(String id){
	 Site site = siteServiceImpl.selectSiteById(id);
	 if(site!=null){
		 return new Response(ResponseStatus.Success,site,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	 }
  }
  @RequestMapping(value="/selectSiteByDomain",method=RequestMethod.GET)
  public Response selectSiteByDomain(String domain){
	  List<Site> list = siteServiceImpl.selectSiteByMain(domain);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value="/selectUpdateSiteByDomain",method=RequestMethod.GET)
  public Response selectUpdateSiteByDomain(Site record){
	  List<Site> list = siteServiceImpl.selectUpdateSiteByMain(record);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value="/updateSiteById",method=RequestMethod.GET)
  public Response updateSiteById(SiteBo record){
	  record.setUpdateTime(new Date());
	  int num = siteServiceImpl.updateSiteById(record);
	  if(num >0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	  }
  }
  @RequestMapping(value="/deleteSiteById",method=RequestMethod.GET)
  public Response deleteSiteById(String id){
	  int num = siteServiceImpl.deleteSiteById(id);
	  if(num >0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
	  }
	  
  }
  @RequestMapping(value="/selectSiteBypage",method=RequestMethod.GET)
  public Response selectSiteBypage(Site record,PageInfo page){
	  PageHelper.startPage(page.getPageNum(), page.getPageSize());
	  List<Site> list = siteServiceImpl.selectSiteByTiaojian(record);
	  for(int i=0;i<list.size();i++){
		  list.get(i).setType(AppConstant.covent.enToCn(list.get(i).getType()));
	  }
	  PageInfo<Site> info = new PageInfo<Site>(list);
	  if(info!=null){
		  return new Response(ResponseStatus.Success,info,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value="/getSiteScreen",method=RequestMethod.GET)
  public Response getSiteScreen(SubjectArticle subjectArticle){
	  //PageHelper.startPage(page.getPageNum(), page.getPageSize());
	  SolrPage<SubjectArticle> list = siteServiceImpl.getSiteScreenPage(subjectArticle);
	 /* PageInfo<SubjectArticle> info = new PageInfo<SubjectArticle>(list);*/
	  /*subjectArticle.setSpageNum((page.getPageNum()-1)*page.getPageSize());
	  subjectArticle.setSpageSize(page.getPageSize());
	  List<SubjectArticle> sList = siteServiceImpl.getSiteScreen(subjectArticle);
	  info.setList(sList);*/
	  if(list!=null){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  
  @RequestMapping(value="/updateSiteScreen",method=RequestMethod.POST)
  public Response updateSiteScreen(SubJectArticleBo subJectArticleBo){
	  int count = siteServiceImpl.updateSiteScreen(subJectArticleBo);
	  
	  if(count>=0){
		  return new Response(ResponseStatus.Success,AppConstant.responseInfo.UPDATESUCCESS,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	  }
  }
  @RequestMapping(value="/selectAllSiteInfo",method=RequestMethod.GET)
  public Response selectAllSiteInfo(String id){
	  List<Site> list = siteServiceImpl.selectAllSite();
	 
	          return new Response(ResponseStatus.Success,list,true);
  }
  @RequestMapping(value="/updateExcel",method=RequestMethod.GET)
  public Response updateExcel(HttpServletResponse response, HttpServletRequest request){
	  String paths ="E:/大众网监测网站.xlsx";
	  List<Site> list = siteServiceImpl.selectAllSiteInfo();
	  InputStream is;
	  List<Site> addlist = new ArrayList<Site>();
	
	  Map<String,Object> maplist = new HashMap<String,Object>();
	  for(int i=0;i<list.size();i++){
	      /* String s = String.valueOf(list.get(i).getUrl());
           Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
           Matcher m = p.matcher(s);
           String domain = "";
           if(m.find()){
                // System.out.println(m.group());
                 domain = m.group();
           }else{
        	   domain = list.get(i).getUrl();
           }
           list.get(i).setUrl(domain);*/
           maplist.put(list.get(i).getDomain(),list.get(i));
	  }
	  
	  
	try {
		is = new FileInputStream(paths);
		  XSSFWorkbook xssfWorkbook;
		try {
			xssfWorkbook = new XSSFWorkbook(is);
			  XSSFCellStyle wrapStyle = (XSSFCellStyle)xssfWorkbook.createCellStyle(); 
		      
		      // 设置单元格边框颜色  
		      wrapStyle.setBottomBorderColor(new XSSFColor(java.awt.Color.RED));  
		      wrapStyle.setTopBorderColor(new XSSFColor(java.awt.Color.RED));  
		      wrapStyle.setLeftBorderColor(new XSSFColor(java.awt.Color.RED));  
		      wrapStyle.setRightBorderColor(new XSSFColor(java.awt.Color.RED)); 
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
	                       sit.setProvince(String.valueOf(province));
	                       sit.setType(String.valueOf(type));
	                       sit.setName(String.valueOf(source));
	                       sit.setUrl(String.valueOf(url));
	                       addlist.add(sit);
	                      
	                       
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
	//生成新的表格
	for(int j=0;j<addlist.size();j++){
		 String s = String.valueOf(addlist.get(j).getUrl());
		 String regStr="[0-9a-zA-Z]+((\\.com)|(\\.cn)|(\\.org)|(\\.net)|(\\.edu)|(\\.com.cn)|(\\.xyz)|(\\.xin)|(\\.club)|(\\.shop)|(\\.site)|(\\.wang)" +
			 		"|(\\.top)|(\\.win)|(\\.online)|(\\.tech)|(\\.store)|(\\.bid)|(\\.cc)|(\\.ren)|(\\.lol)|(\\.pro)|(\\.red)|(\\.kim)|(\\.space)|(\\.link)|(\\.click)|(\\.news)|(\\.news)|(\\.ltd)|(\\.website)" +
			 		"|(\\.biz)|(\\.help)|(\\.mom)|(\\.work)|(\\.date)|(\\.loan)|(\\.mobi)|(\\.live)|(\\.studio)|(\\.info)|(\\.pics)|(\\.photo)|(\\.trade)|(\\.vc)|(\\.party)|(\\.game)|(\\.rocks)|(\\.band)" +
			 		"|(\\.gift)|(\\.wiki)|(\\.design)|(\\.software)|(\\.social)|(\\.lawyer)|(\\.engineer)|(\\.org)|(\\.net.cn)|(\\.org.cn)|(\\.gov.cn)|(\\.name)|(\\.tv)|(\\.me)|(\\.asia)|(\\.co)|(\\.press)|(\\.video)|(\\.market)" +
			 		"|(\\.games)|(\\.science)|(\\.中国)|(\\.公司)|(\\.网络)|(\\.pub)" +
			 		"|(\\.la)|(\\.auction)|(\\.email)|(\\.sex)|(\\.sexy)|(\\.one)|(\\.host)|(\\.rent)|(\\.fans)|(\\.cn.com)|(\\.life)|(\\.cool)|(\\.run)" +
			 		"|(\\.gold)|(\\.rip)|(\\.ceo)|(\\.sale)|(\\.hk)|(\\.io)|(\\.gg)|(\\.tm)|(\\.com.hk)|(\\.gs)|(\\.us))";
          
         Pattern p = Pattern.compile(regStr);
         Matcher m = p.matcher(s);
         String domain = "";
         if(m.find()){
              // System.out.println(m.group());
               domain = m.group();
         }else{
        	 domain = addlist.get(j).getUrl();
         }
         System.out.println(domain); 
         if(null!=maplist.get(domain)){
        	 addlist.get(j).setIsadd("1");
         }else{
        	 addlist.get(j).setIsadd("0");
         }
	}
	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet();
	 workbook.setSheetName(0, "监测网站");  
	sheet.setColumnWidth(0, 10 * 256);
	sheet.setColumnWidth(1, 20 * 256);
	sheet.setColumnWidth(2, 20 * 256);
	sheet.setColumnWidth(3, 20 * 256);
	sheet.setColumnWidth(4, 50 * 256);
	sheet.setColumnWidth(5, 50 * 256);
	OutputStream out = null;
	String loadurl = "";
	    try {
	    	HSSFRow rowm = sheet.createRow(0); // 标题行
			String top[] = { "序号", "地区", "类型", "来源", "网址","是否添加"};
			for (int i = 0; i < top.length; i++) {
				HSSFCell cellm = rowm.createCell(i);
				cellm.setCellValue(top[i]);
				cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
			}
			int rownum = 1;
			HSSFCellStyle style =ExportExcelUtil.getStyle(workbook);
			System.out.println(addlist.size());
			if(addlist.size()>0){
				for(int k=65000;k<addlist.size();k++){
					
					Site site = new Site();
					site = addlist.get(k);
					HSSFRow row = sheet.createRow(rownum);
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(rownum);
					cell.setCellStyle(style);
					cell = row.createCell(1);
					cell.setCellValue(addlist.get(k).getProvince());
					cell.setCellStyle(style);
					cell = row.createCell(2);
					cell.setCellValue(site.getType());
					cell.setCellStyle(style);
					cell = row.createCell(3);
					cell.setCellValue(site.getName());
					cell.setCellStyle(style);
					cell = row.createCell(4);
					cell.setCellValue(site.getUrl());
					cell.setCellStyle(style);
					cell = row.createCell(5);
					cell.setCellValue(site.getIsadd());
					cell.setCellStyle(style);
					rownum++;
				}
			}
			
			String date = DateFormatUtil.getCurrentTime("yyyyMMddHHmmss");
			String fileName = date+"jiance" + ".xls";
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
			System.out.println("path"+path);
			loadurl = "upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd") + "/" + fileName;
			out = new FileOutputStream(new File(path));
			workbook.write(out);
		System.out.println(loadurl);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	    return new Response(ResponseStatus.Success,loadurl, true);	
  }

  
}

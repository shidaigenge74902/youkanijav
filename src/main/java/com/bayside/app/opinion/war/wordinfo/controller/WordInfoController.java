package com.bayside.app.opinion.war.wordinfo.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.wordinfo.model.WordInfo;
import com.bayside.app.opinion.war.wordinfo.service.WordInfoService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.dependency.nnparser.util.Log;

@RestController
public class WordInfoController {
	private static final Logger Log = Logger.getLogger(WordInfoController.class);
   @Autowired
   private WordInfoService wordInfoServiceImpl;
	private static String url = "jdbc:mysql://rm-2ze69ok73j2aizl47o.mysql.rds.aliyuncs.com:3306/bayside?autoReconnect=true"; // 数据库地址
	private static String username = "bayside"; // 数据库用户名
	private static String password = "Bayside801"; // 数据库密码
	/**
	 * 
	 * <p>方法名称：deleteWordInfo</p>
	 * <p>方法描述：根据id删除词
	 * \</p>
	 * @param id
	 * @return
	 * @author liuyy
	 * @since  2017年2月15日
	 * <p> history 2017年2月15日 Administrator  创建   <p>
	 */
   @RequestMapping(value="/deleteWordInfo", method = RequestMethod.GET)
   public Response deleteWordInfo(String id){
	   int num = wordInfoServiceImpl.deleteWordInfo(id);
	   if(num > 0){
		   return new Response(ResponseStatus.Success,num,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETESUCCESS,false);
	   }
   }
   /**
    * \
    * <p>方法名称：insertWordInfo</p>
    * <p>方法描述：插入词库</p>
    * @param record
    * @return
    * @author liuyy
    * @since  2017年2月15日
    * <p> history 2017年2月15日 Administrator  创建   <p>
    */
   @RequestMapping(value="/insertWordInfo", method = RequestMethod.GET)
   public Response insertWordInfo(WordInfo record){
	   record.setId(UuidUtil.getUUID());
	   record.setRegistertime(new Date());
	   int num = wordInfoServiceImpl.insertWordInfo(record);
	   if(num > 0){
		   return new Response(ResponseStatus.Success,num,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	   }
   }
   /**
    * 
    * <p>方法名称：selectWordInfoById</p>
    * <p>方法描述：根据id查询词库</p>
    * @param id
    * @return
    * @author liuyy
    * @since  2017年2月15日
    * <p> history 2017年2月15日 Administrator  创建   <p>
    */
   @RequestMapping(value="/selectWordInfoById", method = RequestMethod.GET)
   public Response selectWordInfoById(String id){
	   WordInfo info = wordInfoServiceImpl.selecWordInfoById(id);
	   if(info !=null){
		   return new Response(ResponseStatus.Success,info,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	   }
   }
   /**
    * 
    * <p>方法名称：updateWordInfo</p>
    * <p>方法描述：修改词库</p>
    * @param record
    * @return
    * @author liuyy
    * @since  2017年2月15日
    * <p> history 2017年2月15日 Administrator  创建   <p>
    */
   @RequestMapping(value="/updateWordInfo", method = RequestMethod.GET)
   public Response updateWordInfo(WordInfo record){
	   int num = wordInfoServiceImpl.updateWordInfo(record);
	   if(num > 0){
		   return new Response(ResponseStatus.Success,num,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	   }
   }
   @RequestMapping(value="/selectWordInfoByPage", method = RequestMethod.GET)
   public Response selectWordInfoByPage(PageInfo page,WordInfo record){
	   PageHelper.startPage(page.getPageNum(), page.getPageSize());
	   List<WordInfo> list = wordInfoServiceImpl.selectWordInfoByName(record);
	   PageInfo<WordInfo> info = new PageInfo<WordInfo>(list);
	   if(info!=null){
		   return new Response(ResponseStatus.Success,info,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	   }
   }
   /**
    * 
    * <p>方法名称：downloadTxt</p>
    * <p>方法描述：导出词库</p>
    * @param response
    * @author liuyy
    * @since  2017年2月15日
    * <p> history 2017年2月15日 Administrator  创建   <p>
    */
   @RequestMapping(value="/downloadTxt", method = RequestMethod.GET)
   public void downloadTxt(HttpServletResponse response){
	    response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "BosonNLP_sentiment_score.txt";
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		StringBuffer obj = new StringBuffer();
		List<WordInfo> list = wordInfoServiceImpl.selectAllWordInfo();
		for (WordInfo sub : list) {
			obj.append(sub.getWordname()).append(" ")
					.append(sub.getValue()).append("\r\n");
		}
		try {
			InputStream inputStream = new ByteArrayInputStream(obj.toString().getBytes());
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.error(e.getMessage(),e);
		} catch (IOException e) {
			e.printStackTrace();
			Log.error(e.getMessage(),e);
		}
		 
   }
   
   

    


}

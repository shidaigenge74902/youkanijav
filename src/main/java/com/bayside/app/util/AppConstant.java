package com.bayside.app.util;

/**
 * <p>Title: AppConstant</P>
 * <p>Description:应用层常量类 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author hadoop
 * @version 1.0
 * @since 2016年9月9日
 */
public class AppConstant {
	/**
	 * <p>Title: serchType</P>
	 * <p>Description:媒体类型</p>
	 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
	 * @author yuangl
	 * @version 1.0
	 * @since 2016年7月26日
	 */
	public interface mediaType{
		/**
		 * 媒体类型： 新闻
		 */
		public static final String NEWS = "news"; 
		/**
		 * 媒体类型： 论坛
		 */
		public static final String LUNTAN = "bbs"; 
		/**
		 * 媒体类型： 博客
		 */
		public static final String BLOG = "blog";  
		/**
		 * 媒体类型： 贴吧
		 */
		public static final String TIEBA = "tieba";  
		/**
		 * 媒体类型： 微博
		 */
		public static final String WEIBO = "weibo"; 
		/**
		 * 媒体类型：平媒
		 */
		public static final String PRINT_MEDIA = "print_media"; 
		/**
		 * 媒体类型： 微信
		 */
		public static final String WEIXIN = "weixin"; 
		/**
		 * 媒体类型： 视频
		 */
		public static final String VIDEO = "video"; 
		/**
		 * 媒体类型： App
		 */
		public static final String APP = "app"; 
		/**
		 * 媒体类型： 评论
		 */
		public static final String COMMENT = "comment"; 
		/**
		 * 媒体类型： 其他
		 */
		public static final String OTHER = "other"; 
	}
	public interface database{
		public static final String url = "jdbc:mysql://rm-2ze69ok73j2aizl47o.mysql.rds.aliyuncs.com:3306/bayside?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false"; // 数据库地址
		public static final String username = "bayside"; // 数据库用户名
		public static final String password = "Bayside801"; // 数据库密码
	}
	/**
	 * <p>Title: emotionType</P>
	 * <p>Description:情感类型 </p>
	 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
	 * @author hadoop
	 * @version 1.0
	 * @since 2016年9月9日
	 */
	public interface emotionType{
		/**
		 * 倾向性：负面 
		 */
		public static final String NEGATIVE = "negative";
		/**
		 * 倾向性：疑似负面 
		 */
		public static final String SUSPECTNEGATIVE = "suspectnegative";
		/**
		 * 倾向性：正面 
		 */
		public static final String POSITIVE = "positive";
		/**
		 * 倾向性：中性
		 */
		public static final String NEUTRAL= "neutral";
		/**
		 * 
		 */
		public static final String ABROAD = "abroad";
		
	}
	/**
	 * 
	 * <p>Title: responseInfo</P>
	 * <p>Description:后台返回前台的信息 </p>
	 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
	 * @author Administrator
	 * @version 1.0
	 * @since 2016年9月19日
	 */
	public interface responseInfo{
		/**
		 * 保存成功
		 */
		public static final String SAVESUCCESS = "保存成功";
		/**
		 * 保存失败
		 */
		public static final String SAVEERRO = "保存失败";
		/**
		 * 删除成功
		 */
		public static final String DELETESUCCESS = "删除成功";
		/**
		 * 删除失败
		 */
		public static final String DELETEERRO = "删除失败";
		/**
		 * 查询无结果
		 */
		public static final String SELECTEERRO="没有查询到数据";

		
		public static final String UPDATESUCCESS = "修改成功";
		
		public static final String UPDATEEERRO="修改失败";

		/**
		 * id   不能为空
		 */
		public static final String IDNOTNULL = "id不能为空";
		
		public static final String JINZHIINSERT="禁止插入数据";
		public static final String PARTREMONEY="此用户已经部分回款";
		public static final String CHECKORGNAME="机构名称不能重复";
		
		

	}
	/**
	 * 
	 * <p>Title: solrUrl</P>
	 * <p>Description: sorlr的请求地址</p>
	 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
	 * @author Administrator
	 * @version 1.0
	 * @since 2016年9月29日
	 */
	public interface solrUrl{
		/**
		 * 微博page
		 */
		public static final String WEIBOPAGE = "http://10.44.11.57:8983/solr/weibopage";
		/**
		 * 微信
		 */
		public static final String WEIXINPAGE = "http://10.44.11.57:8983/solr/weixinpage";
		/**
		 * 元搜索
		 */
		public static final String METASEARCHPAGE = "http://10.44.11.57:8983/solr/metasearchpage";
		/**
		 * 贴吧
		 */
		public static final String TIEBAPAGE = "http://10.44.11.57:8983/solr/tiebapage";
		/**
		 * 通用网站
		 */
		public static final String ARTICLE = "http://10.44.11.57:8983/solr/article";
	}
	public interface covent{
		/**
		 * 
		 * <p>方法名称：covent</p>
		 * <p>方法描述：将formats转化成中文</p>
		 * @param formats
		 * @return
		 * @author Administrator 
		 * @since  2016年10月20日
		 * <p> history 2016年10月20日 Administrator  创建   <p>
		 */
		public static String enToCn(String formats){
			if("web".equals(formats)) return "网站";
			if("news".equals(formats)) return "新闻";
			if("bbs".equals(formats)) return "论坛";
			if("blog".equals(formats)) return "博客";
			if("tieba".equals(formats)) return "贴吧";
			if("weibo".equals(formats)) return "微博";
			if("print_media".equals(formats)) return "平媒";
			if("weixin".equals(formats)) return "微信";
			if("video".equals(formats)) return "视频";
			if("app".equals(formats)) return "app";
			if("comment".equals(formats)) return "评论";
			if("abroad".equals(formats)) return "境外";
			if("trade".equals(formats)) return "交易";
			if("bt".equals(formats)) return "广播";
			if("tv".equals(formats)) return "电视";
			if("other".equals(formats)) return "其他";
			return formats;
		}
	}
	public interface standPower{
		/**
		 * 
		 */
		public static final String PERSONNAME = "人物个数";
		/**
		 * 
		 */
		public static final String  JIANCENAME= "两微一端监测项";
		/**
		 *  
		 */
		public static final String SUBJECTNAME = "专题个数";
		/**
		 * 
		 */
		public static final String CLOUDNAME= "云搜索次数";
		
		public static final String YUJINGNAME="预警信息设置个数";
		
		public static final String SONNAME="子账号个数";
		public static final String MICROOPENNAME="微监测开通";
		public static final String EXPIRDATE="有效期限";
		public static final String SUBJECTREPORTNAME="专报生成次数";
		public static final String DAYREPORTNAME="日报开通";
		public static final String WEEKREPORTNAME="周报开通";
		public static final String MONTHREPORTNAME="月报开通";
		public static final String MODALNAME="模板可选择数量";
		public static final String WORDNAME="关键词个数";
		public static final String SETREPORT="人工报告开通";
		public static final String SETTRADE="交易开通";
		public static final String ISUPDATE="操作开通";
		public static final String EMPHASISNAME="重点关注个数";
		public static final String CHECKDAYS="查询天数";
		public static final String LOGINNUM="登录账号个数";
		public static final String ISMEDIA="媒体开通";
		public static final String EMOTIONNUM="负面精确度";
		
		public static final String[] POWERLIST= {"媒体开通","人物个数","两微一端监测项","专题个数","云搜索次数","预警信息设置个数","子账号个数","微监测开通","有效期限","专报生成次数","日报开通","周报开通","月报开通","模板可选择数量","关键词个数","人工报告开通","交易开通","操作开通","重点关注个数"}; 
		
		
	}
	public interface timetype{
		//本月
		public static final String MONTH="month";
		public static final String WEEK="week";
		//本季度
		public static final String JIDU="jidu";
		//本年
		public static final String YEAR="year";
		//自定义时间
		public static final String ZIDINGYI="zidingyi";
	}
	public interface managertype{
		//超级管理员
		public static final Integer TWO=2;
		//代理商
		public static final Integer ZERO=0;
		//普通管理员
		public static final Integer ONE=1;
		//财务管理员
		public static final Integer THREE=3;
		//总监
		public static final Integer FOUR=4;
		//客服
		public static final Integer FIVE=5;
		
		//维护管理员
		public static final Integer SEVEN=7;
	}
	public interface remoneytag{
		//部分回款
		public static final Integer ZERO=0;
		//全部回款
		public static final Integer ONE=1;
	}
}

package com.bayside.app.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class ComMethodUtil {
	private static final Logger log = Logger.getLogger(ComMethodUtil.class);
	/**
	 * 
	 * <p>
	 * 方法名称：GetDomainName
	 * </p>
	 * <p>
	 * 方法描述：正则表达式获取域名
	 * </p>
	 * 
	 * @param url
	 * @return
	 * @author sql
	 * @since 2016年9月5日
	 *        <p>
	 *        history 2016年9月5日 sql 创建
	 *        <p>
	 */
	public static String getDomainName(String url) {
		if (url == null || "".equals(url)) {
			return "";
		}
		Pattern p = Pattern.compile("([\\w-]+\\.)+[\\w-]+(?<=/?)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		try{
			return matcher.group();
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return "";
		}
	}
	public static String getDomain(String url,int level){
		String str="j|c|com|gr|dk|blue|gs|host|ga|fm|sydney|ch|kr|vc|tm|cx|gdn|it|travel|sh|sc|ma|tw|nl|hu|nu|accountant|webcam|fr|mobi|gg|gd|cd|party|my|la|sg|de|cm|in|so|us|eu|at|ws|jp|pw|gov|in|hk|ca|edu|int|mil|info|tv|pro|museum|coop|aero|cn|org|name|tv|me|asia|co|net|中国|公司|网络|pub|group|我爱你|集团|kim|cn|xin|xyz|shop|club|top|wang|win|site|vip|store|net|bid|cc|ltd|ren|lol|mom|online|tech|biz|red|website|space|link|news|date|com.cn|net.cn|org.cn|go.kr|or.kr|edu.cn|ac.kr|gov.cn";
		str=new StringBuffer(str).reverse().toString();
		String reg = "(/|^)"+"("+str+")+\\.(\\.?\\w*){"+level+"}";
		Pattern pattern = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
		url=new StringBuffer(url).reverse().toString();
		Matcher matcher = pattern.matcher(url);
		
		if(matcher.find()){
				return new StringBuffer(matcher.group(0)).reverse().toString().replace("/", "");
		}else{
			return new StringBuffer(url).reverse().toString();
		}
	}
	/**
	 * 
	 * <p>方法名称：filterEmoji</p>
	 * <p>方法描述：过滤特殊字符的数据</p>
	 * @param source
	 * @return
	 * @author Administrator
	 * @since  2016年9月10日
	 * <p> history 2016年9月10日 Administrator  创建   <p>
	 */
	public static String filterEmoji(String source) {
		if (StringUtils.isNotBlank(source)) {
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
		} else {
			return source;
		}
	}
	public static int[] getWordIndexs(String content,String word){
		int[] indexs= new int[content.split(word).length-1];
		if(content.length()==content.lastIndexOf(word)+word.length()){
			indexs= new int[content.split(word).length];
		}
		 int index = content.indexOf(word);
		 int length = 0;
	       while(index>-1){
	    	   indexs[length] = index;
	    	   length++;
	    	   index = content.indexOf(word, index+1);
	       }
	       return indexs;
	}
	/**
	 * 
	 * <p>方法名称：getEmotion</p>
	 * <p>方法描述：获取情感信息</p>
	 * @param title
	 * @param content
	 * @return
	 * @author Administrator
	 * @since  2017年8月30日
	 * <p> history 2017年8月30日 Administrator  创建   <p>
	 */
	public static Map<String,Object> getEmotion(String title, String content) {
		Map<String,Object> result = new HashMap<String,Object>(); 
		String emotion = "0";
		Double negativeProbability = 0.0;
		try {
			String param = "verificatcode=bayside&title=" + title + "&content=" + URLEncoder.encode(content, "utf-8");
			String s = HttpRequest.sendPost("http://47.93.122.237:8080/emotionBycontent", param);
			//String s = HttpRequest.sendPost("http://10.30.48.140:8080/emotionBycontent", param);
			ObjectMapper mapper = new ObjectMapper();
			Response response = mapper.readValue(s, Response.class);
			if (response.isSucceed()) {
				Map<String, Object> map = (Map<String, Object>) response.getObject();
				//System.out.println(map);
				//System.out.println(map.get("name") + "\t" + map.get("value"));
				emotion =  map.get("value")==null?"0":map.get("value")+"";
				if ("-1".equals(emotion)) {
					emotion = "-2";
				}
				negativeProbability = (Double) map.get("negativeProbability");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		result.put("emotion", emotion);
		result.put("negativeProbability", negativeProbability);
		return result;
	}
	/**
	 * 
	 * <p>方法名称：getMinDiff</p>
	 * <p>方法描述：获取两个数组的最小差值</p>
	 * @param array1
	 * @param array2
	 * @return
	 * @author Administrator
	 * @since  2016年12月15日
	 * <p> history 2016年12月15日 Administrator  创建   <p>
	 */
	public static int getMinDiff(int[] array1,int[] array2){
		int min =Integer.MAX_VALUE;
    	if(array1.length>0&&array2.length==0){
    		for (int i = 0; i < array1.length; i++) {
    			if(array1[i]<min){
    				min = array1[i];
    				if(min==0){
    					break;
    				}
    			}
			}
    	}else if(array1.length==0&&array2.length>0){
    		for (int i = 0; i < array2.length; i++) {
    			if(array2[i]<min){
    				min = array2[i];
    				if(min==0){
    					break;
    				}
    			}
			}
    	}else if(array1.length>0&&array2.length>0){
    	ok:
    	for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array2.length; j++) {
				int tem = Math.abs(array1[i]-array2[j]);
				if(tem<min){
					min = tem;
					if(min==0){
						break ok;
					}
				}
			}
		}
    	}else{
    		min=0;
    	}
    	return min;
	}
	/**
	 * 
	 * <p>方法名称：getDistance</p>
	 * <p>方法描述：相似度</p>
	 * @param str1
	 * @param str2
	 * @return
	 * @author Administrator
	 * @since  2016年11月29日
	 * <p> history 2016年11月29日 Administrator  创建   <p>
	 */
	public static int getDistance(String str1, String str2) {    
        int distance;    
        if (str1.length() != str2.length()) {    
            distance = -1;    
        } else {    
            distance = 0;    
            for (int i = 0; i < str1.length(); i++) {    
                if (str1.charAt(i) != str2.charAt(i)) {    
                    distance++;    
                }    
            }    
        }    
        return distance;    
    }  
}

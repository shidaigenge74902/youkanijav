package com.bayside.app.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <p>
 * Title: Html2Text
 * </P>
 * <p>
 * Description: 获取网页的标题、发布时间、内容
 * </p>
 * <p>
 * Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016
 * </p>
 * 
 * @author hadoop
 * @version 1.0
 * @since 2016年11月3日
 */
public class Html2Text {
	private static int dept = 4;
	private static int limitCount = 80;
	private static int headEmptyLines = 2;
	private static int endLimitCharCount = 40;
	private static boolean appendMode = false;

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://www.bestb2b.com/prod_57296661.htm").get();
		String body = doc.select("body").text();
		body = getContentText(doc);
		System.out.println(getTitle(doc.toString()));
		System.out.println(getPublishDate(doc.select("body").toString()));
		System.out.println(getContentCN(body));
	}

	public static String getContentText(Document doc) {
		String body = doc.select("body").text();
		List<String> list = getListTitle(doc);
		if (list != null && list.size() > 4) {
			if (body.indexOf(list.get(4)) / (double) body.length() < 0.15) {
				System.out.println("列表页面");
				return null;
			}
		}
		for (String str : list) {
			body = body.replace(str, " 止 ");
		}
		return body;
	}

	public static List<String> getListTitle(Document doc) {
		Elements ares = doc.select("a");
		List<String> list = new ArrayList<String>();
		int i = 0;
		String url = CommenMethod.getDomainName(doc.baseUri());
		for (Element a : ares) {
			String tempStr = a.text();
			if (a.baseUri().contains(url) && a.text().length() > 10) {
				list.add(tempStr);
			}
		}
		return list;
	}

	public static String getPublishDate(String body) {
		body = body.replaceAll("&nbsp;", " ");
		body = body.replaceAll(" +", " ");
		int startpoint = body.indexOf("发布时间：");
		if (startpoint > 0) {
			body = body.substring(startpoint, startpoint + 50);
		}
		String datestr = "";
		String dateregx = null;
		String[] dateregxs = { "(\\d{4}-\\d{1,2}-\\d{1,2})(\\s?\\d{2}:\\d{2}:\\d{2})",
				"(\\d{4}-\\d{1,2}-\\d{1,2})(\\s?\\d{2}:\\d{2})", "(\\d{4}-\\d{1,2}-\\d{1,2}-)(\\s?\\d{2}点\\d{2})",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}:\\d{2}:\\d{2})",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}点\\d{2}分\\d{2})",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}:\\d{2})", "(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}点\\d{2})",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)((\\s?\\d{2}:\\d{2}:\\d{2})?|(\\s?\\d{2}点\\d{2}分\\d{2})?|(\\s?\\d{2}:\\d{2}))",
				"(\\d{4}年\\d{1,2}月\\d{1,2}日)",
				"((\\d{4}|\\d{2})(\\-|\\/)\\d{1,2}\\3\\d{1,2})(\\s?\\d{2}:\\d{2})?|(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}:\\d{2})",
				"((\\d{4}|\\d{2})(\\-|\\/)\\d{1,2}\\3\\d{1,2})((\\s?\\d{2}:\\d{2}:\\d{2})?|(\\s?\\d{2}点\\d{2}分\\d{2})?|(\\s?\\d{2}:\\d{2})?|(\\s?\\d{2}点\\d{2}))",
				"(\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}:\\d{2})", "(\\d{1,2}-\\d{1,2})(\\s?\\d{2}:\\d{2})" };
		String tempdatestr = null;
		for (int i = 0; i < dateregxs.length; i++) {
			dateregx = dateregxs[i];
			String str = machPublishDate(body, dateregx);
			tempdatestr = DateFormatUtil.getPubdate(str);
			if (tempdatestr != null && tempdatestr.compareTo(datestr) > 0) {
				datestr = tempdatestr;
			}
		}
		if (datestr.equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String hour = sdf.format(new Date());
		return DateFormatUtil.getPubdate(datestr).replace("00:00:00", hour);
	}

	public static String machPublishDate(String body, String dateregx) {
		Pattern p = Pattern.compile(dateregx);
		Matcher matcher = p.matcher(body);
		String datestr = null;
		String tempdatestr = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curdatestr = sdf.format(new Date());
		if (matcher.find()) {
			datestr = matcher.group(0);
			tempdatestr = DateFormatUtil.getPubdate(datestr);
			while (curdatestr.compareTo(tempdatestr) < 0 || tempdatestr.compareTo("1999-01-01") < 0) {
				body = body.replace(datestr, "");
				matcher = p.matcher(body);
				if (matcher.find()) {
					datestr = matcher.group(0);
				} else {
					datestr = null;
					break;
				}
				tempdatestr = DateFormatUtil.getPubdate(datestr);
			}
		}
		return datestr;
	}

	public static String getTitle(String html) {
		String titleFilter = "<title>[\\s\\S]*?</title>";
		String h1Filter = "<h1.*?>.*?</h1>";
		String clearFilter = "<.*?>";

		String title = "";
		Pattern p = Pattern.compile(titleFilter);
		Matcher matcher = p.matcher(html);
		if (matcher.find()) {
			title = matcher.group(0).replaceAll(clearFilter, "");
		}

		// 正文的标题一般在h1中，比title中的标题更干净 存在缺陷 有可能h1中和标题无关 后续添加辨别真伪的判断 都是真选择h1 否则选择真的
		// p = Pattern.compile(h1Filter);
		// matcher = p.matcher(html);
		// if (matcher.find()) {
		// title = matcher.group(0).replaceAll(clearFilter, "");
		// }
		return title;
	}

	/**
	 * <p>
	 * 方法名称：GetContentCN
	 * </p>
	 * <p>
	 * 方法描述：只能采集中文网页 如果需要采集英文 cnlen 改成len判断
	 * </p>
	 * 
	 * @param body
	 * @return
	 * @author hadoop
	 * @since 2016年11月3日
	 *        <p>
	 *        history 2016年11月3日 hadoop 创建
	 *        <p>
	 */
	public static String getContentCN(String body) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		String[] lines = null;
		if (body == null || "".equals(body)) {
			return null;
		}
		lines = body.split(" ");
		List<String> lineList = new ArrayList<String>();
		Collections.addAll(lineList, lines);
		int delenum = 0;
		for (int i = 0; i < lines.length - 1; i++) {
			Matcher matcher = p.matcher(lines[i]);
			boolean flg = false;
			if (!matcher.find()) {
				lineList.remove(i - delenum);
				delenum++;
			} else {
				lineList.set(i - delenum, lineList.get(i - delenum).replace("　", ""));
			}
		}
		lines = (String[]) lineList.toArray(new String[lineList.size()]);
		StringBuilder sb = new StringBuilder();
		int preTextLen = 0; // 记录上一次统计的字符数量
		int startPos = -1; // 记录文章正文的起始位置
		String sbstr = null;
		for (int i = 0; i < lines.length - dept; i++) {
			int len = 0;
			int cnlen = 0;
			for (int j = 0; j < dept; j++) {
				len += lines[i + j].length();
				Matcher m = p.matcher(lines[i + j]);
				while (m.find()) {
					for (int k = 0; k <= m.groupCount(); k++) {
						cnlen = cnlen + 1;
					}
				}
			}
			if (startPos == -1) { // 还没有找到文章起始位置，需要判断起始位置
				if (preTextLen > limitCount && cnlen > 0) { // 如果上次查找的文本数量超过了限定字数，且当前行数字符数不为0，则认为是开始位置
					int emptyCount = 0; // 查找文章起始位置, 如果向上查找，发现2行连续的空行则认为是头部
					for (int j = i; j > 0; j++) {
						int templine = 0;
						Matcher m = p.matcher(lines[j]);
						while (m.find()) {
							for (int k = 0; k <= m.groupCount(); k++) {
								templine = templine + 1;
							}
						}
						if (templine < 10) {
							continue;
						} else {
							startPos = j;
							i = startPos;
							break;
						}
					}
					// 如果没有定位到文章头，则以当前查找位置作为文章头
					if (startPos == -1) {
						startPos = i;
					}
					// 填充发现的文章起始部分
					for (int j = startPos; j <= i; j++) {
						sb.append(lines[j]);
					}
				}
			} else {
				if (cnlen <= endLimitCharCount && preTextLen < endLimitCharCount) {
					if (!appendMode) {
						sbstr = sb.toString().replace(lines[i - 1], "");
						break;
					}
					startPos = -1;
				}
				sb.append(lines[i]);
			}
			if (sb.length() > 200 && (lines[i].contains("责任编辑：") || lines[i].contains("编辑："))) {
				sbstr = sb.toString();
				break;
			}
			preTextLen = cnlen;
		}
		// System.out.println(sbstr);
		String content = sbstr == null ? sb.toString() : sbstr;
		if (content.contains("，") || content.contains("。") || content.contains("；") || content.contains("！")) {
			return content;
		} else {
			return null;
		}
	}

}

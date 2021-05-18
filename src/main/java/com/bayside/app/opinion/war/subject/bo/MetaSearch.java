package com.bayside.app.opinion.war.subject.bo;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class MetaSearch{
	/**
	 * 文章id
	 */
	@Field
	private String id = "";
	
	private String zhongjianid ="";//中间表id
	
	private String subjectid ="";//专题id
	
	private String usersid ="";//用户id
	@Field
	private String author = "";
	/**
	 * 标题
	 */
	@Field
	private String title = "";
	/**
	 * 中文分词
	 */
	@Field
	private String title_cn = "";
	/**
	 * 图片地址
	 */
	@Field
	private String imgUrl = "";
	/**
	 * 原文地址
	 */
	@Field
	private String url = "";
	/**
	 * 域名
	 */
	@Field
	private String domain = "";
	/**
	 * 简介
	 */
	@Field
	private String note = "";
	@Field
	private String note_cn = "";
	/**
	 * 内容
	 */
	@Field
	private String content ="";
	@Field
	private String content_cn = "";
	/**
	 * 搜索引擎
	 */
	@Field
	private String serchEnType = "";
	/**
	 * 搜索类型
	 */
	@Field
	private String serchType = "";
	/**
	 * 采集时间
	 */
	@Field
	private String updateTime = "";
	/**
	 * 情感
	 */
	@Field
	private String emotion  = "";
	/**
	 * 来源
	 */
	@Field
	private String dataSource = "";
	/**
	 * 发布时间
	 */
	@Field
	private String pubdate = "";
	/**
	 * 媒体类型
	 */
	@Field
	private String formats = "";
	/**
	 * 贴吧名称
	 */
	@Field
	private String sourceName = "";
	/**
	 * 贴吧地址
	 */
	@Field
	private String sourceLink = "";
	/**
	 * 转发人数
	 */
	@Field
	private String repeatcount = "0";
	/**
	 * 评论次数
	 */
	@Field
	private String commtcount = "0";
	/**
	 * 点赞人数
	 */
	@Field
	private String agreecount = "0";
	/**
	 * 阅读人数
	 */
	@Field
	private String readcount = "-1";
	@Field
	private String negativeWord = "";
	@Field
	private String positiveWord = "";
	@Field
	private String simhashcode = "";
	@Field
	private String contentType = "";
	
	private String keywordRule ="";
	@Field
	private String viewPoint = "";//观点
	private Boolean warning = false;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null?"":id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null?"":title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl == null?"":imgUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null?"":url;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain == null?"":domain;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note == null?"":note;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null?"":content;
	}

	public String getSerchEnType() {
		return serchEnType;
	}

	public void setSerchEnType(String serchEnType) {
		this.serchEnType = serchEnType == null?"":serchEnType;
	}

	public String getSerchType() {
		return serchType;
	}

	public void setSerchType(String serchType) {
		this.serchType = serchType == null?"":serchType;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null?"":updateTime;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion == null?"":emotion;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource == null?"":dataSource;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate == null?"":pubdate;
	}

	public String getTitle_cn() {
		return title_cn;
	}

	public void setTitle_cn(String title_cn) {
		this.title_cn = title_cn == null?"":title_cn;
	}

	public String getNote_cn() {
		return note_cn;
	}

	public void setNote_cn(String note_cn) {
		this.note_cn = note_cn == null?"":note_cn;
	}

	public String getContent_cn() {
		return content_cn;
	}

	public void setContent_cn(String content_cn) {
		this.content_cn = content_cn == null?"":content_cn;
	}

	public String getFormats() {
		return formats;
	}

	public void setFormats(String formats) {
		this.formats = formats == null? "":formats;
	}

	public String getZhongjianid() {
		return zhongjianid;
	}

	public void setZhongjianid(String zhongjianid) {
		this.zhongjianid = zhongjianid;
	}

	public String getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}

	public String getUsersid() {
		return usersid;
	}

	public void setUsersid(String usersid) {
		this.usersid = usersid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author == null?"":author;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName == null?"0":sourceName;
	}

	public String getSourceLink() {
		return sourceLink;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink == null?"0":sourceLink;
	}

	public String getRepeatcount() {
		return repeatcount;
	}

	public void setRepeatcount(String repeatcount) {
		this.repeatcount = repeatcount == null?"0":repeatcount;
	}

	public String getCommtcount() {
		return commtcount;
	}

	public void setCommtcount(String commtcount) {
		this.commtcount = commtcount == null?"0":commtcount;
	}

	public String getAgreecount() {
		return agreecount;
	}

	public void setAgreecount(String agreecount) {
		this.agreecount = agreecount == null?"0":agreecount;
	}

	public String getReadcount() {
		return readcount;
	}

	public void setReadcount(String readcount) {
		this.readcount = readcount == null?"0":readcount;
	}

	public String getNegativeWord() {
		return negativeWord;
	}

	public void setNegativeWord(String negativeWord) {
		this.negativeWord = negativeWord == null? "":negativeWord;
	}

	public String getPositiveWord() {
		return positiveWord;
	}

	public void setPositiveWord(String positiveWord) {
		this.positiveWord = positiveWord == null? "":positiveWord;
	}

	public String getSimhashcode() {
		return simhashcode;
	}

	public void setSimhashcode(String simhashcode) {
		this.simhashcode = simhashcode == null ? "" : simhashcode;
	}

	public Boolean getWarning() {
		return warning;
	}

	public void setWarning(Boolean warning) {
		this.warning = warning ==null? false:warning;
	}

	public String getKeywordRule() {
		return keywordRule;
	}

	public void setKeywordRule(String keywordRule) {
		this.keywordRule = keywordRule==null? "":keywordRule;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType == null? "":contentType;
	}

	public String getViewPoint() {
		return viewPoint;
	}

	public void setViewPoint(String viewPoint) {
		this.viewPoint = viewPoint == null?"":viewPoint;
	}
}

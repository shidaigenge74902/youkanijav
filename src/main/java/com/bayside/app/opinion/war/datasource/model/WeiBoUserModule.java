package com.bayside.app.opinion.war.datasource.model;

/**
 * <p>Title: WeiBoUserModule</P>
 * <p>Description: 微博用户表</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author sql
 * @version 1.0
 * @since 2016年5月23日
 */
public class WeiBoUserModule {
	/**
	 * 用户主键
	 */
	private String id;
	/**
	 * 用户描述
	 */
	private String description;
	/**
	 * 用户图片地址
	 */
	private String image_url;
	/**
	 * 用户主页地址
	 */
	private String domain;
	/**
	 * 注册时间
	 */
	private String register_time;
	
	/**
	 * 工作经历
	 */
	private String work;
	/**
	 * 最后活跃时间
	 */
	private String lasttime;
	/**
	 * 生日
	 */
	private String birth;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 所在城市
	 */
	private String city;
	/**
	 * 用戶簡介
	 */
	private String reason;
	/**
	 * 博客地址
	 */
	private String blogurl;
	/**
	 * 标签
	 */
	private String tags;
	/**
	 * 昵称
	 */
	private String screen_name;
	/**
	 * 认证类别
	 */
	private String verified;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 教育
	 */
	private String educate;
	/**
	 * 好友数
	 */
	private String friends_count;
	/**
	 * 关注数
	 */
	private String attach_count;
	/**
	 * 关注列表网页地址
	 */
	private String attach_url;
	/**
	 * 微博数
	 */
	private String statuses_count;
	/**
	 * 微博列表地址
	 */
	private String statuses_url;
	/**
	 * 粉丝数
	 */
	private String followers_count;
	/**
	 * 粉丝列表地址
	 */
	private String followers_url;
	/**
	 * 用户等级
	 */
	private String level;
	/**
	 * 职业信息
	 */
	private String career_information;
	/**
	 * 教育信息
	 */
	private String education_information;
	
	/**
	 * 性取向
	 */
	private String sexuality;
	/**
	 * 感情状况
	 */
	private String emotional_state;
	
	/**
	 * 血型
	 */
	private String  blood_type;
	
	/**
	 * 个性域名
	 */
	private String ownbolg_address;
	/**
	 * 真实姓名
	 */
	private String real_name;
	/**
	 * 邮箱地址
	 */
	private String email_address;
	/**
	 * QQ号
	 */
	private String qqnumber;
	/**
	 * MSN
	 */
	private String msn_address;
	/**
	 * 是否是淘宝商户
	 */
	private String istaobao;
	/**
	 * 用户uid
	 */
	private String uid;
	/**
	 * 微博会员
	 */
	private String vip;
	/**
	 * 是否随手拍
	 */
	private String pai;
	/**
	 * 二维码地址
	 */
	private String qrcode_url;
	/**
	 * 权重
	 */
	private String score;
	/**
	 * 更新时间
	 */
	private String updateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getRegister_time() {
		return register_time;
	}
	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getBlogurl() {
		return blogurl;
	}
	public void setBlogurl(String blogurl) {
		this.blogurl = blogurl;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public String getVerified() {
		return verified;
	}
	public void setVerified(String verified) {
		this.verified = verified;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEducate() {
		return educate;
	}
	public void setEducate(String educate) {
		this.educate = educate;
	}
	public String getFriends_count() {
		return friends_count;
	}
	public void setFriends_count(String friends_count) {
		this.friends_count = friends_count;
	}
	public String getStatuses_count() {
		return statuses_count;
	}
	public void setStatuses_count(String statuses_count) {
		this.statuses_count = statuses_count;
	}
	public String getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(String followers_count) {
		this.followers_count = followers_count;
	}
	public String getAttach_count() {
		return attach_count;
	}
	public void setAttach_count(String attach_count) {
		this.attach_count = attach_count;
	}
	public String getCareer_information() {
		return career_information;
	}
	public void setCareer_information(String career_information) {
		this.career_information = career_information;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getEducation_information() {
		return education_information;
	}
	public void setEducation_information(String education_information) {
		this.education_information = education_information;
	}
	public String getSexuality() {
		return sexuality;
	}
	public void setSexuality(String sexuality) {
		this.sexuality = sexuality;
	}
	public String getEmotional_state() {
		return emotional_state;
	}
	public void setEmotional_state(String emotional_state) {
		this.emotional_state = emotional_state;
	}
	public String getBlood_type() {
		return blood_type;
	}
	public void setBlood_type(String blood_type) {
		this.blood_type = blood_type;
	}
	public String getOwnbolg_address() {
		return ownbolg_address;
	}
	public void setOwnbolg_address(String ownbolg_address) {
		this.ownbolg_address = ownbolg_address;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getEmail_address() {
		return email_address;
	}
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	public String getQqnumber() {
		return qqnumber;
	}
	public void setQqnumber(String qqnumber) {
		this.qqnumber = qqnumber;
	}
	public String getMsn_address() {
		return msn_address;
	}
	public void setMsn_address(String msn_address) {
		this.msn_address = msn_address;
	}
	public String getIstaobao() {
		return istaobao;
	}
	public void setIstaobao(String istaobao) {
		this.istaobao = istaobao;
	}
	public String getAttach_url() {
		return attach_url;
	}
	public void setAttach_url(String attach_url) {
		this.attach_url = attach_url;
	}
	public String getFollowers_url() {
		return followers_url;
	}
	public void setFollowers_url(String followers_url) {
		this.followers_url = followers_url;
	}
	public String getStatuses_url() {
		return statuses_url;
	}
	public void setStatuses_url(String statuses_url) {
		this.statuses_url = statuses_url;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public String getPai() {
		return pai;
	}
	public void setPai(String pai) {
		this.pai = pai;
	}
	public String getQrcode_url() {
		return qrcode_url;
	}
	public void setQrcode_url(String qrcode_url) {
		this.qrcode_url = qrcode_url;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}

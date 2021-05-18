package com.bayside.app.opinion.war.myuser.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
	
    private String id;

    private String parentid;

    private String agent;

    private String name;

    private String code;

    private String password;

    private String telephone;

    private String mobilephone;

    private String email;

    private String ownindustry;

    private String orgname;

    private String usertype;

    private String accounttype;

    private BigDecimal prices;

    private String realname;

    private String idcard;

    private String image;

    private Date birthday;

    private String gender;

    private String address;

    private String familyMember;

    private String note;

    private String fileaddress;

    private String companyfullname;

    private String companyshortname;

    private String province;

    private String city;

    private String companycode;

    private String operateruser;

    private Integer status;

    private Date endtime;

    private Integer maxlanmunumber;

    private Integer maxkeywordnumber;

    private String beizhu;

    private Integer authority;
    
    private Integer subjecttimes;
    
    private Integer microopen;
    
    private Integer monitortimes;
    
    private Integer warntimes;
    
    private Integer persontimes;
    
    private Date expirydate;
    
    private Integer cloudtimes;
    
    private Integer childtimes;
    
    private Integer usertimes;
    
    private Date registertime;
    
    private String managerid;
    
    private Date startdate;
    
	private Integer tag;

	private String userTypeId;

	private Integer expirdate;

	private Integer subjectReport;

	private Integer dayReport;

	private Integer weekReport;

	private Integer monthReport;

	private Integer modalNum;
	private Integer keywordNum;



	private String startTime;

	private String endTime;

	private Integer shiyongAcount;
	private Integer biaozhunAcount;// 签约用户
	private Integer innerAcount;// 内部用户
	private Integer jinzhiAcount;// 禁用用户
	private Integer totalid;
	private Integer alltotalid;
	private Integer expirAcount;
	private String managername;
	private String managertag;
	private String time;
	private String expirtag;

	private Integer allexpirDay;
	private Integer resexpirDay;
	private Integer ressubjectNum;
	private Integer resPersonNum;
	private Integer reskeywordNum;
	private Integer resmiroNum;
	private Integer resyujingNum;
	private Integer resreportNum;
	private Integer rescloudNum;

	private Date expirtime;
	
	private String typecode;
	
	private List<String> list;
	
	private String cpoyTime;
	
    private String choseExir;
    
    //重点关注
    private String emphasisnum;
    
    private Integer sontag;
    
    public String getEmphasisnum() {
		return emphasisnum;
	}

	public void setEmphasisnum(String emphasisnum) {
		this.emphasisnum = emphasisnum;
	}

	/* //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getOwnindustry() {
        return ownindustry;
    }

    public void setOwnindustry(String ownindustry) {
        this.ownindustry = ownindustry == null ? null : ownindustry.trim();
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype == null ? null : usertype.trim();
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype == null ? null : accounttype.trim();
    }

    public BigDecimal getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal prices) {
        this.prices = prices;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember == null ? null : familyMember.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getFileaddress() {
        return fileaddress;
    }

    public void setFileaddress(String fileaddress) {
        this.fileaddress = fileaddress == null ? null : fileaddress.trim();
    }

    public String getCompanyfullname() {
        return companyfullname;
    }

    public void setCompanyfullname(String companyfullname) {
        this.companyfullname = companyfullname == null ? null : companyfullname.trim();
    }

    public String getCompanyshortname() {
        return companyshortname;
    }

    public void setCompanyshortname(String companyshortname) {
        this.companyshortname = companyshortname == null ? null : companyshortname.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode == null ? null : companycode.trim();
    }

    public String getOperateruser() {
        return operateruser;
    }

    public void setOperateruser(String operateruser) {
        this.operateruser = operateruser == null ? null : operateruser.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getMaxlanmunumber() {
        return maxlanmunumber;
    }

    public void setMaxlanmunumber(Integer maxlanmunumber) {
        this.maxlanmunumber = maxlanmunumber;
    }

    public Integer getMaxkeywordnumber() {
        return maxkeywordnumber;
    }

    public void setMaxkeywordnumber(Integer maxkeywordnumber) {
        this.maxkeywordnumber = maxkeywordnumber;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu == null ? null : beizhu.trim();
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

	public Integer getSubjecttimes() {
		return subjecttimes;
	}

	public void setSubjecttimes(Integer subjecttimes) {
		this.subjecttimes = subjecttimes;
	}

	public Integer getMicroopen() {
		return microopen;
	}

	public void setMicroopen(Integer microopen) {
		this.microopen = microopen;
	}

	public Integer getMonitortimes() {
		return monitortimes;
	}

	public void setMonitortimes(Integer monitortimes) {
		this.monitortimes = monitortimes;
	}

	public Integer getWarntimes() {
		return warntimes;
	}

	public void setWarntimes(Integer warntimes) {
		this.warntimes = warntimes;
	}

	public Integer getPersontimes() {
		return persontimes;
	}

	public void setPersontimes(Integer persontimes) {
		this.persontimes = persontimes;
	}

	public Date getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	public Integer getCloudtimes() {
		return cloudtimes;
	}

	public void setCloudtimes(Integer cloudtimes) {
		this.cloudtimes = cloudtimes;
	}

	public Integer getChildtimes() {
		return childtimes;
	}

	public void setChildtimes(Integer childtimes) {
		this.childtimes = childtimes;
	}

	public Integer getUsertimes() {
		return usertimes;
	}

	public void setUsertimes(Integer usertimes) {
		this.usertimes = usertimes;
	}

	public Date getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	public String getManagerid() {
		return managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public String getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Integer getExpirdate() {
		return expirdate;
	}

	public void setExpirdate(Integer expirdate) {
		this.expirdate = expirdate;
	}

	public Integer getSubjectReport() {
		return subjectReport;
	}

	public void setSubjectReport(Integer subjectReport) {
		this.subjectReport = subjectReport;
	}

	public Integer getDayReport() {
		return dayReport;
	}

	public void setDayReport(Integer dayReport) {
		this.dayReport = dayReport;
	}

	public Integer getWeekReport() {
		return weekReport;
	}

	public void setWeekReport(Integer weekReport) {
		this.weekReport = weekReport;
	}

	public Integer getMonthReport() {
		return monthReport;
	}

	public void setMonthReport(Integer monthReport) {
		this.monthReport = monthReport;
	}

	public Integer getModalNum() {
		return modalNum;
	}

	public void setModalNum(Integer modalNum) {
		this.modalNum = modalNum;
	}

	public Integer getKeywordNum() {
		return keywordNum;
	}

	public void setKeywordNum(Integer keywordNum) {
		this.keywordNum = keywordNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getShiyongAcount() {
		return shiyongAcount;
	}

	public void setShiyongAcount(Integer shiyongAcount) {
		this.shiyongAcount = shiyongAcount;
	}

	public Integer getBiaozhunAcount() {
		return biaozhunAcount;
	}

	public void setBiaozhunAcount(Integer biaozhunAcount) {
		this.biaozhunAcount = biaozhunAcount;
	}

	public Integer getInnerAcount() {
		return innerAcount;
	}

	public void setInnerAcount(Integer innerAcount) {
		this.innerAcount = innerAcount;
	}

	public Integer getJinzhiAcount() {
		return jinzhiAcount;
	}

	public void setJinzhiAcount(Integer jinzhiAcount) {
		this.jinzhiAcount = jinzhiAcount;
	}

	public Integer getTotalid() {
		return totalid;
	}

	public void setTotalid(Integer totalid) {
		this.totalid = totalid;
	}

	public Integer getAlltotalid() {
		return alltotalid;
	}

	public void setAlltotalid(Integer alltotalid) {
		this.alltotalid = alltotalid;
	}

	public Integer getExpirAcount() {
		return expirAcount;
	}

	public void setExpirAcount(Integer expirAcount) {
		this.expirAcount = expirAcount;
	}

	public String getManagername() {
		return managername;
	}

	public void setManagername(String managername) {
		this.managername = managername;
	}

	public String getManagertag() {
		return managertag;
	}

	public void setManagertag(String managertag) {
		this.managertag = managertag;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getExpirtag() {
		return expirtag;
	}

	public void setExpirtag(String expirtag) {
		this.expirtag = expirtag;
	}

	public Integer getAllexpirDay() {
		return allexpirDay;
	}

	public void setAllexpirDay(Integer allexpirDay) {
		this.allexpirDay = allexpirDay;
	}

	public Integer getResexpirDay() {
		return resexpirDay;
	}

	public void setResexpirDay(Integer resexpirDay) {
		this.resexpirDay = resexpirDay;
	}

	public Integer getRessubjectNum() {
		return ressubjectNum;
	}

	public void setRessubjectNum(Integer ressubjectNum) {
		this.ressubjectNum = ressubjectNum;
	}

	public Integer getResPersonNum() {
		return resPersonNum;
	}

	public void setResPersonNum(Integer resPersonNum) {
		this.resPersonNum = resPersonNum;
	}

	public Integer getReskeywordNum() {
		return reskeywordNum;
	}

	public void setReskeywordNum(Integer reskeywordNum) {
		this.reskeywordNum = reskeywordNum;
	}

	public Integer getResmiroNum() {
		return resmiroNum;
	}

	public void setResmiroNum(Integer resmiroNum) {
		this.resmiroNum = resmiroNum;
	}

	public Integer getResyujingNum() {
		return resyujingNum;
	}

	public void setResyujingNum(Integer resyujingNum) {
		this.resyujingNum = resyujingNum;
	}

	public Integer getResreportNum() {
		return resreportNum;
	}

	public void setResreportNum(Integer resreportNum) {
		this.resreportNum = resreportNum;
	}

	public Integer getRescloudNum() {
		return rescloudNum;
	}

	public void setRescloudNum(Integer rescloudNum) {
		this.rescloudNum = rescloudNum;
	}

	public Date getExpirtime() {
		return expirtime;
	}

	public void setExpirtime(Date expirtime) {
		this.expirtime = expirtime;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getCpoyTime() {
		return cpoyTime;
	}

	public void setCpoyTime(String cpoyTime) {
		this.cpoyTime = cpoyTime;
	}

	public String getChoseExir() {
		return choseExir;
	}

	public void setChoseExir(String choseExir) {
		this.choseExir = choseExir;
	}

	public Integer getSontag() {
		return sontag;
	}

	public void setSontag(Integer sontag) {
		this.sontag = sontag;
	}

	/*public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}*/
}
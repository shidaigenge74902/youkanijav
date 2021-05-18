package com.bayside.app.opinion.war.myuser.model;

import java.util.List;

public class StanderPower {
    private String id;

    private Integer setword;

    private Integer cansetword;

    private String name;

    private String typename;
    private String typecode;

    private String typeid;
    
    private Integer status;
     //
    private Integer personnum;
    
    private Integer jiancenum;
    
    private Integer subjectnum;
    
    private Integer cloudnum;
    
    private Integer yujingnum;
    
    private Integer sonnum;
    
    private Integer agentnum;
    
    private Integer microopen;
    
    private Integer expirdate;
    
    private Integer subjectReport;
    
    private Integer dayReport;
    
    private Integer weekReport;
    
    private Integer monthReport;
    
    private Integer modalNum;
    
    private Integer keywordNum;
    
    private Integer setReport;
    
    private Integer setTrade;
    private Integer isupdate;
    //重点关注个数
    private Integer emphasisnum;
    private Integer loginnum;
    private Integer checkdays;
    
    private Integer ismedia;
    
    public Integer getEmphasisnum() {
		return emphasisnum;
	}

	public void setEmphasisnum(Integer emphasisnum) {
		this.emphasisnum = emphasisnum;
	}

	private List<StanderPower> list;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getSetword() {
        return setword;
    }

    public void setSetword(Integer setword) {
        this.setword = setword;
    }

    public Integer getCansetword() {
        return cansetword;
    }

    public void setCansetword(Integer cansetword) {
        this.cansetword = cansetword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPersonnum() {
		return personnum;
	}

	public void setPersonnum(Integer personnum) {
		this.personnum = personnum;
	}

	public Integer getJiancenum() {
		return jiancenum;
	}

	public void setJiancenum(Integer jiancenum) {
		this.jiancenum = jiancenum;
	}

	public Integer getSubjectnum() {
		return subjectnum;
	}

	public void setSubjectnum(Integer subjectnum) {
		this.subjectnum = subjectnum;
	}

	public Integer getCloudnum() {
		return cloudnum;
	}

	public void setCloudnum(Integer cloudnum) {
		this.cloudnum = cloudnum;
	}

	public Integer getYujingnum() {
		return yujingnum;
	}

	public void setYujingnum(Integer yujingnum) {
		this.yujingnum = yujingnum;
	}

	public Integer getSonnum() {
		return sonnum;
	}

	public void setSonnum(Integer sonnum) {
		this.sonnum = sonnum;
	}

	public Integer getAgentnum() {
		return agentnum;
	}

	public void setAgentnum(Integer agentnum) {
		this.agentnum = agentnum;
	}

	public Integer getMicroopen() {
		return microopen;
	}

	public void setMicroopen(Integer microopen) {
		this.microopen = microopen;
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

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public Integer getSetReport() {
		return setReport;
	}

	public void setSetReport(Integer setReport) {
		this.setReport = setReport;
	}

	public Integer getSetTrade() {
		return setTrade;
	}

	public void setSetTrade(Integer setTrade) {
		this.setTrade = setTrade;
	}

	public List<StanderPower> getList() {
		return list;
	}

	public void setList(List<StanderPower> list) {
		this.list = list;
	}

	public Integer getIsupdate() {
		return isupdate;
	}

	public void setIsupdate(Integer isupdate) {
		this.isupdate = isupdate;
	}

	public Integer getLoginnum() {
		return loginnum;
	}

	public void setLoginnum(Integer loginnum) {
		this.loginnum = loginnum;
	}

	public Integer getCheckdays() {
		return checkdays;
	}

	public void setCheckdays(Integer checkdays) {
		this.checkdays = checkdays;
	}

	public Integer getIsmedia() {
		return ismedia;
	}

	public void setIsmedia(Integer ismedia) {
		this.ismedia = ismedia;
	}
}
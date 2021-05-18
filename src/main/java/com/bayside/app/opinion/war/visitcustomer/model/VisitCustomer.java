package com.bayside.app.opinion.war.visitcustomer.model;

import java.util.Date;
import java.util.List;

public class VisitCustomer {
    private String id;

    private String companyname;

    private String contactuser;

    private String companyphone;

    private String companyaddress;

    private String personmobilephone;

    private String projecttype;

    private String province;

    private String city;

    private Date visitstarttime;

    private Date visitendtime;

    private String content;

    private String managername;

    private String managerid;

    private Integer status;

    private String zhiwu;

    private String beizhu;
    
    private Date operatorTime;
    
    private Integer noacount;
    
    private Integer yacount;
    
    private Integer jacount;
    
    private Integer addNum;
    
    private List<String> list;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname == null ? null : companyname.trim();
    }

    public String getContactuser() {
        return contactuser;
    }

    public void setContactuser(String contactuser) {
        this.contactuser = contactuser == null ? null : contactuser.trim();
    }

    public String getCompanyphone() {
        return companyphone;
    }

    public void setCompanyphone(String companyphone) {
        this.companyphone = companyphone == null ? null : companyphone.trim();
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress == null ? null : companyaddress.trim();
    }

    public String getPersonmobilephone() {
        return personmobilephone;
    }

    public void setPersonmobilephone(String personmobilephone) {
        this.personmobilephone = personmobilephone == null ? null : personmobilephone.trim();
    }

    public String getProjecttype() {
        return projecttype;
    }

    public void setProjectype(String projecttype) {
        this.projecttype = projecttype == null ? null : projecttype.trim();
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

    public Date getVisitstarttime() {
        return visitstarttime;
    }

    public void setVisitstarttime(Date visitstarttime) {
        this.visitstarttime = visitstarttime;
    }

    public Date getVisitendtime() {
        return visitendtime;
    }

    public void setVisitendtime(Date visitendtime) {
        this.visitendtime = visitendtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getManagername() {
        return managername;
    }

    public void setManagername(String managername) {
        this.managername = managername == null ? null : managername.trim();
    }

    public String getManagerid() {
        return managerid;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid == null ? null : managerid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getZhiwu() {
        return zhiwu;
    }

    public void setZhiwu(String zhiwu) {
        this.zhiwu = zhiwu == null ? null : zhiwu.trim();
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu == null ? null : beizhu.trim();
    }

	
	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}

	public Integer getNoacount() {
		return noacount;
	}

	public void setNoacount(Integer noacount) {
		this.noacount = noacount;
	}

	public Integer getYacount() {
		return yacount;
	}

	public void setYacount(Integer yacount) {
		this.yacount = yacount;
	}

	public Integer getJacount() {
		return jacount;
	}

	public void setJacount(Integer jacount) {
		this.jacount = jacount;
	}

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	public Integer getAddNum() {
		return addNum;
	}

	public void setAddNum(Integer addNum) {
		this.addNum = addNum;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
}
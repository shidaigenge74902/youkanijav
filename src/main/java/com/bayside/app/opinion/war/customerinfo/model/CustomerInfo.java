package com.bayside.app.opinion.war.customerinfo.model;

import java.util.Date;

public class CustomerInfo {
    private String id;

    private String companyname;

    private String contactuser;

    private String zhiwu;

    private String companyphone;

    private String companyaddress;

    private String personmobilephone;

    private String projecttype;

    private String email;

    private String fax;

    private String province;

    private String city;

    private Integer status;

    private String includuser;

    private String desicuser;

    private String includmobilephone;

    private String includphone;

    private String desimobilephone;

    private String desiphone;

    private String managername;

    private String managerid;

    private String operator;

    private Date operatortime;

    private String beizhu;

    private Date qianyuedate;

    private String contractnum;

    private Double contractmoney;
    
    private Date visitStartTime;
    
    private Date visitEndTime;
    
    private Date renlingDate;
    
    private Integer idNum;
    
    private Integer succacount;
    
    private Integer provinceNum;
    
    private Integer customerType;
    
    private Integer puacount;
    
    private Integer spacount;
    

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

    public String getZhiwu() {
        return zhiwu;
    }

    public void setZhiwu(String zhiwu) {
        this.zhiwu = zhiwu == null ? null : zhiwu.trim();
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

    public void setProjecttype(String projecttype) {
        this.projecttype = projecttype == null ? null : projecttype.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIncluduser() {
        return includuser;
    }

    public void setIncluduser(String includuser) {
        this.includuser = includuser == null ? null : includuser.trim();
    }

    public String getDesicuser() {
        return desicuser;
    }

    public void setDesicuser(String desicuser) {
        this.desicuser = desicuser == null ? null : desicuser.trim();
    }

    public String getIncludmobilephone() {
        return includmobilephone;
    }

    public void setIncludmobilephone(String includmobilephone) {
        this.includmobilephone = includmobilephone == null ? null : includmobilephone.trim();
    }

    public String getIncludphone() {
        return includphone;
    }

    public void setIncludphone(String includphone) {
        this.includphone = includphone == null ? null : includphone.trim();
    }

    public String getDesimobilephone() {
        return desimobilephone;
    }

    public void setDesimobilephone(String desimobilephone) {
        this.desimobilephone = desimobilephone == null ? null : desimobilephone.trim();
    }

    public String getDesiphone() {
        return desiphone;
    }

    public void setDesiphone(String desiphone) {
        this.desiphone = desiphone == null ? null : desiphone.trim();
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperatortime() {
        return operatortime;
    }

    public void setOperatortime(Date operatortime) {
        this.operatortime = operatortime;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu == null ? null : beizhu.trim();
    }

    public Date getQianyuedate() {
        return qianyuedate;
    }

    public void setQianyuedate(Date qianyuedate) {
        this.qianyuedate = qianyuedate;
    }

    public String getContractnum() {
        return contractnum;
    }

    public void setContractnum(String contractnum) {
        this.contractnum = contractnum == null ? null : contractnum.trim();
    }

    public Double getContractmoney() {
        return contractmoney;
    }

    public void setContractmoney(Double contractmoney) {
        this.contractmoney = contractmoney;
    }

	public Date getVisitStartTime() {
		return visitStartTime;
	}

	public void setVisitStartTime(Date visitStartTime) {
		this.visitStartTime = visitStartTime;
	}

	public Date getVisitEndTime() {
		return visitEndTime;
	}

	public void setVisitEndTime(Date visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

	public Date getRenlingDate() {
		return renlingDate;
	}

	public void setRenlingDate(Date renlingDate) {
		this.renlingDate = renlingDate;
	}

	public Integer getIdNum() {
		return idNum;
	}

	public void setIdNum(Integer idNum) {
		this.idNum = idNum;
	}

	public Integer getSuccacount() {
		return succacount;
	}

	public void setSuccacount(Integer succacount) {
		this.succacount = succacount;
	}

	public Integer getProvinceNum() {
		return provinceNum;
	}

	public void setProvinceNum(Integer provinceNum) {
		this.provinceNum = provinceNum;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getPuacount() {
		return puacount;
	}

	public void setPuacount(Integer puacount) {
		this.puacount = puacount;
	}

	public Integer getSpacount() {
		return spacount;
	}

	public void setSpacount(Integer spacount) {
		this.spacount = spacount;
	}
}
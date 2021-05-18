package com.bayside.app.opinion.war.baobei.bo;

import java.util.Date;
import java.util.List;

public class BaoBeiBo {

	private String id;

    private String orgname;

    private String province;

    private String city;

    private String county;

    private String expirtytime;

    private String managerid;
    
    private String createTime;
    
    private List<String> list;
    
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getExpirtytime() {
		return expirtytime;
	}

	public void setExpirtytime(String expirtytime) {
		this.expirtytime = expirtytime;
	}

	public String getManagerid() {
		return managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	

	

}

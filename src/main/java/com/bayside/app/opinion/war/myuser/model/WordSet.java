package com.bayside.app.opinion.war.myuser.model;

import java.util.Date;
import java.util.List;

public class WordSet {
    private String id;

    private String name;

    private Integer setword;

    private Integer cansetword;

    private String userid;

    private Integer status;
    
    private List<WordSet> list;
  
    private Date endtime;
    
    private Integer isupdate;
    
    private String emotionname;
    
    private String evalue;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public List<WordSet> getList() {
		return list;
	}

	public void setList(List<WordSet> list) {
		this.list = list;
	}

	public Integer getIsupdate() {
		return isupdate;
	}

	public void setIsupdate(Integer isupdate) {
		this.isupdate = isupdate;
	}

	public String getEmotionname() {
		return emotionname;
	}

	public void setEmotionname(String emotionname) {
		this.emotionname = emotionname;
	}

	public String getEvalue() {
		return evalue;
	}

	public void setEvalue(String evalue) {
		this.evalue = evalue;
	}

  
}
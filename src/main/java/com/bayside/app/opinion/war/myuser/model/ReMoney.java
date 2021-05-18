package com.bayside.app.opinion.war.myuser.model;

import java.util.Date;

public class ReMoney {
    private String id;

    private Integer remoney;

    private Date expirtime;

    private String userid;

    private Date operatetime;

    private String operator;
    
    private Integer yacount;
    
    private String managerid;
    private String startTime;
    private String endTime;
    private String time;
    private Integer idNum;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getRemoney() {
        return remoney;
    }

    public void setRemoney(Integer remoney) {
        this.remoney = remoney;
    }

    public Date getExpirtime() {
        return expirtime;
    }

    public void setExpirtime(Date expirtime) {
        this.expirtime = expirtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

	public Integer getYacount() {
		return yacount;
	}

	public void setYacount(Integer yacount) {
		this.yacount = yacount;
	}

	public String getManagerid() {
		return managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
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


	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public Integer getIdNum() {
		return idNum;
	}

	public void setIdNum(Integer idNum) {
		this.idNum = idNum;
	}
}
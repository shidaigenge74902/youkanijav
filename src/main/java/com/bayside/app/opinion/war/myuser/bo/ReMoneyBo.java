package com.bayside.app.opinion.war.myuser.bo;

import java.util.Date;

public class ReMoneyBo {

	    private String id;

	    private Integer remoney;

	    private String expirtime;

	    private String userid;

	    private Date operatetime;

	    private String operator;
	    
	    private String managerid;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Integer getRemoney() {
			return remoney;
		}

		public void setRemoney(Integer remoney) {
			this.remoney = remoney;
		}

		public String getExpirtime() {
			return expirtime;
		}

		public void setExpirtime(String expirtime) {
			this.expirtime = expirtime;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
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
			this.operator = operator;
		}

		public String getManagerid() {
			return managerid;
		}

		public void setManagerid(String managerid) {
			this.managerid = managerid;
		}

}

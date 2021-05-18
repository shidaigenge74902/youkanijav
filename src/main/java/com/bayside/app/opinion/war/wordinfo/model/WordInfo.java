package com.bayside.app.opinion.war.wordinfo.model;

import java.util.Date;

public class WordInfo {
    private String id;

    private String wordname;

    private String value;
    
    private String dustryid;
    
    private String dustryname;
    
    private Date registertime;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWordname() {
        return wordname;
    }

    public void setWordname(String wordname) {
        this.wordname = wordname == null ? null : wordname.trim();
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDustryid() {
		return dustryid;
	}

	public void setDustryid(String dustryid) {
		this.dustryid = dustryid;
	}

	public String getDustryname() {
		return dustryname;
	}

	public void setDustryname(String dustryname) {
		this.dustryname = dustryname;
	}

	public Date getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

   
   
	
}
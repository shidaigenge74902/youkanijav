package com.bayside.app.opinion.war.site.model;

import java.util.Date;
import java.util.List;

public class Site {
    private String id;

    private String url;

    private String name;

    private Date updateTime;

    private Integer count;

    private Date nextTime;

    private Double weight;

    private String fathersiteid;

    private Integer deep;

    private String type;

    private Double frequency;

    private String province;

    private String domain;
    private Boolean screening;
    private String firstdomain;
    private String seconddomain;
    private Integer dcollect;
    private Integer gcollect;
    private String collectrule;
    private String targetcustomer;
    private Integer specialcollect;
    private String targetsubject;
    private String keyword;
    private List<Site> list;
    private String isadd;
   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getFathersiteid() {
        return fathersiteid;
    }

    public void setFathersiteid(String fathersiteid) {
        this.fathersiteid = fathersiteid == null ? null : fathersiteid.trim();
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

	public Boolean getScreening() {
		return screening;
	}

	public void setScreening(Boolean screening) {
		this.screening = screening;
	}

	public String getFirstdomain() {
		return firstdomain;
	}

	public void setFirstdomain(String firstdomain) {
		this.firstdomain = firstdomain;
	}

	public Integer getDcollect() {
		return dcollect;
	}

	public void setDcollect(Integer dcollect) {
		this.dcollect = dcollect;
	}

	public Integer getGcollect() {
		return gcollect;
	}

	public void setGcollect(Integer gcollect) {
		this.gcollect = gcollect;
	}

	public String getCollectrule() {
		return collectrule;
	}

	public void setCollectrule(String collectrule) {
		this.collectrule = collectrule;
	}

	public String getTargetcustomer() {
		return targetcustomer;
	}

	public void setTargetcustomer(String targetcustomer) {
		this.targetcustomer = targetcustomer;
	}

	public Integer getSpecialcollect() {
		return specialcollect;
	}

	public void setSpecialcollect(Integer specialcollect) {
		this.specialcollect = specialcollect;
	}

	public String getTargetsubject() {
		return targetsubject;
	}

	public void setTargetsubject(String targetsubject) {
		this.targetsubject = targetsubject;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Site> getList() {
		return list;
	}

	public void setList(List<Site> list) {
		this.list = list;
	}

	public String getIsadd() {
		return isadd;
	}

	public void setIsadd(String isadd) {
		this.isadd = isadd;
	}

	public String getSeconddomain() {
		return seconddomain;
	}

	public void setSeconddomain(String seconddomain) {
		this.seconddomain = seconddomain;
	}

}
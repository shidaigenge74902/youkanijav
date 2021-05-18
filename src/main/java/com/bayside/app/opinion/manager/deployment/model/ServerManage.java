package com.bayside.app.opinion.manager.deployment.model;

public class ServerManage {
    private Integer id;

    private String outip;

    private String inip;

    private String name;

    private String collect;
    
    private String serverCollect;
    
    private Boolean isNormal = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutip() {
        return outip;
    }

    public void setOutip(String outip) {
        this.outip = outip == null ? null : outip.trim();
    }

    public String getInip() {
        return inip;
    }

    public void setInip(String inip) {
        this.inip = inip == null ? null : inip.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect == null ? null : collect.trim();
	}

	public String getServerCollect() {
		return serverCollect;
	}

	public void setServerCollect(String serverCollect) {
		this.serverCollect = serverCollect == null ? null : serverCollect.trim();
	}

	public Boolean getIsNormal() {
		return isNormal;
	}

	public void setIsNormal(Boolean isNormal) {
		this.isNormal = isNormal;
	}
    
}
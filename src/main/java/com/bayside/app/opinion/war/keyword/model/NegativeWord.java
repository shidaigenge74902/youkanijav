package com.bayside.app.opinion.war.keyword.model;

public class NegativeWord {
    private String id;

    private String name;

    private String weiduid;

    private Integer code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getWeiduid() {
        return weiduid;
    }

    public void setWeiduid(String weiduid) {
        this.weiduid = weiduid == null ? null : weiduid.trim();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
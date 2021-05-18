package com.bayside.app.opinion.manager.hanlp.bo;

public class NominalBo {
    private String nominal;

    private String nominalName;

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal == null ? null : nominal.trim();
    }

    public String getNominalName() {
        return nominalName;
    }

    public void setNominalName(String nominalName) {
        this.nominalName = nominalName == null ? null : nominalName.trim();
    }
}
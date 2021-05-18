package com.bayside.app.opinion.manager.hanlp.bo;

public class HanlpWordBo {
    private String id;

    private String wordName;

    private String tradeId;

    private String tradeName;

    private String nominal;
    
    private String nominalName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName == null ? null : wordName.trim();
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName == null ? null : tradeName.trim();
    }

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
package com.manhui.model;

public class ShowDataUpdate extends BaseObject{
	
	private String id;
	
	private String name;				//名称
	
	private String number;				//数量
	
	private String sj;					//时间
	
	private String namet;				//实时交易用到，
	
	private String money;				//实时交易用到，
	
	private String leibie;				//实时交易用到，
	
	private String leimu;				//实时交易用到，

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getNamet() {
		return namet;
	}

	public void setNamet(String namet) {
		this.namet = namet;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getLeibie() {
		return leibie;
	}

	public void setLeibie(String leibie) {
		this.leibie = leibie;
	}

	public String getLeimu() {
		return leimu;
	}

	public void setLeimu(String leimu) {
		this.leimu = leimu;
	}
}

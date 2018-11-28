package com.manhui.model;

import java.util.List;

/**
 * 
 * Title: Company
 * @description:公司实体类
 * @author Liucheng
 *
 */
public class Company extends BaseObject{

	private Integer id;
	
	private String name;
	
	private String imgUrl; //公司图片路径
	
	private Integer regionId; //地区id
	
	private String pCode;
	
	private String province; //省
	
	private String cCode;
	
	private String city; //市
	
	private String dCode;
	
	private String district; //区县
	
	private String address; //详细地址
	
	private String legalPerson; //法人
	
	private String legalPersonPhone; //法人联系方式
	
	private Integer state; //0代表禁用，1代表启用
	
	//显示用
	private Integer shopNum; //店铺数量
	
	private List<Shop> shops;//店铺

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl == null ? null : imgUrl.trim();
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district == null ? null : district.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson == null ? null : legalPerson.trim();
	}

	public String getLegalPersonPhone() {
		return legalPersonPhone;
	}

	public void setLegalPersonPhone(String legalPersonPhone) {
		this.legalPersonPhone = legalPersonPhone == null ? null : legalPersonPhone.trim();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getShopNum() {
		return shopNum;
	}

	public void setShopNum(Integer shopNum) {
		this.shopNum = shopNum;
	}

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public String getdCode() {
		return dCode;
	}

	public void setdCode(String dCode) {
		this.dCode = dCode;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", imgUrl=" + imgUrl + ", regionId=" + regionId + ", province="
				+ province + ", city=" + city + ", district=" + district + ", address=" + address + ", legalPerson="
				+ legalPerson + ", legalPersonPhone=" + legalPersonPhone + ", state=" + state + "]";
	}
	
}

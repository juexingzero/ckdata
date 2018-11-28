package com.manhui.model;

import java.io.Serializable;

/**
 * 
 * Title: Shop
 * @description:店铺实体类
 * @author Liucheng
 *
 */
public class Shop extends BaseObject implements Serializable{

	private Integer id;
	
	private String name;
	
	private String shopNid;
	
	private String imgUrl; //店铺图片路径
	
	private String phone; //联系方式
	
	private Integer channelId; //店铺来源
	
	private Integer regionId; //所属地区id
	
	private String province; //省
	
	private String city; //市
	
	private String district; //区县
	
	private String address; //详细地址
	
	private Integer companyId; //所属公司id
	
	private String businessIds; //所属行业ids
	
	private Integer state; //0代表禁用，1代表启用
	
	private String url;
	
	private Integer goodsNum;	//移动端展示需要
	
	private String companyName;	//移动段展示需要，公司名称

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
		this.name = name;
	}


	public String getShopNid() {
		return shopNid;
	}


	public void setShopNid(String shopNid) {
		this.shopNid = shopNid;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Integer getChannelId() {
		return channelId;
	}


	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
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
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public String getBusinessIds() {
		return businessIds;
	}


	public void setBusinessIds(String businessIds) {
		this.businessIds = businessIds;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
	


	public Integer getGoodsNum() {
		return goodsNum;
	}


	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	@Override
	public String toString() {
		return "Shop [id=" + id + ", name=" + name + ", shopNid=" + shopNid + ", imgUrl=" + imgUrl + ", phone=" + phone
				+ ", channelId=" + channelId + ", regionId=" + regionId + ", province=" + province + ", city=" + city
				+ ", district=" + district + ", address=" + address + ", companyId=" + companyId + ", businessIds="
				+ businessIds + ", state=" + state + ", url=" + url + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shopNid == null) ? 0 : shopNid.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (shopNid == null) {
			if (other.shopNid != null)
				return false;
		} else if (!shopNid.equals(other.shopNid))
			return false;
		return true;
	}
	
}


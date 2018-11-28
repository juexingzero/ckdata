package com.manhui.model;
/**
 * 
 * Title: Site
 * @description:服务站点实体类
 * @author Liucheng
 *
 */
public class Site extends BaseObject{

	private Integer id;
	
	private String name;
	
	private String cname;	//展示用公司名称
	
	private String imgUrl; //站点图片路径
	
	private String phone; //联系方式
	
	private Integer regionId; //所属地区id
	
	private String province; //省
	
	private String city; //市
	
	private String district; //区县
	
	private String address; //详细地址
	
	private Integer companyId; //所属公司id
	
	private Integer state; //0代表禁用，1代表启用

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

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", name=" + name + ", imgUrl=" + imgUrl + ", phone=" + phone + ", regionId="
				+ regionId + ", province=" + province + ", city=" + city + ", district=" + district + ", address="
				+ address + ", companyId=" + companyId + ", state=" + state + "]";
	}

}


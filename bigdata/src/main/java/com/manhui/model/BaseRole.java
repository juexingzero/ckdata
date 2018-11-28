package com.manhui.model;

import java.util.List;

/**
 * @ClassName: BaseRole
 * @description:基础角色表
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/1/28
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class BaseRole extends BaseObject{
    private Integer id;        //角色编码

    private String name;       //角色名称

    private String descr;      //描述
    
    private Integer popedomId; //权限根目录 
    
    private List<BaseUser> baseUserList;

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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getPopedomId() {
		return popedomId;
	}

	public void setPopedomId(Integer popedomId) {
		this.popedomId = popedomId;
	}

	public List<BaseUser> getBaseUserList() {
		return baseUserList;
	}

	public void setBaseUserList(List<BaseUser> baseUserList) {
		this.baseUserList = baseUserList;
	}
}
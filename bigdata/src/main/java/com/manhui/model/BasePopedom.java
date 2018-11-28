package com.manhui.model;


import java.util.List;

/**
 * @ClassName: BasePopedom
 * @description:基础权限表
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/1/28
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class BasePopedom extends BaseObject{
	private Integer id;			  //菜单id
	
    private String code;          //菜单编码

    private String name;          //菜单名称

    private Integer pid;          //菜单级数(父菜单)
    
    private Integer type;         //菜单类型

    private String event;         //菜单事件

    private String icon;          //菜单图标
    
    private String remark;        //备注
    
    private List<BasePopedom> submenus;   //子菜单

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<BasePopedom> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<BasePopedom> submenus) {
		this.submenus = submenus;
	}
}
package com.manhui.model;

import java.util.List;

/**
 * @ClassName: Category
 * @description:商品类目表
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/1/30
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class Category extends BaseObject{
    private Integer id;             //id

    private String name;            //类目名称
 
    private Integer pid;            //父节点
    
    private String pName;			//父节点名称(展示用)

    private String classList;       //类别id列表

    private Integer classLayer;     //类目深度

    private Integer sortid;         //排序

    private Integer state;          //状态
    
    private String leibie;			//类别名称（页面展示需要）
    
    private String leimu;			//类目名称（页面展示需要）
    
    private List<Category> cList;		//移动端展示需要
    
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getClassList() {
        return classList;
    }

    public void setClassList(String classList) {
        this.classList = classList == null ? null : classList.trim();
    }

    public Integer getClassLayer() {
        return classLayer;
    }

    public void setClassLayer(Integer classLayer) {
        this.classLayer = classLayer;
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

	public List<Category> getcList() {
		return cList;
	}

	public void setcList(List<Category> cList) {
		this.cList = cList;
	}
    
}
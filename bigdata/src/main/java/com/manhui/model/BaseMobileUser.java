package com.manhui.model;

import java.math.BigDecimal;

/**
 * @ClassName: BaseMobileUser
 * @description:基础移动用户信息主表
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/3/20
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class BaseMobileUser{
    private Integer id;

    private String name;

    private String password;

    private String phone;

    private Integer state;
    
    private String loginFlag;
    
    //以下为首页展示用
    private Integer companyNum;  //公司数量
    
    private Integer shopNum;  	 //店铺数量
    
    private Integer articleNum;	 //商品
    
    private Integer sellNum;	 //售卖数量
    
    private BigDecimal sellPrice;//销售金额
    
    private Integer inputNum;	 //收件数量
    
    private Integer outputNum;	 //发件数量
    
    private BigDecimal buyTotalAmount;	 //代买商品总金额
    
    private BigDecimal sellTotalAmount;	 //销售商品总金额

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public Integer getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(Integer companyNum) {
		this.companyNum = companyNum;
	}

	public Integer getShopNum() {
		return shopNum;
	}

	public void setShopNum(Integer shopNum) {
		this.shopNum = shopNum;
	}

	public Integer getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(Integer articleNum) {
		this.articleNum = articleNum;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getInputNum() {
		return inputNum;
	}

	public void setInputNum(Integer inputNum) {
		this.inputNum = inputNum;
	}

	public Integer getOutputNum() {
		return outputNum;
	}

	public void setOutputNum(Integer outputNum) {
		this.outputNum = outputNum;
	}

	public BigDecimal getBuyTotalAmount() {
		return buyTotalAmount;
	}

	public void setBuyTotalAmount(BigDecimal buyTotalAmount) {
		this.buyTotalAmount = buyTotalAmount;
	}

	public BigDecimal getSellTotalAmount() {
		return sellTotalAmount;
	}

	public void setSellTotalAmount(BigDecimal sellTotalAmount) {
		this.sellTotalAmount = sellTotalAmount;
	}
}
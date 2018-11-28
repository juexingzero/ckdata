package com.manhui.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Title: Order
 * @description:订单信息主表
 * @author Liucheng
 *
 */
public class Order extends BaseObject{

	private Integer id;

    private Integer articleId;//商品id

    private Integer shopId;//店铺id

    private Date sellTime;//销售时间

    private BigDecimal sellPrice;//销售价格

    private Integer quantity;//销售数量

    private Integer expressId;//物流id

    private String expressNo;//物流单号

    private Byte state;
    
    private Shop shop;	//店铺信息（页面展示需要）
    
    private Article article;	//商品信息（页面展示需要）
    
    private Category category;	//类目，类别信息（页面展示需要）
    
    private Channel channel;	//渠道信息，（移动端展示需要）
    
    private Express express;	//快递物流信息（移动端展示需要）
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Date getSellTime() {
		return sellTime;
	}

	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}
	

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Express getExpress() {
		return express;
	}

	public void setExpress(Express express) {
		this.express = express;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", articleId=" + articleId + ", shopId=" + shopId + ", sellTime=" + sellTime
				+ ", sellPrice=" + sellPrice + ", quantity=" + quantity + ", expressId=" + expressId + ", expressNo="
				+ expressNo + ", state=" + state + "]";
	}
}

package com.manhui.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: Article.java
 * @description:   商品信息表
 * @author: WangSheng
 * @date Create in 11:33 2018/1/29
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class Article extends BaseObject{
    private Integer id;

    private String title;
    
    private String articleNid;

    private String url;

    private String imgUrl;

    private BigDecimal price;
    
    private BigDecimal quantity;

    private Integer shopId;

    private Integer categoryId;

    private Byte state;
    
    //展示用
    private Category category;
    
    private Shop shop;
    
    private Channel channel;
    
    private List<Order> orderList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getArticleNid() {
		return articleNid;
	}

	public void setArticleNid(String articleNid) {
		this.articleNid = articleNid;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", url=" + url + ", imgUrl=" + imgUrl + ", price=" + price
				+ ", shopId=" + shopId + ", categoryId=" + categoryId + ", state=" + state + "]";
	}
    
}
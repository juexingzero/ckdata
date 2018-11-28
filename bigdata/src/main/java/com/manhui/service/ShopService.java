package com.manhui.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.Shop;
import com.manhui.util.PageBean;

/**
 * 
 * <p>Title: ShopService</p>
 * @author LiuCheng
 */
@Mapper
public interface ShopService {

	//获取店铺数据总条数(分页用)
	int findShopCount();
	List<Shop> findShopPage(Shop shop);
	List<Shop> findShopList(); 
	//显示店铺列表（pc）
	List<Shop> getShopList(Shop shop); 
	//显示店铺列表分页
	List<Shop> getShopListPage(Shop shop,Integer page);
	
	//分页店铺公司列表
	List<Map> getPageShop(@Param("pageBean") PageBean<Map> pageBean);
	//新增店铺信息
	void insertShop (Shop shop)  ;
	//修改店铺信息
	int updateShop(Shop shop);
	//查看店铺详细信息
	Map selectById(Shop shop);
	//删除店铺信息
	void deleteShop(Shop shop);
	//根据id查店铺名
	Shop findNameById(Integer id);
	//获取总店铺数
	int getShopCount();
	//按ID查询店铺数量
	List<Integer> getShopCountById(String id);
	//获取所有店铺地址数据
	List<Shop> getShopUrlList(Integer id);
	//根据公司id查询店铺
	List<Shop> getShopByCompanyId(Integer companyId);
	//根据店铺id查询店铺信息
	Shop getshopByid(Integer id);
	
	//移动端展示店铺
	List<Shop> findAllShop(String name,Integer page);
}

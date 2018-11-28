package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.Category;


/**
 * @ClassName: BaseRuleService
 * @description: 用户角色关联 Service 接口
 * @author: HeJiayan
 * @date Create in 15:29 2017/9/29
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface CategoryService {
	
	//计算分页总数用
	int count();
	
	//计算类目分页总数用
	int subCount();
	
	//查询商品行业数据
	List<Category> getCategoryPage(Category category);
	
	//查询商品类目数据
	List<Category> getSubCategoryPage(Category category);
	
	//获取所有类目列表，以供新增商品时试用
	List<Category> getCategoryByCateIdForList();
	
	//根据Id查询商品行业数据
	Category getCategoryById(Integer id);
	
	//查询所有行业数据
	List<Category> getAllCategory();//添加商品信息可用
	
	//查询商品行业排序
	Integer getCategorySortByPid(@Param("pId")Integer pId);
	
	//查询商品行业所对应的类目
	List<Category> getCategoryByPid(@Param("pId")Integer pId);//添加商品信息可用
	
	//新增商品行业数据
	void insertMainCategory(Category category);
	
	void deleteMainCategory(Integer id);
	
	void deleteCategory(Integer pid);
}

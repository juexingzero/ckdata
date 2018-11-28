package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.ArticlePriceLog;

/**
 * @ClassName: ArticlePriceLogService
 * @description: 商品价格历史Service 接口
 * @author: WangSheng
 * @date Create in 16:29 2018/1/27
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface ArticlePriceLogService {
	
	//条件查询，根据名称查询商品价格信息
	List<ArticlePriceLog> findArticlePL(String name);
	
	//根据商品id查询商品价格记录（多种商品）
	List<ArticlePriceLog> findAPLByaId(Integer articleId);
	
	
	//根据商品id查询商品价格记录（一种商品）
	List<ArticlePriceLog> findAPLByaIds(Integer articleId);
	
	//数据全查前端根据数据分类展示？
	List<ArticlePriceLog> findArticlePriceLog();
	
	//查询当月商品id
	List<ArticlePriceLog> findArticlePriceLogByMonth(String month);
	
	//查询当天商品id
	List<ArticlePriceLog> findArticlePriceLogByDay(String day);
	
	//查询当年商品id
	List<ArticlePriceLog> findArticlePriceLogByYear(String year);
}
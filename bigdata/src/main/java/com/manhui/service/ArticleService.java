package com.manhui.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.Article;
import com.manhui.util.PageBean;

/**
 * @ClassName: ArticleService
 * @description: 商品信息Service 接口
 * @author: LiuCheng
 * @date Create in 11:29 2018/1/29
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface ArticleService {
		//显示商品列表
		List<Article> getArticleList(Article article); 
		//分页商品列表
		List<Article> getPageArticle(Article article);
		//新增商品信息
		int insertArticle(Article article);
		//查看商品详细信息
		Article selectById(Integer id);
		//修改商品信息
		int updateArticle(Article article);
		//删除商品信息
		int deleteArticle(Article article);
		//按按商品ID获取商品数量
		int getCountByNid(String id);
		//获取全部商品
		List<Article> getAllArticle();
		//获取商品总量
		int getArticleCount();
		//获取全部商品总价以及总销量
		Article getAllTotalAmount();
		
		//查询某店铺商品总量
		int findGodosNumByShopId(Integer id);
		
		//查询商品信息（移动端查询商品名称和价格）
		List<Article> findArticle(String name,Integer page);
}
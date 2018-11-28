package com.manhui.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.Site;
import com.manhui.util.PageBean;

/**
 * 
 * <p>Title: SiteService</p>
 * @author LiuCheng
 */
@Mapper
public interface SiteService {

	//显示站点列表
	List<Site> getSiteList(Site site); 
	//分页站点列表
	List<Site> getPageSite(Site site);
	//新增站点信息
	int insertSite(Site site);
	//修改站点信息
	int updateSite(Site site);
	//查看站点详细信息
	Map selectById(Site site);
	//删除站点信息
	int deleteSite(Site site);
	
	//查询站点详细ByID
	Site findSiteById(Integer id);
}

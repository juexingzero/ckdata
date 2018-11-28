package com.manhui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.manhui.model.Company;
import com.manhui.model.Site;
import com.manhui.service.CompanyService;
import com.manhui.service.SiteService;
import com.manhui.util.PageBean;

/**
 * 
 * Title: SiteController
 * @description:站点控制层
 * @author Liucheng
 *
 */
@Controller
public class SiteController {

	@Autowired
	private SiteService siteService;
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 获取站点信息列表
	 * */
	@RequestMapping("/siteList_{pageCurrent}_{pageSize}_{pageCount}")
	@ResponseBody
	public ModelAndView getSiteList(String name,String title,@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,
									@PathVariable Integer pageCount,String upInfo){
		ModelAndView mv = new ModelAndView();
		Site site = new Site();
		if(name != null || name != ""){
			site.setName(name);
		}
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = siteService.getSiteList(site).size();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		site.setStart((pageCurrent - 1)*pageSize);
		site.setEnd(pageSize);
		List<Site> sites = siteService.getPageSite(site);
		mv.addObject("site", sites);
		mv.setViewName("pages/site/siteList");
		mv.addObject("rows", rows);
		mv.addObject("pageCurrent", pageCurrent);
		mv.addObject("pageSize", pageSize);
		mv.addObject("pageCount", pageCount);
		mv.addObject("upInfo", upInfo);
		return mv;
	}
	
	/**
	 * 跳转到新增站点页面
	 * */
	@RequestMapping("/addSite")
	public String addSite(Model m){
		List<Company> list = new ArrayList<>();
		Company company = new Company();
		list = companyService.getCompanyList(company);//查询全部的公司
		m.addAttribute("list", list);
		return "pages/site/addSite";
	}
	
	/**
	 * 保存新增站点信息
	 */
	@RequestMapping("/saveSite")
	public String saveSite(Site site){
		String upInfo = "";
		if(site.getRegionId() == null){
			site.setRegionId(1);
		}
		if(site.getImgUrl() == null){
			site.setImgUrl("/mjw");
		}
		if(site.getState() == null){
			site.setState(1);
		}
		int row = siteService.insertSite(site);
		if(row > 0){
			upInfo = "success";
		}else{
			upInfo = "error";
		}
		return "redirect:siteList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/viewSite")
	public ModelAndView viewSite(Site site){
		ModelAndView mv = new ModelAndView();
		Map c = siteService.selectById(site);
		mv.addObject("vSite", c);
		mv.setViewName("pages/site/viewSite");
		return mv;
	}
	
	/**
	 * 修改站点信息
	 */
	@RequestMapping("/updateSite")
	public ModelAndView updateSite(Site site){
		ModelAndView mv = new ModelAndView();
		Map c = siteService.selectById(site);
		mv.addObject("eSite", c);
		mv.setViewName("pages/site/editSite");
		return mv;
	}
	
	/**
	 * 修改页面点击保存
	 */
	@RequestMapping("/editSite")
	public String editSite(Site site){
		String upInfo = "";
		int row = siteService.updateSite(site);
		if(row > 0){
			upInfo = "success";
		}else{
			upInfo = "error";
		}
		return "redirect:siteList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 删除站点信息
	 */
	@RequestMapping("/deleteSite")
	@ResponseBody
	public Map deleteSite(Site site){
		Map m = new HashMap<>();
		site.setState(0);
		int row = siteService.deleteSite(site);
		if(row > 0){
			m.put("success", "删除成功！");
		}else{
			m.put("error", "删除失败！");
		}
		return m;
	}
}

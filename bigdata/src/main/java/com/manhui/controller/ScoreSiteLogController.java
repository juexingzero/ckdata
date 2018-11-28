package com.manhui.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.manhui.model.Express;
import com.manhui.model.ScoreSiteLog;
import com.manhui.model.Site;
import com.manhui.service.ExpressService;
import com.manhui.service.ScoreShopLogService;
import com.manhui.service.ScoreSiteLogService;
import com.manhui.service.ShopService;
import com.manhui.service.SiteService;
import com.manhui.util.PageBean;

/**
 * 
 * Title: ExpressController
 * @description:服务站点评价信息控制层
 * @author LiuCheng
 *
 */
@Controller
public class ScoreSiteLogController {
	
	@Autowired
	private ScoreSiteLogService scoreSiteLogService;
	
	@Autowired
	private SiteService siteService;
	
	/**
	 * 店铺评分信息列表
	 * */
	@RequestMapping("/scoreSiteLogList_{pageCurrent}_{pageSize}_{pageCount}")
	@ResponseBody
	public ModelAndView getscoreSiteLogList(@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,
											@PathVariable Integer pageCount,String upInfo){
		ModelAndView mv = new ModelAndView();
		List<ScoreSiteLog> list = new ArrayList<>();
		list = scoreSiteLogService.getScoreSiteLogList();//查询全部的物流信息
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = list.size();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		//int startIndex = pb.getStartIndex();
		//System.out.println(startIndex);
		ScoreSiteLog ssl = new ScoreSiteLog();
		ssl.setStart((pageCurrent - 1)*pageSize);
		ssl.setEnd(pageSize);
		List<ScoreSiteLog> listmap = scoreSiteLogService.getPageScoreSiteLog(ssl);//分页物流信息
		mv.addObject("scoreSiteLog", listmap);
		mv.addObject("rows", rows);
		mv.addObject("pageCurrent", pageCurrent);
		mv.addObject("pageSize", pageSize);
		mv.addObject("pageCount", pageCount);
		mv.addObject("upInfo", upInfo);
		mv.setViewName("pages/scoreSiteLog/scoreSiteLog_list");
		return mv;
	}
	
	/**
	 * 跳转到新增店铺评价页面
	 * */
	@RequestMapping("/addScoreSiteLog")
	public String addScoreSiteLog(Model m){
		//查询出所有的店铺名称，以供下拉框选择
		Site site = new Site();
		List<Site> listSite = siteService.getSiteList(site);
		m.addAttribute("listSite", listSite);
		return "pages/scoreSiteLog/addScoreSiteLog";
	}
	
	/**
	 * 保存新增订单信息
	 */
	@RequestMapping("/saveScoreSiteLog")
	public String saveScoreSiteLog(ScoreSiteLog scoreSiteLog){
		String upInfo = "";
		if(scoreSiteLog.getAddTime() == null){
			Date date = new Date();
			scoreSiteLog.setAddTime(date);;
		}
		try{
			scoreSiteLogService.insertScoreSiteLog(scoreSiteLog);
			upInfo = "success";
		}catch(Exception e){
			e.printStackTrace();
			upInfo = "error";
		};
		return "redirect:scoreSiteLogList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 修改店铺评价信息
	 */
	@RequestMapping("/updateScoreSiteLog")
	public ModelAndView updateScoreSiteLog(String id){
		ModelAndView mv = new ModelAndView();
		ScoreSiteLog scoreSiteLog = scoreSiteLogService.selectScoreSiteLogById(Integer.valueOf(id));//根据id查询对应的物流信息
		Site site = new Site();
		List<Site> listSite = siteService.getSiteList(site);//查询出所有的商品名称，以供下拉框选择
		mv.addObject("listSite", listSite);
		mv.addObject("scoreSiteLog", scoreSiteLog);
		mv.setViewName("pages/scoreSiteLog/editScoreSiteLog");
		return mv;
	}
	
	/**
	 * 修改页面点击保存
	 */
	@RequestMapping("/editScoreSiteLog")
	public String editScoreSiteLog(ScoreSiteLog scoreSiteLog){
		String upInfo = "";
		//System.out.println(article);
		try{
			scoreSiteLogService.updateScoreSiteLog(scoreSiteLog);
			upInfo = "success";
		}catch(Exception e){
			e.printStackTrace();
			upInfo = "error";
		};
		return "redirect:scoreSiteLogList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 删除订单信息
	 */
	@RequestMapping("/deleteScoreSiteLog")
	@ResponseBody
	public Map<String,String> deleteScoreSiteLog(String id){
		Map<String,String> m = new HashMap<>();
		try{
			scoreSiteLogService.deleteScoreSiteLog(Integer.valueOf(id));
			m.put("success", "删除成功！");
		}catch(Exception e){
			e.printStackTrace();
			m.put("error", "删除失败！");
		};
		return m;
	}
}

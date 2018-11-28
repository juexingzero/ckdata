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

import com.manhui.model.ScoreShopLog;
import com.manhui.model.Shop;
import com.manhui.service.ScoreShopLogService;
import com.manhui.service.ShopService;

/**
 * 
 * Title: ExpressController
 * @description:店铺评价信息控制层
 * @author LiuCheng
 *
 */
@Controller
public class ScoreShopLogController {
	
	@Autowired
	private ScoreShopLogService scoreShopLogService;
	
	@Autowired
	private ShopService shopService;
	
	/**
	 * 店铺评分信息列表
	 * */
	@RequestMapping("/scoreShopLogList_{pageCurrent}_{pageSize}_{pageCount}")
	@ResponseBody
	public ModelAndView getscoreShopLogList(@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,
											@PathVariable Integer pageCount,String upInfo){
		ModelAndView mv = new ModelAndView();
		List<ScoreShopLog> list = new ArrayList<>();
		list = scoreShopLogService.getScoreShopLogList();//查询全部的物流信息
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = list.size();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		//int startIndex = pb.getStartIndex();
		//System.out.println(startIndex);
		ScoreShopLog ssl = new ScoreShopLog();
		ssl.setStart((pageCurrent - 1)*pageSize);
		ssl.setEnd(pageSize);
		List<ScoreShopLog> listmap = scoreShopLogService.getPageScoreShopLog(ssl);//分页物流信息
		mv.addObject("scoreShopLog", listmap);
		mv.addObject("rows", rows);
		mv.addObject("pageCurrent", pageCurrent);
		mv.addObject("pageSize", pageSize);
		mv.addObject("pageCount", pageCount);
		mv.addObject("upInfo", upInfo);
		mv.setViewName("pages/scoreShopLog/scoreShopLog_list");
		return mv;
	}
	
	/**
	 * 跳转到新增店铺评价页面
	 * */
	@RequestMapping("/addScoreShopLog")
	public String addScoreShopLog(Model m){
		//查询出所有的店铺名称，以供下拉框选择
		Shop shop = new Shop();
		List<Shop> listShop = shopService.getShopList(shop);
		m.addAttribute("listShop", listShop);
		return "pages/scoreShopLog/addScoreShopLog";
	}
	
	/**
	 * 保存新增订单信息
	 */
	@RequestMapping("/saveScoreShopLog")
	public String saveScoreShopLog(ScoreShopLog scoreShopLog){
		String upInfo = "";
		if(scoreShopLog.getAddTime() == null){
			Date date = new Date();
			scoreShopLog.setAddTime(date);;
		}
		try{
			scoreShopLogService.insertScoreShopLog(scoreShopLog);
			upInfo = "success";
		}catch(Exception e){
			e.printStackTrace();
			upInfo = "error";
		};
		return "redirect:scoreShopLogList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 修改店铺评价信息
	 */
	@RequestMapping("/updateScoreShopLog")
	public ModelAndView updateScoreShopLog(String id){
		ModelAndView mv = new ModelAndView();
		ScoreShopLog scoreShopLog = scoreShopLogService.selectScoreShopLogById(Integer.valueOf(id));//根据id查询对应的物流信息
		Shop shop = new Shop();
		List<Shop> listShop = shopService.getShopList(shop);//查询出所有的商品名称，以供下拉框选择
		mv.addObject("listShop", listShop);
		mv.addObject("scoreShopLog", scoreShopLog);
		mv.setViewName("pages/scoreShopLog/editScoreShopLog");
		return mv;
	}
	
	/**
	 * 修改页面点击保存
	 */
	@RequestMapping("/editScoreShopLog")
	public String editScoreShopLog(ScoreShopLog scoreShopLog){
		String upInfo = "";
		//System.out.println(article);
		try{
			scoreShopLogService.updateScoreShopLog(scoreShopLog);
			upInfo = "success";
		}catch(Exception e){
			e.printStackTrace();
			upInfo = "error";
		};
		return "redirect:scoreShopLogList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 删除订单信息
	 */
	@RequestMapping("/deleteScoreShopLog")
	@ResponseBody
	public Map<String,String> deleteScoreShopLog(String id){
		Map<String,String> m = new HashMap<>();
		try{
			scoreShopLogService.deleteScoreShopLog(Integer.valueOf(id));
			m.put("success", "删除成功！");
		}catch(Exception e){
			e.printStackTrace();
			m.put("error", "删除失败！");
		};
		return m;
	}
}

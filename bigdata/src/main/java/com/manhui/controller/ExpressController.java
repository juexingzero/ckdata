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

import com.manhui.model.Express;
import com.manhui.model.ScoreSiteLog;
import com.manhui.service.ExpressService;
import com.manhui.util.PageBean;

/**
 * 
 * Title: ExpressController
 * @description:物流信息控制层
 * @author LiuCheng
 *
 */
@Controller
public class ExpressController {
	
	@Autowired
	private ExpressService expressService; //物流mapper
	
	/**
	 * 订单信息列表
	 * */
	@RequestMapping("/expressList_{pageCurrent}_{pageSize}_{pageCount}")
	@ResponseBody
	public ModelAndView getExpressList(@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,
										@PathVariable Integer pageCount,String upInfo){
		ModelAndView mv = new ModelAndView();
		List<Express> list = new ArrayList<>();
		list = expressService.findAllExpress();//查询全部的物流信息
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = list.size();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		//int startIndex = pb.getStartIndex();
		//System.out.println(startIndex);
		Express express = new Express();
		express.setStart((pageCurrent - 1)*pageSize);
		express.setEnd(pageSize);
		List<Express> listmap = expressService.getPageExpress(express);//分页物流信息
		mv.addObject("express", listmap);
		mv.addObject("rows", rows);
		mv.addObject("pageCurrent", pageCurrent);
		mv.addObject("pageSize", pageSize);
		mv.addObject("pageCount", pageCount);
		mv.addObject("upInfo", upInfo);
		mv.setViewName("pages/express/express_list");
		return mv;
	}
	
	/**
	 * 跳转到新增运单页面
	 * */
	@RequestMapping("/addExpress")
	public String addExpress(Model m){
		List<Map> list = expressService.selectExpressName();//查询出所有的物流公司名称，以供下拉框选择
		m.addAttribute("expressName", list);
		return "pages/express/addExpress";
	}
	
	/**
	 * 保存新增订单信息
	 */
	@RequestMapping("/saveExpress")
	public String saveExpress(Express express){
		String upInfo = "";
		if(express.getSortid() == null){
			express.setSortid(0);
		}
		if(express.getState() == null){
			express.setState(1);
		}
		try{
			expressService.insertExpress(express);
			upInfo = "success";
		}catch(Exception e){
			e.printStackTrace();
			upInfo = "error";
		};
		return "redirect:expressList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 修改物流信息
	 */
	@RequestMapping("/updateExpress")
	public ModelAndView updateExpress(String id){
		ModelAndView mv = new ModelAndView();
		Express express = expressService.findExpressById(Integer.valueOf(id));//根据id查询对应的物流信息
		List<Map> list = expressService.selectExpressName();//查询出所有的物流公司名称，以供下拉框选择
		mv.addObject("expressName", list);
		mv.addObject("express", express);
		mv.setViewName("pages/express/editExpress");
		return mv;
	}
	
	/**
	 * 修改页面点击保存
	 */
	@RequestMapping("/editExpress")
	public String editExpress(Express express){
		String upInfo = "";
		//System.out.println(article);
		try{
			expressService.updateExpress(express);
			upInfo = "success";
		}catch(Exception e){
			e.printStackTrace();
			upInfo = "error";
		};
		return "redirect:expressList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 删除订单信息
	 */
	@RequestMapping("/deleteExpress")
	@ResponseBody
	public Map<String,String> deleteExpress(Express express){
		Map<String,String> m = new HashMap<>();
		express.setState(0);
		try{
			expressService.deleteExpressState(express);
			m.put("success", "删除成功！");
		}catch(Exception e){
			e.printStackTrace();
			m.put("error", "删除失败！");
		};
		return m;
	}
}

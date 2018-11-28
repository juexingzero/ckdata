package com.manhui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.model.ArticlePriceLog;
import com.manhui.service.ArticlePriceLogService;

/**
 * @ClassName: DailyDataController.java
 * @description:   日报数据管理控制器
 * @author: WangSheng
 * @date Create in 14:33 2018/1/27
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */

@Controller
public class DailyDataController {
	
	@Autowired
	private ArticlePriceLogService articlePriceLogService;

	/**
     * 价格历史变动加载页面
     *
     * @return
     */
	@RequestMapping("/pricehistory")
	public String pricehistory(){
		
		return "pages/DailyData/pricehistory";
		
	}
	
	/**
     * 根据名称查询商品价格变动基础信息
     *
     * @param model
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("/toPricehistroy")
	public String toPricehistroy(Model model,String name) throws ParseException{
		
		String n="%"+name+"%";
		
		List<ArticlePriceLog> aplList=articlePriceLogService.findArticlePL(n);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (ArticlePriceLog apl : aplList) {
			apl.setAddTime(sdf.format(sdf.parse(apl.getAddTime())));
		}
		model.addAttribute("aplList", aplList);
		
		return "pages/DailyData/topricehistory";
	}
	
	/**
     * 根据商品id查询商品价格变动信息(跳转页面)
     *
     * @param model
     * @param id
     * @return
     */
	@RequestMapping("toseeChart")
	public String toseeChart(Model model,Integer id){
		model.addAttribute("id",id);
		return "pages/DailyData/pricehistorycharts";
	}
	
	
	/**
     * 根据商品id查询商品价格变动信息(图表)
     *
     * @param model
     * @param aId
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("seeChart")
	@ResponseBody
	public Map<String, Object> seeChart(Model model,Integer aId) throws ParseException{
		Map<String, Object> map=new HashMap<>();
		List<ArticlePriceLog> aplList=articlePriceLogService.findAPLByaId(aId);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> sList=new ArrayList<>();
		List<BigDecimal> iList=new ArrayList<>();
		for (ArticlePriceLog apl : aplList) {
			sList.add(sdf.format(sdf.parse(apl.getAddTime())));
			iList.add(apl.getNewPrice());
		}

		String[] sj=new String[sList.size()];
		BigDecimal[] jg=new BigDecimal[iList.size()];
		sList.toArray(sj);
		iList.toArray(jg);
		map.put("sj", sj);
		map.put("jg", jg);
		return map;
	}
	
	
	/**
     * 根据商品id查询商品价格变动信息（数据）
     *
     * @param model
     * @param id
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("toseeDetail")
	public String toseeDetail(Model model,Integer id) throws ParseException{
		
		List<ArticlePriceLog> aplList=articlePriceLogService.findAPLByaIds(id);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ArticlePriceLog apl : aplList) {
			apl.setAddTime(sdf.format(sdf.parse(apl.getAddTime())));
		}
		model.addAttribute("aplList", aplList);
		return "pages/DailyData/pricedetail";
	}
	
}

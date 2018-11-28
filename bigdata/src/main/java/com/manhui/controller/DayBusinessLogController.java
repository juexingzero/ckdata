package com.manhui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.model.ArticlePriceLog;
import com.manhui.model.DayBusinessLog;
import com.manhui.service.DayBusinessLogService;

/**
 * @ClassName: DayBusinessLogController.java
 * @description:   行业数据管理控制器
 * @author: WangSheng
 * @date Create in 14:33 2018/1/30
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */

@Controller
public class DayBusinessLogController {
	
	@Autowired
	private DayBusinessLogService dayBusinessLogService;
	
	/**
     * 行业日统计加载页面
     *
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("industrystatistical")
	public String industrystatistical(){
		
		return "pages/DayBusinessLog/industrystatistical";
	}

	
	/**
     * 行业日统计(图表)
     *
     * @param model
     * @param aId
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("seebusiness")
	@ResponseBody
	public Map<String, Object> seeBusiness(Model model,Integer aId) throws ParseException{
		Map<String, Object> map=new HashMap<>();
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		yesterday=yesterday+"%";
		List<DayBusinessLog> dbllList=dayBusinessLogService.findDayBL(yesterday);
		List<String> tList=new ArrayList<>();
		List<Integer> sList=new ArrayList<>();
		List<Integer> dList=new ArrayList<>();
		for (DayBusinessLog dbl : dbllList) {
			tList.add(dbl.getCategory().getName());
			sList.add(dbl.getDealShopNum());
			dList.add(dbl.getDealArticleNum());
		}

		String[] name=new String[tList.size()];
		Integer[] sp=new Integer[sList.size()];
		Integer[] dd=new Integer[dList.size()];
		tList.toArray(name);
		sList.toArray(sp);
		dList.toArray(dd);
		map.put("name", name);
		map.put("sp", sp);
		map.put("dd", dd);
		return map;
	}

}

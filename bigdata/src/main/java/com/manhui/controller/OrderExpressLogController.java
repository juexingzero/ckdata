package com.manhui.controller;

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

import com.manhui.model.DayChannelLog;
import com.manhui.model.OrderExpressLog;
import com.manhui.service.OrderExpressLogService;

/**
 * @ClassName: OrderExpressLogController.java
 * @description:   物流数据管理控制器
 * @author: WangSheng
 * @date Create in 10:33 2018/1/31
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */

@Controller
public class OrderExpressLogController {

	@Autowired
	private OrderExpressLogService orderExpressLogService;
	
	/**
     * 物流日统计加载页面
     *
     * @return
     */
	@RequestMapping("logisticsstatistical")
	public String logisticsstatistical(){
		return "pages/OrderExpressLog/logisticsstatistical";
	}
	
	
	/**
     * 物流日统计信息(图表)
     *
     * @param model
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("seeexpress")
	@ResponseBody
	public Map<String, Object> seeExpress(Model model) throws ParseException{
		Map<String, Object> map=new HashMap<>();
		Calendar   cal   =   Calendar.getInstance();
		  cal.add(Calendar.DATE,   -1);
		  String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		  yesterday=yesterday+"%";
		List<OrderExpressLog> oelList=orderExpressLogService.findOrderEL(yesterday);
		List<String> sList=new ArrayList<>();
		List<Integer> iList=new ArrayList<>();
		for (OrderExpressLog oel : oelList) {
			sList.add(oel.getExpress().getName());
			iList.add(oel.getExpressNum());
		}

		String[] name=new String[sList.size()];
		Integer[] jg=new Integer[iList.size()];
		sList.toArray(name);
		iList.toArray(jg);
		System.out.println(iList);
		System.out.println(sList);
		map.put("name", name);
		map.put("jg", jg);
		return map;
	}
	
}

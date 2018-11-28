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
import com.manhui.model.DayChannelLog;
import com.manhui.service.DayChannelLogService;

/**
 * @ClassName: DayChannelLogController.java
 * @description:   渠道数据管理控制器
 * @author: WangSheng
 * @date Create in 09:33 2018/1/31
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */

@Controller
public class DayChannelLogController {

	@Autowired
	private DayChannelLogService dayChannelLogService;
	
	/**
     * 渠道日统计加载页面
     *
     * @return
     */
	@RequestMapping("channelstatistical")
	public String channelStatistical(){
		return "pages/DayChannelLog/channelstatistical";
	}
	
	
	/**
     * 渠道日统计信息(图表)
     *
     * @param model
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("seechannel")
	@ResponseBody
	public Map<String, Object> seeChannel(Model model) throws ParseException{
		Map<String, Object> map=new HashMap<>();
		Calendar   cal   =   Calendar.getInstance();
		  cal.add(Calendar.DATE,   -1);
		  String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		  yesterday=yesterday+"%";
		List<DayChannelLog> dclList=dayChannelLogService.findDayCL(yesterday);
		List<String> sList=new ArrayList<>();
		List<Integer> iList=new ArrayList<>();
		for (DayChannelLog dcl : dclList) {
			sList.add(dcl.getChannel().getName());
			iList.add(dcl.getOrderNum());
		}

		String[] name=new String[sList.size()];
		Integer[] jg=new Integer[iList.size()];
		sList.toArray(name);
		iList.toArray(jg);
		map.put("name", name);
		map.put("jg", jg);
		return map;
	}
	
}

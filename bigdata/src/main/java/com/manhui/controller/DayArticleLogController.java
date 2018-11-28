package com.manhui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.model.DayArticleLog;
import com.manhui.model.DayRegionLog;
import com.manhui.model.KeyWord;
import com.manhui.model.OrderExpressLog;
import com.manhui.service.DayArticleLogService;
import com.manhui.service.DayRegionLogService;
import com.manhui.service.KeyWordService;

/**
 * @ClassName: DayArticleLogController.java
 * @description:   商品日 统计控制器
 * @author: WangSheng
 * @date Create in 14:33 2018/02/01
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */

@Controller
public class DayArticleLogController {

	@Autowired
	private DayArticleLogService dayArticleLogService;
	
	/**
     * 商品日 统计加载页面
     *
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("goodsstatistical")
	public String goodsStatistical(){
		
		return "pages/DayArticleLog/goodsstatistical";
	}
	
	
	/**
     * 商品日 统计信息(图表)
     *
     * @param model
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("seearticle")
	@ResponseBody
	public Map<String, Object> seeArticle(Model model) throws ParseException{
		Map<String, Object> map=new HashMap<>();
		Calendar   cal   =   Calendar.getInstance();
		  cal.add(Calendar.DATE,   -1);
		  String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		  yesterday=yesterday+"%";
		List<DayArticleLog> dalList=dayArticleLogService.findDayAL(yesterday);
		List<String> sList=new ArrayList<>();
		List<Integer> iList=new ArrayList<>();
		for (DayArticleLog dal : dalList) {
			sList.add(dal.getArticle().getTitle());
			iList.add(dal.getArticleNum());
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

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
import com.manhui.model.DayOrderLog;
import com.manhui.service.DayOrderLogService;

/**
 * @ClassName: DayOrderLogController.java
 * @description:   订单数据管理控制器
 * @author: WangSheng
 * @date Create in 13:33 2018/1/30
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */

@Controller
public class DayOrderLogController {
	
	
	@Autowired
	private DayOrderLogService dayOrderLogService;
	
	@RequestMapping("orderstatistical")
	public String orderstatistical(Model model){
		Calendar   cal   =   Calendar.getInstance();
		  cal.add(Calendar.DATE,   -1);
		  String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		  Calendar   ca   =   Calendar.getInstance();
		  ca.add(Calendar.DATE,   -2);
		  String qitian=new SimpleDateFormat( "yyyy-MM-dd ").format(ca.getTime());
		  qitian=qitian+"%";
		  yesterday=yesterday+"%";
		Integer order=dayOrderLogService.findOrderNum(yesterday);
		BigDecimal money=dayOrderLogService.findOrderMoney(yesterday);
		
		Integer orderadd=dayOrderLogService.findOrderNum(qitian);
		BigDecimal moneyadd=dayOrderLogService.findOrderMoney(qitian);
		if(order!=null&&money!=null&&orderadd!=null&&moneyadd!=null){
		//订单金额增长率
		if(money.doubleValue()>=moneyadd.doubleValue()){
		double moneyad=new BigDecimal((money.doubleValue()-moneyadd.doubleValue())/money.doubleValue()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		model.addAttribute("moneyad", moneyad);
		}else{
		double moneyreduce=new BigDecimal((moneyadd.doubleValue()-money.doubleValue())/money.doubleValue()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		model.addAttribute("moneyreduce", moneyreduce);
		}
		//订单增长率
		if(order>=orderadd){
			double orderad=new BigDecimal((double)(order-orderadd)/(double)orderadd*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			model.addAttribute("orderad", orderad);
		}else{
			double orderreduce=new BigDecimal((double)(orderadd-order)/(double)order*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			model.addAttribute("orderreduce", orderreduce);
		}
		double avg=new BigDecimal(money.doubleValue()/(float)order).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		model.addAttribute("avg", avg);
		model.addAttribute("order",order);
		model.addAttribute("money", money);
		
		}else{
			System.out.println("暂时没有数据");
			model.addAttribute("state", 1);
		}
		
		
		//问题：1.当没有前天数据时
		//问题：2.当昨天订单金额减去前天金额为负数时
		
		
		return "pages/DayOrderLog/orderstatistical";
	}
	
	
	/**
     * 根据商品id查询商品价格变动信息(图表)
     *
     * @param model
     * @param aId
     * @return
	 * @throws ParseException 
     */
	@RequestMapping("toorderstatistical")
	@ResponseBody
	public Map<String, Object> toOrderStatistical(Integer aId) throws ParseException{
		Map<String, Object> map=new HashMap<>();
		Calendar   cal   =   Calendar.getInstance();
		  cal.add(Calendar.DATE,   -1);
		  String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		  yesterday=yesterday+"%";
		List<DayOrderLog> dolList=dayOrderLogService.findDayOL(yesterday);
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> sList=new ArrayList<>();
		List<BigDecimal> iList=new ArrayList<>();
		for (DayOrderLog dol : dolList) {
			sList.add(sdf.format(sdf.parse(dol.getAddTime())));
			iList.add(dol.getTotalAmount());
		}

		String[] sj=new String[sList.size()];
		BigDecimal[] jg=new BigDecimal[iList.size()];
		sList.toArray(sj);
		iList.toArray(jg);
		map.put("sj", sj);
		map.put("jg", jg);
		
		return map;
	}
	

}

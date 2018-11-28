package com.manhui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.model.Article;
import com.manhui.model.ArticlePriceLog;
import com.manhui.model.AutoTask;
import com.manhui.model.DayAllLog;
import com.manhui.model.DayArticleLog;
import com.manhui.model.DayCategoryLog;
import com.manhui.model.DayChannelLog;
import com.manhui.model.DayRegionLog;
import com.manhui.model.DayShopLog;
import com.manhui.model.Express;
import com.manhui.model.KeyWord;
import com.manhui.model.Order;
import com.manhui.model.OrderExpressLog;
import com.manhui.model.Shop;
import com.manhui.service.ArticlePriceLogService;
import com.manhui.service.ArticleService;
import com.manhui.service.AutoTaskService;
import com.manhui.service.BaseRegionService;
import com.manhui.service.CategoryService;
import com.manhui.service.ChannelService;
import com.manhui.service.DayAllLogService;
import com.manhui.service.DayArticleLogService;
import com.manhui.service.DayCategoryLogService;
import com.manhui.service.DayChannelLogService;
import com.manhui.service.DayOrderLogService;
import com.manhui.service.DayRegionLogService;
import com.manhui.service.DayShopLogService;
import com.manhui.service.ExpressService;
import com.manhui.service.KeyWordService;
import com.manhui.service.OrderExpressLogService;
import com.manhui.service.OrderService;
import com.manhui.service.ScoreRegionLogService;
import com.manhui.service.ScoreSiteLogService;
import com.manhui.service.ShopService;
import com.manhui.service.SiteService;
import com.manhui.util.JsonData;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

/**
 * @ClassName: ResourceDataController.java
 * @description: 大屏幕显示抓取数据控制器
 * @author: Jiangxiaosong
 * @date Create in 14:33 2018/2/2
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Controller
public class ResourceDataController {

	@Autowired 
	private DayCategoryLogService dayCategoryLogService;
	
	@Autowired
	private DayChannelLogService dayChannelLogService;
	
	@Autowired
	private DayArticleLogService dayArticleLogService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ArticlePriceLogService articlePriceLogService;
	
	@Autowired
	private DayShopLogService dayShopLogService;
	
	@Autowired
	private ScoreRegionLogService scoreRegionLogService;
	
	@Autowired
	private ScoreSiteLogService scoreSiteLogService;
	
	@Autowired
	private OrderExpressLogService orderExpressLogService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BaseRegionService baseRegionService; 
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ExpressService expressService;
	
	@Autowired
	private DayOrderLogService dayOrderLogService;
	
	@Autowired
	private DayAllLogService dayAllLogService;

	@Autowired
	private DayRegionLogService dayRegionLogService;
	
	@Autowired
	private KeyWordService keyWordService;
	
	@Autowired
	private SpiderService spiderService;
	
	@Autowired
	private AutoTaskService autoTaskService;
	
	/**
	 * 获取显示内容
	 * 
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("get_day_all_log")
	@ResponseBody
	public JSONObject getDayAllLog(Model model) throws ParseException {
		JSONObject jsons = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(new Date());
		Date date = sdf.parse(s);
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(date);
		if(dal != null){
			String result = String.valueOf(dal.getData());
			jsons = (JSONObject) JSONValue.parse(result);
		}
		return jsons;
	}

	/**
	 * 获取京东接口测试
	 * 
	 * @param model
	 * @return
	 * @throws JdException
	 * @throws ParseException
	 */
	// @GetMapping("getJDTest")
	// @ResponseBody
	// public JSONObject getJDTest(Model model) throws JdException {
	// JSONObject jsons = new JSONObject();
	//
	// JdClient client = new
	// DefaultJdClient("https://gw.api.360buy.com/routerjson", "",
	// "A1C35F5999E85E3248E0BC69D81D6611", "0f0c2fcb31244a508ee371cd442597a3");
	//
	// NewWareBaseproductGetRequest request=new NewWareBaseproductGetRequest();
	//
	// String baseFields = "name,model,url,shopName";
	//
	// request.setBasefields(baseFields);
	//
	// NewWareBaseproductGetResponse response=client.execute(request);
	//
	// System.out.println("Url = "+response.getUrl());
	// System.out.println("Msg = "+response.getMsg());
	// System.out.println("Code = "+response.getCode());
	// System.out.println("ZhDesc="+response.getZhDesc());
	// jsons.put("jdresult", response.getListproductbaseResult());
	//
	// return jsons;
	// }

	/**
	 * 查询当天订单数/交易总额/商品总数
	 * 
	 * @return
	 */
	@GetMapping("get_day_sum")
	@ResponseBody
	public List<String> getDaySum(Model model) {

		// 获取日期
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		String today = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
		Integer dayOrderNum = dayOrderLogService.findOrderNum(today);
		BigDecimal dayOrderMon = dayOrderLogService.findOrderMoney(today);
		List<ArticlePriceLog> articlePriceLog = articlePriceLogService.findArticlePriceLogByDay(today);
		Integer articleNum = 0;
		for (ArticlePriceLog apl : articlePriceLog) {
			articleNum = articleNum + 1;
		}

		List<String> result = new ArrayList<>();
		if (dayOrderNum != null) {
			result.add(dayOrderNum.toString());
		} else {
			result.add("0");
		}
		if (dayOrderMon != null) {
			result.add(dayOrderMon.toString());
		} else {
			result.add("0");
		}
		result.add(articleNum.toString());

		return result;
	}

	/**
	 * 查询当月订单数/交易总额/商品总数
	 * 
	 * @return
	 */
	@GetMapping("get_month_sum")
	@ResponseBody
	public List<String> getMonthSum(Model model) {

		// 获取当前月
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String month = sdf.format(new Date());
		// 查询当月总收入
		BigDecimal monthsally = dayOrderLogService.findOrderMoneyByMonth(month);
		// 查询当月订单数
		Integer monthOrderNum = dayOrderLogService.findOrderNumByMonth(month);
		// 获取当月商品数量
		Integer articleNum = 0;
		List<ArticlePriceLog> articlePriceLog = articlePriceLogService.findArticlePriceLogByMonth(month);
		for (ArticlePriceLog apl : articlePriceLog) {
			articleNum = articleNum + 1;
		}

		List<String> result = new ArrayList<>();
		if (monthOrderNum != null) {
			result.add(monthOrderNum.toString());
		} else {
			result.add("0");
		}
		if (monthsally != null) {
			result.add(monthsally.toString());
		} else {
			result.add("0");
		}
		result.add(articleNum.toString());

		return result;
	}

	/**
	 * 查询当年订单数/交易总额/商品总数
	 * 
	 * @return
	 */
	@GetMapping("get_year_sum")
	@ResponseBody
	public List<String> getYearSum(Model model) {

		// 获取当前月
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
		String year = sdf.format(new Date());
		// 查询当月总收入
		BigDecimal yearSally = dayOrderLogService.findOrderMoneyByYear(year);
		// 查询当月订单数
		Integer yearOrderNum = dayOrderLogService.findOrderNumByYear(year);
		// 获取当月商品数量
		Integer articleNum = 0;
		List<ArticlePriceLog> articlePriceLog = articlePriceLogService.findArticlePriceLogByYear(year);
		for (ArticlePriceLog apl : articlePriceLog) {
			articleNum = articleNum + 1;
		}

		List<String> result = new ArrayList<>();
		if (yearOrderNum != null) {
			result.add(yearOrderNum.toString());
		} else {
			result.add("0");
		}
		if (yearSally != null) {
			result.add(yearSally.toString());
		} else {
			result.add("0");
		}
		result.add(articleNum.toString());

		return result;
	}
	/**
	 * 自动任务
	 * @param model
	 * @return
	 */
@Scheduled(cron="0 01 00 ? * * ")
public void automatic(){
		
		//更改自动任务状态为执行中
		AutoTask at = new AutoTask();
		at.setId(1);
		at.setState(2);
		autoTaskService.updateAutoTask(at);
		
		spiderService.JDGather();
		spiderService.TMtGather();
		
		//统计基础数据到日报表
		CountBaseData();
		
		//店铺销售排行1
		List<DayShopLog> dayShopLogs = dayShopLogService.findDayShopLogSortBytotalAmount();
		
		//商品销售排行1
		List<DayArticleLog> dals = dayArticleLogService.findDALDByMoney();
																								
		//地区销售排行1
		List<DayRegionLog> drl=dayRegionLogService.findDayRegionLog();
		
		//销售地区热度1
		List<DayRegionLog> drlrd=dayRegionLogService.findDayRegionLog();
				
		//热门关键词1
		List<KeyWord> kw=keyWordService.findKeyWord();
		
		//最近交易1
		List<Order> orderlist = orderService.getOrderList(new Order());
		
		// 商品销售额 和 平台店铺数1
		List<DayChannelLog> dcl = dayChannelLogService.findAllDayChannelLog();
		
		//获取平台销售额
		List<DayChannelLog> dclx=dayChannelLogService.findDayChannelLog();
		
		// 类目店铺数 和 类目商品数
		List<DayCategoryLog> dctlse = dayCategoryLogService.findAllDayCategryLog();
		
		//包裹数
		List<OrderExpressLog> oels = orderExpressLogService.findOrderExpressNum();
		
		//类目价格趋势
		List<DayCategoryLog> apls = dayCategoryLogService.findDayCategoryLog();
		
		//平台店铺数
		List<DayChannelLog> daydp=dayChannelLogService.findDayCh();
		
		JSONObject bigData = JsonData.saveJson(dals, dayShopLogs, drl, orderlist, dclx, daydp, oels, drlrd, kw, dctlse, apls, dcl);
		
		DayAllLog data = new DayAllLog();
		data.setAddTime(new Date());
		data.setData(bigData.toString());
		//存入数据库
		dayAllLogService.saveDayAllLog(data);
		
		//更改自动任务状态为已完成
		AutoTask autoTask = new AutoTask();
		autoTask.setId(1);
		autoTask.setState(3);
		autoTaskService.updateAutoTask(at);
		
	}
	
	
	
	//采集基础数据统计
	public String CountBaseData(){
		
		//获取商品基础数据
		List<Article> articleList = articleService.getAllArticle();
		//将基础数据存入日报表中
		for(Article data : articleList){
			DayArticleLog dal = new DayArticleLog();
			dal.setAddTime(new Date());
			dal.setAllTotalAmount(data.getQuantity().multiply(data.getPrice()));
			dal.setArticleId(data.getId());
			dal.setArticleNum(data.getQuantity().divide(BigDecimal.valueOf(0.2)).intValue());
			dayArticleLogService.insertDayArticleLogData(dal);
		}
		
		//存入类目日报表
		DayCategoryLog dcl = new DayCategoryLog();
		dcl.setAddTime(new Date());
		dcl.setCategoryId(9);
		dcl.setShopNum(shopService.getShopCount());
		dcl.setArticleNum(articleService.getArticleCount());
		dcl.setOrderNum(orderService.getCountOrder());
		Article totalAmount = articleService.getAllTotalAmount();
		dcl.setTotalAmount(totalAmount.getPrice().multiply(BigDecimal.valueOf(dcl.getOrderNum())));
		dayCategoryLogService.insertDayCategoryLog(dcl);
		
		
		//存入平台日报表
		DayChannelLog dclx = new DayChannelLog();
		dclx.setAddTime(new Date());
		dclx.setChannelId(4);
		dclx.setShopNum(shopService.getShopCount());
		dclx.setArticleNum(articleService.getArticleCount());
		dclx.setOrderNum(orderService.getCountOrder());
		dclx.setTotalAmount(totalAmount.getPrice().multiply(BigDecimal.valueOf(dclx.getOrderNum())));
		dayChannelLogService.insertDayChannelLog(dclx);
		
		
		//存入订单数据
		for(Article data : articleList){
			Order order = new Order();
			order.setSellTime(new Date());
			order.setArticleId(data.getId());
			order.setShopId(data.getShopId());
			order.setSellPrice(data.getPrice());
			order.setQuantity(data.getQuantity().intValue());
			order.setState(Byte.valueOf("1"));
			orderService.insertOrder(order);
		}
		
		
		//存入物流记录表
		List<Express> express = expressService.findAllExpress();
		for(Express data : express){
			OrderExpressLog oel = new OrderExpressLog();
			oel.setAddTime(new Date());
			oel.setExpressId(data.getId());
			oel.setExpressNum(expressService.findAllExpressCount());
			orderExpressLogService.insertOrderExpress(oel);
		}
		
		
		//存入日商品报表
		List<Shop> shop = shopService.findShopList();
		for(Shop data : shop){
			DayShopLog dsl = new DayShopLog();
			dsl.setAddTime(new Date());
			dsl.setShopId(data.getId());
			dsl.setOrderNum(orderService.getCountOrder());
			dsl.setTotalAmount(totalAmount.getPrice().multiply(BigDecimal.valueOf(dsl.getOrderNum())));
			dayShopLogService.insertDayShopLog(dsl);
		}
		
		
		//保存到地区日统计表
		List<Order> order = orderService.getAllOrder();
		for(Order data : order){
			DayRegionLog drl = new DayRegionLog();
			drl.setAddTime(new Date());
			drl.setRegionId(2815);
			drl.setOrderId(data.getId());
			drl.setOrderNum(orderService.getCountOrder());
			drl.setTotalAmount(data.getSellPrice().multiply(BigDecimal.valueOf(drl.getOrderNum())));
			dayRegionLogService.insertDayRegionLog(drl);
		}
		
		return "success";
	}
	
}

package com.manhui.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.manhui.model.BaseUser;
import com.manhui.model.Channel;
import com.manhui.model.Dashboard;
import com.manhui.model.DayChannelLog;
import com.manhui.model.DayOrderLog;
import com.manhui.model.Express;
import com.manhui.model.Order;
import com.manhui.model.ScoreRegionLog;
import com.manhui.cacheManage.CacheManager;
import com.manhui.model.Article;
import com.manhui.model.ArticlePriceLog;
import com.manhui.model.AutoTask;
import com.manhui.model.BasePopedom;
import com.manhui.model.BaseRegion;
import com.manhui.service.BaseUserService;
import com.manhui.service.ChannelService;
import com.manhui.service.DayChannelLogService;
import com.manhui.service.DayOrderLogService;
import com.manhui.service.ExpressService;
import com.manhui.service.OrderService;
import com.manhui.service.ScoreRegionLogService;
import com.manhui.service.ShopService;
import com.manhui.util.PlanResourcesUtil;
import com.manhui.service.ArticlePriceLogService;
import com.manhui.service.ArticleService;
import com.manhui.service.AutoTaskService;
import com.manhui.service.BasePopedomService;
import com.manhui.service.BaseRegionService;


/**
 * @ClassName: IndexController.java
 * @description:   首页相关控制器
 * @author: Jiangxiaosong
 * @date Create in 14:33 2018/1/26
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Controller
public class IndexController {

	@Autowired
	private BaseUserService baseUserService;
	
	@Autowired
	private BasePopedomService menuService;
	
	@Autowired
	private DayOrderLogService dayOrderLogService;
	
	@Autowired
	private ShopService shopSerivce;
	
	@Autowired
	private ArticlePriceLogService articlePriceLogService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ExpressService expressService;
	
	@Autowired
	private DayChannelLogService dayChannelLogService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ScoreRegionLogService scoreRegionLogService;
	
	@Autowired
	private BaseRegionService baseRegionService;
	
	@Autowired
	private AutoTaskService autoTaskService;
	
    /**
     * 登录跳转
     *
     * @param 
     * @return
     */
    @RequestMapping("/admin")
    public ModelAndView loginGet(String flag) {
    	ModelAndView mv = new ModelAndView("login");
    	if(flag != null && flag.equals("timeout")){
    		mv.addObject("error", "登陆超时，请重新登陆！");  
    	}
    	return mv;
    }

    /**
     * 登录
     *
     * @param user
     * @param httpSession
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView loginPost(String userName, String passWord, HttpSession session) {
    	ModelAndView mv = new ModelAndView();
    	BaseUser user = new BaseUser();
    	user.setUserName(userName);
    	user.setPassword(passWord);
		
    	BaseUser baseUser = baseUserService.findByUserAndPassword(user) ;
    	
		if(baseUser != null){
			session.setAttribute("user", user);
			
			//通过用户的一些信息查询机构部门以及公司员工，并将所查询结果存入缓存
			new PlanResourcesUtil(user);
			
			//查询用户菜单信息
			List<BasePopedom> menus = getUserMenus(baseUser);
			
            mv.setViewName("index");
            mv.addObject("menus", menus);
            mv.addObject("user", user);
            return mv;
		}else{
			mv.setViewName("login");
            mv.addObject("error", "用户名或密码错误，请重新登录！");  
            return mv;
		}
    }

	/**
     * 仪表板页面
     *
     * @param 
     * @return
     */
    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
    	
    	//获取当前月
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String month = sdf.format(new Date());
		
		//获取当前年
		SimpleDateFormat sdfy = new SimpleDateFormat("YYYY");
		String year = sdfy.format(new Date());
		
		//查询当月总收入
		BigDecimal monthsally = dayOrderLogService.findOrderMoneyByMonth(month);
		if(monthsally == null){
			monthsally = new BigDecimal("0.00");
		}
		
		//查询当月订单数
		Integer monthOrderNum = dayOrderLogService.findOrderNumByMonth(month);
		if(monthOrderNum == null){
			monthOrderNum = 0;
		}
		
		//获取全部店铺数量
		Integer shopCount = shopSerivce.getShopCount();
		
		//获取当月商品数量
		Integer articleNum = 0;
		List<ArticlePriceLog> articlePriceLog = articlePriceLogService.findArticlePriceLogByMonth(month);
		for(ArticlePriceLog apl : articlePriceLog){
			articleNum = articleNum + 1;
		}
		
		//获取当月每天的订单数
		List<DayOrderLog> orderNumlist = dayOrderLogService.findDayOrderNumByMonth(month);
		List<String> orderNumJson = new ArrayList<>();
		for(DayOrderLog dol : orderNumlist){
			String json = "gd("+year+","+month+","+dol.getAddTime()+"),"+dol.getOrderNum();
			orderNumJson.add(json);
		}
		
		//获取当月每天的订单交易金额
		List<DayOrderLog> orderMoneylist = dayOrderLogService.findDayOrderMoneyByMonth(month);
		List<String> orderMoneyJson = new ArrayList<>();
		for(DayOrderLog dol : orderMoneylist){
			String json = "gd("+year+","+month+","+dol.getAddTime()+"),"+dol.getTotalAmount();
			orderMoneyJson.add(json);
		}
		
		//获取当天订单数/交易总额/商品总数
		//获取日期
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,0);
		String today = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		Integer dayOrderNum = dayOrderLogService.findOrderNum(today);
		BigDecimal dayOrderMon = dayOrderLogService.findOrderMoney(today);
		List<ArticlePriceLog> articlePriceLoglist = articlePriceLogService.findArticlePriceLogByDay(today);
		Integer articlesNum = 0;
		for(ArticlePriceLog apl : articlePriceLoglist){
			articlesNum = articlesNum + 1;
		}
		List<String> DayInfo = new ArrayList<>();
		if(dayOrderNum != null){
			DayInfo.add(dayOrderNum.toString());
		}else{
			DayInfo.add("0");
		}
		if(dayOrderMon != null){
			DayInfo.add(dayOrderMon.toString());
		}else{
			DayInfo.add("0");
		}
		DayInfo.add(articlesNum.toString());
		
		//获取最新商品信息
		List<Article> artList = new ArrayList<>();
		//获取订单排名
		List<Order> orderlist = orderService.getTopOrder();
		if(orderlist != null && orderlist.size() > 0){
			for(Order order : orderlist){
				Integer articleId = order.getArticleId();
				Article art = articleService.selectById(articleId);
				artList.add(art);
			}
		}
		
		//获取最新物流信息
		List<Express> expressList = new ArrayList<>();
		//获取订单排名
		List<Order> expressOrderlist = orderService.getTopExpressOrder();
		if(expressOrderlist != null && expressOrderlist.size() > 0){
			for(Order order : expressOrderlist){
				Integer expressId = order.getExpressId();
				Express express = expressService.findExpressById(expressId);
				expressList.add(express);
			}
		}
		
		//获取渠道任务信息
		List<Channel> channelList = new ArrayList<>();
		//获取日渠道记录
		List<DayChannelLog> dcls = dayChannelLogService.findAllDayChannelLog();
		if(dcls != null && dcls.size() > 0){
			for(DayChannelLog dcl : dcls){
				Integer channelId = dcl.getChannelId();
				Channel channel = channelService.getChannelById(channelId);
				channelList.add(channel);
			}
		}
		
		//获取行政区划数据
		List<BaseRegion> regionList = new ArrayList<>();
		//获取行政记录表
		List<ScoreRegionLog> srls = scoreRegionLogService.findScoreRegionLogSortByScore();
		if(srls != null && srls.size() > 0){
			for(ScoreRegionLog srl : srls){
				Integer regionId = srl.getRegionId();
				BaseRegion region = baseRegionService.findBaseRegionById(regionId);
				regionList.add(region);
			}
		}
		
		//数据采集任务
		List<AutoTask> autoTaskList = autoTaskService.findAllAutoTask();
		
		
		Dashboard dashboard = new Dashboard(year, month, monthsally, monthOrderNum, shopCount, articleNum);
		dashboard.setOrderNum(orderNumJson);
		dashboard.setOrderMoney(orderMoneyJson);
		dashboard.setDayInfo(DayInfo);
		dashboard.setArtList(artList);
		dashboard.setExpressList(expressList);
		dashboard.setChannelList(channelList);
		dashboard.setRegionList(regionList);
		dashboard.setAutoTaskList(autoTaskList);
		
		model.addAttribute("dashboard", dashboard);
    	return "dashboard";
    }

    /**
     * 登出
     * @param httpSession
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        CacheManager.clearAll();
        return "login";
    }
    
    /**
	 * @description: 登陆成功后，查询用户对应菜单信息
	 * @param paramMap
	 * @return 
	 * @throws 
	 */
	private List<BasePopedom> getUserMenus(BaseUser user) {
		List<BasePopedom> menuList = menuService.findByUser(user.getId());
		List<BasePopedom> menus = new ArrayList<BasePopedom>();
		if(menuList != null){
			for (BasePopedom object : menuList) {
				//查询出来的都是根菜单
				BasePopedom menu = new BasePopedom();
				menu.setId(object.getId());
				menu.setName(object.getName());
				menu.setCode(object.getCode());
				menu.setPid(object.getPid());
				menu.setType(object.getType());
				menu.setEvent(object.getEvent());
				menu.setIcon(object.getIcon());
				menu.setRemark(object.getRemark());
				if(menu != null)
					menus.add(menu);
			}
		}
		
		List<BasePopedom> newMenus = new ArrayList<BasePopedom>();
		for (BasePopedom menu : menus) {
			String parentIds = String.valueOf(menu.getPid());
			//String[] idArr = parentIds.split(",");
			List<BasePopedom> subMenus = new ArrayList<BasePopedom>();
			if(parentIds != null && !"null".equals(parentIds)){
				//不是根菜单即按照原逻辑编写
				for (BasePopedom subMenu : menus) {
					if(subMenu.getPid().equals(menu.getId()))
						subMenus.add(subMenu);
				}
			}else{
				//是根菜单则查询下面几级菜单
				List<BasePopedom> subMenuList =  menuService.findByPId(menu.getId());
				for(BasePopedom subMenu : subMenuList){
					List<BasePopedom> subSubMenuList = menuService.findByPId(subMenu.getId());
					if(subSubMenuList != null && subSubMenuList.size() > 0){
						//再读取一次
						subMenu.setSubmenus(subSubMenuList);
						subMenus.add(subMenu);
					}else{
						subMenus.add(subMenu);
					}
				}
			}
			menu.setSubmenus(subMenus);
			newMenus.add(menu);
		}
		return newMenus;
	}
}

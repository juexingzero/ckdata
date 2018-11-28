package com.manhui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.model.BaseMobileUser;
import com.manhui.model.BaseObject;
import com.manhui.model.BaseUser;
import com.manhui.model.DayBusinessLog;
import com.manhui.model.OrderExpressOtherLog;
import com.manhui.model.ReportLogin;
import com.manhui.service.ArticleService;
import com.manhui.service.BaseMobileUserService;
import com.manhui.service.CompanyService;
import com.manhui.service.DayBusinessLogService;
import com.manhui.service.DayOrderLogService;
import com.manhui.service.OrderExpressLogService;
import com.manhui.service.OrderExpressOtherLogService;
import com.manhui.service.OrderService;
import com.manhui.service.ReportLoginService;
import com.manhui.service.ShopService;

/**
 * @ClassName: MobileLoginController.java
 * @description:   移动端登录控制器
 * @author: Jiangxiaosong
 * @date Create in 14:33 2018/3/20
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Controller
public class MobileLoginController {
	
	@Autowired
	private BaseMobileUserService baseMobileUserService;
	
	@Autowired
	private CompanyService companySerivce;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private DayOrderLogService dayOrderLogService;
	
	@Autowired
	private OrderExpressOtherLogService orderExpressOtherLogService;
	
	@Autowired
	private DayBusinessLogService dayBusinessLogService;
	
	@Autowired
	private ReportLoginService reportLoginService;

	//移动端登录
	@RequestMapping("mobile_login")
	@ResponseBody
	public BaseMobileUser mobileLogin(String userName, String passWord, HttpSession session){
		
		BaseMobileUser user = new BaseMobileUser();
		user.setName(userName);
		user.setPassword(passWord);
		
		BaseMobileUser baseUser = baseMobileUserService.findUserByNameAndPassword(user);
		if(baseUser != null){
			baseUser.setLoginFlag("success");
			session.setAttribute("user", baseUser);
		}else{
			baseUser = new BaseMobileUser();
			baseUser.setLoginFlag("failed");
		}
		return baseUser;
	}
	
	
	
	//登录后获取数据
	@RequestMapping("mobile_getdata")
	@ResponseBody
	public BaseMobileUser getdata(){
		BaseMobileUser baseUser = new BaseMobileUser();
		baseUser.setCompanyNum(companySerivce.getCompanyCount());
		baseUser.setShopNum(shopService.findShopCount());
		baseUser.setArticleNum(articleService.getArticleCount());
		baseUser.setSellNum(orderService.getCountOrder());
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
		baseUser.setSellPrice(dayOrderLogService.findOrderMoneyByYear(sdf.format(new Date())));
		System.out.println("进入");
		OrderExpressOtherLog oeol = orderExpressOtherLogService.findnputnumAnd();
		System.out.println("出来");
		baseUser.setInputNum(oeol.getInputNum());
		baseUser.setOutputNum(oeol.getOutputNum());
		DayBusinessLog dbl = dayBusinessLogService.findAmount();
		baseUser.setSellTotalAmount(dbl.getPerAmount());
		baseUser.setBuyTotalAmount(dbl.getTotalAmount());
		return baseUser;
	}
	
	
	//模拟登录保存
	@RequestMapping("mobile_simu_login_save")
	@ResponseBody
	public BaseObject saveReportLogin(String name,String password,HttpSession session){
		BaseObject bo = new BaseObject();
		BaseMobileUser user = (BaseMobileUser)session.getAttribute("user");
		if(user != null){
			ReportLogin rl = new ReportLogin();
			rl.setName(name);
			rl.setPassword(password);
			rl.setMobileUserId(user.getId());
			reportLoginService.saveReportLogin(rl);
			bo.setFlag("success");
		}else{
			bo.setFlag("failed");
		}
		return bo;
	}
	
	
	
	//展示模拟登录账号
	@RequestMapping("mobile_simu_login_show")
	@ResponseBody
	public ReportLogin showReportLogin(HttpSession session){
		BaseMobileUser user = (BaseMobileUser)session.getAttribute("user");
		ReportLogin rllist = reportLoginService.findReportLoginOrderByTime(user.getId());
		return rllist;
	}
}

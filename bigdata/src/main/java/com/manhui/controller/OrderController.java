package com.manhui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.manhui.model.Article;
import com.manhui.model.BaseObject;
import com.manhui.model.Express;
import com.manhui.model.Order;
import com.manhui.model.Shop;
import com.manhui.service.ArticleService;
import com.manhui.service.ExpressService;
import com.manhui.service.OrderService;
import com.manhui.service.ShopService;
import com.manhui.util.FileUploadUtil;
import com.manhui.util.PageBean;

/**
 * 
 * Title: OrderController
 * @description:订单信息控制层
 * @author LiuCheng
 *
 */
@Controller
public class OrderController {
	
	@Autowired
	private ArticleService articleService; //商品mapper
	
	@Autowired
	private OrderService orderService; //订单mapper
	
	@Autowired
	private ExpressService expressService; //物流mapper
	
	@Autowired
	private ShopService shopService; //店铺mapper
	
	/**
	 * 订单信息列表
	 * */
	@RequestMapping("/orderList_{pageCurrent}_{pageSize}_{pageCount}")
	@ResponseBody
	public ModelAndView getOrderList(@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,
										@PathVariable Integer pageCount,String upInfo){
		ModelAndView mv = new ModelAndView();
		List<Order> list = new ArrayList<>();
		Order order = new Order();
		list = orderService.getOrderList(order);//查询全部的订单
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = list.size();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		//int startIndex = pb.getStartIndex();
		//System.out.println(startIndex);
		order.setStart((pageCurrent - 1)*pageSize);
		order.setEnd(pageSize);
		List<Order> listmap = orderService.getPageOrder(order);
		for(Order m : listmap){
			Integer articleId = Integer.parseInt(String.valueOf(m.getArticleId()));//object转Integer
			Article s = articleService.selectById(articleId);
			m.setArticle(s);;//查出订单对应的商品
			Integer shopId = Integer.parseInt(String.valueOf(m.getShopId()));
			Shop shop = shopService.findNameById(shopId);
			m.setShop(shop);;//查询订单对应的店铺信息
			if(m.getExpressId() != null){
				Integer expressId = Integer.parseInt(String.valueOf(m.getExpressId()));
				Express express = expressService.findExpressById(expressId);
				m.setExpress(express);//查询订单对应的物流信息
			}else{
				m.setExpress(null);//查询订单对应的物流信息
			}
		}
		
		mv.addObject("order", listmap);
		mv.addObject("rows", rows);
		mv.addObject("pageCurrent", pageCurrent);
		mv.addObject("pageSize", pageSize);
		mv.addObject("pageCount", pageCount);
		mv.addObject("upInfo", upInfo);
		mv.setViewName("pages/order/order_list");
		return mv;
	}
	
	/**
	 * 跳转到新增订单页面
	 * */
	@RequestMapping("/addOrder")
	public String addOrder(Model m){
		Article article = new Article();
		List<Article> listShop = articleService.getArticleList(article);
		m.addAttribute("listArticle", listShop);//查出所有商品，以供选择(在页面异步获取店铺名称)
		List<Express> listExpress = expressService.findAllExpress();
		m.addAttribute("listExpress", listExpress);//查出所有物流公司，以供选择
		return "pages/order/addOrder";
	}
	
	/**
	 * 添加订单信息页面，异步获取商品对应的店铺名称
	 */
	@RequestMapping("/getShopByArticleId")
	@ResponseBody
	public Shop getShopByArticleId(String id){
		Integer articleId = Integer.valueOf(id);
		//System.out.println(pId);
		Article article = articleService.selectById(articleId);
		Shop shop = shopService.findNameById(article.getShopId());
		return shop;
	}
	
	/**
	 * 保存新增订单信息
	 */
	@RequestMapping("/saveOrder")
	public String saveOrder(Order order){
		String upInfo = "";
		if(order.getState() == null){
			order.setState((byte)1);
		}
		Date date = new Date();  
		order.setSellTime(date);
		int row = orderService.insertOrder(order);
		if(row > 0){
			upInfo = "success";
		}else{
			upInfo = "error";
		}
		return "redirect:orderList?upInfo="+upInfo;
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/viewOrder")
	public ModelAndView viewArticle(String id){
		ModelAndView mv = new ModelAndView();
		//System.out.println(id);
		Order order = orderService.selectById(Integer.valueOf(id));
		Article article = articleService.selectById(order.getArticleId());//查询订单对应的商品信息
		Shop shop = shopService.findNameById(order.getShopId());//查询订单对应的店铺信息
		Express express = expressService.findExpressById(order.getExpressId());//查询订单对应的物流信息
		mv.addObject("order", order);
		mv.addObject("article", article);
		mv.addObject("shop", shop);
		mv.addObject("express", express);
		mv.setViewName("pages/order/viewOrder");
		return mv;
	}
	
	/**
	 * 修改订单信息
	 */
	@RequestMapping("/updateOrder")
	public ModelAndView updateOrder(Model m,String id){
		ModelAndView mv = new ModelAndView();
		Order order = orderService.selectById(Integer.valueOf(id));//查询订单的详细信息
		Article article = articleService.selectById(order.getArticleId());//查询订单对应的商品名称
		Shop aShop = shopService.findNameById(order.getShopId());//查询订单对应的店铺名称
		Express express = expressService.findExpressById(order.getExpressId());//查询订单对应的物流公司信息
		mv.addObject("order", order);
		mv.addObject("article", article);
		mv.addObject("aShop", aShop);
		mv.addObject("express", express);
		//查询出所有的商品信息数据，为下拉框的选项做准备
		Article art = new Article();
		List<Article> listArticle = articleService.getArticleList(art);
		m.addAttribute("listArticle", listArticle);
		//查询出所有的物流公司数据，为下拉框的选项做准备
		List<Express> listExpress = expressService.findAllExpress();
		m.addAttribute("listExpress", listExpress);//查出所有物流公司
		mv.setViewName("pages/order/editOrder");
		return mv;
	}
	
	/**
	 * 修改页面点击保存
	 */
	@RequestMapping("/editOrder")
	public String editOrder(Order order){
		String upInfo = "";
		//System.out.println(article);
		int row =orderService.updateOrder(order);
		if(row > 0){
			upInfo = "success";
		}else{
			upInfo = "error";
		}
		return "redirect:orderList?upInfo="+upInfo;
	}
	
	/**
	 * 删除订单信息
	 */
	@RequestMapping("/deleteOrder")
	@ResponseBody
	public Map deleteOrder(Order order){
		Map m = new HashMap<>();
		order.setState((byte)0);
		int row = orderService.deleteOrder(order);
		if(row > 0){
			m.put("success", "删除成功！");
		}else{
			m.put("error", "删除失败！");
		}
		return m;
	}
	
	////////////////////////////////////////////////////////////////
	
	//移动端销售信息展示
		@RequestMapping("mobile_sales_show")
		@ResponseBody
		public List<Order> showsales(String title){
			System.out.println("走");
			List<Order> datas = orderService.findAllOrder("%"+title+"%");
			return datas;
		}
		
		
		//移动端销售信息详细展示
		@RequestMapping("mobile_sales_detal")
		@ResponseBody
		public Order showSalesDetail(String id){
			Order data = orderService.findOrder(Integer.parseInt(id));
			return data;
		}
		
		
		
		//移动端销售信息添加保存
		@RequestMapping("mobile_sales_save")
		@ResponseBody
		public BaseObject mobileSalesSave(Order order){
			BaseObject bo = new  BaseObject();
			if(order != null){
				orderService.insertOrder(order);
				bo.setFlag("success");
			}else{
				bo.setFlag("faild");
			}
			return bo;
		}
		
		//移动端查询商品名称和单价
		@RequestMapping("mobile_find_article")
		@ResponseBody
		public List<Article> mobileFindArticle(Integer page,String name){
			System.out.println(name);
			System.out.println(page);
			String n="%"+name+"%";
			Integer i=page*10;
			
			List<Article> aList=articleService.findArticle(n,i);
			
			return aList;
		}		
		
}

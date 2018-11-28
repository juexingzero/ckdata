package com.manhui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
import com.manhui.model.Category;
import com.manhui.model.Company;
import com.manhui.model.Express;
import com.manhui.model.Order;
import com.manhui.model.Shop;
import com.manhui.service.ArticleService;
import com.manhui.service.CategoryService;
import com.manhui.service.ChannelService;
import com.manhui.service.ExpressService;
import com.manhui.service.OrderService;
import com.manhui.service.ShopService;
import com.manhui.util.FileUploadUtil;
import com.manhui.util.PageBean;

/**
 * 
 * Title: ArticleController
 * @description:商品信息控制层
 * @author Liucheng
 *
 */
@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService; //商品mapper
	
	@Autowired
	private ShopService shopService; //店铺mapper
	
	@Autowired
	private CategoryService categoryService; //行业及类目mapper
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ExpressService expressService;
	
	/**
	 * 公司信息页面
	 * */
	/*@RequestMapping("/companyList")
	public String company(){
		return "/pages/company/company_list";
	}*/
	
	/**
	 * 公司信息列表
	 * */
	@RequestMapping("/articleList_{pageCurrent}_{pageSize}_{pageCount}")
	@ResponseBody
	public ModelAndView getArticleList(String title,@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,
										@PathVariable Integer pageCount,String upInfo){
		ModelAndView mv = new ModelAndView();
		Article article = new Article();
		if(title != null || title != ""){
			article.setTitle(title);
		}
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = articleService.getArticleCount();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		article.setStart((pageCurrent - 1)*pageSize);
		article.setEnd(pageSize);
		//int startIndex = pb.getStartIndex();
		//System.out.println(startIndex);
		List<Article> listmap = articleService.getPageArticle(article);
		for(Article m : listmap){
			if(m.getShopId() != null){
				Integer shopId = Integer.parseInt(String.valueOf(m.getShopId()));//object转Integer
				Shop s = shopService.findNameById(shopId);
				m.setShop(s);//查出商品对应的店铺
			}
			if(m.getCategoryId() != null){
				Integer cateId = Integer.parseInt(String.valueOf(m.getCategoryId()));
				Category cate = categoryService.getCategoryById(cateId);
				m.setCategory(cate);//查询商品对应的类目
			}else{
				m.setCategory(null);//查询商品对应的类目
			}
		}
		
		mv.addObject("article", listmap);
		mv.addObject("rows", rows);
		mv.addObject("pageCurrent", pageCurrent);
		mv.addObject("pageSize", pageSize);
		mv.addObject("pageCount", pageCount);
		mv.addObject("upInfo", upInfo);
		mv.setViewName("pages/article/article_list");
		return mv;
	}
	
	/**
	 * 跳转到新增公司页面
	 * */
	@RequestMapping("/addArticle")
	public String addArticle(Model m){
		Shop shop = new Shop();
		List<Shop> listShop = shopService.getShopList(shop);
		m.addAttribute("listShop", listShop);//查出所有店铺，以供选择
		List<Category> listCate = categoryService.getCategoryByCateIdForList();
		m.addAttribute("listCate", listCate);//查出所有类目
		return "pages/article/addArticle";
	}
	
	/**
	 * 添加商品信息页面，获取行业对应的类目信息
	 */
	/*@RequestMapping("/getCategory")
	@ResponseBody
	public List<Category> getCategory(String id){
		Integer pId = Integer.valueOf(id);
		//System.out.println(pId);
		List<Category> list = categoryService.getCategoryByPid(pId);
		return list;
	}*/
	
	/**
	 * 保存新增公司信息
	 */
	@RequestMapping("/saveArticle")
	public String saveArticle(Article article){
		String upInfo = "";
		if(article.getImgUrl() == null){
			article.setImgUrl("/mjw");
		}
		if(article.getState() == null){
			article.setState((byte)1);
		}
		int row = articleService.insertArticle(article);
		if(row > 0){
			upInfo = "success";
		}else{
			upInfo = "error";
		}
		return "redirect:articleList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/viewArticle")
	public ModelAndView viewArticle(String id){
		ModelAndView mv = new ModelAndView();
		//System.out.println(id);
		Article article = articleService.selectById(Integer.valueOf(id));
		Shop aShop = shopService.findNameById(article.getShopId());//查询商品对应的店铺名称
		Category aCate = categoryService.getCategoryById(article.getCategoryId());//查询商品对应的类目名称
		mv.addObject("article", article);
		mv.addObject("aShop", aShop);
		mv.addObject("aCate", aCate);
		mv.setViewName("pages/article/viewArticle");
		return mv;
	}
	
	/**
	 * 修改公司信息
	 */
	@RequestMapping("/updateArticle")
	public ModelAndView updateArticle(Model m,String id){
		ModelAndView mv = new ModelAndView();
		Article article = articleService.selectById(Integer.valueOf(id));//查询商品的详细信息
		Shop aShop = shopService.findNameById(article.getShopId());//查询商品对应的店铺名称
		Category aCate = categoryService.getCategoryById(article.getCategoryId());//查询商品对应的类目名称
		mv.addObject("article", article);
		mv.addObject("aShop", aShop);
		mv.addObject("aCate", aCate);
		//查询出所有的店铺和类目数据，为下拉框的选项
		Shop shop = new Shop();
		List<Shop> listShop = shopService.getShopList(shop);
		m.addAttribute("listShop", listShop);//查出所有店铺，以供选择
		List<Category> listCate = categoryService.getCategoryByCateIdForList();
		m.addAttribute("listCate", listCate);//查出所有类目
		mv.setViewName("pages/article/editArticle");
		return mv;
	}
	
	/**
	 * 修改页面点击保存
	 */
	@RequestMapping("/editArticle")
	public String editArticle(Article article){
		String upInfo = "";
		//System.out.println(article);
		int row =articleService.updateArticle(article);
		if(row > 0){
			upInfo = "success";
		}else{
			upInfo = "error";
		}
		return "redirect:articleList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 删除公司信息
	 */
	@RequestMapping("/deleteArticle")
	@ResponseBody
	public Map deleteArticle(Article article){
		Map m = new HashMap<>();
		article.setState((byte)0);
		int row = articleService.deleteArticle(article);
		if(row > 0){
			m.put("success", "删除成功！");
		}else{
			m.put("error", "删除失败！");
		}
		return m;
	}
	
	
	
	//移动端商品信息展示
	@RequestMapping("mobile_article_show")
	@ResponseBody
	public List<Article> showArticle(String articleName,Integer page){
		Integer p=page*10;
		List<Article> datas = new ArrayList<>();
		if(articleName == null || "".equals(articleName)){
			String name = "%%";
			datas = articleService.findArticle(name,p);
		}else{
			String name = "%"+articleName+"%";
			datas = articleService.findArticle(name,p);
		}
		if(datas != null && datas.size() > 0){
			for(Article data : datas){
				data.setCategory(categoryService.getCategoryById(data.getCategoryId()));
				Shop shop = shopService.getshopByid(data.getShopId());
				data.setChannel(channelService.getChannelById(shop.getChannelId()));
			}
		}else{
			datas = new ArrayList<>();
			datas.add(new Article());
		}
		
		return datas;
	}
	
	
	//移动端商品详细信息展示
	@RequestMapping("mobile_article_detal")
	@ResponseBody
	public Article showArticleDetal(String id){
		Article data = new Article();
		if(id != null && !"".equals(id)){
			data = articleService.selectById(Integer.parseInt(id));
			data.setShop(shopService.getshopByid(data.getShopId()));
			data.setCategory(categoryService.getCategoryById(data.getCategoryId()));
			data.setOrderList(orderService.getOrderListByArticleId(data.getId()));
		}
		
		return data;
	}
	
	
	//移动端物流信息
	@RequestMapping("mobile_getExpress")
	@ResponseBody
	public List<Express> ShowExpress(){
		return expressService.findAllExpress();
	}
	
	
	
	//移动端商品信息添加保存
	@RequestMapping("mobile_article_save")
	@ResponseBody
	public BaseObject mobileArticleSave(Article article,List<Order> orderlist,
					@RequestParam(value="file") MultipartFile[] file,HttpServletRequest request){
		BaseObject bo = new  BaseObject();
		if(article != null){
			List<String> urlList=FileUploadUtil.moerPicture(request, file,"GoodsPicture");
			String url = urlList.get(0);
			article.setImgUrl(url);
			articleService.insertArticle(article);
			if(orderlist != null && orderlist.size() > 0){
				for(Order data : orderlist){
					orderService.insertOrder(data);
				}
			}
			
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
	
	
	//移动端商品信息添加保存
	@RequestMapping("mobile_article_update")
	@ResponseBody
	public BaseObject mobileArticleUpdate(Article article,List<Order> orderlist,
			@RequestParam(value="file") MultipartFile[] file,HttpServletRequest request){
		BaseObject bo = new  BaseObject();
		if(article != null){
			List<String> urlList=FileUploadUtil.moerPicture(request, file,"GoodsPicture");
			String url = urlList.get(0);
			article.setImgUrl(url);
			articleService.updateArticle(article);
			if(orderlist != null && orderlist.size() > 0){
				for(Order data : orderlist){
					orderService.insertOrder(data);
				}
			}
			
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
	
	
	//移动端商品信息修改里订单删除
	@RequestMapping("mobile_article_order_delete")
	@ResponseBody
	public BaseObject mobileArticleUpdate(String orderId){
		BaseObject bo = new  BaseObject();
		if(orderId != null && !"".equals(orderId)){
			Order order = new Order();
			order.setId(Integer.parseInt(orderId));
			orderService.deleteOrder(order);
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
}

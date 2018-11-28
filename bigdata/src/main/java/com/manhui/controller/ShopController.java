package com.manhui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.manhui.model.Article;
import com.manhui.model.BaseObject;
import com.manhui.model.Category;
import com.manhui.model.Company;
import com.manhui.model.Shop;
import com.manhui.service.ArticleService;
import com.manhui.service.CategoryService;
import com.manhui.service.CompanyService;
import com.manhui.service.ShopService;
import com.manhui.util.FileUploadUtil;
import com.manhui.util.PageBean;

/**
 * 
 * Title: ShopController
 * @description:店铺控制层
 * @author Liucheng
 *
 */
@Controller
public class ShopController {

	@Autowired
	private ShopService shopService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ArticleService articleService;
	/**
	 * 获取店铺信息列表
	 * */
	@GetMapping("findShop_{pageCurrent}_{pageSize}_{pageCount}")
	public String findShop(Model model,@PathVariable Integer pageCurrent,
			@PathVariable Integer pageSize,@PathVariable Integer pageCount,String flag){
		//分页操作
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = shopService.findShopCount();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		Shop shop = new Shop();
		shop.setStart((pageCurrent - 1)*pageSize);
		shop.setEnd(pageSize);
		//查询分页数据
		List<Shop> shopList = shopService.findShopPage(shop);
		model.addAttribute("shopList", shopList);
		model.addAttribute("rows", rows);
		model.addAttribute("pageCurrent", pageCurrent);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageCount", pageCount);
		if(flag != null && "add_success".equals(flag)){
			flag = "添加成功!";
		}else if(flag != null && "add_error".equals(flag)){
			flag = "添加失败!";
		}else if(flag != null && "delete_success".equals(flag)){
			flag = "删除成功!";
		}else if(flag != null && "delete_error".equals(flag)){
			flag = "删除失败!";
		}
		model.addAttribute("flag",flag);
		return "pages/shop/shop_list";
	}
	
	/**
	 * 跳转到添加店铺页面	
	 * */
	@RequestMapping("/addShop")
	public String addShop(Model m){
		List<Company> list = new ArrayList<>();
		Company company = new Company();
		list = companyService.getCompanyList(company);//查询全部的公司
		List<Category> listcate = categoryService.getAllCategory();
		m.addAttribute("list", list);
		m.addAttribute("listCate", listcate);
		return "pages/shop/addShop";
	}
	
	/**
	 * 保存新增店铺信息
	 */
	@RequestMapping("/saveShop")
	public String saveShop(Shop shop){
		System.out.println(shop);
		if(shop.getRegionId() == null){
			shop.setRegionId(1);
		}
		if(shop.getImgUrl() == null){
			shop.setImgUrl("/mjw");
		}
		if(shop.getState() == null){
			shop.setState(1);
		}
		try{
			shopService.insertShop(shop);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:findShop_0_0_0";
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/viewShop")
	public ModelAndView viewShop(String id){
		ModelAndView mv = new ModelAndView();
		Shop shop = new Shop();
		shop.setId(Integer.parseInt(id));
		Map c = shopService.selectById(shop);
		//System.out.println(c);
		List<Category> l = new ArrayList<>();//准备一个空的list
		String str = String.valueOf(c.get("business_ids"));//Object转String
		String[] strs = str.split(",");
		for(String s : strs){
			Integer busId = Integer.valueOf(s);
			//根据行业id查出行业名称
			Category cate = categoryService.getCategoryById(busId);
			l.add(cate);//把商品对应的行业放进list中
		}
		mv.addObject("vShop", c);
		mv.addObject("cate", l);
		mv.setViewName("pages/shop/viewShop");
		return mv;
	}
	
	/**
	 * 修改店铺信息
	 */
	@RequestMapping("/updateShop")
	@ResponseBody
	public ModelAndView updateShop(Shop shop){
		ModelAndView mv = new ModelAndView();
		Map c = shopService.selectById(shop);
		
		/*List<Category> l = new ArrayList<>();//准备一个空的list
		String str = String.valueOf(c.get("business_ids"));//Object转String
		String[] strs = str.split(",");
		for(String s : strs){
			Integer busId = Integer.valueOf(s);
			//根据行业id查出行业名称
			Category cate = categoryService.getCategoryById(busId);
			l.add(cate);//把商品对应的行业放进list中
		}*/
		
		List<Category> listcate = categoryService.getAllCategory();//查询全部的行业名称
		mv.addObject("eShop", c);
		/*mv.addObject("cate", l);*/
		mv.addObject("listcate", listcate);
		mv.setViewName("pages/shop/editShop");
		return mv;
	}
	
	/**
	 * 修改页面点击保存
	 */
	@RequestMapping("/editShop")
	public String editShop(Shop shop){
		String upInfo = "";
		int row = shopService.updateShop(shop);
		if(row > 0){
			upInfo = "success";
		}else{
			upInfo = "error";
		}
		return "redirect:findShop_0_0_0";
	}
	
	/**
	 * 删除店铺信息
	 */
	@GetMapping("/deleteShop")
	public String deleteShop(String id){
		Shop shop = new Shop();
		shop.setId(Integer.valueOf(id));
		try{
			shopService.deleteShop(shop);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:findShop_0_0_0";
	}
	
	
	//移动端店铺信息展示
	@RequestMapping("mobile_shop_show")
	@ResponseBody
	public List<Shop> showshop(String name,Integer page){
		if(page == null){
			page = 1;
		}
		Integer p=page*10;
		List<Shop> datas = new ArrayList<>();
		if(name ==null ||"".equals(name)){
			System.out.println("进入这里");
			datas=shopService.findAllShop("%%", p);
		}else{
			name="%"+name+"%";
			
			datas = shopService.findAllShop(name,p);
		}
		
		if(datas != null){
			for(Shop data : datas){
				Integer num = articleService.findGodosNumByShopId(data.getId());
				data.setGoodsNum(num); 
			}
		}
		return datas;
	}
	
	
	//移动端店铺信息详细展示
	@RequestMapping("mobile_shop_detal")
	@ResponseBody
	public Shop showShopDetail(String id){
		Shop data = shopService.findNameById(Integer.parseInt(id));
		
		Company company=new Company();
		company.setId(data.getCompanyId());
		Company c=companyService.selectById(company);
		data.setCompanyName(c.getName());
		
		return data;
	}
	
	
	//移动端店铺信息编辑保存
	@RequestMapping("mobile_shop_update")
	@ResponseBody
	public BaseObject mobileShopUpdate(HttpServletRequest request,List<Article> list,Shop shop,@RequestParam("uploadFile") MultipartFile file,
											@RequestParam("uploadFiles") MultipartFile[] files){
		BaseObject bo = new  BaseObject();
		if(shop != null){
			//更新公司信息
			//TODO文件信息解析出地址放入company中
			String url = file.getOriginalFilename();
			shop.setImgUrl(url);
			FileUploadUtil.upload(request, file, "Shoppicture");
			shopService.updateShop(shop);
			if(shop != null){
				//添加商品信息
				List<String> urlList=FileUploadUtil.moerPicture(request, files,"Goodspicture");
				for(int i=0;i<list.size();i++){
					String ur=urlList.get(i);
					Article ar=list.get(i);
					ar.setImgUrl(ur);
					articleService.insertArticle(ar);
				}
			}
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
	
	
	//移动端店铺信息添加保存
	@RequestMapping("mobile_shop_save")
	@ResponseBody
	public BaseObject mobileshopSave(HttpServletRequest request,List<Article> list,Shop shop,@RequestParam("uploadFile") MultipartFile file,
			@RequestParam("uploadFiles") MultipartFile[] files){
		BaseObject bo = new  BaseObject();
		if(shop != null){
			//更新店铺信息
			//TODO文件信息解析出地址放入company中
			String url = file.getOriginalFilename();
			shop.setImgUrl(url);
			FileUploadUtil.upload(request, file, "Shoppicture");
			shopService.insertShop(shop);
			if(shop != null){
				//添加商品信息
				List<String> urlList=FileUploadUtil.moerPicture(request, files,"Goodspicture");
				for(int i=0;i<list.size();i++){
					String ur=urlList.get(i);
					Article ar=list.get(i);
					ar.setImgUrl(ur);
					articleService.insertArticle(ar);
				}
			}
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
	
	//移动端删除商品
	@RequestMapping("mobile_shop_article_delete")
	@ResponseBody
	public BaseObject mobileCompanyShopDelete(String articleId){
		BaseObject bo = new  BaseObject();
		if(articleId != null && !"".equals(articleId)){
			Article article=new Article();
			article.setId(Integer.parseInt(articleId));
			int i=0;
			article.setState((byte)i);
			articleService.deleteArticle(article);
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
	
	
		//移动端查询类目
		@RequestMapping("mobile_category_show")
		@ResponseBody
		public List<Category> mobileCategoryShow(String articleId){
			
			List<Category> dList=categoryService.getAllCategory();
			for (Category c : dList) {
				List<Category> list=categoryService.getCategoryByPid(c.getId());
				c.setcList(list);
			}
			
			return dList;
		}
}

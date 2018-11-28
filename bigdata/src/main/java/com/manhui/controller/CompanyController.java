package com.manhui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.manhui.model.BaseObject;
import com.manhui.model.BaseRegion;
import com.manhui.model.Company;
import com.manhui.model.Shop;
import com.manhui.service.BaseRegionService;
import com.manhui.service.CompanyService;
import com.manhui.service.ShopService;
import com.manhui.util.FileUploadUtil;
import com.manhui.util.PageBean;

/**
 * 
 * Title: CompanyController
 * @description:公司控制层
 * @author Liucheng
 *
 */
@Controller
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private BaseRegionService baseRegionService;
	
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
	@RequestMapping("/companyList_{pageCurrent}_{pageSize}_{pageCount}")
	@ResponseBody
	public ModelAndView getCompanyList(String name,String title,@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,
										@PathVariable Integer pageCount,String upInfo){
		ModelAndView mv = new ModelAndView();
		Company company = new Company();
		if(name != null || name != ""){
			company.setName(name);
		}
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = companyService.getCompanyCount();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		company.setStart((pageCurrent - 1)*pageSize);
		company.setEnd(pageSize);
		List<Company> companyList = companyService.getPageCompany(company);
		mv.addObject("company", companyList);
		mv.setViewName("pages/company/company_list");
		mv.addObject("rows", rows);
		mv.addObject("pageCurrent", pageCurrent);
		mv.addObject("pageSize", pageSize);
		mv.addObject("pageCount", pageCount);
		mv.addObject("upInfo", upInfo);
		return mv;
	}
	
	/**
	 * 跳转到新增公司页面
	 * */
	@RequestMapping("/addCompany")
	public String addCompany(){
		return "pages/company/addCompany";
	}
	
	/**
	 * 保存新增公司信息
	 */
	@RequestMapping("/saveCompany")
	public String saveCompany(Company company){
		String upInfo = "";
		if(company.getRegionId() == null){
			company.setRegionId(1);
		}
		if(company.getImgUrl() == null){
			company.setImgUrl("/mjw");
		}
		if(company.getState() == null){
			company.setState(1);
		}
		int row = companyService.insertCompany(company);
		if(row > 0){
			upInfo = "success";
		}else{
			upInfo = "error";
		}
		return "redirect:companyList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/viewCompany")
	public ModelAndView viewCompany(String id){
		ModelAndView mv = new ModelAndView();
		//System.out.println(id);
		Company company = new Company();
		company.setId(Integer.parseInt(id));
		Company c = companyService.selectById(company);
		
		mv.addObject("vCompany", c);
		mv.setViewName("pages/company/viewCompany");
		return mv;
	}
	
	/**
	 * 修改公司信息
	 */
	@RequestMapping("/updateCompany")
	public ModelAndView updateCompany(Company company){
		ModelAndView mv = new ModelAndView();
		Company c = companyService.selectById(company);
		mv.addObject("eCompany", c);
		mv.setViewName("pages/company/editCompany");
		return mv;
	}
	
	/**
	 * 修改页面点击保存
	 */
	@RequestMapping(value = "/editCompany",produces = "application/json;charset=utf-8")
	public String editCompany(HttpServletRequest request){
		String upInfo = "";
		Company c = new Company();
		c.setId(Integer.valueOf(request.getParameter("id")));
		c.setName(request.getParameter("name"));
		c.setLegalPerson(request.getParameter("legalPerson"));
		c.setLegalPersonPhone(request.getParameter("legalPersonPhone"));
		c.setCity(request.getParameter("city"));
		c.setDistrict(request.getParameter("district"));
		c.setAddress(request.getParameter("address"));
		c.setImgUrl(request.getParameter("imgUrl"));
	    //System.out.println(c);
		int row = companyService.updateCompany(c);
		if(row > 0){
			upInfo = "success";
		}else{
			upInfo = "error";
		}
		return "redirect:companyList_0_0_0?upInfo="+upInfo;
	}
	
	/**
	 * 删除公司信息
	 */
	@RequestMapping("/deleteCompany")
	@ResponseBody
	public Map deleteCompany(Company company){
		Map m = new HashMap<>();
		company.setState(0);
		int row = companyService.deleteCompany(company);
		if(row > 0){
			m.put("success", "删除成功！");
		}else{
			m.put("error", "删除失败！");
		}
		return m;
	}
	
	
	//移动端公司信息展示
	@RequestMapping("mobile_company_show")
	@ResponseBody
	public List<Company> showCompany(String companyName,Integer page){
		
		List<Company> datas = new ArrayList<>();
		if(page == null){
			page = 0;
		}
		if(companyName != null && !"".equals(companyName)){
			String name = "%"+companyName+"%";
			datas = companyService.getCompanyByName(name,page);
		}else{
			datas = companyService.getCompanyByName("%%",page);
		}
		if(datas != null){
			for(Company data : datas){
				List<Shop> shops = shopService.getShopByCompanyId(data.getId());
				data.setShopNum(shops.size());
			}
		}else{
			datas = new ArrayList<>();
			datas.add(new Company());
		}
		return datas;
	}
	
	
	//移动端公司信息详细展示
	@RequestMapping("mobile_company_detal")
	@ResponseBody
	public Company showCompanyDetail(String id){
		Company data = new Company();
		if(id != null && !"".equals(id)){
			data = companyService.getCompanyById(Integer.parseInt(id));
			List<Shop> shops = shopService.getShopByCompanyId(data.getId());
			data.setShops(shops);
		}
		return data;
	}
	
	
	//地区查询
	@RequestMapping("mobile_getRegion")
	@ResponseBody
	public List<BaseRegion> showRegion(){
		return baseRegionService.findAllBaseRegion();
	}
	
	//条件查询地区
	@RequestMapping("mobile_getRegionData")
	@ResponseBody
	public List<BaseRegion> getRegionData(String parentCode,Integer level){
		List<BaseRegion> brs = new ArrayList<>();
		if(parentCode != null && !"".equals(parentCode) && level != null){
			brs = baseRegionService.findRegionDataByParentCodeAndLevel(parentCode, level);
		}
		return brs;
	}
	
	
	//移动端公司信息编辑保存
	@RequestMapping("mobile_company_update")
	@ResponseBody
	public BaseObject mobileCompanyUpdate(Company company,List<Shop> shop,@RequestParam(value="file") MultipartFile[] file,
							@RequestParam(value="files") MultipartFile[] files,HttpServletRequest request){
		BaseObject bo = new  BaseObject();
		if(company != null){
			//更新公司信息
			//TODO文件信息解析出地址放入company中并存入文件
			List<String> urlList=FileUploadUtil.moerPicture(request, file,"Companypicture");
			String url = urlList.get(0);
			company.setImgUrl(url);
			companyService.updateCompany(company);
			if(shop != null && shop.size() > 0){
				//添加店铺信息
				List<String> urlLists=FileUploadUtil.moerPicture(request, files,"Shoppicture");
				for(int i=0;i<shop.size();i++){
					String shopurl = urlLists.get(i);
					Shop data = shop.get(i);
					data.setImgUrl(shopurl);
					data.setCompanyId(company.getId());
					shopService.insertShop(data);
				}
			}
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
	
	
	//移动端公司信息添加保存
	@RequestMapping("mobile_company_save")
	@ResponseBody
	public BaseObject mobileCompanySave(Company company,List<Shop> shops,@RequestParam(value="file") MultipartFile[] file,
			@RequestParam(value="files") MultipartFile[] files,HttpServletRequest request){
		BaseObject bo = new  BaseObject();
		if(company != null){
			//保存公司信息
			//TODO文件信息解析出地址放入company中
			List<String> urlList=FileUploadUtil.moerPicture(request, file,"Companypicture");
			String url = urlList.get(0);
			company.setImgUrl(url);
			companyService.insertCompany(company);
			if(shops != null && shops.size() > 0){
				//添加店铺信息
				Integer newid = companyService.getCompanyMaxID();
				Company datas = companyService.getCompanyById(newid);
				List<String> urlLists=FileUploadUtil.moerPicture(request, files,"Shoppicture");
				for(int i=0;i<shops.size();i++){
					String shopurl = urlLists.get(i);
					Shop data = shops.get(i);
					data.setImgUrl(shopurl);
					data.setCompanyId(datas.getId());
					shopService.insertShop(data);
				}
			}
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
	
	//移动端删除店铺
	@RequestMapping("mobile_company_shop_delete")
	@ResponseBody
	public BaseObject mobileCompanyShopDelete(String shopId){
		
		BaseObject bo = new  BaseObject();
		if(shopId != null && !"".equals(shopId)){
			//删除店铺即可
			Shop shop = new Shop();
			shop.setId(Integer.parseInt(shopId));
			shopService.deleteShop(shop);
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
}

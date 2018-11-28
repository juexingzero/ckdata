package com.manhui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.manhui.model.BaseOption;
import com.manhui.model.BaseRegion;
import com.manhui.model.Category;
import com.manhui.model.Channel;
import com.manhui.model.Express;
import com.manhui.service.BaseOptionService;
import com.manhui.service.BaseRegionService;
import com.manhui.service.CategoryService;
import com.manhui.service.ChannelService;
import com.manhui.service.ExpressService;


/**
 * @ClassName: BaseConfigController.java
 * @description:   基础设置控制器
 * @author: Jiangxiaosong
 * @date Create in 14:33 2018/1/30
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Controller
public class BaseConfigController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private BaseOptionService baseOptionService;
	
	@Autowired
	private ExpressService expressSerivce;
	
	@Autowired
	private BaseRegionService baseRegionService;
	
	//----------商品行业数据-------------------------------------------------------------------------------
	/**
	 * 获取商品行业数据
	 * @param model
	 * @return
	 */
	@GetMapping("getCategoryInfo_{pageCurrent}_{pageSize}_{pageCount}")
	public String getCategoryInfo(Model model,@PathVariable Integer pageCurrent,
			@PathVariable Integer pageSize,@PathVariable Integer pageCount,String flag){
		//分页操作
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = categoryService.count();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		Category category = new Category();
		category.setStart((pageCurrent - 1)*pageSize);
		category.setEnd(pageSize);
		//查询分页数据
		List<Category> categoryList = categoryService.getCategoryPage(category);
		model.addAttribute("category", categoryList);
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
		return "pages/BaseConfig/main_category_list";
	}
	
	
	/**
	 * 跳转到新增页面
	 * @param model
	 * @return
	 */
	@GetMapping("to_add_category")
	public String toAddCategory(Model model){
		
		return "pages/BaseConfig/main_category_add";
	}
	
	
	/**
	 * 商品行业新增
	 * @param category
	 * @param model
	 * @return
	 */
	@PostMapping("add_category")
	public String addCategory(Category category,Model model){
		String flag = "";
		if(category != null){
			//执行新增
			category.setPid(0);
			Integer sort = categoryService.getCategorySortByPid(0);
			category.setSortid(sort+1); 
			category.setState(1);
			categoryService.insertMainCategory(category);
			flag = "add_success";
		}else{
			flag = "add_error";
		}
		return "redirect:getCategoryInfo_0_0_0?flag="+flag;
	}
	
	
	/**
	 * 商品行业删除
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/delete_category")
	public String deleteCategory(String id,Model model){
		String flag = "";
		if(id != null && !"".equals(id)){
			//删除行业
			categoryService.deleteMainCategory(Integer.parseInt(id));
			//删除行业下的类目？
			categoryService.deleteCategory(Integer.parseInt(id));
			flag="delete_success";
		}else{
			flag = "delete_error";
		}
		return "redirect:getCategoryInfo_0_0_0?flag="+flag;
	}
	
	
	//----------商品行业数据-------------------------------------------------------------------------------
	
	
	//----------商品类目数据-------------------------------------------------------------------------------
	
	/**
	 * 获取商品类目信息
	 * @param model
	 * @param pageCurrent
	 * @param pageSize
	 * @param pageCount
	 * @param flag
	 * @return
	 */
	@GetMapping("getSubCategoryInfo_{pageCurrent}_{pageSize}_{pageCount}")
	public String getSubCategoryInfo(Model model,@PathVariable Integer pageCurrent,
			@PathVariable Integer pageSize,@PathVariable Integer pageCount,String flag){
		//分页操作
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = categoryService.subCount();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		Category category = new Category();
		category.setStart((pageCurrent - 1)*pageSize);
		category.setEnd(pageSize);
		List<Category> categoryList = categoryService.getSubCategoryPage(category);
		//查询父节点数据
		for(Category categorys : categoryList){
			//获取到父节点数据
			Category mainCategory = categoryService.getCategoryById(categorys.getPid());
			categorys.setpName(mainCategory.getName());
		}
		model.addAttribute("category", categoryList);
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
		return "pages/BaseConfig/category_list";
	}
	
	
	/**
	 * 跳转到新增页面
	 * @param model
	 * @return
	 */
	@GetMapping("to_add_subcategory")
	public String toAddSubCategory(Model model){
		//查询所有行业以供选择
		List<Category> mainCategorys = categoryService.getAllCategory();
		model.addAttribute("mainCategorys", mainCategorys);
		return "pages/BaseConfig/category_add";
	}
	
	/**
	 * 商品类目新增
	 * @param category
	 * @param model
	 * @return
	 */
	@PostMapping("add_subcategory")
	public String addSubCategory(Category category,Model model){
		String flag = "";
		if(category != null){
			//执行新增
			Integer sort = categoryService.getCategorySortByPid(category.getPid());
			if(sort != null){
				category.setSortid(sort+1);
			}else{
				category.setSortid(0);
			}
			category.setPid(category.getPid());
			category.setState(1);
			categoryService.insertMainCategory(category);
			flag = "add_success";
		}else{
			flag = "add_error";
		}
		return "redirect:getSubCategoryInfo_0_0_0?flag="+flag;
	}
	
	
	/**
	 * 商品类目删除
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/delete_subcategory")
	public String deleteSubCategory(String id,Model model){
		String flag = "";
		if(id != null && !"".equals(id)){
			//删除行业
			categoryService.deleteMainCategory(Integer.parseInt(id));
			flag="delete_success";
		}else{
			flag = "delete_error";
		}
		return "redirect:getSubCategoryInfo_0_0_0?flag="+flag;
	}
	
	//----------商品类目数据--------------------------------------------------------------------------------
	
	
	
	//----------来源渠道数据--------------------------------------------------------------------------------
	
	/**
	 * 获取来源渠道数据
	 * @param model
	 * @param pageCurrent
	 * @param pageSize
	 * @param pageCount
	 * @param flag
	 * @return
	 */
	@GetMapping("getChannelInfo_{pageCurrent}_{pageSize}_{pageCount}")
	public String getChannelInfo(Model model,@PathVariable Integer pageCurrent,
			@PathVariable Integer pageSize,@PathVariable Integer pageCount,String flag){
		//分页操作
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = channelService.getChannelCount();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		Channel channel = new Channel();
		channel.setStart((pageCurrent - 1)*pageSize);
		channel.setEnd(pageSize);
		//查询分页数据
		List<Channel> channelList = channelService.getChannelPage(channel);
		model.addAttribute("channel", channelList);
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
		return "pages/BaseConfig/channel_list";
	}
	
	
	/**
	 * 跳转到追加页面
	 * @param model
	 * @return
	 */
	@GetMapping("to_add_channel")
	public String toAddChannel(Model model){
		//查询采集来源数据
		BaseOption baseOption = new BaseOption();
		baseOption.setTablename("bg_channel");
		baseOption.setColumname("type");
		List<BaseOption> baseOptions = baseOptionService.findAllBaseOption(baseOption);
		model.addAttribute("baseOptions", baseOptions);
		return "pages/BaseConfig/channel_add";
	}
	
	
	/**
	 * 新增来源渠道数据
	 * @param model
	 * @param channel
	 * @return
	 */
	@PostMapping("add_channel")
	public String addChannel(Model model,Channel channel){
		String flag = "";
		if(channel != null){
			//执行追加
			channel.setState(1);
			channelService.insertChannel(channel);
			flag = "add_success";
		}else{
			flag = "add_error";
		}
		return "redirect:getChannelInfo_0_0_0?flag="+flag;
	}
	
	
	/**
	 * 删除来源渠道数据
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("delete_channel")
	public String deleteChannel(Model model,String id){
		String flag = "";
		if(id != null && !"".equals(id)){
			channelService.deleteChannelById(Integer.parseInt(id));
			flag = "delete_success";
		}else{
			flag = "delete_error";
		}
		return "redirect:getChannelInfo_0_0_0?flag="+flag;
	}
	
	
	
	//----------来源渠道数据--------------------------------------------------------------------------------
	
	
	//----------采集方式数据--------------------------------------------------------------------------------
	
	/**
	 * 获取采集方式
	 * @param model
	 * @param flag
	 * @return
	 */
	@GetMapping("get_channel_type")
	public String getChannelType(Model model,String flag){
		BaseOption bo = new BaseOption();
		bo.setTablename("bg_channel");
		bo.setColumname("type");
		List<BaseOption> baseOptionList = baseOptionService.findAllBaseOption(bo);
		model.addAttribute("bo", baseOptionList);
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
		return "pages/BaseConfig/channel_type_list";
	}
	
	
	/**
	 * 跳转到追加页面
	 * @param model
	 * @return
	 */
	@GetMapping("to_add_channel_type")
	public String toAddChannelType(Model model){
		return "pages/BaseConfig/channel_type_add";
	}
	
	
	
	/**
	 * 新增采集方式
	 * @param model
	 * @param channel
	 * @return
	 */
	@PostMapping("add_channel_type")
	public String addChannelType(Model model,BaseOption baseOption){
		String flag = "";
		if(baseOption != null){
			//执行追加
			baseOption.setTablename("bg_channel");
			baseOption.setColumname("type");
			baseOption.setState(1);
			Integer sortid = baseOptionService.findSortByBaseOption(baseOption);
			baseOption.setSortid(sortid+1);
			baseOption.setCode(String.valueOf(sortid+1));
			baseOptionService.insertBaseOptions(baseOption);
			flag = "add_success";
		}else{
			flag = "add_error";
		}
		return "redirect:get_channel_type?flag="+flag;
	}
	
	
	/**
	 * 删除采集方式
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("delete_channel_type")
	public String deleteChannelType(Model model,String id){
		String flag = "";
		if(id != null && !"".equals(id)){
			baseOptionService.deleteBaseOptions(Integer.parseInt(id));
			flag = "delete_success";
		}else{
			flag = "delete_error";
		}
		return "redirect:get_channel_type?flag="+flag;
	}
	
	
	//----------采集方式数据--------------------------------------------------------------------------------
	
	
	//----------物流快递数据--------------------------------------------------------------------------------
	
	/**
	 * 获取快递物流数据
	 * @param model
	 * @param pageCurrent
	 * @param pageSize
	 * @param pageCount
	 * @param flag
	 * @return
	 */
	@GetMapping("getExpressInfo_{pageCurrent}_{pageSize}_{pageCount}")
	public String getExpressInfo(Model model,@PathVariable Integer pageCurrent,
			@PathVariable Integer pageSize,@PathVariable Integer pageCount,String flag){
		//分页操作
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = expressSerivce.findAllExpressCount();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		Express express = new Express();
		express.setStart((pageCurrent - 1)*pageSize);
		express.setEnd(pageSize);
		//查询分页数据
		List<Express> expressList = expressSerivce.findAllExpressPage(express);
		model.addAttribute("express", expressList);
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
		return "pages/BaseConfig/express_list";
	}
	
	
	/**
	 * 跳转到追加页面
	 * @param model
	 * @return
	 */
	@GetMapping("to_add_express")
	public String toAddExpress(Model model){
		return "pages/BaseConfig/express_add";
	}
	
	
	/**
	 * 新增快递物流数据
	 * @param model
	 * @param channel
	 * @return
	 */
	@PostMapping("add_express")
	public String addExpress(Model model,Express express){
		String flag = "";
		if(express != null){
			//执行追加
			express.setState(1);
			expressSerivce.insertExpress(express);
			flag = "add_success";
		}else{
			flag = "add_error";
		}
		return "redirect:getExpressInfo_0_0_0?flag="+flag;
	}
	
	
	/**
	 * 删除快递物流数据
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("delete_express")
	public String deleteExpress(Model model,String id){
		String flag = "";
		if(id != null && !"".equals(id)){
			expressSerivce.deleteExpress(Integer.parseInt(id));
			flag = "delete_success";
		}else{
			flag = "delete_error";
		}
		return "redirect:getExpressInfo_0_0_0?flag="+flag;
	}
	
	
	
	
	//----------物流快递数据--------------------------------------------------------------------------------
	
	
	//----------行政区划数据--------------------------------------------------------------------------------
	
	/**
	 * 获取行政区划数据
	 * @param model
	 * @param pageCurrent
	 * @param pageSize
	 * @param pageCount
	 * @param flag
	 * @return
	 */
	@GetMapping("getBaseRegion_{pageCurrent}_{pageSize}_{pageCount}")
	public String getBaseRegion(Model model,@PathVariable Integer pageCurrent,
			@PathVariable Integer pageSize,@PathVariable Integer pageCount,String flag){
		//分页操作
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = baseRegionService.findBaseRegionCount();
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		BaseRegion baseRegion = new BaseRegion();
		baseRegion.setStart((pageCurrent - 1)*pageSize);
		baseRegion.setEnd(pageSize);
		//查询分页数据
		List<BaseRegion> baseRegionList = baseRegionService.findBaseRegionPage(baseRegion);
		for(BaseRegion region : baseRegionList){
			if(region.getParentCode() != null){
				region.setParentName(baseRegionService.findProvByParentCode(region.getParentCode()).get(0).getName());
			}else{
				region.setParentName("");
			}
		}
		model.addAttribute("baseRegion", baseRegionList);
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
		return "pages/BaseConfig/region_list";
	}
	
	/**
	 * 跳转到追加页面
	 * @param model
	 * @return
	 */
	@GetMapping("to_add_region")
	public String toAddRegion(Model model){
		return "pages/BaseConfig/region_add";
	}
	
	
	/**
	 * 新增行政区划数据
	 * @param model
	 * @param channel
	 * @return
	 */
	@PostMapping("add_region")
	public String addRegion(Model model,BaseRegion region){
		String flag = "";
		if(region != null){
			//执行追加
			region.setState(1);
			baseRegionService.insertBaseRegion(region);
			flag = "add_success";
		}else{
			flag = "add_error";
		}
		return "redirect:getBaseRegion_0_0_0?flag="+flag;
	}
	
	
	/**
	 * 删除行政区划数据
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("delete_region")
	public String deleteRegion(Model model,String id){
		String flag = "";
		if(id != null && !"".equals(id)){
			baseRegionService.deleteBaseRegion(Integer.parseInt(id));
			flag = "delete_success";
		}else{
			flag = "delete_error";
		}
		return "redirect:getBaseRegion_0_0_0?flag="+flag;
	}
	
	
	//----------行政区划数据--------------------------------------------------------------------------------
}

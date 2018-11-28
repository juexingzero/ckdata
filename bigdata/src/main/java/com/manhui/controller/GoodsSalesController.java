package com.manhui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manhui.model.DayAllLog;
import com.manhui.model.ShowDataUpdate;
import com.manhui.service.DayAllLogService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * Title: GoodsSalesController
 * @description:展示信息修改控制层(后面6个)
 * @author WangSheng
 *
 */
@Controller
public class GoodsSalesController {

	@Autowired
	private DayAllLogService dayAllLogService;
	
	/**
	 * 查询商品销售排行
	 * @param model
	 * @return
	 */
	@RequestMapping("/Commodity sales rank")
	public String GoodsSalesN(Model model){
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			//System.out.println("内容："+dal.getData());
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesPlatfroms=bigData.getJSONObject(0).getJSONArray("Commondlty_sales_rank");
			
			for (int i = 0; i < SalesPlatfroms.size(); i++) {
				String title=SalesPlatfroms.getJSONObject(i).getString("title");
				String totalAmount=SalesPlatfroms.getJSONObject(i).getString("totalAmount");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(title);
				sdu.setNumber(totalAmount);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/goodsSales/SalesvolumeofMerchandise_list";
		
	}	
	
	/**
	 * 跳转到商品销售排行新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addGoodsS")
	public String addGoodsS(Model model){
		return "pages/goodsSales/SalesvolumeofMerchandise_add";
	}
	
	/**
	 * 保存商品销售排行数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveGoodsS")
	public String saveGoodsS(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String title = sdu.getName();
			String channalSales = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(0).getJSONArray("Commondlty_sales_rank");
				//System.out.println("内容="+SalesPlatfroms);
				JSONObject addjson = new JSONObject();
				addjson.put("title", title);
				addjson.put("totalAmount", channalSales);
				SalesPlatfroms.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(0, "SalesVolumeMarchandise");
				bigDatas.put("SalesPlatfroms", SalesPlatfroms);
				bigData.element(0, bigDatas);
				json.element("bigData", bigData);
				//将数据重新加入数据库
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}else{
				//重新获取全部json数据
				DayAllLog dals=dayAllLogService.findDayAllLogHistory();
				JSONObject json =JSONObject.fromObject(dals.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(0).getJSONArray("Commondlty_sales_rank");
				JSONObject addjson = new JSONObject();
				addjson.put("title", title);
				addjson.put("totalAmount", channalSales);
				SalesPlatfroms.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(0, "Commondlty_sales_rank");
				bigDatas.put("Commondlty_sales_rank", SalesPlatfroms);
				bigData.element(0, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Commodity sales rank";
	}
	
	
	/**
	 * 跳转到商品销售排行修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editGoodsS")
	public String editGoodsS(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(0).getJSONArray("Commondlty_sales_rank");
				String channalName = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("title");
				String channalSeles = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("totalAmount");
				sdu.setName(channalName);
				sdu.setNumber(channalSeles);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/goodsSales/SalesvolumeofMerchandise_update";
	}
	
	/**
	 * 更新商品销售排行数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateGoodsS")
	public String updateGoodsS(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String newName = sdu.getName();
			String newSales = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(0).getJSONArray("Commondlty_sales_rank");
				JSONObject addjson = new JSONObject();
				addjson.put("title", newName);
				addjson.put("totalAmount", newSales);
				SalesPlatfroms.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(0, "Commondlty_sales_rank");
				bigDatas.put("Commondlty_sales_rank", SalesPlatfroms);
				bigData.element(0, bigDatas);
				json.element("bigData", bigData);
				System.out.println("json="+json);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Commodity sales rank";
	}
	
	/**
	 * 删除商品销售排行数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteGoodsS")
	public String deleteGoodsS(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(0).getJSONArray("Commondlty_sales_rank");
				SalesPlatfroms.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(0, "Commondlty_sales_rank");
				bigDatas.put("Commondlty_sales_rank", SalesPlatfroms);
				bigData.element(0, bigDatas);
				json.element("bigData", bigData);
				//System.out.println("json="+json);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Commodity sales rank";
	}
	
	//====================================================================================
	
	
	/**
	 * 查询店铺销售排行
	 * @param model
	 * @return
	 */
	@RequestMapping("/The_store_sales_rank")
	public String BestThestoresalesrank(Model model){
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesPlatfroms=bigData.getJSONObject(1).getJSONArray("The_store_sales_rank");
			
			for (int i = 0; i < SalesPlatfroms.size(); i++) {
				String shopName=SalesPlatfroms.getJSONObject(i).getString("name");
				String totalAmount=SalesPlatfroms.getJSONObject(i).getString("totalAmount");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(shopName);
				sdu.setNumber(totalAmount);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/goodsSales/Thestoresalesrank_list";
		
	}	
	
	/**
	 * 跳转到店铺销售排行新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addThestoresalesrank")
	public String addThestoresalesrank(Model model){
		return "pages/goodsSales/Thestoresalesrank_add";
	}
	
	/**
	 * 保存店铺销售排行数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveThestoresalesrank")
	public String saveThestoresalesrank(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String channalName = sdu.getName();
			String channalSales = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(1).getJSONArray("The_store_sales_rank");
				JSONObject addjson = new JSONObject();
				addjson.put("name", channalName);
				addjson.put("totalAmount", channalSales);
				SalesPlatfroms.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(1, "The_store_sales_rank");
				bigDatas.put("The_store_sales_rank", SalesPlatfroms);
				bigData.element(1, bigDatas);
				json.element("bigData", bigData);
				//将数据重新加入数据库
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}else{
				//重新获取全部json数据
				DayAllLog dals=dayAllLogService.findDayAllLogHistory();
				JSONObject json =JSONObject.fromObject(dals.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(1).getJSONArray("The_store_sales_rank");
				JSONObject addjson = new JSONObject();
				addjson.put("name", channalName);
				addjson.put("totalAmount", channalSales);
				SalesPlatfroms.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(1, "The_store_sales_rank");
				bigDatas.put("The_store_sales_rank", SalesPlatfroms);
				bigData.element(1, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:The_store_sales_rank";
	}
	
	
	/**
	 * 跳转到店铺销售排行修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editThestoresalesrank")
	public String editThestoresalesrank(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(1).getJSONArray("The_store_sales_rank");
				String channalName = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("name");
				String channalSeles = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("totalAmount");
				sdu.setName(channalName);
				sdu.setNumber(channalSeles);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/goodsSales/Thestoresalesrank_update";
	}
	
	/**
	 * 更新店铺销售排行数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateThestoresalesrank")
	public String updateThestoresalesrank(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String newName = sdu.getName();
			String newSales = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(1).getJSONArray("The_store_sales_rank");
				JSONObject addjson = new JSONObject();
				addjson.put("name", newName);
				addjson.put("totalAmount", newSales);
				SalesPlatfroms.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(1, "The_store_sales_rank");
				bigDatas.put("The_store_sales_rank", SalesPlatfroms);
				bigData.element(1, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:The_store_sales_rank";
	}
	
	/**
	 * 删除店铺销售排行数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteThestoresalesrank")
	public String deleteThestoresalesrank(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(1).getJSONArray("The_store_sales_rank");
				SalesPlatfroms.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(1, "The_store_sales_rank");
				bigDatas.put("The_store_sales_rank", SalesPlatfroms);
				bigData.element(1, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:The_store_sales_rank";
	}
	
	
	//====================================================================================
	
	
	/**
	 * 查询地区销售排行
	 * @param model
	 * @return
	 */
	@RequestMapping("/The_region_sales_rank")
	public String Theregionsalesrank(Model model){
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesPlatfroms=bigData.getJSONObject(2).getJSONArray("The_region_sales_rank");
			
			for (int i = 0; i < SalesPlatfroms.size(); i++) {
				String regionName=SalesPlatfroms.getJSONObject(i).getString("name");
				String score=SalesPlatfroms.getJSONObject(i).getString("totalAmount");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(regionName);
				sdu.setNumber(score);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/goodsSales/Theregionsalesrank_list";
		
	}	
	
	/**
	 * 跳转到地区销售排行新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addTheregionsalesrank")
	public String addTheregionsalesrank(Model model){
		return "pages/goodsSales/Theregionsalesrank_add";
	}
	
	/**
	 * 保存地区销售排行数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveTheregionsalesrank")
	public String saveTheregionsalesrank(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String channalName = sdu.getName();
			String channalSales = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(2).getJSONArray("The_region_sales_rank");
				JSONObject addjson = new JSONObject();
				addjson.put("name", channalName);
				addjson.put("totalAmount", channalSales);
				SalesPlatfroms.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(2, "The_region_sales_rank");
				bigDatas.put("The_region_sales_rank", SalesPlatfroms);
				bigData.element(2, bigDatas);
				json.element("bigData", bigData);
				//将数据重新加入数据库
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}else{
				//重新获取全部json数据
				DayAllLog dals=dayAllLogService.findDayAllLogHistory();
				JSONObject json =JSONObject.fromObject(dals.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(2).getJSONArray("The_region_sales_rank");
				JSONObject addjson = new JSONObject();
				addjson.put("name", channalName);
				addjson.put("totalAmount", channalSales);
				SalesPlatfroms.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(2, "The_region_sales_rank");
				bigDatas.put("The_region_sales_rank", SalesPlatfroms);
				bigData.element(2, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:The_region_sales_rank";
	}
	
	
	/**
	 * 跳转到地区销售排行修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editTheregionsalesrank")
	public String editTheregionsalesrank(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(2).getJSONArray("The_region_sales_rank");
				String channalName = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("name");
				String channalSeles = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("totalAmount");
				sdu.setName(channalName);
				sdu.setNumber(channalSeles);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/goodsSales/Theregionsalesrank_update";
	}
	
	/**
	 * 更新地区销售排行数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateTheregionsalesrank")
	public String updateTheregionsalesrank(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String newName = sdu.getName();
			String newSales = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(2).getJSONArray("The_region_sales_rank");
				JSONObject addjson = new JSONObject();
				addjson.put("name", newName);
				addjson.put("totalAmount", newSales);
				SalesPlatfroms.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(2, "The_region_sales_rank");
				bigDatas.put("The_region_sales_rank", SalesPlatfroms);
				bigData.element(2, bigDatas);
				json.element("bigData", bigData);
				System.out.println("json="+json);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:The_region_sales_rank";
	}
	
	/**
	 * 删除地区销售排行数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteTheregionsalesrank")
	public String deleteTheregionsalesrank(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(2).getJSONArray("The_region_sales_rank");
				SalesPlatfroms.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(2, "The_region_sales_rank");
				bigDatas.put("The_region_sales_rank", SalesPlatfroms);
				bigData.element(2, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:The_region_sales_rank";
	}
	
	//====================================================================================
	
	
	/**
	 * 查询类目价格趋势
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/Price_trend_of_merchandise")
	public String PriceTOM(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesPlatfroms=bigData.getJSONObject(11).getJSONArray("Price_trend_of_merchandise");
			SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < SalesPlatfroms.size(); i++) {
				String articleName=SalesPlatfroms.getJSONObject(i).getString("leimu");
				String articlePrice=SalesPlatfroms.getJSONObject(i).getString("num");
				String time=SalesPlatfroms.getJSONObject(i).getString("addTime");
				ShowDataUpdate sdu = new ShowDataUpdate();
				if(time.length()>10){
					sdu.setSj(sdf2.format(sdf1.parse(time)));
				}else{
					sdu.setSj(time);
				}
				
				sdu.setName(articleName);
				sdu.setNumber(articlePrice);
				
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/goodsSales/PriceTOM_list";
		
	}	
	
	/**
	 * 跳转到类目价格趋势新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addPriceTOM")
	public String addPriceTOM(Model model){
		return "pages/goodsSales/PriceTOM_add";
	}
	
	/**
	 * 保存类目价格趋势数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/savePriceTOM")
	public String savePriceTOM(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String leimu = sdu.getName();
			String num = sdu.getNumber();
			String addTime = sdu.getSj();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(11).getJSONArray("Price_trend_of_merchandise");
				JSONObject addjson = new JSONObject();
				addjson.put("leimu", leimu);
				addjson.put("num", num);
				addjson.put("addTime", addTime);
				SalesPlatfroms.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(11, "Price_trend_of_merchandise");
				bigDatas.put("Price_trend_of_merchandise", SalesPlatfroms);
				bigData.element(11, bigDatas);
				json.element("bigData", bigData);
				//将数据重新加入数据库
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}else{
				//重新获取全部json数据
				DayAllLog dals=dayAllLogService.findDayAllLogHistory();
				JSONObject json =JSONObject.fromObject(dals.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(11).getJSONArray("Price_trend_of_merchandise");
				JSONObject addjson = new JSONObject();
				addjson.put("leimu", leimu);
				addjson.put("num", num);
				addjson.put("addTime", addTime);
				SalesPlatfroms.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(11, "Price_trend_of_merchandise");
				bigDatas.put("Price_trend_of_merchandise", SalesPlatfroms);
				bigData.element(11, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Price_trend_of_merchandise";
	}
	
	
	/**
	 * 跳转到类目价格趋势修改页面
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/editPriceTOM")
	public String editPriceTOM(Model model,String id) throws ParseException{
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(11).getJSONArray("Price_trend_of_merchandise");
				String channalName = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("leimu");
				String channalSeles = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("num");
				String addTime = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("addTime");
				SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
				SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd");
				if(addTime.length()>10){
					sdu.setSj(sdf2.format(sdf1.parse(addTime)));
				}else{
					sdu.setSj(addTime);
				}
				sdu.setName(channalName);
				sdu.setNumber(channalSeles);
				
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/goodsSales/PriceTOM_update";
	}
	
	/**
	 * 更新商品价格趋势数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updatePriceTOM")
	public String updatePriceTOM(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String leimu = sdu.getName();
			String num = sdu.getNumber();
			String addTime = sdu.getSj();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(11).getJSONArray("Price_trend_of_merchandise");
				JSONObject addjson = new JSONObject();
				addjson.put("leimu", leimu);
				addjson.put("num", num);
				addjson.put("addTime", addTime);
				SalesPlatfroms.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(11, "Price_trend_of_merchandise");
				bigDatas.put("Price_trend_of_merchandise", SalesPlatfroms);
				bigData.element(11, bigDatas);
				json.element("bigData", bigData);
				System.out.println("json="+json);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Price_trend_of_merchandise";
	}
	
	/**
	 * 删除商品价格趋势数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deletePriceTOM")
	public String deletePriceTOM(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(11).getJSONArray("Price_trend_of_merchandise");
				SalesPlatfroms.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(11, "Price_trend_of_merchandise");
				bigDatas.put("Price_trend_of_merchandise", SalesPlatfroms);
				bigData.element(11, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Price_trend_of_merchandise";
	}
	
	
	//====================================================================================
	
	
	/**
	 * 查询实时交易
	 * @param model
	 * @return
	 */
	@RequestMapping("/Recent_time_trading")
	public String Recenttimetrading(Model model){
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesPlatfroms=bigData.getJSONObject(3).getJSONArray("Recent_time_trading");
			
			for (int i = 0; i < SalesPlatfroms.size(); i++) {
				String title=SalesPlatfroms.getJSONObject(i).getString("title");
				String name=SalesPlatfroms.getJSONObject(i).getString("name");
				String quantity=SalesPlatfroms.getJSONObject(i).getString("quantity");
				String sellPrice=SalesPlatfroms.getJSONObject(i).getString("sellPrice");
				String leibie=SalesPlatfroms.getJSONObject(i).getString("leibie");
				String leimu=SalesPlatfroms.getJSONObject(i).getString("leimu");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(title);
				sdu.setNamet(name);
				sdu.setNumber(quantity);
				sdu.setMoney(sellPrice);
				sdu.setLeibie(leibie);
				sdu.setLeimu(leimu);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/goodsSales/Recenttimetrading_list";
		
	}	
	
	/**
	 * 跳转到实时交易新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addRecenttimetrading")
	public String addRecenttimetrading(Model model){
		return "pages/goodsSales/Recenttimetrading_add";
	}
	
	/**
	 * 保存实时交易数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveRecenttimetrading")
	public String saveRecenttimetrading(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String title = sdu.getName();
			String name = sdu.getNamet();
			String quantity=sdu.getNumber();
			String money=sdu.getMoney();
			String leibie=sdu.getLeibie();
			String leimu=sdu.getLeimu();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(3).getJSONArray("Recent_time_trading");
				JSONObject addjson = new JSONObject();
				addjson.put("title", title);
				addjson.put("name", name);
				addjson.put("quantity", quantity);
				addjson.put("sellPrice", money);
				addjson.put("leibie", leibie);
				addjson.put("leimu", leimu);
				SalesPlatfroms.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(3, "Recent_time_trading");
				bigDatas.put("Recent_time_trading", SalesPlatfroms);
				bigData.element(3, bigDatas);
				json.element("bigData", bigData);
				//将数据重新加入数据库
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}else{
				//重新获取全部json数据
				DayAllLog dals=dayAllLogService.findDayAllLogHistory();
				JSONObject json =JSONObject.fromObject(dals.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(3).getJSONArray("Recent_time_trading");
				JSONObject addjson = new JSONObject();
				addjson.put("title", title);
				addjson.put("name", name);
				addjson.put("quantity", quantity);
				addjson.put("sellPrice", money);
				addjson.put("leibie", leibie);
				addjson.put("leimu", leimu);
				SalesPlatfroms.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(3, "Recent_time_trading");
				bigDatas.put("Recent_time_trading", SalesPlatfroms);
				bigData.element(3, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Recent_time_trading";
	}
	
	
	/**
	 * 跳转到实时交易修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editRecenttimetrading")
	public String editRecenttimetrading(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(3).getJSONArray("Recent_time_trading");
				String title = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("title");
				String name = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("name");
				String quantity = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("quantity");
				String sellPrice = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("sellPrice");
				String leibie = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("leibie");
				String leimu = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("leimu");
				sdu.setName(title);
				sdu.setNamet(name);
				sdu.setNumber(quantity);
				sdu.setMoney(sellPrice);
				sdu.setLeibie(leibie);
				sdu.setLeimu(leimu);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/goodsSales/Recenttimetrading_update";
	}
	
	/**
	 * 更新实时交易点数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateRecenttimetrading")
	public String updateRecenttimetrading(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String title = sdu.getName();
			String name = sdu.getNamet();
			String quantity=sdu.getNumber();
			String money=sdu.getMoney();
			String leibie=sdu.getLeibie();
			String leimu=sdu.getLeimu();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(3).getJSONArray("Recent_time_trading");
				JSONObject addjson = new JSONObject();
				addjson.put("title", title);
				addjson.put("name", name);
				addjson.put("quantity", quantity);
				addjson.put("sellPrice", money);
				addjson.put("leibie", leibie);
				addjson.put("leimu", leimu);
				SalesPlatfroms.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(3, "Recent_time_trading");
				bigDatas.put("Recent_time_trading", SalesPlatfroms);
				bigData.element(3, bigDatas);
				json.element("bigData", bigData);
				System.out.println("json="+json);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Recent_time_trading";
	}
	
	/**
	 * 删除实时交易数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteRecenttimetrading")
	public String deleteRecenttimetrading(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(3).getJSONArray("Recent_time_trading");
				SalesPlatfroms.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(3, "Recent_time_trading");
				bigDatas.put("Recent_time_trading", SalesPlatfroms);
				bigData.element(3, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Recent_time_trading";
	}
	
	
	/**
	 * 查询热门关键词
	 * @param model
	 * @return
	 */
	@RequestMapping("/Hot Sseller")
	public String HotSseller(Model model){
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesPlatfroms=bigData.getJSONObject(8).getJSONArray("Hot_key_words");
			
			for (int i = 0; i < SalesPlatfroms.size(); i++) {
				//热门关键词
				String kayWord=SalesPlatfroms.getJSONObject(i).getString("kayWord");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(kayWord);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/goodsSales/HotSseller_list";
		
	}	
	
	/**
	 * 跳转到热门关键词新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addHotSseller")
	public String addHotSseller(Model model){
		return "pages/goodsSales/HotSseller_add";
	}
	
	/**
	 * 保存热门关键词数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveHotSseller")
	public String saveHotSseller(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String channalName = sdu.getName();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(8).getJSONArray("Hot_key_words");
				JSONObject addjson = new JSONObject();
				addjson.put("kayWord", channalName);
				SalesPlatfroms.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(8, "Hot_key_words");
				bigDatas.put("Hot_key_words", SalesPlatfroms);
				bigData.element(8, bigDatas);
				json.element("bigData", bigData);
				//将数据重新加入数据库
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}else{
				//重新获取全部json数据
				DayAllLog dals=dayAllLogService.findDayAllLogHistory();
				JSONObject json =JSONObject.fromObject(dals.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(8).getJSONArray("Hot_key_words");
				JSONObject addjson = new JSONObject();
				addjson.put("kayWord", channalName);
				SalesPlatfroms.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(8, "Hot_key_words");
				bigDatas.put("Hot_key_words", SalesPlatfroms);
				bigData.element(8, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Hot Sseller";
	}
	
	
	/**
	 * 跳转到热门关键词修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editSseller")
	public String editSseller(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(8).getJSONArray("Hot_key_words");
				String channalName = SalesPlatfroms.getJSONObject(Integer.parseInt(id)-1).getString("kayWord");
				sdu.setName(channalName);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/goodsSales/HotSseller_update";
	}
	
	/**
	 * 更新热门关键词数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateHotSseller")
	public String updateHotSseller(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String newName = sdu.getName();
			String newSales = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(8).getJSONArray("Hot_key_words");
				JSONObject addjson = new JSONObject();
				addjson.put("kayWord", newName);
				SalesPlatfroms.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(8, "Hot_key_words");
				bigDatas.put("Hot_key_words", SalesPlatfroms);
				bigData.element(8, bigDatas);
				json.element("bigData", bigData);
				System.out.println("json="+json);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Hot Sseller";
	}
	
	/**
	 * 删除热门关键词数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteHotSseller")
	public String deleteHotSseller(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesPlatfroms=bigData.getJSONObject(8).getJSONArray("Hot_key_words");
				SalesPlatfroms.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(8, "Hot_key_words");
				bigDatas.put("Hot_key_words", SalesPlatfroms);
				bigData.element(8, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Hot Sseller";
	}
}

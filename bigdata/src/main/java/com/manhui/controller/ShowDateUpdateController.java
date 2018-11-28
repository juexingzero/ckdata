package com.manhui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * Title: ShowDateUpdateController
 * @description:展示信息修改控制层
 * @author WangSheng
 *
 */
@Controller
public class ShowDateUpdateController {

	@Autowired
	private DayAllLogService dayAllLogService;
	
	
	/**
	 * 查询平台销售额
	 * */
	@RequestMapping("/Sales of Platforms")
	public String SalesP(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray Sales_of_Platforms=bigData.getJSONObject(4).getJSONArray("Sales_of_Platforms");
			
			for (int i = 0; i < Sales_of_Platforms.size(); i++) {
				String name=Sales_of_Platforms.getJSONObject(i).getString("name");
				String totalAmount=Sales_of_Platforms.getJSONObject(i).getString("totalAmount");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(name);
				sdu.setNumber(totalAmount);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/showDataUpdate/salesofplatforms_list";
	}
	
	
	/**
	 * 跳转到平台销售额新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addSoP")
	public String addSop(Model model){
		return "pages/showDataUpdate/salesofplatforms_add";
	}
	
	
	/**
	 * 保存平台销售额数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveSoP")
	public String saveSop(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String name = sdu.getName();
			String totalAmount = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Sales_of_Platforms=bigData.getJSONObject(4).getJSONArray("Sales_of_Platforms");
				//System.out.println("内容="+SalesPlatfroms);
				JSONObject addjson = new JSONObject();
				addjson.put("name", name);
				addjson.put("totalAmount", totalAmount);
				Sales_of_Platforms.add(addjson);
				//System.out.println("内容1="+SalesPlatfroms);
				JSONObject bigDatas = (JSONObject)bigData.set(4, "Sales_of_Platforms");
				bigDatas.put("Sales_of_Platforms", Sales_of_Platforms);
				bigData.element(4, bigDatas);
				//System.out.println("大数据1="+bigData);
				json.element("bigData", bigData);
				//System.out.println("整个数据="+json);
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
				JSONArray Sales_of_Platforms=bigData.getJSONObject(4).getJSONArray("Sales_of_Platforms");
				JSONObject addjson = new JSONObject();
				addjson.put("name", name);
				addjson.put("totalAmount", totalAmount);
				Sales_of_Platforms.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(4, "Sales_of_Platforms");
				bigDatas.put("Sales_of_Platforms", Sales_of_Platforms);
				bigData.element(4, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Sales of Platforms";
	}
	
	
	/**
	 * 跳转到平台销售额修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editSoP")
	public String editSop(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Sales_of_Platforms=bigData.getJSONObject(4).getJSONArray("Sales_of_Platforms");
				String name = Sales_of_Platforms.getJSONObject(Integer.parseInt(id)-1).getString("name");
				String totalAmount = Sales_of_Platforms.getJSONObject(Integer.parseInt(id)-1).getString("totalAmount");
				sdu.setName(name);
				sdu.setNumber(totalAmount);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/showDataUpdate/salesofplatforms_update";
	}
	
	
	
	/**
	 * 更新平台销售额数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateSoP")
	public String updateSop(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String newName = sdu.getName();
			String newSales = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Sales_of_Platforms=bigData.getJSONObject(4).getJSONArray("Sales_of_Platforms");
				JSONObject addjson = new JSONObject();
				addjson.put("name", newName);
				addjson.put("totalAmount", newSales);
				Sales_of_Platforms.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(4, "Sales_of_Platforms");
				bigDatas.put("Sales_of_Platforms", Sales_of_Platforms);
				bigData.element(4, bigDatas);
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
		
		return "redirect:Sales of Platforms";
	}
	
	
	
	/**
	 * 删除平台销售额数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSoP")
	public String deleteSop(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Sales_of_Platforms=bigData.getJSONObject(4).getJSONArray("Sales_of_Platforms");
				Sales_of_Platforms.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(4, "Sales_of_Platforms");
				bigDatas.put("Sales_of_Platforms", Sales_of_Platforms);
				bigData.element(4, bigDatas);
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
		
		return "redirect:Sales of Platforms";
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------	
	
	/**
	 * 查询平台销售量
	 * */
	/*@RequestMapping("/Sales volume of Platforms")
	public String Svop(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesPlatfroms=bigData.getJSONObject(5).getJSONArray("SalesVolume");
			
			for (int i = 0; i < SalesPlatfroms.size(); i++) {
				String channelName=SalesPlatfroms.getJSONObject(i).getString("channelName");
				String totalAmount=SalesPlatfroms.getJSONObject(i).getString("totalAmount");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(channelName);
				sdu.setNumber(totalAmount);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/showDataUpdate/salesvolumeofplatforms_list";
	}
	
	
	*//**
	 * 跳转到平台销售量新增页面
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/addSvoP")
	public String addSvoP(Model model){
		return "pages/showDataUpdate/salesvolumeofplatforms_add";
	}
	
	
	*//**
	 * 保存平台销售量数据
	 * @param model
	 * @param sdu
	 * @return
	 *//*
	@RequestMapping("/saveSvoP")
	public String saveSvoP(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String channalName = sdu.getName();
			String channalNumber = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesVolume=bigData.getJSONObject(5).getJSONArray("SalesVolume");
				//System.out.println("内容="+SalesPlatfroms);
				JSONObject addjson = new JSONObject();
				addjson.put("channelName", channalName);
				addjson.put("totalAmount", channalNumber);
				SalesVolume.add(addjson);
				//System.out.println("内容1="+SalesPlatfroms);
				JSONObject bigDatas = (JSONObject)bigData.set(5, "SalesVolume");
				bigDatas.put("SalesVolume", SalesVolume);
				bigData.element(5, bigDatas);
				//System.out.println("大数据1="+bigData);
				json.element("bigData", bigData);
				//System.out.println("整个数据="+json);
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
				JSONArray SalesVolume=bigData.getJSONObject(5).getJSONArray("SalesVolume");
				JSONObject addjson = new JSONObject();
				addjson.put("channelName", channalName);
				addjson.put("totalAmount", channalNumber);
				SalesVolume.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(5, "SalesVolume");
				bigDatas.put("SalesVolume", SalesVolume);
				bigData.element(5, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Sales volume of Platforms";
	}
	
	
	*//**
	 * 跳转到平台销售量修改页面
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/editSvoP")
	public String editSvoP(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesVolume=bigData.getJSONObject(5).getJSONArray("SalesVolume");
				String channalName = SalesVolume.getJSONObject(Integer.parseInt(id)-1).getString("channelName");
				String channalNumber = SalesVolume.getJSONObject(Integer.parseInt(id)-1).getString("totalAmount");
				sdu.setName(channalName);
				sdu.setNumber(channalNumber);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/showDataUpdate/salesvolumeofplatforms_update";
	}
	
	
	
	*//**
	 * 更新平台销售量数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 *//*
	@RequestMapping("/updateSvoP")
	public String updateSvop(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String newName = sdu.getName();
			String newNumber = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesVolume=bigData.getJSONObject(5).getJSONArray("SalesVolume");
				JSONObject addjson = new JSONObject();
				addjson.put("channelName", newName);
				addjson.put("totalAmount", newNumber);
				SalesVolume.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(5, "SalesVolume");
				bigDatas.put("SalesVolume", SalesVolume);
				bigData.element(5, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Sales volume of Platforms";
	}
	
	
	*//**
	 * 删除平台销售量数据
	 * @param model
	 * @param id
	 * @return
	 *//*
	@RequestMapping("/deleteSvoP")
	public String deleteSvop(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesVolume=bigData.getJSONObject(5).getJSONArray("SalesVolume");
				SalesVolume.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(5, "SalesVolume");
				bigDatas.put("SalesVolume", SalesVolume);
				bigData.element(5, bigDatas);
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
		
		return "redirect:Sales volume of Platforms";
	}*/
	
//-------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 查询平台店铺数
	 * */
	@RequestMapping("/Number of Shops in platforms")
	public String nosp(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray Number_of_shops_in_platforms=bigData.getJSONObject(5).getJSONArray("Number_of_Sales_in_platforms");
			
			for (int i = 0; i < Number_of_shops_in_platforms.size(); i++) {
				String name=Number_of_shops_in_platforms.getJSONObject(i).getString("name");
				String shopNum=Number_of_shops_in_platforms.getJSONObject(i).getString("shopNum");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(name);
				sdu.setNumber(shopNum);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/showDataUpdate/numberofshopsinplatforms_list";
	}
	
	
	/**
	 * 跳转到平台店铺数新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addNosp")
	public String addnosp(Model model){
		return "pages/showDataUpdate/numberofshopsinplatforms_add";
	}
	
	
	/**
	 * 保存平台店铺数数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveNosp")
	public String saveNosp(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String name = sdu.getName();
			String shopNumber = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_shops_in_platforms=bigData.getJSONObject(5).getJSONArray("Number_of_Sales_in_platforms");
				JSONObject addjson = new JSONObject();
				addjson.put("name", name);
				addjson.put("shopNum", shopNumber);
				Number_of_shops_in_platforms.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(5, "Number_of_Sales_in_platforms");
				bigDatas.put("Number_of_Sales_in_platforms", Number_of_shops_in_platforms);
				bigData.element(5, bigDatas);
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
				JSONArray Number_of_shops_in_platforms=bigData.getJSONObject(5).getJSONArray("Number_of_Sales_in_platforms");
				JSONObject addjson = new JSONObject();
				addjson.put("name", name);
				addjson.put("shopNum", shopNumber);
				Number_of_shops_in_platforms.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(5, "Number_of_Sales_in_platforms");
				bigDatas.put("Number_of_Sales_in_platforms", Number_of_shops_in_platforms);
				bigData.element(5, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Number of Shops in platforms";
	}
	
	
	/**
	 * 跳转到平台店铺数修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editNosp")
	public String editNosp(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_shops_in_platforms=bigData.getJSONObject(5).getJSONArray("Number_of_Sales_in_platforms");
				String name = Number_of_shops_in_platforms.getJSONObject(Integer.parseInt(id)-1).getString("name");
				String shopNum = Number_of_shops_in_platforms.getJSONObject(Integer.parseInt(id)-1).getString("shopNum");
				sdu.setName(name);
				sdu.setNumber(shopNum);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/showDataUpdate/numberofshopsinplatforms_update";
	}
	
	/**
	 * 更新平台店铺数数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateNosp")
	public String updateNosp(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String newName = sdu.getName();
			String newshopNum = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_shops_in_platforms=bigData.getJSONObject(5).getJSONArray("Number_of_shops_in_platforms");
				JSONObject addjson = new JSONObject();
				addjson.put("name", newName);
				addjson.put("shopNum", newshopNum);
				Number_of_shops_in_platforms.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(5, "Number_of_shops_in_platforms");
				bigDatas.put("Number_of_shops_in_platforms", Number_of_shops_in_platforms);
				bigData.element(5, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Number of Shops in platforms";
	}
	
	
	/**
	 * 删除平台店铺数数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteNosp")
	public String deleteNosp(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_shops_in_platforms=bigData.getJSONObject(5).getJSONArray("Number_of_shops_in_platforms");
				Number_of_shops_in_platforms.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(5, "Number_of_shops_in_platforms");
				bigDatas.put("Number_of_shops_in_platforms", Number_of_shops_in_platforms);
				bigData.element(5, bigDatas);
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
		
		return "redirect:Number of Shops in platforms";
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 查询物流包裹数
	 * */
	@RequestMapping("/Number of parcels")
	public String nop(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray Number_of_parcels=bigData.getJSONObject(6).getJSONArray("Number_of_parcels");
			
			for (int i = 0; i < Number_of_parcels.size(); i++) {
				String name=Number_of_parcels.getJSONObject(i).getString("name");
				String expressNum=Number_of_parcels.getJSONObject(i).getString("expressNum");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(name);
				sdu.setNumber(expressNum);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/showDataUpdate/numberofparcels_list";
	}
	
	
	/**
	 * 跳转到物流包裹数新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addNop")
	public String addnop(Model model){
		return "pages/showDataUpdate/numberofparcels_add";
	}
	
	
	/**
	 * 保存物流包裹数数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveNop")
	public String saveNop(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String name = sdu.getName();
			String expressNum = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_parcels=bigData.getJSONObject(6).getJSONArray("Number_of_parcels");
				JSONObject addjson = new JSONObject();
				addjson.put("name", name);
				addjson.put("expressNum", expressNum);
				Number_of_parcels.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(6, "Number_of_parcels");
				bigDatas.put("Number_of_parcels", Number_of_parcels);
				bigData.element(6, bigDatas);
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
				JSONArray Number_of_parcels=bigData.getJSONObject(6).getJSONArray("Number_of_parcels");
				JSONObject addjson = new JSONObject();
				addjson.put("name", name);
				addjson.put("expressNum", expressNum);
				Number_of_parcels.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(6, "Number_of_parcels");
				bigDatas.put("Number_of_parcels", Number_of_parcels);
				bigData.element(6, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Number of parcels";
	}
	
	
	/**
	 * 跳转到物流包裹数修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editNop")
	public String editNop(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_parcels=bigData.getJSONObject(6).getJSONArray("Number_of_parcels");
				String name = Number_of_parcels.getJSONObject(Integer.parseInt(id)-1).getString("name");
				String expressNum = Number_of_parcels.getJSONObject(Integer.parseInt(id)-1).getString("expressNum");
				sdu.setName(name);
				sdu.setNumber(expressNum);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/showDataUpdate/numberofparcels_update";
	}
	
	
	/**
	 * 更新物流包裹数数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateNop")
	public String updateNop(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String name = sdu.getName();
			String expressNum = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_parcels=bigData.getJSONObject(6).getJSONArray("Number_of_parcels");
				JSONObject addjson = new JSONObject();
				addjson.put("name", name);
				addjson.put("expressNum", expressNum);
				Number_of_parcels.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(6, "Number_of_parcels");
				bigDatas.put("Number_of_parcels", Number_of_parcels);
				bigData.element(6, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Number of parcels";
	}
	
	
	/**
	 * 删除平台店铺数数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteNop")
	public String deleteNop(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_parcels=bigData.getJSONObject(6).getJSONArray("Number_of_parcels");
				Number_of_parcels.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(6, "Number_of_parcels");
				bigDatas.put("Number_of_parcels", Number_of_parcels);
				bigData.element(6, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId()); 
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Number of parcels";
	}
	
	
//----------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 查询销售地区热度
	 * */
	@RequestMapping("/The heat of the sales area")
	public String soc(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesCategory=bigData.getJSONObject(7).getJSONArray("The_heat_of_the_sales_area");
			
			for (int i = 0; i < SalesCategory.size(); i++) {
				String name=SalesCategory.getJSONObject(i).getString("name");
				String totalAmount=SalesCategory.getJSONObject(i).getString("totalAmount");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(name);
				sdu.setNumber(totalAmount);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/showDataUpdate/theheatofthesalesarea_list";
	}
	
	/**
	 * 跳转到销售地区热度新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addSoc")
	public String addSoc(Model model){
		return "pages/showDataUpdate/theheatofthesalesarea_add";
	}
	
	/**
	 * 保存销售地区热度
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveSoc")
	public String saveSoc(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String name = sdu.getName();
			String totalAmount = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray The_heat_of_the_sales_area=bigData.getJSONObject(7).getJSONArray("The_heat_of_the_sales_area");
				JSONObject addjson = new JSONObject();
				addjson.put("name", name);
				addjson.put("totalAmount", totalAmount);
				The_heat_of_the_sales_area.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(7, "The_heat_of_the_sales_area");
				bigDatas.put("The_heat_of_the_sales_area", The_heat_of_the_sales_area);
				bigData.element(7, bigDatas);
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
				JSONArray The_heat_of_the_sales_area=bigData.getJSONObject(7).getJSONArray("The_heat_of_the_sales_area");
				JSONObject addjson = new JSONObject();
				addjson.put("name", name);
				addjson.put("totalAmount", totalAmount);
				The_heat_of_the_sales_area.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(7, "The_heat_of_the_sales_area");
				bigDatas.put("The_heat_of_the_sales_area", The_heat_of_the_sales_area);
				bigData.element(7, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:The heat of the sales area";
	}
	
	
	/**
	 * 跳转到类目销售额修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editSoc")
	public String editSoc(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray The_heat_of_the_sales_area=bigData.getJSONObject(7).getJSONArray("The_heat_of_the_sales_area");
				String categoryName = The_heat_of_the_sales_area.getJSONObject(Integer.parseInt(id)-1).getString("name");
				String totalAmount = The_heat_of_the_sales_area.getJSONObject(Integer.parseInt(id)-1).getString("totalAmount");
				sdu.setName(categoryName);
				sdu.setNumber(totalAmount);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/showDataUpdate/theheatofthesalesarea_update";
	}
	
	
	/**
	 * 更新类目销售额数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateSoc")
	public String updateSoc(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String categoryName = sdu.getName();
			String totalAmount = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray The_heat_of_the_sales_area=bigData.getJSONObject(7).getJSONArray("The_heat_of_the_sales_area");
				JSONObject addjson = new JSONObject();
				addjson.put("name", categoryName);
				addjson.put("totalAmount", totalAmount);
				The_heat_of_the_sales_area.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(7, "The_heat_of_the_sales_area");
				bigDatas.put("The_heat_of_the_sales_area", The_heat_of_the_sales_area);
				bigData.element(7, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:The heat of the sales area";
	}
	
	
	/**
	 * 删除类目销售额数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSoc")
	public String deleteSoc(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray The_heat_of_the_sales_area=bigData.getJSONObject(7).getJSONArray("The_heat_of_the_sales_area");
				The_heat_of_the_sales_area.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(7, "The_heat_of_the_sales_area");
				bigDatas.put("The_heat_of_the_sales_area", The_heat_of_the_sales_area);
				bigData.element(7, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId()); 
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:The heat of the sales area";
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 查询类目销售量
	 * *//*
	@RequestMapping("/Sales volume of Catalogues")
	public String svoc(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesVolumeCategory=bigData.getJSONObject(7).getJSONArray("SalesVolumeCategory");
			
			for (int i = 0; i < SalesVolumeCategory.size(); i++) {
				String categoryName=SalesVolumeCategory.getJSONObject(i).getString("categoryName");
				String dealArticleNum=SalesVolumeCategory.getJSONObject(i).getString("dealArticleNum");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(categoryName);
				sdu.setNumber(dealArticleNum);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/showDataUpdate/salesvolumeofcatalogues_list";
	}
	
	*//**
	 * 跳转到类目销售量新增页面
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/addSvoc")
	public String addSvoc(Model model){
		return "pages/showDataUpdate/salesvolumeofcatalogues_add";
	}
	
	*//**
	 * 保存类目销售量数据
	 * @param model
	 * @param sdu
	 * @return
	 *//*
	@RequestMapping("/saveSvoc")
	public String saveSvoc(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String categoryName = sdu.getName();
			String dealArticleNum = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesVolumeCategory=bigData.getJSONObject(7).getJSONArray("SalesVolumeCategory");
				JSONObject addjson = new JSONObject();
				addjson.put("categoryName", categoryName);
				addjson.put("dealArticleNum", dealArticleNum);
				SalesVolumeCategory.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(7, "NumberCategory");
				bigDatas.put("NumberCategory", SalesVolumeCategory);
				bigData.element(7, bigDatas);
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
				JSONArray SalesVolumeCategory=bigData.getJSONObject(7).getJSONArray("SalesVolumeCategory");
				JSONObject addjson = new JSONObject();
				addjson.put("categoryName", categoryName);
				addjson.put("dealArticleNum", dealArticleNum);
				SalesVolumeCategory.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(7, "NumberCategory");
				bigDatas.put("NumberCategory", SalesVolumeCategory);
				bigData.element(7, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Sales volume of Catalogues";
	}
	
	
	*//**
	 * 跳转到类目销售量修改页面
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/editSvoc")
	public String editSvoc(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesVolumeCategory=bigData.getJSONObject(7).getJSONArray("SalesVolumeCategory");
				String categoryName = SalesVolumeCategory.getJSONObject(Integer.parseInt(id)-1).getString("categoryName");
				String dealArticleNum = SalesVolumeCategory.getJSONObject(Integer.parseInt(id)-1).getString("dealArticleNum");
				sdu.setName(categoryName);
				sdu.setNumber(dealArticleNum);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/showDataUpdate/salesvolumeofcatalogues_update";
	}
	
	
	*//**
	 * 更新类目销售量数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 *//*
	@RequestMapping("/updateSvoc")
	public String updateSvoc(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String categoryName = sdu.getName();
			String dealArticleNum = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesVolumeCategory=bigData.getJSONObject(7).getJSONArray("SalesVolumeCategory");
				JSONObject addjson = new JSONObject();
				addjson.put("categoryName", categoryName);
				addjson.put("dealArticleNum", dealArticleNum);
				SalesVolumeCategory.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(7, "NumberCategory");
				bigDatas.put("NumberCategory", SalesVolumeCategory);
				bigData.element(7, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Sales volume of Catalogues";
	}
	
	
	*//**
	 * 删除类目销售量数据
	 * @param model
	 * @param id
	 * @return
	 *//*
	@RequestMapping("/deleteSvoc")
	public String deleteSvoc(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesVolumeCategory=bigData.getJSONObject(7).getJSONArray("SalesVolumeCategory");
				SalesVolumeCategory.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(7, "NumberCategory");
				bigDatas.put("NumberCategory", SalesVolumeCategory);
				bigData.element(7, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId()); 
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Sales volume of Catalogues";
	}*/
	
//---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 查询类目店铺数
	 * */
	@RequestMapping("/Number of Shops in Catalogues")
	public String nosc(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray Number_of_shops_in_categories=bigData.getJSONObject(9).getJSONArray("Number_of_shops_in_categories");
			
			for (int i = 0; i < Number_of_shops_in_categories.size(); i++) {
				String leimu=Number_of_shops_in_categories.getJSONObject(i).getString("leimu");
				String shopNum=Number_of_shops_in_categories.getJSONObject(i).getString("shopNum");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(leimu);
				sdu.setNumber(shopNum);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/showDataUpdate/numberofshopsincatalogues_list";
	}
	
	
	/**
	 * 跳转到类目店铺数新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addNosc")
	public String addNosc(Model model){
		return "pages/showDataUpdate/numberofshopsincatalogues_add";
	}
	
	
	/**
	 * 保存类目店铺数数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveNosc")
	public String saveNosc(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String leimu = sdu.getName();
			String shopNum = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_shops_in_categories=bigData.getJSONObject(9).getJSONArray("Number_of_shops_in_categories");
				JSONObject addjson = new JSONObject();
				addjson.put("leimu", leimu);
				addjson.put("shopNum", shopNum);
				Number_of_shops_in_categories.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(9, "Number_of_shops_in_categories");
				bigDatas.put("Number_of_shops_in_categories", Number_of_shops_in_categories);
				bigData.element(9, bigDatas);
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
				JSONArray Number_of_shops_in_categories=bigData.getJSONObject(9).getJSONArray("Number_of_shops_in_categories");
				JSONObject addjson = new JSONObject();
				addjson.put("leimu", leimu);
				addjson.put("shopNum", shopNum);
				Number_of_shops_in_categories.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(9, "Number_of_shops_in_categories");
				bigDatas.put("Number_of_shops_in_categories", Number_of_shops_in_categories);
				bigData.element(9, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Number of Shops in Catalogues";
	}
	
	/**
	 * 跳转到类目店铺数修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editNosc")
	public String editNosc(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_shops_in_categories=bigData.getJSONObject(9).getJSONArray("Number_of_shops_in_categories");
				String leimu = Number_of_shops_in_categories.getJSONObject(Integer.parseInt(id)-1).getString("leimu");
				String shopNum = Number_of_shops_in_categories.getJSONObject(Integer.parseInt(id)-1).getString("shopNum");
				sdu.setName(leimu);
				sdu.setNumber(shopNum);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/showDataUpdate/numberofshopsincatalogues_update";
	}
	
	
	/**
	 * 更新类目店铺数数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateNosc")
	public String updateNosc(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String categoryName = sdu.getName();
			String shopNum = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_shops_in_categories=bigData.getJSONObject(9).getJSONArray("Number_of_shops_in_categories");
				JSONObject addjson = new JSONObject();
				addjson.put("leimu", categoryName);
				addjson.put("shopNum", shopNum);
				Number_of_shops_in_categories.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(9, "Number_of_shops_in_categories");
				bigDatas.put("Number_of_shops_in_categories", Number_of_shops_in_categories);
				bigData.element(9, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Number of Shops in Catalogues";
	}
	
	/**
	 * 删除类目店铺数数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteNosc")
	public String deleteNosc(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Number_of_shops_in_categories=bigData.getJSONObject(9).getJSONArray("Number_of_shops_in_categories");
				Number_of_shops_in_categories.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(9, "Number_of_shops_in_categories");
				bigDatas.put("Number_of_shops_in_categories", Number_of_shops_in_categories);
				bigData.element(9, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId()); 
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Number of Shops in Catalogues";
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 查询类目商品量
	 * */
	@RequestMapping("/NNumber Volume of Catalogues")
	public String nvc(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
				
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray Sales_volume_of_merchandise=bigData.getJSONObject(10).getJSONArray("Sales_volume_of_merchandise");
			
			for (int i = 0; i < Sales_volume_of_merchandise.size(); i++) {
				String leimu=Sales_volume_of_merchandise.getJSONObject(i).getString("leimu");
				String articleNum=Sales_volume_of_merchandise.getJSONObject(i).getString("articleNum");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(leimu);
				sdu.setNumber(articleNum);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/showDataUpdate/numbervolumeofcatalogues_list";
	}
	
	/**
	 * 跳转到类目商品量新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addNvc")
	public String addNvc(Model model){
		return "pages/showDataUpdate/numbervolumeofcatalogues_add";
	}
	
	/**
	 * 保存类目商品量数据
	 * @param model
	 * @param sdu
	 * @return
	 */
	@RequestMapping("/saveNvc")
	public String saveNvc(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String categoryName = sdu.getName();
			String articeNum = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Sales_volume_of_merchandise=bigData.getJSONObject(10).getJSONArray("Sales_volume_of_merchandise");
				JSONObject addjson = new JSONObject();
				addjson.put("leimu", categoryName);
				addjson.put("articleNum", articeNum);
				Sales_volume_of_merchandise.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(10, "Sales_volume_of_merchandise");
				bigDatas.put("Sales_volume_of_merchandise", Sales_volume_of_merchandise);
				bigData.element(10, bigDatas);
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
				JSONArray Sales_volume_of_merchandise=bigData.getJSONObject(10).getJSONArray("Sales_volume_of_merchandise");
				JSONObject addjson = new JSONObject();
				addjson.put("leimu", categoryName);
				addjson.put("articleNum", articeNum);
				Sales_volume_of_merchandise.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(10, "Sales_volume_of_merchandise");
				bigDatas.put("Sales_volume_of_merchandise", Sales_volume_of_merchandise);
				bigData.element(10, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:NNumber Volume of Catalogues";
	}
	
	/**
	 * 跳转到类目商品量修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/editNvc")
	public String editNvc(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Sales_volume_of_merchandise=bigData.getJSONObject(10).getJSONArray("Sales_volume_of_merchandise");
				String leimu = Sales_volume_of_merchandise.getJSONObject(Integer.parseInt(id)-1).getString("leimu");
				String articleNum = Sales_volume_of_merchandise.getJSONObject(Integer.parseInt(id)-1).getString("articleNum");
				sdu.setName(leimu);
				sdu.setNumber(articleNum);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/showDataUpdate/numbervolumeofcatalogues_update";
	}
	
	
	/**
	 * 更新类目商品量数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateNvc")
	public String updateNvc(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String categoryName = sdu.getName();
			String articeNum = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Sales_volume_of_merchandise=bigData.getJSONObject(10).getJSONArray("Sales_volume_of_merchandise");
				JSONObject addjson = new JSONObject();
				addjson.put("leimu", categoryName);
				addjson.put("articleNum", articeNum);
				Sales_volume_of_merchandise.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(10, "Sales_volume_of_merchandise");
				bigDatas.put("Sales_volume_of_merchandise", Sales_volume_of_merchandise);
				bigData.element(10, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId());
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:NNumber Volume of Catalogues";
	}
	
	/**
	 * 删除类目商品量数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteNvc")
	public String deleteNvc(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray Sales_volume_of_merchandise=bigData.getJSONObject(10).getJSONArray("Sales_volume_of_merchandise");
				Sales_volume_of_merchandise.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(10, "Sales_volume_of_merchandise");
				bigDatas.put("Sales_volume_of_merchandise", Sales_volume_of_merchandise);
				bigData.element(10,bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId()); 
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:NNumber Volume of Catalogues";
	}

//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 查询商品销售额
	 * *//*
	@RequestMapping("/Sales of Merchandise")
	public String som(Model model) throws ParseException{
		DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
		List<ShowDataUpdate> sdulist = new ArrayList<>();
		if(dal != null){
			JSONObject json =JSONObject.fromObject(dal.getData());
			
			JSONArray bigData=json.getJSONArray("bigData");
			
			JSONArray SalesMarchandise=bigData.getJSONObject(8).getJSONArray("SalesMarchandise");
			
			for (int i = 0; i < SalesMarchandise.size(); i++) {
				String channelName=SalesMarchandise.getJSONObject(i).getString("channelName");
				String totalAmount=SalesMarchandise.getJSONObject(i).getString("totalAmount");
				ShowDataUpdate sdu = new ShowDataUpdate();
				sdu.setName(channelName);
				sdu.setNumber(totalAmount);
				sdulist.add(sdu);
			}
		}
		
		model.addAttribute("sdu",sdulist);
		return "pages/showDataUpdate/salesofmerchandise_list";
	}
	
	
	*//**
	 * 跳转到商品销售额新增页面
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/addSom")
	public String addSom(Model model){
		return "pages/showDataUpdate/salesofmerchandise_add";
	}
	
	*//**
	 * 保存商品销售额数据
	 * @param model
	 * @param sdu
	 * @return
	 *//*
	@RequestMapping("/saveSom")
	public String saveSom(Model model,ShowDataUpdate sdu){
		
		if(sdu != null){
			String channelName = sdu.getName();
			String totalAmount = sdu.getNumber();
			
			DayAllLog dal=dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesMarchandise=bigData.getJSONObject(8).getJSONArray("SalesMarchandise");
				JSONObject addjson = new JSONObject();
				addjson.put("channelName", channelName);
				addjson.put("totalAmount", totalAmount);
				SalesMarchandise.add(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(8, "SalesMarchandise");
				bigDatas.put("SalesMarchandise", SalesMarchandise);
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
				JSONArray SalesMarchandise=bigData.getJSONObject(8).getJSONArray("SalesMarchandise");
				JSONObject addjson = new JSONObject();
				addjson.put("channelName", channelName);
				addjson.put("totalAmount", totalAmount);
				SalesMarchandise.element(addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(8, "SalesMarchandise");
				bigDatas.put("SalesMarchandise", SalesMarchandise);
				bigData.element(8, bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				dayAllLogService.saveDayAllLog(newdal);
			}
		}
		
		return "redirect:Sales of Merchandise";
	}
	
	*//**
	 * 跳转到商品销售额修改页面
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/editSom")
	public String editSom(Model model,String id){
		ShowDataUpdate sdu = new ShowDataUpdate();
		if(id != null && !"".equals(id)){
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesMarchandise=bigData.getJSONObject(8).getJSONArray("SalesMarchandise");
				String channelName = SalesMarchandise.getJSONObject(Integer.parseInt(id)-1).getString("channelName");
				String totalAmount = SalesMarchandise.getJSONObject(Integer.parseInt(id)-1).getString("totalAmount");
				sdu.setName(channelName);
				sdu.setNumber(totalAmount);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("sdu", sdu);
		return "pages/showDataUpdate/salesofmerchandise_update";
	}
	
	
	*//**
	 * 更新商品销售额数据
	 * @param model
	 * @param sdu
	 * @param id
	 * @return
	 *//*
	@RequestMapping("/updateSom")
	public String updateSom(Model model,ShowDataUpdate sdu,String id){
		
		if(sdu != null ){
			
			String channelName = sdu.getName();
			String totalAmount = sdu.getNumber();
			
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesMarchandise=bigData.getJSONObject(8).getJSONArray("SalesMarchandise");
				JSONObject addjson = new JSONObject();
				addjson.put("channelName", channelName);
				addjson.put("totalAmount", totalAmount);
				SalesMarchandise.set(Integer.parseInt(id)-1, addjson);
				JSONObject bigDatas = (JSONObject)bigData.set(8, "SalesMarchandise");
				bigDatas.put("SalesMarchandise", SalesMarchandise);
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
		
		return "redirect:Sales of Merchandise";
	}
	
	*//**
	 * 删除商品销售额数据
	 * @param model
	 * @param id
	 * @return
	 *//*
	@RequestMapping("/deleteSom")
	public String deleteSom(Model model,String id){
		if(id != null && !"".equals(id)){
			int actionId = Integer.parseInt(id) - 1;
			DayAllLog dal = dayAllLogService.findDayAllLogByDate(new Date());
			if(dal != null){
				JSONObject json =JSONObject.fromObject(dal.getData());
				JSONArray bigData=json.getJSONArray("bigData");
				JSONArray SalesMarchandise=bigData.getJSONObject(8).getJSONArray("SalesMarchandise");
				SalesMarchandise.remove(actionId); 
				JSONObject bigDatas = (JSONObject)bigData.set(8, "SalesMarchandise");
				bigDatas.put("SalesMarchandise", SalesMarchandise);
				bigData.element(8,bigDatas);
				json.element("bigData", bigData);
				Date now = new Date();
				DayAllLog newdal = new DayAllLog();
				newdal.setAddTime(now);
				newdal.setData(json.toString());
				newdal.setId(dal.getId()); 
				dayAllLogService.updateDayAllLog(newdal);
			}
		}
		
		return "redirect:Sales of Merchandise";
	}*/
}

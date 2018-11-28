package com.manhui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.model.BaseObject;
import com.manhui.model.Express;
import com.manhui.model.OrderExpressOtherLog;
import com.manhui.service.ExpressService;
import com.manhui.service.OrderExpressOtherLogService;

@Controller
public class OrderExpressOtherLogController {

	@Autowired
	private OrderExpressOtherLogService orderExpressOtherLogService;
	
	@Autowired
	private ExpressService expressService;
	
	//移动端展示物流数据
	@RequestMapping("mobile_express_show")
	@ResponseBody
	public List<OrderExpressOtherLog> showExpressOtherLog(String expressName,String selectId) {
		
		List<OrderExpressOtherLog> oeolList = new ArrayList<>();
		if(selectId != null && !"".equals(selectId)){
			if("0".equals(selectId)){
				//全部
				if(expressName != null && !"".equals(expressName)){
					String name = "%"+expressName+"%";
					oeolList=orderExpressOtherLogService.findAllOrderExpressOtherLog(name);
				}else{
					oeolList=orderExpressOtherLogService.findAllOrderExpressOtherLog(null);
					
				}
			}else if("1".equals(selectId)){
				//收件信息
				if(expressName != null && !"".equals(expressName)){
					String name = "%"+expressName+"%";
					oeolList=orderExpressOtherLogService.findInputOrderExpressOtherLog(name);
				}else{
					oeolList=orderExpressOtherLogService.findInputOrderExpressOtherLog(null);
				}
			}else{
				//出件信息
				if(expressName != null && !"".equals(expressName)){
					String name = "%"+expressName+"%";
					oeolList=orderExpressOtherLogService.findOutputOrderExpressOtherLog(name);
				}else{
					oeolList=orderExpressOtherLogService.findOutputOrderExpressOtherLog(null);
				}
			}
		}
		System.out.println("数量"+oeolList.size());
		return oeolList;
	}
	
	
	//移动端添加物流数据
	@RequestMapping("mobile_express_save")
	@ResponseBody
	public BaseObject ExpressOtherLogSave(OrderExpressOtherLog oeol){
		BaseObject bo = new  BaseObject();
		if(oeol != null){
			//先查询物流信息
			List<Express> express = expressService.findExpressByName(oeol.getExpressName());
			oeol.setExpressId(express.get(0).getId());
			if(oeol.getSelectId() == 1){
				//收件
				oeol.setOutputNum(0);
				oeol.setInputNum(oeol.getNumber());
			}else if(oeol.getSelectId() == 2){
				//出件
				oeol.setInputNum(0);
				oeol.setOutputNum(oeol.getNumber());
			}
			orderExpressOtherLogService.saveOrderExpressOtherLog(oeol);
			bo.setFlag("success");
		}else{
			bo.setFlag("faild");
		}
		return bo;
	}
}

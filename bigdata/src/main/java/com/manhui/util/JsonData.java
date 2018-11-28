package com.manhui.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.manhui.model.DayArticleLog;
import com.manhui.model.DayCategoryLog;
import com.manhui.model.DayChannelLog;
import com.manhui.model.DayRegionLog;
import com.manhui.model.DayShopLog;
import com.manhui.model.KeyWord;
import com.manhui.model.Order;
import com.manhui.model.OrderExpressLog;
import com.manhui.service.DayAllLogService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class JsonData {

	//Json字符串拼接
	public static JSONObject saveJson(List<DayArticleLog> dals,List<DayShopLog> dayShopLogs,List<DayRegionLog> drl,List<Order> orderlist,
						List<DayChannelLog> dclx,List<DayChannelLog> daydp,List<OrderExpressLog> oels,List<DayRegionLog> drlrd,
						List<KeyWord> kw,List<DayCategoryLog> dctlse,List<DayCategoryLog> apls,List<DayChannelLog> dcl){
		
		JSONArray bigdatas = new JSONArray();
		//商品销售排行
		if(dals != null && dals.size() > 0){
			JSONObject csr = new JSONObject();
			JSONArray csrs = new JSONArray();
			for(DayArticleLog data : dals){
				JSONObject dataJson = new JSONObject();
				dataJson.put("title", data.getArticle().getTitle());
				dataJson.put("totalAmount", data.getTotalAmount());
				csrs.add(dataJson);
			}
			csr.put("Commondlty_sales_rank", csrs);
			bigdatas.add(0, csr);
		}
		
		//店铺销售排行
		if(dayShopLogs != null && dayShopLogs.size() > 0){
			JSONObject tssr = new JSONObject();
			JSONArray tssrs = new JSONArray();
			for(DayShopLog data : dayShopLogs){
				JSONObject dataJson = new JSONObject();
				dataJson.put("name", data.getShop().getName());
				dataJson.put("totalAmount", data.getTotalAmount());
				tssrs.add(dataJson);
			}
			tssr.put("The_store_sales_rank", tssrs);
			bigdatas.add(1, tssr);
		}
		
		//地区销售排行
		if(drl != null && drl.size() > 0){
			JSONObject trsr = new JSONObject();
			JSONArray trsrs = new JSONArray();
			for(DayRegionLog data : drl){
				JSONObject dataJson = new JSONObject();
				dataJson.put("name", data.getBaseRegion().getName());
				dataJson.put("totalAmount", data.getTotalAmount());
				trsrs.add(dataJson);
			}
			trsr.put("The_region_sales_rank", trsrs);
			bigdatas.add(2, trsr);
		}
		
		
		//实时交易
		if(orderlist != null && orderlist.size() > 0){
			JSONObject rtt = new JSONObject();
			JSONArray rtts = new JSONArray();
			for(Order data : orderlist){
				JSONObject dataJson = new JSONObject();
				dataJson.put("sellPrice", data.getSellPrice());
				dataJson.put("quantity", data.getQuantity());
				dataJson.put("name", data.getShop().getName());
				dataJson.put("title", data.getArticle().getTitle());
				dataJson.put("leibie", data.getCategory().getLeibie());
				dataJson.put("leimu", data.getCategory().getLeimu());
				rtts.add(dataJson);
			}
			rtt.put("Recent_time_trading", rtts);
			bigdatas.add(3, rtt);
		}
		
		
		//平台销售额
		if(dclx != null && dclx.size() > 0){
			JSONObject sop = new JSONObject();
			JSONArray sops = new JSONArray();
			for(DayChannelLog data : dclx){
				JSONObject dataJson = new JSONObject();
				dataJson.put("name", data.getChannel().getName());
				dataJson.put("totalAmount", data.getTotalAmount());
				sops.add(dataJson);
			}
			sop.put("Sales_of_Platforms", sops);
			bigdatas.add(4, sop);
		}
		
		//平台店铺数
		if(daydp != null && daydp.size() > 0){
			JSONObject nosip = new JSONObject();
			JSONArray nosips = new JSONArray();
			for(DayChannelLog data : daydp){
				JSONObject dataJson = new JSONObject();
				dataJson.put("name", data.getChannel().getName());
				dataJson.put("shopNum", data.getShopNum());
				nosips.add(dataJson);
			}
			nosip.put("Number_of_shops_in_platforms", nosips);
			bigdatas.add(5, nosip);
		}
		
		//包裹数
		if(oels != null && oels.size() > 0){
			JSONObject nop = new JSONObject();
			JSONArray nops = new JSONArray();
			for(OrderExpressLog data : oels){
				JSONObject dataJson = new JSONObject();
				dataJson.put("name", data.getExpress().getName());
				dataJson.put("expressNum", data.getExpressNum());
				nops.add(dataJson);
			}
			nop.put("Number_of_parcels", nops);
			bigdatas.add(6, nop);
		}
		
		
		//销售地区热度
		if(drlrd != null && drlrd.size() > 0){
			JSONObject thotsa = new JSONObject();
			JSONArray thotsas = new JSONArray();
			for(DayRegionLog data : drlrd){
				JSONObject dataJson = new JSONObject();
				dataJson.put("name", data.getBaseRegion().getName());
				dataJson.put("totalAmount", data.getTotalAmount());
				thotsas.add(dataJson);
			}
			thotsa.put("The_heat_of_the_sales_area", thotsas);
			bigdatas.add(7, thotsa);
		}
		
		
		//热门关键词
		if(kw != null && kw.size() > 0){
			JSONObject hkw = new JSONObject();
			JSONArray hkws = new JSONArray();
			for(KeyWord data : kw){
				JSONObject dataJson = new JSONObject();
				dataJson.put("kayWord", data.getKayWord());
				hkws.add(dataJson);
			}
			hkw.put("Hot_key_words", hkws);
			bigdatas.add(8, hkw);
		}
		
		
		// 类目店铺数 和 类目商品数
		if(dctlse != null && dctlse.size() > 0){
			JSONObject nosic = new JSONObject();
			JSONArray nosics = new JSONArray();
			for(DayCategoryLog data : dctlse){
				JSONObject dataJson = new JSONObject();
				dataJson.put("leimu", data.getCategory().getLeimu());
				dataJson.put("shopNum", data.getShopNum());
				nosics.add(dataJson);
			}
			nosic.put("Number_of_shops_in_categories", nosics);
			bigdatas.add(9, nosic);
		}
		
		if(dctlse != null && dctlse.size() > 0){
			JSONObject svom = new JSONObject();
			JSONArray svoms = new JSONArray();
			for(DayCategoryLog data : dctlse){
				JSONObject dataJson = new JSONObject();
				dataJson.put("leimu", data.getCategory().getLeimu());
				dataJson.put("articleNum", data.getArticleNum());
				svoms.add(dataJson);
			}
			svom.put("Sales_volume_of_merchandise", svoms);
			bigdatas.add(10, svom);
		}
		
		//类目价格趋势
		if(apls != null && apls.size() > 0){
			JSONObject ptom = new JSONObject();
			JSONArray ptoms = new JSONArray();
			for(DayCategoryLog data : apls){
				JSONObject dataJson = new JSONObject();
				dataJson.put("addTime", data.getAddTime());
				dataJson.put("num", data.getNum());
				dataJson.put("leimu", data.getCategory().getLeimu());
				ptoms.add(dataJson);
			}
			ptom.put("Price_trend_of_merchandise", ptoms);
			bigdatas.add(11, ptom);
		}
		
		
		// 商品销售额 和,店铺总数
		if(dcl != null && dcl.size() > 0){
			JSONObject sumcont = new JSONObject();
			JSONArray sumconts = new JSONArray();
			for(DayChannelLog data : dcl){
				JSONObject dataJson = new JSONObject();
				dataJson.put("sales_sum", data.getTotalAmount());
				dataJson.put("shop_num", data.getShopNum());
				sumconts.add(dataJson);
			}
			sumcont.put("top_sum_num", sumconts);
			bigdatas.add(12, sumcont);
		}
		
		
		JSONObject bigData = new JSONObject();
		bigData.put("bigData", bigdatas);
		
		System.out.println(bigData.toString());
		
		return bigData;
	}
	
	
}

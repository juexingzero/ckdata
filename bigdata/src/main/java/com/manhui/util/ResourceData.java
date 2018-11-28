package com.manhui.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.model.Article;
import com.manhui.model.ArticlePriceLog;
import com.manhui.model.BaseRegion;
import com.manhui.model.Category;
import com.manhui.model.Channel;
import com.manhui.model.DayArticleLog;
import com.manhui.model.DayCategoryLog;
import com.manhui.model.DayChannelLog;
import com.manhui.model.DayShopLog;
import com.manhui.model.Express;
import com.manhui.model.Order;
import com.manhui.model.OrderExpressLog;
import com.manhui.model.ScoreRegionLog;
import com.manhui.model.ScoreSiteLog;
import com.manhui.model.Shop;
import com.manhui.model.Site;
import com.manhui.service.ArticlePriceLogService;
import com.manhui.service.ArticleService;
import com.manhui.service.BaseRegionService;
import com.manhui.service.CategoryService;
import com.manhui.service.ChannelService;
import com.manhui.service.DayArticleLogService;
import com.manhui.service.DayCategoryLogService;
import com.manhui.service.DayChannelLogService;
import com.manhui.service.DayOrderLogService;
import com.manhui.service.DayShopLogService;
import com.manhui.service.ExpressService;
import com.manhui.service.OrderExpressLogService;
import com.manhui.service.OrderService;
import com.manhui.service.ScoreRegionLogService;
import com.manhui.service.ScoreSiteLogService;
import com.manhui.service.ShopService;
import com.manhui.service.SiteService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @ClassName: ResourceData.java
 * @description:   数据统计工具类
 * @author: Jiangxiaosong
 * @date Create in 14:33 2018/3/12
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class ResourceData {

	@Autowired 
	private DayCategoryLogService dayCategoryLogService;
	
	@Autowired
	private DayChannelLogService dayChannelLogService;
	
	@Autowired
	private DayArticleLogService dayArticleLogService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ArticlePriceLogService articlePriceLogService;
	
	@Autowired
	private DayShopLogService dayShopLogService;
	
	@Autowired
	private ScoreRegionLogService scoreRegionLogService;
	
	@Autowired
	private ScoreSiteLogService scoreSiteLogService;
	
	@Autowired
	private OrderExpressLogService orderExpressLogService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BaseRegionService baseRegionService; 
	
	@Autowired
	private SiteService siteSerivce;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ExpressService expressService;
	
	@Autowired
	private DayOrderLogService dayOrderLogService;
	
	
	/**
	 * 店铺排名(只显示前8名)
	 * @param model
	 * @return
	 * @throws JSONException 
	 */
	@ResponseBody
	@GetMapping("get_shop_rank")
	public JSONObject getShopRank(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		
		List<DayShopLog> dayShopLogs = dayShopLogService.findDayShopLogSortBytotalAmount();
		if(dayShopLogs != null && dayShopLogs.size() > 0){
			for(DayShopLog dsl : dayShopLogs){
				int index = 0;
				JSONObject json = new JSONObject();
				Shop shop = shopService.findNameById(dsl.getShopId());
				json.put("shopName", shop.getName());
				json.put("province", shop.getProvince());
				json.put("city", shop.getCity());
				json.put("district", shop.getDistrict());
				json.put("address", shop.getAddress());
				json.put("totalAmount", dsl.getTotalAmount());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("ShopRank", jsona);
		return jsons;
	}
	
	
	/**
	 * 乡镇考核排名(只显示前8名)
	 * @param model
	 * @return
	 * @throws JSONException 
	 */
	@ResponseBody
	@GetMapping("get_score_region_rank")
	public JSONObject getScoreRegionRank(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<ScoreRegionLog> ScoreRegionLogs = scoreRegionLogService.findScoreRegionLogSortByScore();
		if(ScoreRegionLogs != null && ScoreRegionLogs.size() > 0){
			for(ScoreRegionLog srl :  ScoreRegionLogs){
				int index = 0;
				JSONObject json = new JSONObject();
				BaseRegion region = baseRegionService.findBaseRegionById(srl.getRegionId()) ;
				json.put("regionName", region.getName());
				json.put("shortname", region.getShortname());
				json.put("score", srl.getScore());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("ScoreRegionRank", jsona);
		return jsons;
	}
	
	
	/**
	 * 服务站点考核排名(只显示前8名)
	 * @param model
	 * @return
	 */
	@ResponseBody
	@GetMapping("get_score_site_rank")
	public JSONObject getScoreSiteRank(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<ScoreSiteLog> scoreSiteLogs = scoreSiteLogService.findScoreSiteLogSortByScore();
		if(scoreSiteLogs != null && scoreSiteLogs.size() > 0){
			for(ScoreSiteLog ssl : scoreSiteLogs){
				int index = 0;
				JSONObject json = new JSONObject();
				Site site = siteSerivce.findSiteById(ssl.getSiteId());
				json.put("siteName", site.getName());
				json.put("province", site.getProvince());
				json.put("city", site.getCity());
				json.put("district", site.getDistrict());
				json.put("address", site.getAddress());
				json.put("score", ssl.getScore());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("ScoreSiteRank", jsona);
		return jsons;
	}
	
	
	/**
	 * 最近交易
	 * @param model
	 * @return
	 */
	@ResponseBody
	@GetMapping("get_recent_transaction")
	public JSONObject getRecentTransaction(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<Order> orderlist = orderService.getOrderList(new Order());
		if(orderlist != null && orderlist.size() > 0){
			for(Order order : orderlist){
				int index = 0;
				JSONObject json = new JSONObject();
				Integer articleId = order.getArticleId();
				Integer shopId = order.getShopId();
				Article article = articleService.selectById(articleId);
				Shop shop = shopService.findNameById(shopId);
				json.put("articleName", article.getTitle());
				json.put("shopName", shop.getName());
				json.put("quantity", order.getQuantity());
				json.put("sellPrice", order.getSellPrice());
				//行业，类目关系缺失
				String businessId = shop.getBusinessIds().split(",")[0];
				json.put("business", categoryService.getCategoryById(Integer.parseInt(businessId)).getName());
				json.put("category", categoryService.getCategoryById(article.getCategoryId()).getName());
				jsona.add(index, json);
				index++;
			}
		}
		
		jsons.put("RecentTransaction", jsona);
		return jsons;
	}
	
	
	
	/**
	 * 获取平台销售额
	 * @param model
	 * @return
	 */
	@GetMapping("get_sales_platfroms")
	@ResponseBody
	public JSONObject getSalesPlatfroms(Model model){
		
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayChannelLog> dcl = dayChannelLogService.findAllDayChannelLog();
		if(dcl != null && dcl.size() > 0){
			for(DayChannelLog dcls : dcl){
				int index = 0;
				JSONObject json = new JSONObject();
				Channel channel = channelService.getChannelById(dcls.getChannelId());
				json.put("channelName", channel.getName());
				json.put("totalAmount", dcls.getTotalAmount());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("SalesPlatfroms", jsona);
		return jsons;
	}
	
	
	/**
	 * 获取平台销售量
	 * @param model
	 * @return
	 */
	@GetMapping("get_sales_volume")
	@ResponseBody
	public JSONObject getSalesVolume(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayChannelLog> dcls =dayChannelLogService.findAllDayChannelLog();
		if(dcls != null && dcls.size() > 0){
			for(DayChannelLog dcl : dcls){
				int index = 0;
				JSONObject json = new JSONObject();
				Channel channel = channelService.getChannelById(dcl.getChannelId());
				json.put("channelName", channel.getName());
				json.put("totalAmount", dcl.getDealArticleNum());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("SalesVolume", jsona);
		return jsons;
	}
	
	
	/**
	 * 获取类目销售额
	 * @param model
	 * @return
	 */
	@GetMapping("get_sales_category")
	@ResponseBody
	public JSONObject getSalesCategory(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayCategoryLog> dcls = dayCategoryLogService.findAllDayCategryLog();
		if(dcls != null && dcls.size() >0){
			for(DayCategoryLog dcl : dcls){
				int index = 0;
				JSONObject json = new JSONObject();
				Category category = categoryService.getCategoryById(dcl.getCategoryId());
				json.put("categoryName", category.getName());
				json.put("totalAmount", dcl.getTotalAmount());
				jsona.add(index, json);
				index++;
			}
		}
		
		jsons.put("SalesCategory", jsona);
		return jsons;
	}
	
	
	/**
	 * 获取类目销售量
	 * @param model
	 * @return
	 */
	@GetMapping("get_sales_volume_category")
	@ResponseBody
	public JSONObject getSalesVolumeCategory(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayCategoryLog> dcls = dayCategoryLogService.findAllDayCategryLog();
		if(dcls != null && dcls.size() >0){
			for(DayCategoryLog dcl : dcls){
				int index = 0;
				JSONObject json = new JSONObject();
				Category category = categoryService.getCategoryById(dcl.getCategoryId());
				json.put("categoryName", category.getName());
				json.put("dealArticleNum", dcl.getDealArticleNum());
				jsona.add(index, json);
				index++;
			}
		}
		
		jsons.put("SalesVolumeCategory", jsona);
		return jsons;
	}
	
	
	/**
	 * 获取商品销售额
	 * @param model
	 * @return
	 */
	@GetMapping("get_sales_marchandise")
	@ResponseBody
	public JSONObject getSalesMarchandise(Model model){
		
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayChannelLog> dcl = dayChannelLogService.findAllDayChannelLog();
		if(dcl != null && dcl.size() > 0){
			for(DayChannelLog dcls : dcl){
				int index = 0;
				JSONObject json = new JSONObject();
				Channel channel = channelService.getChannelById(dcls.getChannelId());
				json.put("channelName", channel.getName());
				json.put("totalAmount", dcls.getTotalAmount());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("SalesMarchandise", jsona);
		return jsons;
	}
	
	
	
	/**
	 * 获取商品销售量
	 * @param model
	 * @return
	 */
	@GetMapping("get_sales_volume_marchandise")
	@ResponseBody
	public JSONObject getSalesVolumeMarchandise(Model model){
		
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayArticleLog> dals = dayArticleLogService.findDayArticleLogData();
		if(dals != null && dals.size() > 0){
			for(DayArticleLog dal : dals){
				int index = 0;
				JSONObject json = new JSONObject();
				Article article = articleService.selectById(dal.getArticleId());
				json.put("channelName", article.getTitle());
				json.put("totalAmount", dal.getArticleNum());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("SalesVolumeMarchandise", jsona);
		return jsons;
	}
	
	
	
	/**
	 * 平台店铺数
	 * @param model
	 * @return
	 */
	@GetMapping("get_num_shops")
	@ResponseBody
	public JSONObject getNumberShops(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayChannelLog> dcls = dayChannelLogService.findAllDayChannelLog();
		if(dcls != null && dcls.size()>0){
			for(DayChannelLog dcl : dcls){
				int index = 0;
				JSONObject json = new JSONObject();
				Channel channel = channelService.getChannelById(dcl.getChannelId());
				json.put("shopNum", dcl.getShopNum());
				json.put("channelName", channel.getName());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("NumberShops", jsona);
		return jsons;
	}
	
	
	/**
	 * 包裹数
	 * @param model
	 * @return
	 */
	@GetMapping("get_num_parcels")
	@ResponseBody
	public JSONObject getNuberParcels(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<OrderExpressLog> oels = orderExpressLogService.findOrderExpressNum();
		for(OrderExpressLog oel : oels){
			int index = 0;
			JSONObject json = new JSONObject();
			Express express = expressService.findExpressById(oel.getExpressId());
			json.put("expressName", express.getName());
			json.put("expressNum", oel.getExpressNum());
			jsona.add(index, json);
			index++;
		}
		jsons.put("NuberParcels", jsona);
		return jsons;
	}
	
	
	
	/**
	 * 类目店铺数
	 * @param model
	 * @return
	 */
	@GetMapping("get_num_shops_catalogues")
	@ResponseBody
	public JSONObject getNumShopsCatalogues(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayCategoryLog> dcls = dayCategoryLogService.findAllDayCategryLog();
		if(dcls != null && dcls.size() > 0){
			for(DayCategoryLog dcl : dcls){
				int index = 0;
				JSONObject json = new JSONObject();
				Category category = categoryService.getCategoryById(dcl.getCategoryId());
				json.put("categoryName", category.getName());
				json.put("shopNum", dcl.getShopNum());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("NumShopsCatalogues", jsona);
		return jsons;
	}
	
	
	/**
	 * 类目商品数
	 * @param model
	 * @return
	 */
	@GetMapping("get_num_article_catalogues")
	@ResponseBody
	public JSONObject getNumArticleCatalogues(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayCategoryLog> dcls = dayCategoryLogService.findAllDayCategryLog();
		if(dcls != null && dcls.size() > 0){
			for(DayCategoryLog dcl : dcls){
				int index = 0;
				JSONObject json = new JSONObject();
				Category category = categoryService.getCategoryById(dcl.getCategoryId());
				json.put("categoryName", category.getName());
				json.put("articeNum", dcl.getArticleNum());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("NumberArticles", jsona);
		return jsons;
	}
	
	
	
	/**
	 * 商品价格趋势
	 * @param model
	 * @return
	 */
	@GetMapping("get_price_trend_merchandise")
	@ResponseBody
	public JSONObject getPriceTrendMerchandise(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<ArticlePriceLog> apls = articlePriceLogService.findArticlePriceLog();
		if(apls != null && apls.size() > 0){
			for(ArticlePriceLog apl : apls){
				int index = 0;
				JSONObject json = new JSONObject();
				Article article = articleService.selectById(apl.getArticleId());
				json.put("articleName", article.getTitle());
				json.put("articlePrice", apl.getNewPrice());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("PriceTrendMerchandise", jsona);
		return jsons;
	}
	
	
	
	/**
	 * 热门商品
	 * @param model
	 * @return
	 */
	@GetMapping("get_hot_seller")
	@ResponseBody
	public JSONObject getHotSeller(Model model){
		JSONObject jsons = new JSONObject();
		JSONArray jsona = new JSONArray();
		List<DayArticleLog> dals = dayArticleLogService.findDayArticleLogData();
		if(dals != null && dals.size() > 0){
			for(DayArticleLog dal : dals){
				int index = 0;
				JSONObject json = new JSONObject();
				Article article = articleService.selectById(dal.getArticleId());
				json.put("articleName", article.getTitle());
				json.put("articleNum", dal.getArticleNum());
				jsona.add(index, json);
				index++;
			}
		}
		jsons.put("HotSeller", jsona);
		return jsons;
	}
	
}

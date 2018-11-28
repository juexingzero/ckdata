package com.manhui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manhui.model.Article;
import com.manhui.model.DataHistory;
import com.manhui.model.Shop;
import com.manhui.service.ArticleService;
import com.manhui.service.DataHistoryService;
import com.manhui.service.OrginArticleService;
import com.manhui.service.OrginShopService;
import com.manhui.service.ShopService;
import com.manhui.spider.TBGetData;
import com.manhui.spider.HttpRequest;
import com.manhui.spider.JDConstant;
import com.manhui.spider.TMConstant;
import com.manhui.spider.TMGetData;


@Controller
public class SpiderService {
	@Autowired
	ShopService shopService;
	@Autowired
	ArticleService articleService;
	@Autowired
	private OrginShopService orginShopService;
	@Autowired
	private OrginArticleService orginArticleService;
	@Autowired
	DataHistoryService historyService;
	public Integer insert=0;
	public Integer update=0;
	private Integer shopId=null;
	private String oldId=null;
	
	private Set<Shop> shopSet=null;
	private Set<Article> articleSet=null;
	private Set<String> idSet=new HashSet<>();
	/**
	 * 淘宝店铺采集
	 * @return
	 */
	@RequestMapping("/getTaobaoData.action")
	@ResponseBody
	public String getTaobaoData(){
		Integer total=TBGetData.getTotalPage();
		for(int i =0;i<total;i++){
			JSONArray array=TBGetData.getShopList(i);
			writeShop(array);
		}
		return "共添加："+insert+"个店铺;更新："+update+"个店铺。";
	}
	
	
	private void writeShop(JSONArray array){
		if(array==null){
			return;
		}
		for(int i=0;i<array.size();i++){
			JSONObject item=array.getJSONObject(i);
			Shop shop=new Shop();
			shop.setName(item.getString("title"));
			shop.setShopNid(item.getString("uid"));
			shop.setImgUrl("http:"+item.getString("picUrl"));
			shop.setChannelId(3);
			shop.setUrl("http:"+item.getString("shopUrl"));
			String add=item.getString("provcity");
			if(add !=null && add != ""){
				String[] adds=add.split(" ");
				shop.setProvince(adds[0]);
				if(adds.length > 1){
					shop.setCity(adds[1]);
				}else{
					shop.setCity(adds[0]);
				}
			}
			List<Integer>list=shopService.getShopCountById(shop.getShopNid());
			if(list.size()<1){
				shopService.insertShop(shop);
				insert++;
			}else{
				shopService.updateShop(shop);
				update++;
			}
			
			String shopUrl="http:"+item.getString("shopUrl");
			System.out.println(shopUrl);
		}
	}
	/**
	 * 淘宝商品采集
	 * @return
	 */
	@RequestMapping("/getCommodityList.action")
	@ResponseBody
	public String resolveArticle(){
		List<Shop>list=shopService.getShopUrlList(3);
		for (Shop shop : list) {
			shopId=shop.getId();
			updateArticle(shop.getUrl());
		}
		return "新增商品："+insert+",更新商品："+update;
	}
	
	private void updateArticle(String url){
		String html=TBGetData.getCommodity(url,1);
		String startStr="<dl",endStr="</dl>";
		int start=html.indexOf(startStr)+startStr.length();
		int end=html.indexOf(endStr,start);
		while(start!=-1 && end!=-1){
			String item=html.substring(start,end);
			writeArticle(item);
			start=html.indexOf(startStr,end);
			end=html.indexOf(endStr,start);
		}
	}
	
	private void writeArticle(String item){
		int index=item.indexOf("data-id=\\\"")+"data-id=\\\"".length();
		String id=item.substring(index,item.indexOf("\\\"",index));
		index=item.indexOf("img alt=\\\"")+"img alt=\\\"".length();
		String title=item.substring(index,item.indexOf("\\\"",index));
		index=item.indexOf("\"  src=\\\"")+"\"  src=\\\"".length();
		String imgurl=item.substring(index,item.indexOf("\\\"",index));
		index=item.indexOf("class=\\\"c-price\\\">")+"class=\\\"c-price\\\">".length();
		String price=item.substring(index,item.indexOf("</span>",index));
		System.out.println("ID:"+id+",title:"+title+",price:"+price+",img:"+imgurl);
		Article article=new Article();
		article.setArticleNid(id);
		article.setImgUrl("http:"+imgurl);
		article.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
		article.setTitle(title);
		article.setShopId(shopId);
		article.setUrl("http://item.taobao.com/item.htm?id="+id);
		int count=articleService.getCountByNid(id);
		if(count<1){
			articleService.insertArticle(article);
			insert++;
		}else{
			articleService.updateArticle(article);
			update++;
		}

	}
	
	/**
	 * 京东采集入口
	 * @return
	 */
	
	@RequestMapping("/jd/start")
	@ResponseBody
	public DataHistory JDGather(){
		DataHistory history=new DataHistory();
		history.setChannelId(2);
		historyService.insertHistory(history);
		insert=0;
		update=0;
		try{
			gather();
			updateCommodit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			history.setDataCount(insert+update);
			historyService.updateHistory(history);
		}
		return history;
	}
	/***********京东采集相关*************/
	
	@RequestMapping("/jd/gather")
	@ResponseBody
	public String gather(){
		shopSet=new HashSet<>();
		//京东搜索结果都是100页
		for(int i=1;i<=100;i++){
			String[] param={JDConstant.JD_SEARCH_URL
					, JDConstant.JD_SEARCH_VALUEE+(i*2-1), JDConstant.JD_SEARCH_COOKIE};
			String html=HttpRequest.sendGet(param);
			getShopList(html);
		}
		List<Shop>shopList=new ArrayList<>();
		shopList.addAll(shopSet);
		orginShopService.insertList(shopList);
		return "新增店铺："+insert+",更新店铺："+update;
	}
	
	/**
	 * 解析HTML
	 * @param html
	 */
	private void getShopList(String html){
		if(html==""){
			return;
		}
		int start=0,end=0;
		String startStr="gl-item",endStr="加入购物车";
		start=html.indexOf(startStr,end)+startStr.length();
		end=html.indexOf(endStr,start);
		while(start!=-1 && end!=-1){
			String item=html.substring(start+startStr.length(), end);
			writeShopList(item);
			start=html.indexOf(startStr,end);
			end=html.indexOf(endStr,start);
		}
	}
	/**
	 * 获取商铺信息
	 * @param item
	 */
	private void writeShopList(String item){
		int index=0;
		index=item.indexOf("err-product")+"err-product".length();
		index=item.indexOf("//",index)+"//".length();
		String pic="https://"+item.substring(index,item.indexOf('"',index));
		index=item.indexOf("J_im_icon");
		index=item.indexOf("href=",index)+"href=".length()+1;
		String url="https:"+item.substring(index,item.indexOf('"',index));
		int i=url.indexOf('-');
		if(i==-1)return;
		String id=url.substring(i+1,url.indexOf('.',i));
		if(id.equals(oldId)){
			//如果上一个产品的店铺与现在相同，则返回
			return;
		}
		index=item.indexOf("title",index)+"title".length()+2;
		String name=item.substring(index,item.indexOf('"',index));
		System.out.println("id:"+id+",name:"+name+",url:"+url+",pic:"+pic);
		//创建店铺对象实体
		Shop shop=new Shop();
		shop.setName(name);
		shop.setImgUrl(pic);
		shop.setShopNid(id);
		shop.setUrl(url);
		shop.setChannelId(2);
		shopSet.add(shop);
		List<Integer>list=shopService.getShopCountById(id);
		if(list.size()>0){
			update++;
			shopService.updateShop(shop);
		}else{
			insert++;
			shopService.insertShop(shop);
		}
		oldId=id;
		System.out.println("新增店铺："+insert+",更新店铺："+update);
	}
	
	/**
	 * 更新商品
	 * @return
	 */
	@RequestMapping("/jd/updateCommdit")
	@ResponseBody
	public String updateCommodit(){
		List<Shop>list=shopService.getShopUrlList(2);
		for (Shop shop : list) {
			shopService.updateShop(shop);
			shopId=shop.getId();
			String shopId=shop.getShopNid();
			getCommoditList(shopId);
		}
		return "新增商品："+insert+",更新商品："+update;
	}
	//获取该店铺的商品列表
	private void getCommoditList(String id){
		String[] param={JDConstant.JD_COMM_URL
				, JDConstant.JD_COMM_VALUE+1+"&shopId="+id, JDConstant.JD_COMM_COOKIE};
		String html=HttpRequest.sendGet(param);
		int index=html.indexOf("<em>共")+"<em>共".length();
		String total=html.substring(index, html.indexOf("条",index));
		Integer amount=Integer.valueOf(total);
		if(amount%24==0){
			amount=amount/24;
		}else{
			amount=amount/24+1;
		}
		resolveCommodit(html);
		for(int i=2;i<amount;i++){
			String[]param1={JDConstant.JD_COMM_URL, JDConstant.JD_COMM_VALUE+i+"&shopId="+id, JDConstant.JD_COMM_COOKIE};
			html=HttpRequest.sendGet(param1);
			resolveCommodit(html);
		}
	}
	
	public void resolveCommodit(String html){
		articleSet=new HashSet<>();
		if("".equals(html))return;
		String startStr="jItem";
		String endStr="jBtnArea";
		int start=html.indexOf(startStr);
		int end=html.indexOf(endStr,start);
		while(start != -1 && end != -1){
			String item=html.substring(start,end);
			start=html.indexOf(startStr,end);
			end=html.indexOf(endStr,start);
			writeCommod(item);
		}
		List<Article>articleList=new ArrayList<>();
		articleList.addAll(articleSet);
		orginArticleService.insertList(articleList);
		
	}
	
	public void writeCommod(String item){
//		System.out.println(item);
		int index=item.indexOf("original=")+"original=".length()+2;
		String pic="https:"+item.substring(index,item.indexOf('"',index)-1);
		index=item.indexOf("jdcomment=")+"jdcomment=".length()+1;
		String id=item.substring(index,item.indexOf('\'',index));
		index=item.indexOf("jDesc");
		index=item.indexOf("_blank",index)+"_blank".length()+3;
		String name=item.substring(index,item.indexOf('<',index));
		String url="https://item.jd.com/"+id+".html";
		String[] param={JDConstant.JD_PRICE_URL, JDConstant.JD_PRICE_VALUE+id,"","https://mall.jd.com/view_search-615371-0-5-1-24-2.html"};
		String priceInfo=HttpRequest.sendGet(param);
		index=priceInfo.indexOf("\"p\":\"");
		if(index == -1){
			System.out.println("获取价格失败!");
			return;
		};
		index+="\"p\":\"".length();
		String price=priceInfo.substring(index, priceInfo.indexOf('"',index));
		String[] comment={JDConstant.JD_COMMENT_URL,JDConstant.JD_COMMENT_VALUE+id};
		String commentInfo=HttpRequest.sendGet(comment);
		index=commentInfo.indexOf(",\"CommentCount\":");
		if(index == -1){
			System.out.println("获取评价数量失败！");
			return;
		}
		index+=",\"CommentCount\":".length();
		String quantity=commentInfo.substring(index,commentInfo.indexOf(',',index));
		System.out.println("id:"+id+",name:"+name+",price:"+price+",pic:"+pic);
		int amount=articleService.getCountByNid(id);
		
		Article article=new Article();
		article.setArticleNid(id);
		article.setTitle(name);
		article.setImgUrl(pic);
		article.setUrl(url);
		try{
			article.setQuantity(new BigDecimal(quantity));
			article.setPrice(new BigDecimal(price));
		}catch(Exception e){
			return;
		}
		article.setShopId(shopId);
		articleSet.add(article);
		if(amount<1){
			articleService.insertArticle(article);
			insert++;
		}else{
			articleService.updateArticle(article);
			update++;
		}
		System.out.println("新增商品："+insert+",更新商品："+update);
	}

	/**
	 * 查看当前采集状态
	 * @return
	 */
	@RequestMapping("/spider/state")
	@ResponseBody
	public String getState(){
		return "添加："+insert+"条数据,更新："+update+"条数据！";
	}
	/***************以下是天猫采集相关***************************/
	
	
	
	@RequestMapping("/tm/gather")
	@ResponseBody
	public String tmallGather(){
		idSet=new HashSet<>();
		String[] param={TMConstant.TM_SHOP_URL,TMConstant.TM_SHOP_VALUE,TMConstant.TM_SHOP_COOKIE};
		String html=HttpRequest.sendGet(param);
		int end=html.indexOf("页，到第");
		int start=html.lastIndexOf('共',end);
		String page=html.substring(start+1,end);
		Integer total=Integer.valueOf(page);
		for(int i=0;i<total;i++){
			shopSet=new HashSet<>();
			String[] val={TMConstant.TM_SHOP_URL,TMConstant.TM_SHOP_VALUE+(i*60),TMConstant.TM_SHOP_COOKIE};
			html=HttpRequest.sendGet(val);
			System.out.println(html);
			getShop(html);
			List<Shop>list=new ArrayList<>();
			list.addAll(shopSet);
			System.out.println(idSet);
			if(list.size()>0){
				orginShopService.insertList(list);
			}
		}
		return "添加店铺："+insert+",更新店铺："+update;
	}
	
	public void getShop(String html){
		if("".equals(html))return;
		int start=0,end=0;
		String startStr="product-iWrap";
		String endStr="评价";
		start=html.indexOf(startStr,end);
		end=html.indexOf(endStr, start);
		while(start !=-1 && end !=-1 ){
			String item=html.substring(start, end);
			start=html.indexOf(startStr, end);
			end=html.indexOf(endStr, start);
			resolveShop(item);
		}
		
	}
	
	public void resolveShop(String item){
		int index=item.indexOf("user_number_id=");
		if(index == -1)return;
		index+="user_number_id=".length();
		String userNumber=item.substring(index,item.indexOf('&',index));
		if(!idSet.add(userNumber)){
			return;
		}
		String url=TMGetData.getUrl(userNumber);
		index=item.indexOf('>',index);
		String name=item.substring(index+1,item.indexOf('<',index));
		System.out.println("name:"+name+",ID:"+userNumber+",url:"+url);
		//创建店铺对象实体
		Shop shop=new Shop();
		shop.setName(name);
		shop.setShopNid(userNumber);
		shop.setUrl(url);
		shop.setChannelId(5);
		shopSet.add(shop);
		List<Integer>list=shopService.getShopCountById(userNumber);
		if(list.size()>0){
			update++;
			shopService.updateShop(shop);
		}else{
			insert++;
			shopService.insertShop(shop);
		}
	}
	
	/**
	 * 更新商品
	 * @return
	 */
	@RequestMapping("/tm/updateCommdit")
	@ResponseBody
	public String updateTMCommodit(){
		List<Shop>list=shopService.getShopUrlList(5);
		for (Shop shop : list) {
			shopService.updateShop(shop);
			shopId=shop.getId();
			String url=shop.getUrl();
			getTMCommoditList(url);
		}
		return "新增商品："+insert+",更新商品："+update;
	}
	
	public void getTMCommoditList(String url){
		articleSet=new HashSet<>();
		int i=1;
		while(i<1000){
			Map<String,List<String>> map=HttpRequest.getHeade(new String[]{url+"i/asynSearch.htm",TMConstant.TM_COMMENT_VALUE+i++,TMConstant.TM_COMMENT_COOKIE,"https://mtkz.fliggy.com/search.htm?spm=a1z10.3-b.w4011-13453021504.271.RJ2Ur2&search=y&orderType=hotsell_desc&pageNo=2&tsearch=y"});
			if(map == null){
				continue;
			}
			List<String> heades=map.get("Location");
			String addr=url+"i/asynSearch.htm";
			if(heades != null && heades.size()>0){
				addr=heades.get(0);
			}
			String html=HttpRequest.sendGet(new String[]{addr,"",TMConstant.TM_COMMENT_COOKIE});
			if(html.indexOf("没找到符合条件的商品") != -1){
				break;
			}
			if(html.indexOf("shopsystem-search_asyn_interface-anti_Spider-checklogin") != -1){
				System.out.println("系统要求登陆！");
				break;
			}
			if(html.indexOf("shopsystem-search_asyn_interface-anti_Spider-checkcode") != -1){
				System.out.println("系统要求输入验证码！");
				break;
			}
			resolveComm(html);
		}
		List<Article>articleList=new ArrayList<>();
		articleList.addAll(articleSet);
		if(articleList.size()>0){
			orginArticleService.insertList(articleList);
		}
	}
	
	
	public void resolveComm(String html){
		String startStr="<dl class=";
		String endStr="</dl>";
		int start=0,end=0;
		start=html.indexOf(startStr);
		end=html.indexOf(endStr,start);
		while(start != -1 && end != -1){
			String item=html.substring(start,end);
			start=html.indexOf(startStr, end);
			end=html.indexOf(endStr,start);
			saveArticle(item);
		}
	}
	
	public void saveArticle(String item){
		int index=item.indexOf("item.htm?id=")+"item.htm?id=".length();
		String id=item.substring(index,item.indexOf('&',index));
		index=item.indexOf("img alt=")+"img alt=".length()+2;
		String title=item.substring(index,item.indexOf('\\',index));
		String url="https://detail.tmall.com/item.htm?id="+id;
		index=item.indexOf("data-ks-lazyload=")+"data-ks-lazyload=".length()+2;
		String img=item.substring(index,item.indexOf('\\',index));
		index=item.indexOf("c-price")+"c-price".length()+3;
		String price=item.substring(index,item.indexOf('<',index)).trim();
		index=item.indexOf("sale-num")+"sale-num".length()+3;
		String amount=item.substring(index,item.indexOf('<',index));
		System.out.println("商品信息--> name:"+title+",ID:"+id+",price:"+price+",amount:"+amount+",img:"+img);
		
		int number=articleService.getCountByNid(id);
		Article article=new Article();
		article.setShopId(shopId);
		article.setImgUrl(img);
		article.setArticleNid(id);
		article.setTitle(title);
		article.setUrl(url);
		try{
			article.setPrice(new BigDecimal(price));
			article.setQuantity(new BigDecimal(amount));
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		articleSet.add(article);
		if(number<1){
			articleService.insertArticle(article);
			insert++;
		}else{
			articleService.updateArticle(article);
			update++;
		}
		System.out.println("新增商品："+insert+",更新商品："+update);
	}
	/**
	 * 天猫采集入口
	 */
	public DataHistory TMtGather(){
		DataHistory history=new DataHistory();
		history.setChannelId(5);
		historyService.insertHistory(history);
		insert=0;
		update=0;
		try{
			gather();
			updateCommodit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			history.setDataCount(insert+update);
			historyService.updateHistory(history);
		}
		return history;
	}
}

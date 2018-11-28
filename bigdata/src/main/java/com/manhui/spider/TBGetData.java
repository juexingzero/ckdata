package com.manhui.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TBGetData {

	/**
	 * 请求远程数据
	 * @param currentPage
	 * @return
	 */
	public static JSONObject getTaoBaoData(Integer currentPage) {
		String[] param={TBConstant.SEARCH, TBConstant.PARAM+(currentPage*20), TBConstant.COOKIE};
		String html=HttpRequest.sendGet(param);
		System.out.println("原始HTML："+html);
		int start=html.indexOf("g_page_config = ")+"g_page_config = ".length();
		int end=html.indexOf(";    g_srp_loadCss", start);
		if(end == -1){
			return null;
		}
		html=html.substring(start, end);
		System.out.println("截取HTML："+html);
		JSONObject json=JSONObject.parseObject(html);
		return json;
	}
	
	
	/**
	 * 从返回中获取总页数
	 * @param json
	 * @return
	 */
	public static Integer getTotalPage(){
		JSONObject json=getTaoBaoData(0);
		if(json == null){
			return 0;
		}
		return json.getJSONObject("mods").getJSONObject("pager").getJSONObject("data").getInteger("totalPage");
	}
	
	/**
	 * 按页面获取店铺列表
	 */
	public static JSONArray getShopList(Integer currentPage){
		JSONObject json=getTaoBaoData(currentPage);
		if(json==null){
			return null;
		}
		JSONArray shoplist=json.getJSONObject("mods").getJSONObject("shoplist").getJSONObject("data").getJSONArray("shopItems");
		return shoplist;
	}
	
	/**
	 * 按店铺地址获取商品列表
	 * @param shopUrl
	 * @return
	 */
	public static String getCommodity(String shopUrl,Integer currentPage){
		String[] param={shopUrl+TBConstant.COMMODITY_ALL, TBConstant.COMMODITY_PAEAM+currentPage, TBConstant.COMMODITY_COOKIE};
		String html=HttpRequest.sendGet(param);
		System.out.println("爬取HTML："+html);
		return html;
	}
	
	public static void resolveArticle(){
		String html=getCommodity("http://shop128462251.taobao.com",1);
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

	public static String[] writeArticle(String item){
		int index=item.indexOf("data-id=\\\"")+"data-id=\\\"".length();
		String id=item.substring(index,item.indexOf("\\\"",index));
		index=item.indexOf("img alt=\\\"")+"img alt=\\\"".length();
		String title=item.substring(index,item.indexOf("\\\"",index));
		index=item.indexOf("\"  src=\\\"")+"\"  src=\\\"".length();
		String imgurl=item.substring(index,item.indexOf("\\\"",index));
		index=item.indexOf("class=\\\"c-price\\\">")+"class=\\\"c-price\\\">".length();
		String price=item.substring(index,item.indexOf("</span>",index));
		System.out.println("ID:"+id+",title:"+title+",price:"+price+",img:"+imgurl);
		String[] commodity=new String[]{id,title,imgurl,price};
		return commodity;
	}
	
	public static void main(String[] args) {
		resolveArticle();
	}
}

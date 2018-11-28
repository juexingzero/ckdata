package com.manhui.spider;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TMGetData {
	private static Set<String> idSet=new HashSet<>();
	public static void main(String[] args) {
/*		String[] param={TMConstant.TM_SHOP_URL,TMConstant.TM_SHOP_VALUE,TMConstant.TM_SHOP_COOKIE};
//		String[] param={"https://store.taobao.com/search.htm","rn=b614f32263c1539424dce23e82448bda&keyword=城口&user_number_id=2760509686"};
		String html=HttpRequest.sendGet(param);
////		System.out.println(html);
//		Map<String,List<String>>map=HttpRequest.getHeade(param);
//		System.out.println(map);
		int end=html.indexOf("页，到第");
		int start=html.lastIndexOf('共',end);
		String page=html.substring(start+1,end);
		Integer total=Integer.valueOf(page);
		for(int i=0;i<total;i++){
			String[] val={TMConstant.TM_SHOP_URL,TMConstant.TM_SHOP_VALUE+(i*60),TMConstant.TM_SHOP_COOKIE};
			html=HttpRequest.sendGet(val);
			String html2=HttpRequest.sendGet(new String[]{TMConstant.TM_SHOP_URL,TMConstant.TM_SHOP_VALUE+240,TMConstant.TM_SHOP_COOKIE});
//			getShop(html);
			System.out.println(html);
			System.out.println(html2);
		}
*/
		getComm();
	}
	
	public static void getShop(String html){
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
	
	public static void resolveShop(String item){
		int index=item.indexOf("user_number_id=");
		if(index == -1)return;
		index+="user_number_id=".length();
		String userNumber=item.substring(index,item.indexOf('&',index));
		if(!idSet.add(userNumber)){
			return;
		}
		String url=getUrl(userNumber);
		index=item.indexOf('>',index);
		String name=item.substring(index+1,item.indexOf('<',index));
		System.out.println("name:"+name+",ID:"+userNumber+",url:"+url);
	}
	
	public static String getUrl(String id){
		String[] param={"https://store.taobao.com/search.htm","rn=b614f32263c1539424dce23e82448bda&keyword=城口&user_number_id="+id};
		Map<String,List<String>> map=HttpRequest.getHeade(param);
		String url=map.get("Location").get(0);
//		System.out.println("URL:"+url);
//		map=HttpRequest.getHeade(new String[]{url,"1=1"});
//		url=map.get("Location").get(0);
//		System.out.println("URL:"+url);
		url=url.substring(0, url.indexOf("search"));
		System.out.println(url);
		return url;
	}
	
	public static void getComm(){
		String url="http://gzlznly.fliggy.com/i/asynSearch.htm";
		int i=1;
		while(i<2){
			Map<String,List<String>> map=HttpRequest.getHeade(new String[]{url,TMConstant.TM_COMMENT_VALUE+i++,TMConstant.TM_COMMENT_COOKIE,"https://mtkz.fliggy.com/search.htm?spm=a1z10.3-b.w4011-13453021504.271.RJ2Ur2&search=y&orderType=hotsell_desc&pageNo=2&tsearch=y"});
			url=map.get("Location").get(0);
			String html=HttpRequest.sendGet(new String[]{url,"",TMConstant.TM_COMMENT_COOKIE});
			if(html.indexOf("没找到符合条件的商品") != -1){
				continue;
			}
			resolveComm(html);
		}

	}
	
	public static void resolveComm(String html){
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
	
	public static void saveArticle(String item){
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
	}
}

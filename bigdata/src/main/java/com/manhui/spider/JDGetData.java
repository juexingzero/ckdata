package com.manhui.spider;
/**
 * 京东采集相关方法
 * @author Administrator
 *
 */
public class JDGetData {
	
	public static void gather(){
		//京东搜索结果都是100页
		for(int i=1;i<=100;i++){
			String[] strs={JDConstant.JD_SEARCH_URL,JDConstant.JD_SEARCH_VALUEE+(i*2-1), JDConstant.JD_SEARCH_COOKIE};
			String html=HttpRequest.sendGet(strs);
			getShopList(html);
		}
	}
	
	private static void getShopList(String html){
		if(html==""){
			return;
		}
		int start=0,end=0;
		String startStr="gl-item",endStr="加入购物车";
		while(start!=-1 && end!=-1){
			start=html.indexOf(startStr)+startStr.length();
			end=html.indexOf(endStr,start);
			String item=html.substring(start, end);
			writeShopList(item);
		}
	}
	
	private static void writeShopList(String item){
		int index=0;
		index=item.indexOf("err-product")+"err-product".length();
		index=item.indexOf("src=",index)+"src=".length()+1;
		String pic="http:"+item.substring(index,item.indexOf("\"",index));
		index=item.indexOf("J_im_icon");
		index=item.indexOf("href=",index)+"href=".length()+1;
		String url="http:"+item.substring(index,item.indexOf('"',index));
		int i=url.indexOf('-');
		String id=url.substring(url.indexOf('-')+1,url.indexOf('.',i));
		index=item.indexOf("title",index)+"title".length()+2;
		String name=item.substring(index,item.indexOf('"',index));
		System.out.println("id:"+id+",name:"+name+",url:"+url+",pic:"+pic);
	}
	

}

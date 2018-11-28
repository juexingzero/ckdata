package com.manhui.spider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String[] param) {
    	int paramLength=param.length;
    	if(paramLength<2){
    		return "参数不合法！";
    	}
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = param[0];
            if(!"".equals(param[1])){
            	urlNameString =param[0] + "?" + param[1];
            }
            System.out.println("访问："+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    " Mozilla/5.0 (Windows NT 6.1; WOW64; rv:59.0) Gecko/20100101 Firefox/59.0");
            if(paramLength>2){
                connection.setRequestProperty("cookie", param[2]);
            }
            if(paramLength>3){
                connection.setRequestProperty("referer",param[3]);
            }
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            String type="UTF-8";
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            if(map.get("Content-Type")!=null){
            	type=map.get("Content-Type").get(0);
            	int index=type.indexOf("charset");
            	if(index != -1){
            		type=type.substring(index+"charset".length()+1);
                    in = new BufferedReader(new InputStreamReader(
                    		connection.getInputStream(),type));
            	}else{
                    in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));	
            	}
            }else{
                in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
            }
            String line;
            while ((line = in.readLine()) != null) {
//            	System.out.println(line);
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static Map<String, List<String>> getHeade(String[] param) {
    	int paramLength=param.length;
    	if(paramLength<2){
    		return null;
    	}
//        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = param[0] + "?" + param[1];
            System.out.println("访问："+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if(paramLength>2){
                connection.setRequestProperty("cookie", param[2]);
            }
            if(paramLength>3){
                connection.setRequestProperty("referer",param[3]);
            }
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            return map;
//            String type="UTF-8";
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
//            if(map.get("Content-Type")!=null){
//            	type=map.get("Content-Type").get(0);
//            	int index=type.indexOf("charset");
//            	if(index != -1){
//            		type=type.substring(index+"charset".length()+1);
//                    in = new BufferedReader(new InputStreamReader(
//                    		connection.getInputStream(),type));
//            	}else{
//                    in = new BufferedReader(new InputStreamReader(
//                            connection.getInputStream()));	
//            	}
//            }else{
//                in = new BufferedReader(new InputStreamReader(
//                        connection.getInputStream()));
//            }
//            String line;
//            while ((line = in.readLine()) != null) {
////            	System.out.println(line);
//                result += line;
//            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}

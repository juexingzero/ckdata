package com.manhui.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.model.ReportInfo;
import com.manhui.model.ReportLogin;
import com.manhui.service.ReportInfoService;
import com.manhui.service.ReportLoginService;

/**
 * @ClassName: ReportController.java
 * @description:   数据上报控制器
 * @author: HanLin
 * @date Create in 13:33 2018/1/30
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */

@Controller
public class ReportController {
	@Autowired
	ReportLoginService reportLoginService;
	@Autowired
	ReportInfoService reportInfoService;

	/**
	 * 页面跳转
	 * @param path　页面名称
	 * @return
	 */
	@RequestMapping("/report/page/{path}")
	public String page(@PathVariable("path")String path){
		return "pages/report/"+path;
	}

	/**
	 * 模拟登陆
	 * @param request
	 * @param verify　验证码
	 */
	@RequestMapping("/report/chklogin")
	@ResponseBody
	public String chklogin(HttpServletRequest request,String verify){
		Cookie[] cookies=request.getCookies();
        String result = "";
        BufferedReader reader = null;
		String sessionId=null;
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("PHPSESSID")){
				sessionId=cookie.getValue();
			}
		}
		if(sessionId ==null ){
			return "获取cookie失败！";
		}
		ReportLogin reportLogin=reportLoginService.findReportLoginOrderByTime(110);
		try{
			URL url=new URL("http://ck.dzswtb.cn/front/chklogin.php");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	       	connection.setRequestProperty("accept", "");
	        connection.setRequestProperty("connection", "Keep-Alive");
	        connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:59.0) Gecko/20100101 Firefox/59.0");
	        connection.setRequestProperty("cookie", "PHPSESSID="+sessionId);
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        PrintWriter pw=new PrintWriter(connection.getOutputStream());
	        pw.print("username="+reportLogin.getName()+"&userpwd="+reportLogin.getPassword()+"&validate="+verify);
	        pw.flush();
	        reader=new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            int index=result.indexOf("alert('")+"alert('".length();
            String back=result.substring(index,result.indexOf('\'',index));
            System.out.println("back:"+back);
            return back;
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		return result;
	}
	/**
	 * 显示验证码
	 * @param response
	 */
	@RequestMapping("/report/verify")
	public void getVerify(HttpServletResponse response){
		response.setContentType("image/jpeg");
		try {
			URL url=new URL("http://ck.dzswtb.cn/back/verify.php");
			URLConnection connection = url.openConnection();
           	connection.setRequestProperty("accept", "");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:59.0) Gecko/20100101 Firefox/59.0");
            connection.connect();
            String cookie=connection.getHeaderField("Set-Cookie");
            if(cookie == null || cookie ==""){
            	System.out.println("获取cookie失败！");
            	return;
            }
            cookie=cookie.substring(cookie.indexOf('=')+1, cookie.indexOf(';'));
            Cookie c= new Cookie("PHPSESSID",cookie);
            c.setPath("/");
            response.addCookie(c);
            InputStream input=connection.getInputStream();
			
         // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流

            OutputStream os = response.getOutputStream();
            // 开始读取
            while ((len = input.read(bs)) != -1) {
            	os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/report/countList")
	@ResponseBody
	public List<Map<String,Object>> countList(){
		return reportInfoService.countList();
	}


	@RequestMapping("/report/saveReportInfo")
	@ResponseBody
	public String saveReportInfo(@RequestBody String jsonStr,HttpServletRequest request){
		Cookie[] cookies=request.getCookies();
		String sessionId=null;
        String result = "";
	    BufferedReader reader = null;
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("PHPSESSID")){
				sessionId=cookie.getValue();
			}
		}
		if(sessionId == null){
			return "获取session ID失败，请刷新验证码！";
		}
		JSONObject json=JSONObject.parseObject(jsonStr);
		List<BigDecimal>buyList=JSON.parseArray(json.getString("buyList"),BigDecimal.class);
		List<BigDecimal>sellList=JSON.parseArray(json.getString("sellList"),BigDecimal.class);
//		saveInfo(buyList,sellList);
		if(buyList.size() != 19 || sellList.size() != 19){
			return "参数数量不正确！请传19个值";
		}
		try{
			URL url=new URL("http://ck.dzswtb.cn/front/uploadData.php");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	       	connection.setRequestProperty("accept", "");
	        connection.setRequestProperty("connection", "Keep-Alive");
	        connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:59.0) Gecko/20100101 Firefox/59.0");
	        connection.setRequestProperty("cookie", "PHPSESSID="+sessionId);
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        PrintWriter pw=new PrintWriter(connection.getOutputStream());
	        String value="buyId[]=4&buyName[]=%E7%B2%AE%E6%B2%B9%E7%B1%BB&buy[]="+buyList.get(0)+
	        		"&buyId[]=5&buyName[]=%E7%94%9F%E9%B2%9C%E9%A3%9F%E5%93%81%E7%B1%BB&buy[]="+buyList.get(1)+
	        		"&buyId[]=6&buyName[]=%E5%B9%B2%E8%B4%A7%E7%B1%BB&buy[]="+buyList.get(2)+
	        		"&buyId[]=8&buyName[]=%E4%BC%91%E9%97%B2%E9%A3%9F%E5%93%81%E7%B1%BB&buy[]="+buyList.get(3)+
	        		"&buyId[]=9&buyName[]=%E7%83%9F%E9%85%92%E7%B1%BB&buy[]="+buyList.get(4)+
	        		"&buyId[]=10&buyName[]=%E6%9C%8D%E8%A3%85%E8%A3%85%E9%A5%B0%E7%B1%BB&buy[]="+buyList.get(5)+
	        		"&buyId[]=11&buyName[]=%E5%8C%96%E5%A6%86%E5%93%81%E7%B1%BB&buy[]="+buyList.get(6)+
	        		"&buyId[]=12&buyName[]=%E9%87%91%E9%93%B6%E7%8F%A0%E5%AE%9D%E7%B1%BB&buy[]="+buyList.get(7)+
	        		"&buyId[]=13&buyName[]=%E6%97%A5%E7%94%A8%E5%93%81%E7%B1%BB&buy[]="+buyList.get(8)+
	        		"&buyId[]=14&buyName[]=%E5%AE%B6%E7%94%B5%E7%B1%BB&buy[]="+buyList.get(9)+
	        		"&buyId[]=15&buyName[]=%E4%B8%AD%E8%A5%BF%E8%8D%AF%E5%93%81%E7%B1%BB&buy[]="+buyList.get(10)+
	        		"&buyId[]=16&buyName[]=%E6%96%87%E5%8C%96%E5%8A%9E%E5%85%AC%E7%B1%BB&buy[]="+buyList.get(11)+
	        		"&buyId[]=17&buyName[]=%E5%AE%B6%E5%85%B7%E7%B1%BB&buy[]="+buyList.get(12)+
	        		"&buyId[]=18&buyName[]=%E9%80%9A%E8%AE%AF%E5%99%A8%E6%9D%90%E7%B1%BB&buy[]="+buyList.get(13)+
	        		"&buyId[]=19&buyName[]=%E5%BB%BA%E7%AD%91%E8%A3%85%E6%BD%A2%E6%9D%90%E6%96%99&buy[]="+buyList.get(14)+
	        		"&buyId[]=20&buyName[]=%E5%86%9C%E8%B5%84%E7%94%9F%E4%BA%A7%E7%B1%BB&buy[]="+buyList.get(15)+
	        		"&buyId[]=21&buyName[]=%E5%8C%96%E5%B7%A5%E4%BA%A7%E5%93%81%E7%B1%BB&buy[]="+buyList.get(16)+
	        		"&buyId[]=22&buyName[]=%E6%9C%BA%E7%94%B5%E7%B1%BB&buy[]="+buyList.get(17)+
	        		"&buyId[]=23&buyName[]=%E6%9C%A8%E6%9D%90%E7%B1%BB&buy[]="+buyList.get(18)+
	        		"&sellId[]=27&sellName[]=%E7%B2%AE%E6%B2%B9%E7%B1%BB&sell[]="+sellList.get(0)+
	        		"&sellId[]=28&sellName[]=%E7%94%9F%E9%B2%9C%E9%A3%9F%E5%93%81%E7%B1%BB&sell[]="+sellList.get(1)+
	        		"&sellId[]=29&sellName[]=%E5%B9%B2%E8%B4%A7%E7%B1%BB&sell[]="+sellList.get(2)+
	        		"&sellId[]=30&sellName[]=%E4%BC%91%E9%97%B2%E9%A3%9F%E5%93%81%E7%B1%BB&sell[]="+sellList.get(3)+
	        		"&sellId[]=32&sellName[]=%E7%83%9F%E9%85%92%E7%B1%BB&sell[]="+sellList.get(4)+
	        		"&sellId[]=33&sellName[]=%E6%9C%8D%E8%A3%85%E8%A3%85%E9%A5%B0%E7%B1%BB&sell[]="+sellList.get(5)+
	        		"&sellId[]=34&sellName[]=%E5%8C%96%E5%A6%86%E5%93%81%E7%B1%BB&sell[]="+sellList.get(6)+
	        		"&sellId[]=35&sellName[]=%E9%87%91%E9%93%B6%E7%8F%A0%E5%AE%9D%E7%B1%BB&sell[]="+sellList.get(7)+
	        		"&sellId[]=36&sellName[]=%E6%97%A5%E7%94%A8%E5%93%81%E7%B1%BB&sell[]="+sellList.get(8)+
	        		"&sellId[]=37&sellName[]=%E5%AE%B6%E7%94%B5%E7%B1%BB&sell[]="+sellList.get(9)+
	        		"&sellId[]=38&sellName[]=%E4%B8%AD%E8%A5%BF%E8%8D%AF%E5%93%81%E7%B1%BB&sell[]="+sellList.get(10)+
	        		"&sellId[]=39&sellName[]=%E6%96%87%E5%8C%96%E5%8A%9E%E5%85%AC%E7%B1%BB&sell[]="+sellList.get(11)+
	        		"&sellId[]=40&sellName[]=%E5%AE%B6%E5%85%B7%E7%B1%BB&sell[]="+sellList.get(12)+
	        		"&sellId[]=41&sellName[]=%E9%80%9A%E8%AE%AF%E5%99%A8%E6%9D%90%E7%B1%BB&sell[]="+sellList.get(13)+
	        		"&sellId[]=42&sellName[]=%E5%BB%BA%E7%AD%91%E8%A3%85%E6%BD%A2%E6%9D%90%E6%96%99&sell[]="+sellList.get(14)+
	        		"&sellId[]=43&sellName[]=%E5%86%9C%E8%B5%84%E7%94%9F%E4%BA%A7%E7%B1%BB&sell[]="+sellList.get(15)+
	        		"&sellId[]=44&sellName[]=%E5%8C%96%E5%B7%A5%E4%BA%A7%E5%93%81%E7%B1%BB&sell[]="+sellList.get(16)+
	        		"&sellId[]=45&sellName[]=%E6%9C%BA%E7%94%B5%E7%B1%BB&sell[]="+sellList.get(17)+
	        		"&sellId[]=46&sellName[]=%E6%9C%A8%E6%9D%90%E7%B1%BB&sell[]="+sellList.get(18)+
	        		"&btnSave=+%E4%B8%8A+%E6%8A%A5++";
	        pw.print(value);
	        pw.flush();
	        reader=new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            if(result.indexOf("填报成功！") !=-1){
            	saveInfo(buyList,sellList);
            }
            int index=result.indexOf("alert('")+"alert('".length();
            String back=result.substring(index,result.indexOf('\'',index));
            System.out.println(back);
            return back;
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return result;
	}
	
	private void saveInfo(List<BigDecimal> buyList,List<BigDecimal> sellList){
		//保存代买数据
		ReportInfo info=new ReportInfo();
		info.setLingyou(buyList.get(0));
		info.setSxsp(buyList.get(1));
		info.setGanhuo(buyList.get(2));
		info.setXxsp(buyList.get(3));
		info.setYanjiu(buyList.get(4));
		info.setFzzs(buyList.get(5));
		info.setHzp(buyList.get(6));
		info.setJyzb(buyList.get(7));
		info.setRyp(buyList.get(8));
		info.setJiadian(buyList.get(9));
		info.setZxyp(buyList.get(10));
		info.setWhbg(buyList.get(11));
		info.setJiaju(buyList.get(12));
		info.setTxqc(buyList.get(13));
		info.setJzzhcl(buyList.get(14));
		info.setNzsc(buyList.get(15));
		info.setHgcp(buyList.get(16));
		info.setJidian(buyList.get(17));
		info.setMucai(buyList.get(18));
		
		info.setMode(1);
		BigDecimal total =new BigDecimal(0);
		for (BigDecimal bigDecimal : buyList) {
			total=total.add(bigDecimal);
		}
		info.setDaimaiTotal(total);
		reportInfoService.saveReportInfo(info);
		//以下是保存卖出数据
		info=new ReportInfo();
		info.setLingyou(sellList.get(0));
		info.setSxsp(sellList.get(1));
		info.setGanhuo(sellList.get(2));
		info.setXxsp(sellList.get(3));
		info.setYanjiu(sellList.get(4));
		info.setFzzs(sellList.get(5));
		info.setHzp(sellList.get(6));
		info.setJyzb(sellList.get(7));
		info.setRyp(sellList.get(8));
		info.setJiadian(sellList.get(9));
		info.setZxyp(sellList.get(10));
		info.setWhbg(sellList.get(11));
		info.setJiaju(sellList.get(12));
		info.setTxqc(sellList.get(13));
		info.setJzzhcl(sellList.get(14));
		info.setNzsc(sellList.get(15));
		info.setHgcp(sellList.get(16));
		info.setJidian(sellList.get(17));
		info.setMucai(sellList.get(18));
		
		info.setMode(2);
		total =new BigDecimal(0);
		for (BigDecimal bigDecimal : sellList) {
			total=total.add(bigDecimal);
		}
		info.setDaimaiTotal(total);
		reportInfoService.saveReportInfo(info);
	}
	
	/**
	 * 按日期查询
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/report/findByDate")
	@ResponseBody
	public List<ReportInfo> findReportInfoByDate(String date) throws ParseException{
		System.out.println(date);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d=sdf.parse(date);
		List<ReportInfo> infos=reportInfoService.findReportInfoByDate(d);
		return infos;
	}
}

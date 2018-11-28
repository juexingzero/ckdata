package com.manhui.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * @ClassName: FileUpload
 * @description: 文件上传下载删除
 * @author:	WangSheng
 * @date Create in 2017年11月06日11:15:05
 * @version: v1.0.0
 * @modify By: HeJiayan  2017年12月05日
 * @Copyright: 版权由满惠科技拥有
 */
public class FileUploadUtil {
	public static Logger logger = Logger.getLogger(FileUploadUtil.class);
	
	/**
	 * 单张图片上传
	 * @param file
	 */
	public static boolean upload(HttpServletRequest request, MultipartFile file, String name){
		boolean flag = true;
		//获取保存文件的路径
				String path=request.getSession().getServletContext().getRealPath("");
				String pathRoot=path+"/"+name;
				//获取图片名称
				String fileName=file.getOriginalFilename();
				
				File targetFile=new File(pathRoot,fileName);
				
				//保存
				try {
					file.transferTo(targetFile);
					 flag = false;
				} catch (IllegalStateException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		return flag;
	}
	
	
	/**
	 * 多图片上传
	 * @param file
	 */
	public static List<String> moerPicture(HttpServletRequest request,@RequestParam(value="file") MultipartFile[] file,String name){
		List<String> list=new ArrayList<>();
		 String pathRoot = request.getSession().getServletContext().getRealPath("");  
		 String te="";
		 String path="";  
		 for (MultipartFile mf : file) {  
	        	@SuppressWarnings("unused")
				int i=0;
	            if(!mf.isEmpty()){    
	                //获得文件类型（可以判断如果不是图片，禁止上传）  
	                String fileName = mf.getOriginalFilename();
	                te+=fileName+",";
	                path=name+"/"+fileName;
	                //pathRoot是路径  path是文件
	                list.add(pathRoot+path);
	                try {
						mf.transferTo(new File(pathRoot+path));
					} catch (IllegalStateException e) {
					
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}  
	            }  
	            i++;
	        }  
		 System.out.println("走到这没有");
		 return list;
	}
	
	
	/**
	 * 删除文件
	 * @param file
	 */
	 public String deleteFile(String fileName) {
	        File file = new File("d://",fileName);
	        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	        if (file.exists() && file.isFile()) {
	            if (file.delete()) {
	                return "删除成功";
	            } else {
	                return "删除失败";
	            }
	        } else {
	            return "删除失败，该文件不存在";
	        }
	    }
	 
	 
	 /**
		 * 下载文件
		 * @param file
	 * @throws UnsupportedEncodingException 
		 */
	 public void downLoad(String filename,HttpServletResponse response) throws UnsupportedEncodingException{
		 
	        String filePath = "D://" ;
	        File file = new File(filePath + "/" + filename);
	        if(file.exists()){ //判断文件父目录是否存在
	            response.setContentType("application/force-download");
	            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(filename, "UTF-8")); 
	            
	            byte[] buffer = new byte[1024];
	            FileInputStream fis = null; //文件输入流
	            BufferedInputStream bis = null;
	            
	            OutputStream os = null; //输出流
	            try {
	                os = response.getOutputStream();
	                fis = new FileInputStream(file); 
	                bis = new BufferedInputStream(fis);
	                int i = bis.read(buffer);
	                while(i != -1){
	                    os.write(buffer);
	                    i = bis.read(buffer);
	                }
	                
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            try {
	                bis.close();
	                fis.close();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
		 
	 }
	 
	 /**
	  * @description: 下载相关文件
	  * @param  request
	  * @param  response
	  * @param  fileName
	  * @param  filePath
	  * @return 
	  * @throws
	  */
	 public static void download(HttpServletRequest request, HttpServletResponse response, String fileName,
			String filePath) {
		 filePath = request.getSession().getServletContext().getRealPath(filePath);     //本地测试用地址
		 //filePath = "/manhui-plan/" + filePath;    //部署到linux服务器上时，使用这个下载地址
		 
		 File file = null;
         BufferedInputStream input = null;
         BufferedOutputStream out = null;
         OutputStream os = null;
		 try {
			 response.reset();
			 response.setHeader("conent-type", "application/octet-stream");  
             response.setContentType("application/octet-stream");
             response.setContentType("application/msexcel"); 
             response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(fileName, "UTF-8"));
	         System.out.println(filePath+fileName);
	         file = new File(filePath+fileName);
		     input = new BufferedInputStream(new FileInputStream(file));
			 os = response.getOutputStream();
			 out = new BufferedOutputStream(os);
			 byte[] buffer = new byte[2048];
			 int len;
			 while((len = input.read(buffer)) != -1) {
				 out.write(buffer, 0, len);
			 }
			 out.close();
			out.flush();
        	 input.close();
        	 os.close();
        	 logger.info("下载成功！");
		 } catch (Exception e) {
			 logger.error(e.getMessage());
		 }
	 }
	 
	 /**
	  * @description: TODO
	  * @param fileS
	  * @param 
	  * @return 
	  * @throws
	  */
	 public static String FormetFileSize(long size) {
       DecimalFormat df = new DecimalFormat("#.00");
       String fileSizeString = "";
       if (size < 1024) {
           fileSizeString = df.format((double) size) + "B";
       } else if (size < 1048576) {
           fileSizeString = df.format((double) size / 1024) + "KB";
       } else if (size < 1073741824) {
           fileSizeString = df.format((double) size / 1048576) + "MB";
       } else {
           fileSizeString = df.format((double) size / 1073741824) +"GB";
       }
       return fileSizeString;
    }

}

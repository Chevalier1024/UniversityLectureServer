package com.universitylecture.universitylecture.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;

import com.universitylecture.universitylecture.dao.LectureDao;
import com.universitylecture.universitylecture.dao.impl.LectureDaoImpl;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.util.MD5Util;

@SuppressWarnings("deprecation")
@WebServlet("/UploadImageServlet")
public class UploadImageServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
				throws ServletException,IOException {
		doPost(request,response);	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException,IOException {
		request.setCharacterEncoding("utf-8");  
        //获得磁盘文件条目工厂。  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        
        //获取文件上传需要保存的路径，upload文件夹需存在。  
        String path = request.getSession().getServletContext().getRealPath("/image");  

        
        File directory = new File(path);
	      //如果文件夹不存在则创建    
	      if  (!directory .exists()  && !directory .isDirectory())      
	      {       
	          System.out.println("//不存在");  
	          directory .mkdir();    
	      } else   
	      {  
	          System.out.println("//目录存在");  
	      }  
        
        
       
        //设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。  
        factory.setRepository(new File(path));  
        //设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。  
        factory.setSizeThreshold(1024*1024);  
        //上传处理工具类（高水平API上传处理？）  
        ServletFileUpload upload = new ServletFileUpload(factory);  
          
        try{  
            //调用 parseRequest（request）方法  获得上传文件 FileItem 的集合list 可实现多文件上传。  
            List<FileItem> list = upload.parseRequest(request);  
            for(FileItem item:list){  
                //获取表单属性名字。  
                String name = item.getFieldName();  
                
                
                
                //如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。  
                if(item.isFormField()){  
                    //获取用户具体输入的字符串，  
                    String value = item.getString();  
                    request.setAttribute(name, value);  
                }  
                //如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。  
                else{   
                    //获取路径名  
                    String value = item.getName();
                    String[] nameList = value.split("\\.");
                    String typeName = nameList[nameList.length-1];
                    System.out.println(Arrays.toString(nameList));
                    
                    System.out.println(value);
                    
                    File image = new File(path +"/"+ value);
                    try {
                    	if(!image.exists()) {
                    		image.createNewFile();
                    	}
                    } catch(Exception e) {
                    	e.printStackTrace();
                    }
                    item.write(image);

                    String md5 = MD5Util.getFileMD5String(image);
                    System.out.println("MD5 " + md5);
                    
                    String imageUri = path + "/" + md5 + "." + typeName;
                    File newFile = new File(imageUri);
                    image.renameTo(newFile);
		
                   OutputStream os = response.getOutputStream();
                   String file = md5 + "." + typeName;
                   os.write(file.getBytes());
                   os.close();
                }  
            }  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
          
    }  

}

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
        //鑾峰緱纾佺洏鏂囦欢鏉＄洰宸ュ巶銆�  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        
        //鑾峰彇鏂囦欢涓婁紶闇�瑕佷繚瀛樼殑璺緞锛寀pload鏂囦欢澶归渶瀛樺湪銆�  
        String path = request.getSession().getServletContext().getRealPath("/image");  

        
        File directory = new File(path);
	      //濡傛灉鏂囦欢澶逛笉瀛樺湪鍒欏垱寤�    
	      if  (!directory .exists()  && !directory .isDirectory())      
	      {       
	          System.out.println("//涓嶅瓨鍦�");  
	          directory .mkdir();    
	      } else   
	      {  
	          System.out.println("//鐩綍瀛樺湪");  
	      }  
        
        
       
        //璁剧疆鏆傛椂瀛樻斁鏂囦欢鐨勫瓨鍌ㄥ锛岃繖涓瓨鍌ㄥ鍙互鍜屾渶缁堝瓨鍌ㄦ枃浠剁殑鏂囦欢澶逛笉鍚屻�傚洜涓哄綋鏂囦欢寰堝ぇ鐨勮瘽浼氬崰鐢ㄨ繃澶氬唴瀛樻墍浠ヨ缃瓨鍌ㄥ銆�  
        factory.setRepository(new File(path));  
        //璁剧疆缂撳瓨鐨勫ぇ灏忥紝褰撲笂浼犳枃浠剁殑瀹归噺瓒呰繃缂撳瓨鏃讹紝灏辨斁鍒版殏鏃跺瓨鍌ㄥ銆�  
        factory.setSizeThreshold(1024*1024);  
        //涓婁紶澶勭悊宸ュ叿绫伙紙楂樻按骞矨PI涓婁紶澶勭悊锛燂級  
        ServletFileUpload upload = new ServletFileUpload(factory);  
          
        try{  
            //璋冪敤 parseRequest锛坮equest锛夋柟娉�  鑾峰緱涓婁紶鏂囦欢 FileItem 鐨勯泦鍚坙ist 鍙疄鐜板鏂囦欢涓婁紶銆�  
            List<FileItem> list = upload.parseRequest(request);  
            for(FileItem item:list){  
                //鑾峰彇琛ㄥ崟灞炴�у悕瀛椼��  
                String name = item.getFieldName();  
                
                
                
                //濡傛灉鑾峰彇鐨勮〃鍗曚俊鎭槸鏅�氱殑鏂囨湰淇℃伅銆傚嵆閫氳繃椤甸潰琛ㄥ崟褰㈠紡浼犻�掓潵鐨勫瓧绗︿覆銆�  
                if(item.isFormField()){  
                    //鑾峰彇鐢ㄦ埛鍏蜂綋杈撳叆鐨勫瓧绗︿覆锛�  
                    String value = item.getString();  
                    request.setAttribute(name, value);  
                }  
                //濡傛灉浼犲叆鐨勬槸闈炵畝鍗曞瓧绗︿覆锛岃�屾槸鍥剧墖锛岄煶棰戯紝瑙嗛绛変簩杩涘埗鏂囦欢銆�  
                else{   
                    //鑾峰彇璺緞鍚�  
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

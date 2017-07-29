package com.universitylecture.universitylecture.servlet;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.universitylecture.universitylecture.pojo.MobClient;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.DBUtil;

public class VerifyCodeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException {
		doPost(request,response);	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,IOException {
		
		String appkey = "1e81bc5f2c43f";
		String zone = "86";
		String address = "https://webapi.sms.mob.com/sms/verify";
		MobClient client = null;
		
		request.setCharacterEncoding("UTF-8");
		ServletInputStream in = request.getInputStream();
		User returnUser = null;
		ObjectInputStream inObj = new ObjectInputStream(in);	
		try {
			returnUser = (User) inObj.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			inObj.close();
		}
		
		try {
			client = new MobClient(address);
			client.addParam("appkey", appkey).addParam("phone", returnUser.getPhoneNumber())
					.addParam("zone", zone).addParam("code", returnUser.getCode());
			client.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			client.addRequestProperty("Accept", "application/json");
			String result = client.post();
			System.out.println(result);
			JSONObject obj = JSONObject.fromObject(result);
			
			switch(obj.getString("status")) {
				case "200":
					returnUser.setMessage("OK");
					break;
				case "456":
					returnUser.setMessage("手机号码不能为空");
					break;
				case "457":
					returnUser.setMessage("手机号码格式错误");
					break;
				case "466":
					returnUser.setMessage("验证码不能为空");
					break;
				case "467":
					returnUser.setMessage("请求验证码频繁");
					break;
				case "468":
					returnUser.setMessage("验证码错误");
					break;
			}
			
			String sql = "select * from user where phoneNumber = ?";
			
			DBUtil util = new DBUtil();
			Connection conn = util.openConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				
				//获得prepareStatement
				ps = conn.prepareStatement(sql);
				
				//设置查询参数
				ps.setString(1, returnUser.getPhoneNumber());
				
				//执行查询
				rs = ps.executeQuery();
				//判断手机号是否存在
				if(rs.next()) {
					returnUser.setMessage("手机号已注册");
				}
			}  catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) {
						rs.close();
						rs = null;
					}
					if(ps != null) {
						ps.close();
						ps = null;
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
				util.closeConnection(conn);
			}
			
			
			response.setContentType("application/x-java-serialized-object");
			OutputStream os = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			
			oos.writeObject(returnUser);
			oos.writeObject(null);
			oos.flush();
			os.close();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			client.release();
		}
		
		
		
	}
}

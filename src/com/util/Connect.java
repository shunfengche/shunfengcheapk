package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Scanner;






import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.app.po.ChatClass;
import com.app.po.Comment;
import com.app.po.Driver;
import com.app.po.Line;
import com.app.po.Order;
import com.app.po.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shunfengche.Activity.DriverRegisterActivity;
import com.shunfengche.Activity.MainlistActivity;

public final class Connect {
	//private static String url="http://192.168.22.1:8080/shenfengcheweb/";
	static String url="http://192.168.23.1:8080/shenfengcheweb/";
	//private static String url="http://127.0.0.1:8080/shenfengcheweb/";
	private static String secret="ffefofeofjaifeofafeofef";
	 //网络状态
	private static Context ctv;
	/*private static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO 接收消息并且去更新UI线程上的控件内容
			switch(msg.what ) {
			case 0:
				Toast.makeText(ctv, "网络未连接！", 1000).show();
				break;
		
		case 3:
			Toast.makeText(ctv, "服务器连接不上！", Toast.LENGTH_LONG).show();
			break;
			}
			super.handleMessage(msg);
		}
	};*/
    public static boolean isNetworkReachable(Context ctvs){
    	boolean flag=true;
    	ctv=ctvs;
  	  ConnectivityManager mManager=(ConnectivityManager)ctv.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo current=mManager.getActiveNetworkInfo();
			if(current==null) {
				flag=false;
				//handler.sendEmptyMessage(0);
			}
			
				
				try {
					URL Url = new URL(url+"index.jsp");
					HttpURLConnection httpURLConnection = (HttpURLConnection) Url
							.openConnection();
					httpURLConnection.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					flag=false;
					//handler.sendEmptyMessage(3);
				}
				
			return flag;
    }

	//会员注册连接
     public  static boolean dogusetregister(String str){
    	 String urls=url+"guestregisterservlert";
    	 return dos(urls,str);
     }
     //车主发布路线连接
     public  static boolean dopostdriverline(String str){
    	
    	 String urls=url+"Linepointservlert";
    	 return dos(urls,str);
     }
     //用户名是否存在判断
     public  static boolean hasusername(String str){
    	 String urls=url+"hasusernameservlert";
    	 return dos(urls,str);
     }
     //司机注册连接
     //
     public static boolean dodriverregister(String str){
    	 String urls=url+"driverregisterservlert";
    	 return dos(urls,str);
     }
     //登陆连接
     public static boolean dologin(String str,MyApplication app){
    	 String urls=url+"loginservlert";
    	 String txt= getconnectout( urls, str);
    	 Log.e("txt", txt);
    	 if(txt==null)return false;
    	 else if(txt.contains("result")){
    		 String results = "";
 			JSONObject result;
 			try {
 				result = new JSONArray(txt).getJSONObject(0);
 				
 			
 			  if(result.get("result")!=null)return false;
 			} catch (JSONException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			System.out.println("-----result:"+results);
 			
    	 }
  		Gson json = new Gson();
  			Type listType = new TypeToken<List<User>>() {
  			}.getType();

  			List<User> list = json.fromJson(txt, listType);
  			
  			app.setUser(list.get(0));
         
    	 return true;
     }
     public static boolean getMydriver(String str,MyApplication app){
    	 String urls=url+"getdriveinfoservlert";
    	 String txt= getconnectout( urls, str);
    	 Log.e("txt", txt);
    	 if(txt==null)return false;
    	 else if(txt.contains("result")){
    		 String results = "";
 			JSONObject result;
 			try {
 				result = new JSONArray(txt).getJSONObject(0);
 				
 			
 			  if(result.get("result")!=null)return false;
 			} catch (JSONException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			System.out.println("-----result:"+results);
 			
    	 }
    	
  		Gson json = new Gson();
  			Type listType = new TypeToken<List<Driver>>() {
  			}.getType();

  			List<Driver> list = json.fromJson(txt, listType);
  			if(list.size()==0)return false;
  			app.setMydriver(list.get(0));
         
    	 return true;
     }
     private static String getconnectout(String urls,String bf){
    	 StringBuilder sb = new StringBuilder();
    	 try{
     		URL Url = new URL(urls);
 			bf=bf+"&secret="+secret;
 			HttpURLConnection httpURLConnection = (HttpURLConnection) Url
 					.openConnection();

 			httpURLConnection.setDoInput(true);
 			httpURLConnection.setDoOutput(true);
 			httpURLConnection.setUseCaches(false);
 			httpURLConnection.setRequestMethod("POST");
 			httpURLConnection.setRequestProperty("Charset", "UTF-8");
 			httpURLConnection.getOutputStream().write(
 					bf.toString().getBytes("UTF-8"));
 			
 			InputStream is = httpURLConnection.getInputStream();
 			BufferedReader reader = new BufferedReader(new InputStreamReader(
 					is, "UTF-8"), 8);
 			 
 			String line = null;
 			while ((line = reader.readLine()) != null) {
 				sb.append(line );
 			}
 			
 			
 			
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
    	   return sb.toString();
     }
     private static boolean dos(String urls,String bf){
    	String txt= getconnectout( urls, bf);
    	String results = "";
			JSONObject result;
			try {
				result = new JSONObject(txt);
			
			 results=(String) result.get("result");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("-----result:"+results);
			if(results.equals("1111")){
				return true;
			}
			
			
    	 return false;
     }
 	// type=0查询数据库
 	public List<?> getlist(String table, String bf) {
 		 String urls=url+"getlistservlert";
 		
 		String txt= getconnectout( url, bf);
 		List<?> lists = null;
 		Gson json = new Gson();
 		
 		
 		

 		
 	
 		if (table.equals("usertb")) {
 			Type listType = new TypeToken<List<User>>() {
 			}.getType();

 			List<User> list = json.fromJson(txt, listType);

 			lists = list;
 		}
 		if (table.equals("drivertb")) {
 			Type listType = new TypeToken<List<Driver>>() {
 			}.getType();

 			List<Driver> list = json.fromJson(txt, listType);

 			lists = list;
 		}
 		if (table.equals("linetb")) {
 			Type listType = new TypeToken<List<Line>>() {
 			}.getType();

 			List<Line> list = json.fromJson(txt, listType);

 			lists = list;
 		}
 		if (table.equals("ordertb")) {
 			Type listType = new TypeToken<List<Order>>() {
 			}.getType();

 			List<Order> list = json.fromJson(txt, listType);

 			lists = list;
 		}
 		if (table.equals("commenttb")) {
 			Type listType = new TypeToken<List<Comment>>() {
 			}.getType();

 			List<Comment> list = json.fromJson(txt, listType);

 			lists = list;
 		}

 		if (table.equals("chattb")) {
 			Type listType = new TypeToken<List<ChatClass>>() {
 			}.getType();

 			List<ChatClass> list = json.fromJson(txt, listType);

 			lists = list;
 		}
 		return lists;
 	}
	// type=1,对数据库进行增删改操作，type=2,用户名是否已经存在判断，type=3,登陆判断
	//
	public String doS(String sql, int type, String tablename, String username,
			String password) {
		url =Maputil.url;
		StringBuffer bf = new StringBuffer(url);
		url="0";
		switch (type) {
		case 1:
			bf.append("Mytextservlert");

			break;
		case 2:
			System.out.println(username);

			try {
				bf.append("isusername?username=").append(
						URLEncoder.encode(username, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;
	
		case 3:
			try {
				bf.append("loginservlert?username=")
						.append(URLEncoder.encode(username, "UTF-8"))
						.append("&password=").append(password);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;
		}
		try {

			// url=URLEncoder.encode(url, "UTF-8");
			URL Url = new URL(bf.toString());
			HttpURLConnection httpURLConnection = (HttpURLConnection) Url
					.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");

			if (type == 1) {
				JSONObject temp = new JSONObject();
				try {
					temp.put("sql", sql).put("tablename", tablename)
							.put("type", type);

				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				httpURLConnection.getOutputStream().write(
						temp.toString().getBytes("UTF-8"));
			}
			InputStream is = httpURLConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			url = sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(url);
		return url;
	}

	// type=0查询数据库
	public List<?> getlist(int type, String table, String sql) {
		List<?> lists = null;
		Gson json = new Gson();
		StringBuilder sb = new StringBuilder();
		JSONObject temp = new JSONObject();
		url = Maputil.url+"Mytextservlert";
		StringBuffer bf = new StringBuffer(url);

		try {
			temp.put("sql", sql).put("tablename", table).put("type", type);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			// url=URLEncoder.encode(url, "UTF-8");
			URL Url = new URL(bf.toString());
			HttpURLConnection httpURLConnection = (HttpURLConnection) Url
					.openConnection();

			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.getOutputStream().write(
					temp.toString().getBytes("UTF-8"));

			InputStream is = httpURLConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			url = sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if (table.equals("usertb")) {
			Type listType = new TypeToken<List<User>>() {
			}.getType();

			List<User> list = json.fromJson(sb.toString(), listType);

			lists = list;
		}
		if (table.equals("drivertb")) {
			Type listType = new TypeToken<List<Driver>>() {
			}.getType();

			List<Driver> list = json.fromJson(sb.toString(), listType);

			lists = list;
		}
		if (table.equals("linetb")) {
			Type listType = new TypeToken<List<Line>>() {
			}.getType();

			List<Line> list = json.fromJson(sb.toString(), listType);

			lists = list;
		}
		if (table.equals("ordertb")) {
			Type listType = new TypeToken<List<Order>>() {
			}.getType();

			List<Order> list = json.fromJson(sb.toString(), listType);

			lists = list;
		}
		if (table.equals("comment")) {
			Type listType = new TypeToken<List<Comment>>() {
			}.getType();

			List<Comment> list = json.fromJson(sb.toString(), listType);

			lists = list;
		}

		if (table.equals("chat")) {
			Type listType = new TypeToken<List<ChatClass>>() {
			}.getType();

			List<ChatClass> list = json.fromJson(sb.toString(), listType);

			lists = list;
		}
		return lists;
	}
   //计算油费价格订单,distant单位为米。
	public String getoilMoney(int distant,int seat){
		url =Maputil.url+"getoilmoneyservlert?distant="+distant+"&seat="+seat;
	
		URL Url = null;
		try {
			Url = new URL(url);
	
		HttpURLConnection httpURLConnection;
		
			httpURLConnection = (HttpURLConnection) Url
					.openConnection();
	

		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setRequestProperty("Charset", "UTF-8");
	
		InputStream is = httpURLConnection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				is, "UTF-8"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		url = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			url="";
		}
		return url;
	}
	
}
package com.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;




import android.content.Context;
import android.os.Environment;
import android.widget.BaseAdapter;
import android.widget.Toast;



import com.android.app.dao.ChatDao;
import com.app.po.ChatClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Clientsocket {
    public void startsocket(int port,Context context){
    
    	clientsocket c= new clientsocket(port,context);
        
        c.start();
    }
    class clientsocket extends Thread{
    	int port;
    	Context context;
    	BaseAdapter myAdapter;
    	public clientsocket(int port,Context context){
    		this.port = port;
    		this.context=context;
    		
    	}
    	public void run(){
    		try{
    		   
    			Socket socket = new Socket("192.168.80.21",port);
    			InputStream is = socket.getInputStream();
    			BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
    			StringBuffer sb = new StringBuffer();
    			String line = null;
    			
    			while((line = reader.readLine())!= null){
    				sb.append(line+"\n");
    			}
    			if(sb.length()>=3){
    				
    			
    			System.out.println("Clientsocket:"+sb.toString());
    		
    			
    			Gson gson = new Gson();
    			Type listType = new TypeToken<List<ChatClass>>() {
    			}.getType();

    			List<ChatClass> list = gson.fromJson(sb.toString(), listType);
    			
    			for(int i=0;i<list.size();i++){
    				ChatClass chat=list.get(i);
    				ChatDao chatDao = new ChatDao(context);
    				int fromid = chat.getFromid();
    				int toid = chat.getToid();
    				int flag=chat.getFlag();
    				String msg=chat.getChatcontent();
    			//	UserClass.chatflag.put(fromid, 1);
    				//声音图片文件
    				if(flag==2||flag==3){
    					
    					uploadFileClass up=new uploadFileClass();
    					 if( up.downloadfile(msg).equals("下载成功")){
    						 //Toast.makeText(context, "下载成功", duration)
    						 System.out.print("下载成功");
    						 String name=msg.substring(msg.lastIndexOf("/"));
    						 msg=Environment.getExternalStorageDirectory().toString()+"/Download"+name;
    						 
    					 }
    					 else  System.out.print("下载失败");
    					  
    				}
    				
    				
    				String time =chat.getTime();
    				//
    				int freeze = 0;
    				String strSQL = "insert into chat values(null,'" + fromid
    						+ "','" + toid + "','" + msg + "','" + time + "','"
    						+ flag + "','" + freeze + "')";
                   System.out.println(strSQL);   
    				boolean index = chatDao.execOther(strSQL);
    				
    			}
    		
    			}
    			
    			socket.close();
    		}
    		catch(Exception e){
    			e.printStackTrace();
    			
    		}
    		
    	}
    	
    }
}

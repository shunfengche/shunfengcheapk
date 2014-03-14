package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.android.app.dao.ChatDao;
import com.shunfengche.Activity.R;

public class Chat_DataOperation {
		private ChatDao chatDao;
		// 创建�?��私有的getData方法用于初始化List列表中的数据
		//将字符创形式的数据源转化为List形式
		public List<Map<String, ?>> getData(String sql,Context context) {
			// TODO Auto-generated method stub
			// 创建�?��空的List集合，用于存放item选项
			List<Map<String, ?>> lstData = new ArrayList<Map<String, ?>>();
			// 获取待封装数�?  
			String result = fetchData(sql,context);
			// 创建�?��item选项
			Map<String, Object> item ;  
			if(result==null){
				// 将封装好的lstData集合返回
				return lstData;
			}else{
				// 循环嵌套循环完成对每条信息的封装
				String[] rows = result.split("#");
				for (int i = 0; i < rows.length; i++) {
					// 使用split方法继续分割数据
					String[] cols = rows[i].split("-");
					// 创建�?��item选项
					item = new HashMap<String, Object>();
					// 为该选项（item）赋�?
					item.put("content_id", cols[0]);
					item.put("friend_pic", R.drawable.andy_icon1);
					item.put("chat_content", cols[1]);
					item.put("chat_time", cols[2]);
					item.put("fromid", cols[3]);
					item.put("toid", cols[4]);
					item.put("flag", cols[5]);
					item.put("freeze", cols[6]);
					// 将创建好的item01放入到lstData集合�?
					lstData.add(item);
			}		
			}
			// 将封装好的lstData集合返回
			return lstData;
		}
		
		// 编写�?��内部私有的方法获取List组建中的数据（数据库，互联网�?
				public String fetchData(String sql,Context context) {
					// 实例化ChatDao对象，完成数据库的操�?  
					chatDao = new ChatDao(context);	
					String strSQL = sql;
					String data = chatDao.execQuery(strSQL);
					return data;
				}
}

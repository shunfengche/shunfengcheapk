package com.shunfengche.Activity;

import java.util.List;
import java.util.Map;

import com.util.Chat_DataOperation;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShowAll extends Activity {
	// ����һ��ȫ���͵�List���ϣ����ڴ��List�б��е����?
		private List<Map<String,?>> data;
	// ������ͼ�齨����
		private ListView chat_list;
	//����һ�����ذ�ť
		Button back = null;
		Chat_DataOperation cdo = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȡ�����ı�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_all);
		cdo = new Chat_DataOperation();
		// ����һ��˽�еķ������ڳ�ʼ��data���?
				String sql = "select * from chat order by time";
				data = cdo.getData(sql,ShowAll.this);
				// ��ʼ��ListView�齨
				chat_list = (ListView)findViewById(R.id.chat_show_all);
				//ȡ��ListView�ķָ���
				chat_list.setDivider(null);
				// �����Զ����˽�з�����ʼ��ListView�齨
				this.initListView(chat_list);
				//��ʼ��һ�����ذ�ť
				back = (Button) findViewById(R.id.return_back);
				back.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(ShowAll.this, Chat.class);
						ShowAll.this.startActivity(intent);
					}
				});
				
				
				
	}
	// ��ListView�齨�ķ�װ
		private void initListView(ListView chat_list) {
			//ʵ���Զ���������
			MyListViewAdapter myAdapter = new MyListViewAdapter(this,this.data);
			//���趨�õ��������󶨵�ListView�齨��
			chat_list.setAdapter(myAdapter);
			//ListViewˢ�º��Զ�������ײ�?
			chat_list.setSelection(chat_list.getAdapter().getCount()-1); 
		}
	//����ListVirew���Զ���������
		class MyListViewAdapter extends BaseAdapter{
			int flag = 1;
			//������Ҫ�󶨵�����?
			private List<Map<String, ?>> content;
			//�������ڵ�Activity
			private Context context;
			//���췽��
			public MyListViewAdapter(Context context,List<Map<String, ?>> content){
				this.content = content;
				this.context = context;
			}
			
			public int getCount() {
				// TODO Auto-generated method stub
				return content.size();
			}

			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return content.get(position);
			}

			public long getItemId(int position) {
				// ���ضԻ�����
				return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				View statusView = null;
				ViewHolder holder = null;
				holder = new ViewHolder();
				TextView showTitle = null;
				
					statusView = LayoutInflater.from(context).inflate(R.layout.chat_bg_top, null);
					int fromid;
					for(int i =0 ; i < content.size() ; i++){
						fromid = Integer.parseInt(content.get(position).get("fromid").toString());					
						
						if(fromid == 1){
							statusView = LayoutInflater.from(context).inflate(R.layout.andy_chat_content, null);
							holder.pic = (ImageView) statusView.findViewById(R.id.chat_pic);
							holder.content = (TextView)statusView.findViewById(R.id.chat_content);
							holder.time = (TextView)statusView.findViewById(R.id.chat_time);
							//�趨ͷ��
							holder.pic.setImageResource(Integer.parseInt(content.get(position).get("friend_pic").toString()));
							//�趨��������
							holder.content.setText(content.get(position).get("chat_content").toString());
							//�趨����ʱ��"chat_time"
							holder.time.setText(content.get(position).get("chat_time").toString());
						/*	Toast.makeText(getApplicationContext(), flag,
									Toast.LENGTH_LONG).show();*/
						}
						
						else{
							statusView = LayoutInflater.from(context).inflate(R.layout.andy_chat_content_receive, null);
							holder.pic = (ImageView) statusView.findViewById(R.id.chat_pic_receive);
							holder.content = (TextView)statusView.findViewById(R.id.chat_content_receive);
							holder.time = (TextView)statusView.findViewById(R.id.chat_time_receive);
							//�趨ͷ��
							holder.pic.setImageResource(Integer.parseInt(content.get(position).get("friend_pic").toString()));
							//�趨��������
							holder.content.setText(content.get(position).get("chat_content").toString());
							//�趨����ʱ��"chat_time"
							holder.time.setText(content.get(position).get("chat_time").toString());
							/*Toast.makeText(getApplicationContext(), flag,
									Toast.LENGTH_LONG).show();*/
						}
					}

				/**/
				return statusView;
			}
			
		}
		//����һ����̬�����ڻ�ȡ�ʹ����б���ÿ����Ŀ��ݵĸ���?
		public static class ViewHolder{
			ImageView pic;//�����û�ͷ��
			TextView content;//����������Ϣ
			TextView time; //��������ʱ��
			}
}

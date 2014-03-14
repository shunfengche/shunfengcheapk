package com.shunfengche.Activity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.util.Chat_DataOperation;
import com.util.MyApplication;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Chat_Batch_Operation extends Activity {
		LinearLayout layout;
		int userid = 10;	
		
			private List<Map<String,?>> data;
		
			private ListView chat_list;
		
			Button back = null;
			Chat_DataOperation cdo = null;
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		// ȡ�����ı�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.andy_batch_operation);
		MyApplication myapp=(MyApplication) getApplication();
		userid=myapp.getUser().getUserid();
		chat_list = (ListView) findViewById(R.id.chat_batch_operation);
		//ȡ��ListView�ķָ���
		chat_list.setDivider(null);
		
		layout = (LinearLayout) findViewById(R.id.chat_batch_opera);		
		// ����SharedPreferences�������ڶ�ȡ���?
		SharedPreferences preferences = getSharedPreferences("image_content", MODE_WORLD_WRITEABLE);
		// ����2����ȡkey�е����?
		int url = preferences.getInt("content"+userid, R.drawable.andy_chat_bg9);
		layout.setBackgroundResource(url);
		
		
		cdo = new Chat_DataOperation();
		// ����һ��˽�еķ������ڳ�ʼ��data���?
				String sql = "select * from chat order by time";
				data = cdo.getData(sql,Chat_Batch_Operation.this);			
				//ȡ��ListView�ķָ���
				chat_list.setDivider(null);
				// �����Զ����˽�з�����ʼ��ListView�齨
				this.initListView(chat_list);
				//��ʼ��һ�����ذ�ť
				back = (Button) findViewById(R.id.return_back_batch);
				back.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Chat_Batch_Operation.this, Chat.class);
						Chat_Batch_Operation.this.startActivity(intent);
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
					ViewHolder1 audioholder=null;
					audioholder = new ViewHolder1();
					ViewHolder1 picholder = null;
					picholder = new ViewHolder1();
						int fromid;
						int flag;
						for(int i =0 ; i < content.size() ; i++){
							fromid = Integer.parseInt(content.get(position).get("fromid").toString());
							flag = Integer.parseInt(content.get(position).get("flag").toString());
							if(fromid == 1){
								if(flag==1){
								statusView = LayoutInflater.from(context).inflate(R.layout.andy_batch_send, null);
								
								holder.content = (TextView)statusView.findViewById(R.id.chat_batch_send);
								holder.time = (TextView)statusView.findViewById(R.id.chat_batch_time_send);
								
								//�趨��������
								holder.content.setText(content.get(position).get("chat_content").toString());
								//�趨����ʱ��"chat_time"
								holder.time.setText(content.get(position).get("chat_time").toString());						
								}
								else if(flag==2){				
									statusView = LayoutInflater.from(context).inflate(R.layout.andy_batch_audio_send, null);
									
	
									audioholder.icon = (ImageView)statusView.findViewById(R.id.chat_batch_icon_audio_send);
									//Ϊ����ͼƬ����¼�������?
									audioholder.icon.setOnClickListener(new ImageOnClickListener(content,position));
									
									audioholder.time = (TextView)statusView.findViewById(R.id.chat_batch_audio_time_send);
									
									//�趨��������
									audioholder.icon.setImageResource(R.drawable.andy_mms_play_btn);
									//�趨����ʱ��"chat_time"
									audioholder.time.setText(content.get(position).get("chat_time").toString());
								}
								else if(flag==3){
									statusView = LayoutInflater.from(context).inflate(R.layout.andy_batch_pic_send, null);
									
									picholder.icon = (ImageView)statusView.findViewById(R.id.chat_batch_icon_photo_send);
									//Ϊ����ͼƬ����¼�������?
									picholder.icon.setOnClickListener(new PhotoOnClickListener(content,position));
									
									picholder.time = (TextView)statusView.findViewById(R.id.chat_batch_photo_time_send);
									//�趨ͷ��
									String url = content.get(position).get("chat_content").toString();
									picholder.icon.setImageBitmap(BitmapFactory.decodeFile(url));
									
									picholder.time.setText(content.get(position).get("chat_time").toString());
								}
							}
							else{
								if(flag==1){
								statusView = LayoutInflater.from(context).inflate(R.layout.andy_batch_receive, null);						
								holder.content = (TextView)statusView.findViewById(R.id.chat_batch_receive);
								holder.time = (TextView)statusView.findViewById(R.id.chat_batch_time_receive);
							
								//�趨��������
								holder.content.setText(content.get(position).get("chat_content").toString());
								//�趨����ʱ��"chat_time"
								holder.time.setText(content.get(position).get("chat_time").toString());		
								}
								else if(flag==2){					
									statusView = LayoutInflater.from(context).inflate(R.layout.andy_batch_audio_receive, null);
									
									audioholder.icon= (ImageView)statusView.findViewById(R.id.chat_batch_icon_audio_receive);
									audioholder.time = (TextView)statusView.findViewById(R.id.chat_batch_audio_time_receive);
									
									//�趨��������
									audioholder.icon.setImageResource(R.drawable.andy_mms_play_btn);							
									//�趨����ʱ��"chat_time"
									audioholder.time.setText(content.get(position).get("chat_time").toString());
									//Ϊ����ͼƬ����¼�������?
									audioholder.icon.setOnClickListener(new ImageOnClickListener(content,position));			
								}
								else if(flag==3){

									//Ϊ����ͼƬ����¼�������?
									//picholder.icon.setOnClickListener(new ImageOnClickListener(content,position));
									//picholder.icon.setOnClickListener(new PhotoOnClickListener(content,position));
	
								
								
									statusView = LayoutInflater.from(context).inflate(R.layout.andy_batch_pic_receive, null);
									picholder.icon = (ImageView)statusView.findViewById(R.id.chat_batch_icon_photo_receive);
									//Ϊ����ͼƬ����¼�������?
		/*							audioholder.audio_icon.setOnClickListener(new ImageOnClickListener(content,position));
		*/							
									picholder.time = (TextView)statusView.findViewById(R.id.chat_batch_photo_time_receive);
									String url = content.get(position).get("chat_content").toString();
									picholder.icon.setImageBitmap(BitmapFactory.decodeFile(url));	
									picholder.time.setText(content.get(position).get("chat_time").toString());
									picholder.icon.setOnClickListener(new PhotoOnClickListener(content,position));

								}
								}
						}
					return statusView;
				}
				
			}
			 class ImageOnClickListener implements OnClickListener {
		    	 private int position;
		    	//������Ҫ�󶨵�����?
		 		private List<Map<String, ?>> content;
		         ImageOnClickListener(List<Map<String, ?>> content,int pos) {
		             position = pos;
		             this.content=content;
		         }
		         MediaPlayer mp=new MediaPlayer();
		  	    public void playrecord(String recordurl){
		  			  
		  		   mp.reset();
		  		  
		  		   try {
		  			mp.setDataSource(recordurl);
		  		} catch (IllegalArgumentException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  		} catch (IllegalStateException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  		} catch (IOException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  		}
		  		   try {
		  			mp.prepare();
		  		} catch (IllegalStateException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  		} catch (IOException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  		}
		  		   mp.start();
		  	
		  	   }
		  	
		          public void onClick(View v) {
		        	  String recordurl = (String) content.get(position).get("chat_content");
				        Toast.makeText(getApplicationContext(),recordurl,Toast.LENGTH_SHORT).show();
						playrecord(recordurl);
		  			}
		      }
		    //ΪͼƬ����¼�������?
		    class PhotoOnClickListener implements OnClickListener {
		   	 private int position;
		   	//������Ҫ�󶨵�����?
				private List<Map<String, ?>> content;
				PhotoOnClickListener(List<Map<String, ?>> content,int pos) {
		            position = pos;
		            this.content=content;
		        }
		        
		         public void onClick(View v) {
		        	 String picurl = (String) content.get(position).get("chat_content"); 	 
			    		ImageView imgview =new ImageView(Chat_Batch_Operation.this);
			    		Dialog dialog=new Dialog(Chat_Batch_Operation.this);
			    		//��������������ʧ
			    		dialog.setCanceledOnTouchOutside(true);  		
			            imgview.setImageBitmap(BitmapFactory.decodeFile(picurl));	
			            imgview.setImageBitmap(BitmapFactory.decodeFile(picurl)
								.createScaledBitmap(BitmapFactory.decodeFile(picurl), 400,
										400, false));
			            dialog.setContentView(imgview);
			            
			            dialog.show();
		        	 
			            }
		     }
			//����һ����̬�����ڻ�ȡ�ʹ��������б���ÿ����Ŀ��ݵĸ���?
			public static class ViewHolder{
				ImageView pic;//�����û�ͷ��
				TextView content;//����������Ϣ
				TextView time; //��������ʱ��
				}
			public static class ViewHolder1{
				ImageView icon;
				ImageView pic;//�����û�ͷ��
				TextView time; //��������ʱ��
			}

	}


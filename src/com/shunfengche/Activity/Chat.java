package com.shunfengche.Activity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;







import com.android.app.dao.ChatDao;
import com.app.po.User;
import com.util.Chat_DataOperation;
import com.util.Clientsocket;
import com.util.Connect;
import com.util.MyApplication;
import com.util.uploadFileClass;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas.EdgeType;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.WifiConfiguration.Status;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Chat extends Activity {
	//声明数据的操作类
	Chat_DataOperation cdo = null;
	private ChatDao chatDao;
	// 声明视图组建对象
	private ListView chat_list;
	// 声明�?��全局型的List集合，用于存放List列表中的数据
	private List<Map<String,?>> data;
	//OptionsMenu菜单的�?项ID
	private static final int MENU_CHAT = Menu.FIRST;
	//ContextMenu菜单的�?项ID
	private static final int MENU_CHAT_CONTENT = Menu.FIRST+1;
	//声明�?��Button对象
	private Button send_btn;
	//声明�?��EditText对象
	private EditText msg_content;
	private LinearLayout chat_bottom;
	private ImageView chat_friend_pic;
	private TextView chat_nickname,chat_mood;
	private User user;
	TextView audio = null;
    //声明许愿提示框的常量
  	 final int List_DIALOG_MULTIPLE=0;
  	//對話�?
  	 Dialog dialog=null;
  	//錄音
  		MediaRecorder mRecorder = new MediaRecorder();		 
  		 Builder b;
  		 ImageView imgxylyview;
  	 	 long timelong;
  		 Timer recordtime;
  	Timer timer;
  	//声明照相Button
  		 Button chat_phone = null;
  	final int LIST_DIALOG = 1;
  	final int SHOW_PHOTO_DIALOG = 2;
  	//定义3个列表项的名�?
  	private String[] names = new String[]
  	{ "图库", "拍照", "取消"};
  		//定义3个列表项对应的图�?
  	private int[] imageIds = new int[]
  	{ R.drawable.andy_menu_gallery , R.drawable.andy_menu_camera
  			, R.drawable.btn_stop_record_normal
  	};
  	
  	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果

	public static final String IMAGE_UNSPECIFIED = "image/*";
	File picture;
	Bitmap photo;
	public static String picurl;
	public static String picid=System.currentTimeMillis()+"";
	public static int flag1=1;
	//ImageView imageView = null;
	
	Bundle bundle;
   int friendid,userid;
   String friendname,friendmood;
   MyListViewAdapter myAdapter;
   Clientsocket socket;
   hander h=new hander();
  
	//声明�?��SQL语句	//重写方法设置该界面的菜单
	public boolean onCreateOptionsMenu(Menu menu) {
		// 绑定资源文件中的菜单选项
		this.getMenuInflater().inflate(R.menu.chat_om, menu);
		return true;
	}
	
	
	//
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.menu_batch_operations:
		//
		Toast.makeText(Chat.this, "��������", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Chat.this,Chat_Batch_Operation.class);
		this.startActivity(intent);
		break;
		case R.id.menu_favorite:
			//
			Toast.makeText(Chat.this, "������ϵ��", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_send_contact:
			//
			Toast.makeText(Chat.this, "������Ϣ����", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_black_list:
			//
			Toast.makeText(Chat.this, "���ñ���", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_back_picture:
			//
			Toast.makeText(Chat.this, "ͨ����¼", Toast.LENGTH_SHORT).show();
			Intent intent1 = new Intent(Chat.this,Chat_Background.class);
			this.startActivity(intent1);
			break;
		}	
		return true;
	}


	//设置该界面的上下文菜�?
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		//绑定资源文件中的菜单选项
		menu.setHeaderIcon(android.R.drawable.ic_menu_more);
		menu.setHeaderTitle("相关操作");
		this.getMenuInflater().inflate(R.menu.chat_show_content, menu);
		
	}


//	//重写该方法为ContextMenu方法添加监听事件	
//	public boolean onContextItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//		switch(item.getItemId()){
//		case R.id.andy_copy:
//			Toast.makeText(Chat.this, "复制信息", Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.andy_delete:
//			Toast.makeText(Chat.this, "删除", Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.andy_batch_opeation:
//			Toast.makeText(Chat.this, "批量操作", Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.andy_favorite:
//			Toast.makeText(Chat.this, "加入收藏�?, Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.andy_repeat:
//			Toast.makeText(Chat.this, "转发信息", Toast.LENGTH_SHORT).show();
//			break;
//		}
//		return super.onContextItemSelected(item);
//	}


	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// 取消界面的标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.andy_chat);
		//初始化send_btn按钮
		send_btn = (Button) findViewById(R.id.btn_mes_send);
		send_btn.setOnClickListener(new BtnAdapterOCL());
		//初始化聊天的好友 �?信息 
		chat_friend_pic = (ImageView) findViewById(R.id.chat_friend_pic);
		chat_nickname = (TextView) findViewById(R.id.chat_nickname);
		chat_mood = (TextView) findViewById(R.id.chat_mood);
		// 调用�?��私有的方法用于初始化data数据
		String sql = "select * from chat where id >= (select count(*) from chat)-15  order by time";
		cdo = new Chat_DataOperation();
		data = cdo.getData(sql,Chat.this);
		// 初始化ListView组建
		this.chat_list = (ListView) findViewById(R.id.lst_chat);
		//取出ListView的分隔条
		chat_list.setDivider(null);
	/*	b.putInt("userid",list.get(position).getUserid());
		b.putString("userphoto",list.get(position).getPhoto());
		b.putString("username",list.get(position).getUsername());
		b.putString("usermood", list.get(position).getQianming());*/
		bundle=this.getIntent().getExtras();
		MyApplication myapp=(MyApplication) getApplication();
		user=myapp.getUser();
		userid=user.getUserid();
		
		friendid=bundle.getInt("userid");
	  
	
		friendname = bundle.getString("username");
		friendmood = bundle.getString("usermood");
		chat_nickname.setText(friendname) ;
		chat_mood.setText(friendmood);
		Bitmap head = BitmapFactory.decodeFile(bundle.getString("userphoto"));
		head = head.createScaledBitmap(head, 70, 70, true);
		chat_friend_pic.setImageBitmap(head);
		

		timetask time = new timetask();
		timer = new Timer();
		timer.schedule(time, 0, 5000);
		// 调用自定义的私有方法初始化ListView组建
		this.initListView(chat_list);
		// 创建SharedPreferences对象，用于读取数�?
				SharedPreferences preferences = getSharedPreferences("image_content", MODE_WORLD_WRITEABLE);
				// 步骤2：获取key中的数据
				int url = preferences.getInt("content"+userid, R.drawable.andy_chat_bg9);
				chat_list.setBackgroundResource(url);
		
		
		
				
				
				
		//将上下文菜单banding到ListView组建�?
		this.registerForContextMenu(this.chat_list);
		//将自定义监听器绑定到视图组建�?
		//this.chat_list.setOnItemClickListener(new SimpleAdapterOCL());
		//在文本框中显示之前未提交的内�?
		this.fetchMsgContent();
		audio = (TextView) findViewById(R.id.audio);
		audio.setOnClickListener(new  OnClickListener(){	    	
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
					//弹出许愿对画�?	  
					showDialog(List_DIALOG_MULTIPLE);
					
				}				
		});		
		//拍照
		chat_phone = (Button)findViewById(R.id.chat_phone);
		//为按钮绑定事件监听器
		chat_phone.setOnClickListener(new View.OnClickListener()
		{
	
			public void onClick(View source)
			{
				//显示对话�?
				showDialog(LIST_DIALOG);
			}
		});
	}
	class hander extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// System.out.println("handlemessage");
			super.handleMessage(msg);

			if (msg.what == 0) {
				 socket=new Clientsocket();
				 MyApplication myapp=(MyApplication) getApplication();
					System.out.println("port:"+myapp.getPort());
				
				socket.startsocket(myapp.getPort(), getApplicationContext());
					//有消息来
				
					String sql = "select * from chat where id >= (select count(*) from chat)-15  order by time";
					
					Chat.this.data = cdo.getData(sql,Chat.this);
				 // 初始化ListView组建
				   
					// 调用自定义的私有方法初始化ListView组建
					Chat.this.initListView(chat_list);
				}
					
		
			
			if (msg.what == 1) {
				
			}

			if (msg.what == 2) {
				timer.cancel();
			}
		}

	}

	class timetask extends TimerTask {
		@Override
		public void run() {

			
			Message mg = new Message();

			
				mg.what = 0;
		
			h.sendMessage(mg);
	
			// TODO Auto-generated method stub
		
			  
		
		}
	}
	private void fetchMsgContent() {
			
		// 用于显示文本框中之前未提交的内容
		// 创建SharedPreferences对象，用于读取数�?
		SharedPreferences preferences = getSharedPreferences("msg_content", MODE_WORLD_WRITEABLE);
		// 步骤2：获取key中的数据
		String msg = preferences.getString("content", "");
		//在EditText中显示内�?//

		msg_content = (EditText) findViewById(R.id.andy_msg_content);
		msg_content.setText(msg);	
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//利用save方法保存文本框中输入的数�?
		this.saveEdit();
	}


	private void saveEdit() {
		// TODO Auto-generated method stub
		msg_content = (EditText) findViewById(R.id.andy_msg_content);
		// 步骤1：获取密码�?
		String msg = msg_content.getText().toString().trim();
		// 步骤2：创建SharedPreferences.edit对象，用于完成数据的存储
		SharedPreferences.Editor editor = getSharedPreferences("msg_content",
				MODE_WORLD_WRITEABLE).edit();
		// 步骤2：利用editor对象的putString方法添加新数�?
		editor.putString("content", msg);
		// 步骤3：提�?
		editor.commit();
	}


	// 对ListView组建的封�?
	private void initListView(ListView chat_list) {
		//实例化自定义适配�?
		 myAdapter = new MyListViewAdapter(this,this.data);
		//将设定好的�?配器绑定到ListView组建�?
		chat_list.setAdapter(myAdapter);
		//ListView刷新后自动滚到最底部
		chat_list.setSelection(chat_list.getAdapter().getCount()-1); 
	}



	//创建界面的监听器
	/*class SimpleAdapterOCL implements AdapterView.OnItemClickListener{

		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long id) {
			//获取到用户�?中的选项对象
			Map<String, Object> item = (Map<String, Object>) data.get(position);
			//测试弹出相关信息
			Toast.makeText(Chat.this, "您的ID为�?"+item.get("content_id")+"�?, Toast.LENGTH_SHORT).show();
		}
		
	}*/
	class BtnAdapterOCL implements OnClickListener{

		public void onClick(View v) {
		
			switch (v.getId()){
			case R.id.btn_mes_send:	
				msg_content = (EditText) findViewById(R.id.andy_msg_content);
				String msg = msg_content.getText().toString().trim();
				// 实例化ChatDao对象，完成数据库的操�?
				chatDao = new ChatDao(Chat.this);
				int fromid = userid;
				int toid = friendid;
				String content = msg;
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				int flag = 1;
				int freeze = 0;
				String strSQL = "insert into chat values(null,'" + fromid + "','"
						+ toid + "','" + content + "','"
								+ time + "','"
										+flag+ "','" + freeze + "')";
				
				boolean index = chatDao.execOther(strSQL);
				//�?��务器写数�?
				Connect conn=new Connect();
				if(conn.doS( "insert into chat values(null,'" + fromid + "','"
						+ toid + "','" + content + "','"
						+ time + "','"
								+flag+ "','" + freeze + "',0)", 1, "chat", null, null).trim().equals("0")){
					Toast.makeText(getApplicationContext(), "���ӳɹ�", Toast.LENGTH_SHORT).show();
				}
				else Toast.makeText(getApplicationContext(), "����ʧ��", Toast.LENGTH_SHORT).show();
				
				String message = index ? "����ɹ�" : "����ʧ��";
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG).show();
/*				chat_list.addView(getcorvet(content,time,null),chat_list.getCount()-1);
*/			
				String sql = "select * from chat where id >= (select count(*) from chat)-15  order by time";
				cdo = new Chat_DataOperation();
				Chat.this.data = cdo.getData(sql,Chat.this);
			 // 初始化ListView组建
				chat_list = (ListView) findViewById(R.id.lst_chat);
				// 调用自定义的私有方法初始化ListView组建
				Chat.this.initListView(chat_list);
				msg_content.setText("");
				Toast.makeText(getApplicationContext(), time,
						Toast.LENGTH_LONG).show();		
				break;
			case R.id.chat_bg_top_text:
				/*List<Map<String, ?>> content1 = Chat.this.data;*/
				/*int fromid1 = Integer.parseInt(content1.get(1).get("fromid").toString());
				int toid1 = Integer.parseInt(content1.get(1).get("toid").toString());*/
				Intent intent = new Intent();
				intent.setClass(Chat.this, ShowAll.class);
				Chat.this.startActivity(intent);
				/*i.putExtra("fromid",fromid);
				i.putExtra("toid", toid);*/
				/*startActivity(i);*/
				Toast.makeText(getApplicationContext(), "fromid1",
						Toast.LENGTH_LONG).show();	
				break;
			
			}
		}
		
	}
	//创建ListVirew的自定义适配�?
	class MyListViewAdapter extends BaseAdapter{	
		//声明�?��绑定的数据源
		private List<Map<String, ?>> content;
		//声明�?��的Activity
		private Context context;
		//构�?方法
		public MyListViewAdapter(Context context,List<Map<String, ?>> content){
			this.content = content;
			this.context = context;
		}
		public void unregisterDataSetObserver(DataSetObserver arg0) {
		}
		public void registerDataSetObserver(DataSetObserver arg0) {
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
			// 返回对话框编�?
			return position;
		}
		public int getItemViewType(int arg0) {
			return arg0;
		}
		public boolean isEnabled(int position) {
			return false;
		}
		public boolean areAllItemsEnabled() {
			return false;
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
				int toid;
				int flag;
				for(int i =0 ; i < content.size() ; i++){
					fromid = Integer.parseInt(content.get(position).get("fromid").toString());
					toid = Integer.parseInt(content.get(position).get("toid").toString());
					flag = Integer.parseInt(content.get(position).get("flag").toString());
					if(position ==0){
						statusView = LayoutInflater.from(context).inflate(R.layout.chat_bg_top, null);
						TextView text = (TextView) statusView.findViewById(R.id.chat_bg_top_text);
						text.setOnClickListener(new OnClickListener() {
							
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(Chat.this,ShowAll.class);
								startActivity(intent);
							}
						});
					}
					else if(fromid == userid && toid == friendid){
						if(flag==1){
						statusView = LayoutInflater.from(context).inflate(R.layout.andy_chat_content, null);
						holder.pic = (ImageView) statusView.findViewById(R.id.chat_pic);
						holder.content = (TextView)statusView.findViewById(R.id.chat_content);
						holder.time = (TextView)statusView.findViewById(R.id.chat_time);
						//设定头像
						Bitmap head = BitmapFactory.decodeFile(user.getPhoto());
						head = head.createScaledBitmap(head, 70, 70, true);
						holder.pic.setImageBitmap(head);
						//设定聊天内容
						holder.content.setText(content.get(position).get("chat_content").toString());
						//设定聊天时间"chat_time"
						holder.time.setText(content.get(position).get("chat_time").toString());						
						}
						else if(flag==2){				
							statusView = LayoutInflater.from(context).inflate(R.layout.andy_chat_audio_send, null);
							//获取录音路径
							//String recordurl = (String) content.get(position).get("chat_content");		
							audioholder.pic = (ImageView) statusView.findViewById(R.id.chat_audio_send_pic);
							audioholder.icon = (ImageView)statusView.findViewById(R.id.chat_audio_icon_send);
							//为语音图片添加事件监听器
							audioholder.icon.setOnClickListener(new ImageOnClickListener(content,position));
							
							audioholder.time = (TextView)statusView.findViewById(R.id.chat_audio_time_send);
							Bitmap head = BitmapFactory.decodeFile(user.getPhoto());
							head = head.createScaledBitmap(head, 70, 70, true);
							audioholder.pic.setImageBitmap(head);
							//设定聊天内容
							audioholder.icon.setImageResource(R.drawable.andy_mms_play_btn);
							//设定聊天时间"chat_time"
							audioholder.time.setText(content.get(position).get("chat_time").toString());
						}
						else if(flag==3){
							statusView = LayoutInflater.from(context).inflate(R.layout.andy_chat_pic_send, null);
							picholder.pic = (ImageView) statusView.findViewById(R.id.chat_pic_send_pic);
							picholder.icon = (ImageView)statusView.findViewById(R.id.chat_pic_send);
							//为语音图片添加事件监听器
/*							audioholder.audio_icon.setOnClickListener(new ImageOnClickListener(content,position));
*/							
							picholder.time = (TextView)statusView.findViewById(R.id.chat_pic_time_send);
							//设定头像
							Bitmap head = BitmapFactory.decodeFile(user.getPhoto());
							head = head.createScaledBitmap(head, 70, 70, true);
							picholder.pic.setImageBitmap(head);
							String url = content.get(position).get("chat_content").toString();
							picholder.icon.setImageBitmap(BitmapFactory.decodeFile(url));
							picholder.icon.setOnClickListener(new PhotoOnClickListener(content,position));
							picholder.time.setText(content.get(position).get("chat_time").toString());
						}
					}
					else if(fromid==friendid && toid == userid){
						if(flag==1){
						statusView = LayoutInflater.from(context).inflate(R.layout.andy_chat_content_receive, null);
						holder.pic = (ImageView) statusView.findViewById(R.id.chat_pic_receive);
						holder.content = (TextView)statusView.findViewById(R.id.chat_content_receive);
						holder.time = (TextView)statusView.findViewById(R.id.chat_time_receive);
						//设定头像
						Bitmap head = BitmapFactory.decodeFile(bundle.getString("userphoto"));
						head = head.createScaledBitmap(head, 70, 70, true);
						holder.pic.setImageBitmap(head);
						//设定聊天内容
						holder.content.setText(content.get(position).get("chat_content").toString());
						//设定聊天时间"chat_time"
						holder.time.setText(content.get(position).get("chat_time").toString());		
						}
						else if(flag==2){					
							statusView = LayoutInflater.from(context).inflate(R.layout.andy_chat_audio_receive, null);
							audioholder.pic = (ImageView) statusView.findViewById(R.id.chat_audio_pic_receive);
							audioholder.icon= (ImageView)statusView.findViewById(R.id.chat_icon_audio_receive);
							audioholder.time = (TextView)statusView.findViewById(R.id.chat_audio_time_receive);
							//设定头像
							Bitmap head = BitmapFactory.decodeFile(bundle.getString("userphoto"));
							head = head.createScaledBitmap(head, 70, 70, true);
							audioholder.pic.setImageBitmap(head);
							//设定聊天内容
							audioholder.icon.setImageResource(R.drawable.andy_mms_play_btn);							
							//设定聊天时间"chat_time"
							audioholder.time.setText(content.get(position).get("chat_time").toString());
							//为语音图片添加事件监听器
							audioholder.icon.setOnClickListener(new ImageOnClickListener(content,position));			
						}
						else if(flag==3){
							statusView = LayoutInflater.from(context).inflate(R.layout.andy_chat_pic_receive, null);
							picholder.pic = (ImageView) statusView.findViewById(R.id.chat_photo_pic_receive);
							picholder.icon = (ImageView)statusView.findViewById(R.id.chat_icon_photo_receive);
							//为语音图片添加事件监听器
/*							audioholder.audio_icon.setOnClickListener(new ImageOnClickListener(content,position));
*/							picholder.icon.setOnClickListener(new PhotoOnClickListener(content,position));
							picholder.time = (TextView)statusView.findViewById(R.id.chat_photo_time_receive);
							//设定头像
							Bitmap head = BitmapFactory.decodeFile(bundle.getString("userphoto"));
							head = head.createScaledBitmap(head, 70, 70, true);
							picholder.pic.setImageBitmap(head);
							String url = content.get(position).get("chat_content").toString();
							picholder.icon.setImageBitmap(BitmapFactory.decodeFile(url));		
							picholder.time.setText(content.get(position).get("chat_time").toString());
						}
						}
				}
			return statusView;
		}
		
	}
    class ImageOnClickListener implements OnClickListener {
    	 private int position;
    	//声明�?��绑定的数据源
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
    //为图片添加事件监听器
    class PhotoOnClickListener implements OnClickListener {
   	 private int position;
   	//声明�?��绑定的数据源
		private List<Map<String, ?>> content;
		PhotoOnClickListener(List<Map<String, ?>> content,int pos) {
            position = pos;
            this.content=content;
        }
        
         public void onClick(View v) {
        	 String picurl = (String) content.get(position).get("chat_content"); 	 
	    		ImageView imgview =new ImageView(Chat.this);
	    		Dialog dialog=new Dialog(Chat.this);
	    		//单击以外区域消失
	    		dialog.setCanceledOnTouchOutside(true);  		
	           /* imgview.setImageBitmap(BitmapFactory.decodeFile(picurl));	*/
	            imgview.setImageBitmap(BitmapFactory.decodeFile(picurl)
						.createScaledBitmap(BitmapFactory.decodeFile(picurl), 400,
								400, false));
	            dialog.setContentView(imgview);
	            
	            dialog.show();
        	 Toast.makeText(Chat.this
						, "到了监听器了"
						, 5000)
						.show();
	            }
     }
	//定义�?��静�?类用于获取和处理文字列表中每个条目数据的更新
	public static class ViewHolder{
		ImageView pic;//设置用户头像
		TextView content;//设置聊天信息
		TextView time; //设置聊天时间
		}
	public static class ViewHolder1{
		ImageView icon;
		ImageView pic;//设置用户头像
		TextView time; //设置聊天时间
	}
	
	
	
	boolean mulFlag=false;//是否录音的标�?   
	  //创建按钮的事件监听器
	 /*   class imgbtnls implements OnClickListener{	    	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){				
				case R.id.audio:
					//弹出许愿对画�?	  
					showDialog(List_DIALOG_MULTIPLE);
					break;
				}				
			}		
	    }*/
	  //创建录音对话框的方法
	    String cordurl ;
	    public Dialog onCreateDialog(int id){
	    	switch(id){
	    	case List_DIALOG_MULTIPLE:
	    		//创建Builder对象
	    		dialog=new Dialog(Chat.this,R.style.dialog);
	    		//单击以外区域消失
	    		dialog.setCanceledOnTouchOutside(true);
	             imgxylyview=new ImageView(this); 
	            imgxylyview.setBackgroundResource(R.drawable.luyinbtn);
	            //录音图片单击监听�?
	    	    imgxylyview.setOnClickListener(new OnClickListener(){
	    	    	public void onClick(View arg0) {
						dialog.setCanceledOnTouchOutside(false);
						File soundfile = null;						
						String cordid=System.currentTimeMillis()+"";					
						if(!mulFlag){
						    mulFlag=true;
						    doudong();
						    mRecorder = new MediaRecorder();
						    imgxylyview.setBackgroundResource(R.drawable.luyin);
							if (!Environment.getExternalStorageState().equals(
									android.os.Environment.MEDIA_MOUNTED))
								{
									Toast.makeText(Chat.this
										, "SD卡不存在，请插入SD卡！"
										, 5000)
										.show();
									return;
								}					
									// 创建保存录音的音频文�?						
										try {
											File files = new File("sdcard/chatVoice");
											 files.mkdirs();
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											Toast.makeText(getApplicationContext(),"files已經存在",Toast.LENGTH_SHORT).show();
										}
											soundfile = new File(Environment.getExternalStorageDirectory().toString()+"/chatVoice/"+cordid+".amr");
											cordurl=soundfile.getAbsolutePath();
									//�?��录音
									// 设置录音的声音来�?
									mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
									// 设置录制的声音的输出格式（必须在设置声音编码格式之前设置�?
									// 设置录制的声音的输出格式（必须在设置声音编码格式之前设置�?
									mRecorder.setOutputFormat(MediaRecorder
										.OutputFormat.DEFAULT);
									// 设置声音编码的格�?
									mRecorder.setAudioEncoder(MediaRecorder
										.AudioEncoder.DEFAULT);
									mRecorder.setOutputFile(soundfile.getAbsolutePath());
									String user="liushen";
										try{mRecorder.prepare();}										
										catch (Exception e)
										{											
											Toast.makeText(getApplicationContext(),"��ʼ¼��",Toast.LENGTH_SHORT).show();
										}
										// �?��录音
										mRecorder.start();
										timelong=0;
									    recordtime=new Timer();
									    recordtime.schedule(new TimerTask() {
										public void run() {
											// TODO Auto-generated method stub
										  timelong=timelong+200;
										}
									}, 0,200);
						}
						else{
						    mulFlag=false;
						 // 停止录音
						
						mRecorder.stop();
						 recordtime.cancel();
							// 释放资源
						    mRecorder.release();
							mRecorder = null;
						
					        Toast.makeText(getApplicationContext(),"正在结束录音",Toast.LENGTH_SHORT).show();
					    	
					     // 实例化ChatDao对象，完成数据库的操�?
							chatDao = new ChatDao(Chat.this);
							int fromid = userid;
							int toid = friendid;
							String content = cordurl;
							String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(new Date());
							int flag = 2;
							int freeze = 0;
							String strSQL = "insert into chat values(null,'" + fromid + "','"
									+ toid + "','" + content + "','"
											+ time + "','"
													+flag+ "','" + freeze + "')";
							new uploadFileClass().uploadFile("", content);
							content="d://upload"+content.substring(content.lastIndexOf("/"));
							
							
							new Connect().doS("insert into chat values(null,'" + fromid + "','"
									+ toid + "','" + content + "','"
									+ time + "','"
											+flag+ "','" + freeze + "',0)", 1, "chat", null, null);
							boolean index = chatDao.execOther(strSQL);
							String message = index ? "����ɹ�" : "����ʧ��";
							Toast.makeText(getApplicationContext(), message,
									Toast.LENGTH_LONG).show();
							String sql = "select * from chat where id >= (select count(*) from chat)-15  order by time";
							cdo = new Chat_DataOperation();
							Chat.this.data = cdo.getData(sql,Chat.this);
						 // 初始化ListView组建
							chat_list = (ListView) findViewById(R.id.lst_chat);
							// 调用自定义的私有方法初始化ListView组建
							Chat.this.initListView(chat_list);
							

					      dismissDialog(List_DIALOG_MULTIPLE);				      
					      imgxylyview.setBackgroundResource(R.drawable.luyinbtn);
					      dialog.setCanceledOnTouchOutside(true);
					     } 
					  
						
					}
	    			
	    		});
	    	  
	    	    dialog.setContentView(imgxylyview);
	    		break;
	    	case LIST_DIALOG:
	    		Builder b = new AlertDialog.Builder(this);
				// 设置对话框的图标
				b.setIcon(R.drawable.tools);
				// 设置对话框的标题
				b.setTitle("     选择您的操作");
				//创建�?��List对象，List对象的元素是Map
				List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < names.length; i++)
				{
					Map<String, Object> listItem = new HashMap<String, Object>();
					listItem.put("header", imageIds[i]);
					listItem.put("personName", names[i]);
					listItems.add(listItem);
				}
				//创建�?��SimpleAdapter
				SimpleAdapter simpleAdapter = new SimpleAdapter(this
					, listItems 
					, R.layout.row
					, new String[]{ "personName", "header" }
					, new int[]{R.id.name , R.id.header});
				
				// 为对话框设置多个列表
				b.setAdapter(simpleAdapter				
					//为列表项的单击事件设置监听器
					, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,
							int which)
						{
							
							try {
								File files = new File("sdcard/chatPic");
								 files.mkdirs();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								Toast.makeText(getApplicationContext(),"files�Ѿ�����",Toast.LENGTH_SHORT).show();
							}
							if(which==0){
								Intent intent = new Intent(Intent.ACTION_PICK, null);
								intent.setDataAndType(
										MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										IMAGE_UNSPECIFIED);
								
                                 
								intent.putExtras(bundle);
								startActivityForResult(intent, PHOTOZOOM);
							}else if(which==1){
								Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
								picid=System.currentTimeMillis()+"";
								intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
										Environment.getExternalStorageDirectory()+"/chatPic", picid+".jpg")));
								startActivityForResult(intent, PHOTOHRAPH);
							}else if(which==2){

							}
							// which代表哪个列表项被单击�?
							//show.setText("您最擅长的种族为�? + names[which]);

						}
					});
				// 创建对话�?
				return b.create();
	    	
	    	}
			return dialog;
	    	
	    }
	  //抖动
	    public void doudong(){
	    	 Vibrator mVibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);//获取振动�?
	    	 mVibrator.vibrate(100);
	    }
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	File picfile = null;
	    	flag1=1;
	    	if (resultCode == NONE)
				return;
			// 拍照
			if (requestCode == PHOTOHRAPH) {
				
				picfile = new File(Environment.getExternalStorageDirectory().toString()+"/chatPic/"+picid+".jpg");
				picurl=picfile.getAbsolutePath();				
				flag1=0;
				startPhotoZoom(Uri.fromFile(picfile));
				
			}

			if (data == null){
				Toast.makeText(getApplicationContext(),"data == null",
						Toast.LENGTH_LONG).show();
				return;
			}
			// 读取相册缩放图片
			if (requestCode == PHOTOZOOM) {
				startPhotoZoom(data.getData());
				Toast.makeText(getApplicationContext(),"读取相册缩放图片",
						Toast.LENGTH_LONG).show();
				
			}
			// 处理结果
			if (requestCode == PHOTORESOULT) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					photo = extras.getParcelable("data");					
					
					if(flag1==1){
						picid=System.currentTimeMillis()+"";
						picfile = new File(Environment.getExternalStorageDirectory().toString()
								+"/chatPic/"+picid+".jpg");
						try {
							FileOutputStream stream = new FileOutputStream(picfile);
							photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
							
							 // 实例化ChatDao对象，完成数据库的操�?
							
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						/**/
					}
					chatDao = new ChatDao(Chat.this);
					int fromid = userid;
					int toid = friendid;
					String content = picfile.getAbsolutePath();
					String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date());
					int flag = 3;
					int freeze = 0;
					String strSQL = "insert into chat values(null,'" + fromid + "','"
							+ toid + "','" + content + "','"
									+ time + "','"
											+flag+ "','" + freeze + "')";
					new uploadFileClass().uploadFile("", content);
					content="d://upload"+content.substring(content.lastIndexOf("/"));
					
					new Connect().doS("insert into chat values(null,'" + fromid + "','"
							+ toid + "','" + content + "','"
							+ time + "','"
									+flag+ "','" + freeze + "',0)", 1, "chat", null, null);
					boolean index = chatDao.execOther(strSQL);
					String message = index ? "ͼƬ����ɹ�" : "ͼƬ����ʧ��";
					Toast.makeText(getApplicationContext(), message,
							Toast.LENGTH_LONG).show();
					String sql = "select * from chat where id >= (select count(*) from chat)-15  order by time";
					cdo = new Chat_DataOperation();
					Chat.this.data = cdo.getData(sql,Chat.this);
				 // 初始化ListView组建
					chat_list = (ListView) findViewById(R.id.lst_chat);
					// 调用自定义的私有方法初始化ListView组建
					Chat.this.initListView(chat_list);
				}
			}

			super.onActivityResult(requestCode, resultCode, data);
		}

		public void startPhotoZoom(Uri uri) {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			Toast.makeText(getApplicationContext(),"startPhotoZoom",
					Toast.LENGTH_LONG).show();
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽�?
			intent.putExtra("outputX", 384);
			intent.putExtra("outputY", 256);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, PHOTORESOULT);

		}
	}

package com.shunfengche.Activity;

import java.util.List;

import com.app.po.User;
import com.util.Connect;
import com.util.MyApplication;
import com.util.uploadFileClass;



import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {
	// 澹版槑鍙橀噺
	private Button btnLogin;
	private TextView btnReg01;
	private EditText usneditText, paweditText;
	String username, password;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO 接收消息并且去更新UI线程上的控件内容
			switch(msg.what ) {
			case 0:
				Toast.makeText(getApplicationContext(), "身份验证失败！", Toast.LENGTH_LONG).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_LONG).show();
				Intent in = new Intent(LoginActivity.this,
						MainlistActivity.class);
				startActivity(in);
               
				
				//finish();
				break;
			
		case 3:
			//Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_LONG).show();
			break;
			}
			super.handleMessage(msg);
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		//new SqliteHelper(LoginActivity.this, "user");
		usneditText = (EditText) findViewById(R.id.usneditText);
		paweditText = (EditText) findViewById(R.id.paweditText);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnReg01 = (TextView) findViewById(R.id.btnReg01);
		btnLogin.setOnClickListener(new View.OnClickListener() {
        
			@Override
			public void onClick(View v) {

				String username = usneditText.getText().toString().trim();
				String password = paweditText.getText().toString().trim();

				if (username.equals("") || password.equals("")) {
					Toast.makeText(getApplicationContext(), "用户名，密码不能为空",
							Toast.LENGTH_SHORT).show();
				} else {
					final String str="User[username]="+username+"&User[password]="+password;
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							MyApplication myapp=(MyApplication) getApplication();
							boolean isuser=Connect.dologin(str,myapp);
							if(isuser)
								handler.sendEmptyMessage(1);
							else handler.sendEmptyMessage(0);
						}
					}).start();
					
					/*String msg = con.doS(null, 3, null, username, password).trim();
					if (!msg.equals("0")) {
						Connect conn = new Connect();
						String sql = "select *from usertb where username = '"+username+"'";
						List<User> list =(List<User>) conn.getlist(0, "usertb", sql);
						User user=list.get(0);
						MyApplication myapp=(MyApplication) getApplication();
						
						uploadFileClass upf=new uploadFileClass();
						String result=upf.downloadfile(user.getPhoto());
						if(result.equals("下载成功")){
							user.setPhoto(upf.getdownloadBitmappath(user.getPhoto()));
							Toast.makeText(getApplicationContext(), "头像下载成功", Toast.LENGTH_SHORT).show();
						}
						else  Toast.makeText(getApplicationContext(), "头像下载失败", Toast.LENGTH_SHORT).show();
						myapp.setUser(user);
						int port=Integer.parseInt(msg.substring(1));
						myapp.setPort(port);
						Intent in = new Intent(LoginActivity.this,
								MainlistActivity.class);
						startActivity(in);
                       
						overridePendingTransition(R.anim.guide_inanim,R.anim.guide_out);
						finish();*/
						/*UserClass.setUsername(user.getUsername());
						UserClass.setAddress(user.getAddress());
						UserClass.setPassword(password=user.getPassword());
						String name=user.getPhoto();
						new uploadFileClass().downloadfile(name);
						name=name.substring(name.lastIndexOf("/"));
						UserClass.setPhoto(Environment.getExternalStorageDirectory()+ "/Download"+name);
						
						UserClass.setQianming(user.getQianming());
					
						UserClass.setUserid(user.getUserid());
						UserClass.setSex(user.getSex());
						System.out.println("msg:"+msg);
						System.out.println("msg:"+msg);
						//
					
						UserClass.setPort(port);
						
						Intent in = new Intent(LoginActivity.this,
								TestRolateAnimActivity.class);
						startActivity(in);

						overridePendingTransition(R.anim.to_right,
								R.anim.from_left);*/
					
				}

				/*
				 * //锟叫讹拷锟矫伙拷锟角凤拷锟斤拷锟斤拷锟斤拷确 String username =
				 * usneditText.getText().toString().trim(); String password =
				 * paweditText.getText().toString().trim();
				 * if(!"".equals(username)&&!"".equals(password)){
				 * 
				 * SQLiteDatabase db = dbHelper.getReadableDatabase(); Cursor cu
				 * = db.query("user",new
				 * String[]{"id"},"username=? and password=?",new
				 * String[]{username,password},null,null,null);
				 * if(cu.moveToNext()){ db.close();
				 * Toast.makeText(getApplicationContext(), "login success",
				 * Toast.LENGTH_LONG).show(); Intent i = new
				 * Intent(LoginActivity.this,TestRolateAnimActivity.class);
				 * i.putExtra("username",username);
				 * 
				 * startActivity(i); }else{
				 * Toast.makeText(getApplicationContext(), "鐢ㄦ埛鍚嶅瘑鐮侀敊璇綖",
				 * Toast.LENGTH_LONG).show(); }
				 * 
				 * }else{ Toast.makeText(getApplicationContext(), "鐧诲綍澶辫触锝?,
				 * Toast.LENGTH_LONG).show(); } }
				 */}
		});
		btnReg01.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this,
						GuestRegisterActivity.class);
				startActivity(i);

				overridePendingTransition(R.anim.guide_inanim,R.anim.guide_out);
			}
		});
	}
}
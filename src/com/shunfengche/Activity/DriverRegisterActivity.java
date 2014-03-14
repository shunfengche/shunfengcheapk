package com.shunfengche.Activity;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.util.Connect;
import com.util.MyApplication;
import com.util.uploadFileClass;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DriverRegisterActivity extends Activity {
    /** Called when the activity is first created. */
    
	Bitmap bitmap;
	ImageView imageview1;
	EditText edit_licensenum,  edit_platenum;
	String imgpath;
int flag=0;
MyApplication myapp;
private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
		// TODO 接收消息并且去更新UI线程上的控件内容
		switch(msg.what ) {
		case 0:
			Toast.makeText(getApplicationContext(), "注册失败！", 1000).show();
			break;
		case 1:
			Intent in=new Intent(DriverRegisterActivity.this,MainlistActivity.class);
			startActivity(in);
			finish();
			break;
		case 2:
			Toast.makeText(getApplicationContext(), "图片上传成功！", 1000).show();
			break;
	case 3:
		Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_LONG).show();
		break;
		}
		super.handleMessage(msg);
	}
};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        setContentView(R.layout.driver_register);
        
        //驾照号码
        edit_licensenum = (EditText) findViewById(R.id.edit_licensenum);
       //车牌号码
        edit_platenum  = (EditText) findViewById(R.id.edit_platenum);
        
        @SuppressWarnings("unused")
		Button btnlocal=(Button) findViewById(R.id.submit_picture);
        Button btncamera=(Button) findViewById(R.id.submit_camera);
        imageview1 = (ImageView) findViewById(R.id.image_in);
       myapp=(MyApplication) getApplication();
        /*//设置显示图片
        imageview1.setImageResource(R.drawable.zanwu_zeh);*/
       Button subbtn = (Button) findViewById(R.id.submit_reg);
       int userid=myapp.getUser().getUserid();
		
		File fi=new File(Environment.getExternalStorageDirectory()+"/shunfengche");
		if(!fi.exists()){
			fi.mkdirs();
		}
		imgpath=fi.getPath()+"/car_"+userid+".jpg";
       //页面跳转
       subbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		/*	  Intent intent = new Intent(RegisterActivity.this,RegisterActivity1.class);
			  startActivity(intent);*/
				
				final String licensenum=edit_licensenum.getText().toString().trim();
				final String platenum=edit_platenum.getText().toString().trim();
				if(flag==1&&(!licensenum.equals(""))&&(!platenum.equals(""))){
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(Connect.isNetworkReachable(getApplicationContext())){
							int userid=myapp.getUser().getUserid();
							//int userid=11;
							 String uppath="d://upload/"+imgpath.substring(imgpath.lastIndexOf("/"));
							String str="Driver[licenesenum]="+licensenum
									+"&Driver[platenum]="+platenum
									+"&Driver[userid]="+userid+"&Driver[carphotostream]="+uppath;
							boolean isregister=Connect.dodriverregister(str);
							if(isregister){ 
								handler.sendEmptyMessage(1);
								String result=new   uploadFileClass().uploadFile(null,imgpath);
								if(result.equals("图片上传成功！"))
									handler.sendEmptyMessage(2);
							   
							}
							else{
								handler.sendEmptyMessage(0);
							}
							}
						}
					}).start();
				
			
			
				}else  Toast.makeText(getApplicationContext(), "请填写完信息", Toast.LENGTH_SHORT).show();
			}
		});
        

       //拍照按钮
         btncamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent();
				//开启picture设定画面
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 1);*/
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				String state  = Environment.getExternalStorageState();
				if(state.equals(Environment.MEDIA_MOUNTED)){
					//存储卡可用
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgpath)));
					
					startActivityForResult(intent, PHOTOHRAPH);
				   }else{
					   Toast.makeText(getApplicationContext(),"存储卡不可用",Toast.LENGTH_LONG).show();
				   }


			}
		});
        //获取本地图片
        btnlocal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/*String state  = Environment.getExternalStorageState();
			if(state.equals(Environment.MEDIA_MOUNTED)){
				//存储卡可用
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			    startActivityForResult(intent,2);
			   }else{
				   Toast.makeText(getApplicationContext(),"存储卡不可用",Toast.LENGTH_LONG).show();
			   }*/
			String state  = Environment.getExternalStorageState();
			if(state.equals(Environment.MEDIA_MOUNTED)){
				//存储卡可用
			Intent intent1 = new Intent(Intent.ACTION_PICK,
					null);
			intent1.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					IMAGE_UNSPECIFIED);
			startActivityForResult(intent1, PHOTOZOOM);
			 }else{
				   Toast.makeText(getApplicationContext(),"存储卡不可用",Toast.LENGTH_LONG).show();
			   }

			}
		});
    }
	  
	/*   
      //实现获取本地图片
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		if(data != null && requestCode == 2){
		//拍照上传
		Bundle extras = data.getExtras();
		if(extras != null){
			bitmap = (Bitmap) extras.get("data");
			File file = new File(Environment.getExternalStorageDirectory()+"/kng.jpg");
			BufferedOutputStream fos=null;
			try {
				fos = new BufferedOutputStream(new FileOutputStream(file));
				 bitmap.compress(Bitmap.CompressFormat.JPEG,80 , fos);
			     
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imgpath=file.getAbsolutePath();
			flag=1;
			imageview1.setImageBitmap(bitmap);
	      }else{
	    	  Toast.makeText(getApplicationContext(), "未找到图片",Toast.LENGTH_LONG).show();
	      }
		}
		if(resultCode == RESULT_OK){
        Uri uri = data.getData();
        Log.e("uri",uri.toString());
        
        try{
        	String[] pojo = {MediaStore.Images.Media.DATA};
        	Cursor cursor = managedQuery(uri,pojo,null,null,null);
        	if(cursor != null){
        		
        		ContentResolver cr = this.getContentResolver();
        		int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        	cursor.moveToFirst();
	        	String path =cursor.getString(colunm_index);
        	imgpath=path;
        	if(path.endsWith("jpg")||path.endsWith("png")){
        		bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
        	    imageview1.setImageBitmap(bitmap);  
        	   
        	  }
        	}
            
        }catch(FileNotFoundException e){
        	Log.e("Exception", e.getMessage(), e);
          }
		}
		super.onActivityResult(requestCode, resultCode, data);
	}*/
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;
	public static final int PHOTOZOOM = 2;
	public static final int PHOTORESOULT = 3;
	ByteArrayOutputStream stream ;
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			
			startPhotoZoom( Uri.fromFile(new File(imgpath)));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				bitmap = extras.getParcelable("data");
				
				 stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);// (0 - 100)压缩文件
			    try {
					FileOutputStream fos=new FileOutputStream(new File(imgpath));
					fos.write(stream.toByteArray());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    flag=1;
				imageview1.setImageBitmap(bitmap);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);

	}


}
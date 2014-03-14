package com.shunfengche.Activity;




import com.android.app.sqlite.SqliteHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class GuideActivity extends Activity {
	  private ViewFlipper mViewFlipper;  
	  @Override  
	    public void onCreate(Bundle savedInstanceState) {   
	        super.onCreate(savedInstanceState);  
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.guiderxml);   
	           
	       
	        mViewFlipper = (ViewFlipper) findViewById(R.id.flipper);  
	        mViewFlipper.setInAnimation(getApplicationContext(), R.anim.guide_inanim);   
      mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.guide_out);   
 
     //  mViewFlipper.setFlipInterval(3000);   
            
        
			// mViewFlipper.showNext();   
			
             //调用下面的函数将会循环显示mViewFlipper内的所有View。   
     // mViewFlipper.startFlipping();   
   
	  }
	//伐屏参数key是否有move事件，
		private int key,position;
		private float firstx=0,firsty=0,secx=0,secy=0;
		
	  public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			 //获取分辨率
	        DisplayMetrics dm = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(dm);        
	        
	        int nowWidth = dm.widthPixels; //当前分辨率 宽度
	        int nowHeigth = dm.heightPixels; //当前分辨率高度
	        
			int action=event.getAction();
			System.out.println("====onTouchEvent=====");
			
			if(action==MotionEvent.ACTION_DOWN){
				System.out.println("====ACTION_DOWN=====");
				firstx=event.getX();
				firsty=event.getY();
				key=0;
				//Toast.makeText(getApplicationContext(),"横坐标:"+event.getX()+"   纵坐标:"+event.getY(), Toast.LENGTH_SHORT).show();
			}
			if(action==MotionEvent.ACTION_MOVE){
			System.out.println("====ACTION_MOVE=====");
//				Toast.makeText(getApplicationContext(),"横坐标:"+event.getX()+"   纵坐标:"+event.getY(), Toast.LENGTH_SHORT).show();
			key=1;
			}
		   if(action==MotionEvent.ACTION_UP){
				System.out.println("====ACTION_UP=====");
				//有滑动事件
				if(key==1){
					
					secx=event.getX();
					secy=event.getY();
					//往右划
					if(Math.abs(secy-firsty)<=130&&secx-firstx>140){
						Toast.makeText(getApplicationContext(),"往右划屏", Toast.LENGTH_SHORT).show();
						
					}
					//往左划屏
					if(Math.abs(secy-firsty)<=130&&firstx-secx>140){
						 
						  if(position<2)
							  {
							  mViewFlipper.showNext(); 
								position++;
							  }
							else {
								Intent in=new Intent(GuideActivity.this, MainlistActivity.class);
								
								startActivity(in);
								  overridePendingTransition(R.anim.guide_inanim,R.anim.guide_out);
								GuideActivity.this.finish();
							}
						//Toast.makeText(getApplicationContext(),"往左划屏", Toast.LENGTH_SHORT).show();
					
					
					}
					//往上划屏
					if(Math.abs(secx-firstx)<=130&&firsty-secy>140){
						
						Toast.makeText(getApplicationContext(),"往上划屏", Toast.LENGTH_SHORT).show();
						
					}
					//往下划屏
					if(Math.abs(secx-firstx)<=130&&secy-firsty>140){
						
						Toast.makeText(getApplicationContext(),"往下划屏", Toast.LENGTH_SHORT).show();
					   
					}
				}
				//Toast.makeText(getApplicationContext(),"横坐标:"+event.getX()+"   纵坐标:"+event.getY(), Toast.LENGTH_SHORT).show();
			}	
			return super.onTouchEvent(event);
		}
	// 方法：放在手启动界面，调用SQLiteHelper中的方法创建数据库和数据表
		private void initDataBase() {

			SqliteHelper sqLiteHelper = new SqliteHelper(GuideActivity.this);
			sqLiteHelper.getReadableDatabase();
			// 测试：
			Toast.makeText(GuideActivity.this, "数据库创建完毕", Toast.LENGTH_SHORT)
					.show();

		}
	    
}


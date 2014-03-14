package com.shunfengche.Activity;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKRoute;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKStep;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.Overlay;
import com.baidu.mapapi.RouteOverlay;

import com.util.Connect;
import com.util.Maputil;
import com.util.MyApplication;


public class DriverMapActivity extends MapActivity {
	/* BMapManager mBMapMan = null; 
	 MapView mMapView;
	 private LocationListener locationListener;
	 GeoPoint pt;
	 MKLocationManager	mLocationManager;
*/
	  private String mMapKey = Maputil.mapkey;
	      
	  
	      private Button btn_location = null,btn_search=null,btn_sound,btn_stepdetail,btn_setline,btn_order;
	 
	      private MapView mapView = null;

	      private BMapManager mMapManager = null;
	      private static final int SHOW_DATAPICK = 0;
	      private static final int SHOW_TIMEPICK = 2;
	      private static final int DATE_DIALOG_ID = 4;  
	     
	      private static final int TIME_DIALOG_ID = 5;
	      private int mYear;  
	      private int mMonth;
	      private int mDay; 
	      private int mHour;
	      private int mMinute;
	      private int userid;
	      private StringBuffer strx=new StringBuffer("");
	      private StringBuffer stry=new StringBuffer("");
	   RouteOverlay routeOverlay;
	     MyOverlay mylay=new MyOverlay();
		StringBuffer stepdetailtxt=new StringBuffer("");
	      //onResume时注册此listener，onPause时需要Remove,注意此listener不是Android自带的，是百度API中的
	
	 private LocationListener locationListener;

	      private MKSearch searchModel;
	
	      GeoPoint pt;
	      public class MyOverlay extends Overlay {
	  	    GeoPoint geoPoint ;
	  	   Paint paint = new Paint();
	  	
	  	   public GeoPoint getGeoPoint() {
	  		return geoPoint;
	  	}

	  	public void setGeoPoint(GeoPoint geoPoint) {
	  		this.geoPoint = geoPoint;
	  	}

	  	@Override
	  	   public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	  		        //在我的位置绘制一个String
	  	       Point point = mapView.getProjection().toPixels(geoPoint, null);
	  	       
	  	       paint.setColor(Color.RED);
	  	       paint.setTextSize(10);
	  	       paint.setTypeface(Typeface.DEFAULT_BOLD);
	  	           
	  		      
	  		      canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_has),  point.x-15,point.y-13, paint);
	  		    canvas.drawText("当前位置", point.x-15, point.y+10, paint);
  		        
	  	    }
	  			}
	      //为按钮创建监听类
	  	class BtnLs implements OnClickListener{

	  		@Override
	  		public void onClick(View arg0) {
	  			// TODO Auto-generated method stub
	  			switch  (arg0.getId()) {
	  			case R.id.btn_location:
	  				onPause();
	  				onResume();
	  				Toast.makeText(getApplicationContext(),"回到我的位置", Toast.LENGTH_SHORT).show();
	  		
	  				break;
	  	       case R.id.btn_search:
	  	    	 showDialog(1);
	  				break;
	  	       case R.id.btn_sound:
	  				
	  				break;
	  	       case R.id.btn_stepdetail:
	  	    	 showDialog(2);
	  	 
	  				break;
	  	     case R.id.btn_order:
	  	    	 Toast.makeText(getApplicationContext(), "查看订单", Toast.LENGTH_SHORT).show();
	  	    	 break;	
	  	     case R.id.btn_setline:
	  	   	if(!stepdetailtxt.toString().equals(""))
	  	    	 showDialog(3);
	  	   	else Toast.makeText(getApplicationContext(), "请选择路线！", Toast.LENGTH_SHORT).show();
	  	    	 break;
	  	     case R.id.setstarttime:
	  	    	  showDialog(4);
	  	    	 break;
	  	   case R.id.setstarttimedate:
	  		 showDialog(5);
	  	    	 break;
	  			default:
	  				break;
	  			}
	  		}
	  		
	  	}
	  	 ViewHolder viewHolder = new ViewHolder();
	  	  MKPlanNode startNode = new MKPlanNode();
	  	 MKPlanNode endNode = new MKPlanNode();
	  	 //创建dialog
	    public Dialog onCreateDialog(int id){
	    	System.out.println("duihuakuang");
	    	 Dialog dialog=null;
	    	 View corvertview=null;
	    	 dialog=new Dialog(DriverMapActivity.this,R.style.dialog);
	    
	     LayoutInflater flater = LayoutInflater.from(this);
	    	switch(id){
	    	//查询dialog
	    	case 1:
	    		//创建Builder对象
	    		 //b=new AlertDialog.Builder(this);
	    		

	    		corvertview= flater.inflate(R.layout.searchdialogxml, null);
	    	
	    		viewHolder.root=(LinearLayout)corvertview. findViewById(R.id.searchdialog);
	    		viewHolder.et_end=(EditText)corvertview. findViewById(R.id.et_end);
	    		viewHolder.et_start=(EditText)corvertview. findViewById(R.id.et_start);
	    		viewHolder.btn_searchDrivingRoute=(Button) corvertview.findViewById(R.id.btn_searchDrivingRoute);
	    		viewHolder.btn_searchDrivingRoute.setOnClickListener(new OnClickListener() {
	    				
		              
	    				
			             @Override
			
			             public void onClick(View v) {
			
			                 String destination = viewHolder.et_end.getText().toString();
			                 String start=viewHolder.et_start.getText().toString();
			                  //Toast.makeText(getApplicationContext(), "chaxun", Toast.LENGTH_SHORT).show();
			                 
			                 //设置起始地（当前位置）
			
			                endNode = new MKPlanNode();
			                 if(start.equals(""))
			                 startNode.pt = pt;
			            
			                 else startNode.name=start;
			                 //设置目的地
			 
			                 endNode = new MKPlanNode();
		
			                 endNode.name = destination;
		
			                  
			
			                 //展开搜索的城市
			
			                 String city = "天津";
			                 searchModel.drivingSearch(city, startNode, city, endNode);

		
//			               System.out.println("----"+city+"---"+destination+"---"+pt);
			
			        
			            
			                 //步行路线
			
//			               searchModel.walkingSearch(city, startNode, city, endNode);
			
			                 //公交路线
			
//			               searchModel.transitSearch(city, startNode, endNode);
			
			             }
			
			         });
	    		//单击以外区域消失
	    		 dialog.setContentView(corvertview);
	    		dialog.setCanceledOnTouchOutside(true);
	    	
	    		break;
	    		//导航信息dialog
	    	case 2:
	    		corvertview= flater.inflate(R.layout.stepdetail, null);
	    		viewHolder.stepdetail=(TextView) corvertview.findViewById(R.id.steptv);
	    		if(!stepdetailtxt.toString().equals("")){
	    		    
	    			viewHolder.stepdetail.setText(stepdetailtxt.toString());
	    			viewHolder.stepdetail.setMovementMethod(ScrollingMovementMethod.getInstance()); 
	    		}
	    		else  viewHolder.stepdetail.setText("没有导航信息！");
	    		 dialog.setContentView( corvertview);
		    		dialog.setCanceledOnTouchOutside(true);
	    		break;
	    
	    case 3:
    		corvertview= flater.inflate(R.layout.settinglinexml, null);
    		viewHolder.seating=(EditText) corvertview.findViewById(R.id.seating);
    		viewHolder.starttime=(TextView) corvertview.findViewById(R.id.starttime);
    		viewHolder.starttimedate=(TextView) corvertview.findViewById(R.id.starttimedate);
    		
    		viewHolder.speedet=(EditText) corvertview.findViewById(R.id.speedet);
    		viewHolder.setstarttime=(Button) corvertview.findViewById(R.id.setstarttime);
    		viewHolder.setstarttimedate=(Button) corvertview.findViewById(R.id.setstarttimedate);
    		viewHolder.setstarttimedate.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    		           Message msg = new Message(); 
    		           if (viewHolder.setstarttimedate.equals((Button) v)) {  
    		              msg.what = SHOW_DATAPICK;  
    		           }  
    		           dateandtimeHandler.sendMessage(msg); 
    				}
    			});
    	        
    		 viewHolder.setstarttime.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    		           Message msg = new Message(); 
    		           if (viewHolder.setstarttime.equals((Button) v)) {  
    		              msg.what =SHOW_TIMEPICK;  
    		           }  
    		         dateandtimeHandler.sendMessage(msg); 
    				}
    			});
    		viewHolder.aplayline=(Button) corvertview.findViewById(R.id.aplayline);
    		viewHolder.aplayline.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					final String seating=viewHolder.seating.getText().toString().trim();
					final String starttime=viewHolder.starttimedate.getText().toString()+viewHolder.starttime.getText().toString().trim();
					final String speed=viewHolder.speedet.getText().toString().trim();
					if(seating.equals("")||speed.equals("")||starttime.equals(""))
						Toast.makeText(getApplicationContext(), "请正确填写！", Toast.LENGTH_SHORT).show();
					else {
						new Thread( new Runnable() {
							public void run() {
							
								//发布线路
								String starttxt;
								if(startNode.name==null){
									starttxt=startNode.pt.getLongitudeE6()+"/"+startNode.pt.getLatitudeE6();
								}
								else starttxt=startNode.name;
								
							 String str="Line[userid]="
									+userid+"&Line[seating]="
									+seating+"&Line[starttime]="
									
									+starttime+"&Line[speed]="
									+speed+"&Line[starttxt]="
									+starttxt+"&Line[endtxt]="
									+endNode.name+"&Line[carLongitudeE6]="
									+pt.getLongitudeE6()+"&Line[carLatitudeE6]="
									+pt.getLatitudeE6();
								
								
								
								strx.append("/").append(stry);
								str=str+"&Line[linept]="+strx;
								Connect.dopostdriverline(str);
							}
						}).start();
						/*MyApplication myapp=(MyApplication) getApplication();
						int userid=myapp.getUser().getUserid();
						int state=0;
						String starttxt;
						if(startNode.name==null){
							starttxt=startNode.pt.getLongitudeE6()+"/"+startNode.pt.getLatitudeE6();
						}
						else starttxt=startNode.name;
						String sql="insert into linetb values(null,"
							+userid+","
							+seating+","
							+seating+","
							+state+",'"
							+starttime+"','"
							+speed+"','"
							+starttxt+"','"
							+endNode.name+"',"
							+pt.getLongitudeE6()+","
							+pt.getLatitudeE6()+")";
						Connect con=new Connect();
						if(con.doS(sql, 1, "linetb", null,null).trim().equals("0"))
							Toast.makeText(getApplicationContext(), "发布失败！",Toast.LENGTH_SHORT).show();
						
						else  {
							//上传离散点
							Toast.makeText(getApplicationContext(), "发布成功！",Toast.LENGTH_SHORT).show();
						}*/
					}
				}
			});
    		final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);  
            mMonth = c.get(Calendar.MONTH);  
            mDay = c.get(Calendar.DAY_OF_MONTH);
            
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            
            setDateTime(); 
            setTimeOfDay();
    		 dialog.setContentView( corvertview);
	    		dialog.setCanceledOnTouchOutside(true);
    		break;
	    case DATE_DIALOG_ID:  
	           return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,  
	                  mDay);
	       case TIME_DIALOG_ID:
	    	   return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
	    	}

			return dialog;
	    	
	    }
	    static class ViewHolder { 
	       public Button btn_searchDrivingRoute;
	        public EditText et_start;
	        public  EditText et_end;
	       public   LinearLayout root;
	       public  TextView stepdetail;
	       public  EditText seating;
	       public TextView starttime;
	       public TextView starttimedate;
	       public EditText speedet;
	       public Button aplayline;
	       public Button setstarttime;
	       public Button setstarttimedate;
	    }
	    //抖动
	    public void doudong(){
	    	 Vibrator mVibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);//获取振动器
	    	 mVibrator.vibrate(100);
	    }
	  	BtnLs btnls=new BtnLs();
	  	 /**
	     * 设置日期
	     */
		private void setDateTime(){
	       final Calendar c = Calendar.getInstance();  
	       
	       mYear = c.get(Calendar.YEAR);  
	       mMonth = c.get(Calendar.MONTH);  
	       mDay = c.get(Calendar.DAY_OF_MONTH); 
	  
	       updateDateDisplay(); 
		}
		
		/**
		 * 更新日期显示
		 */
		private void updateDateDisplay(){
			viewHolder.starttimedate.setText(new StringBuilder().append(mYear).append("-")
		    		   .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
		               .append((mDay < 10) ? "0" + mDay : mDay)); 
		}
		
	    /** 
	     * 日期控件的事件 
	     */  
	    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {  
	  
	       public void onDateSet(DatePicker view, int year, int monthOfYear,  
	              int dayOfMonth) {  
	           mYear = year;  
	           mMonth = monthOfYear;  
	           mDay = dayOfMonth;  

	           updateDateDisplay();
	       }  
	    }; 
		
		/**
		 * 设置时间
		 */
		private void setTimeOfDay(){
		   final Calendar c = Calendar.getInstance(); 
	       mHour = c.get(Calendar.HOUR_OF_DAY);
	       mMinute = c.get(Calendar.MINUTE);
	       updateTimeDisplay();
		}
		
		/**
		 * 更新时间显示
		 */
		private void updateTimeDisplay(){
			viewHolder.starttime.setText(new StringBuilder().append(mHour).append(":")
	               .append((mMinute < 10) ? "0" + mMinute : mMinute)); 
		}
	    
	    /**
	     * 时间控件事件
	     */
	    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				mHour = hourOfDay;
				mMinute = minute;
				
				updateTimeDisplay();
			}
		};
	    
	  
	  //时间和日期dialog
	    @Override  
	    protected void onPrepareDialog(int id, Dialog dialog) {  
	       switch (id) {  
	       case DATE_DIALOG_ID:  
	           ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);  
	           break;
	       case TIME_DIALOG_ID:
	    	   ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
	    	   break;
	       }
	    }  
	  
	  	/** 
	     * 处理日期和时间控件的Handler 
	     */  
	    Handler dateandtimeHandler = new Handler() {
	  
	       @Override  
	       public void handleMessage(Message msg) {  
	           switch (msg.what) {  
	           case SHOW_DATAPICK:  
	               showDialog(4);  
	               break; 
	           case SHOW_TIMEPICK:
	        	   showDialog(5);
	        	   break;
	           
	           }  
	       }  
	  
	    }; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		         setContentView(R.layout.main);
		         //初始化控件
		         btn_location = (Button) findViewById(R.id.btn_location);
		         btn_search = (Button) findViewById(R.id. btn_search);
		         btn_sound = (Button) findViewById(R.id.btn_sound);
		         btn_stepdetail = (Button) findViewById(R.id.btn_stepdetail);
		         btn_setline=(Button) findViewById(R.id.btn_setline);
		         btn_order=(Button) findViewById(R.id.btn_order);
		         btnls=new BtnLs();
		         btn_location.setOnClickListener(btnls);
		         btn_search.setOnClickListener(btnls);
		         btn_sound.setOnClickListener(btnls);
		         btn_stepdetail.setOnClickListener(btnls);
		         btn_setline.setOnClickListener(btnls);
		         btn_order.setOnClickListener(btnls);
		     mMapManager = new BMapManager(getApplication());
	         mMapManager.init(mMapKey, new MyGeneralListener());
	     	MyApplication myapp=(MyApplication) getApplication();
			 userid=myapp.getUser().getUserid();
		         super.initMapActivity(mMapManager);
	           mapView = (MapView) this.findViewById(R.id.bmapsView);
	
		         //设置启用内置的缩放控件
             
		         //mapView.setBuiltInZoomControls(true); 
	       
		         //设置在缩放动画过程中也显示overlay,默认为不绘制
		
//		         mapView.setDrawOverlayWhenZooming(true);
		
		         //获取当前位置层
		    
		   /*      myLocationOverlay = new MyLocationOverlay(baidumapActivity.this, mapView);
 		         myLocationOverlay.enableCompass(); // 打开指南针

 		         //将当前位置的层添加到地图底层中
 	
 		       
 		    mapView.getOverlays().add(myLocationOverlay);*/
 		    
		
		      // 注册定位事件
	
		         locationListener = new LocationListener(){
		     @Override
		
		             public void onLocationChanged(Location location) {
	
		                 if (location != null){
	
		                     //生成GEO类型坐标并在地图上定位到该坐标标示的地点
		
		                      pt = new GeoPoint((int)(location.getLatitude()*1e6),
	
		                             (int)(location.getLongitude()*1e6));
		                    
		     		        mylay.setGeoPoint(pt);
		     		         //将当前位置的层添加到地图底层中
		     		    
		     		      if( mapView.getOverlays().size()>=1)
		     		   
		     		      {
		     		    	  mapView.getOverlays().set(0,mylay);
		     		    	 if(mapView.getOverlays().size()>1)
		     		    		 mapView.getOverlays().set(1,routeOverlay);
			                 //将当前位置的层添加到地图底层中
		     		      }
		     		      else   mapView.getOverlays().add(mylay);
		     		
//		                   System.out.println("---"+location.getLatitude() +":"+location.getLongitude());
	
		                     mapView.getController().animateTo(pt);
	
		                 }
		
		             }
		
		         };
		  
		         //初始化搜索模块
		
		         searchModel = new MKSearch();
	
		         //设置路线策略为最短距离
	
		         searchModel.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);
		 
		         searchModel.init(mMapManager, new MKSearchListener() {
	
		             //获取驾车路线回调方法
		
		             @Override
		
		             public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
		
		                 // 错误号可参考MKEvent中的定义
		            	   //Toast.makeText(getApplicationContext(), "onGetDrivingRouteResult", Toast.LENGTH_SHORT).show();
		                 if (error != 0 || res == null) {
		
		                     Toast.makeText(DriverMapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		
		                     return;
	
		                 }
		
		           routeOverlay = new RouteOverlay(DriverMapActivity.this, mapView);
	
		                  
	
		                 // 此处仅展示一个方案作为示例
	
		                 MKRoute route = res.getPlan(0).getRoute(0);
		                 
		                 int distanceM = route.getDistance();

		                 String distanceKm = String.valueOf(distanceM / 1000) +"."+String.valueOf(distanceM % 1000);
	
		                 System.out.println("距离:"+distanceKm+"公里---节点数量:"+route.getNumSteps());
		
		                 for (int i = 0; i < route.getNumSteps(); i++) {
	
		                     MKStep step = route.getStep(i);
		                     if(i!=0){
		                    	 strx.append(" ");
		                    	 stry.append(" ");
		                     }
		                     strx.append(step.getPoint().getLatitudeE6());
		                     stry.append(step.getPoint().getLongitudeE6());
	                          stepdetailtxt.append("节点信息："+step.getContent()+"\n");
		                     System.out.println("节点信息："+step.getContent());
	
		                 }
		                // Toast.makeText(DriverMapActivity.this, listp.size(), 2000).show();
		               
		                 routeOverlay.setData(route);
		                 Toast.makeText(DriverMapActivity.this, "----------size:"+route.getNumSteps(), Toast.LENGTH_SHORT).show();
		         		
		             mapView.getOverlays().clear();
		              
		             
		      /*     if(mapView.getOverlays().size()>2){
		        	   mapView.getOverlays().set(2,routeOverlay);
		           mapView.getOverlays().set(0,myLocationOverlay);
		           }
		           else   mapView.getOverlays().add(routeOverlay);*/
		             mapView.getOverlays().add(mylay);
		        	  mapView.getOverlays().add(routeOverlay);
		                 //将当前位置的层添加到地图底层中
		             	
		              
		             
		
		               mapView.invalidate();
		
		                 mapView.getController().animateTo(res.getStart().pt);
		                 dismissDialog(1);
	
		             }
		
		              
		
		             //以下两种方式和上面的驾车方案实现方法一样
		
		             @Override
		
		             public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
		
		                 //获取步行路线
	
		             }
		
		              
		
		             @Override
	
		             public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
		
		                 //获取公交线路
		
		             }
		
		              
		
		             @Override
		
		             public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
	
		             }
		
		             @Override
	
		             public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
	
		             }
		
		          
		             @Override
		
		             public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
		
		             }
		 
		         });
		
		          
		 
		       
		
		          
		
		     }
	
  
    
	
		     @Override
		
		     protected void onResume() {
		 
		         mMapManager.getLocationManager().requestLocationUpdates(locationListener);
		
		   /*      myLocationOverlay.enableMyLocation();
		 
		   myLocationOverlay.enableCompass(); // 打开指南针
*/
		         mMapManager.start();
		
		         super.onResume();
		 
		     }
		
		      
		
		     @Override
		
		     protected void onPause() {
		
		         mMapManager.getLocationManager().removeUpdates(locationListener);
	
		 /*   myLocationOverlay.disableMyLocation();//显示当前位置
		
		         myLocationOverlay.disableCompass(); // 关闭指南针
*/
		         mMapManager.stop();
		 
		         super.onPause();
		
		     }
	
		  
		
		     @Override
		 
		     protected boolean isRouteDisplayed() {
		
		         // TODO Auto-generated method stub
	
		         return false;
	
		     }
	
		      
	
		     // 常用事件监听，用来处理通常的网络错误，授权验证错误等
	
		     class MyGeneralListener implements MKGeneralListener {
		 
		             @Override
		
		             public void onGetNetworkState(int iError) {
		
		                 Log.d("MyGeneralListener", "onGetNetworkState error is "+ iError);
		
		                 Toast.makeText(DriverMapActivity.this, "您的网络出错啦！",
		 
		                         Toast.LENGTH_LONG).show();
		 
		             }
		
		  
		 
		             @Override
		
		             public void onGetPermissionState(int iError) {
	
		                 Log.d("MyGeneralListener", "onGetPermissionState error is "+ iError);
		
		                 if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
		 
		                     // 授权Key错误：
		
		                     Toast.makeText(DriverMapActivity.this,
	
		                             "请在BMapApiDemoApp.java文件输入正确的授权Key！",
	
		                             Toast.LENGTH_LONG).show();

		                 }
	
		             }
		
		 }
		     // 返回键  
		     private long exitTime = 0;  
		     @Override  
		     public boolean onKeyDown(int keyCode, KeyEvent event) {  
		         if (keyCode == event.KEYCODE_BACK) {  
		             if ((System.currentTimeMillis() - exitTime) > 2000) {  
		                 Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();  
		                 exitTime = System.currentTimeMillis();  
		             } else {  
		                 finish();  
		                 System.exit(0);  
		             }  
		             return true;  
		         }  
		         return super.onKeyDown(keyCode, event);  
		     }  
		 }

		/*mBMapMan = new BMapManager(getApplication());
			mBMapMan.init(Maputil.mapkey, null);
		super.initMapActivity(mBMapMan);
	 
		mMapView = (MapView) findViewById(R.id.bmapsView);
	//mMapView.setBuiltInZoomControls(true);  //设置启用内置的缩放控件
			MapController mMapController = mMapView.getController();  // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
			GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
			      (int) (116.404 * 1E6));  //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
				mMapController.setCenter(point); 

		//mMapController.setZoom(12);    //设置地图zoom级别
		//mMapView.getOverlays().add(new MyOverlay());
		
			// 初始化Location模块
	mLocationManager = mBMapMan.getLocationManager();
		// 通过enableProvider和disableProvider方法，选择定位的Provider
	 mLocationManager.enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
	 mLocationManager.disableProvider(MKLocationManager.MK_GPS_PROVIDER);
		// 添加定位图层
	 // 注册定位事件

	 

	
		MyLocationOverlay mylocTest = new MyLocationOverlay(this, mMapView);
		mylocTest.enableMyLocation(); // 启用定位
		Double geolat =mLocationManager.getLocationInfo().getLatitude() * 1E6;  
        Double geoLng = mLocationManager.getLocationInfo().getLongitude() * 1E6;  
        // Toast.makeText(ShowMap.this, "纬度 ："+geolat+"经度: "+geoLng,  
        // Toast.LENGTH_SHORT ).show();  
        GeoPoint mypoint1 = new GeoPoint(geolat.intValue(), geoLng.intValue()); 
	mylocTest.enableCompass();    // 启用指南针
			mMapView.getOverlays().add(mylocTest);
			//mMapController.setCenter(mypoint1); 
		locationListener = new LocationListener() {

	            @Override
	            public void onLocationChanged(Location location) {
	                    // TODO Auto-generated method stub
	                    if (location != null) {
	                            GeoPoint geoPoint = new GeoPoint(
	                                            (int) (location.getLatitude() * 1e6),
	                                            (int) (location.getLongitude() * 1e6));
	                            MyOverlay mylay=new MyOverlay();
	                            mylay.setGeoPoint(geoPoint);
	                            mMapView.getOverlays().add( mylay);
	                            mMapView.getController().animateTo(geoPoint);
	                            mMapView.getController().setCenter(geoPoint);
	                            mMapView.getController().setZoom(14);// 设置地图zoom级别
	                            mLocationManager.removeUpdates(locationListener);  
	                    }
	            }

	    };
	    mLocationManager.enableProvider(MKLocationManager.MK_GPS_PROVIDER);  
	    mLocationManager.enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);  
	    mLocationManager.requestLocationUpdates(locationListener);
	    mLocationManager.setNotifyInternal(5, 2); 
	}
	// 定位自己的位置，只定位一次  
    private LocationListener mLocationListener = new LocationListener() {  
  
        @Override  
        public void onLocationChanged(Location location) {  
            if (location != null) {  
                Double geolat = location.getLatitude() * 1E6;  
                Double geoLng = location.getLongitude() * 1E6;  
                // Toast.makeText(ShowMap.this, "纬度 ："+geolat+"经度: "+geoLng,  
                // Toast.LENGTH_SHORT ).show();  
                GeoPoint mypoint1 = new GeoPoint(geolat.intValue(), geoLng.intValue());  
                app.mBMapMan.getLocationManager().removeUpdates(mLocationListener);  
                onceMyPoint++;  
            }  
        }  
    };  
public class MyOverlay extends Overlay {
	    GeoPoint geoPoint ;
	   Paint paint = new Paint();
		
	   public GeoPoint getGeoPoint() {
		return geoPoint;
	}

	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}

	@Override
	   public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		        //在天安门的位置绘制一个String
	       Point point = mMapView.getProjection().toPixels(geoPoint, null);
		        canvas.drawText("★这里是我的位置", point.x, point.y, paint);
		       
	    }
			}

@Override
protected void onDestroy() {
	   if (mBMapMan != null) {
        mBMapMan.destroy();
        mBMapMan = null;
    }
	    super.onDestroy();
	}
@Override
protected void onPause() {
	    if (mBMapMan != null) {
		        mBMapMan.stop();
	    }
	    super.onPause();
		}
	@Override
protected void onResume() {
	    if (mBMapMan != null) {
	        mBMapMan.start();
	    }
		    super.onResume();
	}


}
*/
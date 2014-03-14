package com.shunfengche.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.app.po.Driver;
import com.app.po.Line;
import com.app.po.Order;
import com.app.po.User;
import com.baidu.mapapi.GeoPoint;
import com.util.Connect;
import com.util.Maputil;
import com.util.MyApplication;
import com.util.uploadFileClass;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class  DriverinfoActivity extends Activity {
	
	private Button submit_order;
	ImageView  carimg,dotelephone,imgsendmsg,chatimv,driverhead;
	TextView
	 edit_platenum,drivertelephone,drivername,seatnum,
	 edit_licensenum;
	Line line;
	User user,driveruser;
	Driver dr;
	 float money=0;
	  MyApplication myapp;
	private List< Map<String,?>>listpipei=new ArrayList<Map<String,?>>();
	 @Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);

    setContentView(R.layout.driverdetailinfo);
/*    edit_platenum=(TextView) findViewById(R.id. edit_platenum);
    drivertelephone=(TextView) findViewById(R.id. drivertelephone);
    edit_licensenum=(TextView) findViewById(R.id. edit_licensenum);
    drivername=(TextView) findViewById(R.id. drivername);
    seatnum=(TextView) findViewById(R.id.allseatnum);
 myapp=(MyApplication) getApplication();
 listpipei=myapp.getListpipei();
   line= (Line) listpipei.get(2).get("resultline");
   money=Float.parseFloat((String) listpipei.get(3).get("money"));
   user=myapp.getUser();
   driveruser=myapp.getDriver();
   
    Connect con=new Connect();
    String sql="select * from drivertb where userid="+driveruser.getUserid();
    dr=(Driver) con.getlist(0, "drivertb", sql).get(0);
    carimg=(ImageView) findViewById(R.id. carimg);
    dotelephone=(ImageView) findViewById(R.id. dotelephone);
    imgsendmsg=(ImageView) findViewById(R.id. imgsendmsg);
    chatimv=(ImageView) findViewById(R.id. chatimv);
    driverhead=(ImageView) findViewById(R.id. driverhead);
  
   submit_order=(Button) findViewById(R.id. submit_order);
   
  seatnum.setText(line.getSeatleft()+"");
   drivername.setText(driveruser.getUsername());
   drivertelephone.setText(driveruser.getTelephone());
   edit_platenum.setText(dr.getCarnum());
   edit_licensenum.setText(dr.getDrivinglicenceid());
   uploadFileClass ufc=new uploadFileClass();
   ufc.downloadfile(dr.getCarimgi());
  
  
   Bitmap head=BitmapFactory.decodeFile(driveruser.getPhoto());
   driverhead.setImageBitmap(head);
   carimg.setImageBitmap(ufc.getdownloadBitmap(dr.getCarimgi()));
   dotelephone.setOnClickListener(btnls);
   imgsendmsg.setOnClickListener(btnls);
   chatimv.setOnClickListener(btnls);
   submit_order.setOnClickListener(btnls);
 
   
	 }
	 private BtnLs btnls=new BtnLs();
	 //为按钮创建监听类
	  	class BtnLs implements OnClickListener{

	  		@Override
	  		public void onClick(View arg0) {
	  			// TODO Auto-generated method stub
	  			switch  (arg0.getId()) {
	  			case R.id.dotelephone:
	  				 Toast.makeText(getApplicationContext(), "我是 乘车人", Toast.LENGTH_SHORT).show();
	  			
	  				
	  				break;
	  	       case R.id.imgsendmsg:
	  	    	 Toast.makeText(getApplicationContext(), "我是司机", Toast.LENGTH_SHORT).show();
	  	    	
	  	    
	  				break;
	  	       case R.id.chatimv:
	  	    	 Toast.makeText(getApplicationContext(), "聊天", Toast.LENGTH_SHORT).show();
	  				break;
	  				
	  	       case R.id.submit_order:
	  	    	 Toast.makeText(getApplicationContext(), "预定", Toast.LENGTH_SHORT).show();
	  	             Connect con=new Connect();
	  	           String sql="select * from ordertb where lineid="+line.getLineid()+" and userid="+user.getUserid();
	  	        List<Order> orders= (List<Order>) con.getlist(0, "ordertb", sql);
	  	        if(orders.size()==0){
	  	           GeoPoint starpt=(GeoPoint) listpipei.get(0).get("disptsresult");
	  	         GeoPoint endpt=(GeoPoint) listpipei.get(0).get("dispteresult");
	  	        String  start=starpt.getLongitudeE6()+"/"+starpt.getLatitudeE6();
	  	      String end=endpt.getLongitudeE6()+"/"+endpt.getLatitudeE6();
	  	     String start= (String) listpipei.get(0).get("bystart");
	  	     String end=(String) listpipei.get(0).get("byend");
	  	             sql="insert into ordertb values(null,"+line.getLineid()+","+user.getUserid()+",0,'"+start+"','"+end+"',"+myapp.myLongitudeE6+","+myapp.myLatitudeE6+","+money+",'"+Maputil.getnowTime()+"')";
	  	            String result= con.doS(sql, 1, "ordertb", null, null);
	  	            if(result.trim().equals("1")) Toast.makeText(getApplicationContext(), "预定成功！", Toast.LENGTH_SHORT).show();
	  	        }
	  	        else Toast.makeText(getApplicationContext(), "抱歉，您已经预定过了！", Toast.LENGTH_SHORT).show();
	  				break;
	  	              
	  	  
	  			default:
	  				break;
	  			}
	  		}
	  		*/
	  	}

}

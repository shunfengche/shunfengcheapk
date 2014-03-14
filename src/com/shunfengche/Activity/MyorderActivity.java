package com.shunfengche.Activity;

import java.text.DecimalFormat;
import java.util.List;

import com.app.po.Order;
import com.util.Connect;
import com.util.MyApplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyorderActivity extends Activity {
	private ListView myorderlist;
	private List<Order> listorder;
	private TextView noorder;
	Connect con=new Connect();
	MyApplication myapp;
	myorderBaseAdapter myadpter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myorderlayout);
		myorderlist=(ListView) findViewById(R.id.listmyorder);
		myapp=(MyApplication) this.getApplication();
		noorder=(TextView) findViewById(R.id.noorder);
		String sql="select * from ordertb where userid="+myapp.getUser().getUserid()+" order by -orderid";
		listorder=(List<Order>) con.getlist(0, "ordertb", sql);
		if(listorder.isEmpty()) {
			myorderlist.setVisibility(View.GONE);
			noorder.setVisibility(View.VISIBLE);
		
		}
		else{
			noorder .setVisibility(View.GONE);
			myorderlist.setVisibility(View.VISIBLE);
			myadpter=new myorderBaseAdapter( getApplicationContext(),listorder);
			myorderlist.setAdapter(myadpter);
		}
	}
	public class myorderBaseAdapter extends BaseAdapter {
	
		 private List<Order> coll;

		    private Context ctx;
		  
		    
		    private LayoutInflater mInflater;
		public myorderBaseAdapter(Context context, List<Order> coll) {
	        ctx = context;
	        this.coll = coll;
	        mInflater = LayoutInflater.from(context);
	    }

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return coll.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return coll.get(position);
		}

		    
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			final Order entity = coll.get(position);
	
	    		
	    	ViewHolder viewHolder = null;	
		    if (convertView == null)
		    {
		    	viewHolder = new ViewHolder();
		    	
					  convertView = mInflater.inflate(R.layout.myorderxml, null);
					
					  viewHolder.tvUserimg = (ImageView) convertView.findViewById(R.id.driverhead);
					  viewHolder.lineinfo = (TextView) convertView.findViewById(R.id.lineinfo);
					  viewHolder.linemoney = (TextView) convertView.findViewById(R.id.linemoney);
					  viewHolder.linestate = (TextView) convertView.findViewById(R.id.linestate);
					  viewHolder.pinglun = (TextView) convertView.findViewById(R.id.pinglun);
					  viewHolder.tvtime = (TextView) convertView.findViewById(R.id.tvtime);
		    	   
				 
				  
				  convertView.setTag(viewHolder);
		    }else{
		        viewHolder = (ViewHolder) convertView.getTag();
		    }
		    viewHolder.tvUserimg.setTag(position);
		    viewHolder.tvUserimg.setOnClickListener(new listen());
		
		  /*  Bitmap map=BitmapFactory.decodeFile(entity.getUserimg());
		    viewHolder.tvUserimg.setImageBitmap(map);*/
		  String start=entity.getOrigin();
		  String end=entity.getDestination();
		    viewHolder.lineinfo.setText(start+"--->"+end);
		    String state = null;
		    Boolean flag=false;
		    switch(entity.getState()){
		    case -1:
		    	state="拒绝";
		    	break;
		    case 0:
		    	state="预定";
		    	break;
		    case 1:
		    	state="等车";
		    	break;
		    case 2:
		    	state="上车";
		    	break;
		    case 3:
		    	state="下车";
		    	flag=true;
		    	break;
		    case 4:
		    	state="关闭";
		    	break;
		    }
		    viewHolder.linestate.setText(state);
		    String money=new DecimalFormat("￥###.##").format(entity.getMoney());
		    
		    viewHolder.linemoney.setText(money);
		    viewHolder.tvtime.setText(entity.getTime());
		    viewHolder.pinglun.setTag(position);
		    viewHolder.pinglun.setOnClickListener(new listen());
		    if(flag){
		    	
		    }
		    return convertView;
	    }
	    

	

	}
	class listen implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		   switch (v.getId()){
		   case R.id.pinglun:
			   Toast.makeText(getApplicationContext(), "你点击了第"+v.getTag()+"个评论", Toast.LENGTH_SHORT).show();
			   break;
		   case R.id.driverhead:
			   Toast.makeText(getApplicationContext(), "你点击了第"+v.getTag()+"个头像", Toast.LENGTH_SHORT).show();
			   break;
		   }
		}
		
	}
    static class ViewHolder { 
        public ImageView tvUserimg;
   
        public TextView lineinfo,linemoney,linestate,pinglun,tvtime;
        
     
    }

}

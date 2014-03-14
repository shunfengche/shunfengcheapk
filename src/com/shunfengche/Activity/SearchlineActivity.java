package com.shunfengche.Activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class SearchlineActivity extends Activity implements OnClickListener{
	private EditText start,end;
	private TextView time,date;
	private Button settime,setdate;
	private final int DATE_DIALOG_ID=1,TIME_DIALOG_ID=2;
	private int mYear;  
    private int mMonth;
    private int mDay; 
    private int mHour;
    private int mMinute;
   
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.linesearchxml);
            time=(TextView) findViewById(R.id.searchline_starttime);
            date=(TextView) findViewById(R.id.searchline_starttimedate);
            start=(EditText) findViewById(R.id.searchline_et_start);
            end=(EditText) findViewById(R.id.searchline_et_end);
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);  
            mMonth = c.get(Calendar.MONTH);  
            mDay = c.get(Calendar.DAY_OF_MONTH);
            
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            
            setDateTime(); 
            setTimeOfDay();
	 }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.searchline_setstarttime:
			
			showDialog(TIME_DIALOG_ID);
			break;
		case R.id.searchline_setstarttimedate:
			showDialog(DATE_DIALOG_ID);
			
			
			break;
		case R.id.linesearchbtn:
			Intent in=new Intent(SearchlineActivity.this, MatchActivity.class);
			Bundle bundle = new Bundle();
			/*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
			bundle.putString("start", start.getText().toString());
			bundle.putString("end", end.getText().toString());
			bundle.putString("time", time.getText().toString());
			bundle.putString("date", date.getText().toString());
			/*把bundle对象assign给Intent*/
			in.putExtras(bundle);
			startActivity(in);
			onDestroy();
			break;
		}
	}
	 //创建dialog
    public Dialog onCreateDialog(int id){
    	 Dialog dialog=null;
    	 View corvertview=null;
    	 dialog=new Dialog(SearchlineActivity.this,R.style.dialog);
    
     LayoutInflater flater = LayoutInflater.from(this);
    	switch(id){
    	 case DATE_DIALOG_ID:  
	           return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,  
	                  mDay);
	       case TIME_DIALOG_ID:
	    	   return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
	    	}

		   return null;
    	}
	
   
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
	date.setText(new StringBuilder().append(mYear).append("-")
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
	time.setText(new StringBuilder().append(mHour).append(":")
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
}

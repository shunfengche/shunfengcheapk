package com.shunfengche.Activity;

import com.util.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

public class Chat_Background extends Activity {
	//int userid = UserClass.getUserid();
	int userid =0;
	int[] imageIds = new int[]
	{
	    R.drawable.andy_chat_bg8,
		R.drawable.andy_chat_bg9, R.drawable.andy_chat_bg10, R.drawable.andy_chat_bg11,
		R.drawable.andy_chat_bg1, R.drawable.andy_chat_bg2, R.drawable.andy_chat_bg3,
		  R.drawable.andy_chat_bg6 };
	View status = null;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// ȡ�����ı�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.andy_show_bg);
		MyApplication myapp=(MyApplication) getApplication();
		userid=myapp.getUser().getUserid();
		final Gallery gallery = (Gallery) findViewById(R.id.gallery);
		// ��ȡ��ʾͼƬ��ImageSwitcher����
		final ImageSwitcher switcher = (ImageSwitcher) 
			findViewById(R.id.switcher);
		// ΪImageSwitcher��������ViewFactory����
		switcher.setFactory(new ViewFactory()
		{

			public View makeView()
			{
				ImageView imageView = new ImageView(Chat_Background.this);
				imageView.setBackgroundColor(0xff0000);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				return imageView;
			}
		});
		// ����ͼƬ��Ķ���Ч��?
		switcher.setInAnimation(AnimationUtils.loadAnimation(this,
			android.R.anim.fade_in));
		switcher.setOutAnimation(AnimationUtils.loadAnimation(this,
			android.R.anim.fade_out));
		// ����һ��BaseAdapter���󣬸ö������ṩGallery����ʾ��ͼƬ
		BaseAdapter adapter = new BaseAdapter()
		{

			public int getCount()
			{
				return imageIds.length;
			}
	
			public Object getItem(int position)
			{
				return position;
			}
		
			public long getItemId(int position)
			{
				return position;
			}

			// �÷����ķ��ص�View���Ǵ����ÿ���б���?
			
			public View getView(int position, View convertView, ViewGroup parent)
			{
				// ����һ��ImageView
				ImageView imageView = new ImageView(Chat_Background.this);
				imageView
					.setImageResource(imageIds[position % imageIds.length]);
				// ����ImageView����������
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new Gallery.LayoutParams(180, 240));
				TypedArray typedArray = obtainStyledAttributes(
					R.styleable.Gallery);
				imageView.setBackgroundResource(typedArray.getResourceId(
					R.styleable.Gallery_android_galleryItemBackground, 0));
				return imageView;
			}
		};
		
		gallery.setAdapter(adapter);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			// ��Galleryѡ�����ı�ʱ�����÷���?
		
			public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id)
			{
				switcher.setImageResource(imageIds[position % imageIds.length]);
				switcher.setOnClickListener(new ImageClickListener(imageIds[position % imageIds.length]));

			}

		
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
	}
	class ImageClickListener implements OnClickListener{
		int image;
		
		public ImageClickListener(int image){
			this.image = image;
		
		}
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(Chat_Background.this, "����������", Toast.LENGTH_SHORT).show();
			SharedPreferences.Editor editor = getSharedPreferences("image_content",
								MODE_WORLD_WRITEABLE).edit();
			editor.putInt("content"+userid, image);
	
			editor.commit();
			/*Intent intent = new Intent(Chat_Background.this,Chat.class);
			intent.putExtra("imagesrc", image);*/
			/*Chat_Background.this.startActivity(intent);*/
			
			
			
			
			
			
			
			
			
		}
		
	}
}

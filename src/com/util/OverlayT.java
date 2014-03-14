package com.util;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;

public class OverlayT extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
	private Drawable marker;
	private Context mContext;

	public OverlayT(Drawable arg0) {
		super(arg0);
	}

	public OverlayT(Drawable marker, GeoPoint point, Context context) {
		super(boundCenterBottom(marker));
		this.mContext = context;
		this.marker = marker;
		mGeoList.add(new OverlayItem(point, "我", "点击出来"));
		populate();
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean arg2) {
		super.draw(canvas, mapView, arg2);
		Projection projection = mapView.getProjection();
		for (int index = size() - 1; index >= 0; index--) {
			OverlayItem overLayItem = getItem(index);
			String title = overLayItem.getTitle();
			Point point = projection.toPixels(overLayItem.getPoint(), null);
			Paint paintText = new Paint();
			paintText.setColor(Color.BLUE);
			paintText.setTextSize(15);
			canvas.drawText(title, point.x - 30, point.y, paintText);
		}
		boundCenterBottom(marker);
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		// TODO Auto-generated method stub
		return super.onTap(arg0, arg1);
	}

	protected boolean onTap(int i) {
		setFocus(mGeoList.get(i));
		GeoPoint pt = mGeoList.get(i).getPoint();
		Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(), Toast.LENGTH_SHORT).show();
		return super.onTap(i);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mGeoList.get(i);
	}

	@Override
	public int size() {
		return mGeoList.size();
	}
}


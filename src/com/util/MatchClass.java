package com.util;

import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import java.util.Map;
import com.app.po.Line;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
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
import com.baidu.mapapi.RouteOverlay;


public class MatchClass {
private List<Line> listline;

private String startname,endname;
private BMapManager mMapManager = null;

private MKSearch searchModel;
private  MKRoute route,startroute,endroute,routeresult
,startrouteresult,endrouteresult
,startrouteresult1,endrouteresult1;
private GeoPoint dispts,dispte,disptsresult,dispteresult,disptsresult1,dispteresult1;
private int type;
private float startdis,enddis,startdisresult,enddisresult,mindisresult
,startdisresult1,enddisresult1;
private Line resultline;
public void initdate(){
	Connect con=new Connect();
	//未开的和已开的
	String sql="select * from linetb where state!=-1 and seatleft>0";
	//得到线路表集合
	listline=(List<Line>) con.getlist(0, "linetb", sql);
	
      //初始化搜索模块
  	
      searchModel = new MKSearch();

      //设置路线策略为最短距离

      searchModel.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);

      searchModel.init(mMapManager, new MKSearchListener() {

          //获取驾车路线回调方法

          @Override

          public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {

              // 错误号可参考MKEvent中的定义
        	  System.out.println("onGetDrivingRouteResult");
         	  // Toast.makeText(getApplicationContext(), "onGetDrivingRouteResult", Toast.LENGTH_SHORT).show();
              if (error != 0 || res == null) {

                  //Toast.makeText(c, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            	  isaddress=false;
            	  System.out.println("抱歉，未找到结果");
                  return;

              }
            isaddress=true;
            switch(type){
            //司机线路
            case 1:
          	  route = res.getPlan(0).getRoute(0);
          	  break;
          	  //用户起点到司机线路上的点的线路
            case 2:
          	 startroute = res.getPlan(0).getRoute(0);
          	    startdis= startroute.getDistance();
          	    
          	  break;
          	  //用户终点到司机线路上的点的线路
            case 3:
          	  endroute = res.getPlan(0).getRoute(0);
          	  enddis= endroute.getDistance();
          	
            break;
            }
              	
              
          }

  		@Override
  		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
  			// TODO Auto-generated method stub
  			
  		}

  		@Override
  		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
  			// TODO Auto-generated method stub
  			
  		}

  		@Override
  		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
  			// TODO Auto-generated method stub
  			
  		}

  		@Override
  		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
  			// TODO Auto-generated method stub
  			
  		}

  		@Override
  		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
  			// TODO Auto-generated method stub
  			
  		}    
      });
}
public MatchClass(String startname,String endname,BMapManager mMapManager){
	this.startname=startname;
	this.endname=endname;
    this.mMapManager=mMapManager;
	initdate();
}
private boolean isaddress=true;
public boolean isaddress(){
	MKPlanNode  endNode = new MKPlanNode();
	MKPlanNode startNode = new MKPlanNode();
	System.out.println("起点："+startname);
	System.out.println("终点："+endname);
	startNode.name=startname;
	endNode.name=endname;
	searchModel.drivingSearch("天津", startNode, "天津", endNode);
	 return isaddress;
}
public  List< Map<String,?>> getlinebest(){
	int i=0;
	  System.out.println("进入getlinebest循环");
	for(Line line:listline){
		//line.get
	    //设置起始地（当前位置）
		  System.out.println("for lineid:"+line.getLineid());
		String start=line.getOrigin();
		String end=line.getDestination();
		MKPlanNode  endNode = new MKPlanNode();
		MKPlanNode startNode = new MKPlanNode();
         if(start.contains("/")){
        	 GeoPoint startpt=new GeoPoint(
        			 Integer.parseInt(start.substring(0, start.lastIndexOf("/")-1))
        			 , Integer.parseInt(start.substring(start.lastIndexOf("/")+1))); 	 
         startNode.pt =startpt;
         }
         else startNode.name=start;
         //设置目的地
      
         endNode = new MKPlanNode();

         endNode.name = end;
         //更改线路 route
         type=1;
         System.out.println("for lineid:"+line.getLineid()+"type=1:"+startNode.name+"-->"+endNode.name);
		 searchModel.drivingSearch("天津", startNode, "天津", endNode);
		 //处理线路 route，更改dispts,dispte
		 dealroute();
		 //最短走路路线
		 if(i==0) {
			 mindisresult=startdisresult+enddisresult;
			
		 }
		 if(startdisresult+enddisresult<mindisresult){
			 mindisresult=startdisresult+enddisresult;
			 routeresult=route;
			 startrouteresult1=startrouteresult;
			 endrouteresult1=endrouteresult;
			startdisresult1=startdisresult;
             enddisresult1=enddisresult;
             disptsresult1=disptsresult;
             dispteresult1=dispteresult;
             resultline=line;
		 }
		 i++;
	}
	//给一个范围
List< Map<String,?>>list=new ArrayList<Map<String,?>>();
	if(mindisresult<=Maputil.mindis){
		Map<String,GeoPoint> mappt=new HashMap<String, GeoPoint>();
		mappt.put("disptsresult", disptsresult1);
		mappt.put("dispteresult", dispteresult1);
		list.add(mappt);
		Map<String,MKRoute> maproute=new HashMap<String, MKRoute>();
		maproute.put("routeresult", routeresult);
		maproute.put("startrouteresult", startrouteresult1);
		maproute.put("endrouteresult1", endrouteresult1);
		list.add(maproute);
		Map<String,Line> mapline=new HashMap<String, Line>();
		mapline.put("resultline", resultline);
		list.add(mapline);
	}
	return list;
}
public void dealroute(){
	 System.out.println("进入dealroute");
		MKPlanNode  endNode = new MKPlanNode();
		MKPlanNode startNode = new MKPlanNode();
		startNode.name=startname;
   
   System.out.println("---节点数量:"+route.getNumSteps());
    int t=0;
    System.out.println("dealroute起点循环");
     for (int i = 0; i < route.getNumSteps(); i++) {
    	
         MKStep step = route.getStep(i);
        endNode.pt=step.getPoint();
        type=2;
     
        searchModel.drivingSearch("天津", startNode, "天津", endNode);
        if(i==0)  startdisresult=startdis;
        if(startdis<=startdisresult){
        	startdisresult=startdis;
        	startrouteresult=startroute;
        	disptsresult=dispts;
        	t=i;
        }
     }
     System.out.println("dealroute起点循环结果startdisresult："+startdisresult);
     System.out.println("dealroute终点循环");
     startNode.name=endname;
     for (int i = t; i < route.getNumSteps(); i++) {
    	 
         MKStep step = route.getStep(i);
        endNode.pt=step.getPoint();
        type=3;
        searchModel.drivingSearch("天津", startNode, "天津", endNode);
        if(i==0)  enddisresult=enddis;
        if(enddis<=enddisresult){
        	enddisresult=enddis;
        	endrouteresult=endroute;
        	dispteresult=dispte;
        
        }
     }
     System.out.println("dealroute 终点 循环结果enddisresult："+enddisresult);
}

	

// 常用事件监听，用来处理通常的网络错误，授权验证错误等


}

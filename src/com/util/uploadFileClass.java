package com.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;




public final class uploadFileClass {
    @SuppressWarnings("unused")
	public String uploadFile(Object type,String srcPath){
    	/*srcPath= Environment.getExternalStorageDirectory().toString()
				+ "/MyVoice/1362913266985.amr";*/
    	 String uploadUrl = Connect.url+"upload";//+"?type="+type;
    	
    	   String end = "\r\n";
           String twoHyphens = "--";
           String boundary = "******";
           String result ="�ϴ�ʧ��";
           try
           {
             URL url = new URL(uploadUrl);
             HttpURLConnection httpURLConnection = (HttpURLConnection) url
                 .openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
             httpURLConnection.setUseCaches(false);
             httpURLConnection.setRequestMethod("POST");
             httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
             httpURLConnection.setRequestProperty("Charset", "UTF-8");
             httpURLConnection.setRequestProperty("Content-Type",
                 "multipart/form-data;boundary=" + boundary);

             DataOutputStream dos = new DataOutputStream(httpURLConnection
                 .getOutputStream());
             dos.writeBytes(twoHyphens + boundary + end);
             dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
                     + srcPath.substring(srcPath.lastIndexOf("/") + 1)
                     + "\"" + end);
             dos.writeBytes(end);
             byte[] buffer = new byte[8192]; // 8k
             int count = 0;
          
            	  FileInputStream fis = new FileInputStream(srcPath);
            while ((count = fis.read(buffer)) != -1)
            {
              dos.write(buffer, 0, count);

            }
            fis.close();
        
            
           

             dos.writeBytes(end);
             dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
             dos.flush();
             DataInputStream dos2=new DataInputStream(httpURLConnection.getInputStream()); 
            InputStream is2 = httpURLConnection.getInputStream();
            
             //Bitmap bm=BitmapFactory.decodeStream(is);
        /* FileOutputStream fos = new FileOutputStream( Environment.getExternalStorageDirectory().toString()+"/MyVoice/刘申.jpg");
   			byte[] buffer2 = new byte[8192]; // 每次�?K字节
   			int count2 = 0;
   			// �?��读取上传文件的字节，并将其输出到服务端的上传文件输出流中
   			while ((count2 = is2.read(buffer2)) > 0)
   			{
   				fos.write(buffer2, 0, count2); // 向客户端文件写入字节�?
   				
   			}
         */
              result ="�ϴ��ɹ�";

           
          /*   is2.close();
             fos.flush();
             fos.close();*/

           } catch (Exception e)
           {
           System.out.println("�ϴ�ʧ��");
             
           }
          return result;
         }
      
    //下载文件
    public String downloadfile(String path){
    	String result="����ʧ��";
    	  StringBuffer bf=new StringBuffer(Connect.url+"download?filename=");
		try {
			bf.append(URLEncoder.encode(path, "UTF-8"));
	
            URL url;
		     String name=path.substring(path.lastIndexOf("/"));
				url = new URL(bf.toString());
				HttpURLConnection httpURLConnection = (HttpURLConnection) url
				    .openConnection();
			    InputStream is2 = httpURLConnection.getInputStream();
		         boolean flag=false;
		         try {
		        	 File f=new File(Environment.getExternalStorageDirectory().toString()+"/Download"+name);
		         }
		         catch (Exception e) {
					// TODO: handle exception
		        	 flag=true;
				}
		         try {
		        	 File f=new File(Environment.getExternalStorageDirectory().toString()+"/Download");
		         }
		         catch (Exception e) {
					// TODO: handle exception
		        	 
				}
		         if(!flag){
		          //Bitmap bm=BitmapFactory.decodeStream(is);
		      FileOutputStream fos = new FileOutputStream( Environment.getExternalStorageDirectory().toString()+"/Download"+name);
					byte[] buffer2 = new byte[8192]; 
					int count2 = 0;
					// 
					while ((count2 = is2.read(buffer2)) > 0)
					{
						fos.write(buffer2, 0, count2); 
						
					}
		         }
		           result ="���سɹ�";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return result;
    }
    public Bitmap getdownloadBitmap(String path){
    	String name=path.substring(path.lastIndexOf("/")+1);
    	path=Environment.getExternalStorageDirectory().toString()+"/Download/"+name;
		return BitmapFactory.decodeFile(path);
    }
    public String getdownloadBitmappath(String path){
    	String name=path.substring(path.lastIndexOf("/")+1);
    	path=Environment.getExternalStorageDirectory().toString()+"/Download/"+name;
		return path;
    }
}

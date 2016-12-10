package com.thunder.ks3cloud;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ksyun.ks3.exception.Ks3Error;
import com.ksyun.ks3.model.acl.CannedAccessControlList;
import com.ksyun.ks3.model.result.GetObjectResult;
import com.ksyun.ks3.services.Ks3Client;
import com.ksyun.ks3.services.Ks3ClientConfiguration;
import com.ksyun.ks3.services.handler.DeleteObjectRequestHandler;
import com.ksyun.ks3.services.handler.GetObjectResponseHandler;
import com.ksyun.ks3.services.handler.PutObjectResponseHandler;
import com.ksyun.ks3.services.request.PutObjectRequest;


import org.apache.http.Header;

import java.io.File;
import java.io.IOException;


public class Ks3ClientHelper {

	public static Ks3Client client;
	private static Ks3ClientConfiguration configuration;
	private static Context mContext;

	private static Ks3ClientHelper instance = null;


	private Ks3ClientHelper(){

	}

	public static Ks3ClientHelper  getInstance(){

		if(null == instance){
			instance = new Ks3ClientHelper();
		}
		return instance;
	}

	//初始化
	public static void init(Context context) {
		client = new Ks3Client(Constants.ACCESS_KEY__ID, Constants.ACCESS_KEY_SECRET,
				context);
		mContext = context;
		client.setEndpoint(Constants.END_POINT);
		configuration = Ks3ClientConfiguration.getDefaultConfiguration();
		client.setConfiguration(configuration);

	}


	/**
	 *
	 * @param bucketName 金山云空间名，该项目默认: lsandroid
	 * @param fileName
     */

	//上传文件
	public void uploadObj(String bucketName, String fileName) {


		File f = new File(fileName);
		PutObjectRequest request = new PutObjectRequest(bucketName,
				"test/"+f.getName(), f);
		//设置公共
		request.setCannedAcl(CannedAccessControlList.PublicRead);
		client.putObject(request, new PutObjectResponseHandler() {

					@Override
					public void onTaskProgress(double arg0) {
						Log.i("KSYun-Progress", arg0+"");

					}

					@Override
					public void onTaskFailure(int i, Ks3Error ks3Error, Header[] headers, String s, Throwable throwable) {

						Log.i("KSYun-Failure", i+"");
					}

					@Override
					public void onTaskSuccess(int arg0, Header[] arg1) {

						Log.i("KSYun-Success", arg0+"");
						Toast.makeText(mContext, "success!", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onTaskStart() {

						Log.i("KSYun-Start", "start");

						Toast.makeText(mContext, "Upload start", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onTaskFinish() {

						Log.i("KSYun-Finish", "Finish");
						Toast.makeText(mContext, "finish!", Toast.LENGTH_SHORT).show();
					}


					@Override
					public void onTaskCancel() {

						Log.i("KSYun-Cancel", "Cancel");
					}
				});
	}

	//下载文件
	public void downloadObj(String bucketName, String objectKey,
								   String fileName) {


		File file = new File(fileName);
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		client.getObject(mContext, bucketName, objectKey,
				new GetObjectResponseHandler(file, bucketName,
						objectKey) {

					@Override
					public void onTaskSuccess(int arg0, Header[] arg1,
											  GetObjectResult arg2) {
						// TODO Auto-generated method stub
						Log.i("KSYun-Success", arg0 + "");
						Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onTaskFailure(int i, Ks3Error ks3Error, Header[] headers, Throwable throwable, File file) {

						Log.i("KSYun-Failure", i+"");
					}

					@Override
					public void onTaskStart() {
						// TODO Auto-generated method stub
						Log.i("KSYun-Start", "start");
						Toast.makeText(mContext, "Download begin", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onTaskProgress(double arg0) {
						// TODO Auto-generated method stub
						Log.i("KSYun-Progress", arg0 + "");
					}

					@Override
					public void onTaskFinish() {
						// TODO Auto-generated method stub
						Log.i("KSYun-Finish", "Finish");
						Toast.makeText(mContext, "finish!", Toast.LENGTH_SHORT).show();
					}


					@Override
					public void onTaskCancel() {
						// TODO Auto-generated method stub
						Log.i("KSYun-Cancel", "Cancel");
					}
				}
		);

	}

	//删除文件
	public void deleteObj(String bucketName, String objectKey) {
		client.deleteObject(bucketName, objectKey,
				new DeleteObjectRequestHandler() {

					@Override
					public void onFailure(int i, Ks3Error ks3Error, Header[] headers, String s, Throwable throwable) {

					}

					@Override
					public void onSuccess(int arg0, Header[] arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
					}

				});
	}




}

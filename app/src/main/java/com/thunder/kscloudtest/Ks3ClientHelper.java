package com.thunder.kscloudtest;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ksyun.ks3.exception.Ks3Error;
import com.ksyun.ks3.model.PartETag;
import com.ksyun.ks3.model.acl.CannedAccessControlList;
import com.ksyun.ks3.model.result.CompleteMultipartUploadResult;
import com.ksyun.ks3.model.result.GetObjectResult;
import com.ksyun.ks3.model.result.InitiateMultipartUploadResult;
import com.ksyun.ks3.model.result.ListPartsResult;
import com.ksyun.ks3.services.Ks3Client;
import com.ksyun.ks3.services.Ks3ClientConfiguration;
import com.ksyun.ks3.services.handler.CompleteMultipartUploadResponseHandler;
import com.ksyun.ks3.services.handler.CreateBucketResponceHandler;
import com.ksyun.ks3.services.handler.DeleteBucketResponceHandler;
import com.ksyun.ks3.services.handler.DeleteObjectRequestHandler;
import com.ksyun.ks3.services.handler.GetObjectResponseHandler;
import com.ksyun.ks3.services.handler.InitiateMultipartUploadResponceHandler;
import com.ksyun.ks3.services.handler.ListPartsResponseHandler;
import com.ksyun.ks3.services.handler.PutObjectResponseHandler;
import com.ksyun.ks3.services.handler.UploadPartResponceHandler;
import com.ksyun.ks3.services.request.CompleteMultipartUploadRequest;
import com.ksyun.ks3.services.request.InitiateMultipartUploadRequest;
import com.ksyun.ks3.services.request.PutObjectRequest;
import com.ksyun.ks3.services.request.UploadPartRequest;

import org.apache.http.Header;

import static com.thunder.kscloudtest.Ks3ClientHelper.client;


public class Ks3ClientHelper {

	public static Ks3Client client;

	private static Ks3ClientConfiguration configuration;
	private static Context _context;
	public UploadPartHandler handler;

	//初始化
	public static void Init(Context context) {
		client = new Ks3Client(Constants.ACCESS_KEY__ID, Constants.ACCESS_KEY_SECRET,
				context);
		_context = context;
		client.setEndpoint("ks3-cn-beijing.ksyun.com");
		configuration = Ks3ClientConfiguration.getDefaultConfiguration();
		client.setConfiguration(configuration);
	}



	//上传文件
	public static void uploadObj(String bucketName, String fileName) {


		File f = new File(fileName);
		PutObjectRequest request = new PutObjectRequest(bucketName,
				"test/"+f.getName(), f);
		//设置公共
		request.setCannedAcl(CannedAccessControlList.PublicRead);
		client.putObject(request, new PutObjectResponseHandler() {

					@Override
					public void onTaskProgress(double arg0) {
						// TODO Auto-generated method stub

						Log.i("KSYun-Progress", arg0+"");

					}

					@Override
					public void onTaskFailure(int i, Ks3Error ks3Error, Header[] headers, String s, Throwable throwable) {

						Log.i("KSYun-Failure", i+"");
					}

					@Override
					public void onTaskSuccess(int arg0, Header[] arg1) {
						// TODO Auto-generated method stub
						Log.i("KSYun-Success", arg0+"");
						Toast.makeText(_context, "success!", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onTaskStart() {
						// TODO Auto-generated method stub
						Log.i("KSYun-Start", "start");

						Toast.makeText(_context, "Upload start", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onTaskFinish() {
						// TODO Auto-generated method stub
						Log.i("KSYun-Finish", "Finish");
						Toast.makeText(_context, "finish!", Toast.LENGTH_SHORT).show();
					}


					@Override
					public void onTaskCancel() {
						// TODO Auto-generated method stub
						Log.i("KSYun-Cancel", "Cancel");
					}
				});
	}

	// 分快上传
	private void doMultipartUpload( String bucketName,
								   String fileName) {

		handler = new UploadPartHandler();
		client.initiateMultipartUpload(new InitiateMultipartUploadRequest(
						bucketName, fileName),
				new InitiateMultipartUploadResponceHandler() {

					@Override
					public void onFailure(int i, Ks3Error ks3Error, Header[] headers, String s, Throwable throwable) {

					}

					@Override
					public void onSuccess(int statesCode,
										  Header[] responceHeaders,
										  InitiateMultipartUploadResult result) {
						String uploadId = result.getUploadId();
						handler.sendEmptyMessage(1);


					}
				});
		}


	class UploadPartHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what){
				case 0:

					break;
				case 1:
					break;
				case 2:
					break;
				case -1:
					break;
			}

		}
	}


	//下载文件
	public static void downloadObj(String bucketName, String objectKey,
								   String fileName) {


		File file = new File(fileName);
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		client.getObject(_context, bucketName, objectKey,
				new GetObjectResponseHandler(file, bucketName,
						objectKey) {

					@Override
					public void onTaskSuccess(int arg0, Header[] arg1,
											  GetObjectResult arg2) {
						// TODO Auto-generated method stub
						Log.i("KSYun-Success", arg0 + "");
						Toast.makeText(_context, "success", Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onTaskFailure(int i, Ks3Error ks3Error, Header[] headers, Throwable throwable, File file) {

						Log.i("KSYun-Failure", i+"");
					}

					@Override
					public void onTaskStart() {
						// TODO Auto-generated method stub
						Log.i("KSYun-Start", "start");
						Toast.makeText(_context, "Download begin", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(_context, "finish!", Toast.LENGTH_SHORT).show();
					}


					@Override
					public void onTaskCancel() {
						// TODO Auto-generated method stub
						Log.i("KSYun-Cancel", "Cancel");
					}
				}
		);

	}

	//删除 文件
	public static void deleteObj(String bucketName, String objectKey) {
		client.deleteObject(bucketName, objectKey,
				new DeleteObjectRequestHandler() {

					@Override
					public void onFailure(int i, Ks3Error ks3Error, Header[] headers, String s, Throwable throwable) {

					}

					@Override
					public void onSuccess(int arg0, Header[] arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(_context, "success", Toast.LENGTH_SHORT).show();
					}

				});
	}

	//创建空间
	public static void createBucket(String bucketName){
		client.createBucket(bucketName, new CreateBucketResponceHandler() {

			@Override
			public void onFailure(int i, Ks3Error ks3Error, Header[] headers, String s, Throwable throwable) {

			}

			@Override
			public void onSuccess(int arg0, Header[] arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(_context, "success!", Toast.LENGTH_SHORT).show();
			}

		});
	}

	//删除空间
	public static void deleteBucket(String bucketName) {
		client.deleteBucket(bucketName, new DeleteBucketResponceHandler() {
			@Override
			public void onFailure(int i, Ks3Error ks3Error, Header[] headers, String s, Throwable throwable) {

			}

			@Override
			public void onSuccess(int arg0, Header[] arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(_context, "success", Toast.LENGTH_SHORT).show();
			}

		});
	}


}


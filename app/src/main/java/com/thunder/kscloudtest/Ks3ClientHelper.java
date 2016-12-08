package com.thunder.kscloudtest;

import java.io.File;

import android.content.Context;
import android.widget.Toast;

import com.ksyun.ks3.exception.Ks3Error;
import com.ksyun.ks3.model.result.GetObjectResult;
import com.ksyun.ks3.services.Ks3Client;
import com.ksyun.ks3.services.Ks3ClientConfiguration;
import com.ksyun.ks3.services.handler.CreateBucketResponceHandler;
import com.ksyun.ks3.services.handler.DeleteBucketResponceHandler;
import com.ksyun.ks3.services.handler.DeleteObjectRequestHandler;
import com.ksyun.ks3.services.handler.GetObjectResponseHandler;
import com.ksyun.ks3.services.handler.PutObjectResponseHandler;

import org.apache.http.Header;


public class Ks3ClientHelper {

	public static Ks3Client client;

	private static Ks3ClientConfiguration configuration;
	private static Context _context;

	//初始化
	public static void Init(Context context) {
		client = new Ks3Client("73OBWSCOKC5CJVXQG73A", "X2MJIHrX3zjoQ2QO4ZOuhmU5mGk9dEJGqeb1e2D+",
				context);
		_context = context;
		configuration = Ks3ClientConfiguration.getDefaultConfiguration();
		client.setConfiguration(configuration);
	}



	//上传文件
	public static void UploadObj(String bucketName, String fileName) {

		File f = new File(fileName);
		client.putObject(bucketName, f.getName(), f,
				new PutObjectResponseHandler() {

					@Override
					public void onTaskProgress(double arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onTaskFailure(int i, Ks3Error ks3Error, Header[] headers, String s, Throwable throwable) {

					}

					@Override
					public void onTaskSuccess(int arg0, Header[] arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(_context, "success!", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onTaskStart() {
						// TODO Auto-generated method stub
						Toast.makeText(_context, "Upload begin", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onTaskFinish() {
						// TODO Auto-generated method stub
						Toast.makeText(_context, "finish!", Toast.LENGTH_SHORT).show();
					}


					@Override
					public void onTaskCancel() {
						// TODO Auto-generated method stub

					}
				});
	}

	//下载文件
	public static void DownloadObj(String bucketName, String objectKey,
								   String fileName) {
		client.getObject(_context, bucketName, objectKey,
				new GetObjectResponseHandler(new File(fileName), bucketName,
						objectKey) {

					@Override
					public void onTaskSuccess(int arg0, Header[] arg1,
											  GetObjectResult arg2) {
						// TODO Auto-generated method stub
						Toast.makeText(_context, "success", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onTaskFailure(int i, Ks3Error ks3Error, Header[] headers, Throwable throwable, File file) {

					}

					@Override
					public void onTaskStart() {
						// TODO Auto-generated method stub
						Toast.makeText(_context, "Download begin", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onTaskProgress(double arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onTaskFinish() {
						// TODO Auto-generated method stub
						Toast.makeText(_context, "finish!", Toast.LENGTH_SHORT).show();
					}


					@Override
					public void onTaskCancel() {
						// TODO Auto-generated method stub

					}
				}
		);
	}

	//删除 文件
	public static void DeleteObj(String bucketName, String objectKey) {
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
	public static void CreateBucket(String bucketName){
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
	public static void DeleteBucket(String bucketName) {
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

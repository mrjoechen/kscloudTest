package com.thunder.kscloudtest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.apache.http.Header;
import com.ksyun.ks3.services.Ks3Client;
import com.ksyun.ks3.services.Ks3ClientConfiguration;
import com.ksyun.ks3.services.request.GetObjectRequest;

import java.io.File;

import static com.thunder.kscloudtest.Ks3ClientHelper.client;

public class DownloadActivity extends AppCompatActivity {


    private Ks3ClientConfiguration configuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        // 初始化Ks3Client
        configuration = Ks3ClientConfiguration.getDefaultConfiguration();
        client = new Ks3Client(Constants.ACCESS_KEY__ID,
                Constants.ACCESS_KEY_SECRET, DownloadActivity.this);
        //如果用户需要通过自己的域名上传，可以将Endpoint设置成自己域名
        //configuration.setDomainMode(true);
        //client.setEndpoint("***.***.****");

        client.setEndpoint("ks3-cn-shanghai.ksyun.com");
        client.setConfiguration(configuration);

//        client.getObject(DummyActivity.this,Constants.BucketName,Constants.ObjectKey,
//                new GetObjectResponceHandler(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),Constants.ObjectKey), Constants.BucketName,Constants.ObjectKey) {
//
//                    @Override
//                    public void onTaskSuccess(int statesCode, Header[] responceHeaders) {
//                    }
//
//                    @Override
//                    public void onTaskFailure(int statesCode, Header[] responceHeaders,
//                                              String response, Throwable throwable) {
//                    }
//
//                    @Override
//                    public  void onTaskFinish(){
//
//                    }
//
//                    @Override
//                    public  void onTaskStart(){
//
//                    }
//
//                    @Override
//                    public void onTaskProgress(double progress){
//                        //运行在非UI线程，更新UI时需要注意
//                        //Progress为0-100之间的double类型数值
//                    }
//                }
//        );
//
//
//        client.getObject(new GetObjectRequest(bucketname, key),
//                new GetObjectResponceHandler(new File(Environment
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                        Constants.ObjectKey), Constants.BucketName,
//                        Constants.ObjectKey) {
//
//                    @Override
//                    public void onTaskSuccess(int statesCode, Header[] responceHeaders) {
//                    }
//
//                    @Override
//                    public void onTaskFailure(int statesCode, Header[] responceHeaders,
//                                              String response, Throwable throwable) {
//                    }
//
//                    @Override
//                    public  void onTaskFinish(){
//
//                    }
//
//                    @Override
//                    public  void onTaskStart(){
//
//                    }
//
//                    @Override
//                    public void onTaskProgress(double progress){
//                        //运行在非UI线程，更新UI时需要注意
//                        //Progress为0-100之间的double类型数值
//                    }
//                }
//        );
//
//
//        // 下载操作示例
//        final GetObjectRequest request = new GetObjectRequest(
//                item.bucketName, item.objectKey);
//        String objectName = item.objectKey.substring(item.objectKey
//                .lastIndexOf("/") == -1 ? 0 : item.objectKey
//                .lastIndexOf("/"));
////			request.setCallBack(callBackUrl, callBackBody, callBackHeaders);
//        File file = new File(storeForder, objectName);
//        client.getObject(request, new GetObjectResponseHandler(file,
//                item.bucketName, item.objectKey) {
//
//            @Override
//            public void onTaskSuccess(int paramInt,
//                                      Header[] paramArrayOfHeader,
//                                      GetObjectResult getObjectResult) {
//
//            }
//
//            @Override
//            public void onTaskStart() {
//                RemoteFile remoteFile = dataSource.get(item.objectKey);
//                remoteFile.status = RemoteFile.STATUS_STARTED;
//                remoteFile.progress = 0;
//                item.status = RemoteFile.STATUS_STARTED;
//                item.progress = 0;
//                mHandler.sendEmptyMessage(0);
//
//            }
//
//            @Override
//            public void onTaskProgress(double progress) {
////					if (progress > 50.0) {
////						request.abort();
////					}
//                RemoteFile remoteFile = dataSource.get(item.objectKey);
//                remoteFile.status = RemoteFile.STATUS_DOWNLOADING;
//                remoteFile.progress = (int) progress;
//                item.status = RemoteFile.STATUS_DOWNLOADING;
//                item.progress = (int) progress;
//                mHandler.sendEmptyMessage(0);
//            }
//
//            @Override
//            public void onTaskFinish() {
//                RemoteFile remoteFile = dataSource.get(item.objectKey);
//                remoteFile.status = RemoteFile.STATUS_FINISH;
//                remoteFile.progress = 100;
//                item.status = RemoteFile.STATUS_FINISH;
//                item.progress = 100;
//                mHandler.sendEmptyMessage(0);
//            }
//
//            @Override
//            public void onTaskCancel() {
//                Log.d(com.ksyun.ks3.util.Constants.LOG_TAG, "cancle ok");
//            }
//
//            @Override
//            public void onTaskFailure(int paramInt, Ks3Error ks3Error,
//                                      Header[] paramArrayOfHeader, Throwable paramThrowable,
//                                      File paramFile) {
//                Log.d(com.ksyun.ks3.util.Constants.LOG_TAG,
//                        paramInt+"failure: reason = " + paramThrowable.toString()+"/n"+"response:"+ks3Error.getErrorMessage());
//                RemoteFile remoteFile = dataSource.get(item.objectKey);
//                remoteFile.status = RemoteFile.STATUS_FAIL;
//                item.status = RemoteFile.STATUS_FAIL;
//                mHandler.sendEmptyMessage(0);
//            }
//        });
    }


}

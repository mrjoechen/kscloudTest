package com.thunder.kscloudtest;

import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.Header;

import com.ksyun.ks3.exception.Ks3Error;
import com.ksyun.ks3.model.result.GetObjectResult;
import com.ksyun.ks3.services.Ks3Client;
import com.ksyun.ks3.services.Ks3ClientConfiguration;
import com.ksyun.ks3.services.handler.GetObjectResponseHandler;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.thunder.kscloudtest.Ks3ClientHelper.client;

public class DownloadActivity extends AppCompatActivity {
    private File storeForder;

    private Ks3ClientConfiguration configuration;
    private File file;

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
        prepareStoreForder();
        client.setEndpoint("ks3-cn-beijing.ksyun.com");
//        client.setEndpoint("ks3-cn-shanghai.ksyun.com");
        client.setConfiguration(configuration);


        String bucketName = "lsandroid";
        String objectKey = "test/1.jpg";

//        String bucketName = "app123";
//        String objectKey = "1.jpg";


        DownloadObj(bucketName, objectKey, "test.jpg");

//        final GetObjectRequest request = new GetObjectRequest(bucketName, objectKey);

//        client.getObject(DownloadActivity.this,"publish","ceshi.png",
//        client.getObject(request,
//                new GetObjectResponseHandler(file, bucketName, objectKey) {
//
//
//                    @Override
//                    public  void onTaskFinish(){
//
//                    }
//
//                    @Override
//                    public void onTaskCancel() {
//
//                    }
//
//                    @Override
//                    public void onTaskSuccess(int i, Header[] headers, GetObjectResult getObjectResult) {
//                        Log.i("KSYun", "state code : " + i);
//                        Log.i("KSYun", getObjectResult.getObject().getFile().getPath());
//                        Log.i("KSYun", "" + getObjectResult.getObject().getFile().length());
//                        Toast.makeText(DownloadActivity.this,"下载成功"+Environment.getExternalStorageDirectory(),Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onTaskFailure(int i, Ks3Error ks3Error, Header[] headers, Throwable throwable, File file) {
//                        Log.i("KSYun", "state code : " + i);
//                        Toast.makeText(DownloadActivity.this,"下载失败",Toast.LENGTH_LONG).show();
//
//                        Toast.makeText(DownloadActivity.this,throwable.getMessage(),Toast.LENGTH_LONG).show();
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
//
//                        Log.i("KSYun","progress :"+progress);
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

    //下载文件
    public void DownloadObj(String bucketName, String objectKey, String fileName) {


        file = new File(storeForder, fileName);



//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectKey);
//
//        client.getObject(getObjectRequest, new GetObjectResponseHandler(file, bucketName,
//                objectKey) {
//
//            @Override
//            public void onTaskSuccess(int arg0, Header[] arg1,
//                                      GetObjectResult arg2) {
//                // TODO Auto-generated method stub
//                Toast.makeText(DownloadActivity.this, "success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTaskFailure(int i, Ks3Error ks3Error, Header[] headers, Throwable throwable, File file) {
//
//            }
//
//            @Override
//            public void onTaskStart() {
//                // TODO Auto-generated method stub
//                Toast.makeText(DownloadActivity.this, "Download begin", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTaskProgress(double arg0) {
//                // TODO Auto-generated method stub
//                Log.i("KSYun", "progress :" + arg0);
//
//            }
//
//            @Override
//            public void onTaskFinish() {
//                // TODO Auto-generated method stub
//                Toast.makeText(DownloadActivity.this, "finish!", Toast.LENGTH_SHORT).show();
//                DownloadActivity.this.finish();
//            }
//
//
//            @Override
//            public void onTaskCancel() {
//                // TODO Auto-generated method stub
//
//            }
//        });

        client.getObject(DownloadActivity.this, bucketName, objectKey,
                new GetObjectResponseHandler(file, bucketName,
                        objectKey) {

                    @Override
                    public void onTaskSuccess(int arg0, Header[] arg1,
                                              GetObjectResult arg2) {
                        // TODO Auto-generated method stub
                        Toast.makeText(DownloadActivity.this, "success", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onTaskFailure(int i, Ks3Error ks3Error, Header[] headers, Throwable throwable, File file) {

                    }

                    @Override
                    public void onTaskStart() {
                        // TODO Auto-generated method stub
                        Toast.makeText(DownloadActivity.this, "Download begin", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTaskProgress(double arg0) {
                        // TODO Auto-generated method stub
                        Log.i("KSYun", "progress :" + arg0);

                    }

                    @Override
                    public void onTaskFinish() {
                        // TODO Auto-generated method stub
                        Toast.makeText(DownloadActivity.this, "finish!", Toast.LENGTH_SHORT).show();
                        DownloadActivity.this.finish();
                    }


                    @Override
                    public void onTaskCancel() {
                        // TODO Auto-generated method stub

                    }
                }
        );



//        String url = result.getUrl();
//        Log.i("KSYun-URL",url);
//        //创建异步的httpclient对象AsyncHttpClient
//        AsyncHttpClient ahc = new AsyncHttpClient();
//        //发送get请求
//        ahc.get(url, new MyHandler());



//        GetObjectResult result = client.getObject(request);
//
//        Ks3Object object = result.getObject();
//        //获取object的元数据
//        ObjectMetadata meta = object.getObjectMetadata();
//        //当分块下载时获取文件的实际大小，而非当前小块的大小
//        Long length = meta.getInstanceLength();
//        //获取object的输入流
//        object.getObjectContent();

    }


    class MyHandler extends AsyncHttpResponseHandler {
        //http请求成功，返回码为200，系统回调此方法
        @Override
        //responseBody的内容就是服务器返回的数据
        public void onSuccess(int statusCode, Header[] headers,byte[] responseBody) {


            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(responseBody);

                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers,byte[] responseBody, Throwable error) {
            Log.i("=====","==========");
            error.printStackTrace();
            Toast.makeText(DownloadActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            Log.i("=====","==========");

        }
    }
    private void prepareStoreForder() {


        storeForder = new File(getExtSDCardPath(),
                "ksyun_download");
        if (!storeForder.exists()) {
            storeForder.mkdirs();
        } else if (storeForder.isFile()) {
            storeForder.delete();
        }
    }

    /**
     * 获取外置SD卡路径以及TF卡的路径
     * <p>
     * 返回的数据：paths.get(0)肯定是外置SD卡的位置，因为它是primary external storage.
     *
     * @return 所有可用于存储的不同的卡的位置，用一个List来保存
     */
    public static String getExtSDCardPath() {
        List<String> paths = new ArrayList<String>();
        String extFileStatus = Environment.getExternalStorageState();
        File extFile = Environment.getExternalStorageDirectory();
        //首先判断一下外置SD卡的状态，处于挂载状态才能获取的到
        if (extFileStatus.equals(Environment.MEDIA_MOUNTED)
                && extFile.exists() && extFile.isDirectory()
                && extFile.canWrite()) {
            //外置SD卡的路径
            paths.add(extFile.getAbsolutePath());
        }
        try {
            // obtain executed result of command line code of 'mount', to judge
            // whether tfCard exists by the result
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int mountPathIndex = 1;
            while ((line = br.readLine()) != null) {
                // format of sdcard file system: vfat/fuse
//                Log.i("KSYun",line);
                if ((!line.contains("fat") && !line.contains("fuse") && !line
                        .contains("storage")&& !line.contains("mnt"))
                        || line.contains("secure")
                        || line.contains("asec")
                        || line.contains("firmware")
                        || line.contains("shell")
                        || line.contains("obb")
                        || line.contains("legacy") ) {
                    continue;
                }
                String[] parts = line.split(" ");
                int length = parts.length;
                if (mountPathIndex >= length) {
                    continue;
                }
                String mountPath = parts[mountPathIndex];
                if (!mountPath.contains("/") || mountPath.contains("data")
                        || mountPath.contains("Data")) {
                    continue;
                }
                File mountRoot = new File(mountPath);
                if (!mountRoot.exists() || !mountRoot.isDirectory()
                        || !mountRoot.canWrite()) {
                    continue;
                }
                boolean equalsToPrimarySD = mountPath.equals(extFile
                        .getAbsolutePath());
                if (equalsToPrimarySD) {
                    continue;
                }
                //扩展存储卡即TF卡或者SD卡路径
                paths.add(mountPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取最大容量的sd卡路径
        long size = 0;
        String path = "";

        for (int i=0;i<paths.size();i++) {

            if (getFreeBytes(paths.get(i))/(1024*1024)>size){
                size = getFreeBytes(paths.get(i))/(1024*1024);
                path = paths.get(i);
            }

            Log.i("KSYun", paths.get(i)+"="+getFreeBytes(paths.get(i))/(1024*1024));
        }
        return path;
    }


    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {

        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
        // int freeRoot = (int) (SDCardUtils.getFreeBytes(sdpath) / (1024 * 1024)); ---> 获取的是M为单位
    }
}

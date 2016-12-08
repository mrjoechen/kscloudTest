package com.thunder.kscloudtest;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import static com.thunder.kscloudtest.Ks3ClientHelper.client;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ks3ClientHelper.Init(MainActivity.this);



        findViewById(R.id.tv_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_upload = new Intent(MainActivity.this,
                        UploadActivity.class);
                startActivity(intent_upload);
            }
        });


        findViewById(R.id.tv_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_download = new Intent(MainActivity.this,
                        DownloadActivity.class);
                startActivity(intent_download);

            }
        });
    }

    private String _bucketName;
    private String _objectKey;
    private String _fileName;



    //上传文件
    public void UpLoad(String bucketName,String fileName){
        _bucketName = bucketName;
        _fileName = fileName;
        runOnUiThread(new Runnable(){

            @Override
            public void run() {
                Ks3ClientHelper.UploadObj(_bucketName, _fileName);

            }

        });

    }

    //下载文件
    public void Download(String bucketName, String objectKey,String fileName){
        _bucketName = bucketName;
        _objectKey = objectKey;
        _fileName = fileName;

        runOnUiThread(new Runnable(){

            @Override
            public void run() {
                Ks3ClientHelper.DownloadObj(_bucketName, _objectKey, _fileName);

            }

        });

    }

    //删除文件
    public void Delete(String bucketName,String objectKey){
        _bucketName = bucketName;
        _objectKey = objectKey;

        runOnUiThread(new Runnable(){

            @Override
            public void run() {
                Ks3ClientHelper.DeleteObj(_bucketName, _objectKey);

            }

        });

    }

    //创建空间
    public void CreateBucket(String bucketName){
        _bucketName = bucketName;

        runOnUiThread(new Runnable(){

            @Override
            public void run() {
                Ks3ClientHelper.CreateBucket(_bucketName);

            }


        });
    }

    //删除空间
    public void DeleteBucket(String bucketName){
        _bucketName = bucketName;

        runOnUiThread(new Runnable(){

            @Override
            public void run() {
                Ks3ClientHelper.DeleteBucket(_bucketName);

            }

        });

    }

}




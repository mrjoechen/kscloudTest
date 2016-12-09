package com.thunder.kscloudtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


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


        findViewById(R.id.tv_getobjacl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_getobjacl = new Intent(MainActivity.this,
                        GetObjectAclActivity.class);
                startActivity(intent_getobjacl);

            }
        });
    }

    private String _bucketName;
    private String _objectKey;
    private String _fileName;


}




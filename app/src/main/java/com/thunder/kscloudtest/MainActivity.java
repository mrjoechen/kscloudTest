package com.thunder.kscloudtest;

import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView tv_content;

    private String mBucketName = "lsandroid";
    private String mObjectKey = "";
    private String mFileName = Environment.getExternalStorageDirectory()+"/q1.jpg";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ks3ClientHelper.Init(MainActivity.this);

        tv_content = (TextView) findViewById(R.id.tv_content);

        findViewById(R.id.tv_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent_upload = new Intent(MainActivity.this, UploadActivity.class);
//                startActivity(intent_upload);

                Log.i("KSYun","upload");
                Ks3ClientHelper.uploadObj(mBucketName, mFileName);

            }
        });


        findViewById(R.id.tv_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent_download = new Intent(MainActivity.this, DownloadActivity.class);
//                startActivity(intent_download);

                File storeForder = new File(getExtSDCardPath(),
                        "ksyun_download");
                if (!storeForder.exists()) {
                    storeForder.mkdirs();
                } else if (storeForder.isFile()) {
                    storeForder.delete();
                }
                Ks3ClientHelper.downloadObj(mBucketName,"test/q1.jpg", storeForder.getAbsolutePath()+"/q1.jpg");
            }
        });


        findViewById(R.id.tv_getobjacl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent_getobjacl = new Intent(MainActivity.this, GetObjectAclActivity.class);
//                startActivity(intent_getobjacl);

            }
        });
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
        long availableBlocks = (long) stat.getAvailableBlocks();
        return stat.getBlockSize() * availableBlocks;
        // int freeRoot = (int) (SDCardUtils.getFreeBytes(sdpath) / (1024 * 1024)); ---> 获取的是M为单位
    }


}




package com.thunder.kscloudtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.apache.http.Header;

import com.ksyun.ks3.exception.Ks3Error;
import com.ksyun.ks3.model.Owner;
import com.ksyun.ks3.model.acl.AccessControlPolicy;
import com.ksyun.ks3.model.acl.Grant;
import com.ksyun.ks3.services.Ks3Client;
import com.ksyun.ks3.services.Ks3ClientConfiguration;
import com.ksyun.ks3.services.handler.GetObjectACLResponseHandler;

import java.util.HashSet;

public class GetObjectAclActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_object_acl);
        bucketObjectInpuDialog = new BucketObjectInpuDialog(GetObjectAclActivity.this);
        client = new Ks3Client(Constants.ACCESS_KEY__ID,
                Constants.ACCESS_KEY_SECRET, GetObjectAclActivity.this);
        client.setEndpoint("ks3-cn-beijing.ksyun.com");
//        client.setEndpoint("ks3-cn-shanghai.ksyun.com");
        configuration = Ks3ClientConfiguration.getDefaultConfiguration();
        client.setConfiguration(configuration);
        getObjectACL();


    }
    private static final String API = "api";
    private static final String RESULT = "result";


    private BucketObjectInpuDialog bucketObjectInpuDialog;
    private Ks3ClientConfiguration configuration;
    private Ks3Client client;


    private void getObjectACL() {
        bucketObjectInpuDialog
                .setOnBucketObjectDialogListener(new BucketObjectInpuDialog.OnBucketObjectDialogListener() {
                    @Override
                    public void confirmBucketAndObject(String name, String key) {
                        client.getObjectACL(name, key,
                                new GetObjectACLResponseHandler() {

                                    @Override
                                    public void onSuccess(
                                            int statesCode,
                                            Header[] responceHeaders,
                                            AccessControlPolicy accessControlPolicy) {
                                        StringBuffer stringBuffer = new StringBuffer();
                                        Owner owner = accessControlPolicy
                                                .getOwner();
                                        stringBuffer
                                                .append("=======Owner : ID "
                                                        + owner.getId()
                                                        + " ; NAME :"
                                                        + owner.getDisplayName())
                                                .append("\n");
                                        stringBuffer
                                                .append("==============ACL LIST=========");
                                        HashSet<Grant> grants = accessControlPolicy
                                                .getAccessControlList()
                                                .getGrants();
                                        for (Grant grant : grants) {
                                            stringBuffer
                                                    .append(grant.getGrantee()
                                                            .getIdentifier()
                                                            + "========>"
                                                            + grant.getPermission()
                                                            .toString())
                                                    .append("\n");
                                        }
                                        Intent intent = new Intent(
                                                GetObjectAclActivity.this,
                                                RESTAPITestResult.class);

                                        Bundle data = new Bundle();
                                        data.putString(RESULT,
                                                stringBuffer.toString());
                                        data.putString(API,
                                                "head object Result");
                                        intent.putExtras(data);
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onFailure(int statesCode,
                                                          Ks3Error error,
                                                          Header[] responceHeaders,
                                                          String response,
                                                          Throwable paramThrowable) {
                                        StringBuffer stringBuffer = new StringBuffer();
                                        stringBuffer.append(
                                                "get object ACL FAIL !!!!!!, states code :"
                                                        + statesCode).append(
                                                "\n").append("response:").append(response);
                                        stringBuffer.append("Exception :"
                                                + paramThrowable.toString());
                                        Intent intent = new Intent(
                                                GetObjectAclActivity.this,
                                                RESTAPITestResult.class);
                                        Bundle data = new Bundle();
                                        data.putString(RESULT,
                                                stringBuffer.toString());
                                        data.putString(API,
                                                "GET Object ACL Result");
                                        intent.putExtras(data);
                                        startActivity(intent);
                                    }
                                });

                    }
                });
        bucketObjectInpuDialog.show();
    }
}

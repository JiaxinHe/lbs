package app.lbs.com.lbsapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.api.Constant;
import app.lbs.com.lbsapp.utils.SharedPreferencesUtil;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;

/**
 * 一键求助界面
 */
public class SafeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
//    private LocationClient locationClient;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        button = findViewById(R.id.safe_btn);
        button.setOnClickListener(this);

        username = SharedPreferencesUtil.getStringValue(SafeActivity.this, "username");

//        LocationClientOption option = new LocationClientOption();
//        option.setIsNeedAddress(true);
//        locationClient = new LocationClient(this);
//        locationClient.setLocOption(option);
//        locationClient.registerLocationListener(new BDLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                String address = bdLocation.getAddrStr();
//                Log.e("address", "address: " + address);
//                String msg = "用户【" + username + "】向你发出求助\n位置：" + address;
//                SmsManager.getDefault()
//                        .sendTextMessage(Constant.HOSPITAL_PHONE, null, msg, null, null);
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.safe_btn:
                callForHelp();
                break;
        }
    }

    private void callForHelp() {
        sendSms();
        callPhone();
    }

    private void sendSms() {
//        locationClient.start();
    }


    /**
     * 拨打求助电话
     */
    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + Constant.HOSPITAL_PHONE);
        intent.setData(uri);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

}

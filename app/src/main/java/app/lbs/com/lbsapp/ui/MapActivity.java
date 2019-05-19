package app.lbs.com.lbsapp.ui;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.baidu.mapapi.map.MapView;

import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.MapView;
import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.TrackApplication;
import app.lbs.com.lbsapp.base.BaseActivity;
import app.lbs.com.lbsapp.utils.Constants;
import app.lbs.com.lbsapp.utils.MapUtil;
import app.lbs.com.lbsapp.utils.ViewUtil;
public class MapActivity  extends BaseActivity implements View.OnClickListener {

    private int mCurrentDirection = 0;
    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;

    private TrackApplication trackApp = null;

    private ViewUtil viewUtil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.tracing_title);
        setOnClickListener(this);
        init();
    }
    private void init() {
        trackApp = (TrackApplication) getApplicationContext();
        viewUtil = new ViewUtil();
        mapUtil = MapUtil.getInstance();
        mapUtil.init((MapView) findViewById(R.id.tracing_mapView));
        mapUtil.setCenter(mCurrentDirection);//设置地图中心点
    }


    @Override
    public void onClick(View view) {}

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapUtil.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapUtil.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapUtil.clear();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_map;
    }
}
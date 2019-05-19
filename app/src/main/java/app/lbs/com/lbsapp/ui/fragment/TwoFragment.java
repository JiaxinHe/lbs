package app.lbs.com.lbsapp.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.TrackApplication;
import app.lbs.com.lbsapp.base.BaseFragment;
import app.lbs.com.lbsapp.ui.MapActivity;
import app.lbs.com.lbsapp.ui.TracingActivity;
import app.lbs.com.lbsapp.ui.TrackQueryActivity;


public class TwoFragment extends BaseFragment {

    private TrackApplication trackApp;

    private SDKReceiver mReceiver;

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();

            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                Toast.makeText(getActivity(),"apikey验证失败，地图功能无法正常使用",Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                Toast.makeText(getActivity(),"apikey验证成功",Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(getActivity(),"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // apikey的授权需要一定的时间，在授权成功之前地图相关操作会出现异常；apikey授权成功后会发送广播通知，我们这里注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        getActivity().registerReceiver(mReceiver, iFilter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
//        BitmapUtil.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        Button button1 =(Button)view.findViewById(R.id.tv_tracing);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), TracingActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "轨迹时时追踪", Toast.LENGTH_LONG).show();
            }
        });
        Button button2 =(Button)view.findViewById(R.id.tv_query);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), TrackQueryActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "历史轨迹查询", Toast.LENGTH_LONG).show();
            }
        });
        Button button3 =(Button)view.findViewById(R.id.tv_map);
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "地图查询", Toast.LENGTH_LONG).show();
            }
        });
        return view;

    }



    @Override
    protected int initLayout() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}

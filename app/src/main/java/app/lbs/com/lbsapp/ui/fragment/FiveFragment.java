package app.lbs.com.lbsapp.ui.fragment;

import android.app.LocalActivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.api.Constant;
import app.lbs.com.lbsapp.base.BaseFragment;
import app.lbs.com.lbsapp.bean.Car;
import app.lbs.com.lbsapp.bean.ResultDTO;
import app.lbs.com.lbsapp.bean.User;
import app.lbs.com.lbsapp.ui.UserInfoActivity;
import app.lbs.com.lbsapp.utils.SharedPreferencesUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FiveFragment extends BaseFragment {

    TextView username;
    TextView name;
    TextView phone;
    TextView vin;
    TextView plate;
    TextView vehicleType;
    TextView engine;
    TextView model;

    /**
     * 子View管理
     */
    private Map<String, View> childViews = new HashMap<String, View>();
    private String currentTag;

    private LocalActivityManager mLocalActivityManager;
    private View headerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLocalActivityManager = new LocalActivityManager(getActivity(), false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        headerView = inflater.inflate(initLayout(), container, false);
        return headerView;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_five;
    }

    protected void initView() {
        username = headerView.findViewById(R.id.info_username);
        name = headerView.findViewById(R.id.info_name);
        phone = headerView.findViewById(R.id.info_phone);

        vin = headerView.findViewById(R.id.info_vin);
        plate = headerView.findViewById(R.id.info_plate);
        vehicleType = headerView.findViewById(R.id.info_vehicle_type);
        engine = headerView.findViewById(R.id.info_engine);
        model = headerView.findViewById(R.id.info_model);
    }

    @Override
    protected void initData() {
        getUserInfo();
        getCarInfo();
    }

    private void getUserInfo() {
        Long userId = SharedPreferencesUtil.getLongValue(getActivity(), "userId");
        final Request request = new Request.Builder()
                .url(Constant.USER_INFO + "?userId=" + userId).build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("json", "json: " + json);
                Gson gson = new Gson();
                Type type = new TypeToken<ResultDTO<User>>() {
                }.getType();
                ResultDTO<User> resultDTO = gson.fromJson(json, type);
                final User user = resultDTO.getResult();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        username.setText(user.getUsername());
                        name.setText(user.getName());
                        phone.setText(user.getPhone());
                    }
                });
            }
        });
    }

    private void getCarInfo() {
        Long userId = SharedPreferencesUtil.getLongValue(getActivity(), "userId");
        final Request request = new Request.Builder()
                .url(Constant.CAR_INFO + "?userId=" + userId).build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("json", "json: " + json);
                Gson gson = new Gson();
                Type type = new TypeToken<ResultDTO<List<Car>>>() {
                }.getType();
                ResultDTO<List<Car>> resultDTO = gson.fromJson(json, type);
                List<Car> carList = resultDTO.getResult();
                if (carList == null || carList.size() == 0) {
                    return;
                }

                final Car car = carList.get(0);
                if (car == null) {
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vin.setText(car.getVin());
                        plate.setText(car.getPlate());
                        vehicleType.setText(car.getVehicleType());
                        engine.setText(car.getEngine());
                        model.setText(car.getModel());
                    }
                });
            }
        });
    }

}

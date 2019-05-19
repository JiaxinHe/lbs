package app.lbs.com.lbsapp.ui.fragment;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.api.Constant;
import app.lbs.com.lbsapp.api.NetUtils;
import app.lbs.com.lbsapp.bean.Car;
import app.lbs.com.lbsapp.bean.LimitBean;
import app.lbs.com.lbsapp.bean.ResultDTO;
import app.lbs.com.lbsapp.bean.User;
import app.lbs.com.lbsapp.utils.SharedPreferencesUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OneFragment extends Fragment {
    private RadioGroup mRgGroup;
    private LinearLayout mLLView;
    private LinearLayout container;
    private TextView mTvChePai;
    private TextView mTvXingHao;
    private TextView mTvStatus;
    private LinearLayout mLLWeiHao;
    private TextView mTvNumOne;
    private TextView mTvNumTwo;
    /**
     * 子View管理
     */
    private Map<String, View> childViews = new HashMap<String, View>();
    private String currentTag;

    private LocalActivityManager mLocalActivityManager;
    private View headerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLocalActivityManager = new LocalActivityManager(getActivity(), false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        headerView = inflater.inflate(initLayout(), container, false);
        return headerView;
    }

    protected int initLayout() {
        return R.layout.fragment_one;
    }

    protected void initView() {
        mTvChePai = headerView.findViewById(R.id.tv_chepai);
        mTvXingHao = headerView.findViewById(R.id.tv_xinghao);
        mTvStatus = headerView.findViewById(R.id.tv_status);
        mLLWeiHao = headerView.findViewById(R.id.ll_weihao);
        mTvNumOne = headerView.findViewById(R.id.tv_num_one);
        mTvNumTwo = headerView.findViewById(R.id.tv_num_two);
        mRgGroup = headerView.findViewById(R.id.rg_group);
        mLLView = headerView.findViewById(R.id.ll_view);
        container = headerView.findViewById(R.id.container);
        mRgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mLLView.removeAllViews();
                if (i == R.id.rb_one) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_one, null);
                    mLLView.addView(view);
                    container.removeAllViews();
                } else if (i == R.id.rb_two) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_two, null);
                    mLLView.addView(view);
                    startTargetActivity("app.lbs.com.lbsapp.ui.InsuranceActivity", "InsuranceActivity");
                } else if (i == R.id.rb_three) {
                    mLLView.removeAllViews();
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_three, null);
                    mLLView.addView(view);
                    TextView oneTv = view.findViewById(R.id.tv_one);
                    oneTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            container.removeAllViews();
                        }
                    });
                    TextView twoTv = view.findViewById(R.id.tv_two);
                    twoTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startTargetActivity("app.lbs.com.lbsapp.ui.AccidentActivity", "AccidentActivity");
                        }
                    });
                    TextView threeTv = view.findViewById(R.id.tv_three);
                    threeTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            container.removeAllViews();
                        }
                    });
                }
            }
        });
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_one, null);
        mLLView.addView(view);
    }

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
                        mTvChePai.setText(user.getDriverLicense());
                    }
                });
                httpLimitCheck(user.getDriverLicense());
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
                        mTvXingHao.setText(car.getModel());
                    }
                });
            }
        });
    }

    private void httpLimitCheck(final String driverLicense) {
        String url = Constant.LIMIT_CHECK + "?city=北京&plate=" + driverLicense;
        NetUtils.getInstance().getDataAsynFromNet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.e("json", "json: " + json);
                Gson gson = new Gson();
                Type type = new TypeToken<ResultDTO<LimitBean>>() {
                }.getType();
                final ResultDTO<LimitBean> resultDTO = gson.fromJson(json, type);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resultDTO.getStatus() == 0) {
                            LimitBean limitBean = resultDTO.getResult();
                            String limitNumber = limitBean.getLimitSuffix();
                            if (limitBean.isLimited()) {
                                //被限号了
                                mLLWeiHao.setVisibility(View.VISIBLE);
                                if (!TextUtils.isEmpty(limitNumber)) {
                                    String[] numbers = limitNumber.split(",");
                                    mTvNumOne.setText(numbers[0]);
                                    if (numbers.length > 1) {
                                        mTvNumTwo.setText(numbers[1]);
                                    }
                                }
                                if (driverLicense.contains(limitNumber.replace(",", ""))) {
                                    mTvStatus.setText("该车已被限号");
                                } else {
                                    mTvStatus.setText("允许通行");
                                }
                            } else {
                                mLLWeiHao.setVisibility(View.GONE);
                                mTvStatus.setText("允许通行");
                            }

                        } else {
                            Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void startTargetActivity(String className, String tag) {
        Intent intent = null;
        try {
            intent = new Intent(getActivity(), Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        startActivity(tag, intent);
    }

    /**
     * 加载子Activity
     *
     * @param tag
     * @param intent
     */
    private void startActivity(String tag, Intent intent) {
        if (currentTag != null) {
            View currentView = childViews.get(currentTag);
            if (currentView != null)
                currentView.setVisibility(View.GONE);
        }
        currentTag = tag;
        View originView = childViews.get(tag);
        final Window window = mLocalActivityManager.startActivity(tag, intent);
        final View decorView = window.getDecorView();
        if (decorView != originView && originView != null) {
            if (originView.getParent() != null)
                ((ViewGroup) originView.getParent()).removeView(originView);
        }
        childViews.put(tag, decorView);
        if (decorView != null) {
            decorView.setVisibility(View.VISIBLE);
            decorView.setFocusableInTouchMode(true);
            ((ViewGroup) decorView).setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            container.removeAllViews();
            if (decorView.getParent() == null) {
                container.addView(decorView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            decorView.requestFocus();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalActivityManager.dispatchDestroy(getActivity().isFinishing());
    }

    @Override
    public void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }
}

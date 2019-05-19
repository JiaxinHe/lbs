package app.lbs.com.lbsapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.adapter.AccidentAdapter;
import app.lbs.com.lbsapp.api.Constant;
import app.lbs.com.lbsapp.api.NetUtils;
import app.lbs.com.lbsapp.bean.Accident;
import app.lbs.com.lbsapp.bean.ResultDTO;
import app.lbs.com.lbsapp.utils.SharedPreferencesUtil;
import app.lbs.com.lbsapp.utils.TimeTransUtils;
import app.lbs.com.lbsapp.widget.MyListView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 事故记录界面
 */
public class AccidentActivity extends AppCompatActivity {
    private AccidentAdapter accidentAdapter;
    private List<Accident> accidentList = new ArrayList<>();
    private MyListView listView;
    private TextView tagTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident);
        accidentAdapter = new AccidentAdapter(this, R.layout.item_accident, accidentList);
        listView = findViewById(R.id.list_accident);
        tagTv = findViewById(R.id.tv_tag);
        listView.setAdapter(accidentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Accident accident = accidentList.get(i);
                Intent intent = new Intent(AccidentActivity.this,AccidentDetailActivity.class);
                intent.putExtra("accident",accident);
                startActivity(intent);
            }

        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //监听第一个可见的
                if (firstVisibleItem < accidentList.size()) {
                    Accident accident = accidentList.get(firstVisibleItem);
                    tagTv.setText(TimeTransUtils.strToDateLong(accident.getTime()));
                }
            }
        });
        getData();
    }

    /**
     * 通过HTTP请求获取数据并更新界面
     */
    public void getData() {
        Long userId = SharedPreferencesUtil.getLongValue(AccidentActivity.this, "userId");
        String url = Constant.ACCIDENT + "?userId=" + userId;
        NetUtils.getInstance().getDataAsynFromNet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.e("json", "json: " + json);
                Gson gson = new Gson();
                Type type = new TypeToken<ResultDTO<List<Accident>>>() {
                }.getType();
                ResultDTO<List<Accident>> resultDTO = gson.fromJson(json, type);
                if (resultDTO.getStatus() == 0) {
                    accidentList.clear();
                    List<Accident> list = resultDTO.getResult();
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            accidentList.add(list.get(i));
                        }
                    }
//                    accidentList.addAll(resultDTO.getResult());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            accidentAdapter.notifyDataSetChanged();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AccidentActivity.this, "事故信息加载失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}

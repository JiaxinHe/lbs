package app.lbs.com.lbsapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.api.Constant;
import app.lbs.com.lbsapp.api.NetUtils;
import app.lbs.com.lbsapp.bean.LoginResult;
import app.lbs.com.lbsapp.bean.ResultDTO;
import app.lbs.com.lbsapp.utils.SharedPreferencesUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 用户登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView titleTv;
    private TextView loginTv;
    private TextView registerTv;
    private ImageView lookImg;
    private EditText accEt;
    private EditText pwdEt;
    private Toolbar mToolbar;
    private boolean isLook = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        initToolBar();
        titleTv = findViewById(R.id.tv_action_title);
        registerTv = findViewById(R.id.tv_to_register);
        registerTv.setOnClickListener(this);
        loginTv = findViewById(R.id.tv_login);
        loginTv.setOnClickListener(this);
        lookImg = findViewById(R.id.img_look);
        pwdEt = findViewById(R.id.et_pwd);
        accEt = findViewById(R.id.et_username);
        titleTv.setText("登录");
        lookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLook) {
                    isLook = false;
                    //隐藏密码
                    pwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    lookImg.setImageDrawable(getResources().getDrawable(R.mipmap.look));
                } else {
                    isLook = true;
                    //显示密码
                    pwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    lookImg.setImageDrawable(getResources().getDrawable(R.mipmap.no_look));
                }
                pwdEt.setSelection(pwdEt.getText().toString().length());
            }
        });
    }

    public void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    private void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_to_register:
                goRegisterPage();
                break;
            case R.id.tv_login:
                final String userName = accEt.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    showMsg("请输入用户名");
                    return;
                }
                String pwd = pwdEt.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    showMsg("请输入密码");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("username", userName);
                params.put("pass", pwd);
                //发起登录http请求
                NetUtils.getInstance().postDataAsynToNet(Constant.LOGIN, params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Log.e("json", "json: " + json);
                        Type type = new TypeToken<ResultDTO<LoginResult>>() {
                        }.getType();
                        ResultDTO<LoginResult> resultDTO = new Gson().fromJson(json, type);
                        if (resultDTO.getStatus() == 0) {
                            showMsg("登录成功");
                            LoginResult loginResult = resultDTO.getResult();
                            //保存token
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "token", loginResult.getToken());
                            SharedPreferencesUtil.saveLongValue(LoginActivity.this, "userId", loginResult.getUserId());
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "username", userName);
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "token", "");
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "username", "");
                            showMsg(resultDTO.getMessage());
                        }
                    }
                });
                break;
        }
    }

    private void showMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goRegisterPage() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}

package app.lbs.com.lbsapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.api.HttpConstant;
import app.lbs.com.lbsapp.api.NetUtils;
import app.lbs.com.lbsapp.bean.BaseBean;
import app.lbs.com.lbsapp.utils.SharedPreferencesUtil;
import okhttp3.Call;
import okhttp3.Response;

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
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                NetUtils.getInstance().postDataAsynToNet(HttpConstant.LOGIN, params, new NetUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        BaseBean bean = new Gson().fromJson(result, BaseBean.class);
                        if (bean.getStatus() == 0) {
                            showMsg("登录成功");
                            //保存token
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "token", bean.getResult());
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "username", userName);
                            finish();
                        } else {
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "token", "");
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "username", "");
                            showMsg(bean.getMessage());
                        }
                    }

                    @Override
                    public void failed(Call call, IOException e) {

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

package app.lbs.com.lbsapp.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.TrackApplication;
import app.lbs.com.lbsapp.utils.BitmapUtil;
import app.lbs.com.lbsapp.utils.SharedPreferencesUtil;

import static app.lbs.com.lbsapp.utils.BitmapUtil.init;

/**
 * 主界面
 */
public class  MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView headerImg;
    private TextView nameTv;
    private TextView titleTv;
    private MenuItem menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTv = findViewById(R.id.tv_action_title);
        titleTv.setText(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //先获取header，才能获取header里面的控件
        View headerView = navigationView.getHeaderView(0);
        headerImg = headerView.findViewById(R.id.imageView);
        nameTv = headerView.findViewById(R.id.tv_name);
        //获取Menu的控件
        Menu menuView = navigationView.getMenu();
        menuItem = menuView.getItem(7);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_sys) {
            showMsg("扫一扫");
            return true;
        } else if (id == R.id.menu_guanzhu) {
            showMsg("添加关注");
            return true;
        } else if (id == R.id.menu_dianping) {
            showMsg("写点评");
            return true;
        } else if (id == R.id.menu_shanghu) {
            showMsg("添加商户");
            return true;
        } else if (id == R.id.menu_fukuan) {
            showMsg("付款");
            return true;
        } else if (id == R.id.menu_hezuo) {
            showMsg("我要合作");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_shigu:
                goAccidentPage();
                break;
            case R.id.nav_baoxian:
                goInsurancePage();
                break;
            case R.id.nav_anquan:
                goSafePage();
                break;
            case R.id.nav_youjia:
                goGasPage();
                break;
            case R.id.logout:
                goLoginPage();
                break;
            case R.id.imageView:
                goLoginPage();
                break;
            case R.id.nav_kaoti:
                goExamPage();
                break;
            case R.id.nav_xianxing:
                goLimitPage();
                break;
            case R.id.nav_personal_info:
                goInfoPage();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goAccidentPage() {
        String token = SharedPreferencesUtil.getStringValue(MainActivity.this, "token");
        if ("".equals(token)) {
            goLoginPage();
        } else {
            Intent intent = new Intent(this, AccidentActivity.class);
            startActivity(intent);
        }
    }

    private void goInsurancePage() {
        String token = SharedPreferencesUtil.getStringValue(MainActivity.this, "token");
        if ("".equals(token)) {
            goLoginPage();
        } else {
            Intent intent = new Intent(this, InsuranceActivity.class);
            startActivity(intent);
        }
    }

    private void goSafePage() {
        Intent intent = new Intent(this, SafeActivity.class);
        startActivity(intent);
    }

    private void goGasPage() {
        Intent intent = new Intent(this, GasActivity.class);
        startActivity(intent);
    }

    private void goExamPage() {
        Intent intent = new Intent(this, ExamActivity.class);
        startActivity(intent);
    }

    private void goLimitPage() {
        Intent intent = new Intent(this, LimitActivity.class);
        startActivity(intent);
    }

    private void goInfoPage() {
        String token = SharedPreferencesUtil.getStringValue(MainActivity.this, "token");
        if ("".equals(token)) {
            goLoginPage();
        } else {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
        }
    }

    private void goLoginPage() {
        SharedPreferencesUtil.saveStringValue(this, "token", "");
        SharedPreferencesUtil.saveStringValue(this, "username", "");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String userName = SharedPreferencesUtil.getStringValue(MainActivity.this, "username");
        if (!TextUtils.isEmpty(userName)) {
            nameTv.setText(userName);
            menuItem.setTitle("退出登录");
        } else {
            nameTv.setText("去登陆");
            menuItem.setTitle("去登陆");
        }
    }

    private void showMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

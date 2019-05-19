package app.lbs.com.lbsapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import app.lbs.com.lbsapp.R;
import app.lbs.com.lbsapp.bean.Accident;

public class AccidentDetailActivity extends AppCompatActivity {

    private TextView nameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_detail);
        nameTv = findViewById(R.id.tv_name);
        Accident accident = getIntent().getParcelableExtra("accident");
        nameTv.setText("详情："+accident.getDescription());
    }
}

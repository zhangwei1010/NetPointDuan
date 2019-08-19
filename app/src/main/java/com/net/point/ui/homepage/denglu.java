package com.net.point.ui.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.net.point.R;

public class denglu extends AppCompatActivity {

    private TextView tvPostStation;
    private TextView tvDot;
    private TextView tvAllianceBusiness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        tvPostStation = findViewById(R.id.tvPostStation);
        tvDot=findViewById(R.id.tvDot);
        tvAllianceBusiness=findViewById(R.id.tv_AllianceBusiness);

    }
    public void OnPostStation(View view){
        tvPostStation.setTextColor(getResources().getColor(R.color.green));

    }
    public  void  OnDot(View view){
        tvDot.setTextColor(getResources().getColor(R.color.black));
    }
    public  void  OnAllianceBusiness(View view){
        tvAllianceBusiness.setTextColor(getResources().getColor(R.color.white));
    }
}

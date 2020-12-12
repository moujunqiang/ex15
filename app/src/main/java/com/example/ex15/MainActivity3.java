package com.example.ex15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {

    /**
     * 天气
     */
    private Button mBtnWeather;
    /**
     * 人员信息
     */
    private Button mBtnPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }

    private void initView() {
        mBtnWeather = (Button) findViewById(R.id.btn_weather);
        mBtnWeather.setOnClickListener(this);
        mBtnPerson = (Button) findViewById(R.id.btn_person);
        mBtnPerson.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_weather:
                startActivity(new Intent(MainActivity3.this,MainActivity.class));
                break;
            case R.id.btn_person:
                startActivity(new Intent(MainActivity3.this,MainActivity2.class));

                break;
        }
    }
}
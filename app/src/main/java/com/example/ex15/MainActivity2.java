package com.example.ex15;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ListView mListview;
    private EditText mEdittext;
    /**
     * search
     */
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        mListview = (ListView) findViewById(R.id.listview);
        mEdittext = (EditText) findViewById(R.id.edittext);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        List<PersonInfoBean> o = new Gson().fromJson(getJson("data.json", this), new TypeToken<List<PersonInfoBean>>() {
        }.getType());
        mListview.setAdapter(new MyListAdapter(o, MainActivity2.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.button:
                List<PersonInfoBean> o = new Gson().fromJson(getJson("data.json", this), new TypeToken<List<PersonInfoBean>>() {
                }.getType());
                int approximate = getApproximate(Double.parseDouble(mEdittext.getText().toString()), o);
                if (approximate != -1) {
                    PersonInfoBean personInfoBean = o.get(approximate);
                    List<PersonInfoBean> personInfoBeans = new ArrayList<>();
                    personInfoBeans.add(personInfoBean);
                    mListview.setAdapter(new MyListAdapter(personInfoBeans, MainActivity2.this));
                }

                break;
        }
    }

    private static int getApproximate(double x, List<PersonInfoBean> personInfoBeans) {
        double[] xsd = new double[personInfoBeans.size()];
        for (int i = 0; i < personInfoBeans.size(); i++) {
            xsd[i] = personInfoBeans.get(i).getXsd();
        }
        if (xsd == null) {
            return -1;
        }
        if (xsd.length == 1) {
            return 0;
        }
        double minDifference = Math.abs(xsd[0] - x);
        int minIndex = 0;
        for (int i = 1; i < xsd.length; i++) {
            double temp = Math.abs(xsd[i] - x);
            if (temp < minDifference) {
                minIndex = i;
                minDifference = temp;
            }
        }
        return minIndex;
    }

    private static String getJson(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
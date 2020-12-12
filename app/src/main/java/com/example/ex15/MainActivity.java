package com.example.ex15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.promeg.pinyinhelper.Pinyin;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvBing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvBing=findViewById(R.id.tv_bing);
        final ListView listview = (ListView) MainActivity.this.findViewById(R.id.listview);
        final List<String> listdata = new ArrayList<String>();

        final OkHttpClient okclient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url("https://free-api.heweather.com/s6/weather/now?key=e1984444a03d4a52a1f6cc545cce9245&location=beijing")
                .build();

        Call call = okclient.newCall(request);
        loadImage();
        Button btn = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.edittext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tar = String.valueOf(editText.getText());
                String target = Pinyin.toPinyin(tar, "");

                Request request = new Request.Builder()
                        .url("https://free-api.heweather.com/s6/weather/now?key=f23ef7e1cd4e42adaaf5f6ca99a6fbe2&location=" + target)
                        .build();

                Call call = okclient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String htmlStr = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                Weather weather = gson.fromJson(htmlStr, Weather.class);
                                List<Weather.HeWeather6Bean> local = weather.getHeWeather6();

                                if (local.get(0).getStatus().equals("ok")) {
                                    String str[] = new String[6];

                                    str[0] = "id:" + local.get(0).getBasic().getCid();
                                    str[1] = "位置:" + local.get(0).getBasic().getLocation();
                                    str[2] = "国家:" + local.get(0).getBasic().getCnty();
                                    str[3] = "时间:" + local.get(0).getUpdate().getLoc();
                                    str[4] = "天气:" + local.get(0).getNow().getCond_txt();
                                    str[5] = "Tmp" + local.get(0).getNow().getTmp();

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, str);
                                    listview.setAdapter(arrayAdapter);
                                } else {
                                    Toast.makeText(MainActivity.this, "查询失败", Toast.LENGTH_LONG).show();
                                    String str[] = new String[6];

                                    str[5] = "id:null";
                                    str[0] = "位置:null";
                                    str[1] = "国家:null";
                                    str[2] = "时间:null";
                                    str[3] = "天气:null";
                                    str[4] = "Tmp:null";
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, str);
                                    listview.setAdapter(arrayAdapter);
                                }
                            }
                        });
                    }
                });


            }
        });
    }

    private void loadImage() {
        String requestBingPic = "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestBingPic).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                final ImageBean imageBean = gson.fromJson(response.body().string(), ImageBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).load("https://www.bing.com" + imageBean.getImages().get(0).getUrl()).into((ImageView) findViewById(R.id.image_bg));
                        tvBing.setText(imageBean.getImages().get(0).getCopyright());
                    }
                });
            }
        });

    }
}

package com.qunlivideo.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import com.bcq.net.NetApi;
import com.bcq.net.callback.base.BaseListCallback;
import com.oklib.core.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity {
    TextView info;

    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = findViewById(R.id.info);
        String url = "http://api.help.bj.cn/apis/weather";
        Map<String, Object> param = new HashMap<>(2);
        param.put("id", "101010100");
//        OkApi.get(url, param, new StringCallBack() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("MainActivity", "onResponse");
//                if (!TextUtils.isEmpty(response)) {
//                    info.setText(response);
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//        });
        Locations.location(this, new Locations.OnLocationListener() {
            @Override
            public void onLocalAddress(int code, String address) {
                Log.e("MainActivity", "address = " + address);
                String text = info.getText().toString().trim();
                info.setText("位置：" + address + text);
            }
        });

        NetApi.request(null, url, param, Method.get, new BaseListCallback<Weather>() {
            @Override
            public void onSuccess(List<Weather> strings, Boolean loadFull) {
                Weather infos = strings.get(0);
                if (null != infos) {
                    String text = info.getText().toString().trim();
                    info.setText(text + "天气：" + infos.getWeather());
                }
            }
        });

    }
}

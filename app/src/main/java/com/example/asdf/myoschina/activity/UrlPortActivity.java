package com.example.asdf.myoschina.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asdf.myoschina.R;

import java.util.regex.Pattern;

/**
 * Created by asdf on 2017/6/2.
 */

public class UrlPortActivity extends Activity {
    private EditText edt_url;
    private EditText edt_port;
    private Button btn_save;
    private Button btn_clear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.urlport);
        init();
        initdata();
    }

    private void initdata() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edt_url.getText().toString().trim();
                String port = edt_port.getText().toString().trim();
                if (url != null && !url.equals("") && port != null && !port.equals("")) {
                    Pattern UrlPattern = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");
                    if (UrlPattern.matcher(url).matches()) {
                        SharedPreferences sp = getSharedPreferences("url", MODE_PRIVATE);
                        sp.edit().putString("url", url).commit();
                        Toast.makeText(getApplicationContext(), "ip设置成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "ip输入错误", Toast.LENGTH_SHORT).show();
                    }

                    Integer intport = Integer.valueOf(port);
                    if (intport >= 0 && intport < 65535) {
                        SharedPreferences sp = getSharedPreferences("port", MODE_PRIVATE);
                        sp.edit().putInt("port", intport).commit();
                    } else {
                        Toast.makeText(getApplicationContext(), "端口号输入错误", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "请输入ip和端口号", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_url.setText("");
                edt_port.setText("");
                Toast.makeText(getApplicationContext(), "清除成功", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        edt_url = (EditText) findViewById(R.id.edt_url);
        edt_port = (EditText) findViewById(R.id.edt_port);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_clear = (Button) findViewById(R.id.btn_clear);
    }
}

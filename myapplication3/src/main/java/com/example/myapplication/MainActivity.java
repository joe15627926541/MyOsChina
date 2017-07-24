package com.example.myapplication;

import android.speech.tts.Voice;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<info.TrailersBean> trailers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trailers=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://api.m.mtime.cn/PageSubArea/TrailerList.api", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println("----string-----"+s);
                //先转JsonObject
                JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
                System.out.println("----jsonObject-----"+jsonObject);
                //再转JsonArray 加上数据头
                JsonArray jsonArray = jsonObject.getAsJsonArray("trailers");
                System.out.println("----jsonArray-----"+jsonArray);
                Gson gson = new Gson();
                ArrayList<info> userBeanList = new ArrayList<>();
                for (JsonElement user : jsonArray) {
                    //通过反射 得到UserBean.class
                    info userBean = gson.fromJson(user, new TypeToken<info>() {}.getType());
                    userBeanList.add(userBean);
                }

                Toast.makeText(getApplicationContext(),"集合的大小为："+userBeanList.size(),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"连接不到服务器",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}

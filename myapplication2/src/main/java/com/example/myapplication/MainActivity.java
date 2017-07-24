package com.example.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText edt_user;
    private EditText edt_psw;
    private Button btn;
    private Button second;
    ArrayList<FriendInfo> friendinfos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_user = (EditText) findViewById(R.id.edt_user);
        edt_psw = (EditText) findViewById(R.id.edt_psw);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cookie = getSettingNote("Cookie");
                System.out.println("--------请求的数据cookie:"+cookie);
                if (!TextUtils.isEmpty(cookie)) {
//
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.10.72.3:8080/oschina/list/active_list2/page0.xml", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            System.out.println("--------请求的数据:"+s);
                            System.out.println("------图片"+friendinfos.get(0).getPortrait()+"------作者"+friendinfos.get(0).getAuthor()+"--内容"+friendinfos.get(0).getMessage());

                            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap localHashMap = new HashMap();
                                localHashMap.put("Cookie", getSettingNote("Cookie"));//向请求头部添加Cookie
                                return localHashMap;
                            }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.10.72.3:8080/oschina/list/active_list2/page0.xml", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            System.out.println("--------请求的数据:" + s);
                            System.out.println("--------请求的数据1");
                            InputStream is = new ByteArrayInputStream(s.getBytes());
                            friendinfos= FriendXmlParser.xmlparse(is);
                            Toast.makeText(getApplicationContext(), friendinfos.toString(), Toast.LENGTH_LONG).show();
                            System.out.println("------图片"+friendinfos.get(0).getPortrait()+"------作者"+friendinfos.get(0).getAuthor()+"--内容"+friendinfos.get(0).getMessage());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }) {
                        //获取cookie
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            return map;
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            try {

                                Map<String, String> responseHeaders = response.headers;
                                String rawCookies = responseHeaders.get("Set-Cookie");
                                saveSettingNote("Cookie", rawCookies);//保存Cookie
                                Log.i("px", rawCookies + "\n");
                                String dataString = new String(response.data, "UTF-8");
                                Log.i("px", dataString);
                                return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                            } catch (UnsupportedEncodingException e) {
                                return Response.error(new ParseError(e));
                            }
                        }
                    };
                    requestQueue.add(stringRequest);
                }


            }
        });

    }

    private String getSettingNote(String s) {//获取保存设置
        SharedPreferences read = getSharedPreferences("ShareTo", MODE_PRIVATE);
        return read.getString(s, "");
    }

    private void saveSettingNote(String s, String saveData) {//保存设置
        SharedPreferences.Editor note = getSharedPreferences("ShareTo", MODE_PRIVATE).edit();
        note.putString(s, saveData);
        note.commit();
    }
}

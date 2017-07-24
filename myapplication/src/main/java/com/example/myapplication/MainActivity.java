package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("-----");
                RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.oschina.net/action/api/news_list?pageIndex=0&catalog=1&pageSize=20", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        System.out.println("---请求的数据为-----"+s+"--------");

                        if(!s.equals("")){
//                            InputStream is = new ByteArrayInputStream(s.getBytes());

                             InputStream is = new ByteArrayInputStream(s.getBytes());
                            System.out.println("---请求的输入流为-----"+is+"--------");
                            try {
                                List<newsInfo> newsInfos = XmlParser.xmlParser(is);
                                System.out.println("-------------集合的大小为"+newsInfos.size());
                                System.out.println("-------body"+newsInfos.get(0).getBody());
                                System.out.println("-------PubDate"+newsInfos.get(0).getPubDate());
                                System.out.println("-------Title"+newsInfos.get(0).getTitle());
                                System.out.println("-------CommentCount"+newsInfos.get(0).getCommentCount());
                               Toast.makeText(getApplicationContext(),"集合的大小:"+newsInfos.size(),Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{
                            return;
                        }




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);


            }
        });
    }
}

package convery.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.util.UIUtils;


import convery.fragment.FindFriendFragment;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class FindFriendActivity extends FragmentActivity {
    private TextView tv_back_friend;
    private FrameLayout fl_friend;
    private ImageView search_delete;
    private EditText edt_search;
    private SharedPreferences.Editor edit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        tv_back_friend=(TextView)findViewById(R.id.tv_back_friend);
        fl_friend=(FrameLayout)findViewById(R.id.fl_friend);
        search_delete=(ImageView)findViewById(R.id.search_delete);
        edt_search=(EditText)findViewById(R.id.edt_search);
        tv_back_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences sp = getSharedPreferences("find_content", MODE_PRIVATE);
         edit = sp.edit();

       //搜索框的监听
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String content = edt_search.getText().toString().trim();
                if (!content.equals("") && content != null) {
                    RequestQueue requestQueue = Volley.newRequestQueue(UIUtils.getContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.oschina.net/action/api/find_user?name="+content, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            edit.putString("content",s).commit();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(UIUtils.getContext(), "服务器出错，请稍后再试！！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(stringRequest);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    FindFriendFragment friendFragment = new FindFriendFragment();
                    transaction.replace(R.id.fl_friend,friendFragment).commit();
                    search_delete.setVisibility(View.VISIBLE);
                } else {
                    search_delete.setVisibility(View.GONE);
                }
            }
        });

    }
}

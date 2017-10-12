package comprehensive.fragment;

import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage;
import com.example.asdf.myoschina.view.LoadingPage.ResultState;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import comprehensive.XmlParse.HotDetailXmlParser;
import comprehensive.XmlParse.NewsXmlParser;
import comprehensive.domain.newsInfo;

/**
 * Created by ba0ch3ng on 2017/6/24.
 */

public class HotDetailFragment extends BaseFragment {
    ArrayList<newsInfo> mList = null;
    @Override
    public View onCreateSuccessView() {
        WebView webView = new WebView(UIUtils.getContext());
        //设置webview的属性
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(true);
        settings.setSaveFormData(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm.densityDpi > 240 ) {
            settings.setDefaultFontSize(40); //可以取1-72之间的任意值，默认16
        }

        webView.loadDataWithBaseURL(null, mList.get(0).body, "text/html", "utf-8", null);
        return webView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        //获取activity传入的位置值
        int position = getArguments().getInt("position");
        RequestQueue requestQueue = Volley.newRequestQueue(UIUtils.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.oschina.net/action/api/news_detail?id=" + (position + 1), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                InputStream is = new ByteArrayInputStream(s.getBytes());
                mList = HotDetailXmlParser.HotDetailxmlParser(is);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "服务器连接出错，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
        SystemClock.sleep(500);
        return check(mList);

    }
}

package convery.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.asdf.myoschina.R;
import com.xys.libzxing.zxing.activity.CaptureActivity;

public class NewActivity extends Activity {
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        webview = (WebView) findViewById(R.id.webview);
        startActivityForResult(new Intent(NewActivity.this, CaptureActivity.class), 0);
        Myrecevice myrecevice = new Myrecevice();
        IntentFilter filter=new IntentFilter();
        filter.addAction("ToNewActivity");
        registerReceiver(myrecevice,filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
                webview.loadUrl(result);
        }
    }
    class Myrecevice extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }
}
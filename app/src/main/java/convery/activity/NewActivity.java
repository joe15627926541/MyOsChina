package convery.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.activity.MainActivity;
import com.xys.libzxing.zxing.activity.CaptureActivity;

public class NewActivity extends Activity {
    private WebView webview;
    private final int CAMERA_REQUEST_CODE = 1;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        webview = (WebView) findViewById(R.id.webview);
        //需要检查是否打开照相的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                //申请相机权限
                ActivityCompat.requestPermissions(NewActivity.this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);

                //TODO
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }

        }else{
            startActivityForResult(new Intent(NewActivity.this, CaptureActivity.class), 0);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(NewActivity.this, CaptureActivity.class), 0);
                Myrecevice myrecevice = new Myrecevice();
                IntentFilter filter=new IntentFilter();
                filter.addAction("ToNewActivity");
                registerReceiver(myrecevice,filter);
            } else {
                //用户勾选了不再询问
                //提示用户手动打开权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "相机权限已被禁止", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webview.getSettings().setSupportMultipleWindows(true);
            webview.setWebViewClient(new WebViewClient());
            webview.setWebChromeClient(new WebChromeClient());
            webview.loadUrl(result);
        }else{
              intent = new Intent(NewActivity.this,MainActivity.class);
                intent.putExtra("convery",1);
                startActivity(intent);
            finish();
        }
    }

    class Myrecevice extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(NewActivity.this,MainActivity.class);
        intent.putExtra("convery",1);
        startActivity(intent);
    }
}
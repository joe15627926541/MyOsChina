package com.example.asdf.myoschina.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.util.util;
import com.example.asdf.myoschina.view.MySlidingPaneLayout;

import java.io.File;
import java.io.IOException;

import comprehensive.fragment.ComPreHenSiveFragment;
import convery.fragment.ConVeryFragment;
import me.fragment.MeFragment;
import move.fragment.MoveFragment;

public class MainActivity extends FragmentActivity {
    private MySlidingPaneLayout slidingpanelayout;
    private RadioGroup radiogroup;
    private RadioButton add;

    /*
    * 侧边栏的控件
    * */
    private ListView listview_left;
    private TextView tv_setting;
    private TextView tv_exit;
    /*
    * top的控件
    *
    * */
    private TextView toggle;
    private TextView tv_search;
    private boolean isopen;

    /*
    * 填充左边数据
    * */
    private String name[] = {"技术问答", "开源软件", "博客园", "Git客户端"};
    private int IconId[] = {R.drawable.selector_quest_left, R.drawable.selector_opensoft_left,
            R.drawable.selector_blog_left, R.drawable.selector_gitapp_left};
    private Myadapter myadapter;
    /*
    * 四个fragment
    * */
    private ComPreHenSiveFragment comPreHensiveFragment;
    private ConVeryFragment conVeryFragment;
    private MeFragment meFragment;
    private  MoveFragment moveFragment;
    /*
    * 记录上次的radiobutton的位置，解决点击中间的加号而都没有颜色的问题
    * */
    private int RadioButtonId;
    /*
    * 记录是否双击退出
    * */
    private boolean isexit;
    /*
    * 长按按钮
    * */
    private ImageView iv_add;
    private PopupWindow popupWindow;
    private WindowManager.LayoutParams lp;
    /*
    * popuwindow的控件
    * */
    private TextView tv_text;
    private TextView tv_camera;
    private TextView tv_photos;
    private ImageView iv_delete;


    /*
    * 隐式开启相册
    * */
    private static int RESULT_LOAD_IMAGE = 1;
    /*
    * 隐式开启照相机
    * */
    public static final int TAKE_PHOTO = 2;
    //处理后的图片
    public static final int CROP_PHOTO = 3;
    /*
    *照相机的uri
    * */
    private Uri imageUri;

    private FragmentManager fm;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        OnClickListener();
        Intent intert=getIntent();
        int id = intert.getIntExtra("convery",-1);
        if(id>0){
            System.out.println("aaa"+id);
            if(id==1){
                transaction.replace(R.id.fl_content, conVeryFragment); //这里是指定跳转到指定的fragment
            }
        }

    }

    private void OnClickListener() {
        /*
        * 开关按钮
        *
        * */
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingpanelayout.isOpen()) {
                    slidingpanelayout.closePane();
                } else {
                    slidingpanelayout.openPane();
                }


            }
        });
        //搜索按钮
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchPagerActivity.class);
                startActivity(intent);
            }
        });


        /*
        * 切换界面
        * */
        RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                fm = MainActivity.this.getSupportFragmentManager();
                transaction = fm.beginTransaction();
                delete(checkedId);
                switch (checkedId) {
                    case R.id.comprehensive:
                        RadioButtonId = R.id.comprehensive;
                        if (comPreHensiveFragment == null) {
                            comPreHensiveFragment = new ComPreHenSiveFragment();
                        }

                        transaction.replace(R.id.fl_content, comPreHensiveFragment);
                        break;
                    case R.id.move:
                        RadioButtonId = R.id.move;

                        if (moveFragment == null) {
                            moveFragment = new MoveFragment();
                        }
                        transaction.replace(R.id.fl_content, moveFragment);
                        break;
                    case R.id.add:
                        //解决点击中间的加号而都没有颜色的问题
                        radiogroup.check(RadioButtonId);
                        return;
                    case R.id.convery:
                        RadioButtonId = R.id.convery;
                        if (conVeryFragment == null) {
                            conVeryFragment = new ConVeryFragment();
                        }
                        transaction.replace(R.id.fl_content, conVeryFragment);
                        break;
                    case R.id.me:
                        RadioButtonId = R.id.me;
                        if (meFragment == null) {
                            meFragment = new MeFragment();

                        }
                        transaction.replace(R.id.fl_content, meFragment);
                        break;
                }
                transaction.commit();
            }
        };
        radiogroup.setOnCheckedChangeListener(listener);
        //左边的退出
        tv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*
        * 中间按钮的长按
        *
        * */
        iv_add.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, UrlPortActivity.class);
                startActivity(intent);
                return true;
            }
        });
       /*
        * 中间按钮的长按
        *
        * */
        add.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, UrlPortActivity.class);
                startActivity(intent);
                return true;
            }
        });
        /*中间键的点击事件*/
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopuwindow();
            }
        });
         /*中间键的点击事件*/
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopuwindow();
            }
        });


    }

    private void delete(int checkedId) {
        if(checkedId==R.id.comprehensive){
            moveFragment=null;
            conVeryFragment=null;
            meFragment=null;
        }if(checkedId== R.id.move){
            meFragment=null;
            conVeryFragment=null;
            comPreHensiveFragment=null;
        }if(checkedId==R.id.convery){
            moveFragment=null;
            meFragment=null;
            comPreHensiveFragment=null;
        }if(checkedId== R.id.me){
            moveFragment=null;
            conVeryFragment=null;
            comPreHensiveFragment=null;
        }


    }

    /*
    * 初始化控件
    *
    * */
    private void init() {
        listview_left = (ListView) findViewById(R.id.listview_left);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        toggle = (TextView) findViewById(R.id.toggle);
        tv_search = (TextView) findViewById(R.id.tv_search);
        slidingpanelayout = (MySlidingPaneLayout) findViewById(R.id.slidingpanelayout);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        add = (RadioButton) findViewById(R.id.add);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        myadapter = new Myadapter();
        listview_left.setAdapter(myadapter);
        fm = MainActivity.this.getSupportFragmentManager();
        transaction = fm.beginTransaction();
        comPreHensiveFragment = new ComPreHenSiveFragment();
        transaction.replace(R.id.fl_content, comPreHensiveFragment );
        transaction.commit();

    }

    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IconId.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(MainActivity.this, R.layout.item_listview, null);
            ImageView iv_icon = (ImageView) inflate.findViewById(R.id.iv_icon);
            TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
            iv_icon.setImageResource(IconId[position]);
            tv_title.setText(name[position]);
            return inflate;
        }
    }

    /*
    * 双击退出
    * */
    @Override
    public void onBackPressed() {
        //退出程序
        if (!isexit) {
            isexit = true;
            Toast.makeText(getApplicationContext(), "再次点击退出开源中国", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            //退出
            super.onBackPressed();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isexit = false;
        }
    };

    /*
    * 弹出popuwindow
    *
    * */
    public void showpopuwindow() {
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;

        int screenHeigh = dm.heightPixels;
        popupWindow = new PopupWindow();
        View inflate = View.inflate(getApplicationContext(), R.layout.popuwindow, null);
        /*
        * 初始化控件
        *
        * */
        tv_text = (TextView) inflate.findViewById(R.id.tv_text);
        tv_camera = (TextView) inflate.findViewById(R.id.tv_camera);
        tv_photos = (TextView) inflate.findViewById(R.id.tv_photos);
        iv_delete = (ImageView) inflate.findViewById(R.id.iv_delete);
        InitPopuListener();


        popupWindow.setContentView(inflate);
        popupWindow.setWidth(screenWidth);
        popupWindow.setHeight(util.dip2px(MainActivity.this, 120));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

        /*
        * 设置背景变暗
        * */
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        popupWindow.showAtLocation(findViewById(R.id.fl_content), Gravity.LEFT | Gravity.BOTTOM, 0, -(util.dip2px(getApplicationContext(), 50)));
         /*
         * 设置popuwindow的回退键
         * */
        inflate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    return true;
                }
                return false;
            }
        });
        /*
        * popuwindow消失后背景变亮
        *
        * */
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });


    }

    private void InitPopuListener() {
        tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //文字
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                popupWindow.dismiss();
            }
        });
        tv_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调起照相机
                File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序


            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });


    }
    //onActivityResult


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data && requestCode == RESULT_LOAD_IMAGE) {
            Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
            startActivity(intent1);
            final Intent intent = new Intent();
            intent.putExtra("data", data.getData().toString());
            intent.putExtra("requestCode", requestCode);
            intent.setAction("com.data");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendBroadcast(intent);
                }
            }, 500);
        } else if (requestCode == TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageUri, "image/*");
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
            }
        } else if (requestCode == CROP_PHOTO) {
            if (resultCode == RESULT_OK) {
                Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent1);
                final Intent intent = new Intent();
                intent.putExtra("data", data.getData().toString());
                intent.putExtra("imageUri", imageUri.toString());
                intent.putExtra("requestCode", requestCode);
                intent.setAction("com.data");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendBroadcast(intent);
                    }
                }, 500);
            }
        }

    }

}

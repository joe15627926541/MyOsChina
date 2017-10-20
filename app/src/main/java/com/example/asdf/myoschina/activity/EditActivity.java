package com.example.asdf.myoschina.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.util.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asdf on 2017/6/3.
 */

public class EditActivity extends Activity implements View.OnClickListener {
    private EditText edt_edit;
    private RelativeLayout rl_pic;
    private ImageView iv_delete;
    private ImageView iv_show;
    private TextView tv_size;
    private ImageView edit_iv_album;
    private ImageView edit_iv_mention;
    private ImageView edit_iv_trend;
    private ImageView edit_iv_emoji;
    private TextView tv_back;
    private TextView tv_send;

    //自定义表情包的面板
    private LinearLayout ll_emji_bottom;
    private ViewPager viewpager;
    private ImageView iv_delete_emoji;
    //填充viewpager的view
    private GridView gv1;


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

    /**
     * 显示的数据
     */
    private List<Integer> data = new ArrayList<Integer>();
    private AlertDialog alertDialog;
    private NotificationManager manager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit);
        init();
        initListener();

    }

    private void initListener() {
        tv_back.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        edit_iv_album.setOnClickListener(this);
        edit_iv_mention.setOnClickListener(this);
        edit_iv_trend.setOnClickListener(this);
        edit_iv_emoji.setOnClickListener(this);
        iv_delete_emoji.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        //输入框的监听
        edt_edit.addTextChangedListener(new TextWatcher() {


            private int length;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length = edt_edit.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int Lastlength = edt_edit.getText().toString().length();

                tv_size.setText((160 - Lastlength - length) + "x");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void init() {
        edt_edit = (EditText) findViewById(R.id.edt_edit);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        rl_pic = (RelativeLayout) findViewById(R.id.rl_pic);
        iv_show = (ImageView) findViewById(R.id.iv_show);
        edit_iv_album = (ImageView) findViewById(R.id.edit_iv_album);
        edit_iv_mention = (ImageView) findViewById(R.id.edit_iv_mention);
        edit_iv_trend = (ImageView) findViewById(R.id.edit_iv_trend);
        edit_iv_emoji = (ImageView) findViewById(R.id.edit_iv_emoji);
        tv_size = (TextView) findViewById(R.id.tv_size);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_send = (TextView) findViewById(R.id.tv_send);
        //自定义表情包的面板
        ll_emji_bottom = (LinearLayout) findViewById(R.id.ll_emji_bottom);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        iv_delete_emoji = (ImageView) findViewById(R.id.iv_delete_emoji);
        //iv_delete_emoji长按清空数据
        iv_delete_emoji.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                edt_edit.setText("");
                return true;
            }
        });

        addEmogi_1();
        viewpager.setAdapter(new Myadapter());
        //
        SharedPreferences sp = getSharedPreferences("edit_content", MODE_PRIVATE);
        String content = sp.getString("content", "");
        edt_edit.setText(content);
        int number = sp.getInt("number", 160);
        tv_size.setText(number + "x");
        //注册广播
        Myrecevice myrecevice = new Myrecevice();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.data");
        registerReceiver(myrecevice, intentFilter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                //判断是否有编辑
                if (!edt_edit.getText().toString().trim().equals("") && edt_edit != null) {
                    //弹出对话框
                    showExitAlert();
                } else {
                    finish();
                }
                break;
            case R.id.tv_send:
                //TODO
                //处理发送
                SendMessage();
                break;
            case R.id.edit_iv_album:
                //弹出提示框，提供选择
                ShowAlert();
                break;
            case R.id.edit_iv_mention:
                SetEditAtText();
                break;
            case R.id.edit_iv_trend:
                SetEditTrendText();
                break;
            case R.id.edit_iv_emoji:
                ll_emji_bottom.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_delete_emoji:
                if (getEditTextCursorIndex(edt_edit) <= 0) {
                    return;
                } else {
                    //删除光标所在的字符窜
                    edt_edit.getText().delete(getEditTextCursorIndex(edt_edit) - 1, getEditTextCursorIndex(edt_edit));
                }

                break;
        }
    }


    private void SendMessage() {
        if (edt_edit.getText().toString().equals("") || edt_edit == null) {
            Toast.makeText(getApplicationContext(), "请输入要弹送的内容", Toast.LENGTH_SHORT).show();
            return;
        } else {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification build = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                PendingIntent pendintent = PendingIntent.getActivity(EditActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                build = new Notification.Builder(EditActivity.this)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_share)
                        .setContentTitle("发布动弹")
                        .setContentText("用户尚未登录")
                        .setContentIntent(pendintent)
                        .build();
            }
            manager.notify(1, build);
            finish();
        }

    }

    private void showExitAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        View inflate = View.inflate(getApplicationContext(), R.layout.exit_alertdialog, null);
        Button btn_cancel = (Button) inflate.findViewById(R.id.btn_cancel);
        Button btn_confirm = (Button) inflate.findViewById(R.id.btn_confirm);

        builder.setView(inflate);
        alertDialog = builder.create();
        alertDialog.show();
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("edit_content", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("content", edt_edit.getText().toString());
                //ToDo记得把文字剩余记下
                int length = edt_edit.getText().toString().length();
                edit.putInt("number", (160 - length));
                edit.commit();
                alertDialog.dismiss();
                finish();
            }
        });
    }

    private void SetEditTrendText() {
        String content = edt_edit.getText().toString().trim();
        edt_edit.setText(content + "#请输入用户名");
    }

    /*
    * 在输入框添加@请输入用户名
    * */
    private void SetEditAtText() {
        String content = edt_edit.getText().toString().trim();
        edt_edit.setText(content + "@请输入软件名");
    }

    private void ShowAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        View inflate = View.inflate(getApplicationContext(), R.layout.edit_alert_album, null);
        TextView tv_album = (TextView) inflate.findViewById(R.id.tv_album);
        TextView tv_creamer = (TextView) inflate.findViewById(R.id.tv_creamer);
        Button btn_cancel = (Button) inflate.findViewById(R.id.btn_cancel);
        builder.setView(inflate);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调起相册
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, RESULT_LOAD_IMAGE);

                alertDialog.dismiss();
            }
        });
        tv_creamer.setOnClickListener(new View.OnClickListener() {
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
                alertDialog.dismiss();
            }
        });

    }

    /*
    * 选中相册调用
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            rl_pic.setVisibility(View.VISIBLE);
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            iv_show.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl_pic.setVisibility(View.GONE);
                }
            });

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

                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    rl_pic.setVisibility(View.VISIBLE);
                    iv_show.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
                    iv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rl_pic.setVisibility(View.GONE);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * 加入所有表情
     */
    private void addEmogi_1() {
        data.add(R.drawable.smiley_0);
        data.add(R.drawable.smiley_1);
        data.add(R.drawable.smiley_2);
        data.add(R.drawable.smiley_3);
        data.add(R.drawable.smiley_4);
        data.add(R.drawable.smiley_5);
        data.add(R.drawable.smiley_6);
        data.add(R.drawable.smiley_7);
        data.add(R.drawable.smiley_8);
        data.add(R.drawable.smiley_9);
        data.add(R.drawable.smiley_10);
        data.add(R.drawable.smiley_11);
        data.add(R.drawable.smiley_12);
        data.add(R.drawable.smiley_13);
        data.add(R.drawable.smiley_14);
        data.add(R.drawable.smiley_15);
        data.add(R.drawable.smiley_16);
        data.add(R.drawable.smiley_17);
        data.add(R.drawable.smiley_18);
        data.add(R.drawable.smiley_19);
        data.add(R.drawable.smiley_20);
        data.add(R.drawable.smiley_21);
        data.add(R.drawable.smiley_22);
        data.add(R.drawable.smiley_23);
        data.add(R.drawable.smiley_24);
        data.add(R.drawable.smiley_25);
        data.add(R.drawable.smiley_26);
        data.add(R.drawable.smiley_27);
        data.add(R.drawable.smiley_28);
        data.add(R.drawable.smiley_29);
        data.add(R.drawable.smiley_30);
        data.add(R.drawable.smiley_31);
        data.add(R.drawable.smiley_32);
        data.add(R.drawable.smiley_33);
        data.add(R.drawable.smiley_34);
        data.add(R.drawable.smiley_35);
        data.add(R.drawable.smiley_36);
        data.add(R.drawable.smiley_37);
        data.add(R.drawable.smiley_38);
        data.add(R.drawable.smiley_39);
        data.add(R.drawable.smiley_40);
        data.add(R.drawable.smiley_41);
        data.add(R.drawable.smiley_42);
        data.add(R.drawable.smiley_43);
        data.add(R.drawable.smiley_44);
        data.add(R.drawable.smiley_45);
        data.add(R.drawable.smiley_46);
        data.add(R.drawable.smiley_47);
        data.add(R.drawable.smiley_48);
        data.add(R.drawable.smiley_49);
        data.add(R.drawable.smiley_50);
        data.add(R.drawable.smiley_51);
        data.add(R.drawable.smiley_52);
        data.add(R.drawable.smiley_53);
        data.add(R.drawable.smiley_54);
        data.add(R.drawable.smiley_55);
        data.add(R.drawable.smiley_56);
        data.add(R.drawable.smiley_57);
        data.add(R.drawable.smiley_58);
        data.add(R.drawable.smiley_59);
        data.add(R.drawable.smiley_60);
        data.add(R.drawable.smiley_61);
        data.add(R.drawable.smiley_62);
        data.add(R.drawable.smiley_63);
        data.add(R.drawable.smiley_64);
        data.add(R.drawable.smiley_65);
        data.add(R.drawable.smiley_66);
        data.add(R.drawable.smiley_67);
        data.add(R.drawable.smiley_68);
        data.add(R.drawable.smiley_69);
        data.add(R.drawable.smiley_70);
        data.add(R.drawable.smiley_71);
        data.add(R.drawable.smiley_72);
        data.add(R.drawable.smiley_73);
        data.add(R.drawable.smiley_74);
        data.add(R.drawable.smiley_75);
        data.add(R.drawable.smiley_76);
        data.add(R.drawable.smiley_77);
        data.add(R.drawable.smiley_78);
        data.add(R.drawable.smiley_79);
        data.add(R.drawable.smiley_80);
        data.add(R.drawable.smiley_81);
        data.add(R.drawable.smiley_82);
        data.add(R.drawable.smiley_83);
        data.add(R.drawable.smiley_84);
        data.add(R.drawable.smiley_85);
        data.add(R.drawable.smiley_86);
        data.add(R.drawable.smiley_87);
        data.add(R.drawable.smiley_88);
        data.add(R.drawable.smiley_89);
        data.add(R.drawable.smiley_90);
        data.add(R.drawable.smiley_91);
        data.add(R.drawable.smiley_92);
        data.add(R.drawable.smiley_93);
        data.add(R.drawable.smiley_94);
        data.add(R.drawable.smiley_95);
        data.add(R.drawable.smiley_96);
        data.add(R.drawable.smiley_97);
        data.add(R.drawable.smiley_98);
        data.add(R.drawable.smiley_99);
        data.add(R.drawable.smiley_100);
        data.add(R.drawable.smiley_101);
        data.add(R.drawable.smiley_102);
        data.add(R.drawable.smiley_103);
        data.add(R.drawable.smiley_104);

    }

    //viewpager的适配器
    class Myadapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            for (int i = 0; i < 2; i++) {
                //GridView去填充viewpager的内容
                gv1 = new GridView(EditActivity.this);
                gv1.setNumColumns(7);
                gv1.setHorizontalSpacing(util.px2dip(EditActivity.this, 20));
                gv1.setVerticalSpacing(util.px2dip(EditActivity.this, 20));
                //设置GridView的适配器
                gv1.setAdapter(new MyListViewAdapter());
                //设置item点击事件
                gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //把表情转换为可设置在输入框的样式
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
                            public Drawable getDrawable(String source) {
                                int id = Integer.parseInt(source);
                                Drawable d = getResources().getDrawable(id);
                                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                                return d;
                            }
                        };

                        CharSequence cs = Html.fromHtml("<img src='" + data.get(position) + "'/>", imageGetter, null);
                        edt_edit.getText().append(cs);
                    }
                });
                container.addView(gv1);
            }
            return gv1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //gridview的适配器
    class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
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
            View inflate = View.inflate(getApplicationContext(), R.layout.item_recycleview, null);
            ImageView iv = (ImageView) inflate.findViewById(R.id.iv);
            iv.setImageResource(data.get(position));
            return inflate;
        }
    }

    /*
    * 把表情变为可输入的格式的方法
    *
    * */
    public static String FilterHtml(String str) {
        str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
        return str;
    }

    /**
     * 获取EditText光标所在的位置
     */
    private int getEditTextCursorIndex(EditText mEditText) {
        return mEditText.getSelectionStart();
    }

    class Myrecevice extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int requestCode = intent.getIntExtra("requestCode", -1);
            if (requestCode == 1) {
                rl_pic.setVisibility(View.VISIBLE);
                String str = intent.getStringExtra("data");
                Uri selectedImage = Uri.parse(str);

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                iv_show.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                iv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl_pic.setVisibility(View.GONE);
                    }
                });

            } else if (requestCode == 3) {
                try {

                    String strimageUri = intent.getStringExtra("imageUri");
                    imageUri= Uri.parse(strimageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(EditActivity.this.imageUri));
                    rl_pic.setVisibility(View.VISIBLE);
                    iv_show.setImageBitmap(bitmap); // 将裁剪后的照片显示出来

                    iv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rl_pic.setVisibility(View.GONE);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == -1) {
                return;
            }

        }
    }


}

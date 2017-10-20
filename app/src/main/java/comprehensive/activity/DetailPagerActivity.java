package comprehensive.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.util.constansts;
import com.example.asdf.myoschina.util.util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import comprehensive.domain.newsInfo;
import comprehensive.fragment.BlogDetailFragment;
import comprehensive.fragment.HotDetailFragment;
import comprehensive.fragment.NewsDetailFragment;
import comprehensive.fragment.RecommendDetailFragment;
import convery.domain.ActivityDetailInfo;
import convery.domain.MyActivityInfo;
import convery.fragment.ActivityDetailFragment;

/**
 * Created by ba0ch3ng on 2017/6/23.
 */

public class DetailPagerActivity extends FragmentActivity {
    private TextView tv_back_detail;
    private TextView tv_content_detail;
    private TextView tv_commend;
    private TextView tv_comment_send;
    private ImageView iv_opt;
    private ImageView iv_review;
    private ImageView iv_write;
    private ImageView iv_star;
    private ImageView iv_send;
    private ImageView iv_btn_opt;
    private ImageView iv_emoji;
    private EditText edt_input;
    private FrameLayout fl_detail;
    private LinearLayout ll_visibility;
    private LinearLayout ll_show;
    private TextView tv_refresh_detail;
    private newsInfo info;
    private MyActivityInfo infos;
    private int position;
    private int type;
    //生明四个详情页
    private NewsDetailFragment newsdetailfragment;
    private HotDetailFragment hotdetailfragment;
    private BlogDetailFragment blogdetailfragment;
    private RecommendDetailFragment recommenddetailfragment;
    private ActivityDetailFragment Activitydetailfragment;

    //笑脸的
    private List<Integer> data;
    private LinearLayout ll_emji_bottom;
    private ViewPager viewpager;
    private ImageView iv_delete_emoji;
    private GridView gv1;
    //判断键盘是否打开
    private boolean isopen;
    private boolean isshake;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        type = intent.getIntExtra("type", 0);
        isshake = intent.getBooleanExtra("Isshake", false);
        if (type == constansts.ActivityTYPE) {
            infos = (MyActivityInfo) intent.getSerializableExtra("Info");
        } else {
            info = (newsInfo) intent.getSerializableExtra("Info");
        }

        init();
        addEmogi_1();
        viewpager.setAdapter(new Myadapter());
        initListener();
        Gson gson = new Gson();
    }

    private void initListener() {
        //返回
        tv_back_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //语音
        iv_btn_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_visibility.setVisibility(View.GONE);
                ll_show.setVisibility(View.VISIBLE);
                ll_emji_bottom.setVisibility(View.GONE);
            }
        });
        //语音相同位置
        iv_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_show.setVisibility(View.GONE);
                ll_visibility.setVisibility(View.VISIBLE);

            }
        });
        //笑脸
        iv_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isopen) {
                    ll_emji_bottom.setVisibility(View.VISIBLE);
                    iv_emoji.setImageResource(R.drawable.compose_toolbar_emoji_pressed);
                    //隐藏输入面板
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_input.getWindowToken(), 0);
                    isopen = true;
                } else {
                    ll_emji_bottom.setVisibility(View.GONE);
                    iv_emoji.setImageResource(R.drawable.compose_toolbar_emoji_normal);
                    isopen = false;
                }

            }
        });
        //发送
        tv_comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_emji_bottom.setVisibility(View.GONE);
                String content = edt_input.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    final AlertDialog alertDialog;
                    //提交评论
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailPagerActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                    View inflate = UIUtils.inflate(R.layout.alert_commit);
                    builder.setView(inflate);
                    alertDialog = builder.create();
                    alertDialog.show();
                    Window window = alertDialog.getWindow();
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.alpha = 0.8f;
                    window.setAttributes(lp);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                            Toast.makeText(UIUtils.getContext(), "评论失败", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);


                } else {
                    Toast.makeText(UIUtils.getContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //评论在上方
        iv_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //写信
        iv_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_show.setVisibility(View.GONE);
                ll_visibility.setVisibility(View.VISIBLE);
            }
        });
        //星星
        iv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "用户未登陆", Toast.LENGTH_SHORT).show();
            }
        });
        //一键分享
        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        //删除表情
        iv_delete_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getEditTextCursorIndex(edt_input) <= 0) {
                    return;
                } else {
                    //删除光标所在的字符窜
                    edt_input.getText().delete(getEditTextCursorIndex(edt_input) - 1, getEditTextCursorIndex(edt_input));
                }

            }
        });
        //点击输入框时，隐藏表情面板
        edt_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_emji_bottom.setVisibility(View.GONE);
            }
        });
        //刷新界面
        tv_refresh_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UIUtils.getContext(), "正在刷新...", Toast.LENGTH_LONG).show();


                if (type == constansts.NEWSTYPE) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    if (newsdetailfragment == null) {
                        newsdetailfragment = new NewsDetailFragment();
                    }
                    if (!newsdetailfragment.isVisible()) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        newsdetailfragment.setArguments(bundle);
                        tv_content_detail.setText("咨询详情");
                        transaction.replace(R.id.fl_detail, newsdetailfragment);
                        transaction.commit();
                    } else {
                        return;
                    }

                }
                if (type == constansts.HOTTYPE) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    if (hotdetailfragment == null) {
                        hotdetailfragment = new HotDetailFragment();
                    }
                    if (!hotdetailfragment.isVisible()) {
                        tv_content_detail.setText("热点详情");
                        transaction.replace(R.id.fl_detail, hotdetailfragment);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        hotdetailfragment.setArguments(bundle);
                        transaction.commit();
                    } else {
                        return;
                    }

                }
                if (type == constansts.BLOGTYPE) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    if (blogdetailfragment == null) {
                        blogdetailfragment = new BlogDetailFragment();
                    }
                    if (!blogdetailfragment.isVisible()) {
                        tv_content_detail.setText("博客详情");
                        transaction.replace(R.id.fl_detail, blogdetailfragment);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        blogdetailfragment.setArguments(bundle);
                        transaction.commit();
                    } else {
                        return;
                    }

                }
                if (type == constansts.RECOMMENDTYPE) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    if (recommenddetailfragment == null) {
                        recommenddetailfragment = new RecommendDetailFragment();
                    }
                    if (!recommenddetailfragment.isVisible()) {
                        tv_content_detail.setText("评论详情");
                        transaction.replace(R.id.fl_detail, recommenddetailfragment);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        recommenddetailfragment.setArguments(bundle);
                        transaction.commit();
                    } else {
                        return;
                    }

                }


            }
        });


    }

    private void init() {
        tv_back_detail = (TextView) findViewById(R.id.tv_back_detail);
        tv_content_detail = (TextView) findViewById(R.id.tv_content_detail);
        tv_commend = (TextView) findViewById(R.id.tv_commend);
        tv_comment_send = (TextView) findViewById(R.id.tv_comment_send);
        tv_refresh_detail = (TextView) findViewById(R.id.tv_refresh_detail);
        iv_opt = (ImageView) findViewById(R.id.iv_opt);
        iv_review = (ImageView) findViewById(R.id.iv_review);
        iv_write = (ImageView) findViewById(R.id.iv_write);
        iv_star = (ImageView) findViewById(R.id.iv_star);
        iv_send = (ImageView) findViewById(R.id.iv_send);
        iv_btn_opt = (ImageView) findViewById(R.id.iv_btn_opt);
        iv_emoji = (ImageView) findViewById(R.id.iv_emoji);
        edt_input = (EditText) findViewById(R.id.edt_input);
        fl_detail = (FrameLayout) findViewById(R.id.fl_detail);
        ll_visibility = (LinearLayout) findViewById(R.id.ll_visibility);
        ll_show = (LinearLayout) findViewById(R.id.ll_show);
        //笑脸的
        ll_emji_bottom = (LinearLayout) findViewById(R.id.ll_emji_bottom);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        iv_delete_emoji = (ImageView) findViewById(R.id.iv_delete_emoji);


        //设置评论量
        if (isshake) {
            if (type == constansts.ActivityTYPE) {

            } else {
                tv_commend.setText(info.getCommentCount() + "");
            }
        } else {
            if (type == constansts.ActivityTYPE) {

            } else {
                tv_commend.setText(info.getCommentCount() + "");
            }
        }
        refresh();

    }


    /**
     * 加入所有表情
     */
    private void addEmogi_1() {
        data = new ArrayList<Integer>();
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

    private void refresh() {

        //判断是什么类型（咨询，热点，博客，推荐）

        if (type == constansts.NEWSTYPE) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (newsdetailfragment == null) {
                newsdetailfragment = new NewsDetailFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            newsdetailfragment.setArguments(bundle);
            tv_content_detail.setText("咨询详情");
            transaction.replace(R.id.fl_detail, newsdetailfragment);
            transaction.commit();
        }
        if (type == constansts.HOTTYPE) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (hotdetailfragment == null) {
                hotdetailfragment = new HotDetailFragment();
            }
            tv_content_detail.setText("热点详情");
            transaction.replace(R.id.fl_detail, hotdetailfragment);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            hotdetailfragment.setArguments(bundle);
            transaction.commit();
        }
        if (type == constansts.BLOGTYPE) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (blogdetailfragment == null) {
                blogdetailfragment = new BlogDetailFragment();
            }
            tv_content_detail.setText("博客详情");
            transaction.replace(R.id.fl_detail, blogdetailfragment);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            blogdetailfragment.setArguments(bundle);
            transaction.commit();
        }
        if (type == constansts.RECOMMENDTYPE) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (recommenddetailfragment == null) {
                recommenddetailfragment = new RecommendDetailFragment();
            }
            tv_content_detail.setText("评论详情");
            transaction.replace(R.id.fl_detail, recommenddetailfragment);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            recommenddetailfragment.setArguments(bundle);
            transaction.commit();
        }
        //活动的界面
        if (type == constansts.ActivityTYPE) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (Activitydetailfragment == null) {
                Activitydetailfragment = new ActivityDetailFragment();
            }
            tv_content_detail.setText("活动");
            transaction.replace(R.id.fl_detail, Activitydetailfragment);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            Activitydetailfragment.setArguments(bundle);
            transaction.commit();
        }


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
                gv1 = new GridView(UIUtils.getContext());
                gv1.setNumColumns(7);
                gv1.setHorizontalSpacing(util.px2dip(UIUtils.getContext(), 20));
                gv1.setVerticalSpacing(util.px2dip(UIUtils.getContext(), 20));
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
                        edt_input.getText().append(cs);
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

    /**
     * 获取EditText光标所在的位置
     */
    private int getEditTextCursorIndex(EditText mEditText) {
        return mEditText.getSelectionStart();
    }
    //一键分享的方法
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享标题");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本，啦啦啦~http://baidu.com/");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3843794586,3401769359&fm=27&gp=0.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // Url：仅在QQ空间使用
        oks.setTitleUrl("http://www.itheima.com/special/hmjavaeezly/?jingjia-heima-Java-xin-pc-heimachengxuyuanjavapeixun");  //网友点进链接后，可以看到分享的详情
         // 启动分享GUI
        oks.show(this);
    }
}

package move.activity;

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
import com.example.asdf.myoschina.util.util;

import java.util.ArrayList;
import java.util.List;

import comprehensive.activity.DetailPagerActivity;
import comprehensive.domain.newsInfo;
import move.domain.recentmoveInfo;
import move.fragment.MovepagerFragment;

/**
 * Created by ba0ch3ng on 2017/7/19.
 */

public class MoveDetailActivity extends FragmentActivity {
    private TextView tv_back_detail_move;
    private FrameLayout fl_detail_move;
    private EditText edt_input_move;
    private ImageView iv_emoji_move;
    private TextView tv_comment_send_move;
    private LinearLayout ll_emji_bottom;
    private ViewPager viewpager_move;
    private ImageView iv_delete_emoji;
    private List<Integer> data;
    private GridView gv1;
    private boolean isclick;
    private InputMethodManager imm;
    private int position;
    private recentmoveInfo recentmoveInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailmove);
        init();
        initListener();
    }

    private void initListener() {
        iv_emoji_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isclick) {
                    iv_emoji_move.setImageResource(R.drawable.compose_toolbar_emoji_normal);
                    ll_emji_bottom.setVisibility(View.GONE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    isclick = false;
                } else {
                    iv_emoji_move.setImageResource(R.drawable.compose_toolbar_emoji_pressed);
                    ll_emji_bottom.setVisibility(View.VISIBLE);
                    //隐藏输入面板
                    imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_input_move.getWindowToken(), 0);
                    isclick = true;
                }
            }
        });
        //删除表情
        iv_delete_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getEditTextCursorIndex(edt_input_move) <= 0) {
                    return;
                } else {
                    //删除光标所在的字符窜
                    edt_input_move.getText().delete(getEditTextCursorIndex(edt_input_move) - 1, getEditTextCursorIndex(edt_input_move));
                }

            }
        });
        //点击输入框时，隐藏表情面板
        edt_input_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_emji_bottom.setVisibility(View.GONE);
            }
        });
        tv_back_detail_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发送
        tv_comment_send_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_emji_bottom.setVisibility(View.GONE);
                String content = edt_input_move.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    final AlertDialog alertDialog;
                    //提交评论
                    AlertDialog.Builder builder = new AlertDialog.Builder(MoveDetailActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                    View inflate = UIUtils.inflate(R.layout.alert_commit);
                    builder.setView(inflate);
                    alertDialog = builder.create();
                    alertDialog.show();
                    Window window = alertDialog.getWindow();
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.alpha = 0.5f;
                    window.setAttributes(lp);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                            Toast.makeText(UIUtils.getContext(), "评论失败", Toast.LENGTH_SHORT).show();
                            imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edt_input_move.getWindowToken(), 0);

                        }
                    }, 2000);


                } else {
                    Toast.makeText(UIUtils.getContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
                    imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_input_move.getWindowToken(), 0);
                }


            }
        });


    }

    private void init() {
        tv_back_detail_move = (TextView) findViewById(R.id.tv_back_detail_move);
        fl_detail_move = (FrameLayout) findViewById(R.id.fl_detail_move);
        edt_input_move = (EditText) findViewById(R.id.edt_input_move);
        iv_emoji_move = (ImageView) findViewById(R.id.iv_emoji_move);
        tv_comment_send_move = (TextView) findViewById(R.id.tv_comment_send_move);
        ll_emji_bottom = (LinearLayout) findViewById(R.id.ll_emji_bottom);
        viewpager_move = (ViewPager) findViewById(R.id.viewpager_move);
        iv_delete_emoji = (ImageView) findViewById(R.id.iv_delete_emoji);
        addEmogi_1();
        viewpager_move.setAdapter(new Myadapter());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MovepagerFragment movepagerFragment = new MovepagerFragment();
        transaction.replace(R.id.fl_detail_move,movepagerFragment);
        transaction.commit();
        // TODO: 2017/7/20
        /*movepagerFragment.loadData();*/
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
                        edt_input_move.getText().append(cs);
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
}

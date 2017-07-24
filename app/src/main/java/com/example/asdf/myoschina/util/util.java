package com.example.asdf.myoschina.util;

import android.content.Context;

/**
 * Created by asdf on 2017/6/2.
 */

public class util {
    /*
    * dp转px
    *
    * */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*
  * px转dp
  *
  * */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}

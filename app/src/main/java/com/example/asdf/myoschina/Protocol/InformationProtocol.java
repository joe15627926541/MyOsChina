package com.example.asdf.myoschina.Protocol;

import android.content.SharedPreferences;

import com.example.asdf.myoschina.XmlParse.InformationXmlParser;
import com.example.asdf.myoschina.XmlParse.SoftWareXmlParser;
import com.example.asdf.myoschina.domain.SoftWareInfo;
import com.example.asdf.myoschina.util.UIUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ba0ch3ng on 2017/6/19.
 */

public class InformationProtocol extends BaseProtocol<ArrayList<SoftWareInfo>> {
    /*
    *
    *
    * */
    @Override
    public String getKey() {
        return "search_list";
    }

    @Override
    public String getParams() {
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("Default", UIUtils.getContext().MODE_PRIVATE);
        String content = sp.getString("content", "毛主席");
        int position = sp.getInt("PagerPosition", 0);
        String str = null;
        if(position==0){
            str="&catalog=software&pageSize=20&content="+content;
        }if(position==1){
            str="&catalog=post&pageSize=20&content="+content;
        }if(position==2){
            str="&catalog=blog&pageSize=20&content="+content;
        }if(position==3){
            str="&catalog=software&pageSize=20&content="+content;
        }
        System.out.println("---------position咨询"+position+"----str"+str);
        return str;
    }

    @Override
    public ArrayList<SoftWareInfo> parseJson(String result) {
        System.out.println("-----解析咨询"+result);
        ArrayList<SoftWareInfo> softwareinfos= null;
        InputStream is = new ByteArrayInputStream(result.getBytes());
        softwareinfos= InformationXmlParser.InformationxmlParser(is);
        return softwareinfos;
    }
}

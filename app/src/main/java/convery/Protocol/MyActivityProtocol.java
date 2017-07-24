package convery.Protocol;

import com.example.asdf.myoschina.Protocol.BaseProtocol;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import convery.domain.MyActivityInfo;
import convery.xmlParse.MyActivityXmlParser;

/**
 * Created by ba0ch3ng on 2017/6/19.
 */

public class MyActivityProtocol extends BaseProtocol<ArrayList<MyActivityInfo>> {

    /*
    * 咨询的接口news_list?pageIndex=0&catalog=1&pageSize=20
    * event_list?pageIndex=0&pageSize=20
    * */
    @Override
    public String getKey() {
        return "event_list";
    }


    @Override
    public String getParams() {
        return "&pageSize=20";
    }

    @Override
    public ArrayList<MyActivityInfo> parseJson(String result) {
        ArrayList<MyActivityInfo> activityinfos = null;
        InputStream is = new ByteArrayInputStream(result.getBytes());
        activityinfos = MyActivityXmlParser.MyActivityxmlParser(is);
        return activityinfos;
    }
}

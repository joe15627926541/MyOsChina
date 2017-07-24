package convery.xmlParse;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

import comprehensive.domain.newsInfo;
import convery.domain.ActivityDetailInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public  class ActivityDetailXmlParser {

    public  static ArrayList<ActivityDetailInfo> ActivityDetailxmlParser(InputStream in) {
        ArrayList<ActivityDetailInfo> activitydetailinfos = null;
        ActivityDetailInfo activitydetailinfo = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int event = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != event) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        activitydetailinfos = new ArrayList<ActivityDetailInfo>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("post".equals(parser.getName())) {
                            activitydetailinfo = new ActivityDetailInfo();
                        }

                        if ("body".equals(parser.getName())) {
                            String body = parser.nextText();
                            if (null != body) {
                                activitydetailinfo.setBody(body);
                            }
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if ("post".equals(parser.getName()) && (null != activitydetailinfos && null != activitydetailinfo)) {
                            activitydetailinfos.add(activitydetailinfo);
                            activitydetailinfo = null;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activitydetailinfos;
    }






}

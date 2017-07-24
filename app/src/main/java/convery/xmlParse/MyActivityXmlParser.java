package convery.xmlParse;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

import convery.domain.MyActivityInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class MyActivityXmlParser {

    public static  ArrayList<MyActivityInfo> MyActivityxmlParser(InputStream in) {
        ArrayList<MyActivityInfo> activityinfos = null;
        MyActivityInfo activityinfo = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int event = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != event) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        activityinfos = new ArrayList<MyActivityInfo>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("event".equals(parser.getName())) {
                            activityinfo = new MyActivityInfo();
                        }
                        if ("title".equals(parser.getName())) {
                            String title = parser.nextText();
                            if (null != title) {
                                activityinfo.setTitle(title);
                            }
                        }
                        if ("cover".equals(parser.getName())) {
                            String cover = parser.nextText();
                            if (null != cover) {
                               activityinfo.setCover(cover);
                            }
                        }
                        if ("startTime".equals(parser.getName())) {
                            String startTime = parser.nextText();
                            if (null != startTime) {
                                activityinfo.setStartTime(startTime);
                            }
                        }
                        if ("spot".equals(parser.getName())) {
                            String spot = parser.nextText();
                            if (null !=spot) {
                                activityinfo.setSpot(spot);
                            }

                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if ("event".equals(parser.getName()) && (null != activityinfos && null != activityinfo)) {
                            activityinfos.add(activityinfo);
                            activityinfo = null;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return activityinfos;
    }






}

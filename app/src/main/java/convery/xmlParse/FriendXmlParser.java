package convery.xmlParse;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

import convery.domain.FindFriendInfo;
import move.domain.recentmoveInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class FriendXmlParser {


    public static ArrayList<FindFriendInfo> XmlfriendParser(InputStream in) {
        ArrayList<FindFriendInfo> findfriendinfos = null;
        FindFriendInfo findfriendinfo = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int event = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != event) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        findfriendinfos = new ArrayList<FindFriendInfo>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("user".equals(parser.getName())) {
                            findfriendinfo = new FindFriendInfo();
                        }

                        if ("name".equals(parser.getName())) {
                            String name = parser.nextText();
                            if (null != name) {
                                findfriendinfo.setName(name);
                            }
                        }
                        if ("from".equals(parser.getName())) {
                            String from = parser.nextText();
                            if (null != from) {
                               findfriendinfo.setFrom(from);
                            }
                        }

                        if ("gender".equals(parser.getName())) {
                            String gender = parser.nextText();
                            if (null != gender) {
                               findfriendinfo.setGender(gender);
                            }
                        }

                        if ("portrait".equals(parser.getName())) {
                            String portrait = parser.nextText();
                            if (null != portrait) {
                                findfriendinfo.setPortrait(portrait);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("user".equals(parser.getName()) && (null != findfriendinfos && null != findfriendinfo)) {
                            findfriendinfos.add(findfriendinfo);
                            findfriendinfo = null;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return findfriendinfos;
    }


}

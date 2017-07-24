package com.example.myapplication;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class FriendXmlParser {


    public static ArrayList<FriendInfo> xmlparse(InputStream in) {
        ArrayList<FriendInfo> friendinfos = null;
        FriendInfo friendinfo = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int event = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != event) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        friendinfos = new ArrayList<FriendInfo>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("active".equals(parser.getName())) {
                            friendinfo = new FriendInfo();
                        }

                        if ("message".equals(parser.getName())) {
                            String message = parser.nextText();
                            if (null != message) {
                               friendinfo.setMessage(message);
                            }
                        }
                        if ("objecttitle".equals(parser.getName())) {
                            String objecttitle = parser.nextText();
                            if (null != objecttitle) {
                                friendinfo.setObjecttitle(objecttitle);
                            }
                        }
                        if ("pubDate".equals(parser.getName())) {
                            String pubDate = parser.nextText();
                            if (null != pubDate) {
                                friendinfo.setPubDate(pubDate);
                            }

                        }
                        if ("author".equals(parser.getName())) {
                            String author = parser.nextText();
                            if (null != author) {
                                friendinfo.setAuthor(author);
                            }
                        }

                        if ("portrait".equals(parser.getName())) {
                            String portrait = parser.nextText();
                            if (null != portrait) {
                                friendinfo.setPortrait(portrait);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("active".equals(parser.getName()) && (null != friendinfos && null != friendinfo)) {
                            friendinfos.add(friendinfo);
                            friendinfo = null;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendinfos;
    }


}

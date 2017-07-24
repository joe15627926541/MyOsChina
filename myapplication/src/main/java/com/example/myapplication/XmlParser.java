package com.example.myapplication;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class XmlParser {

    public static List<newsInfo> xmlParser(InputStream in) throws Exception {
        List<newsInfo> persons = null;
        newsInfo person = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(in, "utf-8");

        int event = parser.getEventType();
        while (XmlPullParser.END_DOCUMENT != event) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    persons = new ArrayList<newsInfo>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("news".equals(parser.getName())) {
                        person = new newsInfo();
//                        int id = new Integer(parser.getAttributeValue(0));
//                        person.setId(id);
                    }
                    if ("title".equals(parser.getName())) {
                        String name = parser.nextText();
                        if (null != name) {
                            person.setTitle(name);
                        }
                    }
                    if ("body".equals(parser.getName())) {
                        String text = parser.nextText();
                        if (null != text) {
                           person.setBody(text);
                        }
                    }



                    if ("commentCount".equals(parser.getName())) {
                        String text = parser.nextText();
                        if (null != text) {
                            person.setCommentCount(Integer.parseInt(text));
                        }
                    }
                    if ("pubDate".equals(parser.getName())) {
                        String text = parser.nextText();
                        if (null != text) {
                            person.setPubDate(text);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("news".equals(parser.getName()) && (null != persons && null != person)) {
                        persons.add(person);
                        person = null;
                    }
                    break;
            }

            event = parser.next();
        }
        return persons;
    }


}

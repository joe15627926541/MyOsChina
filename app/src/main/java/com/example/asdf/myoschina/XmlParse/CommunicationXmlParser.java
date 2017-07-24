package com.example.asdf.myoschina.XmlParse;

import android.util.Xml;

import com.example.asdf.myoschina.domain.SoftWareInfo;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class CommunicationXmlParser {

    public static ArrayList<SoftWareInfo> CommunicationxmlParser(InputStream in) {
        ArrayList<SoftWareInfo> softwareinfos= null;
       SoftWareInfo softwareinfo= null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int event = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != event) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        softwareinfos = new ArrayList<SoftWareInfo>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("result".equals(parser.getName())) {
                            softwareinfo = new SoftWareInfo();
                        }
                        if ("title".equals(parser.getName())) {
                            String title = parser.nextText();
                            if (null != title) {
                                softwareinfo.setTitle(title);
                            }
                        }
                        if ("description".equals(parser.getName())) {
                            String description = parser.nextText();
                            if (null != description) {
                                softwareinfo.setDescription(description);
                            }
                        }
                        if ("pubDate".equals(parser.getName())) {
                            String pubDate = parser.nextText();
                            if (null != pubDate) {
                                softwareinfo.setPubDate(pubDate);
                            }
                        }

                        if ("author".equals(parser.getName())) {
                            String author = parser.nextText();
                            if (null != author) {
                              softwareinfo.setAuthor(author);
                            }

                        }


                        break;
                    case XmlPullParser.END_TAG:
                        if ("result".equals(parser.getName()) && (null != softwareinfos && null != softwareinfo)) {
                            softwareinfos.add(softwareinfo);
                            softwareinfo= null;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("-----问答"+softwareinfos.size()+softwareinfos.toString());
        return softwareinfos;
    }






}

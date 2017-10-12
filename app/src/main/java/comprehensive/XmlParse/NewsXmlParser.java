package comprehensive.XmlParse;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import comprehensive.domain.newsInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class NewsXmlParser {

    public static ArrayList<newsInfo> xmlParser(InputStream in) {
        ArrayList<newsInfo> newsInfos = null;
        newsInfo newsInfo = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int event = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != event) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        newsInfos = new ArrayList<newsInfo>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("news".equals(parser.getName())) {
                            newsInfo = new newsInfo();
                        }
                        if ("id".equals(parser.getName())) {
                            String id = parser.nextText();
                            if (null != id) {
                                newsInfo.setId(Integer.parseInt(id));
                            }

                        }

                        if ("title".equals(parser.getName())) {
                            String title = parser.nextText();
                            if (null != title) {
                                newsInfo.setTitle(title);
                            }
                        }
                        if ("body".equals(parser.getName())) {
                            String body = parser.nextText();
                            if (null != body) {
                                newsInfo.setBody(body);
                            }
                        }
                        if ("commentCount".equals(parser.getName())) {
                            String commentCount = parser.nextText();
                            if (null != commentCount) {
                                newsInfo.setCommentCount(Integer.parseInt(commentCount));
                            }
                        }
                        if ("pubDate".equals(parser.getName())) {
                            String pubDate = parser.nextText();
                            if (null !=pubDate) {
                                newsInfo.setPubDate(pubDate);
                            }

                        }
                        if ("author".equals(parser.getName())) {
                            String author = parser.nextText();
                            if (null != author) {
                                newsInfo.setAuthor(author);
                            }

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("news".equals(parser.getName()) && (null != newsInfos && null != newsInfo)) {
                            newsInfos.add(newsInfo);
                            newsInfo = null;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsInfos;
    }






}

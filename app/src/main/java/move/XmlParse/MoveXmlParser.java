package move.XmlParse;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

import comprehensive.domain.newsInfo;
import move.domain.recentmoveInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class MoveXmlParser {


    public ArrayList<recentmoveInfo> XmlMoveParser(InputStream in) {
        ArrayList<recentmoveInfo> recentmoveInfos = null;
        recentmoveInfo recentmoveInfo = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int event = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != event) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        recentmoveInfos = new ArrayList<recentmoveInfo>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("tweet".equals(parser.getName())) {
                            recentmoveInfo = new recentmoveInfo();
                        }

                        if ("body".equals(parser.getName())) {
                            String body = parser.nextText();
                            if (null != body) {
                                recentmoveInfo.setBody(body);
                            }
                        }
                        if ("commentCount".equals(parser.getName())) {
                            String commentCount = parser.nextText();
                            if (null != commentCount) {
                                recentmoveInfo.setCommentCount(Integer.parseInt(commentCount));
                            }
                        }
                        if ("pubDate".equals(parser.getName())) {
                            String pubDate = parser.nextText();
                            if (null != pubDate) {
                                recentmoveInfo.setPubDate(pubDate);
                            }

                        }
                        if ("author".equals(parser.getName())) {
                            String author = parser.nextText();
                            if (null != author) {
                                recentmoveInfo.setAuthor(author);
                            }
                        }
                        if ("imgSmall".equals(parser.getName())) {
                            String imgSmall = parser.nextText();
                            if (null != imgSmall) {
                                recentmoveInfo.setImgSmall(imgSmall);
                            }
                        }
                        if ("portrait".equals(parser.getName())) {
                            String portrait = parser.nextText();
                            if (null != portrait) {
                                recentmoveInfo.setPortrait(portrait);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("tweet".equals(parser.getName()) && (null != recentmoveInfos && null != recentmoveInfo)) {
                            recentmoveInfos.add(recentmoveInfo);
                            recentmoveInfo = null;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recentmoveInfos;
    }


}

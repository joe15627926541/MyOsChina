package move.XmlParse;

import android.util.Xml;
import android.widget.Toast;

import com.example.asdf.myoschina.util.UIUtils;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

import move.domain.recentmoveInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class MyMoveXmlParser {


    public static ArrayList<recentmoveInfo> MyXmlMoveParser(InputStream in) {
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
                        if ("id".equals(parser.getName())) {
                            String id = parser.nextText();
                            if (null != id) {
                                recentmoveInfo.setId(Integer.parseInt(id));
                            }

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
        System.out.println("11111我的动弹大小"+recentmoveInfos.size()+recentmoveInfos.toString());
        return recentmoveInfos;
    }


}

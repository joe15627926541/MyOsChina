package move.XmlParse;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

import move.domain.CommentInfo;
import move.domain.recentmoveInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class CommentXmlParser {


    public ArrayList<CommentInfo> XmlCommentParser(InputStream in) {
        ArrayList<CommentInfo> commentinfos = null;
        CommentInfo commentinfo = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int event = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != event) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        commentinfos = new ArrayList<CommentInfo>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("comment".equals(parser.getName())) {
                            commentinfo = new CommentInfo();
                        }

                        if ("content".equals(parser.getName())) {
                            String content = parser.nextText();
                            if (null != content) {
                                commentinfo.setContent(content);
                            }
                        }

                        if ("pubDate".equals(parser.getName())) {
                            String pubDate = parser.nextText();
                            if (null != pubDate) {
                                commentinfo.setPubDate(pubDate);
                            }

                        }
                        if ("author".equals(parser.getName())) {
                            String author = parser.nextText();
                            if (null != author) {
                                commentinfo.setAuthor(author);
                            }
                        }

                        if ("portrait".equals(parser.getName())) {
                            String portrait = parser.nextText();
                            if (null != portrait) {
                                commentinfo.setPortrait(portrait);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("comment".equals(parser.getName()) && (null != commentinfos && null != commentinfo)) {
                            commentinfos.add(commentinfo);
                            commentinfo = null;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentinfos;
    }


}

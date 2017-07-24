package move.XmlParse;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

import comprehensive.domain.newsInfo;
import move.domain.DetailMoveInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class DetailMoveXmlParser {

    public ArrayList<DetailMoveInfo> DetailMovexmlParser(InputStream in) {
        ArrayList<DetailMoveInfo> detailmoveinfos = null;
        DetailMoveInfo detailmoveinfo = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int event = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != event) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        detailmoveinfos = new ArrayList<DetailMoveInfo>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("tweet".equals(parser.getName())) {
                            detailmoveinfo = new DetailMoveInfo();
                        }

                        if ("body".equals(parser.getName())) {
                            String body = parser.nextText();
                            if (null != body) {
                                detailmoveinfo.setBody(body);
                            }
                        }
                        if ("commentCount".equals(parser.getName())) {
                            String commentCount = parser.nextText();
                            if (null != commentCount) {
                                detailmoveinfo.setCommentCount(Integer.parseInt(commentCount));
                            }
                        }
                        if ("pubDate".equals(parser.getName())) {
                            String pubDate = parser.nextText();
                            if (null != pubDate) {
                                detailmoveinfo.setPubDate(pubDate);
                            }

                        }
                        if ("author".equals(parser.getName())) {
                            String author = parser.nextText();
                            if (null != author) {
                                detailmoveinfo.setAuthor(author);
                            }

                        }

                        if ("portrait".equals(parser.getName())) {
                            String portrait = parser.nextText();
                            if (null != portrait) {
                                detailmoveinfo.setPortrait(portrait);
                            }

                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if ("tweet".equals(parser.getName()) && (null != detailmoveinfos && null != detailmoveinfo)) {
                            detailmoveinfos.add(detailmoveinfo);
                            detailmoveinfo = null;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return detailmoveinfos;
    }


}

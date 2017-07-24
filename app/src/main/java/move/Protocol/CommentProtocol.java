package move.Protocol;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.asdf.myoschina.Protocol.BaseProtocol;
import com.example.asdf.myoschina.util.UIUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import move.XmlParse.CommentXmlParser;
import move.XmlParse.MoveXmlParser;
import move.domain.CommentInfo;
import move.domain.recentmoveInfo;

/**
 * Created by ba0ch3ng on 2017/7/20.
 *
 *
 * http://www.oschina.net/action/api/comment_list?pageIndex=0&catalog=3&pageSize=20&id=6066159
 */

public class CommentProtocol extends BaseProtocol<ArrayList<CommentInfo>> {
    @Override
    public String getKey() {
        return "comment_list";
    }

    @Override
    public String getParams() {
        return "&catalog=3&pageSize=20&id=6066159";
    }

    @Override
    public ArrayList<CommentInfo> parseJson(final String result) {
        ArrayList<CommentInfo> commentinfos= null;
        InputStream is = new ByteArrayInputStream(result.getBytes());
        CommentXmlParser commentXmlParser = new CommentXmlParser();
        commentinfos=commentXmlParser.XmlCommentParser(is);
        return commentinfos;
    }
}

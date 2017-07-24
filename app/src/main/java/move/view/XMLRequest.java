package move.view;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;


/**
 * @author HD
 * @date 2015-11-23
 * @package_name com.example.volley_2
 * @file_name XMLRequest.java
 */
public class XMLRequest extends Request<XmlPullParser> {
    private  Listener<XmlPullParser> mListener;

    public XMLRequest(int method, String url, Listener<XmlPullParser> listener,
                      ErrorListener errolistener) {
        super(method, url, errolistener);
        mListener = listener;
        // TODO 自动生成的构造函数存根
    }

    public XMLRequest(int method, String url, ErrorListener listener) {
        super(method, url, listener);
        // TODO 自动生成的构造函数存根
    }

    @Override
    protected Response<XmlPullParser> parseNetworkResponse(
            NetworkResponse response) {
        // TODO 自动生成的方法存根
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(parsed));
            return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            // TODO: handle exception
            return Response.error(new ParseError(e));
        } catch (XmlPullParserException e) {
            // TODO 自动生成的 catch 块
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(XmlPullParser response) {
        // TODO 自动生成的方法存根
        mListener.onResponse(response);
    }

}
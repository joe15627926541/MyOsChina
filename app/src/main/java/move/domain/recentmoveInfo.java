package move.domain;

import java.io.Serializable;

/**
 * Created by ba0ch3ng on 2017/6/19.
 */

public class recentmoveInfo implements Serializable {
    public String imgSmall;
    public String body;
    public String pubDate;
    public String author;
    public int commentCount;
    public int likeCount;
    public String portrait;
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "recentmoveInfo{" +
                "imgSmall='" + imgSmall + '\'' +
                ", body='" + body + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", author='" + author + '\'' +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                ", portrait='" + portrait + '\'' +
                '}';
    }
}

package move.domain;

/**
 * Created by ba0ch3ng on 2017/6/26.
 */

public class CommentInfo {
    public String portrait;
    public String author;
    public String content;
    public String pubDate;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "portrait='" + portrait + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}

package convery.domain;

import java.io.Serializable;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class MyActivityInfo implements Serializable {
    public String cover;
    public String title;
    public String startTime;
    public String spot;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }
}

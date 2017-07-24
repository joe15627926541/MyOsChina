package convery.domain;

import java.io.Serializable;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class FindFriendInfo implements Serializable {

    public String name;
    public String portrait;
    public String gender;
    public String from;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}

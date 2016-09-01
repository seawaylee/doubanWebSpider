package cn.edu.ncut.doubanWebSpider.model;

import java.util.Date;

public class UserInfo {
    private Integer id;

    private String userno;

    private String name;

    private String signature;

    private String location;

    private Date time;

    private String introduction;

    private String reading;

    private String hasread;

    private String wantread;

    private String followees;

    private String followers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading == null ? null : reading.trim();
    }

    public String getHasread() {
        return hasread;
    }

    public void setHasread(String hasread) {
        this.hasread = hasread == null ? null : hasread.trim();
    }

    public String getWantread() {
        return wantread;
    }

    public void setWantread(String wantread) {
        this.wantread = wantread == null ? null : wantread.trim();
    }

    public String getFollowees() {
        return followees;
    }

    public void setFollowees(String followees) {
        this.followees = followees == null ? null : followees.trim();
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers == null ? null : followers.trim();
    }
}
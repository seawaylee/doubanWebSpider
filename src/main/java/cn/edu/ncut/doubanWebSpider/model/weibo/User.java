package cn.edu.ncut.doubanWebSpider.model.weibo;

import java.util.Date;

public class User {
    private Integer id;

    private String homepage;

    private Integer followee;

    private Integer follewer;

    private String gender;

    private Integer publish;

    private String nickname;

    private String city;

    private Date registtime;

    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage == null ? null : homepage.trim();
    }

    public Integer getFollowee() {
        return followee;
    }

    public void setFollowee(Integer followee) {
        this.followee = followee;
    }

    public Integer getFollewer() {
        return follewer;
    }

    public void setFollewer(Integer follewer) {
        this.follewer = follewer;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Date getRegisttime() {
        return registtime;
    }

    public void setRegisttime(Date registtime) {
        this.registtime = registtime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
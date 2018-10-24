package com.energysh.quicklogin.bean;

/**
 * Created by linqing.wang on 2018/9/26.
 */

public class CTUserInfoBean {

    /**
     * result : 0
     * userIconUrl : http://open.e.189.cn/avatar/default/default_large.jpg
     * nickName : 天翼用户
     * userIconUrl2 : http://open.e.189.cn/avatar/default/default_middle.jpg
     * owner : 中国电信
     * userIconUrl3 : http://open.e.189.cn/avatar/default/default_small.jpg
     * mobileName : 13518808832
     * openId : d95cd292052675ec388815475528e8d1
     */

    private int result;
    private String msg;
    private String userIconUrl;
    private String nickName;
    private String userIconUrl2;
    private String owner;
    private String userIconUrl3;
    private String mobileName;
    private String openId;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserIconUrl2() {
        return userIconUrl2;
    }

    public void setUserIconUrl2(String userIconUrl2) {
        this.userIconUrl2 = userIconUrl2;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUserIconUrl3() {
        return userIconUrl3;
    }

    public void setUserIconUrl3(String userIconUrl3) {
        this.userIconUrl3 = userIconUrl3;
    }

    public String getMobileName() {
        return mobileName;
    }

    public void setMobileName(String mobileName) {
        this.mobileName = mobileName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}

package com.energysh.quicklogin.bean;

/**
 * Created by linqing.wang on 2018/9/26.
 */

public class CMCCTokenInfoBean {

    /**
     * authType : 网关鉴权
     * resultCode : 103000
     * openId : 9M7RaoZH1DUrJ15ZjJkctppraYpoNKQW9xKtQrcmCGTFONUKeT3w
     * token : 848401000133020037515451304E7A497A4D7A5A4651554A474E6A41784D304E4640687474 703A2F2F3231312E3133362E31302E3133313A383038302F403031030004051C78400400123 83030313230313730373230313030303137050010694969C667EA4D248DFA125D7C4BD35BFF 00207EF179935851E1578B313B366007126A3FD3667BCD2B812EC2D084B8924E7164
     */

    private String authType;
    private String resultCode;
    private String openId;
    private String token;

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

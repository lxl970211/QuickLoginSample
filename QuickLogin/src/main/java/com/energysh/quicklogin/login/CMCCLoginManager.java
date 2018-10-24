package com.energysh.quicklogin.login;

import android.content.Context;

import com.cmic.sso.sdk.auth.AuthnHelper;
import com.cmic.sso.sdk.auth.TokenListener;
import com.energysh.quicklogin.api.Api;
import com.energysh.quicklogin.bean.CMCCParamterBean;
import com.energysh.quicklogin.bean.CMCCTokenInfoBean;
import com.energysh.quicklogin.bean.CMCCUserInfoBean;
import com.energysh.quicklogin.bean.ResultBean;
import com.energysh.quicklogin.constant.AppConstant;
import com.energysh.quicklogin.interfaces.Result;
import com.energysh.quicklogin.util.GsonUtil;
import com.energysh.quicklogin.util.HttpUtil;
import com.energysh.quicklogin.util.ParamsUtil;
import com.energysh.quicklogin.util.ThreadUtil;

import org.json.JSONObject;



/**
 * Created by linqing.wang on 2018/9/26.
 */

public class CMCCLoginManager {
    private static CMCCLoginManager sManager;
    private AuthnHelper mAuthnHelper;


    private CMCCLoginManager() {
    }

    ;

    public static CMCCLoginManager getInstance() {
        if (sManager == null) {
            sManager = new CMCCLoginManager();
        }
        return sManager;
    }

    public void login(Context context, final Result result) {
        //String SDKVersion = mAuthnHelper.SDK_VERSION;
        mAuthnHelper.getTokenExp(AppConstant.CMCC_APP_ID, AppConstant.CMCC_APP_KEY,
                /*AuthnHelper.AUTH_TYPE_DYNAMIC_SMS + AuthnHelper.AUTH_TYPE_SMS*/null, new TokenListener() {
                    @Override
                    public void onGetTokenComplete(JSONObject jsonObject) {
                        //时间
                        CMCCTokenInfoBean cmccTokenInfoBean = GsonUtil.changeGsonToBean(jsonObject.toString(), CMCCTokenInfoBean.class);
                        if (cmccTokenInfoBean != null) {
                            String code = cmccTokenInfoBean.getResultCode();
                            if ("103000".equals(code)) {
                                //获取token成功
                                CMCCParamterBean params = new CMCCParamterBean();
                                params.setStrictcheck("0");
                                params.setVersion("2.0");
                                params.setMsgid(ParamsUtil.generateNonce32());
                                params.setSystemtime(ParamsUtil.getCurrentTime());
                                params.setAppid(AppConstant.CMCC_APP_ID);
                                params.setToken(cmccTokenInfoBean.getToken());
                                params.setSign(params.generateSign(AppConstant.CMCC_APP_KEY));
                                String contentType = "application/json";
                                new HttpUtil().requestHttp(Api.API_CMCC_USER_INFO, params.toJson().toString(), new HttpUtil.Response() {
                                    @Override
                                    public void onSuccess(String info) {
                                        CMCCUserInfoBean cmccUserInfoBean = GsonUtil.changeGsonToBean(info, CMCCUserInfoBean.class);
                                        ResultBean resultBean = new ResultBean();
                                        if (cmccUserInfoBean != null && "103000".equals(cmccUserInfoBean.getResultCode())) {
                                            resultBean.setSuccess("000");
                                            resultBean.setCode(cmccUserInfoBean.getResultCode());
                                            resultBean.setPhoneNumber(cmccUserInfoBean.getMsisdn());
                                            resultBean.setMsg("获取信息成功");
                                        } else {
                                            resultBean.setSuccess("001");
                                            resultBean.setCode("userinfo Parsing failure");
                                            resultBean.setPhoneNumber("");
                                            resultBean.setMsg("用户信息解析失败");
                                        }
                                        result.onResult(resultBean);

                                    }

                                    @Override
                                    public void onError(String errorCode, String msg) {
                                        ResultBean resultBean = new ResultBean();
                                        resultBean.setSuccess("001");
                                        resultBean.setPhoneNumber("");
                                        resultBean.setCode(errorCode);
                                        resultBean.setMsg("获取token失败");
                                        result.onResult(resultBean);
                                    }
                                }, contentType);
                            } else {
                                //获取token失败
                                final ResultBean resultBean = new ResultBean();
                                resultBean.setSuccess("001");
                                resultBean.setCode(cmccTokenInfoBean.getResultCode());
                                resultBean.setPhoneNumber("");
                                resultBean.setMsg("获取token失败");
                                ThreadUtil.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        result.onResult(resultBean);
                                    }
                                });
                            }
                        } else {
                            //获取token失败
                            final ResultBean resultBean = new ResultBean();
                            resultBean.setSuccess("001");
                            resultBean.setPhoneNumber("");
                            resultBean.setCode("token parsing failure");
                            resultBean.setMsg("Token 解析失败");
                            ThreadUtil.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    result.onResult(resultBean);
                                }
                            });
                        }
                    }
                });

    }

    public void initSdk(Context context) {
        mAuthnHelper = AuthnHelper.getInstance(context);
        mAuthnHelper.setTimeOut(8000);
    }
}

package com.energysh.quicklogin.login;

import android.content.Context;

import com.energysh.quicklogin.bean.CTUserInfoBean;
import com.energysh.quicklogin.bean.ResultBean;
import com.energysh.quicklogin.constant.AppConstant;
import com.energysh.quicklogin.interfaces.Result;
import com.energysh.quicklogin.util.GsonUtil;
import com.energysh.quicklogin.util.HttpUtil;
import com.energysh.quicklogin.util.IpUtil;
import com.energysh.quicklogin.util.RequestUtil;
import com.energysh.quicklogin.util.ThreadUtil;

import java.util.HashMap;
import java.util.Map;

import cn.com.chinatelecom.account.lib.app.helper.listener.AuthResultListener;
import cn.com.chinatelecom.account.lib.auth.CtAuth;
import cn.com.chinatelecom.account.lib.base.entities.AuthResultModel;

import static com.energysh.quicklogin.api.Api.API_CT_USER_INFO;
import static com.energysh.quicklogin.constant.AppConstant.CT_APP_ID;
import static com.energysh.quicklogin.constant.AppConstant.CT_APP_KEY;

/**
 * Created by linqing.wang on 2018/9/26.
 */

public class CTLoginManager {

    private static CTLoginManager sManager;


    private CTLoginManager() {
    }

    public static CTLoginManager getInstance() {
        if (sManager == null) {
            sManager = new CTLoginManager();
        }
        return sManager;
    }

    public void login(final Context context, final Result result) {
        //调用免密登录功能前需要初始化
        CtAuth.getInstance().openAuthActivity(context, new AuthResultListener() {
            @Override
            public void onSuccess(AuthResultModel authResultModel) {
                if (authResultModel == null) {

                    ThreadUtil.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ResultBean bean = new ResultBean();
                            bean.setSuccess("001");
                            bean.setCode("token Parsing failure");
                            bean.setPhoneNumber("");
                            bean.setMsg("Token 解析失败");
                            result.onResult(bean);
                        }
                    });
                    return;
                }
                String clientIp = IpUtil.getIPAddress(context);
                String clientType = "";
                String timeStamp = String.valueOf(System.currentTimeMillis());
                String version = "v1.5";
                Map<String, String> requestData = new HashMap<>(0);
                // 公共参数，应用ID
                requestData.put("clientId", AppConstant.CT_APP_ID);
                // 公共参数，IP
                requestData.put("clientIp", clientIp);
                // 公共参数，客户端类型
                requestData.put("clientType", clientType);
                // 公共参数，时间戳
                requestData.put("timeStamp", timeStamp);
                // 公共参数，版本
                requestData.put("version", version);
                // 私有参数，accessToken
                requestData.put("accessToken", authResultModel.accessToken);
                //获取token成功后 请求用户信息获取手机号码
                String contentType = "application/x-www-form-urlencoded;charset=utf-8";
                new HttpUtil().requestHttp(API_CT_USER_INFO, RequestUtil.getParamString(requestData, AppConstant.CT_APP_KEY), new HttpUtil.Response() {
                    @Override
                    public void onSuccess(String info) {
                        CTUserInfoBean infoBean = GsonUtil.changeGsonToBean(info, CTUserInfoBean.class);
                        if (infoBean == null) {
                            ResultBean bean = new ResultBean();
                            bean.setSuccess("001");
                            bean.setPhoneNumber("");
                            bean.setMsg("解析用户信息失败");
                            bean.setCode("userinfo Parsing failure");
                            result.onResult(bean);
                            return;
                        }
                        int code = infoBean.getResult();
                        ResultBean bean = new ResultBean();
                        if (code == 0) {
                            bean.setSuccess("000");
                            bean.setCode(code + "");
                            bean.setPhoneNumber(infoBean.getMobileName());
                            bean.setMsg("获取用户信息成功");
                        } else {
                            bean.setSuccess("001");
                            bean.setCode(code + "");
                            bean.setPhoneNumber("");
                            bean.setMsg("获取用户信息失败");
                        }
                        result.onResult(bean);
                    }

                    @Override
                    public void onError(String errorCode, String msg) {
                        ResultBean bean = new ResultBean();
                        bean.setSuccess("001");
                        bean.setCode(errorCode);
                        bean.setPhoneNumber("");
                        bean.setMsg(msg);
                        result.onResult(bean);
                    }
                }, contentType);
            }

            @Override
            public void onFail(final AuthResultModel authResultModel) {

                //获取token 失败
                ThreadUtil.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        ResultBean resultBean = new ResultBean();
                        resultBean.setSuccess("001");
                        resultBean.setPhoneNumber("");
                        resultBean.setCode(authResultModel.result + "");
                        resultBean.setMsg(authResultModel.msg);
                        result.onResult(resultBean);
                    }
                });

            }

            @Override
            public void onCustomDeal(final int i, final String s) {


                //获取token 失败
                ThreadUtil.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        ResultBean resultBean = new ResultBean();
                        resultBean.setSuccess("001");
                        resultBean.setPhoneNumber("");
                        resultBean.setCode(i + "");
                        resultBean.setMsg(s);
                        result.onResult(resultBean);
                    }
                });
            }
        });
    }

    public void initSdk(Context context) {
        CtAuth.getInstance().init(context, CT_APP_ID, CT_APP_KEY);
    }


}

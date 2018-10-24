package com.energysh.quicklogin.login;

import android.content.Context;

import com.energysh.quicklogin.interfaces.Result;
import com.energysh.quicklogin.util.OperatorsUtil;

import cn.com.chinatelecom.account.lib.auth.CtAuth;

import static com.energysh.quicklogin.constant.AppConstant.CMCC;
import static com.energysh.quicklogin.constant.AppConstant.CT;
import static com.energysh.quicklogin.constant.AppConstant.CT_APP_ID;
import static com.energysh.quicklogin.constant.AppConstant.CT_APP_KEY;
import static com.energysh.quicklogin.constant.AppConstant.CUCC;
import static com.energysh.quicklogin.constant.AppConstant.UNKNOWN;

/**
 * Created by linqing.wang on 2018/9/26.
 * 显示登录 :: 又叫一键登录
 */

public class LoginManager {


    private static LoginManager sManager;

    private LoginManager() {
    }

    ;

    public static LoginManager getInstance() {
        if (sManager == null) {
            sManager = new LoginManager();
        }
        return sManager;
    }


    public void login(Context context, Result result) {
        if (context == null || result == null) {
            return;
        }
        String op = OperatorsUtil.checkOperator(context);
        switch (op) {
            case CMCC://中国移动
                CMCCLoginManager.getInstance().login(context, result);
                break;
            case CUCC:
            case CT://中国电信
                CTLoginManager.getInstance().login(context, result);
                break;
            case UNKNOWN:
            default:
                break;
        }


    }


    public void initSdk(Context context){
        CMCCLoginManager.getInstance().initSdk(context);
        CTLoginManager.getInstance().initSdk(context);
    }

   /* private static void displayLogin() {
        SharePersist.getInstance().putLong(context, "getPrePhoneTimes", 0);
        SharePersist.getInstance().putLong(context, "phonetimes", System.currentTimeMillis());
        authnHelper.getTokenExp(AppConstant.APP_ID, AppConstant.APP_KEY,
                AuthnHelper.AUTH_TYPE_DYNAMIC_SMS + AuthnHelper.AUTH_TYPE_SMS, listener);
    }*/
}

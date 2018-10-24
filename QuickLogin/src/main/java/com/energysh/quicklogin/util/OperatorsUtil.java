package com.energysh.quicklogin.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import static com.energysh.quicklogin.constant.AppConstant.CMCC;
import static com.energysh.quicklogin.constant.AppConstant.CT;
import static com.energysh.quicklogin.constant.AppConstant.CUCC;
import static com.energysh.quicklogin.constant.AppConstant.UNKNOWN;

/**
 * Created by linqing.wang on 2018/9/26.
 */

public class OperatorsUtil {


    private static boolean isNumber(String phoneNum) {
        for (int i = 0; i < phoneNum.length(); i++) {
            if (!Character.isDigit(phoneNum.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /*     * 判断运营商     */
    public static String execute(String phone) {
        String head1 = "";
        String head2 = "";
        phone = phone.trim();
        // 判断输入的电话号码是否合法
        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            return UNKNOWN;
        } else {            // 处理国内的+86开头
            if (phone.startsWith("+")) {
                phone = phone.substring(1);
            }
            if (phone.startsWith("86")) {
                phone = phone.substring(2);
            }
        }
        // 去除+86后电话号码应为11位
        if (phone.length() != 11) {
            return UNKNOWN;
        }
        // 判断去除+86后剩余的是否全为数字
        if (!isNumber(phone)) {
            return UNKNOWN;
        }
        // 截取前3或前4位电话号码，判断运营商
        head1 = phone.substring(0, 3);
        head2 = phone.substring(0, 4);
        // 移动前三位
        boolean cmcctemp3 = head1.equals("134") || head1.equals("135") || head1.equals("136") || head1.equals("137") || head1.equals("138") || head1.equals("139") || head1.equals("147") || head1.equals("150") || head1.equals("151") || head1.equals("152") || head1.equals("157") || head1.equals("158") || head1.equals("159") || head1.equals("182") || head1.equals("183") || head1.equals("184") || head1.equals("178") || head1.equals("187") || head1.equals("188");
        if (cmcctemp3) {
            return CMCC;
        }
        // 移动前4位
        boolean cmcctemp4 = head2.equals("1340") || head2.equals("1341") || head2.equals("1342") || head2.equals("1343") || head2.equals("1344") || head2.equals("1345") || head2.equals("1346") || head2.equals("1347") || head2.equals("1348") || head2.equals("1705");
        if (cmcctemp4) {
            return CMCC;
        }
        // 联通前3位
        boolean unicomtemp = head1.equals("130") || head1.equals("131") || head1.equals("132") || head1.equals("145") || head1.equals("155") || head1.equals("156") || head1.equals("176") || head1.equals("185") || head1.equals("186");
        if (unicomtemp) {
            return CUCC;
        }
        //unicom 4
        boolean unicomtemp4 = head1.equals("1709");
        if (unicomtemp4) {
            return CUCC;
        }
        // 电信前3位
        boolean telecomtemp = head1.equals("133") || head1.equals("153") || head1.equals("181") || head1.equals("177") || head1.equals("180") || head1.equals("189");
        if (telecomtemp) {
            return CT;
        }
        //telecom 4
        boolean telecomtemp4 = head1.equals("1700");
        if (telecomtemp4) {
            return CT;
        }
        return UNKNOWN;
    }


    public static String checkOperator(Context context) {
        String op = UNKNOWN;
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telManager != null) {
            String operator = telManager.getSimOperator();
            if (operator != null) {
                switch (operator) {
                    case "46000":
                    case "46002":
                    case "46007":
                    case "46020":
                    case "41004"://香港移动
                    case "46025":
                        //中国移动
                        op = CMCC;
                        break;
                    case "46001":
                    case "46006":
                    case "46026":
                    case "46009":
                        //中国联通
                        op = CUCC;
                        break;
                    case "46003":
                    case "46005":
                    case "46011":
                    case "46027":
                    case "20404":
                        //中国电信
                        op = CT;
                        break;
                    default:
                        op = CMCC;
                        break;
                }

            }
        }
        return op;
    }

}

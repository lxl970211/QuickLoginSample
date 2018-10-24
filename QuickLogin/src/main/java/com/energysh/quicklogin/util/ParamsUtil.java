package com.energysh.quicklogin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by linqing.wang on 2018/9/26.
 */

public class ParamsUtil {
    public static String generateNonce32() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
    public static String getCurrentTime() {
        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sf.format(data);
    }
}

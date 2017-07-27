package com.dh.util;

import java.lang.reflect.AccessibleObject;
import java.net.InetAddress;

/**
 * @author lixiangyu
 */
public class NetUtil {

    private static final int TIMEOUT = 1000;


    public static boolean isIpReachable(String ip) {
        boolean accessible = false;
        try {
            InetAddress address = InetAddress.getByName(ip);
            accessible = address.isReachable(TIMEOUT);
        } catch (Exception e) {
            accessible = false;
        }
        return accessible;
    }
}
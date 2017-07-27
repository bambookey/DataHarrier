/**
 * @(#)NetUtil.java, 2017/7/27.
 * <p/>
 * Copyright 2017 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.dh.util;

import java.lang.reflect.AccessibleObject;
import java.net.InetAddress;

/**
 * @author lixiangyu(hzlixiangyu@corp.netease.com)
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
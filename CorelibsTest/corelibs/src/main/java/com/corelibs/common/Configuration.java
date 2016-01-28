package com.corelibs.common;

/**
 * 项目配置
 */
public class Configuration {
    /**
     * 是否是调试环境
     */
    private static boolean debug;
    /**
     * 是否打印网络参数
     */
    private static boolean isShowNetworkParams = false;

    /**
     * 是否是调试环境
     */
    public static boolean isDebug() {
        return debug;
    }

    /**
     * 设置调试环境
     */
    public static void setDebug(boolean debug) {
        Configuration.debug = debug;
    }

    /**
     * 开启打印网络请求参数
     */
    public static void enableLoggingNetworkParams() {
        isShowNetworkParams = true;
    }

    /**
     * 关闭打印网络请求参数
     */
    public static void disableLoggingNetworkParams() {
        isShowNetworkParams = false;
    }

    /**
     * 是否打印网络请求参数
     */
    public static boolean isShowNetworkParams() {
        return isShowNetworkParams;
    }
}

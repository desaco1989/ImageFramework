package com.desaco.imageloader.utils;

import android.util.Log;


/**
 * 日志工具
 */
public class LogTagUtil {

    private static boolean isCommonLog = true; // 可以关闭或不可关闭的日志
    private static boolean isDebugLog = true; // 上线需要关闭的日志，测试为true， 上线为false

    private static void printLog(final String logType, final String tag, final String msg) {
        if (isCommonLog) {
//            AndroidUtil.workerThread(new Runnable() {
//                @Override
//                public void run() {
                    if (logType.equals("e")) {
                        Log.e(tag, msg);
                    } else if (logType.equals("v")) {
                        Log.v(tag, msg);
                    } else if (logType.equals("d")) {
                        Log.d(tag, msg);
                    } else if (logType.equals("i")) {
                        Log.i(tag, msg);
                    } else if (logType.equals("w")) {
                        Log.w(tag, msg);
                    } else if (logType.equals("wtf")) {
                        Log.wtf(tag, msg);
                    } else {
                        Log.i(tag, msg);
                    }
                }
//            });
//        }
    }

    public static void v(String tag, String msg) {
        printLog("v", tag, msg);
//        if (isTestEnvironment) {
//            Log.v(tag, msg);
//        }
    }

    public static void d(String tag, String msg) {
        printLog("d", tag, msg);
//        if (isTestEnvironment) {
//            Log.d(tag, msg);
//        }
    }

    public static void i(String tag, String msg) {
        printLog("i", tag, msg);
//        if (isTestEnvironment) {
//            Log.i(tag, msg);
//        }
    }

    public static void w(String tag, String msg) {
        printLog("w", tag, msg);
//        if (isTestEnvironment) {
//            Log.w(tag, msg);
//        }
    }

    public static void e(String tag, String msg) {
        printLog("e", tag, msg);
//        if (isTestEnvironment) {
//            Log.e(tag, msg);
//        }
    }

    public static void wtf(String tag, String msg) {
        printLog("wtf", tag, msg);
//        if (isTestEnvironment) {
//            Log.wtf(tag, msg);
//        }
    }

    /**
     * 截断输出日志
     * 日志太长，需要截断日志打印出来
     * 调用此方法可能比较耗时，会造成页面卡顿
     * @param msg
     */
    public static void eDebug(final String tag, final String msg) {
//        if (isDebugLog) {
//            AndroidUtil.workerThread(new Runnable() {
//                @Override
//                public void run() {
//                    String logMsg = msg;
//                    int segmentSize = 3 * 1024; // Android内核日志不得超过4KB
//                    long length = logMsg.length();
//                    if (length <= segmentSize) { // 长度小于等于限制直接打印
//                        Log.e(tag, logMsg);
//                    } else {
//                        while (logMsg.length() > segmentSize) { // 循环分段打印日志
//                            String logContent = logMsg.substring(0, segmentSize);
//                            logMsg = logMsg.replace(logContent, "");
//                            Log.e(tag, logContent);
//                        }
//                        Log.e(tag, logMsg); // 打印剩余日志
//                    }
//                }
//            });
//        }
    }



}

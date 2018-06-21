package com.liudongcai.liuclan.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 项目名称：LiuClan<br>
 * 类描述：<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/21 11:09<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class NetworkUtil {


    /** 当前无网络 **/
    public final static int NONE = 0;
    /** 当前的网络不可用 **/
    public final static int VAIN = 1;
    /** 有WIFI **/
    public final static int WIFI = 2;
    /** 有3G、GPRS网络 **/
    public final static int MOBILE = 3;


    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/6/21 11:09<br>
     * 方法描述：获取网络状态<br>
     */
    public static int getNetworkState(ConnectivityManager connManager) {
        // 如果没有网络
        if (connManager == null) {
            return NONE;
        }
        // 如果网络不可用
        NetworkInfo in = null;
        in = connManager.getActiveNetworkInfo();
        if (in != null) {
            if (!in.isAvailable()) {
                return VAIN;
            }
        }

        in = connManager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE);
        // 手机网络判断
        if(in!=null) {
            NetworkInfo.State state = in.getState();
            if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                return MOBILE;
            }
        }

        in = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        // WIFI网络判断
        if(in!=null) {
            NetworkInfo.State state = in.getState();
            if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                return WIFI;
            }
        }
        return NONE;
    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/6/21 11:09<br>
     * 方法描述：判断是否有网络<br>
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return (ni != null) && (ni.isConnectedOrConnecting());
        }
        return true;
    }

}

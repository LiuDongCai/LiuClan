package com.liudongcai.liuclan.base;

import android.app.Application;

import java.io.File;

import ren.yale.android.cachewebviewlib.CacheWebView;

/**
 * 项目名称：LiuClan<br>
 * 类描述：主程序<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/19 17:52<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class BaseApplication  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        init();

    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/6/21 10:48<br>
     * 方法描述：初始化<br>
     */
    private void init() {

        //CacheWebView
        File cacheFile = new File(this.getCacheDir(), "CoustomWebViewCache");
        CacheWebView.getCacheConfig().init(this, cacheFile.getAbsolutePath(), 1024 * 1024 * 100, 1024 * 1024 * 10)
                .enableDebug(true);//100M 磁盘缓存空间,10M 内存缓存空间

    }

}

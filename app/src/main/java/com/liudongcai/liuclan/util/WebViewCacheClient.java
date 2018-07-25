package com.liudongcai.liuclan.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import ren.yale.android.cachewebviewlib.CacheStatus;
import ren.yale.android.cachewebviewlib.CacheWebView;

/**
 * 项目名称：LiuClan<br>
 * 类描述：webview的client<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/29 17:38<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class WebViewCacheClient extends WebViewClient {

    private CacheWebView mWebView;
    private ArrayList list;
    private Context mContext;
    private String jump_url;


    public WebViewCacheClient(Context context,CacheWebView mWebView,String jump_url,ArrayList<String> list){
        this.mContext = context;
        this.mWebView = mWebView;
        this.jump_url = jump_url;
        this.list = list;
    }

    /*list null 默認jpg,png,ico,js*/
    public WebViewCacheClient(Context context,CacheWebView mWebView,String jump_url){
        this.mContext = context;
        this.jump_url = jump_url;
        this.mWebView = mWebView;
    }

    private ArrayList<String> getList() {
        if (list == null) {
            list = new ArrayList<String>(){{add("jpg"); add("png");add("html");add("gif");add("ico");add("js");}};
        }
        return list;
    }


    public WebResourceResponse getShouldInterceptRequest(WebView view, String url){

        CacheStatus cacheStatus =  mWebView.getWebViewCache().getCacheFile(url);
        if (cacheStatus.isExist()){
            File file = cacheStatus.getCacheFile();
            String extenstion = cacheStatus.getExtension();
            if (this.getList().contains(extenstion)){
                InputStream is = null;
                try {
                    is = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                }
                WebResourceResponse response = new WebResourceResponse(cacheStatus.getExtension(),
                        "utf-8", is);
                return response;
            }else {
                return null;
            }
        }
        return null;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //还原上次访问位置
        ACache aCache=ACache.get(mContext);
        Integer position=(Integer)aCache.getAsObject(jump_url);
        if(position!=null &&mWebView!=null){
            mWebView.scrollTo(0, position);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view,request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url); // 使用当前WebView处理跳转
        return true; // true表示此事件在此处被处理，不需要再广播
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        WebResourceResponse response =  getShouldInterceptRequest(view,request.getUrl().toString());
        if (response != null){
            return response;
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        WebResourceResponse response =  getShouldInterceptRequest(view,url);
        if (response != null){
            return response;
        }
        return super.shouldInterceptRequest(view, url);
    }


}

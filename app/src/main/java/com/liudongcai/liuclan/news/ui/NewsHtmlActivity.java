package com.liudongcai.liuclan.news.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.liudongcai.liuclan.R;
import com.liudongcai.liuclan.base.BaseActivity;
import com.liudongcai.liuclan.util.ACache;
import com.liudongcai.liuclan.util.NetworkUtil;
import com.liudongcai.liuclan.util.WebViewCacheClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import ren.yale.android.cachewebviewlib.CacheWebView;

/**
 * 项目名称：LiuClan<br>
 * 类描述：新闻详细页<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/29 17:38<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class NewsHtmlActivity extends BaseActivity {


    @BindView(R.id.webView)
    CacheWebView mWebView;

    private Context mContext;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_html);

        ButterKnife.bind(this);
        mContext=this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.containsKey("url") ? extras.getString("url") : "";
        }

        setWebView();

    }


    private void setWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true); // 设置使用够执行JS脚本
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true); // 设置使支持缩放
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true); // 支持自动加载图片
        try {
            mWebView.getSettings().setDomStorageEnabled(true); // 开启 DOM storage API 功能
            mWebView.getSettings().setDatabaseEnabled(true);   //开启 database storage API 功能
            mWebView.getSettings().setAppCacheEnabled(true);   //开启 Application Caches 功能
            mWebView.getSettings().setAllowFileAccess(true);   //开启 Application Caches 功能
        } catch (Exception ex) {
        }
        // 设置缓存模式
        if (NetworkUtil.isNetworkConnected(this)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 设置适应屏幕
        if (url.startsWith("http")) {
            mWebView.getSettings().setUseWideViewPort(true); // 设置WebView图片自动缩放
        } else {
            mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        }
        mWebView.getSettings().setDisplayZoomControls(false); // 隐藏缩放控件

        mWebView.loadUrl(url);
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mWebView.setWebViewClient(new WebViewCacheClient(mContext, mWebView, url));
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                if (newProgress == 100) {
//                    adProgress.setProgress(newProgress);
//                } else {
//                    adProgress.setProgress(newProgress);
//                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            //记录访问位置
            ACache aCache = ACache.get(mContext);
            aCache.put(url, mWebView.getScrollY());
            mWebView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.setVisibility(View.GONE);
            mWebView.setDrawingCacheEnabled(true);
            mWebView.destroyDrawingCache();
//            mWebView.destroy();
            mWebView = null;
        }
//        if (adProgress != null) {
//            adProgress.setDrawingCacheEnabled(false);
//            adProgress.destroyDrawingCache();
//        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        if (view != null) {
            view.removeAllViews();
        }
        super.finish();
    }

}
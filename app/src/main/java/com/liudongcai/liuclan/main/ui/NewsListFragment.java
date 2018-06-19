package com.liudongcai.liuclan.main.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liudongcai.liuclan.R;
import com.shizhefei.fragment.LazyFragment;

/**
 * 项目名称：LiuClan<br>
 * 类描述：<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/13 16:30<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class NewsListFragment extends LazyFragment{

    private ProgressBar progressBar;
    private TextView textView;
    private int tabIndex;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(android.os.Message msg) {
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        };

        setContentView(R.layout.fragment_home);
        tabIndex = getArguments().getInt("intent_int_index");
        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);
        textView = (TextView) findViewById(R.id.fragment_mainTab_item_textView);
        textView.setText("界面" + " " + tabIndex + " 加载完毕");
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        handler.removeCallbacksAndMessages(null);
    }

    private Handler handler;

}

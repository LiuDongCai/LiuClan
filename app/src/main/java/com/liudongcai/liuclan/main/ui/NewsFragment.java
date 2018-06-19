package com.liudongcai.liuclan.main.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.liudongcai.liuclan.R;
import com.liudongcai.liuclan.main.adapter.NewsIndicatorAdapter;
import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

/**
 * 项目名称：LiuClan<br>
 * 类描述：新闻界面<br>
 * 创建人：刘栋财<br>
 ：2018/6/13 16:30<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class NewsFragment extends LazyFragment{

    private IndicatorViewPager indicatorViewPager;
    private Context mContext;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_news);
        mContext=getApplicationContext();

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_news);
        Indicator indicator = (Indicator) findViewById(R.id.fiv_news);

        indicator.setScrollBar(new ColorBar(getApplicationContext(), getResources().getColor(R.color.colorPrimary), 5));

        float unSelectSize =15;
        float selectSize = unSelectSize * 1.2f;
        // 设置滚动监听
        indicator.setOnTransitionListener(new OnTransitionTextListener().
                setColor(getResources().getColor(R.color.colorPrimary), Color.BLACK).setSize(selectSize, unSelectSize));

        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);
        // 将viewPager和indicator使用
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        // 设置indicatorViewPager的适配器
        indicatorViewPager.setAdapter(new NewsIndicatorAdapter(mContext,getChildFragmentManager()));

    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }

}

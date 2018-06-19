package com.liudongcai.liuclan.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liudongcai.liuclan.R;
import com.liudongcai.liuclan.main.ui.ClubFragment;
import com.liudongcai.liuclan.main.ui.HomeFragment;
import com.liudongcai.liuclan.main.ui.MoreFragment;
import com.liudongcai.liuclan.main.ui.NewsFragment;
import com.liudongcai.liuclan.util.ViewUtil;
import com.shizhefei.view.indicator.IndicatorViewPager;

/**
 * 项目名称：LiuClan<br>
 * 类描述：<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/13 16:19<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class MainIndicatorAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter{

    private LayoutInflater inflate;
    private Context mContext;

    private String[] names = {"首页",  "新闻", "论坛", "更多"};

    public MainIndicatorAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext=context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.tab_top, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(names[position % names.length]);
        int padding = ViewUtil.dipToPix(mContext,10);
        textView.setPadding(padding, 0, padding, 0);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new NewsFragment();
                break;
            case 2:
                fragment = new ClubFragment();
                break;
            case 3:
                fragment = new MoreFragment();
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("intent_int_index", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
        // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
        return PagerAdapter.POSITION_NONE;
    }

}

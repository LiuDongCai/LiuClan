package com.liudongcai.liuclan.news.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liudongcai.liuclan.R;
import com.liudongcai.liuclan.news.ui.NewsListFragment;
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
public class NewsIndicatorAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter{

    private LayoutInflater inflate;
    private Context mContext;

    private String[] names = {"精选",  "世界杯", "军事", "娱乐", "娱乐", "娱乐", "娱乐", "娱乐", "娱乐"};

    public NewsIndicatorAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext=context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.tab_top, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(names[position % names.length]);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX ,mContext.getResources().getDimension(R.dimen.margin_size12dp));
        int padding = ViewUtil.dipToPix(mContext,10);
        textView.setPadding(padding, 0, padding, 0);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        Fragment fragment=new NewsListFragment();
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

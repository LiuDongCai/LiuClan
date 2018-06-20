package com.liudongcai.liuclan.news.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liudongcai.liuclan.R;
import com.liudongcai.liuclan.news.adapter.NewsListAdapter;
import com.liudongcai.liuclan.news.bean.NewsBean;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：LiuClan<br>
 * 类描述：新闻列表页<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/13 16:30<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class NewsListFragment extends LazyFragment{

    private int tabIndex;
    private Context mContext;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_news_list);
        mContext=getApplicationContext();
        tabIndex = getArguments().getInt("intent_int_index");

        RecyclerView rv_news= (RecyclerView) findViewById(R.id.rv_news);

        List<NewsBean> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle("Chad" + i);
            newsBean.setPhoto("https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2017-09-26/352f1d243122cf52462a2e6cdcb5ed6d.png");
            newsBean.setUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            list.add(newsBean);
        }

        NewsListAdapter listAdapter=new NewsListAdapter(R.layout.item_news_list,list);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(mContext);
        rv_news.setLayoutManager(layoutmanager);//设置RecyclerView 布局
        rv_news.setAdapter(listAdapter);

        //Item的点击事件
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mContext, "onItemClick" + position, Toast.LENGTH_SHORT).show();
            }
        });

        //开启动画(默认为渐显效果)  提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        listAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN );
        //动画默认只执行一次,如果想重复执行可设置
        listAdapter.isFirstOnly(false);

    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }


}

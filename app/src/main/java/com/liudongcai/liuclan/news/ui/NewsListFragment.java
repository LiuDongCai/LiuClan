package com.liudongcai.liuclan.news.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liudongcai.liuclan.R;
import com.liudongcai.liuclan.config.Urls;
import com.liudongcai.liuclan.news.adapter.NewsListAdapter;
import com.liudongcai.liuclan.news.bean.NewsBean;
import com.liudongcai.liuclan.util.JsonAnalyze;
import com.liudongcai.liuclan.util.NetworkUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shizhefei.fragment.LazyFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

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

    private String namesIndex;
    private Context mContext;
    private NewsListAdapter listAdapter;
    private int pageIndex=0;

    private RecyclerView rv_news;
    private SwipeRefreshLayout srl_refresh;
    private ProgressBar pb_news;
    private View emptyView;
    private ImageView iv_empty;
    private TextView tv_empty;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_news_list);
        mContext=getApplicationContext();
        namesIndex = getArguments().getString("namesIndex");

        //初始试图
        initView();

        //请求数据
        getData(true);

    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/6/20 18:22<br>
     * 方法描述：初始试图<br>
     */
    private void initView(){
        rv_news= (RecyclerView) findViewById(R.id.rv_news);
        srl_refresh= (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        pb_news= (ProgressBar) findViewById(R.id.pb_news);


        List<NewsBean> list = new ArrayList<>();
        listAdapter=new NewsListAdapter(R.layout.item_news_list,list);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(mContext);
        rv_news.setLayoutManager(layoutmanager);//设置RecyclerView 布局
        rv_news.setAdapter(listAdapter);

        // 没有数据的时候默认显示该布局
        emptyView=((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                inflate(R.layout.empty_news, null, false);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv_empty= (TextView) emptyView.findViewById(R.id.tv_empty);
        iv_empty= (ImageView) emptyView.findViewById(R.id.iv_empty);
        listAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);


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

        //上拉加载
        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageIndex++;
                getData(false);
            }
        }, rv_news);

        // 设置下拉进度的背景颜色，默认就是白色的
        srl_refresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        srl_refresh.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent,R.color.colorPrimaryRed);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                srl_refresh.setRefreshing(true);
                //刷新数据
                pageIndex=0;
                getData(true);
            }
        });

    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/6/20 18:22<br>
     * 方法描述：请求数据<br>
     */
    private void getData(final boolean isRefresh){
        if(!NetworkUtil.isNetworkConnected(mContext)){
            //无网络连接
            Toasty.normal(mContext, getResources().getString(R.string.request_nonet)).show();
            pb_news.setVisibility(View.GONE);
            srl_refresh.setRefreshing(false);
            emptyView.setVisibility(View.VISIBLE);
            iv_empty.setBackgroundResource(R.mipmap.bg_nonetwork);
            tv_empty.setText(R.string.no_network);
            return;
        }
        String url=Urls.IFENG_BASE_NEWS+namesIndex+"_"+pageIndex+Urls.IFENG_END_NEWS+System.currentTimeMillis();
        System.out.println("结果："+url);
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(isRefresh){
                            // 加载完数据设置为不刷新状态，将下拉进度收起来
                            srl_refresh.setRefreshing(false);
                        }else{
                            listAdapter.loadMoreComplete();
                        }
                        pb_news.setVisibility(View.GONE);
                        //注意这里已经是在主线程了
                        String data = response.body();//这个就是返回来的结果
                        //破解防抓取
                        data=data.replace("getListDatacallback(","").replace(");","");
                        try{
                            JSONArray jsonArray=new JSONArray(data);
                            List<NewsBean> list = new ArrayList<>();
                            if (jsonArray!=null && jsonArray.length()>0){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject= JsonAnalyze.getJSONObject(jsonArray,i);
                                    NewsBean newsBean = new NewsBean();
                                    newsBean.setTitle(JsonAnalyze.getJSONValue(jsonObject,"title"));
                                    newsBean.setPhoto(JsonAnalyze.getJSONValue(jsonObject,"i_thumbnail"));
                                    newsBean.setUrl(JsonAnalyze.getJSONValue(jsonObject,"docUrl"));
                                    list.add(newsBean);
                                    listAdapter.addData(newsBean);
                                }
                                if(isRefresh){//下拉刷新，设置新数据
                                    listAdapter.setNewData(list);
                                }
                            }else{
                                if(isRefresh){
                                    Toasty.normal(mContext, getResources().getString(R.string.no_news)).show();
                                    pb_news.setVisibility(View.GONE);
                                    srl_refresh.setRefreshing(false);
                                    emptyView.setVisibility(View.VISIBLE);
                                    iv_empty.setBackgroundResource(R.mipmap.bg_empty);
                                    tv_empty.setText(R.string.no_news);
                                }else{//没数据、加载完成
                                    listAdapter.loadMoreEnd();
                                }
                            }
                        }catch (Exception e){
                            if(isRefresh){
                                Toasty.normal(mContext, getResources().getString(R.string.no_news)).show();
                                pb_news.setVisibility(View.GONE);
                                srl_refresh.setRefreshing(false);
                                emptyView.setVisibility(View.VISIBLE);
                                iv_empty.setBackgroundResource(R.mipmap.bg_empty);
                                tv_empty.setText(R.string.no_news);
                            }else{
                                listAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //加载失败
                        Toasty.normal(mContext, getResources().getString(R.string.request_failed)).show();
                        pb_news.setVisibility(View.GONE);
                        srl_refresh.setRefreshing(false);
                        emptyView.setVisibility(View.VISIBLE);
                        iv_empty.setBackgroundResource(R.mipmap.bg_failed);
                        tv_empty.setText(R.string.request_fail);
                        if(!isRefresh){
                            listAdapter.loadMoreFail();
                        }
                    }
                });
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }


}

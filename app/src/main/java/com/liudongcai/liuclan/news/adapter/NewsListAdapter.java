package com.liudongcai.liuclan.news.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liudongcai.liuclan.R;
import com.liudongcai.liuclan.news.bean.NewsBean;

import java.util.List;

/**
 * 项目名称：LiuClan<br>
 * 类描述：新闻列表适配器<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/19 17:17<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class NewsListAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> {

    public NewsListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        // 加载网络图片
        Glide.with(mContext)
                .load("http://"+item.getPhoto())
                .into((ImageView) helper.getView(R.id.iv_photo));
    }

}

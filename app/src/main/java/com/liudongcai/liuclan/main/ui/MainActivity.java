package com.liudongcai.liuclan.main.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.liudongcai.liuclan.R;
import com.liudongcai.liuclan.base.BaseActivity;
import com.liudongcai.liuclan.main.adapter.MainIndicatorAdapter;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * 项目名称：LiuClan<br>
 * 类描述：主页<br>
 * 创建人：刘栋财<br>
 * 创建时间：2018/6/13 16:30<br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 *
 * @version V1.0
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.siv_indicator)
    FixedIndicatorView fixedIndicatorView;

    private IndicatorViewPager indicatorViewPager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext=this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //设置导航内容
        setIndicatorView();

    }

    /**
     * 创建人：刘栋财<br>
     * 创建时间：2018/6/13 15:48<br>
     * 方法描述：设置导航内容<br>
     */
    private void setIndicatorView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_viewPager);

        fixedIndicatorView.setScrollBar(new ColorBar(getApplicationContext(), getResources().getColor(R.color.colorPrimary), 5));
        float unSelectSize =15;
        float selectSize = unSelectSize * 1.2f;
        // 设置滚动监听
        fixedIndicatorView.setOnTransitionListener(new OnTransitionTextListener().
                setColor(getResources().getColor(R.color.colorPrimary), Color.BLACK).setSize(selectSize, unSelectSize));

        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);
        // 将viewPager和indicator使用
        indicatorViewPager = new IndicatorViewPager(fixedIndicatorView, viewPager);
        // 设置indicatorViewPager的适配器
        indicatorViewPager.setAdapter(new MainIndicatorAdapter(mContext,getSupportFragmentManager()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //--------------双击退出app--------------

    //记录用户首次点击返回键的时间
    private long firstTime=0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toasty.normal(mContext, getResources().getString(R.string.exit_again)).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

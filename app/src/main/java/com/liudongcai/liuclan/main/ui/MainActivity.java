package com.liudongcai.liuclan.main.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.liudongcai.liuclan.R;
import com.liudongcai.liuclan.main.adapter.IndicatorAdapter;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
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

        fixedIndicatorView.setScrollBar(new ColorBar(getApplicationContext(), Color.RED, 5));
        float unSelectSize =15;
        float selectSize = unSelectSize * 1.2f;
        // 设置滚动监听
        fixedIndicatorView.setOnTransitionListener(new OnTransitionTextListener().
                setColor(Color.RED, Color.BLACK).setSize(selectSize, unSelectSize));

        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);
        // 将viewPager和indicator使用
        indicatorViewPager = new IndicatorViewPager(fixedIndicatorView, viewPager);
        // 设置indicatorViewPager的适配器
        indicatorViewPager.setAdapter(new IndicatorAdapter(mContext,getSupportFragmentManager()));
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
}

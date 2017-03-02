package com.example.sanji.bibiliproject.ui;

import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sanji.bibiliproject.R;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @InjectView(R.id.main_toolbar)
    Toolbar main_toolbar;
    @InjectView(R.id.nav_view)
    NavigationView navigationView;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;
    @InjectView(R.id.content_main)
    LinearLayout contentMain;

    FragmentTransaction transaction;
    @InjectView(R.id.layout_drawer)
    LinearLayout layoutDrawer;

    boolean flag = false;//切换夜间模式
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.coorLayout)
    CoordinatorLayout coorLayout;
    private ImageView yejian;
    private IndexFragment indexFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        main_toolbar.setTitle("");
        setSupportActionBar(main_toolbar);
        //去除滚动条
        disableNavigationViewScrollbars(navigationView);

        navigationView.setNavigationItemSelectedListener(this);//侧滑的点击事件
        layoutDrawer.setOnClickListener(this);//打开抽屉
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(coorLayout, "开始直播", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Snackbar.make(coorLayout, "OK！", Snackbar.LENGTH_LONG).show();
                            }
                        });
                snackbar.show();
            }
        });

        //默认加载首页
        initIndexFragment();

    }

    /**
     * 主界面不需要支持滑动返回，重写该方法永久禁用当前界面的滑动返回功能
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }


    private void initIndexFragment() {
        indexFragment = new IndexFragment();
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.content_main, indexFragment);
        transaction.commit();
        navigationView.setCheckedItem(R.id.nav_index);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
        navigationView.setCheckedItem(R.id.nav_index);
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //如果打开侧滑，按返回键先退出侧滑再退出项目
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //绑定Toolbar菜单
        getMenuInflater().inflate(R.menu.toolbar_menu_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ToolBar的点击事件
        int id = item.getItemId();
        if (id == R.id.action_game) {
            //游戏
            return true;
        } else if (id == R.id.action_download) {
            //下载
            return true;
        } else if (id == R.id.action_search) {
            //搜索
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //侧滑的点击事件
        int id = item.getItemId();
        if (id == R.id.nav_index) {
            //主页

            replaceFragment(indexFragment);
        } else if (id == R.id.nav_vip) {
            //大会员
        } else if (id == R.id.nav_point) {
            //会员积分
        } else if (id == R.id.nav_download) {
            //离线缓存
        } else if (id == R.id.nav_shoucang) {
            //我的收藏
        } else if (id == R.id.nav_history) {
            //历史记录
        } else if (id == R.id.nav_people) {
            //我的关注
        } else if (id == R.id.nav_qianbao) {
            //B站钱包
        } else if (id == R.id.nav_color) {
            //主题选择
        } else if (id == R.id.nav_shop) {
            //应用推荐
        } else if (id == R.id.nav_settings) {
            //设置与帮助
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 切换夜间模式
     *
     * @param view
     */
    public void qiehuanyejian(View view) {
        yejian = (ImageView) view;
        if (flag == false) {
            yejian.setImageResource(R.drawable.ic_navigation_header_daily);
            flag = true;
        } else {
            yejian.setImageResource(R.drawable.ic_navigation_header_night);
            flag = false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_drawer:
                drawer.openDrawer(GravityCompat.START);
                break;
        }
    }

}



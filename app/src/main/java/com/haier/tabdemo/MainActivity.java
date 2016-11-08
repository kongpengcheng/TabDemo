package com.haier.tabdemo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

/**
 * 订单管理的顶部标签栏
 * Created by Harry.kong on 2016/10/19.
 */
public class MainActivity extends FragmentActivity {
    OrderFragmentPagerAdapter orderFragmentPagerAdapter;
    private OrderTabLayout order_managment_tablayout;
    private ViewPager order_managment_viewpager;
    private RelativeLayout activity_main;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        order_managment_tablayout = (OrderTabLayout) findViewById(R.id.order_managment_tablayout);
        order_managment_viewpager = (ViewPager) findViewById(R.id.order_managment_viewpager);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        fragmentManager = getSupportFragmentManager();
        orderFragmentPagerAdapter = new OrderFragmentPagerAdapter(fragmentManager);
        order_managment_tablayout.addTabs("全部订单(0)", "待付款(0)", "待发货(0)", "待收货(0)", "已完成(0)");
        order_managment_tablayout.setOnTabSelectedListener(new OrderTabLayout.OnTabSelectedListener() {
            @Override
            public void onSelected(int position) {
                order_managment_viewpager.setCurrentItem(position, false);
            }
        });
        //设置适配器
        order_managment_viewpager.setAdapter(orderFragmentPagerAdapter);
        order_managment_viewpager.setOffscreenPageLimit(0);
        //标签栏和ViewPager建立联系
        order_managment_tablayout.setupWithViewPager(order_managment_viewpager);
    }
}

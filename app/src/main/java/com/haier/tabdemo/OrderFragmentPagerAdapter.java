package com.haier.tabdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Harry.Kong on 2016/11/7.
 */

public class OrderFragmentPagerAdapter extends FragmentPagerAdapter {

    private final static int PAGER_SIZE = 5;  //pager 的页数

    private int orderType = 0;  //订单种类

    public OrderFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 设置订单种类
     *
     * @param orderType
     */
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Override
    public Fragment getItem(int position) {
        return OrderFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGER_SIZE;
    }
}

package com.haier.tabdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harry.kong  on 2016/11/7.
 */

public class OrderTabLayout extends LinearLayout {
    private LinearLayout tabsLayout;  //标签布局
    private View tabIndicator;  //标签指示器
    private ViewPager viewPager;  //建立连接的ViewPager
    private int curPosition = 0;  //当前的位置
    private final ValueAnimator mAnimator = new ValueAnimator();
    private Context context;
    private List<TextView> titleList = new ArrayList<>();
    private OnTabSelectedListener onTabSelectedListener;

    public OrderTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    /**
     * 初始化Views
     */
    private void initViews() {
        //自定义其实和xml写法同样的道理，先建立布局，此处的整体布局是linearLayout，垂直的。
        this.setOrientation(VERTICAL);

        //上层LinearLayout，包容全部的标签和分割符
        tabsLayout = new LinearLayout(context);
        LayoutParams tabsLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tabsLayout.setLayoutParams(tabsLp);
        tabsLayout.setOrientation(HORIZONTAL);
        tabsLayout.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(tabsLayout);

        //下层指示器
        tabIndicator = new View(context);
        tabIndicator.setBackgroundColor(getResources().getColor(android.R.color.background_dark));
        this.addView(tabIndicator);

        //初始化时选中第0位置个标签(此处涉及到绘制时间的问题)
        this.post(new Runnable() {
            @Override
            public void run() {
                setSelectedTab(0);
                TextView textView = titleList.get(0);
                textView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

            }
        });
    }

    /**
     * 设置选中Tab标签
     *
     * @param position
     */
    public void setSelectedTab(int position) {
        Log.d("size-->", "进去了");
        //当前的位置
        curPosition = position;
        //设置监听回调
        if (onTabSelectedListener != null) {
            onTabSelectedListener.onSelected(position);
        }
        Log.d("size-->", "" + titleList.size());
        if (titleList.size() != 0) {

            for (int i = 0; i < titleList.size(); i++) {
                TextView textView = titleList.get(i);
                if (i == position) {
                    textView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    Log.d("size-->", "相等" + position);
                } else {
                    textView.setTextColor(getResources().getColor(android.R.color.background_dark));
                    Log.d("size-->", "不相等" + position);
                }
            }
            TextView textView = titleList.get(position);
            LayoutParams lp = new LayoutParams(textView.getWidth(), 2);
            lp.setMargins(textView.getLeft(), 8, 0, 0);
            tabIndicator.setLayoutParams(lp);
        }


    }

    /**
     * 和ViewPager建立联系
     *
     * @param viewPager
     */
    public void setupWithViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //选中当前标签页
                Log.d("size-->", "走到滑动");
                setSelectedTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 添加Tabs
     *
     * @param tabText
     */
    public void addTabs(String... tabText) {
        for (String str : tabText) {
            addTab(str);
        }
    }

    /**
     * 添加一个标签
     *
     * @param tabTitle
     */
    private void addTab(String tabTitle) {
        TextView textView = new TextView(context);
        if (tabsLayout.getChildCount() == 0) {  //第一个 标签
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 32, 0);
            textView.setLayoutParams(lp);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
            textView.setTextColor(getResources().getColor(android.R.color.background_dark));
            textView.setText(tabTitle);

            tabsLayout.addView(textView);

            titleList.add(textView);
        } else {  //第一个后边的标签
            //分隔符
            View view = new View(context);
            LayoutParams lp = new LayoutParams(2, 60);
            view.setLayoutParams(lp);
            view.setBackgroundColor(Color.parseColor("#ffb847"));
            tabsLayout.addView(view);

            LayoutParams tvlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvlp.setMargins(32, 0, 32, 0);
            textView.setLayoutParams(tvlp);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
            textView.setTextColor(getResources().getColor(android.R.color.background_dark));
            textView.setText(tabTitle);
            tabsLayout.addView(textView);

            titleList.add(textView);
        }
        //此处用Tag 存储相对应的位置
        textView.setTag(titleList.size() - 1);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                setSelectedTab(position);
                if (viewPager != null) {
                    viewPager.setCurrentItem(position);
                }
            }
        });
    }

    /**
     * 更新Tabs显示内容
     *
     * @param tabText
     */
    public void updateTabs(String... tabText) {
        for (int i = 0; i < tabText.length; i++) {
            TextView textView = titleList.get(i);
            textView.setText(tabText[i]);
        }

        //更新当前的指示器位置
        if (titleList.size() != 0) {
            TextView textView = titleList.get(curPosition);
            LayoutParams lp = new LayoutParams(textView.getWidth(), 2);
            lp.setMargins(textView.getLeft(), 8, 0, 0);
            tabIndicator.setLayoutParams(lp);
        }
    }

    /**
     * 设置Tab选中监听事件
     *
     * @param onTabSelectedListener
     */
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    /**
     * Tab选中的监听事件
     */
    public interface OnTabSelectedListener {
        public void onSelected(int position);
    }

}

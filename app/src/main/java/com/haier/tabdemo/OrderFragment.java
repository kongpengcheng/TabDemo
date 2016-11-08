package com.haier.tabdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Harry.Kong on 2016/11/7.
 */

public class OrderFragment extends Fragment {

    private TextView textView;
    private int curNum;

    public static OrderFragment newInstance(int num) {
        OrderFragment fragment = new OrderFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curNum = getArguments() != null ? getArguments().getInt("num") : 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_xml, container, false);
        textView = (TextView) view.findViewById(R.id.tv_text);
        textView.setText(curNum+"个数");
        return view;
    }


}

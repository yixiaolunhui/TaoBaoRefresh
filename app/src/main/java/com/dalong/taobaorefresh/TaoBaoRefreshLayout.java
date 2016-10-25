package com.dalong.taobaorefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * 淘宝下拉刷新
 * Created by zhouweilong on 2016/10/25.
 */

public class TaoBaoRefreshLayout  extends RefreshLayout{
    public TaoBaoRefreshLayout(Context context) {
        super(context);
    }

    public TaoBaoRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        TaoBaoHeader header=new TaoBaoHeader(getContext());
        TaoBaoFooter footer=new TaoBaoFooter(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }
}

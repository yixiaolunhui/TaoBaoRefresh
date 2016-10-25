package com.dalong.taobaorefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalong.refreshlayout.OnHeaderListener;
import com.dalong.refreshlayout.RefreshStatus;

/**
 * 淘宝下拉刷新自定义头部
 * Created by zhouweilong on 2016/10/25.
 */

public class TaoBaoHeader extends LinearLayout implements OnHeaderListener {

    private RotateAnimation refreshingAnimation;
    private TaoBaoView taobaoView;
    private TextView taobaoTv;
    private View taobaoHeader;

    public TaoBaoHeader(Context context) {
        super(context);
        taobaoHeader=LayoutInflater.from(context).inflate(R.layout.taobao_view,this,true);
        taobaoView=(TaoBaoView)findViewById(R.id.taobao_view);
        taobaoTv=(TextView)taobaoHeader.findViewById(R.id.taobao_tv);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
        measureView(taobaoHeader);
        taobaoView.setProgress(90);
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }
    @Override
    public void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight) {
        refreshStatus(RefreshStatus.REFRESH_BEFORE);
        int progress=(int) ((Math.abs(scrollY)/(1.0f*refreshHeight))*100);
        taobaoView.setProgress(progress>90?90:progress);
        taobaoView.setIsShowIcon(true);
    }

    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {
        refreshStatus(RefreshStatus.REFRESH_AFTER);
        taobaoView.setIsShowIcon(false);
    }

    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {
        refreshStatus(RefreshStatus.REFRESH_READY);
    }

    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {
        refreshStatus(RefreshStatus.REFRESH_DOING);
    }

    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isRefreshSuccess) {
        refreshStatus(RefreshStatus.REFRESH_COMPLETE);
    }

    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {
        refreshStatus(RefreshStatus.REFRESH_CANCEL);
    }

    public void refreshStatus(RefreshStatus status){
        switch (status){
            case DEFAULT:
                break;
            case REFRESH_BEFORE:
                taobaoTv.setText(getResources().getString(R.string.pull_to_refresh));
                break;
            case REFRESH_AFTER:
                taobaoTv.setText(getResources().getString(R.string.release_to_refresh));
                break;
            case REFRESH_READY:
                break;
            case REFRESH_DOING:
                taobaoTv.setText(getResources().getString(R.string.refreshing));
                taobaoView.startAnimation(refreshingAnimation);
                break;
            case REFRESH_CANCEL:
                taobaoTv.setText(getResources().getString(R.string.refresh_cancel));
                taobaoView.clearAnimation();
                break;
            case REFRESH_COMPLETE:
                taobaoTv.setText(getResources().getString(R.string.refresh_succeed));
                taobaoView.clearAnimation();
                break;
        }
    }
}

package com.dalong.taobaorefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalong.refreshlayout.OnFooterListener;
import com.dalong.refreshlayout.RefreshStatus;

/**
 * 淘宝下拉刷新自定义底部
 * Created by zhouweilong on 2016/10/25.
 */

public class TaoBaoFooter extends LinearLayout implements OnFooterListener {

    private RotateAnimation refreshingAnimation;
    private TaoBaoView taobaoView;
    private TextView taobaoTv;
    private View taobaoHeader;

    public TaoBaoFooter(Context context) {
        super(context);
        taobaoHeader=LayoutInflater.from(context).inflate(R.layout.taobao_view,this,true);
        taobaoView=(TaoBaoView)findViewById(R.id.taobao_view);
        taobaoTv=(TextView)taobaoHeader.findViewById(R.id.taobao_tv);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
        taobaoView.setProgress(90);
    }


    public void refreshStatus(RefreshStatus status){
        switch (status){
            case DEFAULT:
                break;
            case LOAD_BEFORE:
                taobaoTv.setText(getResources().getString(R.string.pullup_to_load));
                break;
            case LOAD_AFTER:
                taobaoTv.setText(getResources().getString(R.string.release_to_load));
                break;
            case LOAD_READY:
                break;
            case LOAD_DOING:
                taobaoTv.setText(getResources().getString(R.string.loading));
                taobaoView.startAnimation(refreshingAnimation);
                break;
            case LOAD_CANCEL:
                taobaoTv.setText(getResources().getString(R.string.load_cacel));
                taobaoView.clearAnimation();
                break;
            case LOAD_COMPLETE:
                taobaoTv.setText(getResources().getString(R.string.load_succeed));
                taobaoView.clearAnimation();
                break;
        }
    }

    @Override
    public void onLoadBefore(int scrollY) {
        refreshStatus(RefreshStatus.LOAD_BEFORE);
        int progress=(int) ((Math.abs(scrollY)/(1.0f*getHeight()))*100);
        taobaoView.setProgress(progress>90?90:progress);
        taobaoView.setIsShowIcon(true);
    }

    @Override
    public void onLoadAfter(int scrollY) {
        refreshStatus(RefreshStatus.LOAD_AFTER);
        taobaoView.setIsShowIcon(false);
    }

    @Override
    public void onLoadReady(int scrollY) {
        refreshStatus(RefreshStatus.LOAD_READY);
    }

    @Override
    public void onLoading(int scrollY) {
        refreshStatus(RefreshStatus.LOAD_DOING);
    }

    @Override
    public void onLoadComplete(int scrollY, boolean isLoadSuccess) {
        refreshStatus(RefreshStatus.LOAD_COMPLETE);
    }

    @Override
    public void onLoadCancel(int scrollY) {
        refreshStatus(RefreshStatus.LOAD_CANCEL);
    }
}

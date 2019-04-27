package com.rocky.indexbar.rv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rocky.indexbar.R;

/**
 * @author rocky
 * @date 2019/4/26.
 * description：自定义recyclerview 配和indexbar 实现索引功能
 * <p>
 * 要实现的功能
 * 1.封装滑动监听 实现头布局的吸附
 * 2.
 */
public class IndexRecyclerView extends RecyclerView {

    private int flay_head_layout_id;//吸附的布局id
    private int flayTextSize;
    private int flayTextColor;
    private TextView tv_flay;
    private FlayAdapter flayAdapter;

    public IndexRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public IndexRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private int sp2px(int sp) {
        float density = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5f);
    }


    private void init(Context context) {
        linearLayoutManager = new LinearLayoutManager(context);
        setLayoutManager(linearLayoutManager);
        flayAdapter = new FlayAdapter(context);
        setAdapter(flayAdapter);

    }

    public void setTv_flay(final TextView tv_flay) {
        this.tv_flay = tv_flay;
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (tv_flay != null) {
                    View tv_head_view = IndexRecyclerView.this.findChildViewUnder(tv_flay.getMeasuredWidth() / 2, 5);
                    if (tv_head_view != null && tv_head_view.getContentDescription() != null) {
                        tv_flay.setText(String.valueOf(tv_head_view.getContentDescription()));
                    }
                    View transInfoVIew = IndexRecyclerView.this.findChildViewUnder(tv_flay.getMeasuredWidth() / 2, tv_flay.getMeasuredHeight() + 1);

                    if (transInfoVIew != null && transInfoVIew.getTag() != null) {
                        int transViewStatus = (int) transInfoVIew.getTag();
                        int dY = transInfoVIew.getTop() - tv_flay.getMeasuredHeight();
                        if (transViewStatus == FlayStatus.HAS_FLAY_VIEW) {
                            if (transInfoVIew.getTop() > 0) {
                                tv_flay.setTranslationY(dY);
                            } else {
                                tv_flay.setTranslationY(0);
                            }
                        } else if (transViewStatus == FlayStatus.NONE_FLAY_VIEW) {
                            tv_flay.setTranslationY(0);
                        }
                    }
                }
            }
        });
    }

    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public LinearLayoutManager getLayoutManager() {
        return linearLayoutManager;
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(linearLayoutManager);
    }

    @Nullable
    @Override
    public FlayAdapter getAdapter() {
        return flayAdapter;
    }
}

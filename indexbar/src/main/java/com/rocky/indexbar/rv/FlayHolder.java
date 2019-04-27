package com.rocky.indexbar.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rocky.indexbar.R;
import com.rocky.indexbar.indexbar.ItemBean;

import java.util.List;

/**
 * @author rocky
 * @date 2019/4/27.
 * description： 吸附 holder
 */
public class FlayHolder<T> extends RecyclerView.ViewHolder {
    private TextView tv_flay_head, tv_flay_content;

    public FlayHolder(@NonNull View itemView) {
        super(itemView);
        tv_flay_head = itemView.findViewById(R.id.tv_sticky_header_view);
        tv_flay_content = itemView.findViewById(R.id.tv_flay_content);
    }

    public void bindData(List<T> d, int pos) {
        T t = d.get(pos);
        if (t instanceof ItemBean) {
            ItemBean t1 = (ItemBean) t;
            tv_flay_content.setText(t1.getContent());
            tv_flay_head.setText(t1.getName());


            if (pos == 0) {
                tv_flay_head.setVisibility(View.VISIBLE);
                itemView.setTag(FlayStatus.FIRST_FLAY_VIEW);
            } else {
                if (!TextUtils.equals(t1.getName(), ((ItemBean)d.get(pos-1)).getName())) {
                    tv_flay_head.setVisibility(View.VISIBLE);
                    itemView.setTag(FlayStatus.HAS_FLAY_VIEW);
                } else {
                    tv_flay_head.setVisibility(View.GONE);
                    itemView.setTag(FlayStatus.NONE_FLAY_VIEW);
                }
            }
            itemView.setContentDescription(t1.getName());
        }
    }
}

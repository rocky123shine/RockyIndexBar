package com.rocky.rockyindexbar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocky.indexbar.indexbar.DigitalUtil;
import com.rocky.indexbar.indexbar.ItemBean;

import java.util.List;

/**
 * @author rocky
 * @date 2019/4/26.
 * description： c测试indexbar
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvHolder> {
    private List<ItemBean> list;

    private final Context context;

    public RvAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ItemBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RvHolder(LayoutInflater.from(context).inflate(R.layout.item_rv, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RvHolder rvHolder, int pos) {
        ItemBean itemBean = list.get(pos);

        String content = itemBean.getContent();
        String name = itemBean.getName();

        String mFirstName = String.valueOf(TextUtils.isEmpty(name) ? content.charAt(0) : name.charAt(0));
        String mPreFirstName;
        if (pos == 0) {
            mPreFirstName = "-";
        } else {
            ItemBean itemBean1 = (ItemBean) list.get(pos - 1);
            String content1 = itemBean1.getContent();
            String name1 = itemBean1.getName();
            mPreFirstName = String.valueOf(TextUtils.isEmpty(name1) ? content1.charAt(0) : name1.charAt(0));
        }

        if (DigitalUtil.isDigital(mFirstName)) {
            mFirstName = "#";
        }
        if (DigitalUtil.isDigital(mPreFirstName)) {
            mPreFirstName = "#";
        }
        boolean ishow = mFirstName.compareToIgnoreCase(mPreFirstName) != 0;
        rvHolder.tv_name.setVisibility(ishow ? View.VISIBLE : View.GONE);
        itemBean.setShowHead(ishow);
        rvHolder.tv_content.setText(content);
        rvHolder.tv_name.setText(mFirstName.toUpperCase());


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class RvHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_content;

        public RvHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    public ItemBean getItemData(int pos){
        return list.get(pos);
    }
}

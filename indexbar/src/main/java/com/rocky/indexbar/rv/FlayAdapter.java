package com.rocky.indexbar.rv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rocky.indexbar.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rocky
 * @date 2019/4/27.
 * description： 吸附 adapter
 */
public class FlayAdapter<T> extends RecyclerView.Adapter<FlayHolder<T>> {

    private final Context context;
    private List<T> datas = new ArrayList<>();

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public FlayAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FlayHolder<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        return new FlayHolder<T>(LayoutInflater.from(context).inflate(R.layout.item_flay, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FlayHolder<T> flayHolder, int pos) {
        flayHolder.bindData(datas, pos);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

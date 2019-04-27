package com.rocky.rockyindexbar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rocky.indexbar.indexbar.FillNameAndSortUtil;
import com.rocky.indexbar.indexbar.IndexBar;
import com.rocky.indexbar.indexbar.ItemBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView rv = findViewById(R.id.rv);
        IndexBar index_bar = findViewById(R.id.index_bar);
        final RvAdapter adapter = new RvAdapter(this);
        final TextView tv_head = findViewById(R.id.tv_head);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
        List<String> data = Arrays.asList(Girls.NAMES);
        final List<ItemBean> list = new ArrayList<>();
        final List<String> letters = new ArrayList<>();
        FillNameAndSortUtil.getInstance().fillNameAndSort(list, letters, data);
        index_bar.setLetters(letters);
        adapter.setList(list);

        index_bar.setIndexChangedListener(new IndexBar.IOnIndexChangedListener() {
            @Override
            public void onIndexChanged(int pos, String text) {
                if ("#".equals(text)) {
                    rv.scrollToPosition(0);
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    ItemBean itemBean = list.get(i);

                    String name = itemBean.getName();

                    String firstName = String.valueOf(TextUtils.isEmpty(name) ? itemBean.getContent().charAt(0) : name.charAt(0));
                    if (text.compareToIgnoreCase(firstName) == 0) {
                        linearLayoutManager.scrollToPositionWithOffset(i, 0);
                        return;

                    }
                }
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                ItemBean itemBean = adapter.getItemData(firstVisibleItemPosition);

                if (itemBean.isShowHead()) {
                    tv_head.setVisibility(View.GONE);
                } else {
                    if (!adapter.getItemData(firstVisibleItemPosition + 1).isShowHead()) {
                        tv_head.setVisibility(View.VISIBLE);
                        tv_head.setText(itemBean.getName());
                    }
                }
            }
        });
    }
}

package com.rocky.rockyindexbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.widget.TextView;

import com.rocky.indexbar.indexbar.FillNameAndSortUtil;
import com.rocky.indexbar.indexbar.IndexBar;
import com.rocky.indexbar.indexbar.ItemBean;
import com.rocky.indexbar.rv.FlayAdapter;
import com.rocky.indexbar.rv.IndexRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author rocky
 * @date 2019/4/27.
 * description：测试 自定义rv
 */
public class TestFlayRvAvtivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flay_rv);
        final IndexRecyclerView irv = findViewById(R.id.irv);
        IndexBar indexBar = findViewById(R.id.index_bar);

        List<String> data = Arrays.asList(Girls.NAMES);
        final List<ItemBean> list = new ArrayList<>();
        final List<String> letters = new ArrayList<>();
        FillNameAndSortUtil.getInstance().fillNameAndSort(list, letters, data);
        indexBar.setLetters(letters);

        final LinearLayoutManager linearLayoutManager = irv.getLayoutManager();
        FlayAdapter adapter = irv.getAdapter();
        adapter.setDatas(list);
        irv.setTv_flay((TextView)findViewById(R.id.tv_sticky_header_view));

        indexBar.setIndexChangedListener(new IndexBar.IOnIndexChangedListener() {
            @Override
            public void onIndexChanged(int pos, String text) {
                if ("#".equals(text)) {
                    irv.scrollToPosition(0);
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
    }
}

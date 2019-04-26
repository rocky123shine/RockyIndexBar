package com.rocky.indexbar;

import android.text.TextUtils;

/**
 * @author rocky
 * @date 2019/4/26.
 * description：rv item 数据
 */
public class ItemBean implements Comparable<ItemBean> {
    private String name;//u一组内容的 组名
    private String content;
    private boolean showHead;

    public void setShowHead(boolean showHead) {
        this.showHead = showHead;
    }

    public boolean isShowHead() {
        return showHead;
    }

    public ItemBean() {
    }

    public ItemBean(String content) {
        this.content = content;
        this.name = PinYinUtil.getInstance().toPinYin(content);
        setShowHead(false);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(ItemBean o) {

        String content1 = o.getContent();
        String name1 = o.getName();

        if (TextUtils.isEmpty(name)) {
            return content.compareToIgnoreCase(TextUtils.isEmpty(name1) ? content1 : name1);

        } else {
            return name.compareToIgnoreCase(TextUtils.isEmpty(name1) ? content1 : name1);
        }

    }
}

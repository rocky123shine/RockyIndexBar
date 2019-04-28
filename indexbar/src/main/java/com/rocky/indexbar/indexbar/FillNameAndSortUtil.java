package com.rocky.indexbar.indexbar;

import android.text.TextUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author rocky
 * @date 2019/4/26.
 * description： 把数据格式化成需要的
 */
public class FillNameAndSortUtil {
    private static final FillNameAndSortUtil ourInstance = new FillNameAndSortUtil();

    public static FillNameAndSortUtil getInstance() {
        return ourInstance;
    }

    private FillNameAndSortUtil() {
    }

    public void fillNameAndSort(List<ItemBean> itemBeans, List<String> letters, List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            ItemBean itemBean = new ItemBean(data.get(i));

            if (DigitalUtil.isDigital(itemBean.getContent())) {
                if (!letters.contains("#")) {
                    letters.add("#");
                }
                itemBean.setName("#");
                itemBeans.add(itemBean);
                continue;
            }
            String name = itemBean.getName();
            String letter;
            if (!TextUtils.isEmpty(name)) {
                letter = name.substring(0, 1).toUpperCase();

            } else {
                letter = itemBean.getContent().substring(0, 1).toUpperCase();
            }
            System.out.println(letter);
            letter = CharacterUtitl.isChinese(letter) ? PinYinUtil.getInstance().toPinYin(letter).substring(0, 1).toUpperCase() : letter;
            itemBean.setName(letter);
            if (!letters.contains(letter)) {
                letters.add(letter);
            }
            itemBeans.add(itemBean);

        }
        Collections.sort(itemBeans);
        Collections.sort(letters);

    }
}

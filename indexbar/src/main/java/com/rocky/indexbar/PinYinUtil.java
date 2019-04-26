package com.rocky.indexbar;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author rocky
 * @date 2019/4/26.
 * description： 汉语转化成拼音工具类
 */
public class PinYinUtil {
    private static PinYinUtil util;

    private PinYinUtil() {
    }

    public static PinYinUtil getInstance() {
        return InnerClass.util;
    }

    private static class InnerClass {
        private static PinYinUtil util = new PinYinUtil();
    }
    private static HanyuPinyinOutputFormat format;

    public String toPinYin(String s) {
        if (format == null) {
            format = new HanyuPinyinOutputFormat();
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }

        StringBuilder sb = new StringBuilder();
        char c;
        char[] charArray = s.toCharArray();

        for (char aCharArray : charArray) {
            c = aCharArray;
            try {
                String[] p = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (p != null&& p.length>0) {
                    sb.append(p[0]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }

        }
        return sb.toString();
    }


}

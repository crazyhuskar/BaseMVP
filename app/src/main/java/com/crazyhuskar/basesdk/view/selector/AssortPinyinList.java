package com.crazyhuskar.basesdk.view.selector;


import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 获取字符串首字母并转化成列表
 */
public class AssortPinyinList {

    private HashList<String, String> hashList = new HashList<String, String>(
            new KeySort<String, String>() {
                public String getKey(String value) {
                    return getFirstChar(value);
                }
            });

    // 获得字符串的首字母 首字符 转汉语拼音
    public String getFirstChar(String value) {
        // 首字符
        char firstChar = value.charAt(0);
        // 首字母分类
        String first = null;
        // 是否是非汉字
        String[] print = PinyinHelper.toHanyuPinyinStringArray(firstChar);

        if (print == null) {
            // 将小写字母改成大写
            if ((firstChar >= 97 && firstChar <= 122)) {
                firstChar -= 32;
            }
            if (firstChar >= 65 && firstChar <= 90) {
                first = String.valueOf((char) firstChar);
            } else {
                // 认为首字符为数字或者特殊字符
                //意思是names是以其他数字/字符开头的默认为#号分组下的列表显示在最上面
                first = "#";
            }
        } else {
            // 如果是中文 分类大写字母
            first = String.valueOf((char) (print[0].charAt(0) - 32));
        }
        if (first == null) {
            //如果字符是空的默认是问号列表，显示在？分组中
            first = "?";
        }
        return first;
    }

    public HashList<String, String> getHashList() {
        return hashList;
    }

}

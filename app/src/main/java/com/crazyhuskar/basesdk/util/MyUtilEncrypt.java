package com.crazyhuskar.basesdk.util;

import java.security.MessageDigest;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilEncrypt {
    /**
     * md5加密方法
     *
     * @param plainText 加密字符串
     * @param type      ture返回32位md5加密字符串，false返回16位md5加密字符串
     * @return String
     */
    public final static String md5Str(String plainText, boolean type) {

        // 返回字符串
        String md5Str = null;
        try {
            // 操作字符串
            StringBuffer buf = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
            md.update(plainText.getBytes());
            // 计算出摘要,完成哈希计算。
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                // 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
                buf.append(Integer.toHexString(i));
            }

            if (type) {
                md5Str = buf.toString();
            } else {
                md5Str = buf.toString().substring(8, 24);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }
}

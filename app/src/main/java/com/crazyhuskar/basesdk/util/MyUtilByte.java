package com.crazyhuskar.basesdk.util;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilByte {
    /**
     * byte数组大小数据格式互转
     *
     * @param src
     * @return
     */
    public static byte[] byteToByte(byte[] src) {
        byte[] data = new byte[src.length];
        for (int i = 0; i < src.length; i++) {
            data[i] = src[src.length - 1 - i];
        }
        return data;
    }

    /**
     * byte数组转hexString
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv.toUpperCase());
        }
        return stringBuilder.toString();
    }

    /**
     * 单个byte转hexString
     *
     * @param src
     * @return
     */
    public static String byteToHexString(byte src) {
        StringBuilder stringBuilder = new StringBuilder("");
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv.toUpperCase());
        return stringBuilder.toString();
    }

    /**
     * hexString转byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        char[] hexChars = transformArrayToEven(hexString.toCharArray());
        byte[] d = new byte[hexChars.length / 2];
        for (int i = 0; i < hexChars.length; i++) {
            byte b;
            if (i % 2 == 0) {
                b = (byte) (charToByte(hexChars[i]) << 4);
            } else {
                b = charToByte(hexChars[i]);
            }
            d[i / 2] += b;
        }
        return d;
    }

    private static char[] transformArrayToEven(char[] c) {
        if (c.length % 2 == 0) {
            return c;
        } else {
            char[] result = new char[c.length + 1];
            result[0] = '0';
            for (int i = 0; i < c.length; i++) {
                result[i + 1] = c[i];
            }
            return result;
        }
    }

    /**
     * char转byte
     *
     * @param c
     * @return
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * byte转char
     *
     * @param b
     * @return
     */
    public static char byteToChar(byte b) {
        return "0123456789ABCDEF".toCharArray()[b];
    }

    /**
     * byte数组转hexString（不足两位不补0）
     *
     * @param bt
     * @return
     */
    public static String getNumFromBytes(byte[] bt) {
        String str = "";
        for (int i = 0; i < bt.length; i++) {
            str += String.valueOf(bt[i]);
        }
        return str;
    }

    /**
     * int转byte数组
     *
     * @param value  数据源
     * @param length byte长度(1~4)
     * @return
     */
    public static byte[] intToBytes(int value, int length) {
        byte[] src = new byte[length];
        switch (length) {
            case 1:
                src[0] = (byte) (value & 0xFF);
                return src;
            case 2:
                src[0] = (byte) ((value >> 8) & 0xFF);
                src[1] = (byte) (value & 0xFF);
                return src;
            case 3:
                src[0] = (byte) ((value >> 16) & 0xFF);
                src[1] = (byte) ((value >> 8) & 0xFF);
                src[2] = (byte) (value & 0xFF);
                return src;
            case 4:
                src[0] = (byte) ((value >> 24) & 0xFF);
                src[1] = (byte) ((value >> 16) & 0xFF);
                src[2] = (byte) ((value >> 8) & 0xFF);
                src[3] = (byte) (value & 0xFF);
                return src;
            default:
                return src;
        }
    }

    /**
     * byte转int数组
     *
     * @param src    数据源
     * @param offset 起始位
     * @param length byte长度(1~4)
     * @return
     */
    public static int bytesToInt(byte[] src, int offset, int length) {
        int value = 0;
        switch (length) {
            case 1:
                value = (int) ((src[offset] & 0xFF));
                return value;
            case 2:
                value = (int) (((src[offset] & 0xFF) << 8) | (src[offset + 1] & 0xFF));
                return value;
            case 3:
                value = (int) (((src[offset] & 0xFF) << 16) | ((src[offset + 1] & 0xFF) << 8) | (src[offset + 2] & 0xFF));
                return value;
            case 4:
                value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16)
                        | ((src[offset + 2] & 0xFF) << 8) | (src[offset + 3] & 0xFF));
                return value;
            default:
                return value;
        }
    }

    /**
     * byte转int数组
     *
     * @param src 数据源
     * @return
     */
    public static int byteToInt(byte src) {
        int value = 0;
        value = (int) ((src & 0xFF));
        return value;
    }

    /**
     * 带符号位
     *
     * @param src
     * @param offset
     * @param length
     * @return
     */
    public static int bytesToInt_f(byte[] src, int offset, int length) {
        int j = MyUtilByte.bytesToInt(src, offset, length);
        int value = 0;
        switch (length) {
            case 1:
                value = j < 128 ? j : j - 128 * 2;
                return value;
            case 2:
                value = j < 32768 ? j : j - 32768 * 2;
                return value;
            case 3:
                value = j < 8388608 ? j : j - 8388608 * 2;
                return value;
            case 4:
                value = j <= 2147483647 ? j : j - (2147483647 + 1) * 2;
                return value;
            default:
                return value;
        }
    }

    /**
     * 带符号位
     *
     * @param src
     * @return
     */
    public static int byteToInt_f(byte src) {
        int j = MyUtilByte.byteToInt(src);
        int value = 0;
        value = j < 128 ? j : j - 128 * 2;
        return value;
    }

    /**
     * 异或计算
     *
     * @param bytes
     * @param begin (byte[begin])从0开始
     * @param end   (byte[end])从0开始
     * @return
     */
    public static byte getXor(byte[] bytes, int begin, int end) {
        byte xor = bytes[begin];
        for (int i = begin + 1; i <= end; i++) {
            xor ^= bytes[i];
        }
        return xor;

    }

    /**
     * 异或验证
     *
     * @param bytes 必填
     * @param begin 异或开始位
     * @param end   异或结束位
     * @param xor   异或验证位
     * @return
     */
    public static boolean isXor(byte[] bytes, int begin, int end, int xor) {
        if (bytes.length > 0) {
            byte t = getXor(bytes, begin, end);
            byte i = bytes[xor];
            return t == i;
        } else {
            return false;
        }
    }

    /**
     * 计算给定长度数据的16位CRC。
     * CRC-16/MODBUS
     *
     * @param bytes
     * @param begin
     * @param end
     * @return
     */
    public static byte[] getCRC16_MODBUS(byte[] bytes, int begin, int end) {
        int crc = 0xffff;
        int polynomial = 0xa001;

        int i, j;
        for (i = begin; i < end; i++) {
            crc ^= ((int) bytes[i] & 0x00ff);
            for (j = 0; j < 8; j++) {
                if ((crc & 0x0001) != 0) {
                    crc >>= 1;
                    crc ^= polynomial;
                } else {
                    crc >>= 1;
                }
            }
        }
        return MyUtilByte.intToBytes(crc, 2);
    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] getCRC16_XMODEM(byte[] bytes, int begin, int end) {
        int crc = 0x0000;
        int polynomial = 0x1021;
        for (int index = begin; index < end; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit)
                    crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return MyUtilByte.intToBytes(crc, 2);
    }

    /**
     * @param bytes
     * @param begin
     * @param length
     * @return
     */
    public static byte[] copyByte(byte[] bytes, int begin, int length) {
        byte[] bytes_t = new byte[length];
        System.arraycopy(bytes, begin, bytes_t, 0, length);
        return bytes_t;
    }
}

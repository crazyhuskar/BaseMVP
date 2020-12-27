package com.crazyhuskar.basesdk.net;

/**
 * 请求接听类
 *
 * @author CrazyHuskar
 * @date 2018/6/12
 */

public interface MyCallback<T> {
    /**
     * 成功
     *
     * @param data
     */
    void onSuccess(T data);

    /**
     * 失败
     *
     * @param msg
     */
    void onFailure(String msg);

    /**
     * 错误
     */
    void onError();

    /**
     * 结束
     */
    void onComplete();
}

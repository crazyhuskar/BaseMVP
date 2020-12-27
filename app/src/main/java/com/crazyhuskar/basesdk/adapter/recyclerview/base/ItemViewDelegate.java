package com.crazyhuskar.basesdk.adapter.recyclerview.base;

/**
 * @param <T>
 * @author HG
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T item, int position);

}

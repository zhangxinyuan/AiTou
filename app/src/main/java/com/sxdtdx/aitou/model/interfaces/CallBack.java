package com.sxdtdx.aitou.model.interfaces;

/**
 * Created by lenovo on 2017/4/26.
 */

public interface CallBack<T> {
    void onSuccess(T result);
    void onFailed(String error);
}

package com.sxdtdx.aitou.view.interfaces;

import android.content.Intent;

/**
 * Created by xinyuan on 17-5-7.
 */

public interface IAddOption {
    void initView();
    void toFinish(int code, Intent intent);
    void doAdd();
}

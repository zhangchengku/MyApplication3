package com.zggk.bridge.task;

import android.content.Context;

import com.zggk.bridge.mvp.BasePresenter;
import com.zggk.bridge.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class TaskContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}

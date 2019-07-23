package com.zggk.bridge.codewebview;

import android.content.Context;

import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.mvp.BasePresenter;
import com.zggk.bridge.mvp.BaseRequestView;
import com.zggk.bridge.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CodeWebViewContract {
    interface View extends BaseRequestView {
        void getData(AddINTbean videoVos2);
        void getData3(AddINTbean videoVos2);
    }

    interface  Presenter extends BasePresenter<View> {//网络
        void testinfo(String json);
        void addPL(String json);
    }
}

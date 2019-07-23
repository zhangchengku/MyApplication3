package com.zggk.bridge.webview;

import android.content.Context;

import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.mvp.BasePresenter;
import com.zggk.bridge.mvp.BaseRequestView;
import com.zggk.bridge.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WebViewContract {
    interface View extends BaseRequestView {
        void getData(AddINTbean videoVos2);
        void getData2(AddINTbean videoVos2);
        void getData3(AddINTbean videoVos2);
    }

    interface  Presenter extends BasePresenter<View> {//网络
        void testinfo(String json);
        void addPhoto(String userId,String bytes);
        void addPL(String json);

    }
}

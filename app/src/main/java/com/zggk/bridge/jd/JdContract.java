package com.zggk.bridge.jd;

import android.content.Context;

import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.Bean.BridgeIntbean;
import com.zggk.bridge.Bean.ISBridgeIntbean;
import com.zggk.bridge.mvp.BasePresenter;
import com.zggk.bridge.mvp.BaseRequestView;
import com.zggk.bridge.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class JdContract {
    interface View extends BaseRequestView {
        void getData(BridgeIntbean videoVos2);
        void getData2(ISBridgeIntbean videoVos2);
        void getData3(AddINTbean videoVos2);
    }

    interface  Presenter extends BasePresenter<View> {//网络
        void testinfo(String bridgCode);
        void isBridge(String lng1,String lat1,String lng2,String lat2);
        void add(String json);

    }
}

package com.zggk.bridge.Bean;

/**
 * Created by 张成昆 on 2019-4-12.
 */

import java.util.List;
public class WebJson {
    private String BridgeGuid;
    private List<String> PicList;

    public String getBridgeGuid() {
        return BridgeGuid;
    }

    public void setBridgeGuid(String bridgeGuid) {
        BridgeGuid = bridgeGuid;
    }

    public List<String> getPicList() {
        return PicList;
    }

    public void setPicList(List<String> picList) {
        PicList = picList;
    }
}

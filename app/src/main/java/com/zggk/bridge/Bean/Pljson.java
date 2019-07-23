package com.zggk.bridge.Bean;

/**
 * Created by 张成昆 on 2019-4-26.
 */

public class Pljson {
    private String UserId;
    private String Content;
    private String ToFrom;
    private String CJID;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getToFrom() {
        return ToFrom;
    }

    public void setToFrom(String toFrom) {
        ToFrom = toFrom;
    }

    public String getCJID() {
        return CJID;
    }

    public void setCJID(String CJID) {
        this.CJID = CJID;
    }
}

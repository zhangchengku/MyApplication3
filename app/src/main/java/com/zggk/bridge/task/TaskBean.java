package com.zggk.bridge.task;

/**
 * Created by 张成昆 on 2019-4-9.
 */

public class TaskBean {
    private String pic;
    private String video;
    private String guid;
    private String reportTime;
    private String bridgeCode;
    private String CJID;

    public String getCJID() {
        return CJID;
    }

    public void setCJID(String CJID) {
        this.CJID = CJID;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getBridgeCode() {
        return bridgeCode;
    }

    public void setBridgeCode(String bridgeCode) {
        this.bridgeCode = bridgeCode;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}

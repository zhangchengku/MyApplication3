package com.zggk.bridge.Bean;

import java.util.List;

/**
 * Created by 张成昆 on 2019-4-3.
 */

public class addjson {
    private String ID; //ID
    private String BridgeCode;//项目
    private String Description;
    private String ReportTime;
    private String UnintNo;
    private String UserID;
    private String Lon;
    private String Lat;
    private String Location;
    private String ConType;
    private String CompletionState;
    private List<String> PicList;

    public String getCompletionState() {
        return CompletionState;
    }

    public void setCompletionState(String completionState) {
        CompletionState = completionState;
    }

    public List<String> getPicList() {
        return PicList;
    }

    public void setPicList(List<String> picList) {
        PicList = picList;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBridgeCode() {
        return BridgeCode;
    }

    public void setBridgeCode(String bridgeCode) {
        BridgeCode = bridgeCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getReportTime() {
        return ReportTime;
    }

    public void setReportTime(String reportTime) {
        ReportTime = reportTime;
    }

    public String getUnintNo() {
        return UnintNo;
    }

    public void setUnintNo(String unintNo) {
        UnintNo = unintNo;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        Lon = lon;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getConType() {
        return ConType;
    }

    public void setConType(String conType) {
        ConType = conType;
    }

}

package com.zggk.bridge.Bean;

import java.util.List;

/**
 * Created by 张成昆 on 2019-4-4.
 */

public class Add2json {
    private String BridgeGuid;
    private String BridgeCode;
    private String BridgeLocation;
    private String unitNo;
    private String unitName;
    private String ReportTime;
    private String EngineerState;
    private String PriorWork;
    private String CompleteInvestment;
    private String WorkingUnitNo;
    private String WorkingUnitName;
    private String KaiGongTime;
    private String WanGongTime;
    private String Remark;
    private String UserId;
    private List<String> PicList;
    private String PicUrl;
    private String IsSave;

    public String getIsSave() {
        return IsSave;
    }

    public void setIsSave(String isSave) {
        IsSave = isSave;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getBridgeGuid() {
        return BridgeGuid;
    }

    public void setBridgeGuid(String bridgeGuid) {
        BridgeGuid = bridgeGuid;
    }

    public String getBridgeCode() {
        return BridgeCode;
    }

    public void setBridgeCode(String bridgeCode) {
        BridgeCode = bridgeCode;
    }

    public String getBridgeLocation() {
        return BridgeLocation;
    }

    public void setBridgeLocation(String bridgeLocation) {
        BridgeLocation = bridgeLocation;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getReportTime() {
        return ReportTime;
    }

    public void setReportTime(String reportTime) {
        ReportTime = reportTime;
    }

    public String getEngineerState() {
        return EngineerState;
    }

    public void setEngineerState(String engineerState) {
        EngineerState = engineerState;
    }

    public String getPriorWork() {
        return PriorWork;
    }

    public void setPriorWork(String priorWork) {
        PriorWork = priorWork;
    }

    public String getCompleteInvestment() {
        return CompleteInvestment;
    }

    public void setCompleteInvestment(String completeInvestment) {
        CompleteInvestment = completeInvestment;
    }

    public String getWorkingUnitNo() {
        return WorkingUnitNo;
    }

    public void setWorkingUnitNo(String workingUnitNo) {
        WorkingUnitNo = workingUnitNo;
    }

    public String getWorkingUnitName() {
        return WorkingUnitName;
    }

    public void setWorkingUnitName(String workingUnitName) {
        WorkingUnitName = workingUnitName;
    }

    public String getKaiGongTime() {
        return KaiGongTime;
    }

    public void setKaiGongTime(String kaiGongTime) {
        KaiGongTime = kaiGongTime;
    }

    public String getWanGongTime() {
        return WanGongTime;
    }

    public void setWanGongTime(String wanGongTime) {
        WanGongTime = wanGongTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public List<String> getPicList() {
        return PicList;
    }

    public void setPicList(List<String> picList) {
        PicList = picList;
    }
}

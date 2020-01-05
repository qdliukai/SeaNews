package com.liukai.seanews.domain;

public class RecommendResult {
    private String recommendId ;
    private String userId ;
    private String recommendResult;
    private String recommendType;
    private String RecommendTime;

    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecommendResult() {
        return recommendResult;
    }

    public void setRecommendResult(String recommendResult) {
        this.recommendResult = recommendResult;
    }

    public String getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(String recommendType) {
        this.recommendType = recommendType;
    }

    public String getRecommendTime() {
        return RecommendTime;
    }

    public void setRecommendTime(String getRecommendTime) {
        this.RecommendTime = getRecommendTime;
    }
}

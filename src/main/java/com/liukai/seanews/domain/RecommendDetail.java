package com.liukai.seanews.domain;

public class RecommendDetail {

    private String recommendKey;
    private String url;
    private String date;
    private String title;
    private String summary;

    public String getRecommendKey() {
        return recommendKey;
    }

    public void setRecommendKey(String recommendKey) {
        this.recommendKey = recommendKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

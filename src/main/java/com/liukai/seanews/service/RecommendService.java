package com.liukai.seanews.service;

import com.liukai.seanews.domain.RecommendDetail;
import com.liukai.seanews.domain.RecommendResult;
import com.liukai.seanews.domain.User;

public interface RecommendService extends BaseService<RecommendResult> {

    public RecommendResult selectLatestRecommend();

    public RecommendDetail selectRecommendDetail(String recommendKey);
}

package com.liukai.seanews.service.impl;

import com.liukai.seanews.dao.BaseDao;
import com.liukai.seanews.dao.impl.RecommendDaoImpl;
import com.liukai.seanews.domain.RecommendDetail;
import com.liukai.seanews.domain.RecommendResult;
import com.liukai.seanews.service.RecommendService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("recommendServiceImpl")
public class RecommendServiceImpl extends BaseServiceImpl<RecommendResult> implements RecommendService {

    @Resource(name = "recommendDaoImpl")
    public void setDao(BaseDao<RecommendResult> dao) {
        super.setDao(dao);
    }

    @Resource(name = "recommendDaoImpl")
    private RecommendDaoImpl dao ;

    @Override
    public RecommendResult selectLatestRecommend() {
        return dao.selectLatest();
    }

    @Override
    public RecommendDetail selectRecommendDetail(String recommendKey) {
        return dao.selectDetail(recommendKey);
    }
}

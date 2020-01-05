package com.liukai.seanews.dao.impl;

import com.liukai.seanews.dao.BaseDao;
import com.liukai.seanews.domain.RecommendDetail;
import com.liukai.seanews.domain.RecommendResult;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("recommendDaoImpl")
public class RecommendDaoImpl extends SqlSessionDaoSupport implements BaseDao<RecommendResult> {
    @Override
    public void insert(RecommendResult recommendResult) {

    }

    @Override
    public void update(RecommendResult recommendResult) {

    }

    @Override
    public void delete(String userName) {

    }

    @Override
    public RecommendResult selectOne(String userName) {
        return null;
    }

    @Override
    public List<RecommendResult> selectAll() {
        return null;
    }

    @Override
    public List<RecommendResult> selectPage(int offset, int len) {
        return null;
    }

    @Override
    public int selectCount() {
        return 0;
    }

    public RecommendResult selectLatest(){
        return getSqlSession().selectOne("rresult.selectLatest");
    }

    public RecommendDetail selectDetail(String recommendKey){
        return getSqlSession().selectOne("rresult.selectRecommendDetail", recommendKey);
    }
}

package com.liukai.seanews.service.impl;

import com.liukai.seanews.dao.BaseDao;
import com.liukai.seanews.service.BaseService;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    private BaseDao<T> dao ;

    public BaseDao<T> getDao() {
        return dao;
    }
    public void setDao(BaseDao<T> dao) {
        this.dao = dao;
    }

    public void insert(T t) {
        dao.insert(t);
    }

    public void update(T t) {
        dao.update(t);
    }

    public void delete(String userName) {
        dao.delete(userName);
    }

    public T selectOne(String userName) {
        return dao.selectOne(userName);
    }

    public List<T> selectAll() {
        return dao.selectAll();
    }

    public List<T> selectPage(int offset, int len) {
        return dao.selectPage(offset,len);
    }
    public int selectCount() {
        return dao.selectCount();
    }
}

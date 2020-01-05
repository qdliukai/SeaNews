package com.liukai.seanews.service;

import java.util.List;

public interface BaseService<T> {
    public void insert(T t) ;
    public void update(T t) ;
    public void delete(String userName) ;
    public T selectOne(String userName) ;
    public List<T> selectAll() ;
    /**
     * 分页查询
     */
    public List<T> selectPage(int offset, int len);
    public int selectCount();
}

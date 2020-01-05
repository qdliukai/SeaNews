package com.liukai.seanews.dao;

import java.util.List;

/**
 * 基本Dao接口
 * @param <T>
 */
public interface BaseDao<T> {
    public void insert(T t) ;
    public void update(T t) ;
    public void delete(String userName) ;
    public T selectOne(String userName) ;
    public List<T> selectAll() ;
    public List<T> selectPage(int offset, int len);
    public int selectCount();
}

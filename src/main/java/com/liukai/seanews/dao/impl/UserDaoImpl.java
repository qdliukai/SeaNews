package com.liukai.seanews.dao.impl;

import com.liukai.seanews.dao.BaseDao;
import com.liukai.seanews.domain.Task;
import com.liukai.seanews.domain.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDaoImpl")
public class UserDaoImpl extends SqlSessionDaoSupport implements BaseDao<User> {
    public void insert(User user) {

    }

    public void update(User user) {

    }

    public void delete(String userName) {
        getSqlSession().delete("users.delete", userName);
    }

    public User selectOne(String userName) {
        return getSqlSession().selectOne("users.selectOne", userName);
    }

    public List<User> selectAll() {
        return getSqlSession().selectList("users.selectAll");
    }

    public List<User> selectPage(int offset, int len) {
        return null;
    }

    public int selectCount() {
        return 0;
    }

    public int updateUserCollect(User user){
        return getSqlSession().update("users.updateUserCollect", user);
    }

    public int insertSparkTask(Task task){
        return getSqlSession().update("users.insertSparkTask", task);
    }

    public Task selectTaskTypeLatest(Task task){
        return getSqlSession().selectOne("users.selectTaskTypeLatest", task);
    }
}

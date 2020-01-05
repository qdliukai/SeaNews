package com.liukai.seanews.service.impl;

import com.liukai.seanews.dao.BaseDao;
import com.liukai.seanews.dao.impl.RecommendDaoImpl;
import com.liukai.seanews.dao.impl.UserDaoImpl;
import com.liukai.seanews.domain.Task;
import com.liukai.seanews.domain.User;
import com.liukai.seanews.service.UserService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Resource(name = "userDaoImpl")
    public void setDao(BaseDao<User> dao) {
        super.setDao(dao);
    }

    @Resource(name = "userDaoImpl")
    private UserDaoImpl dao ;

    // 这是userService独有的方法，其余的查询删除方法在BaseServiceImpl中实现，此类继承之后可以直接调用。
    public void save(User u) {
        if(u.getUserId() != null){
            this.update(u);
        }
        else{
            this.insert(u);
        }
    }

    @Override
    public void updateUserCollect(User user) {
        dao.updateUserCollect(user);
    }

    @Override
    public void insertSparkTask(Task task) {
        dao.insertSparkTask(task);
    }

    @Override
    public Task selectTaskTypeLatest(Task task) {
        return dao.selectTaskTypeLatest(task);
    }
}

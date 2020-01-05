package com.liukai.seanews.service;

import com.liukai.seanews.domain.Task;
import com.liukai.seanews.domain.User;

public interface UserService extends BaseService<User>{

    public void save(User u);

    public void updateUserCollect(User u);

    public void insertSparkTask(Task task);

    public Task selectTaskTypeLatest(Task task);
}

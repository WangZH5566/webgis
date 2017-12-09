package com.ydh.dao;


import com.ydh.model.User;

import java.util.List;

/**
 * @description:
 * @author: .
 * @createDate: 2016/09/04.
 */
public interface UserDao {

    /**
     * 根据用户名查询管理员和推演用户(登录用)
     * @param userName
     * @return
     */
    public List<User> queryLoginUser(String userName);

}
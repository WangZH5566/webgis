package com.ydh.service;

import com.ydh.dao.UserDao;
import com.ydh.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:用户service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userdao;


    /**
     * 根据用户名查询管理员用户(登录用)
     *
     * @param userName
     * @return
     */
    public User queryLoginUser(String userName){
        List<User> list = userdao.queryLoginUser(userName);
        if (list != null && list.size() > 0) {
            return list.iterator().next();
        }
        return null;
    }

}

package com.ydh.service;

import com.ydh.dao.ExeciseUserIconDao;
import com.ydh.model.ExeciseUserIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人推演练习图标Service
 */
@Service
public class ExeciseUserIconService {

    @Autowired
    private ExeciseUserIconDao execiseUserIconDao;


    public List<ExeciseUserIcon> queryListByUser(Integer userID){
        return this.execiseUserIconDao.queryListByUser(userID);
    }

    public ExeciseUserIcon findByID(Integer id){
        if(id==null){
            return null;
        }
        return this.execiseUserIconDao.findByID(id);
    }

    public String addExeciseUserIcon(ExeciseUserIcon execiseUserIcon){
        this.execiseUserIconDao.addExeciseUserIcon(execiseUserIcon);
        return new StringBuffer("success|").append(execiseUserIcon.getId()).toString();
    }

    public String modifyExeciseUserIcon(ExeciseUserIcon execiseUserIcon){
        this.execiseUserIconDao.modifyExeciseUserIcon(execiseUserIcon);
        return new StringBuffer("success|").append(execiseUserIcon.getId()).toString();
    }

    public void delete(Integer id){
        this.execiseUserIconDao.deleteExeciseUserIcon(id);
    }

}

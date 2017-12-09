package com.ydh.dao;

import com.ydh.dto.ExeciseIconDto;
import com.ydh.model.ExeciseIcon;
import com.ydh.model.ExeciseUserIcon;

import java.util.List;

public interface ExeciseUserIconDao {

    public List<ExeciseUserIcon> queryListByUser(Integer userID);

    public ExeciseUserIcon findByID(Integer id);

    /**
     * 新增ExeciseUserIcon
     * @param execiseUserIcon
     * @return
     */
    public int addExeciseUserIcon(ExeciseUserIcon execiseUserIcon);

    /**
     * 修改ExeciseUserIcon
     * @param execiseUserIcon
     * @return
     */
    public int modifyExeciseUserIcon(ExeciseUserIcon execiseUserIcon);

    /**
     * 删除ExeciseUserIcon
     * @param id
     * @return
     */
    public int deleteExeciseUserIcon(Integer id);

}

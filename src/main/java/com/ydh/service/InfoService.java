package com.ydh.service;

import com.ydh.dao.HistoryLogDao;
import com.ydh.dao.InfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:资料查询service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class InfoService {

    @Autowired
    private InfoDao infoDao;

}

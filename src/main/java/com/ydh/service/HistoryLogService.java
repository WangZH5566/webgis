package com.ydh.service;

import com.ydh.dao.HistoryLogDao;
import com.ydh.dto.HistoryLogDto;
import com.ydh.model.HistoryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:日志service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class HistoryLogService {

    @Autowired
    private HistoryLogDao historyLogDao;

    public List<HistoryLog> queryAll() {
        return historyLogDao.queryAll();
    }

    public List<HistoryLogDto> queryByExecId(Integer execId) {
        return historyLogDao.queryByExecId(execId);
    }

    public void addHistoryLog(HistoryLog log) {
        historyLogDao.addHistoryLog(log);
    }

    public Integer queryHistoryLogCountForPage(HistoryLogDto searchDto) {
        return historyLogDao.queryHistoryLogCountForPage(searchDto);
    }

    public List<HistoryLogDto> queryHistoryLogPage(HistoryLogDto searchDto) {
        return historyLogDao.queryHistoryLogPage(searchDto);
    }

}

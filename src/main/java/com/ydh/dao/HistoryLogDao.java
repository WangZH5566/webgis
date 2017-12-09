package com.ydh.dao;


import com.ydh.dto.HistoryLogDto;
import com.ydh.model.HistoryLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:日志Dao
 * @author: .
 * @createDate: 2016/09/04.
 */
public interface HistoryLogDao {

    List<HistoryLog> queryAll();

    List<HistoryLogDto> queryByExecId(@Param("exec_id") Integer id);

    int addHistoryLog(HistoryLog log);

    Integer queryHistoryLogCountForPage(HistoryLogDto searchDto);

    List<HistoryLogDto> queryHistoryLogPage(HistoryLogDto searchDto);
}
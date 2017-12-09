package com.ydh.dao;


import com.ydh.dto.IconDto;
import com.ydh.model.Icon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IconDao {

    List<Icon> findAll();

    void addIcon(Icon icon);

    void deleteByPath(@Param("path") String path);

    void deleteByGroup(Integer groupID);

    Integer queryIconListCount(IconDto searchDto);

    List<IconDto> queryIconListPage(IconDto searchDto);

    void deleteIconExtByIcon(Integer id);

    void deleteByID(Integer id);

    IconDto findByID(Integer id);

    void updateIcon(Icon icon);
}

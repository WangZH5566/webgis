package com.ydh.service;

import com.ydh.dao.IconDao;
import com.ydh.dto.IconDto;
import com.ydh.model.Icon;
import com.ydh.model.IconExt;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IconService {

    @Autowired
    private IconDao iconDao;

    public List<Icon> list() {
        return iconDao.findAll();
    }

    public String addIcon(Icon icon) {
        this.iconDao.addIcon(icon);
        return "SUCCESS";
    }


    public IconDto findByID(Integer id) {
        return this.iconDao.findByID(id);
    }

    public void delete(String urls) {
        String[] _urls = urls.split(",");
        for (String url : _urls) {
            iconDao.deleteByPath(url);
        }
    }

    public Integer queryIconListCount(IconDto searchDto) {
        return this.iconDao.queryIconListCount(searchDto);
    }

    public List<IconDto> queryIconListPage(IconDto searchDto) {
        return this.iconDao.queryIconListPage(searchDto);
    }

    public String deleteIcon(Integer id) {
        this.iconDao.deleteIconExtByIcon(id);
        this.iconDao.deleteByID(id);
        return "SUCCESS";
    }

    public String updateIcon(Icon icon) {
        this.iconDao.updateIcon(icon);
        return "SUCCESS";
    }
}

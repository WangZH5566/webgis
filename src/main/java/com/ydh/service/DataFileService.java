package com.ydh.service;

import com.ydh.dao.DataFileDao;
import com.ydh.model.DataFile;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DataFileService {
    @Value("${fileserver.resource.path}")
    private String FILESERVER_RESOURCE_PATH;
    @Autowired
    private DataFileDao dataFileDao;

    public DataFile findByID(Integer id) {
        return this.dataFileDao.findByID(id);
    }

    /**
     * 新增
     * @param dataFile
     * @return
     */
    public String addDataFile(DataFile dataFile){
        this.dataFileDao.addDataFile(dataFile);
        return "SUCCESS";
    }

    public String addDataFile(DataFile dataFile, MultipartFile file) throws IOException {
        if(file!=null) {
            String fileName = UUID.randomUUID().toString();
            String extName = file.getOriginalFilename();
            int lastIndex = extName.lastIndexOf(".");
            extName = extName.substring(lastIndex);
            String filePath=File.separator + fileName + extName;
            String fullName = this.FILESERVER_RESOURCE_PATH + filePath;
            File destFile = new File(fullName);
            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            dataFile.setFilePath(filePath);
        }
        this.dataFileDao.addDataFile(dataFile);
        return "SUCCESS";
    }

    /**
     * 修改
     * @param dataFile
     * @return
     */
    public String modifyDataFile(DataFile dataFile){
        this.dataFileDao.modifyDataFile(dataFile);
        return "SUCCESS";
    }

    public List<DataFile> queryDataFile(Integer pid) {
        return this.dataFileDao.queryDataFile(pid);
    }

    public String deleteDataFile(Integer id) {
        //判断是否有子分组
        List<DataFile> list=this.dataFileDao.queryDataFile(id);
        if(list!=null&&list.size()>0){
            return "请先删除该文件夹下的文件";
        }

        this.dataFileDao.deleteByID(id);
        return "SUCCESS";
    }
}

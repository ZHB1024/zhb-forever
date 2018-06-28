package com.forever.zhb.dao;

import com.forever.zhb.pojo.FileInfoData;
import java.util.List;

public interface AttachmentDAO {

    void saveOrUpdate(FileInfoData fileInfoData);

    int countFiles(Integer type,String name);

    List<FileInfoData> queryFiles(Integer type,String name,int start,int pageSize);

    FileInfoData  getFileById(String id);

    /*-------------------照片---------------------------------*/



}

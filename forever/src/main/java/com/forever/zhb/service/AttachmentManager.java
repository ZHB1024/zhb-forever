package com.forever.zhb.service;

import com.forever.zhb.page.Page;
import com.forever.zhb.pojo.FileInfoData;

public interface AttachmentManager {

    void saveOrUpdate(FileInfoData fileInfoData);

    Page<FileInfoData> queryFiles(Integer type,String name,int start,int pageSize);

    FileInfoData  getFileById(String id);

    /*------------------------picture-------------------*/

}

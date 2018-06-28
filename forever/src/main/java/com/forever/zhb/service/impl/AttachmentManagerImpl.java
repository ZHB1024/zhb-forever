package com.forever.zhb.service.impl;

import com.forever.zhb.dao.AttachmentDAO;
import com.forever.zhb.page.Page;
import com.forever.zhb.page.PageUtil;
import com.forever.zhb.pojo.FileInfoData;
import com.forever.zhb.service.AttachmentManager;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentManagerImpl implements AttachmentManager {

    protected  final Logger logger = LoggerFactory.getLogger(AttachmentManagerImpl.class);

    @Resource(name = "attachmentDao")
    private AttachmentDAO attachmentDao;

    @Override
    public void saveOrUpdate(FileInfoData fileInfoData){
        attachmentDao.saveOrUpdate(fileInfoData);
    }

    @Override
    public Page<FileInfoData> queryFiles(Integer type,String name,int start,int pageSize){
        int count = attachmentDao.countFiles(type,name);
        if (count == 0){
            return Page.EMPTY_PAGE;
        }
        if (start >= count) {
            start = 0;
        }
        List<FileInfoData> files = attachmentDao.queryFiles(type,name,start,pageSize);
        return PageUtil.getPage(files.iterator(),start,pageSize,count);

    }

    @Override
    public FileInfoData  getFileById(String id){
        return attachmentDao.getFileById(id);
    }

}

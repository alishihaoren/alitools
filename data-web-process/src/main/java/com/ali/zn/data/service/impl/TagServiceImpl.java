package com.ali.zn.data.service.impl;

import com.ali.zn.data.mapper.TagInfoMapper;
import com.ali.zn.data.pojo.model.TagInfoDO;
import com.ali.zn.data.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagInfoMapper tagInfoMapper;

    @Override
    public List<TagInfoDO> getTagInfoById(List<String> id) {
    return     tagInfoMapper.getTagInfoById(id);

//        return null;
    }
}

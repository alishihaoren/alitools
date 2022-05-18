package com.ali.zn.data.service;

import com.ali.zn.data.pojo.model.TagInfoDO;

import java.util.List;

public  interface TagService {

    List<TagInfoDO> getTagInfoById(List<String> id);

}

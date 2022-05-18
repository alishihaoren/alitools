package com.ali.zn.data.mapper;

import com.ali.zn.data.pojo.model.TagInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TagInfoMapper {

    List<TagInfoDO> getTagInfoById(@Param("list") List<String > list);


}

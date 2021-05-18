package com.bayside.app.opinion.manager.hanlp.dao;

import java.util.List;

import com.bayside.app.opinion.manager.hanlp.model.HanlpWord;

public interface HanlpWordMapper {
    int deleteByPrimaryKey(String id);

    int insert(HanlpWord record);

    int insertSelective(HanlpWord record);

    HanlpWord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HanlpWord record);

    int updateByPrimaryKey(HanlpWord record);
    /**
     * 
     * <p>方法名称：selectBySelective</p>
     * <p>方法描述：模糊查询</p>
     * @param record
     * @return
     * @author Administrator
     * @since  2017年3月3日
     * <p> history 2017年3月3日 Administrator  创建   <p>
     */
    List<HanlpWord> selectBySelective(HanlpWord record);
}
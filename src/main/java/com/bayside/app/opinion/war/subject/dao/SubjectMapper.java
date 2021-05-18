package com.bayside.app.opinion.war.subject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.bayside.app.opinion.war.subject.bo.SubjectBo;
import com.bayside.app.opinion.war.subject.model.Subject;

public interface SubjectMapper {
	/**
	 * 
	 * <p>方法名称：deleteByPrimaryKey</p>
	 * <p>方法描述：根据主键删除专题</p>
	 * @param id
	 * @return
	 * @author sql
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 sql  创建   <p>
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 
     * <p>方法名称：insert</p>
     * <p>方法描述：插入专题数据</p>
     * @param record
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    int insert(Subject record);
    /**
     * 
     * <p>方法名称：insertSelective</p>
     * <p>方法描述：选择插入</p>
     * @param record
     * @return
     * @author sql
     * 
     * 
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    int insertSelective(Subject record);
    /**
     * 
     * <p>方法名称：selectByPrimaryKey</p>
     * <p>方法描述：根据主键查询</p>
     * @param id
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    Subject selectByPrimaryKey(String id);
    /**
     * 查询专题 通过userid
     * 
     */
   List<Subject> selectByUserId(String userid);
   List<Subject> selectAllSubject();
   int updateSubjectByUserid(Subject record);
   int updateSubjectByuserid(Subject record);

   
 
}
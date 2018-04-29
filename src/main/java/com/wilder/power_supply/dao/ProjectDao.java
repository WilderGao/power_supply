package com.wilder.power_supply.dao;

import com.wilder.power_supply.model.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：与工程有关的数据库操作
 */
@Mapper
@Repository
public interface ProjectDao {

    /**
     * 判断数据库中是否存在对应的工程编码
     * @param projectCode
     * @return
     */
    int projectExist(@Param("projectCode") String projectCode);

    /**
     * 插入新工程并返回新工程对应的Id
     * @param project 工程类
     * @return 新工程Id
     */
    int insertNewProject(Project project);
}

package com.wilder.power_supply.dao;

import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.model.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wilder Gao
 * time:2018/4/29
 * Description：与工程有关的数据库操作
 */
@Mapper
@Repository
public interface ProjectDao {

    /**
     * 判断数据库中是否存在对应的工程编码
     * @param projectCode  工程编码
     * @return 0为不存在，否则存在
     */
    int projectExist(@Param("projectCode") String projectCode);


    /**
     * 插入新工程并返回新工程对应的Id
     * @param project 工程类
     * @return 新工程Id
     */
    int insertNewProject(Project project);


    /**
     * 插入新工程的所有材料信息
     * @param projectId 工程Id
     * @param meterials 材料信息集合
     * @return  插入结果
     */
    int meterialDetail(@Param("projectId")int projectId,
                       @Param("list")List<Meterial> meterials);

    /**
     * 批量插入工程
     * @param projects 工程集合
     * @return
     */
    int insertProjectList(@Param("list") List<Project> projects);

    /**
     * 获取工程信息
     * @return
     */
    List<Project> getProjectList();


    /**
     * 根据工程Id获得详细情况
     * @param projectId 工程Id
     * @return
     */
    List<Meterial> getProjectDetail(@Param("projectId") int projectId);


    /**
     * 根据Id获取对应工程
     * @param projectId
     * @return
     */
    Project getProjectById(@Param("projectId") int projectId);


    /**
     * 修改历史工程中材料的数量
     * @param projectId
     * @param materialId
     * @param num
     * @return
     */
    int updateProjectMaterialNum(@Param("projectId")Integer projectId,
                                 @Param("materialId")Integer materialId,
                                 @Param("num") Integer num);
}

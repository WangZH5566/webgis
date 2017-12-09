package com.ydh.dao;

import com.ydh.dto.ExeciseIconCrowdDetailDto;
import com.ydh.dto.ExeciseIconCrowdDto;
import com.ydh.dto.ExeciseIconDto;
import com.ydh.model.ExeciseIcon;
import com.ydh.model.ExeciseIconCrowd;
import com.ydh.model.ExeciseIconCrowdDetail;

import java.util.List;

/**
 * 推演图标Dao
 */
public interface ExeciseIconCrowdDao {

    /**
     * 查询推演下的所有集群明细
     * @param execId
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findDetailByExecId(Integer execId);

    /**
     * 为新增集群查询当前推演下所有图标(icon_type = 0)
     * @param execId 推演id
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findIconForInsertCrowd(Integer execId);

    /**
     * 为新增飞机批次查询当前机场下所有飞机图标(icon_type = 0,main_type=1)
     * @param iconId 机场id
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findIconForInsertPlaneCrowd(Integer iconId);

    /**
     * 新增ExeciseIconCrowd
     * @param execiseIconCrowd
     * @return
     */
    public int addExeciseIconCrowd(ExeciseIconCrowd execiseIconCrowd);

    /**
     * 新增ExeciseIconCrowdDetail
     * @param execiseIconCrowdDetail
     * @return
     */
    public int addExeciseIconCrowdDetail(ExeciseIconCrowdDetail execiseIconCrowdDetail);

    /**
     * 查询当前推演下所有的集群
     * @param execId 推演id
     * @return
     */
    public List<ExeciseIconCrowdDto> findExeciseIconCrowdByExecId(Integer execId);

    /**
     * 查询当前机场下所有的集群(包含旗舰图标id)
     * @param iconId 机场id
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findExeciseIconCrowdByIconId(Integer iconId);

    /**
     * 查询当前集群下所有的图标
     * @param crowdId 集群id
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findExeciseIconCrowdDetailByCrowdId(Integer crowdId);


    /**
     * 根据id删除集群
     * @param crowdId 集群id
     * @return
     */
    public int deleteExeciseIconCrowdById(Integer crowdId);

    /**
     * 根据集群id删除集群详情
     * @param crowdId 集群id
     * @return
     */
    public int deleteExeciseIconCrowdDetailByCrowdId(Integer crowdId);

    /**
     * 根据图标id删除集群详情
     * @param iconId 图标id
     * @return
     */
    public int deleteExeciseIconCrowdDetailByIconId(Integer iconId);

    /**
     * 根据图标id查询该图标所属集群id
     * @param iconId
     * @return
     */
    public List<Integer> findCrowdIdByIconId(Integer iconId);

    /**
     * 查询集群下的集群明细数量
     * @param crowdId
     * @return
     */
    public Integer findDetailCountByCrowdId(Integer crowdId);
}

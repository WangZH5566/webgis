package com.ydh.service;

import com.ydh.dao.*;
import com.ydh.dto.ExeciseEquipmentDto;
import com.ydh.dto.ExeciseIconCrowdDetailDto;
import com.ydh.dto.ExeciseIconCrowdDto;
import com.ydh.dto.ExeciseIconDto;
import com.ydh.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SituationAnnotationService {
    @Autowired
    private ExeciseOrderDao execiseOrderDao;
    @Autowired
    private ExeciseEquipmentDao execiseEquipmentDao;
    @Autowired
    private ExeciseIconDao execiseIconDao;
    @Autowired
    private ExeciseIconCrowdDao execiseIconCrowdDao;
    @Autowired
    private HistoryLogDao historyLogDao;

    public List<ExeciseEquipmentDto> queryIconEquipments(Integer iconID) {
        return this.execiseEquipmentDao.queryIconEquipment(iconID);
    }

    public String sendOrder(ExeciseOrder order) throws Exception{
        Integer orderType=order.getOrderType();
        if(orderType==null){
            return "指令错误，请刷新后重试";
        }

        if(orderType==2){

        }else if(orderType==4){
            //设置速度
            ExeciseIcon icon = execiseIconDao.findExeciseIcon(order.getIconOneId());
            icon.setSpeed(order.getMoveSpeed());
            execiseIconDao.modifyExeciseIcon(icon);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setFightBeginTime(sdf.parse(order.getFightBeginTimeView()));
        if(order.getAddEquipmentTime() != null){
            order.setDamage(null);
            order.setDamageTime(null);
            order.setDamageDetail(null);
        }
        this.execiseOrderDao.addOrder(order);

        //指令加上了，再来添加装载列表
        this.addExecEquipmentList(order);
        StringBuffer sb = new StringBuffer();
        if(orderType == 1){
            String pathCoordinate =  order.getPathCoordinate();
            pathCoordinate = pathCoordinate.substring(1,pathCoordinate.length()-1);
            String[] pathCoordinateArr = pathCoordinate.split("],\\[");
            sb.append("目的地移动指令,,图标:").append(order.getIconName())
                    .append(" 由起始位置:").append(pathCoordinateArr[0]).append("]向目的地:[").append(pathCoordinateArr[pathCoordinateArr.length-1]).append("移动");
        }else if(orderType == 2){
            String pathCoordinate =  order.getPathCoordinate();
            pathCoordinate = pathCoordinate.substring(1,pathCoordinate.length()-1);
            String[] pathCoordinateArr = pathCoordinate.split("],\\[");
            sb.append("维修指令,,图标:").append(order.getIconName())
                    .append(" 由起始位置:").append(pathCoordinateArr[0]).append("]向目的地:[").append(pathCoordinateArr[pathCoordinateArr.length-1]).append("移动");
        }else if(orderType == 3){
            String pathCoordinate =  order.getPathCoordinate();
            pathCoordinate = pathCoordinate.substring(1,pathCoordinate.length()-1);
            String[] pathCoordinateArr = pathCoordinate.split("],\\[");
            sb.append("装载指令,图标:").append(order.getIconName())
                    .append(" 由起始位置:").append(pathCoordinateArr[0]).append("]向目的地:[").append(pathCoordinateArr[pathCoordinateArr.length-1]).append("移动");
        }else if(orderType == 4){
            String pathCoordinate =  order.getPathCoordinate();
            pathCoordinate = pathCoordinate.substring(1,pathCoordinate.length()-1);
            sb.append("方向移动指令,图标:").append(order.getIconName())
                    .append(" 由起始位置:").append(pathCoordinate).append("以速度:").append(order.getMoveSpeed()).append(",航向:").append(order.getMoveAngle()).append("方向移动");
        }
        if(sb.length() > 0){
            HistoryLog historyLog = new HistoryLog();
            historyLog.setExecId(order.getExecId());
            historyLog.setContent(sb.toString());
            historyLog.setFightTime(order.getFightBeginTime());
            historyLogDao.addHistoryLog(historyLog);
        }
        return "SUCCESS";
    }

    private void addExecEquipmentList(ExeciseOrder order){
        Integer orderType=order.getOrderType();
        if(orderType!=3){
            return;
        }
        //更新装载时间
        ExeciseIcon icon = new ExeciseIcon();
        icon.setId(order.getIconOneId());
        icon.setAddEquipmentTime(order.getAddEquipmentTime());
        execiseIconDao.modifyExecIconAddEquipmentTime(icon);

        //装载
        String eqListStr=order.getLoadListStr();
        if(eqListStr==null||"".equals(eqListStr)||order.getIconTwoId()==null){
            return ;
        }
        List<ExeciseEquipmentDto> eqSourceList=this.execiseEquipmentDao.queryIconEquipment(order.getIconTwoId());
        Map<Integer,ExeciseEquipmentDto> eqMap=this.buildIconEquipmentMap(eqSourceList);

        List<ExeciseEquipment> list=new ArrayList<ExeciseEquipment>();
        String[] itemStr=eqListStr.split(",");
        for(String tmp:itemStr){
            if(tmp!=null&&!"".equals(tmp)){
                String[] item=tmp.split("-");
                if(item.length!=2){
                    continue;
                }
                Integer eqID=Integer.parseInt(item[0]);
                ExeciseEquipmentDto eq=eqMap.get(eqID);
                ExeciseEquipment entity=new ExeciseEquipment();
                entity.setIconId(order.getIconOneId());
                entity.setOrderId(order.getId());
                entity.setEquipmentId(eqID);
                entity.setEquipmentName(eq == null ? "" : eq.getEquipmentName());
                entity.setLoadTime(eq==null?null:eq.getLoadTime());
                entity.setEquipmentCount(Integer.parseInt(item[1]));
                entity.setMainType(5);
                list.add(entity);
            }
        }
        if(list!=null&&list.size()>0){
            this.execiseEquipmentDao.batchAdd(list);
        }
    }

    private Map<Integer, ExeciseEquipmentDto> buildIconEquipmentMap(List<ExeciseEquipmentDto> eqSourceList) {
        Map<Integer,ExeciseEquipmentDto> map=new HashMap<Integer,ExeciseEquipmentDto>();
        if(eqSourceList==null||eqSourceList.size()==0){
            return map;
        }
        for(ExeciseEquipmentDto dto:eqSourceList){
            map.put(dto.getEquipment(),dto);
        }
        return map;
    }

    public List<ExeciseOrder> listOrder(Integer execId) {
        return execiseOrderDao.listOrder(execId);
    }

    /**
     * 修改维修开始时间
     * @param execiseOrder
     */
    public void modifyRepairBeginTime(ExeciseOrder execiseOrder){
        execiseOrderDao.modifyRepairBeginTime(execiseOrder);
    }

    /**
     * 修改装载开始时间
     * @param execiseOrder
     */
    public void modifyAddEquipmentBeginTime(ExeciseOrder execiseOrder){
        execiseOrderDao.modifyAddEquipmentBeginTime(execiseOrder);
    }

    /**
     * 修改装载/维修结束标识量
     * @param id 指令id
     */
    public void modifyIsEnd(Integer id){
        execiseOrderDao.modifyIsEnd(id);
    }

    /**
     * 为新增集群查询当前推演下所有图标(icon_type = 0)
     * @param execId 推演id
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findIconForInsertCrowd(Integer execId){
        return execiseIconCrowdDao.findIconForInsertCrowd(execId);
    }

    /**
     * 新增集群
     * @param execiseIconCrowd  集群实体
     * @param iconIds    集群图标id(逗号分隔)
     * @param mainId     长机/旗舰id
     */
    public String addCrowd(ExeciseIconCrowd execiseIconCrowd,String iconIds,Integer mainId){
        if(execiseIconCrowd.getCrowdName() == null || "".equals(execiseIconCrowd.getCrowdName())){
            return "集群名称为空";
        }
        if(iconIds == null || "".equals(iconIds)){
            return "集群图标为空";
        }
        if(mainId == null){
            return "旗舰/长机为空";
        }
        execiseIconCrowdDao.addExeciseIconCrowd(execiseIconCrowd);
        List<ExeciseIconCrowdDetail> icdList = new ArrayList<ExeciseIconCrowdDetail>();
        String[] iconIdArr = iconIds.split(",");
        for(int i=0;i<iconIdArr.length;i++){
            ExeciseIconCrowdDetail icd = new ExeciseIconCrowdDetail();
            icd.setIconId(Integer.valueOf(iconIdArr[i]));
            icd.setCrowdId(execiseIconCrowd.getId());
            if(mainId.equals(icd.getIconId())){
                icd.setIsMain(1);
            }
            icdList.add(icd);
        }
        for(ExeciseIconCrowdDetail icd : icdList){
            execiseIconCrowdDao.addExeciseIconCrowdDetail(icd);
        }
        return "success";
    }

    /**
     * 查询当前推演下所有的集群
     * @param execId 推演id
     * @return
     */
    public List<ExeciseIconCrowdDto> findExeciseIconCrowdByExecId(Integer execId){
        return execiseIconCrowdDao.findExeciseIconCrowdByExecId(execId);
    }

    /**
     * 查询当前机场下所有的集群(包含旗舰图标id)
     * @param iconId 机场id
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findExeciseIconCrowdByIconId(Integer iconId){
        return execiseIconCrowdDao.findExeciseIconCrowdByIconId(iconId);
    }

    /**
     * 查询当前集群下所有的图标
     * @param crowdId 集群id
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findExeciseIconCrowdDetailByCrowdId(Integer crowdId){
        return execiseIconCrowdDao.findExeciseIconCrowdDetailByCrowdId(crowdId);
    }

    /**
     * 根据id删除集群
     * @param crowdId 集群id
     * @return
     */
    public void deleteExeciseIconCrowdById(Integer crowdId){
        execiseIconCrowdDao.deleteExeciseIconCrowdById(crowdId);
        execiseIconCrowdDao.deleteExeciseIconCrowdDetailByCrowdId(crowdId);
    }

    /**
     * 为新增飞机批次查询当前机场下所有飞机图标(icon_type = 0,main_type=1)
     * @param iconId 机场id
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findIconForInsertPlaneCrowd(Integer iconId){
        return execiseIconCrowdDao.findIconForInsertPlaneCrowd(iconId);
    }

    /**
     * 新增集群
     * @param execiseIconCrowd  集群实体
     * @param iconIds    飞机图标id(逗号分隔)
     * @param mainId     长机id
     * @param cs         航速
     * @param ca         航向
     * @param co         起始坐标
     * @param ft         当前作战时间
     * @param userId     当前用户id
     */
    public String addPlaneCrowd(ExeciseIconCrowd execiseIconCrowd,String iconIds,Integer mainId,String cs,String ca,String co,String ft,Integer userId) throws Exception{
        if(execiseIconCrowd.getCrowdName() == null || "".equals(execiseIconCrowd.getCrowdName())){
            return "集群名称为空";
        }
        if(iconIds == null || "".equals(iconIds)){
            return "集群图标为空";
        }
        if(mainId == null){
            return "旗舰/长机为空";
        }
        if(cs == null || "".equals(cs)){
            return "航速为空";
        }
        if(ca == null || "".equals(ca)){
            return "航向为空";
        }
        if(co == null || "".equals(co)){
            return "当前坐标为空";
        }
        if(ft == null || "".equals(ft)){
            return "当前作战时间为空";
        }
        execiseIconCrowdDao.addExeciseIconCrowd(execiseIconCrowd);
        List<ExeciseIconCrowdDetail> icdList = new ArrayList<ExeciseIconCrowdDetail>();
        String[] iconIdArr = iconIds.split(",");

        List<ExeciseIcon> icList = new ArrayList<ExeciseIcon>();
        List<ExeciseOrder> ioList = new ArrayList<ExeciseOrder>();
        List<HistoryLog> hsList = new ArrayList<HistoryLog>();
        for(int i=0;i<iconIdArr.length;i++){
            ExeciseIconCrowdDetail icd = new ExeciseIconCrowdDetail();
            icd.setIconId(Integer.valueOf(iconIdArr[i]));
            icd.setCrowdId(execiseIconCrowd.getId());
            if(mainId.equals(icd.getIconId())){
                icd.setIsMain(1);
            }
            icdList.add(icd);

            ExeciseIcon ic = new ExeciseIcon();
            ic.setId(icd.getIconId());
            ic.setNewestCoordinate(co);
            ic.setSpeed((cs == null || "".equals(cs)) ? null : new BigDecimal(cs));
            ic.setLabelTime(new Date());
            icList.add(ic);
            if(ca != null && !"".equals(ca)){
                ExeciseOrder order=new ExeciseOrder();
                order.setExecId(execiseIconCrowd.getExecId());
                order.setOrderType(4);
                order.setPathCoordinate("[[" + co + "]]");
                order.setIconOneId(icd.getIconId());
                order.setBeginTime(new Date());
                order.setOrderBy(userId);
                order.setMoveSpeed(new BigDecimal(cs));
                order.setMoveAngle(new BigDecimal(ca));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                order.setFightBeginTime(sdf.parse(ft));
                ioList.add(order);
                ExeciseIconDto eiDto = execiseIconDao.findExeciseIconById(icd.getIconId());
                StringBuffer sb = new StringBuffer();
                String pathCoordinate =  order.getPathCoordinate();
                pathCoordinate = pathCoordinate.substring(1,pathCoordinate.length()-1);
                sb.append("起飞指令,飞机:").append(eiDto.getIconName())
                        .append(" 由起始位置:").append(pathCoordinate).append("以速度:").append(order.getMoveSpeed()).append(",航向:").append(order.getMoveAngle()).append("方向移动");
                if(sb.length() > 0){
                    HistoryLog historyLog = new HistoryLog();
                    historyLog.setExecId(order.getExecId());
                    historyLog.setContent(sb.toString());
                    historyLog.setFightTime(order.getFightBeginTime());
                    hsList.add(historyLog);
                }
            }
        }
        for(ExeciseIconCrowdDetail icd : icdList){
            execiseIconCrowdDao.addExeciseIconCrowdDetail(icd);
        }
        for(ExeciseIcon ic : icList){
            execiseIconDao.modifyExecIconData(ic);
        }
        for(ExeciseOrder io : ioList){
            execiseOrderDao.addOrder(io);
        }
        for(HistoryLog hs : hsList){
            historyLogDao.addHistoryLog(hs);
        }
        return "success";
    }

    /**
     * 增加降落指令
     * @param crowdId  集群id
     * @param plId     机场id
     * @param mco      集群旗舰坐标
     * @param aco      机场坐标
     * @param ft       当前作战时间
     * @param userId   当前登录用户id
     * @param execId   当前推演id
     * @return
     * @throws Exception
     */
    public String addLandingOrder(Integer crowdId,Integer plId,String mco,String aco,String ft,Integer userId,Integer execId) throws Exception{
        if(crowdId == null){
            return "集群id为空";
        }
        if(plId == null){
            return "机场id为空";
        }
        if(mco == null || "".equals(mco)){
            return "旗舰坐标为空";
        }
        if(aco == null || "".equals(aco)){
            return "机场坐标为空";
        }
        if(ft == null || "".equals(ft)){
            return "当前作战时间为空";
        }
        //查询当前集群下所有飞机
        List<ExeciseIconCrowdDetailDto> cdDtos = execiseIconCrowdDao.findExeciseIconCrowdDetailByCrowdId(crowdId);
        if(cdDtos != null && cdDtos.size() > 0){
            for(ExeciseIconCrowdDetailDto d : cdDtos){
                StringBuffer sb = new StringBuffer();
                sb.append("[[").append(mco).append("],[").append(aco).append("]]");
                ExeciseOrder order=new ExeciseOrder();
                order.setExecId(execId);
                order.setOrderType(5);
                order.setPathCoordinate(sb.toString());
                order.setIconOneId(d.getIconId());
                order.setIconTwoId(plId);
                order.setBeginTime(new Date());
                order.setOrderBy(userId);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                order.setFightBeginTime(sdf.parse(ft));
                execiseOrderDao.addOrder(order);
                ExeciseIconDto eiDto = execiseIconDao.findExeciseIconById(d.getIconId());
                StringBuffer sb1 = new StringBuffer();
                sb1.append("降落指令,飞机:").append(eiDto.getIconName())
                        .append(" 由起始位置:[").append(mco).append("]向目的地:[").append(aco).append("]降落");
                if(sb1.length() > 0){
                    HistoryLog historyLog = new HistoryLog();
                    historyLog.setExecId(order.getExecId());
                    historyLog.setContent(sb1.toString());
                    historyLog.setFightTime(order.getFightBeginTime());
                    historyLogDao.addHistoryLog(historyLog);
                }
            }
        }
        return "success";
    }

    /**
     * 增加降落指令
     * @param iconId   飞机图标id
     * @param plId     机场id
     * @param mco      集群旗舰坐标
     * @param aco      机场坐标
     * @param ft       当前作战时间
     * @param userId   当前登录用户id
     * @param execId   当前推演id
     * @return
     * @throws Exception
     */
    public String addLandingOrderSignle(Integer iconId,Integer plId,String mco,String aco,String ft,Integer userId,Integer execId) throws Exception{
        if(iconId == null){
            return "飞机id为空";
        }
        if(plId == null){
            return "机场id为空";
        }
        if(mco == null || "".equals(mco)){
            return "飞机坐标为空";
        }
        if(aco == null || "".equals(aco)){
            return "机场坐标为空";
        }
        if(ft == null || "".equals(ft)){
            return "当前作战时间为空";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("[[").append(mco).append("],[").append(aco).append("]]");
        ExeciseOrder order=new ExeciseOrder();
        order.setExecId(execId);
        order.setOrderType(5);
        order.setPathCoordinate(sb.toString());
        order.setIconOneId(iconId);
        order.setIconTwoId(plId);
        order.setBeginTime(new Date());
        order.setOrderBy(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setFightBeginTime(sdf.parse(ft));
        execiseOrderDao.addOrder(order);
        ExeciseIconDto eiDto = execiseIconDao.findExeciseIconById(iconId);
        StringBuffer sb1 = new StringBuffer();
        sb1.append("降落指令,飞机:").append(eiDto.getIconName())
                .append(" 由起始位置:[").append(mco).append("]向目的地:[").append(aco).append("]降落");
        if(sb1.length() > 0){
            HistoryLog historyLog = new HistoryLog();
            historyLog.setExecId(order.getExecId());
            historyLog.setContent(sb1.toString());
            historyLog.setFightTime(order.getFightBeginTime());
            historyLogDao.addHistoryLog(historyLog);
        }
        return "success";
    }

    /**
     * 降落完成
     * @param oid  指令id
     * @param fid  图标id
     * @param aid  机场id
     * @return
     */
    public String modifyWhenLandingEnd(Integer oid,Integer fid,Integer aid){
        //更新指令的isEnd为1
        execiseOrderDao.modifyIsEnd(oid);
        //更新图标的坐标、速度为null
        execiseIconDao.modifyExecIconNull(fid,aid);
        //查询当前图标的集群并删除
        List<Integer> ids = execiseIconCrowdDao.findCrowdIdByIconId(fid);
        if(ids != null && ids.size() > 0){
            Integer id = ids.get(0);
            Integer co = execiseIconCrowdDao.findDetailCountByCrowdId(id);
            if(co.equals(2)){
                //删除集群及集群明细
                execiseIconCrowdDao.deleteExeciseIconCrowdById(id);
                execiseIconCrowdDao.deleteExeciseIconCrowdDetailByCrowdId(id);
            }else{
                //只删除集群明细
                execiseIconCrowdDao.deleteExeciseIconCrowdDetailByIconId(fid);
            }
            /*for(Integer id : ids){
                execiseIconCrowdDao.deleteExeciseIconCrowdById(id);
                execiseIconCrowdDao.deleteExeciseIconCrowdDetailByCrowdId(id);
            }*/
        }
        return "success";
    }


    /**
     * 查询推演下的所有集群明细
     * @param execId
     * @return
     */
    public List<ExeciseIconCrowdDetailDto> findDetailByExecId(Integer execId){
        return execiseIconCrowdDao.findDetailByExecId(execId);
    }
}

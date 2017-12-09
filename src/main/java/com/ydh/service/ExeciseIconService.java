package com.ydh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ydh.dao.*;
import com.ydh.dto.BaseInfoDto;
import com.ydh.dto.ExeciseIconCrowdDetailDto;
import com.ydh.dto.ExeciseIconDto;
import com.ydh.dto.ExeciseTroopDto;
import com.ydh.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 推演图标Service
 */
@Service
public class ExeciseIconService {

    @Autowired
    private ExeciseIconDao execiseIconDao;
    @Autowired
    private ExeciseEquipmentDao execiseEquipmentDao;
    @Autowired
    private ExeciseTroopDao execiseTroopDao;
    @Autowired
    private ExeciseOrderDao execiseOrderDao;
    @Autowired
    private BaseInfoDao baseInfoDao;
    @Autowired
    private HistoryLogDao historyLogDao;

    /**
     * 根据推演id,查询该推演下所有图标
     * @param execId
     * @return
     */
    public List<ExeciseIconDto> findExeciseIconByExecId(Integer execId){
        return execiseIconDao.findExeciseIconByExecId(execId);
    }

    /**
     * 根据图标id,查询图标
     * @param ids
     * @return
     */
    public List<ExeciseIconDto> findExeciseIconByIds(Integer[] ids){
        return execiseIconDao.findExeciseIconByIds(ids);
    }

    /**
     * 根据推演id,查询该推演下所有图标(新增图标时用)
     * @param execId
     * @return
     */
    public List<ExeciseIconDto> findExeciseIconByExecIdForUsed(Integer execId){
        return execiseIconDao.findExeciseIconByExecIdForUsed(execId);
    }

    /**
     * 根据id,查询推演图标
     * @param id
     * @return
     */
    public ExeciseIconDto findExeciseIconById(Integer id){
        return execiseIconDao.findExeciseIconById(id);
    }

    public ExeciseIcon findExeciseIcon(Integer id){
        return execiseIconDao.findExeciseIcon(id);
    }

    /**
     * 删除ExeciseIcon
     * @param id
     * @return
     */
    public void deleteExeciseIcon(Integer id){
        execiseEquipmentDao.deleteExeciseEquipmentByExecIconId(id);
        execiseIconDao.deleteExeciseIcon(id);
    }

    /**
     * 更新推演图标的数据
     * @param icon
     */
    public void modifyExecIconData(ExeciseIcon icon){
        this.execiseIconDao.modifyExecIconData(icon);
    }

    public ExeciseIcon addExecIcon(JSONObject json) throws Exception{
        //接收参数
        Integer troopId = json.getInteger("troopId");
        String coordinates=json.getString("coordinates");
        String fightBeginTimeView=json.getString("fightBeginTimeView");
        Integer createUser=json.getInteger("createUser");
        //查询ExeciseTroop
        ExeciseTroopDto execiseTroopDto = execiseTroopDao.queryById(troopId);
        //查询当前机场下的所有飞机
        List<ExeciseTroopDto> execiseTroopDtoList = execiseTroopDao.queryByByAirportId(troopId);
        ExeciseIcon execiseIcon = null;
        List<ExeciseIcon> execiseIconList = new ArrayList<>();
        if(execiseTroopDto != null){
            execiseIcon = new ExeciseIcon();
            execiseIcon.setIconId(execiseTroopDto.getIconId());
            execiseIcon.setIconName(execiseTroopDto.getIconName());
            execiseIcon.setIconData(execiseTroopDto.getIconData());
            execiseIcon.setSpeed(execiseTroopDto.getSpeed());
            execiseIcon.setMaxSpeed(execiseTroopDto.getMaxSpeed());
            execiseIcon.setNewestCoordinate(coordinates);
            execiseIcon.setLabelBy(createUser);
            execiseIcon.setLabelTime(new Date());
            execiseIcon.setIsDelete(0);
            execiseIcon.setExecId(execiseTroopDto.getExecId());
            execiseIcon.setColorArray(execiseTroopDto.getColorArray());
            execiseIcon.setUnitId(execiseTroopDto.getUnitId());
            execiseIcon.setBaseInfoId(execiseTroopDto.getBaseInfoId());
            execiseIcon.setMainType(execiseTroopDto.getMainType());
            String speedUnit="";
            if(execiseTroopDto.getMainType()!=null&&(execiseTroopDto.getMainType()==0||execiseTroopDto.getMainType()==1)){
                speedUnit=execiseTroopDto.getMainType()==0?"kn":"km";
            }
            execiseIcon.setSpeedUnit(speedUnit);
            execiseIcon.setIconType(0);
            execiseIcon.setBelongAirport(null);
            execiseIcon.setExeciseTroopId(execiseTroopDto.getId());
            execiseIconDao.addExeciseIcon(execiseIcon);
            //更新雷弹关系
            execiseEquipmentDao.modifyExeciseIconIdByExeciseTroopId(execiseTroopDto.getId(),execiseIcon.getId());
        }
        if(execiseIcon != null && execiseTroopDtoList != null && execiseTroopDtoList.size()>0){
            for(ExeciseTroopDto dto : execiseTroopDtoList){
                ExeciseIcon ei = new ExeciseIcon();
                ei.setIconId(dto.getIconId());
                ei.setIconName(dto.getIconName());
                ei.setIconData(dto.getIconData());
                ei.setSpeed(null);
                execiseIcon.setMaxSpeed(dto.getMaxSpeed());
                //ei.setNewestCoordinate(coordinates);
                ei.setLabelBy(createUser);
                ei.setLabelTime(new Date());
                ei.setIsDelete(0);
                ei.setExecId(dto.getExecId());
                ei.setColorArray(dto.getColorArray());
                ei.setUnitId(dto.getUnitId());
                ei.setBaseInfoId(dto.getBaseInfoId());
                ei.setMainType(dto.getMainType());
                ei.setSpeedUnit("km");
                ei.setIconType(0);
                ei.setBelongAirport(execiseIcon.getId());
                ei.setExeciseTroopId(dto.getId());
                execiseIconDao.addExeciseIcon(ei);
                execiseIconList.add(ei);
                //更新雷弹关系
                execiseEquipmentDao.modifyExeciseIconIdByExeciseTroopId(dto.getId(),ei.getId());
            }
        }
        // 根据航向设置命令
        if(execiseTroopDto != null && execiseTroopDto.getMoveAngle() != null){
            ExeciseOrder order=new ExeciseOrder();
            order.setExecId(execiseTroopDto.getExecId());
            order.setOrderType(4);
            order.setPathCoordinate("[[" + coordinates + "]]");
            order.setIconOneId(execiseIcon.getId());
            order.setBeginTime(new Date());
            order.setOrderBy(createUser);
            order.setMoveSpeed(execiseTroopDto.getSpeed());
            order.setMoveAngle(execiseTroopDto.getMoveAngle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setFightBeginTime(sdf.parse(fightBeginTimeView));
            this.execiseOrderDao.addOrder(order);
            StringBuffer sb1 = new StringBuffer();
            sb1.append("方向移动指令,图标:").append(execiseTroopDto.getIconName())
                    .append(" 由起始位置:[").append(coordinates).append("]以速度:").append(order.getMoveSpeed()).append(",航向:").append(order.getMoveAngle()).append("方向移动");
            HistoryLog historyLog = new HistoryLog();
            historyLog.setExecId(order.getExecId());
            historyLog.setContent(sb1.toString());
            historyLog.setFightTime(order.getFightBeginTime());
            historyLogDao.addHistoryLog(historyLog);
        }
        return execiseIcon;
    }

    /*public ExeciseIcon addExecIcon(JSONObject json) throws Exception{
        //接收参数
        Integer iconId = json.getInteger("iconID");
        Integer baseInfoID=json.getInteger("baseInfo");
        String iconData=json.getString("iconData");
        Integer mainType=json.getInteger("mainType");
        String colorChange=json.getString("colorArray");
        Integer unit=json.getInteger("unit");
        String name=json.getString("name");
        Integer moveAngle=json.getInteger("moveAngle");
        String coordinates=json.getString("coordinates");
        Integer createUser=json.getInteger("createUser");
        Integer execId=json.getInteger("exec_id");
        String speed=json.getString("speed");
        String maxSpeed=json.getString("maxSpeed");
        String fightBeginTimeView=json.getString("fightBeginTimeView");

        ExeciseIcon execiseIcon = new ExeciseIcon();
        execiseIcon.setIconId(iconId == null ? null : Integer.valueOf(iconId));
        execiseIcon.setIconName(name);
        execiseIcon.setIconData(iconData);
        execiseIcon.setSpeed((speed == null || "".equals(speed)) ? null : new BigDecimal(speed));
        execiseIcon.setMaxSpeed((maxSpeed == null || "".equals(maxSpeed)) ? null : new BigDecimal(maxSpeed));
        execiseIcon.setNewestCoordinate(coordinates);
        execiseIcon.setLabelBy(createUser);
        execiseIcon.setIsDelete(0);
        execiseIcon.setExecId(execId == null ? null : Integer.valueOf(execId));
        execiseIcon.setColorArray(colorChange);
        execiseIcon.setUnitId(unit);
        execiseIcon.setBaseInfoId(baseInfoID);
        execiseIcon.setMainType(mainType);
        String speedUnit="";
        if(mainType!=null&&mainType==0||mainType==1){
            speedUnit=mainType==0?"kn":"km";
        }
        execiseIcon.setSpeedUnit(speedUnit);
        execiseIcon.setIconType(0);
        execiseIcon.setBelongAirport(null);

        //组装机场数据
        String planeAmmu = json.getString("planeAmmu");
        //key--飞机的baseInfoId  value--飞机的icon下的baseInfo信息
        Map<Integer,ExeciseIcon> planeMap = new HashMap<Integer, ExeciseIcon>();
        //key--飞机的baseInfoId  value--飞机的icon下的雷弹信息
        Map<Integer,List<ExeciseEquipment>> planeAmmuMap = new HashMap<Integer,List<ExeciseEquipment>>();
        if(planeAmmu != null){
            JSONObject jo_pl = JSON.parseObject(json.getString("planeAmmu"));
            //查询飞机icon的baseInfo信息
            List<Integer> idList = new ArrayList<Integer>(jo_pl.keySet().size());
            for(String k : jo_pl.keySet()){
                idList.add(Integer.valueOf(k));
            }
            List<ExeciseIcon> planeList = baseInfoDao.queryBaseInfoByIds(idList);
            for(ExeciseIcon pl:planeList){
                planeMap.put(pl.getBaseInfoId(),pl);
            }
            for(String k1 : jo_pl.keySet()){
                List<ExeciseEquipment> ammuList = new ArrayList<ExeciseEquipment>();
                //key -- 飞机的baseInfoId
                //value -- 飞机的雷弹
                JSONObject jo_am = jo_pl.getJSONObject(k1);
                if(jo_am != null){
                    for(String k2 : jo_am.keySet()){
                        //key -- 飞机雷弹的baseInfoId
                        //value -- 飞机的雷弹的名称、数量
                        JSONObject am_info = jo_am.getJSONObject(k2);
                        ExeciseEquipment ammu = new ExeciseEquipment();
                        ammu.setMainType(5);
                        ammu.setEquipmentCount(am_info.getInteger("co"));
                        ammu.setEquipmentId(Integer.valueOf(k2));
                        ammu.setEquipmentName(am_info.getString("na"));
                        ammuList.add(ammu);
                    }
                    planeAmmuMap.put(Integer.valueOf(k1),ammuList);
                }
            }
        }

        this.execiseIconDao.addExeciseIcon(execiseIcon);

        JSONArray ja_am=json.getJSONArray("ammunitions");
        JSONObject[] am=ja_am.toArray(new JSONObject[0]);
        JSONArray ja_eq=json.getJSONArray("equipments");
        JSONObject[] eq=ja_eq.toArray(new JSONObject[0]);
        List<ExeciseEquipment> eelist=new ArrayList<ExeciseEquipment>();
        if(am!=null&&am.length>0){
            for(JSONObject j:am){
                Integer id=j.getInteger("id");
                Integer count=j.getInteger("count");
                String n=j.getString("name");
                Integer loadTime=j.getInteger("loadTime");
                ExeciseEquipment ee=new ExeciseEquipment();
                ee.setMainType(5);
                ee.setEquipmentCount(count);
                ee.setEquipmentId(id);
                ee.setEquipmentName(n);
                ee.setIconId(execiseIcon.getId());
                ee.setLoadTime(loadTime);
                eelist.add(ee);
            }
        }
        if(eq!=null&&eq.length>0){
            for(JSONObject j:eq){
                Integer id=j.getInteger("id");
                Integer count=j.getInteger("count");
                String n=j.getString("name");
                ExeciseEquipment ee=new ExeciseEquipment();
                ee.setMainType(7);
                ee.setEquipmentCount(count);
                ee.setEquipmentId(id);
                ee.setEquipmentName(n);
                ee.setIconId(execiseIcon.getId());
                eelist.add(ee);
            }
        }

        if(planeMap.size() > 0){
            for(Map.Entry<Integer,ExeciseIcon> entry : planeMap.entrySet()){
                ExeciseIcon ei = entry.getValue();
                ei.setSpeed(null);
                //ei.setNewestCoordinate(coordinates);
                ei.setLabelBy(createUser);
                ei.setIsDelete(0);
                ei.setExecId(execId == null ? null : Integer.valueOf(execId));
                ei.setColorArray("[]");
                ei.setUnitId(unit);
                ei.setSpeedUnit("km");
                ei.setIconType(0);
                ei.setBelongAirport(execiseIcon.getId());
                this.execiseIconDao.addExeciseIcon(ei);
                if(planeAmmuMap.get(entry.getKey()) != null){
                    for(ExeciseEquipment e : planeAmmuMap.get(entry.getKey())){
                        e.setIconId(ei.getId());
                    }
                    eelist.addAll(planeAmmuMap.get(entry.getKey()));
                }
            }
        }

        if(eelist!=null&&eelist.size()>0){
            this.execiseEquipmentDao.batchAdd(eelist);
        }

        if(moveAngle!=null&&!"".equals(moveAngle)){
            ExeciseOrder order=new ExeciseOrder();
            order.setExecId(execId == null ? null : Integer.valueOf(execId));
            order.setOrderType(4);
            order.setPathCoordinate("[[" + coordinates + "]]");
            order.setIconOneId(execiseIcon.getId());
            order.setBeginTime(new Date());
            order.setOrderBy(createUser);
            order.setMoveAngle(new BigDecimal(moveAngle));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setFightBeginTime(sdf.parse(fightBeginTimeView));
            this.execiseOrderDao.addOrder(order);
        }

        return execiseIcon;
    }*/

    /**
     * 删除图标
     * @param id
     * @param mt
     * @return
     */
    public String deleteIcon(Integer id,Integer mt) {
        //先只做逻辑删除，推演回放是会用到
//        this.execiseEquipmentDao.deleteExeciseEquipmentByExecIconId(id);
//        this.execiseOrderDao.deleteIconOrders(id);
        if(mt.equals(6)){
            //删除机场时，先删除它下面的飞机
            this.execiseIconDao.deleteExeciseIconByAirport(id);
        }
        this.execiseIconDao.deleteExeciseIcon(id);
        return "SUCCESS";
    }

    public String modifyIconLocation(Integer id,String lot,String lat) {
        //将度秒分转化成度
        StringBuffer coordinates = new StringBuffer();
        String[] arr1 = lot.split(",");
        Double d1 = 0.0;
        for(int i=0;i<arr1.length;i++){
            if(i==0){
                d1 = Double.valueOf(arr1[i]);
            }else if(i == 1){
                d1 = d1 + Double.valueOf(arr1[i])/60;
            }else if(i == 2){
                d1 = d1 + Double.valueOf(arr1[i])/3600;
            }
        }
        String[] arr2 = lat.split(",");
        Double d2 = 0.0;
        for(int i=0;i<arr2.length;i++){
            if(i==0){
                d2 = Double.valueOf(arr2[i]);
            }else if(i == 1){
                d2 = d2 + Double.valueOf(arr2[i])/60;
            }else if(i == 2){
                d2 = d2 + Double.valueOf(arr2[i])/3600;
            }
        }
        DecimalFormat df = new DecimalFormat("#.00000000000000");
        coordinates.append(df.format(d1)).append(",").append(df.format(d2));

        ExeciseIcon icon = new ExeciseIcon();
        icon.setId(id);
        icon.setNewestCoordinate(coordinates.toString());
        execiseIconDao.modifyExeciseIconCoordinate(icon);
        return icon.getNewestCoordinate();
    }

    public ExeciseIcon addExecText(JSONObject json) {
        //接收参数
        String startPoint = json.getString("startPoint");
        String iconText=json.getString("iconText");
        Integer createUser=json.getInteger("createUser");
        Integer execId=json.getInteger("exec_id");
        Integer unitId=json.getInteger("unit_id");

        ExeciseIcon execiseIcon = new ExeciseIcon();
        execiseIcon.setIconName(iconText);
        execiseIcon.setNewestCoordinate(startPoint);
        execiseIcon.setLabelBy(createUser);
        execiseIcon.setLabelTime(new Date());
        execiseIcon.setIsDelete(0);
        execiseIcon.setUnitId(unitId);
        execiseIcon.setExecId(execId == null ? null : Integer.valueOf(execId));
        execiseIcon.setIconType(1);
        this.execiseIconDao.addExeciseIcon(execiseIcon);
        return execiseIcon;
    }

    public void modifyExecIcon(ExeciseIcon icon) {
        this.execiseIconDao.modifyExeciseIcon(icon);
    }

    /**
     * 修改推演图标的受损程度
     * @param icon
     */
    public void modifyExecIconDamage(ExeciseIcon icon) {
        this.execiseIconDao.modifyExecIconDamage(icon);
    }

    /**
     * 修改推演图标的装载时间
     * @param icon
     */
    public void modifyExecIconAddEquipmentTime(ExeciseIcon icon) {
        this.execiseIconDao.modifyExecIconAddEquipmentTime(icon);
    }

    /**
     * 根据推演id,单位id,mainType,查询推演图标
     * @param map
     * @return
     */
    public List<ExeciseIconDto> findExeciseIconByUnitIdAndMainType(Map<String,Object> map){
        return execiseIconDao.findExeciseIconByUnitIdAndMainType(map);
    }
}

package com.ydh.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ydh.dao.BaseInfoDao;
import com.ydh.dao.ExeciseEquipmentDao;
import com.ydh.dao.ExeciseIconDao;
import com.ydh.dao.ExeciseTroopDao;
import com.ydh.dto.ExeciseEquipmentDto;
import com.ydh.dto.ExeciseTroopDto;
import com.ydh.model.ExeciseEquipment;
import com.ydh.model.ExeciseIcon;
import com.ydh.model.ExeciseTroop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 推演兵力Service
 */
@Service
public class ExeciseTroopService {

    @Autowired
    private ExeciseTroopDao execiseTroopDao;
    @Autowired
    private BaseInfoDao baseInfoDao;
    @Autowired
    private ExeciseEquipmentDao execiseEquipmentDao;
    @Autowired
    private ExeciseIconDao execiseIconDao;

    /**
     *
     * @param id
     * @return
     */
    public ExeciseTroopDto queryById(Integer id){
        return execiseTroopDao.queryById(id);

    }

    /**
     * 查询推演兵力总数量
     * @param searchDto
     * @return
     */
    public Integer queryExeciseTroopCount(ExeciseTroopDto searchDto) {
        return execiseTroopDao.queryExeciseTroopCount(searchDto);
    }

    /**
     * 分页查询推演兵力
     * @param searchDto
     * @return
     */
    public List<ExeciseTroopDto> queryExeciseTroopPage(ExeciseTroopDto searchDto) {
        return execiseTroopDao.queryExeciseTroopPage(searchDto);
    }

    /**
     * 新增兵力
     * @param json
     * @return
     * @throws Exception
     */
    public String addExeciseTroop(JSONObject json) throws Exception{
        //接收参数
        Integer iconId = json.getInteger("iconID");
        Integer baseInfoID=json.getInteger("baseInfo");
        String iconData=json.getString("iconData");
        Integer mainType=json.getInteger("mainType");
        String colorChange=json.getString("colorArray");
        Integer unit=json.getInteger("unit");
        String name=json.getString("name");
        Integer moveAngle=json.getInteger("moveAngle");
        Integer execId=json.getInteger("exec_id");
        String speed=json.getString("speed");
        String maxSpeed=json.getString("maxSpeed");

        ExeciseTroop execiseTroop = new ExeciseTroop();
        execiseTroop.setIconId(iconId == null ? null : Integer.valueOf(iconId));
        execiseTroop.setIconName(name);
        execiseTroop.setIconData(iconData);
        execiseTroop.setSpeed((speed == null || "".equals(speed)) ? null : new BigDecimal(speed));
        execiseTroop.setMaxSpeed((maxSpeed == null || "".equals(maxSpeed)) ? null : new BigDecimal(maxSpeed));
        execiseTroop.setExecId(execId == null ? null : Integer.valueOf(execId));
        execiseTroop.setColorArray(colorChange);
        execiseTroop.setUnitId(unit);
        execiseTroop.setBaseInfoId(baseInfoID);
        execiseTroop.setMainType(mainType);
        execiseTroop.setMoveAngle(moveAngle == null?null:new BigDecimal(moveAngle));
        String speedUnit="";
        if(mainType!=null&&mainType==0||mainType==1){
            speedUnit=mainType==0?"kn":"km";
        }
        execiseTroop.setSpeedUnit(speedUnit);
        execiseTroop.setBelongAirport(null);

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

        this.execiseTroopDao.addExeciseTroop(execiseTroop);

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
                ee.setExeciseTroopId(execiseTroop.getId());
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
                ee.setExeciseTroopId(execiseTroop.getId());
                eelist.add(ee);
            }
        }

        if(planeMap.size() > 0){
            for(Map.Entry<Integer,ExeciseIcon> entry : planeMap.entrySet()){
                ExeciseIcon ei = entry.getValue();
                ExeciseTroop et = new ExeciseTroop();
                et.setIconId(ei.getIconId());
                et.setIconName(ei.getIconName());
                et.setIconData(ei.getIconData());
                et.setSpeed(null);
                et.setMaxSpeed(ei.getMaxSpeed());
                et.setExecId(execId == null ? null : Integer.valueOf(execId));
                et.setColorArray("[]");
                et.setUnitId(unit);
                et.setSpeedUnit("km");
                et.setBaseInfoId(ei.getBaseInfoId());
                et.setMainType(ei.getMainType());
                et.setBelongAirport(execiseTroop.getId());
                this.execiseTroopDao.addExeciseTroop(et);
                if(planeAmmuMap.get(entry.getKey()) != null){
                    for(ExeciseEquipment e : planeAmmuMap.get(entry.getKey())){
                        e.setExeciseTroopId(et.getId());
                    }
                    eelist.addAll(planeAmmuMap.get(entry.getKey()));
                }
            }
        }
        if(eelist!=null&&eelist.size()>0){
            this.execiseEquipmentDao.batchAdd(eelist);
        }
        return "success";
    }

    public void modifyExeciseTroop(JSONObject json){
        Integer troopId=json.getInteger("troopId");
        Integer unit=json.getInteger("unit");
        Integer moveAngle=json.getInteger("moveAngle");
        String speed=json.getString("speed");
        String longitude=json.getString("longitude");
        String latitude=json.getString("latitude");
        ExeciseTroop execiseTroop = new ExeciseTroop();
        execiseTroop.setId(troopId);
        execiseTroop.setSpeed((speed == null || "".equals(speed)) ? null : new BigDecimal(speed));
        execiseTroop.setUnitId(unit);
        execiseTroop.setMoveAngle(moveAngle == null?null:new BigDecimal(moveAngle));
        execiseTroop.setLongitude(longitude);
        execiseTroop.setLatitude(latitude);
        execiseTroopDao.modifyExeciseTroop(execiseTroop);
        //更新机场下的单位
        execiseTroopDao.modifyExeciseTroopWhenAirport(execiseTroop);
        //新增或更新ExeciseIcon
        List<ExeciseIcon> list = execiseIconDao.findExeciseIconByTroopId(troopId);
        //将度秒分转化成度
        StringBuffer coordinates = new StringBuffer();
        String[] arr1 = longitude.split(",");
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
        String[] arr2 = latitude.split(",");
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
        if(list.size() >0){
            //更新操作
            execiseIconDao.modifyByExeciseTroop(execiseTroop,coordinates.toString());
            execiseIconDao.modifyByExeciseTroopWhenAirport(execiseTroop,coordinates.toString());
        }else{
            //新增操作
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
                execiseIcon.setSpeed(speed == null ? null : new BigDecimal(speed));
                execiseIcon.setMaxSpeed(execiseTroopDto.getMaxSpeed());
                execiseIcon.setNewestCoordinate(coordinates.toString());
                execiseIcon.setIsDelete(0);
                execiseIcon.setExecId(execiseTroopDto.getExecId());
                execiseIcon.setColorArray(execiseTroopDto.getColorArray());
                execiseIcon.setUnitId(unit);
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
                    //ei.setNewestCoordinate(coordinates);
                    ei.setIsDelete(0);
                    ei.setExecId(dto.getExecId());
                    ei.setColorArray(dto.getColorArray());
                    ei.setUnitId(unit);
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
        }
        //  这里就不去根据航向下达指令了(只是个BUG)
    }

    /**
     * 根据id删除兵力
     * @param troopId
     */
    public void deleteExeciseTroopById(Integer troopId){
        //如果此兵力是机场,则删除机场下的飞机,及飞机相关雷弹
        execiseEquipmentDao.deleteExeciseEquipmentByExeciseTroopIdAirportId(troopId);
        execiseTroopDao.deleteExeciseTroopByAirportId(troopId);
        //删除此兵力相关的雷弹
        execiseEquipmentDao.deleteExeciseEquipmentByExeciseTroopIdId(troopId);
        //删除此兵力
        execiseTroopDao.deleteExeciseTroopById(troopId);
    }

    /**
     * 根据推演id查询maintype(去重复)
     * @param execId
     * @return
     */
    public List<Integer> queryMainTypeByExecId(Integer execId){
        return execiseTroopDao.queryMainTypeByExecId(execId);
    }

    /**
     * 兵力导入
     * @param execId
     */
    public void troopImport(Integer execId,Integer currentExecId){
        List<ExeciseTroopDto> execiseTroopDtoList = execiseTroopDao.queryByExecId(execId);
        List<ExeciseEquipmentDto> execiseEquipmentDtoList = execiseEquipmentDao.findExeciseEquipmentByExecId(execId);
        Map<Integer,List<ExeciseTroopDto>> airportMap = new HashMap<>();
        Map<Integer,List<ExeciseEquipmentDto>> equipmentMap = new HashMap<>();
        for(ExeciseTroopDto etDto : execiseTroopDtoList){
            if(etDto.getBelongAirport() != null){
                if(airportMap.containsKey(etDto.getBelongAirport())){
                    airportMap.get(etDto.getBelongAirport()).add(etDto);
                }else{
                    List<ExeciseTroopDto> tmp = new ArrayList<>();
                    tmp.add(etDto);
                    airportMap.put(etDto.getBelongAirport(),tmp);
                }
            }
        }
        for(ExeciseEquipmentDto eeDto : execiseEquipmentDtoList){
            if(equipmentMap.containsKey(eeDto.getExeciseTroopId())){
                equipmentMap.get(eeDto.getExeciseTroopId()).add(eeDto);
            }else{
                List<ExeciseEquipmentDto> tmp = new ArrayList<>();
                tmp.add(eeDto);
                equipmentMap.put(eeDto.getExeciseTroopId(),tmp);
            }
        }

        List<ExeciseEquipment> eelist = new ArrayList<>();
        for(ExeciseTroopDto etDto : execiseTroopDtoList){
            if(etDto.getBelongAirport() != null){
                continue;
            }
            ExeciseTroop et = new ExeciseTroop();
            et.setIconId(etDto.getIconId());
            et.setIconName(etDto.getIconName());
            et.setIconData(etDto.getIconData());
            et.setSpeed(null);
            et.setMaxSpeed(etDto.getMaxSpeed());
            et.setSpeedUnit(etDto.getSpeedUnit());
            et.setExecId(currentExecId);
            et.setColorArray(etDto.getColorArray());
            et.setBaseInfoId(etDto.getBaseInfoId());
            et.setMainType(etDto.getMainType());
            et.setBelongAirport(null);
            execiseTroopDao.addExeciseTroop(et);
            if(airportMap.get(etDto.getId()) != null){
                for(ExeciseTroopDto al : airportMap.get(etDto.getId())){
                    ExeciseTroop ariplane = new ExeciseTroop();
                    ariplane.setIconId(al.getIconId());
                    ariplane.setIconName(al.getIconName());
                    ariplane.setIconData(al.getIconData());
                    ariplane.setSpeed(null);
                    ariplane.setMaxSpeed(al.getMaxSpeed());
                    ariplane.setSpeedUnit("km");
                    ariplane.setExecId(currentExecId);
                    ariplane.setColorArray(al.getColorArray());
                    ariplane.setBaseInfoId(al.getBaseInfoId());
                    ariplane.setMainType(al.getMainType());
                    ariplane.setBelongAirport(et.getId());
                    execiseTroopDao.addExeciseTroop(ariplane);
                    if(equipmentMap.get(al.getId()) != null){
                        for(ExeciseEquipmentDto ee : equipmentMap.get(al.getId())){
                            ExeciseEquipment ammu = new ExeciseEquipment();
                            ammu.setMainType(ee.getMainType());
                            ammu.setEquipmentCount(ee.getEquipmentCount());
                            ammu.setEquipmentId(ee.getEquipment());
                            ammu.setEquipmentName(ee.getEquipmentName());
                            ammu.setExeciseTroopId(ariplane.getId());
                            eelist.add(ammu);
                        }
                    }
                }
            }
            if(equipmentMap.get(etDto.getId()) != null){
                for(ExeciseEquipmentDto ee : equipmentMap.get(etDto.getId())){
                    ExeciseEquipment ammu = new ExeciseEquipment();
                    ammu.setMainType(ee.getMainType());
                    ammu.setEquipmentCount(ee.getEquipmentCount());
                    ammu.setEquipmentId(ee.getEquipment());
                    ammu.setEquipmentName(ee.getEquipmentName());
                    ammu.setExeciseTroopId(et.getId());
                    eelist.add(ammu);
                }
            }
        }
        if(eelist != null && eelist.size()>0){
            this.execiseEquipmentDao.batchAdd(eelist);
        }
    }
}

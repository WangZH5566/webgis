package com.ydh.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础资料常量类
 */
public class BaseInfoConstant {
    public static final Map<String,String> selectSQL = new HashMap<String,String>();

    static {
        selectSQL.put("0","select bi.id as id,bi.info_name as infoName,bi.info_code as infoCode,bi.belong_unit as belongUnit,bi.max_speed as maxSpeed,bi.endurance as endurance,bi.max_displacement as maxDisplacement,bi.standard_displacement as standardDisplacement,bi.development_unit as developmentUnit,bi.service_date as serviceDate,bi.repair_situation as repairSituation,bi.image_url as imageUrl,bi.main_weapons as mainWeapons,bi.type_id as typeId,bi.main_type as mainType from exec_basic_info bi ");
        selectSQL.put("1","select bi.id as id,bi.info_code as infoCode,bi.belong_unit as belongUnit,bi.max_speed as maxSpeed,bi.fight_radius as fightRadius,bi.max_take_off_weight as maxTakeOffWeight,bi.development_unit as developmentUnit,bi.service_date as serviceDate,bi.repair_situation as repairSituation,bi.image_url as imageUrl,bi.main_weapons as mainWeapons,bi.type_id as typeId,bi.main_type as mainType from exec_basic_info bi ");
        selectSQL.put("2","select bi.id as id,bi.info_name as infoName,bi.address as address,bi.longitude_and_latitude as longitudeAndLatitude,bi.type_id as typeId,bi.main_type as mainType from exec_basic_info bi ");
        selectSQL.put("3","select bi.id as id,bi.info_name as infoName,bi.address as address,bi.longitude_and_latitude as longitudeAndLatitude,bi.type_id as typeId,bi.main_type as mainType from exec_basic_info bi ");
        selectSQL.put("4","select bi.id as id,bi.info_name as infoName,bi.address as address,bi.longitude_and_latitude as longitudeAndLatitude,bi.type_id as typeId,bi.main_type as mainType from exec_basic_info bi ");
        selectSQL.put("5","select bi.id as id,bi.info_code as infoCode,bi.development_unit as developmentUnit,bi.service_date as serviceDate,bi.technology_situation as technologySituation,bi.switch_time as switchTime,bi.load_time as loadTime,bi.performance as performance,bi.type_id as typeId,bi.main_type as mainType from exec_basic_info bi ");
        selectSQL.put("6","select bi.id as id,bi.info_name as infoName,bi.address as address,bi.longitude_and_latitude as longitudeAndLatitude,bi.type_id as typeId,bi.main_type as mainType from exec_basic_info bi ");
        selectSQL.put("7","select bi.id as id,bi.info_code as infoCode,bi.major_id as majorId,bim.major_name as majorName,bi.count as count,bi.type_id as typeId,bi.main_type as mainType from exec_basic_info bi inner join exec_basic_info_major bim on bim.id=bi.major_id ");
    }
}

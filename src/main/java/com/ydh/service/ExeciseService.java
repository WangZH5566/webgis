package com.ydh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ydh.dao.*;
import com.ydh.dto.*;
import com.ydh.model.*;
import com.ydh.util.Constant;
import com.ydh.util.EncodeUtils;
import com.ydh.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description:推演service
 * @author: xxx.
 * @createDate: 2016/9/5.
 */

@Service
@Transactional
public class ExeciseService {

    @Autowired
    private ExeciseDao execiseDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private ExeciseOrderDao execiseOrderDao;
    @Autowired
    private ExeciseStepDao execiseStepDao;
    @Autowired
    private ExeciseFightTimeDao execiseFightTimeDao;

    /**
     * 查询推演总数量
     * @param searchDto
     * @return
     */
    public Integer queryExecCount(ExeciseDto searchDto) {
        return execiseDao.queryExecCount(searchDto);
    }

    /**
     * 分页查询推演
     * @param searchDto
     * @return
     */
    public List<ExeciseDto> queryExecPage(ExeciseDto searchDto) {
        return execiseDao.queryExecPage(searchDto);
    }

    /**
     * 根据id,查询推演
     * @param id 推演id
     * @return
     */
    public ExeciseDto queryById(Integer id) {
        return execiseDao.queryById(id);
    }

    /**
     * 批量删除推演
     * @param ids
     */
    public void deleteExecise(String ids) {
        if (ids != null && ids.length() > 0) {
            String[] tmp = ids.split(",");
            Integer[] idArr = new Integer[tmp.length];
            for (int i = 0; i < tmp.length; i++) {
                idArr[i] = Integer.valueOf(tmp[i]);
            }
            execiseDao.deleteExeciseUserByArr(idArr);
            execiseDao.deleteExeciseDeByArr(idArr);
            execiseDao.deleteExeciseUnitByArr(idArr);
            execiseDao.deleteExecise(idArr);
        }
    }

    /**
     * 根据推演id,查询该推演下的所有单位
     * @param id 推演id
     * @return
     */
    public List<ExeciseUnit> queryExeciseUnit(Integer id) {
        return execiseDao.queryExeciseUnit(id);
    }

    /**
     * 根据推演id,查询该推演下的所有台位
     * @param id 推演id
     * @return
     */
    public List<ExeciseDepartment> queryExeciseDepartment(Integer id){
        return execiseDao.queryExeciseDepartment(id);
    }

    /**
     * 根据当前日期查询用户的最大序列号
     * @param curDate
     * @return
     */
    public Integer queryMaxSerialNo(String curDate) {
        return execiseDao.queryMaxSerialNo(curDate);
    }

    /**
     * 打印角色清单:根据推演id，查询推演人员(包括导演)
     * @param searchDto
     * @return
     */
    public List<ExeciseUserDto> queryExecUserPrint(ExeciseUserDto searchDto){
        return execiseDao.queryExecUserPrint(searchDto);
    }

    /**
     * 步骤一新增
     * @param execise      推演实体
     * @param execiseUser  推演用户(导演)实体
     * @param ids          单位id
     * @param names        单位名称
     * @param pids         单位pid
     * @param levels       级别
     * @return
     * @throws Exception
     */
    public String addStepOne(Execise execise, ExeciseUser execiseUser, String ids, String names, String pids, String levels) throws Exception {
        //数据验证
        String msg = validateStepOne(execise,execiseUser,ids);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }
        //1.新增推演
        execise.setExecStatus(Constant.EXEC_STATUS_INIT);
        execise.setStepLength(1);
        execise.setIsPause(0);
        execise.setCreateTime(new Date());
        execiseDao.addExecise(execise);
        //2.新增推演单位
        String[] idArr = ids.split(",");
        String[] nameArr = names.split(",");
        String[] pidArr = pids.split(",");
        String[] levelArr = levels.split(",");
        //levelMap key:级别  value:该级别下的所有ExeciseUnit
        Map<String, List<ExeciseUnit>> levelMap = new LinkedHashMap<String, List<ExeciseUnit>>();
        //idMap:新旧id对照map  key:旧id  value:新id
        Map<String, String> idMap = new HashMap<String, String>();
        idMap.put("0", "0");
        for (int i = 0; i < idArr.length; i++) {
            ExeciseUnit execiseUnit = new ExeciseUnit();
            execiseUnit.setOldId(idArr[i]);
            execiseUnit.setOldPid(pidArr[i]);
            execiseUnit.setUnitName(nameArr[i]);
            execiseUnit.setExecId(execise.getId());
            if (levelMap.containsKey(levelArr[i])) {
                levelMap.get(levelArr[i]).add(execiseUnit);
            } else {
                List<ExeciseUnit> tmp = new ArrayList<ExeciseUnit>();
                tmp.add(execiseUnit);
                levelMap.put(levelArr[i], tmp);
            }
            idMap.put(idArr[i], idArr[i]);
        }
        List<ExeciseUnit> execiseUnitList = new ArrayList<ExeciseUnit>();
        for (Map.Entry<String, List<ExeciseUnit>> entry : levelMap.entrySet()) {
            for (ExeciseUnit eu : entry.getValue()) {
                //新增推演单位
                eu.setPid(Integer.valueOf(idMap.get(eu.getOldPid())));
                execiseDao.addExeciseUnit(eu);
                execiseUnitList.add(eu);
                idMap.put(eu.getOldId(), eu.getId().toString());
            }
        }
        //3.新增导演
        execiseUser.setExecId(execise.getId());
        execiseUser.setIsCrossUnit(1);
        execiseDao.addExeciseUser(execiseUser);

        //3.新增推演台位
        /*String[] deTmpArr = deIds.split(",");
        Integer[] deArr = new Integer[deTmpArr.length];
        List<ExeciseDepartment> deList = new ArrayList<ExeciseDepartment>();
        if(deTmpArr != null && deTmpArr.length > 0){
            for(int i=0;i<deTmpArr.length;i++){
                deArr[i] = Integer.valueOf(deTmpArr[i]);
            }
            List<DepartmentDto> deDtos = departmentDao.queryByIdArr(deArr);
            if(deDtos != null && deDtos.size() > 0){
                for(DepartmentDto deDto : deDtos){
                    ExeciseDepartment de = new ExeciseDepartment();
                    de.setDepartmentName(deDto.getDepartmentName());
                    de.setDepartmentCode(deDto.getDepartmentCode());
                    de.setIsCrossUnit(deDto.getIsCrossUnit());
                    de.setExecId(execise.getId());
                    de.setDepartmentId(deDto.getId());
                    execiseDao.addExeciseDepartment(de);
                    deList.add(de);
                }
            }
        }
        //4.新增推演人员
        //导演
        execiseUser.setExecId(execise.getId());
        execiseUser.setIsCrossUnit(1);
        execiseDao.addExeciseUser(execiseUser);
        //普通人员
        //获取当前用户最大序列号
        Integer maxSerialNo = execiseDao.queryMaxSerialNo(execiseUser.getCurDate());
        if (maxSerialNo == null) {
            maxSerialNo = execiseUser.getSerialNo();
        }
        if (execiseUnitList.size() > 0 && deList.size() > 0) {
            for (ExeciseUnit eu : execiseUnitList) {
                maxSerialNo++;
                String serialNo = maxSerialNo.toString();
                if (serialNo.length() < 3) {
                    serialNo = String.format("%03d", maxSerialNo);
                }
                for (ExeciseDepartment de : deList) {
                    //新增推演人员
                    StringBuffer sb = new StringBuffer(de.getDepartmentCode()).append(execiseUser.getCurDate()).append(serialNo);
                    ExeciseUser execUser = new ExeciseUser();
                    execUser.setLoginName(sb.toString());
                    execUser.setUserName(sb.toString());
                    execUser.setOldPassword(String.valueOf(RandomUtils.generateRandomInt(100000, 999999)));
                    execUser.setNewPassword(EncodeUtils.MD5Upper(execUser.getOldPassword()));
                    execUser.setUnitId(eu.getId());
                    execUser.setDepartmentId(de.getId());
                    execUser.setIsDirector(0);
                    execUser.setCurDate(execiseUser.getCurDate());
                    execUser.setSerialNo(maxSerialNo);
                    execUser.setExecId(execise.getId());
                    execUser.setIsCrossUnit(de.getIsCrossUnit());
                    execiseDao.addExeciseUser(execUser);
                }
            }
        }*/
        return "success|" + execise.getId();
    }

    /**
     * 步骤一修改
     * @param execise      推演实体
     * @param execiseUser  推演用户(导演)实体
     * @param ids          单位id
     * @param names        单位名称
     * @param pids         单位pid
     * @param levels       级别
     * @return
     * @throws Exception
     */
    public String modifyStepOne(Execise execise, ExeciseUser execiseUser, String ids, String names, String pids, String levels) throws Exception {
        //数据验证
        String msg = validateStepOne(execise,execiseUser,ids);
        if (msg != null && !"".equals(msg)) {
            return msg;
        }
        //1.修改推演
        execise.setLastUpdateTime(new Date());
        execiseDao.modifyExecise(execise);
        //2.修改导演
        execiseDao.modifyExeciseDirector(execiseUser);
        //3.修改单位
        //有新增的单位,去新增它(id以new开头的就是新增的单位)
        //有删除的单位,要同时删除该单位下的台位和人员(id以old开头的就是原来的单位)
        //有修改的单位,要更新此单位的父节点id或名称
        //从数据库查询原有单位,与old开头的id做比较,找到被删除的单位和要修改的单位
        List<ExeciseUnit> unitList = execiseDao.queryExeciseUnit(execise.getId());
        List<Integer> deleteUnitIdList = new ArrayList<Integer>();
        Map<Integer,ExeciseUnit> unitMap = new HashMap<Integer, ExeciseUnit>();
        for(ExeciseUnit eu : unitList){
            deleteUnitIdList.add(eu.getId());
            unitMap.put(eu.getId(),eu);
        }
        //为新增的单位做数据准备
        String[] idArr = ids.split(",");
        String[] nameArr = names.split(",");
        String[] pidArr = pids.split(",");
        String[] levelArr = levels.split(",");
        //levelMap key:级别  value:该级别下的所有要新增的ExeciseUnit
        Map<String, List<ExeciseUnit>> levelMap = new LinkedHashMap<String, List<ExeciseUnit>>();
        //idMap:新旧id对照map  key:旧id  value:新id
        Map<String, String> idMap = new HashMap<String, String>();
        //需要修改的单位
        List<ExeciseUnit> modifyUnitList = new ArrayList<ExeciseUnit>();
        //非新增、非删除的单位id
        List<Integer> oldUnitIdList = new ArrayList<Integer>();
        for (int i = 0; i < idArr.length; i++) {
            String[] tmpArr = idArr[i].split("_");
            if ("old".equals(tmpArr[0])) {
                //旧节点
                Integer tmpId = Integer.valueOf(tmpArr[1]);
                //要删除的节点
                deleteUnitIdList.remove(tmpId);
                //非新增、非删除的单位id
                oldUnitIdList.add(tmpId);
                //要修改父节点id的节点
                boolean isModify = false;
                if("0".equals(pidArr[i]) && !unitMap.get(tmpId).getPid().equals(0)){
                    isModify = true;
                }else if(!"0".equals(pidArr[i])){
                    String[] tmpPidArr = pidArr[i].split("_");
                    if("new".equals(tmpPidArr[0])){
                        isModify = true;
                    }else if("old".equals(tmpPidArr[0]) && !tmpPidArr[1].equals(unitMap.get(tmpId).getPid().toString())){
                        isModify = true;
                    }
                }
                if(!nameArr[i].equals(unitMap.get(tmpId).getUnitName())){
                    isModify = true;
                }
                if(isModify){
                    ExeciseUnit execiseUnit = new ExeciseUnit();
                    execiseUnit.setId(tmpId);
                    execiseUnit.setOldPid(pidArr[i]);
                    execiseUnit.setUnitName(nameArr[i]);
                    modifyUnitList.add(execiseUnit);
                }
            } else if ("new".equals(tmpArr[0])) {
                //新增的节点
                ExeciseUnit execiseUnit = new ExeciseUnit();
                execiseUnit.setOldId(idArr[i]);
                execiseUnit.setOldPid(pidArr[i]);
                execiseUnit.setUnitName(nameArr[i]);
                execiseUnit.setExecId(execise.getId());
                if (levelMap.containsKey(levelArr[i])) {
                    levelMap.get(levelArr[i]).add(execiseUnit);
                } else {
                    List<ExeciseUnit> tmp = new ArrayList<ExeciseUnit>();
                    tmp.add(execiseUnit);
                    levelMap.put(levelArr[i], tmp);
                }
                idMap.put(idArr[i], idArr[i]);
            }
        }

        //3.1.删除推演用户
        //3.2.删除推演单位
        if(deleteUnitIdList.size() > 0){
            Map<String,Object> deleteUnitMap = new HashMap<String, Object>();
            deleteUnitMap.put("execId", execise.getId());
            deleteUnitMap.put("uIdList", deleteUnitIdList);
            execiseDao.deleteExeciseUserByMap(deleteUnitMap);
            execiseDao.deleteExeciseDepartmentByMap(deleteUnitMap);
            execiseDao.deleteExeciseUnitByMap(deleteUnitMap);
        }
        //3.3.新增推演单位
        List<ExeciseUnit> execiseUnitList = new ArrayList<ExeciseUnit>();
        for (Map.Entry<String, List<ExeciseUnit>> entry : levelMap.entrySet()) {
            for (ExeciseUnit eu : entry.getValue()) {
                eu.setPid(0);
                if(!"0".equals(eu.getOldPid())) {
                    String[] tmpPidArr = eu.getOldPid().split("_");
                    if("old".equals(tmpPidArr[0])){
                        eu.setPid(Integer.valueOf(tmpPidArr[1]));
                    }else if("new".equals(tmpPidArr[0])){
                        eu.setPid(Integer.valueOf(idMap.get(eu.getOldPid())));
                    }
                }
                execiseDao.addExeciseUnit(eu);
                execiseUnitList.add(eu);
                idMap.put(eu.getOldId(), eu.getId().toString());
            }
        }
        //3.4修改推演单位父节点id
        for(ExeciseUnit eu : modifyUnitList){
            eu.setPid(0);
            if(!"0".equals(eu.getOldPid())) {
                String[] tmpPidArr = eu.getOldPid().split("_");
                if("old".equals(tmpPidArr[0])){
                    eu.setPid(Integer.valueOf(tmpPidArr[1]));
                }else if("new".equals(tmpPidArr[0])){
                    eu.setPid(Integer.valueOf(idMap.get(eu.getOldPid())));
                }
            }
            execiseDao.modifyExeciseUnit(eu);
        }
        //4.台位新增与删除
       /* String[] deIdArr = deIds.split(",");
        List<Integer> deIdList = new ArrayList<Integer>();
        for (int j = 0; j < deIdArr.length; j++) {
            deIdList.add(Integer.valueOf(deIdArr[j]));
        }
        //更新后的全部台位
        List<ExeciseDepartment> newEdList = new ArrayList<ExeciseDepartment>();
        //新增的台位
        List<ExeciseDepartment> addEdList = new ArrayList<ExeciseDepartment>();
        //查询旧台位
        List<ExeciseDepartment> oldEdList = execiseDao.queryExeciseDepartment(execise.getId());
        //旧台位对应的departmentId
        List<Integer> oldDeIdList = new ArrayList<Integer>();
        Map<Integer,ExeciseDepartment> edMap = new HashMap<Integer,ExeciseDepartment>();
        for(ExeciseDepartment ed : oldEdList){
            oldDeIdList.add(ed.getDepartmentId());
            edMap.put(ed.getDepartmentId(),ed);
        }
        //新增的台位的departmentId
        List<Integer> addDeIdList = new ArrayList<Integer>();
        for (Integer o : deIdList) {
            if(!oldDeIdList.contains(o)){
                addDeIdList.add(o);
            }
        }
        //需要删除的台位Id
        List<Integer> deleteEdIdList = new ArrayList<Integer>();
        for(Integer o : oldDeIdList){
            if(!deIdList.contains(o)){
                deleteEdIdList.add(edMap.get(o).getId());
            }else{
                newEdList.add(edMap.get(o));
            }
        }
        //删除台位及台位下的用户
        if(deleteEdIdList.size() > 0){
            Map<String,Object> deleteDeMap = new HashMap<String, Object>();
            deleteDeMap.put("execId", execise.getId());
            deleteDeMap.put("edIdList", deleteEdIdList);
            execiseDao.deleteExeciseUserByEdId(deleteDeMap);
            execiseDao.deleteExeciseDepartmentByEdId(deleteDeMap);
        }
        //新增台位
        if(addDeIdList.size() > 0){
            List<DepartmentDto> deDtos = departmentDao.queryByIdList(addDeIdList);
            for(DepartmentDto deDto : deDtos){
                ExeciseDepartment de = new ExeciseDepartment();
                de.setDepartmentName(deDto.getDepartmentName());
                de.setDepartmentCode(deDto.getDepartmentCode());
                de.setIsCrossUnit(deDto.getIsCrossUnit());
                de.setExecId(execise.getId());
                de.setDepartmentId(deDto.getId());
                execiseDao.addExeciseDepartment(de);
                newEdList.add(de);
                addEdList.add(de);
            }
        }*/
        //5.新增推演人员
        //获取当前用户最大序列号
        /*Integer maxSerialNo = execiseDao.queryMaxSerialNo(execiseUser.getCurDate());
        if (maxSerialNo == null) {
            maxSerialNo = execiseUser.getSerialNo();
        }
        if (execiseUnitList.size() > 0 && newEdList.size() > 0) {
            //针对新增的单位,要插入所有台位的用户记录
            for (ExeciseUnit eu : execiseUnitList) {
                maxSerialNo++;
                String serialNo = maxSerialNo.toString();
                if (serialNo.length() < 3) {
                    serialNo = String.format("%03d", maxSerialNo);
                }
                for (ExeciseDepartment de : newEdList) {
                    //新增推演人员
                    StringBuffer sb = new StringBuffer(de.getDepartmentCode()).append(execiseUser.getCurDate()).append(serialNo);
                    ExeciseUser execUser = new ExeciseUser();
                    execUser.setLoginName(sb.toString());
                    execUser.setUserName(sb.toString());
                    execUser.setOldPassword(String.valueOf(RandomUtils.generateRandomInt(100000, 999999)));
                    execUser.setNewPassword(EncodeUtils.MD5Upper(execUser.getOldPassword()));
                    execUser.setUnitId(eu.getId());
                    execUser.setDepartmentId(de.getId());
                    execUser.setIsDirector(0);
                    execUser.setCurDate(execiseUser.getCurDate());
                    execUser.setSerialNo(maxSerialNo);
                    execUser.setExecId(execise.getId());
                    execUser.setIsCrossUnit(de.getIsCrossUnit());
                    execiseDao.addExeciseUser(execUser);
                }
            }
        }
        if (oldUnitIdList.size() > 0 && addEdList.size() > 0) {
            //针对非新增的单位,要插入新增台位的用户记录
            for (Integer uid : oldUnitIdList) {
                maxSerialNo++;
                String serialNo = maxSerialNo.toString();
                if (serialNo.length() < 3) {
                    serialNo = String.format("%03d", maxSerialNo);
                }
                for (ExeciseDepartment de : addEdList) {
                    //新增推演人员
                    StringBuffer sb = new StringBuffer(de.getDepartmentCode()).append(execiseUser.getCurDate()).append(serialNo);
                    ExeciseUser execUser = new ExeciseUser();
                    execUser.setLoginName(sb.toString());
                    execUser.setUserName(sb.toString());
                    execUser.setOldPassword(String.valueOf(RandomUtils.generateRandomInt(100000, 999999)));
                    execUser.setNewPassword(EncodeUtils.MD5Upper(execUser.getOldPassword()));
                    execUser.setUnitId(uid);
                    execUser.setDepartmentId(de.getId());
                    execUser.setIsDirector(0);
                    execUser.setCurDate(execiseUser.getCurDate());
                    execUser.setSerialNo(maxSerialNo);
                    execUser.setExecId(execise.getId());
                    execUser.setIsCrossUnit(de.getIsCrossUnit());
                    execiseDao.addExeciseUser(execUser);
                }
            }
        }*/
        return "success|" + execise.getId();
    }

    /**
     * 步骤一数据验证
     * @param execise      推演实体
     * @param execiseUser  推演用户(导演)实体
     * @param ids          单位id
     * @return
     * @throws Exception
     */
    private String validateStepOne(Execise execise,ExeciseUser execiseUser,String ids) throws Exception {
        if (execise == null) {
            return Constant.DATA_ABNORMAL;
        }
        if(execiseUser  == null){
            return Constant.DATA_ABNORMAL;
        }
        if(execise.getExeciseName()  == null){
            return "[推演名称]不能为空";
        }
        if(execiseUser.getLoginName()  == null){
            return "[导演登录名]不能为空";
        }
        if(execiseUser.getUserName()  == null){
            return "[导演用户名]不能为空";
        }
        if(execiseUser.getOldPassword()  == null){
            return "[导演密码]不能为空";
        }
        if(ids == null || ids.length() == 0){
            return "[单位]不能为空";
        }
        return "";
    }

    /**
     * 第二步骤保存
     * @param id              推演id
     * @param unitDepartment  单位与台位的关系(JSON格式的字符串)
     * @return
     * @throws Exception
     */
    public String saveStepTwo(Integer id,String unitDepartment) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(unitDepartment);
        //1.查询推演下的所有台位
        List<ExeciseDepartment> departmentList = execiseDao.queryExeciseDepartment(id);
        // key : 单位id  value:台位集合
        Map<Integer,List<ExeciseDepartment>> unitDepartmentMap = new HashMap<>();
        for(ExeciseDepartment ed:departmentList){
            if(unitDepartmentMap.containsKey(ed.getUnitId())){
                unitDepartmentMap.get(ed.getUnitId()).add(ed);
            }else{
                List<ExeciseDepartment> tmp = new ArrayList<>();
                tmp.add(ed);
                unitDepartmentMap.put(ed.getUnitId(),tmp);
            }
        }
        //2. 有新增的台位,去新增它
        //   有删除的台位,要同时删除该台位下的人员
        //   注:这里是不会删除单位的,但是会删除单位下的全部台位
        // key : 单位id  value:台位id集合
        Map<Integer,List<Integer>> addDepartmentMap = new HashMap<>();
        List<Integer> deleteDepartmentIdList = new ArrayList<>();
        List<Integer> tmpUnitIdList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            // 单位id
            Integer key = Integer.valueOf(entry.getKey());
            String value = (String)entry.getValue();
            String[] valueArr = value.split(",");
            if(unitDepartmentMap.get(key) != null){
                List<ExeciseDepartment> deList = unitDepartmentMap.get(key);
                for(int i=0;i<valueArr.length;i++){
                    Integer o = Integer.valueOf(valueArr[i]);
                    ExeciseDepartment tmpDe = null;
                    for(ExeciseDepartment de : deList){
                        if(de.getDepartmentId().equals(o)){
                            tmpDe = de;
                            break;
                        }
                    }
                    if(tmpDe != null){
                        // 移除后,剩下的就是需要删除的
                        deList.remove(tmpDe);
                    }else{
                        // 新增
                        if(addDepartmentMap.containsKey(key)){
                            addDepartmentMap.get(key).add(o);
                        }else{
                            List<Integer> tmp = new ArrayList<>();
                            tmp.add(o);
                            addDepartmentMap.put(key,tmp);
                        }
                    }
                }
                for(ExeciseDepartment de : deList){
                    deleteDepartmentIdList.add(de.getId());
                }
                tmpUnitIdList.add(key);
            }else{
                //新增
                for(int i=0;i<valueArr.length;i++){
                    Integer o = Integer.valueOf(valueArr[i]);
                    if(addDepartmentMap.containsKey(key)){
                        addDepartmentMap.get(key).add(o);
                    }else{
                        List<Integer> tmp = new ArrayList<>();
                        tmp.add(o);
                        addDepartmentMap.put(key,tmp);
                    }
                }
            }
        }
        // 删除单位下的全部台位
        for(Integer unitId:tmpUnitIdList){
            unitDepartmentMap.remove(unitId);
        }
        Map<String,Object> deleteUnitMap = new HashMap<String, Object>();
        List<Integer> deleteUnitIdList = new ArrayList<Integer>();
        for (Map.Entry<Integer,List<ExeciseDepartment>> entry : unitDepartmentMap.entrySet()) {
            deleteUnitIdList.add(entry.getKey());
        }
        if(deleteUnitIdList.size() > 0){
            deleteUnitMap.put("execId", id);
            deleteUnitMap.put("uIdList", deleteUnitIdList);
            execiseDao.deleteExeciseUserByMap(deleteUnitMap);
            execiseDao.deleteExeciseDepartmentByMap(deleteUnitMap);
        }
        // 根据id集合删除台位和台位下的人员
        if(deleteDepartmentIdList.size() > 0){
            execiseDao.deleteExeciseUserByEdIdList(deleteDepartmentIdList);
            execiseDao.deleteExeciseDepartmentByEdId(deleteDepartmentIdList);
        }
        // 查询台位数据源
        // key : 台位id
        Map<Integer,DepartmentDto> departmentDtoMap = new HashMap<>();
        List<DepartmentDto> deDtos = departmentDao.queryAllDepartment();
        for(DepartmentDto deDto : deDtos){
            departmentDtoMap.put(deDto.getId(),deDto);
        }
        // 新增台位和台位下的人员
        // key : 单位id
        Map<Integer,List<ExeciseDepartment>> addEdMap = new HashMap<>();
        for(Map.Entry<Integer,List<Integer>> entry : addDepartmentMap.entrySet()) {
            List<ExeciseDepartment> addEdList = new ArrayList<>();
            for(Integer i : entry.getValue()){
                ExeciseDepartment de = new ExeciseDepartment();
                DepartmentDto deDto = departmentDtoMap.get(i);
                de.setDepartmentName(deDto.getDepartmentName());
                de.setDepartmentCode(deDto.getDepartmentCode());
                de.setIsCrossUnit(deDto.getIsCrossUnit());
                de.setExecId(id);
                de.setDepartmentId(deDto.getId());
                de.setUnitId(entry.getKey());
                addEdList.add(de);
            }
            addEdMap.put(entry.getKey(),addEdList);
        }

        ExeciseDto execiseDto = execiseDao.queryById(id);
        Integer maxSerialNo = execiseDao.queryMaxSerialNo(execiseDto.getCurDate());
        if (maxSerialNo == null) {
            maxSerialNo = Integer.valueOf(execiseDto.getSerialNo());
        }
        if (addEdMap.size() > 0) {
            for(Map.Entry<Integer,List<ExeciseDepartment>> entry : addEdMap.entrySet()) {
                maxSerialNo++;
                String serialNo = maxSerialNo.toString();
                if (serialNo.length() < 3) {
                    serialNo = String.format("%03d", maxSerialNo);
                }
                for (ExeciseDepartment de : entry.getValue()) {
                    //新增台位
                    execiseDao.addExeciseDepartment(de);
                    //新增推演人员
                    StringBuffer sb = new StringBuffer(de.getDepartmentCode()).append(execiseDto.getCurDate()).append(serialNo);
                    ExeciseUser execUser = new ExeciseUser();
                    execUser.setLoginName(sb.toString());
                    execUser.setUserName(sb.toString());
                    execUser.setOldPassword(String.valueOf(RandomUtils.generateRandomInt(100000, 999999)));
                    execUser.setNewPassword(EncodeUtils.MD5Upper(execUser.getOldPassword()));
                    execUser.setUnitId(de.getUnitId());
                    execUser.setDepartmentId(de.getId());
                    execUser.setIsDirector(0);
                    execUser.setCurDate(execiseDto.getCurDate());
                    execUser.setSerialNo(maxSerialNo);
                    execUser.setExecId(id);
                    execUser.setIsCrossUnit(de.getIsCrossUnit());
                    execiseDao.addExeciseUser(execUser);
                }
            }
        }
        return "success";
    }

    /**
     * 根据推演id，查询推演人员(导演除外)总数量
     * @param searchDto
     * @return
     */
    public Integer queryExecUserCount(ExeciseUserDto searchDto){
        return execiseDao.queryExecUserCount(searchDto);
    }

    /**
     * 根据推演id，查询推演人员(导演除外)
     * @param searchDto
     * @return
     */
    public List<ExeciseUserDto> queryExecUserPage(ExeciseUserDto searchDto){
        return execiseDao.queryExecUserPage(searchDto);
    }

    /**
     * 修改推演人员
     * @param execiseUserDto
     */
    public void modifyExeciseUser(ExeciseUserDto execiseUserDto){
        execiseDao.modifyExeciseUser(execiseUserDto);
    }

    /**
     * 修改海图
     * @param execiseDto
     */
    public void modifyExeciseSeaChart(ExeciseDto execiseDto) {
        execiseDao.modifyExeciseSeaChart(execiseDto);
    }


    /**
     * 修改推演状态(导演开始、结束推演用)
     * @param execise
     */
    public void modifyExeciseStatus(Execise execise) {
        execiseDao.modifyExeciseStatus(execise);
        if(execise.getBeginTime() != null){
            //推演开始时,更新所有指令的开始时间
            ExeciseOrder execiseOrder = new ExeciseOrder();
            execiseOrder.setBeginTime(execise.getBeginTime());
            execiseOrder.setExecId(execise.getId());
            execiseOrderDao.modifyBeginTime(execiseOrder);
            //推演开始时,插入一条步长记录
            ExeciseStep execiseStep = new ExeciseStep();
            execiseStep.setExecId(execise.getId());
            execiseStep.setStepLength(execise.getStepLength());
            execiseStep.setBeginTime(execise.getBeginTime());
            execiseStep.setFightBeginTime(execise.getFightTime());
            execiseStepDao.addExeciseStep(execiseStep);
        }

        if(execise.getBeginTime() == null && execise.getStepLength() != null){
            //修改步长时,更新上一条步长记录的结束时间,并插入一条步长记录
            //所有步长
            List<ExeciseStepDto> esDtos = execiseStepDao.findExeciseStepByExecId(execise.getId());
            if(esDtos != null && esDtos.size() > 0) {
                //最新步长
                ExeciseStepDto esDto = esDtos.get(esDtos.size() - 1);
                //最新步长下的作战时间跳跃
                List<ExeciseFightTimeDto> eftDtos = execiseFightTimeDao.findExeciseFightTimeByExecIdAndStepId(execise.getId(), esDto.getId());
                //步长没发生变化,不做修改
                if(!esDto.getStepLength().equals(execise.getStepLength())){
                    Date now = new Date();
                    //计算这条步长作战时间的结束时间
                    int sl = esDto.getStepLength();
                    //作战时间 = ft + (now - 最后一个步长的天文开始时间) * 步长
                    long ft = esDto.getFightBeginTime().getTime() + (now.getTime() - esDto.getBeginTime().getTime()) * sl;
                    if(eftDtos != null && eftDtos.size() > 0){
                        //作战时间加上跳跃的时间段
                        for(int i=0;i<eftDtos.size();i++){
                            ExeciseFightTimeDto eftDto = eftDtos.get(i);
                            ft += (eftDto.getFightEndTime().getTime() - eftDto.getFightBeginTime().getTime());
                        }
                    }
                    ExeciseStep tmp = new ExeciseStep();
                    tmp.setId(esDto.getId());
                    tmp.setEndTime(now);
                    tmp.setFightEndTime(new Date(ft));
                    execiseStepDao.modifyExeciseStep(tmp);
                    ExeciseStep execiseStep = new ExeciseStep();
                    execiseStep.setExecId(execise.getId());
                    execiseStep.setStepLength(execise.getStepLength());
                    execiseStep.setBeginTime(now);
                    execiseStep.setFightBeginTime(new Date(ft));
                    execiseStepDao.addExeciseStep(execiseStep);
                }
            }
        }
    }

    /**
     * 修改推演暂停状态(导演用)
     * @param execise
     */
    public void modifyExecisePause(Execise execise) {
        execiseDao.modifyExecisePause(execise);
        if(execise.getIsPause().equals(0)){
            //重新开始,插入一条步长记录
            List<ExeciseStepDto> dtos = execiseStepDao.findExeciseStepByExecId(execise.getId());
            ExeciseStepDto dto = dtos.get(dtos.size() - 1);
            ExeciseStep execiseStep = new ExeciseStep();
            execiseStep.setExecId(execise.getId());
            execiseStep.setStepLength(execise.getStepLength());
            execiseStep.setBeginTime(new Date());
            execiseStep.setFightBeginTime(dto.getFightEndTime());
            execiseStepDao.addExeciseStep(execiseStep);
        }else if(execise.getIsPause().equals(1)){
            //暂停,将最新的步长记录的结束时间补上
            //所有步长
            List<ExeciseStepDto> esDtos = execiseStepDao.findExeciseStepByExecId(execise.getId());
            if(esDtos != null && esDtos.size() > 0) {
                //最新步长
                ExeciseStepDto esDto = esDtos.get(esDtos.size() - 1);
                //最新步长下的作战时间跳跃
                List<ExeciseFightTimeDto> eftDtos = execiseFightTimeDao.findExeciseFightTimeByExecIdAndStepId(execise.getId(), esDto.getId());
                //步长没发生变化,不做修改
                if(!esDto.getStepLength().equals(execise.getStepLength())){
                    Date now = new Date();
                    //计算这条步长作战时间的结束时间
                    int sl = esDto.getStepLength();
                    //作战时间 = ft + (now - 最后一个步长的天文开始时间) * 步长
                    long ft = esDto.getFightBeginTime().getTime() + (now.getTime() - esDto.getBeginTime().getTime()) * sl;
                    if(eftDtos != null && eftDtos.size() > 0){
                        //作战时间加上跳跃的时间段
                        for(int i=0;i<eftDtos.size();i++){
                            ExeciseFightTimeDto eftDto = eftDtos.get(i);
                            ft += (eftDto.getFightEndTime().getTime() - eftDto.getFightBeginTime().getTime());
                        }
                    }
                    ExeciseStep tmp = new ExeciseStep();
                    tmp.setId(esDto.getId());
                    tmp.setEndTime(now);
                    tmp.setFightEndTime(new Date(ft));
                    execiseStepDao.modifyExeciseStep(tmp);
                }
            }
        }
    }


    public ExeciseDto findByLoginUser(Integer userid) {
        List<ExeciseDto> execises = execiseDao.queryByLoginUser(userid);
        if (execises.size() > 0) {
            return execises.get(0);
        }
        return null;
    }

    /**
     * 根据推演id,查询该推演下所有推演步长
     * @param execId
     * @return
     */
    public List<ExeciseStepDto> findExeciseStepByExecId(Integer execId){
        return execiseStepDao.findExeciseStepByExecId(execId);
    }

    /**
     * 查看当前用户可以发送文电的用户:
     * @param searchDto
     * @return
     */
    public List<ExeciseUserDto> queryExecUserForSendTelegram(ExeciseUserDto searchDto){
        return execiseDao.queryExecUserForSendTelegram(searchDto);
    }

    /**
     * 查询除当前推演外的所有推演
     * @param id
     * @return
     */
    public List<ExeciseDto> queryExceptCurrent(Integer id){
        return execiseDao.queryExceptCurrent(id);
    }
}

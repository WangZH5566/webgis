package com.ydh.util;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by yqb on 2016/2/3 0003.
 */
public class Constant {
    public static final String LOGIN_USER = "login_user";
	public static final String CAPTCHA = "captcha";
	public static final String EXEC_ID = "exec_id";

    public static final String OPERATE_FAILED = "操作失败,请联系管理员";
    public static final String DATA_ABNORMAL = "数据异常,请联系管理员";


    //推演状态
    public static Integer EXEC_STATUS_INIT=0;
    public static Integer EXEC_STATUS__START=5;
    public static Integer EXEC_STATUS__END=10;
    private static Map<Integer,String> execStatusTextMap=new HashMap<Integer,String>();
    static {
        execStatusTextMap.put(EXEC_STATUS_INIT,"初始化");
        execStatusTextMap.put(EXEC_STATUS__START,"已开始");
        execStatusTextMap.put(EXEC_STATUS__END,"已结束");
    }

    public static String getExecStatusText(Integer status){
        status=status==null?0:status;
        return execStatusTextMap.get(status);
    }

    //文电/文电模板状态
    public static Integer TELEGRAM_STATUS_INIT = 0;                 //初始化
    public static Integer TELEGRAM_STATUS_SUCCESS = 1;              //转换成功
    public static Integer TELEGRAM_STATUS_FAILD = 2;                //转换失败
}

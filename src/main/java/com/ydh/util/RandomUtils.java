package com.ydh.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: xxx.
 * @createDate: 2016/11/21.
 */
public class RandomUtils {

    /**
     * 生成min-max范围内的int类型随机数
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static int generateRandomInt(int min,int max){
        if(min > max){
            return 0;
        }
        int num = (int)(min + Math.random() * (max - min + 1));
        return num;
    }

    public static void main(String[] args) {
        /*int[] nums = new int[]{3,2,4};
        int target = 6;
        int[] rs = RandomUtils.twoSum(nums,target);
        System.out.println(rs.length);*/
        String n1 = "214325432532312132142097984376939050357097";
        String n2 = "987324032489852048324982483948394900000000";
        long b1 = System.currentTimeMillis();
        BigInteger rs1 = new BigInteger(n1).subtract(new BigInteger(n2));
        long e1 = System.currentTimeMillis();
        System.out.println(e1 - b1);
        System.out.println(rs1.toString());

        //214325531264715381127302816875187445196587
        long b2 = System.currentTimeMillis();
        String rs2 = RandomUtils.bigIntSubtract(n1, n2);
        long e2 = System.currentTimeMillis();
        System.out.println(e2 - b2);
        System.out.println(rs2);
        System.out.println(rs2.equals(rs1.toString()));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(target - nums[i])){
                return new int[]{map.get(target - nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return null;
    }

    /**
     * 大数相加
     * @param n1  正整数
     * @param n2  正整数
     * @return
     */
    public static String bigIntAdd(String n1,String n2){
        //1.检查输入
        if(!n1.matches("\\d+") || !n2.matches("\\d+")){
            return null;
        }
        //2.翻转两个字符串，并转换成数组
        char[] a = new StringBuffer(n1).reverse().toString().toCharArray();
        char[] b = new StringBuffer(n2).reverse().toString().toCharArray();
        int lenA = a.length;
        int lenB = b.length;
        //计算两个长字符串中的较长字符串的长度
        int len = lenA > lenB ? lenA : lenB;
        //结果集
        int[] result = new int[len + 1];
        //进位
        int carry = 0;
        for(int i = 0;i < len;i++){
            int av = i < lenA ? (a[i] - '0') : 0;
            int bv = i < lenB ? (b[i] - '0') : 0;
            int sum = carry + av + bv;
            carry = sum / 10;
            result[i] = sum % 10;
        }
        if(carry != 0){
            result[len] = carry;
        }
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < result.length;i++){
            sb.append(result[i]);
        }
        if(carry == 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.reverse().toString();
    }

    /**
     * 大数相减
     * @param n1  正整数  被减数
     * @param n2  正整数  减数
     * @return
     */
    public static String bigIntSubtract(String n1,String n2){
        //1.检查输入
        if(!n1.matches("\\d+") || !n2.matches("\\d+")){
            return null;
        }
        //2.翻转两个字符串，并转换成数组
        char[] a = new StringBuffer(n1).reverse().toString().toCharArray();
        char[] b = new StringBuffer(n2).reverse().toString().toCharArray();
        int lenA = a.length;
        int lenB = b.length;
        //计算两个长字符串中的较长字符串的长度
        int len = lenA > lenB ? lenA : lenB;
        //结果集
        int[] result = new int[len];
        //符号位
        char sign = '+';
        //判断最终结果的正负
        if(lenA < lenB){
            sign = '-';
        }else if(lenA == lenB){
            int i = lenA - 1;
            while(i > 0 && a[i] == b[i]){
                i--;
            }
            if(a[i] < b[i]){
                sign = '-';
            }
        }
        //3.计算结果集，如果最终结果为正，那么就a-b否则的话就b-a
        //借位
        int borrow = 0;
        if(sign == '+'){
            for(int i = 0;i < len;i++){
                int av = i < lenA ? (a[i] - '0') : 0;
                int bv = i < lenB ? (b[i] - '0') : 0;
                int difference = av - bv - borrow;
                if(difference < 0){
                    result[i] = 10 + difference;
                    borrow = 1;
                }else{
                    result[i] = difference;
                    borrow = 0;
                }
            }
        }else if(sign == '-'){
            for(int i = 0;i < len;i++) {
                int av = i < lenA ? (a[i] - '0') : 0;
                int bv = i < lenB ? (b[i] - '0') : 0;
                int difference = bv - av - borrow;
                if (difference < 0) {
                    result[i] = 10 + difference;
                    borrow = 1;
                } else {
                    result[i] = difference;
                    borrow = 0;
                }
            }
        }
        //4.如果最终结果为负值，就将负号放在最前面，正号则不需要
        StringBuffer sb = new StringBuffer();
        if (sign == '-') {
            sb.append('-');
        }
        //判断是否有前置0
        boolean flag = true;
        for(int i = result.length - 1;i >= 0;i--){
            if(flag && result[i] == 0){
                continue;
            }else{
                flag = false;
            }
            sb.append(result[i]);
        }
        //如果被减数和减数值相等,则最终结果是0
        if(sb.toString().equals("")){
            sb.append("0");
        }
        return sb.toString();
    }

    /**
     * 大数相乘
     * @param n1
     * @param n2
     * @return
     */
    public static String bigIntMultiply(String n1,String n2){
        //1.获取符号位
        //符号位
        char sign = '+';
        char signA = n1.charAt(0);
        char signB = n2.charAt(0);
        if(signA == '+' || signA == '-'){
            sign = signA;
            n1 = n1.substring(1);
        }
        if(signB == '+' || signB == '-'){
            if(signA == signB){
                sign = '+';
            }else{
                sign = '-';
            }
            n2 = n2.substring(1);
        }
        //2.检查输入
        if(!n1.matches("\\d+") || !n2.matches("\\d+")){
            return null;
        }


        return null;
    }
}

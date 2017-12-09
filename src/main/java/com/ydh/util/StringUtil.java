package com.ydh.util;

import org.apache.commons.lang.StringUtils;

import java.io.Reader;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtil
{
    public static List<String> stringToList(String val)
    {
        if (val != null) {
            String[] list = val.split("[ ]*,[ ]*");
            return Arrays.asList(list);
        }
        return Collections.EMPTY_LIST;
    }

    public static String nvl(Object obj)
    {
        if (obj == null) {
            return "";
        }
        if ((obj instanceof String)) {
            return (String)obj;
        }
        return obj.toString();
    }

    public static boolean isNotBlank(String str)
    {
        return !nvl(str).trim().equals("");
    }

    public static String getCurrentFormatDate()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(date);
        StringBuffer buf = new StringBuffer("");
        buf.append(currentDate.substring(0, 4)).append(currentDate.substring(5, 7)).append(currentDate.substring(8, 10));

        return buf.toString();
    }

    public static boolean isBlank(String str)
    {
        return !isNotBlank(str);
    }

    public static boolean toBooleanValue(String str) {
        if ("1".equals(str)) {
            return true;
        }
        if ("yes".equals(str)) {
            return true;
        }

        return "true".equals(str);
    }

    public static boolean isChanged(String sr, String ds)
    {
        sr = sr == null ? "" : sr;
        ds = ds == null ? "" : ds;
        return !sr.equals(ds);
    }

    public static boolean isChanged(Date src, Date dst, String format)
    {
        if ((format == null) || ("".equals(format.trim()))) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat(format);
        String sr = src == null ? "" : dataFormat.format(src);
        String ds = dst == null ? "" : dataFormat.format(dst);

        return !sr.equals(ds);
    }

    public static boolean isChanged(Integer src, Integer dst)
    {
        String sr = src == null ? "" : src.toString();
        String ds = dst == null ? "" : dst.toString();

        return !sr.equals(ds);
    }

    public static boolean isValidDate(String dateStr, String format) {
        if ((format == null) || ("".equals(format.trim()))) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat(format);
        try {
            Date date = dataFormat.parse(dateStr);
            return dateStr.equals(dataFormat.format(date)); } catch (Exception e) {
        }
        return false;
    }

    public static String getInSQLStr(String joinType, String fieldName, String[] barCodeArr)
    {
        List<List<String>> list = new ArrayList<List<String>>();
        StringBuffer sb = new StringBuffer("  " + joinType + " (");
        int i = 0;
        List<String> list1 = new ArrayList<String>();
        for (String barcode : barCodeArr) {
            i++;
            list1.add(barcode);
            if (i % 100 == 0) {
                list.add(list1);
                list1 = new ArrayList();
            }
        }
        int some = barCodeArr.length % 100;
        if (some > 0) {
            list1 = new ArrayList();
            for (int k = barCodeArr.length - some; k < barCodeArr.length; k++) {
                list1.add(barCodeArr[k]);
            }
            list.add(list1);
        }
        int j = 0;
        for (List<String> list2 : list) {
            j++;
            if (j == 1) {
                sb.append(fieldName + " in(");
                for (String id : list2) {
                    sb.append("'" + id + "',");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(")");
            } else {
                sb.append(" or " + fieldName + " in(");
                for (String id : list2) {
                    sb.append("'" + id + "',");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(")");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public static String clobToString(Clob clob)
    {
        if (clob == null)
            return null;
        StringBuffer sb = new StringBuffer();
        Reader clobStream = null;
        try {
            clobStream = clob.getCharacterStream();
            char[] b = new char[60000];
            int i = 0;
            while ((i = clobStream.read(b)) != -1)
                sb.append(b, 0, i);
        }
        catch (Exception e) {
            sb = null;
        } finally {
            try {
                if (clobStream != null)
                    clobStream.close();
            }
            catch (Exception e) {
            }
        }
        if (sb == null) {
            return null;
        }
        return sb.toString();
    }

    public static String empty2Blank(String str)
    {
        if (StringUtils.isEmpty(str)) {
            return " ";
        }
        return str.trim();
    }
}
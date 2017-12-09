package com.ydh.util;


import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @description:Aspose.Words for java 将word转换为Html,PDF工具类
 * @author: xxx.
 * @createDate: 2016/11/5.
 */
public class AsposeWordsUtils {
    private static final Logger log = LoggerFactory.getLogger(AsposeWordsUtils.class);
    //证书
    private static InputStream license;

    /**
     * 获取license
     * @return
     */
    public static boolean getLicense() {
        boolean result = false;
        try {
            //Windows:
            license = AsposeWordsUtils.class.getClassLoader().getResourceAsStream("\\config\\license.xml");    // license路径
            //Linux:
            //license = AsposeWordsUtils.class.getClassLoader().getResourceAsStream("/config/license.xml");    // license路径
            License aposeLic = new License();
            aposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertToHtml(String path){
        // 验证License
        if (!getLicense()) {
            return "获取License失败";
        }
        String msg = "";
        try {
            File file = new File(path);// 原始word路径
            InputStream word = new FileInputStream(file);
            Document doc = new Document(word);
            String hpath = path.substring(0,path.lastIndexOf("."));
            hpath += ".html";
            File outFile = new File(hpath);// 输出路径
            FileOutputStream fileOS = new FileOutputStream(outFile);
            doc.save(fileOS, SaveFormat.HTML);
            msg = "success";
        }catch (Exception e){
            msg = e.getMessage();
            log.error(e.getMessage(),e);
        }
        return msg;
    }

    public static void main(String[] args) {
        // 验证License
        if (!getLicense()) {
            return;
        }
        try {
            /*HashMap<String, Object> datas = new HashMap<String, Object>();
            datas.put("property_a", "helloworld_a");
            datas.put("property_b", "helloworld_b");
            datas.put("property_c", "helloworld_c");
            datas.put("property_d", "helloworld_d");
            datas.put("property_e", "helloworld_e");
            datas.put("property_f", "helloworld_f");*/

            long old = System.currentTimeMillis();
            File file = new File("E:\\test2.docx");// 原始word路径
            InputStream word = new FileInputStream(file);
            Document doc = new Document(word);
            // 注:不要用替换,替换会扫描整个文档,效率低
            // 遍历要替换的内容
            /*for (Map.Entry<String, Object> entry : datas.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue()==null?"":String.valueOf(entry.getValue());
                // 对显示值得修改
                value = value.replace("\r\n", " ");
                // 要求替换的内容是完全匹配时的替换
                doc.getRange().replace("$" + key + "$", value, true, false);
            }*/
            File outFile = new File("D:\\test.pdf");// 输出路径
            FileOutputStream fileOS = new FileOutputStream(outFile);
            doc.save(fileOS, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + outFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

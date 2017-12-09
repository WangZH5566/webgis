package com.ydh.controller;

import com.ydh.dto.ExeciseDto;
import com.ydh.dto.SeaChartDto;
import com.ydh.model.SeaChart;
import com.ydh.service.GeoServerService;
import com.ydh.service.SeaChartService;
import com.ydh.util.EncodeUtils;
import com.ydh.util.GeoUtil;
import com.ydh.util.ZipUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/sc")
public class SeaChartController {

    @Autowired
    private GeoServerService geoServerService;

    @Autowired
    private SeaChartService seaChartService;

    @RequestMapping("/main")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("sc.main");
        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("sc.add");
        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView save(@RequestParam(value = "file") MultipartFile file
            , @RequestParam(value = "name") String name
            , @RequestParam(value = "leftlon") Double leftlon
            , @RequestParam(value = "leftlat") Double leftlat
            , @RequestParam(value = "rightlon") Double rightlon
            , @RequestParam(value = "rightlat") Double rightlat
            , HttpServletRequest request) throws IOException {
        ModelAndView mv = new ModelAndView("sc.main");
        String directory = System.getProperty("java.io.tmpdir");
        String fileName = file.getOriginalFilename();
        String base64FileName = EncodeUtils.base64Encode(fileName.getBytes());
        File destFile = new File(directory + File.separator + base64FileName);
        if (destFile.exists()) {
            destFile.delete();
        }
        FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
        SeaChart chart = geoServerService.publishWorldImage(destFile, fileName, leftlon, leftlat, rightlon, rightlat);
        chart.setSeaChartName(name);
        seaChartService.save(chart);
        return mv;
    }

    @RequestMapping("/save_step1")
    public ModelAndView save(@RequestParam(value = "file") MultipartFile file
            , @RequestParam(value = "name") String name
            , HttpServletRequest request) throws IOException {
        String directory = System.getProperty("java.io.tmpdir");
        String fileName = file.getOriginalFilename();
        String base64FileName = EncodeUtils.base64Encode(fileName.getBytes());
        File destZipFile = new File(directory + File.separator + base64FileName);
        if (destZipFile.exists()) {
            destZipFile.delete();
        }
        FileUtils.copyInputStreamToFile(file.getInputStream(), destZipFile);
        //SeaChart chart = geoServerService.publishWorldImage(destFile, fileName, leftlon, leftlat, rightlon, rightlat);
        //chart.setSeaChartName(name);
        //seaChartService.save(chart);
        String destDirectoryUuid = UUID.randomUUID().toString();
        File destDirectory = new File(directory + File.separator + destDirectoryUuid);
        ZipUtil.unzip(destZipFile, destDirectory);
        File[] files = destDirectory.listFiles();
        List<String> images = new ArrayList<String>();
        Boolean isShp = Boolean.FALSE;
        for (File fileIndestDirectory : files) {
            String extension = GeoUtil.getExtensionIfValidated(fileIndestDirectory);
            if (extension == null) {
                //fileIndestDirectory.delete();
                continue;
            }
            if ("shp".equals(extension)) {
                isShp = Boolean.TRUE;
                break;
            }
            String smallFileName = fileIndestDirectory.getParent() + File.separator + "small-" + fileIndestDirectory.getName();
            File smallFile = new File(smallFileName);
            Thumbnails.of(fileIndestDirectory).size(150, 150).toFile(smallFile);
            images.add(smallFile.getName());
        }
        if (isShp) {
            SeaChart chart = geoServerService.publishShp(destZipFile, fileName);
            chart.setSeaChartName(name);
            seaChartService.save(chart);
            return new ModelAndView("sc.main");
        }
        ModelAndView mv = new ModelAndView("sc.add.2");
        mv.addObject("subdir", destDirectoryUuid);
        mv.addObject("images", images);
        mv.addObject("name", name);
        return mv;
    }

    @RequestMapping("/save_step2")
    public ModelAndView save_step2(@RequestParam(value = "subdir") String subdir
            , @RequestParam(value = "name") String name
            , @RequestParam(value = "image") String[] image
            , @RequestParam(value = "leftlon") Double[] leftlon
            , @RequestParam(value = "leftlat") Double[] leftlat
            , @RequestParam(value = "rightlon") Double[] rightlon
            , @RequestParam(value = "rightlat") Double[] rightlat
    ) throws IOException, JDOMException {
        ModelAndView mv = new ModelAndView("sc.main");
        SeaChart chart = geoServerService.publishWorldImage(subdir, image, leftlon, leftlat, rightlon, rightlat);
        chart.setSeaChartName(name);
        seaChartService.save(chart);
        return mv;
    }

    @RequestMapping("/thumbnailator")
    public void thumbnailator(@RequestParam(value = "subdir") String subdir, @RequestParam(value = "image") String image, HttpServletResponse res) throws IOException {
        String directory = System.getProperty("java.io.tmpdir");
        File sourceFile = new File(directory + File.separator + subdir + File.separator + image);
        res.setContentType("image/jpeg");
        Thumbnails.of(sourceFile).size(150, 150).toOutputStream(res.getOutputStream());
    }


    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam(value = "ids") String ids) {
        String[] _ids = ids.split(",");
        for (String _id : _ids) {
            seaChartService.delete(Integer.valueOf(_id));
        }
        return "0";
    }

    @RequestMapping("/queryPage")
    public ModelAndView queryPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("sc.load");
        List<SeaChartDto> dtos = seaChartService.queryAll();
        mv.addObject("dtos", dtos);
        return mv;
    }
}

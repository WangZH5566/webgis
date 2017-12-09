package com.ydh.service;

import com.ydh.exception.GeoServerException;
import com.ydh.model.SeaChart;
import com.ydh.util.GeoUtil;
import com.ydh.util.StringUtil;
import com.ydh.util.ZipUtil;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.HTTPUtils;
import it.geosolutions.geoserver.rest.decoder.RESTFeatureType;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.decoder.RESTStyleList;
import it.geosolutions.geoserver.rest.encoder.identifier.GSIdentifierInfoEncoder;
import org.apache.commons.httpclient.NameValuePair;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class GeoServerService {

    private static Logger logger = LoggerFactory.getLogger(GeoServerService.class);

    //底图工作区名字
    private static String BACKGROUND_MAP_WORKSPACE_NAME = "navy";

    private static String DEFAULT_SRS = "EPSG:4326";

    @Value("${geoserver.username}")
    private String username = "admin";

    @Value("${geoserver.password}")
    private String password = "geoserver";

    @Value("${geoserver.url}")
    private String url = "http://localhost:8080/geoserver";

    public String getUrl() {
        return url;
    }

    public void publishShp(String filename) {
        publishShp(new File(filename), null);
    }

    public void publishShp(String filename, String originalName) {
        publishShp(new File(filename), originalName);
    }

    public void publishShp(File file) {
        publishShp(file, null);
    }

    public SeaChart publishShp(File file, String originalName) {
        if (StringUtil.isBlank(originalName)) {
            originalName = file.getName();
        }
        String layerName = findLayerNameFromZipFile(file, originalName);
        String storeName = layerName;
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(url, username, password);
        try {
            GeoServerRESTReader reader = new GeoServerRESTReader(url, username, password);
            newWorkSpaceIfNotExists(reader, publisher, BACKGROUND_MAP_WORKSPACE_NAME);
            if (!publisher.publishShp(BACKGROUND_MAP_WORKSPACE_NAME, storeName, layerName, file, DEFAULT_SRS)) {
                throw new GeoServerException(String.format("Publishing %s file failed.", originalName));
            }
            RESTFeatureType featureType = reader.getFeatureType(reader.getLayer(BACKGROUND_MAP_WORKSPACE_NAME, layerName));
            StringBuilder bound = new StringBuilder();
            bound.append(featureType.getMinX()).append(", ").append(featureType.getMinY()).append(", ").append(featureType.getMaxX()).append(", ").append(featureType.getMaxY());
            //TODO 这些资料得保存到数据库里面，以便后续可以根据这些参数来访问geoserver的wms服务
            StringBuilder url = new StringBuilder();
            url.append(BACKGROUND_MAP_WORKSPACE_NAME).append("/").append("wms");
            StringBuilder layer = new StringBuilder();
            layer.append(BACKGROUND_MAP_WORKSPACE_NAME).append(":").append(layerName);

            SeaChart seaChart = new SeaChart();
            seaChart.setBound(bound.toString());
            seaChart.setUrl(url.toString());
            seaChart.setLayer(layer.toString());
            return seaChart;
        } catch (MalformedURLException e) {
            throw new GeoServerException(e);
        } catch (FileNotFoundException e) {
            throw new GeoServerException(e);
        }
    }

    public SeaChart publishWorldImage(File file, String originalName, Double leftlon, Double leftlat, Double rightlon, Double rightlat) throws IOException {
        if (StringUtil.isBlank(originalName)) {
            originalName = file.getName();
        }

        List<ZipItem> zipItems = makeup(file, leftlon, leftlat, rightlon, rightlat);
        Collections.sort(zipItems);
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(url, username, password);
        GeoServerRESTReader reader = new GeoServerRESTReader(url, username, password);
        newWorkSpaceIfNotExists(reader, publisher, BACKGROUND_MAP_WORKSPACE_NAME);
        SeaChart seaChart = null;
        try {
            for (int i = 0; i < zipItems.size(); i++) {
                ZipItem zipItem = zipItems.get(i);
                publisher.publishWorldImage(BACKGROUND_MAP_WORKSPACE_NAME, zipItem.getLayName(), zipItem.getZipFile());
                if (i != 0) {
                    continue;
                }
                RESTLayer layer = reader.getLayer(BACKGROUND_MAP_WORKSPACE_NAME, zipItem.getLayName());
                String url = layer.getResourceUrl();
                String response = HTTPUtils.get(url, username, password);
                SAXBuilder sb = new SAXBuilder();
                Document document = sb.build(new StringReader(response));//sb.build("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + response);
                Element root = document.getRootElement();
                Element box = root.getChild("nativeBoundingBox");
                StringBuilder boundsb = new StringBuilder();
                boundsb.append(box.getChildText("minx")).append(",");
                boundsb.append(box.getChildText("miny")).append(",");
                boundsb.append(box.getChildText("maxx")).append(",");
                boundsb.append(box.getChildText("maxy"));

                StringBuilder urlsb = new StringBuilder();
                urlsb.append(BACKGROUND_MAP_WORKSPACE_NAME).append("/").append("wms");
                StringBuilder layersb = new StringBuilder();
                layersb.append(BACKGROUND_MAP_WORKSPACE_NAME).append(":").append(zipItem.getLayName().replace("-0", ""));

                seaChart = new SeaChart();
                seaChart.setBound(boundsb.toString());
                seaChart.setLayerNum(zipItems.size());
                seaChart.setUrl(urlsb.toString());
                seaChart.setLayer(layersb.toString());
            }

            if (seaChart != null) {
                String resolution = "";
                for (int i = 0; i < zipItems.size(); i++) {
                    resolution += zipItems.get(i).getLontitudePrecise();
                    if (i < zipItems.size() - 1) {
                        resolution += ",";
                    }
                }
                seaChart.setResolution(resolution);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }

        return seaChart;
    }

    public SeaChart publishWorldImage(String directory, String[] smallfileNames, Double[] leftlon, Double[] leftlat, Double[] rightlon, Double[] rightlat) throws IOException, JDOMException {
        String tempDirectory = System.getProperty("java.io.tmpdir") + File.separator + directory;
        String destFileBase = UUID.randomUUID().toString();
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(url, username, password);
        GeoServerRESTReader reader = new GeoServerRESTReader(url, username, password);
        newWorkSpaceIfNotExists(reader, publisher, BACKGROUND_MAP_WORKSPACE_NAME);
        StringBuffer allBound = new StringBuffer();
        StringBuffer allLayerNames = new StringBuffer();
        StringBuffer allResolution = new StringBuffer();
        for (int i = 0; i < smallfileNames.length; i++) {
            //1. 产生上传的文件包
            String smallFileName = smallfileNames[i].replace("small-", "");
            File sourceImageFile = new File(tempDirectory + File.separator + smallFileName);

            File destPrjFile = new File(tempDirectory + File.separator + destFileBase + "-" + i + ".prj");
            newPrjFile(destPrjFile);

            File destGeoFile = null;
            String extension = getExtensionIfValidated(sourceImageFile);
            if (extension.equalsIgnoreCase("jpg")) {
                destGeoFile = new File(tempDirectory + File.separator + destFileBase + "-" + i + ".jpgw");
            }
            if (extension.equalsIgnoreCase("png")) {
                destGeoFile = new File(tempDirectory + File.separator + destFileBase + "-" + i + ".pngw");
            }
            GeoUtil.WorldFile worldFile = newGeoFile(sourceImageFile, destGeoFile, leftlon[i], leftlat[i], rightlon[i], rightlat[i]);

            File destImageFile = new File(tempDirectory + File.separator + destFileBase + "-" + i + "." + extension.toLowerCase());
            newImageFile(sourceImageFile, destImageFile);

            File destZipFile = new File(tempDirectory + File.separator + destFileBase + "-" + i + ".zip");
            ZipUtil.zip(new File[]{destImageFile, destPrjFile, destGeoFile}, destZipFile);

            //2. 发布zip包
            publisher.publishWorldImage(BACKGROUND_MAP_WORKSPACE_NAME, destFileBase + "-" + i, destZipFile);

            //3. 读取发布的zip包的经纬度
            RESTLayer layer = reader.getLayer(BACKGROUND_MAP_WORKSPACE_NAME, destFileBase + "-" + i);
            String url = layer.getResourceUrl();
            String response = HTTPUtils.get(url, username, password);
            SAXBuilder sb = new SAXBuilder();
            Document document = sb.build(new StringReader(response));//sb.build("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + response);
            Element root = document.getRootElement();
            Element box = root.getChild("nativeBoundingBox");
            StringBuilder boundsb = new StringBuilder();
            boundsb.append(box.getChildText("minx")).append(",");
            boundsb.append(box.getChildText("miny")).append(",");
            boundsb.append(box.getChildText("maxx")).append(",");
            boundsb.append(box.getChildText("maxy"));
            allBound.append(boundsb).append(";");
            allLayerNames.append(BACKGROUND_MAP_WORKSPACE_NAME).append(":").append(destFileBase + "-" + i).append(";");
            allResolution.append(worldFile.getA()).append(";");
        }
        String bound = allBound.substring(0, allBound.length() - 1);
        String layerName = allLayerNames.substring(0, allLayerNames.length() - 1);
        String resolution = allResolution.substring(0, allResolution.length() - 1);
        SeaChart seaChart = new SeaChart();
        seaChart.setBound(bound);
        seaChart.setLayerNum(smallfileNames.length);
        seaChart.setUrl(BACKGROUND_MAP_WORKSPACE_NAME + "/" + "wms");
        seaChart.setLayer(layerName);
        seaChart.setResolution(resolution);
        return seaChart;
    }


    private List<ZipItem> makeup(File file, Double leftlon, Double leftlat, Double rightlon, Double rightlat) throws IOException {
        String uuid = UUID.randomUUID().toString();
        File parent = file.getParentFile();
        File directory = new File(parent.getAbsoluteFile().toString() + File.separator + uuid);
        ZipUtil.unzip(file, directory);
        File[] images = directory.listFiles();
        String newFileNameHead = UUID.randomUUID().toString();
        List<ZipItem> list = new ArrayList<ZipItem>();
        for (int i = 0; i < images.length; i++) {
            File image = images[i];
            String extension = getExtensionIfValidated(image);
            if (extension == null) {
                continue;
            }
            File newPrjFile = new File(directory.getAbsoluteFile().toString() + File.separator + newFileNameHead + "-" + i + ".prj");
            newPrjFile(newPrjFile);
            File geoFile = null;
            if (extension.equalsIgnoreCase("jpg")) {
                geoFile = new File(directory.getAbsoluteFile().toString() + File.separator + newFileNameHead + "-" + i + ".jgw");
            } else {

            }
            GeoUtil.WorldFile worldFile = newGeoFile(image, geoFile, leftlon, leftlat, rightlon, rightlat);
            File newFile = new File(directory.getAbsoluteFile().toString() + File.separator + newFileNameHead + "-" + i + "." + extension.toLowerCase());
            newImageFile(image, newFile);

            File newZipFile = new File(directory.getAbsoluteFile().toString() + File.separator + newFileNameHead + "-" + i + ".zip");
            ZipUtil.zip(new File[]{newFile, newPrjFile, geoFile}, newZipFile);
            ZipItem zipItem = new ZipItem();
            zipItem.setLontitudePrecise(worldFile.getA());
            zipItem.setLatitudePrecise(worldFile.getE());
            zipItem.setLayName(newFileNameHead + "-" + i);
            zipItem.setZipFile(newZipFile);
            list.add(zipItem);
        }
        return list;
    }

    private GeoUtil.WorldFile newGeoFile(File source, File dest, Double leftlon, Double leftlat, Double rightlon, Double rightlat) throws IOException {
        if (dest.exists()) {
            dest.delete();
        }
        GeoUtil.WorldFile worldFile = GeoUtil.resolution(source, leftlon, leftlat, rightlon, rightlat);
        FileWriter fw = new FileWriter(dest);
        fw.write(worldFile.toString());
        fw.close();
        return worldFile;
    }


    private void newImageFile(File source, File dest) {
        source.renameTo(dest);
    }

    private void newPrjFile(File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("GEOGCS[\"WGS 84\",\n");
        sb.append("  DATUM[\"World Geodetic System 1984\",\n");
        sb.append("    SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]],");
        sb.append("    AUTHORITY[\"EPSG\",\"6326\"]],\n");
        sb.append("  PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],\n");
        sb.append("  UNIT[\"degree\", 0.017453292519943295],\n");
        sb.append("  AXIS[\"Geodetic longitude\", EAST],\n");
        sb.append("  AXIS[\"Geodetic latitude\", NORTH],\n");
        sb.append("  AUTHORITY[\"EPSG\",\"4326\"]]");
        FileWriter fw = new FileWriter(file);
        fw.write(sb.toString());
        fw.close();
    }


    private String getExtensionIfValidated(File image) {
        String fileName = image.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return null;
        }
        String extension = fileName.substring(dotIndex + 1);
        if ("PNG".equalsIgnoreCase(extension.toUpperCase()) || "JPG".equalsIgnoreCase(extension.toUpperCase())) {
            return extension;
        }
        return null;
    }

    /**
     * 检查并创建工作区
     */
    private void newWorkSpaceIfNotExists(GeoServerRESTReader reader, GeoServerRESTPublisher publisher, String workspaceName) {
        List<String> workspaces = reader.getWorkspaceNames();
        if (!workspaces.contains(workspaceName)) {
            if (!publisher.createWorkspace(workspaceName)) {
                throw new GeoServerException(String.format("create workspace %s failed.", workspaceName));
            }
            logger.debug("Created workspace {}", workspaceName);
        }
    }

    /**
     * 根据上传的zip文件从中读出层名
     */
    private String findLayerNameFromZipFile(File tmpFile, String originalName) {
        if (!originalName.endsWith("zip")) {
            throw new IllegalArgumentException(String.format("Error finding layer name from the file %s , whose name must end with .zip", originalName));
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(tmpFile);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        Enumeration enumeration = zipFile.entries();
        if (enumeration.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            String name = entry.getName();
            int index = name.lastIndexOf(".");
            return name.substring(0, index);
        }
        throw new IllegalArgumentException(String.format("Error finding layer name from the file %s , which seem not to contain any file", originalName));
    }

    public static class ZipItem implements Comparable {
        private Double lontitudePrecise;
        private Double latitudePrecise;
        private File zipFile;
        private String layName;

        public Double getLontitudePrecise() {
            return lontitudePrecise;
        }

        public void setLontitudePrecise(Double lontitudePrecise) {
            this.lontitudePrecise = lontitudePrecise;
        }

        public Double getLatitudePrecise() {
            return latitudePrecise;
        }

        public void setLatitudePrecise(Double latitudePrecise) {
            this.latitudePrecise = latitudePrecise;
        }

        public File getZipFile() {
            return zipFile;
        }

        public String getLayName() {
            return layName;
        }

        public void setLayName(String layName) {
            this.layName = layName;
        }

        public void setZipFile(File zipFile) {
            this.zipFile = zipFile;
        }

        public int compareTo(Object o) {
            if (!(o instanceof ZipItem)) {
                return -1;
            }
            ZipItem _o = (ZipItem) o;
            if (_o.lontitudePrecise > this.lontitudePrecise) {
                return 1;
            } else if (_o.lontitudePrecise == this.lontitudePrecise) {
                return _o.latitudePrecise.compareTo(this.latitudePrecise);
            } else {
                return -1;
            }
        }
    }

}

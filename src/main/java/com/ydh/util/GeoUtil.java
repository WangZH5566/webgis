package com.ydh.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GeoUtil {

    public static WorldFile resolution(File image, double lon1, double lat1, double lon2, double lat2) throws IOException {
        InputStream ins = new FileInputStream(image);
        BufferedImage bi = ImageIO.read(ins);
        double realWidth = SphereUtil.distance(lon1, lat1, lon2, lat1);
        double realHeight = SphereUtil.distance(lon1, lat1, lon1, lat2);
        ins.close();
        //double resolution = realWidth / bi.getWidth();
        double A = (lon2 - lon1) / (bi.getWidth());
        //double A = realWidth / bi.getWidth();
        double D = 0;
        double B = 0;
        double E = (lat2 - lat1) / (bi.getHeight());
        //double E = realHeight / bi.getHeight() * (-1);
        double C = lon1;//+ (lon2 - lon1) / 4;
        double F = lat1;//+ (lat2 - lat1) / 4;
        WorldFile worldFile = new WorldFile();
        worldFile.setA(A);
        worldFile.setD(D);
        worldFile.setB(B);
        worldFile.setE(E);
        worldFile.setC(C);
        worldFile.setF(F);
        return worldFile;
    }

    public static String getExtensionIfValidated(File image) {
        String fileName = image.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return null;
        }
        String extension = fileName.substring(dotIndex + 1);
        if ("PNG".equalsIgnoreCase(extension.toUpperCase()) || "JPG".equalsIgnoreCase(extension.toUpperCase())) {
            return extension.toLowerCase();
        }

        if("SHP".equalsIgnoreCase(extension.toUpperCase())){
            return extension.toLowerCase();
        }
        return null;
    }

    public static class WorldFile {
        private Double a;
        private Double d = 0d;
        private Double b = 0d;
        private Double e;
        private Double c;
        private Double f;

        public Double getA() {
            return a;
        }

        public void setA(Double a) {
            this.a = a;
        }

        public Double getD() {
            return d;
        }

        public void setD(Double d) {
            this.d = d;
        }

        public Double getB() {
            return b;
        }

        public void setB(Double b) {
            this.b = b;
        }

        public Double getE() {
            return e;
        }

        public void setE(Double e) {
            this.e = e;
        }

        public Double getC() {
            return c;
        }

        public void setC(Double c) {
            this.c = c;
        }

        public Double getF() {
            return f;
        }

        public void setF(Double f) {
            this.f = f;
        }


        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(a).append("\n");
            sb.append(d).append("\n");
            sb.append(b).append("\n");
            sb.append(e).append("\n");
            sb.append(c).append("\n");
            sb.append(f).append("\n");
            return sb.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        //System.out.println(resolution(new File("f:/test.png"), 116.373511, 39.967378, 116.410305, 39.942047));
        //System.out.println(resolution(new File("f:/test2.png"), 73.44696044921875, 53.557926177978516, 135.08583068847656, 3.408477306365967));
        resolution(new File("D:\\gis\\GeoServer 2.9.1\\data_dir\\data\\testing\\test1244\\test321.jpg"), 114.971924, 41.244772, 120.003662, 37.309014);
        //System.out.println

    }

}

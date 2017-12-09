package com.ydh.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static void unzip(File zipfile, File outdir) {
        ZipInputStream zipIns = null;
        ZipFile zipFile = null;
        try {
            zipIns = new ZipInputStream(new FileInputStream(zipfile));
            zipFile = new ZipFile(zipfile);
            ZipEntry entry = null;
            while ((entry = zipIns.getNextEntry()) != null) {
                File outfile = new File(outdir + File.separator + entry.getName());
                if (!outfile.getParentFile().exists()) {
                    outfile.getParentFile().mkdirs();
                }
                if (!outfile.exists()) {
                    outfile.createNewFile();
                }
                OutputStream out = new FileOutputStream(outfile);
                InputStream ins = zipFile.getInputStream(entry);
                copy(ins, out);
                close(ins);
                close(out);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            try {
                zipIns.close();
                zipFile.close();
            } catch (IOException e) {
            }
        }
    }

    public static void zip(File zipDir, File outputFile) {
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(outputFile));
            loopZip(null, zipDir, zipOut);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            close(zipOut);
        }
    }

    public static void zip(File[] sources, File destFile) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(destFile));
            for (File source : sources) {
                ZipEntry zipEntry = new ZipEntry(source.getName());
                InputStream ins = new FileInputStream(source);
                out.putNextEntry(zipEntry);
                copy(ins,out);
                ins.close();
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static void loopZip(File orginDir, File currentDir, ZipOutputStream out) {
        if (orginDir == null) {
            orginDir = new File(currentDir.getAbsolutePath());
        }
        try {
            if (currentDir.isDirectory()) {
                File[] files = currentDir.listFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        InputStream ins = new FileInputStream(file);
                        String prefix = currentDir.getCanonicalPath().replace(orginDir.getCanonicalPath(), "");
                        if (prefix.startsWith("\\")) {
                            prefix = prefix.substring(1);
                        }
                        ZipEntry zipEntry = prefix.equals("") ? new ZipEntry(file.getName()) : new ZipEntry(prefix + File.separator + file.getName());
                        out.putNextEntry(zipEntry);
                        copy(ins, out);
                        close(ins);
                    } else {
                        loopZip(orginDir, file, out);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy(InputStream ins, OutputStream out) {
        try {
            int byteread = 0;
            byte[] buffer = new byte[4 * 1024];
            while ((byteread = ins.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(InputStream ins) {
        if (ins != null) {
            try {
                ins.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                ins = null;
            }
        }

    }

    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                out = null;
            }
        }
    }


}

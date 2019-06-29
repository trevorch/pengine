package org.bizboost.pengine.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileTool {

    public final static String getContent(File file) throws IOException {
        return FileUtils.readFileToString(file,"utf8");
    }
    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            System.out.println(UUID.randomUUID().toString().replaceAll("-","").toUpperCase());
        }
    }
}

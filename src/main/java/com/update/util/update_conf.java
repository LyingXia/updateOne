package com.update.util;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

public class update_conf {
    public static void file_has_conf(String filename){
        File file = new File(filename);
        if(file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            list.add(file);
            while (!list.isEmpty()) {
                File f = list.poll();
                if (! f.isDirectory()){
                   System.out.println(f.toString());
                }
                File[] files = f.listFiles();
                if (files != null) {
                    list.addAll(Arrays.asList(files));
                }
            }
        }
    }
}

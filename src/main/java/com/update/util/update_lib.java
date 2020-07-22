package com.update.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class update_lib {
    private final static Logger logger = LoggerFactory.getLogger(update_lib.class);
    private static connect_linux cl = new connect_linux();
    public static String[] linux_has_lib(Connection connection){
        String[] linux_has_lib = cl.execute(connection,"ls /CPCN/Payment").split("\n");
        return linux_has_lib;
    }

    public static String  linux_lib_path(Connection connection,String lib_name){
        String[] linux_lib = cl.execute(connection,"find /CPCN/Payment -name "+ lib_name).split("\n");
        logger.info(lib_name + ":"+ linux_lib[0]);
        return linux_lib[0];
    }

    public static HashMap file_has_lib(String filepath){
        HashMap file_has_lib = new HashMap();
        File file = new File(filepath);
        if(file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            list.add(file);
            while (!list.isEmpty()) {
                File f = list.poll();
                String lib_path = f.toString();
                String[] path = lib_path.split("\\\\");
                if ( lib_path.indexOf(".jar")>0 && path.length >=9){
                    String lib_name = path[8];
                    lib_path = lib_path.substring(0,lib_path.lastIndexOf("\\"));
                    file_has_lib.put(lib_name,lib_path);
                }
                File[] files = f.listFiles();
                if (files != null) {
                    list.addAll(Arrays.asList(files));
                }
            }
        }
        return file_has_lib;
    }

    public static String[] jar_files(String filepath){
        File file = new File(filepath);
        String[] jar_file = file.list();
        for (int i=0;i<jar_file.length;i++){
            jar_file[i] = filepath + File.separator +jar_file[i];
        }
        return jar_file;
    }

    public static String win_to_linux( String[] linux_has_lib, HashMap file_has_lib, Connection connection){
        try {
            SCPClient scpClient = connection.createSCPClient();
            for(Object lib_name : file_has_lib.keySet()) {
                if (Arrays.asList(linux_has_lib).contains(lib_name)) {
                    System.out.println("----------------------------"+lib_name+"----部署开始---------------------------------");
                    System.out.println("删除原有"+lib_name+"下的lib包，执行rm -rf /CPCN/Payment/"+lib_name+"/lib/*");
                    System.out.println(file_has_lib.get(lib_name).toString() + "-------->" + "/CPCN/Payment/" + lib_name);
                    System.out.println("----------------------------"+lib_name+"----部署结束---------------------------------\n");
//                    connect_linux.execute(connection,"rm -rf /CPCN/Payment/"+lib_name+"/lib/*");
//                    scpClient.put(jar_files(file_has_lib.get(lib_name).toString()), "/CPCN/Payment/"+lib_name+"/lib/");
                } else {
                    System.out.println("-------------------------未部署-----" + lib_name + "--------------------");
                    System.out.println("服务器中并没有" + lib_name + "，请确认是否需要部署到/CPCN/Payment中\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static HashMap win_to_linux_one(String svn_path,String lib_name ,String lib_version,Connection connection ){
        HashMap update_history = new HashMap();
        try {
            SCPClient scpClient = connection.createSCPClient();
            String lib_win_path = svn_path + File.separator+  lib_name + File.separator + lib_version + File.separator + "lib" + File.separator;
            String lib_lin_path = linux_lib_path(connection,lib_name) ;
            logger.info(lib_win_path + ":"+ lib_win_path);
            if(!lib_lin_path.isEmpty()) {
                lib_lin_path = lib_lin_path +"/lib/";
                connect_linux.execute(connection, "rm -rf " + lib_lin_path + "*");
                logger.info("rm -rf " + lib_lin_path + "*");
                scpClient.put(jar_files(lib_win_path), lib_lin_path);
                logger.info(jar_files(lib_win_path)+"=====>"+lib_lin_path);
            }
            update_history.put(lib_win_path, lib_lin_path);
        }catch (Exception e){
            e.printStackTrace();
        }

        return update_history;
    }
}

package com.update.util;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.update.controller.updateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;


public class update_war {
    private final static Logger logger = LoggerFactory.getLogger(update_war.class);
    private static connect_linux cl = new connect_linux();
    public static HashMap linux_has_war(Connection connection){
        String[] linux_war = cl.execute(connection,"find /CPCN/*/webapps -name *.war").split("\n");
        HashMap linux_has_war = new HashMap();
        for(int i=0;i<linux_war.length;i++){
            linux_has_war.put(linux_war[i].split("/")[4],linux_war[i].substring(0,linux_war[i].lastIndexOf("/")));
        }
        return linux_has_war;
    }

    public static String  linux_war_path(Connection connection,String war_name){
        String[] linux_war = cl.execute(connection,"find /CPCN/*/webapps -name "+ war_name).split("\n");
        logger.info(war_name + ":"+ linux_war[0]);
        return linux_war[0];
    }

    public static HashMap file_has_war(String filepath){
        HashMap file_has_war = new HashMap();
        File file = new File(filepath);
        if(file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            list.add(file);
            while (!list.isEmpty()) {
                File f = list.poll();
                String path = f.toString();
                String war = path.substring(path.lastIndexOf(File.separator)+1);
                if ( f.toString().indexOf(".war")>0){
                    file_has_war.put(war,path);
                }
                File[] files = f.listFiles();
                if (files != null) {
                    list.addAll(Arrays.asList(files));
                }
            }
        }

        return file_has_war;
    }

    public static String win_to_linux(HashMap linux_has_war,HashMap file_has_War,SCPClient scpClient){
        for(Object war_name : file_has_War.keySet()){
            try {
                if(linux_has_war.containsKey(war_name)){
                    System.out.println(file_has_War.get(war_name).toString()+"-------->"+linux_has_war.get(war_name).toString());
//                    scpClient.put(file_has_War.get(war_name).toString(), linux_has_war.get(war_name).toString());
                }else{
                    System.out.println("服务器中并没有"+war_name + "，请确认是否需要部署到指定tomcat中");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    public static String win_to_linux_new(String filename ,Connection connection,String path){
        try {
            SCPClient scpClient = connection.createSCPClient();
            scpClient.put(filename, path);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "filename ----------------》 path";
    }

    public static HashMap win_to_linux_one(String svn_path,String war_name ,String war_version,Connection connection){
        HashMap update_history = new HashMap();
        try {
            String war_win_path = svn_path + File.separator+  war_name.substring(0,war_name.indexOf(".war")) + File.separator + war_version + File.separator + war_name;
            logger.info(war_name + ":"+ war_win_path);
            String war_linux_path = linux_war_path(connection,war_name);
            SCPClient scpClient = connection.createSCPClient();
            if(!war_linux_path.isEmpty()){
                scpClient.put(war_win_path, war_linux_path.substring(0, war_linux_path.lastIndexOf("/")));
               logger.info(war_win_path +"======>"+ war_linux_path.substring(0, war_linux_path.lastIndexOf("/")));

            }
            update_history.put(war_win_path,war_linux_path);
        }catch (IOException e){
            e.printStackTrace();
        }
        return update_history;
    }



    public static String[] linux_tomcat(Connection connection){
        String[] linux_tomcat = cl.execute(connection,"find /CPCN/*/bin/ -name catalina.sh").replaceAll("/bin/catalina.sh","").split("\n");
        return linux_tomcat;
    }

}

package com.update.service;

import ch.ethz.ssh2.Connection;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.update.controller.updateController;
import com.update.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

@Service
public class updateAllService {
    private final static Logger logger = LoggerFactory.getLogger(updateController.class);
    private static update_war uw = new update_war();
    private static update_lib ul = new update_lib();
    private static update_conf uc = new update_conf();
    private static connect_linux cl = new connect_linux();
    private static PropertiesUtil pu = new PropertiesUtil();
    private static sqlTxtUtil su = new sqlTxtUtil();
    private static HashMap linux_has_war;
    private static HashMap file_has_war;
    private static HashMap file_has_sql;
    private static HashMap file_has_lib;
    private static String[] linux_has_lib;


    public static String show_svn(String svn_path){
        HashMap file_has_war = new HashMap();
        HashMap file_has_lib = new HashMap();
        JSONObject allJson = new JSONObject();
        svn_path = pu.getSvnPath(svn_path);
        File file = new File(svn_path);
        if(file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            list.add(file);
            while (!list.isEmpty()) {
                File f = list.poll();
                String path = f.toString();
                String[] paths = path.split(File.separator);
//                String[] paths = path.split("\\\\");
                if ( f.toString().indexOf(".war")>0){
                    String war = path.substring(path.lastIndexOf(File.separator)+1);
                    String war_path= path.substring(0,path.lastIndexOf(File.separator));
                    String war_version = war_path.substring(war_path.lastIndexOf(File.separator)+1);
                    file_has_war.put(war,war_version);
                }

                if (path.indexOf(".jar")>0 && paths.length >=8){
                    String lib_name = paths[paths.length-4];
                    path = path.substring(0,path.lastIndexOf(File.separator));
                    String lib_path = path.substring(0,path.lastIndexOf(File.separator));
                    String lib_version = lib_path.substring(lib_path.lastIndexOf(File.separator)+1);
                    file_has_lib.put(lib_name,lib_version);
                }
                File[] files = f.listFiles();
                if (files != null) {
                    list.addAll(Arrays.asList(files));
                }
            }
        }
        allJson.put("wars",file_has_war);
        allJson.put("libs",file_has_lib);
        return allJson.toJSONString();
    }

    public static String files(String file_dir){
        JSONObject file_json = new JSONObject();
        String files_dir = null;
        if(file_dir.equals("Base")){
            files_dir = pu.getSvnBasePath();
        }else if(file_dir.equals("Pro")){
            files_dir = pu.getSvnProPath();
        }else if(file_dir.equals("Branch")){
            files_dir = pu.getSvnBranchPath();
        }else {
            file_json.put("files","获取文件目录异常");
            return file_json.toJSONString();
        }
        File file  = new File(files_dir);
        File[] fileName= file.listFiles();
        for(int i=0;i<fileName.length;i++){
            if(file_dir.equals("Base")){
                fileName[i] = new File(fileName[i] + File.separator + "01-基线发版"+ File.separator );
            }else if(file_dir.equals("Pro")){
                fileName[i] = new File(fileName[i] + File.separator  + "01-定版程序"+ File.separator  );
            }else if(file_dir.equals("Branch")){
                fileName[i] = new File(fileName[i] + File.separator );
            }
        }
        file_json.put("files",fileName);
        return file_json.toJSONString();
    }

    public static String update_few(String wars,String libs,String[] linux,String svn_path){
        HashMap update_war_history = new HashMap();
        HashMap update_lib_history = new HashMap();
        svn_path = pu.getSvnPath(svn_path);
        JSONObject allJson = new JSONObject();
        try{
            logger.info(wars);
            HashMap wars_update = JSON.parseObject(wars, HashMap.class);
            HashMap libs_update = JSON.parseObject(libs, HashMap.class);
            Connection conn = new Connection(linux[0], Integer.parseInt(linux[1]));
            conn.connect();
            conn.authenticateWithPassword(linux[2], linux[3]);
            if (conn.isAuthenticationComplete()) {
                for (Object key : wars_update.keySet()) {
                    update_war_history.putAll(uw.win_to_linux_one(svn_path, key.toString(), wars_update.get(key).toString(), conn));
                }
                for (Object key : libs_update.keySet()) {
                    update_lib_history.putAll(ul.win_to_linux_one(svn_path, key.toString(), libs_update.get(key).toString(), conn));
                }
            }else{
                allJson.put("wars","linux服务器连接失败，请确认参数");
                logger.info("linux服务器连接失败，请确认参数!!!! IP:" + linux[0] + "----->password:" + linux[3]);
                return allJson.toJSONString();
            }
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        allJson.put("wars",update_war_history);
        allJson.put("libs",update_lib_history);
        return allJson.toJSONString();
    }

    public static String update_war_few(String wars,String[] linux,String svn_path){
        HashMap update_war_history = new HashMap();
        svn_path = pu.getSvnPath(svn_path);
        JSONObject allJson = new JSONObject();
        try{
            logger.info(wars);
            HashMap wars_update = JSON.parseObject(wars, HashMap.class);
            Connection conn = new Connection(linux[0], Integer.parseInt(linux[1]));
            conn.connect();
            conn.authenticateWithPassword(linux[2], linux[3]);
            if (conn.isAuthenticationComplete()) {
                for (Object key : wars_update.keySet()) {
                    update_war_history.putAll(uw.win_to_linux_one(svn_path, key.toString(), wars_update.get(key).toString(), conn));
                }
            }else{ allJson.put("wars","linux服务器连接失败，请确认参数");
                logger.info("linux服务器连接失败，请确认参数!!!! IP:" + linux[0] + "----->password:" + linux[3]);
                return allJson.toJSONString();}
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        allJson.put("wars",update_war_history);
        return allJson.toJSONString();
    }

    public static String update_lib_few(String libs,String[] linux,String svn_path){
        HashMap update_lib_history = new HashMap();
        svn_path = pu.getSvnPath(svn_path);
        JSONObject allJson = new JSONObject();
        try{
            logger.info(libs);
            HashMap libs_update = JSON.parseObject(libs, HashMap.class);
            Connection conn = new Connection(linux[0], Integer.parseInt(linux[1]));
            conn.connect();
            conn.authenticateWithPassword(linux[2], linux[3]);
            if (conn.isAuthenticationComplete()) {
            for (Object key : libs_update.keySet()) {
                update_lib_history.putAll(ul.win_to_linux_one(svn_path, key.toString(),libs_update.get(key).toString(),conn));
            }
            }else{ allJson.put("wars","linux服务器连接失败，请确认参数");
                logger.info("linux服务器连接失败，请确认参数!!!! IP:" + linux[0] + "----->password:" + linux[3]);
                return allJson.toJSONString();}
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        allJson.put("libs",update_lib_history);
        return allJson.toJSONString();
    }


    //SQL相关的
    public static String showSqlFile(String svn_path){
        HashMap file_has_sql = new HashMap();
        JSONObject allJson = new JSONObject();
        svn_path = pu.getSvnPath(svn_path);
        File file = new File(svn_path);
        if(file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            list.add(file);
            while (!list.isEmpty()) {
                File f = list.poll();
                String path = f.toString();
                String prePath = f.getParent();
                if ( f.toString().indexOf(".sql")>0 && !f.toString().contains("对外测试") && !f.toString().contains("项目数据库") ){
                    String sql_filename =prePath.substring(prePath.lastIndexOf(File.separator)+1) +"_"+ path.substring(path.lastIndexOf(File.separator)+1);
                    file_has_sql.put(path,sql_filename);
                }
                File[] files = f.listFiles();
                if (files != null) {
                    list.addAll(Arrays.asList(files));
                }
            }
        }
        allJson.put("sqlFiles",file_has_sql);
        return allJson.toJSONString();
    }

    public static String QueryFiles(String file_dir){
        JSONObject file_json = new JSONObject();
        String files_dir = null;
        if(file_dir.equals("Base")){
            files_dir = pu.getSvnBasePath();
        }else if(file_dir.equals("Pro")){
            files_dir = pu.getSvnProPath();
        }else if(file_dir.equals("Branch")){
            files_dir = pu.getSvnBranchPath();
        }else {
            file_json.put("files","获取文件目录异常");
            return file_json.toJSONString();
        }
        File file  = new File(files_dir);
        File[] fileName= file.listFiles();
        file_json.put("sqlFiles",fileName);
        return file_json.toJSONString();
    }

    public static String run_sql(String dbInfo,String sql_path){
        JSONObject result_json = new JSONObject();
        String[] db = dbInfo.split("db_fgf");
        String result = su.readSQL(sql_path,db[0],db[1],db[2]);
        result_json.put("result_file",result);
        return result_json.toJSONString();
    }

    public static String readSqlResult(String resultFile){
        JSONObject result_json = new JSONObject();
        String filePath = resultFile.replace("执行结果：","");
        logger.info("读取"+filePath);
        String result = su.readSqlResult(filePath);
        result_json.put("result",result);
        return result_json.toJSONString();
    }

    public static void main(String[] args) {
        updateAllService ua = new updateAllService();

    }
}

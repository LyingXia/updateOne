package com.update.util;

import java.io.*;
import java.sql.*;

import ch.ethz.ssh2.ConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class sqlTxtUtil {
    private final static Logger logger = LoggerFactory.getLogger(sqlTxtUtil.class);
    private static PropertiesUtil pu = new PropertiesUtil();
    private static String logs = "";
    public static String readSQL(String filename,String url,String username,String password) {
        String sql = "";
        /*record_excute_log("执行开始","=======================================================开始===============================================");*/
        String sqlname = filename.substring(filename.lastIndexOf(File.separator)+1,filename.lastIndexOf("."));
        excute_log(sqlname,username,"执行开始","=======================================================开始===============================================");
//        String filename = "\\\\192.168.143.142\\03-产品开发\\0302-AutoPublish\\01-按上线日期发版\\20200206\\05-DataBase\\01-生产环境\\01_Payment_5.1.0_DDL.sql";
//        filename = "\\\\192.168.143.142\\03-产品开发\\0302-AutoPublish\\00-按项目发版\\B686-InstitutionTxWhiteListUpdate\\DataBase\\Payment_DDL.sql";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                if (sql == "") {
                    sql = s;
                } else {
                    sql = sql + "\n" + s;
                }
                if (s.startsWith("-") || s.endsWith("-") || s.equals("")) {
                    sql = "";
                } else if (s.contains(";")) {
                    System.out.println(sql);
//                    System.out.println(sql);
                    logger.info(sql);
//                    System.out.println("===============================================");
//                    logger.info("===============================================");
                    Connection connection = getConnectionDW(url, username, password);
                    logger.info("==========准备执行============》"+url+"《============");
                    if (sqlname.contains("DDL")){
                        excuteDDLSQL(connection, sqlname,username,sql.replace(";", ""));
                    }else if(sqlname.contains("DML")){
                        excuteDMLSQL(connection, sqlname,username,sql.replace(";", ""));
                    }
                    sql = "";
                    connection.close();
                }
            }
            /*record_excute_log("执行完成","=======================================================结束===============================================");
            write_excute_log(sqlname,username,logs);*/
            excute_log(sqlname,username,"执行完成","=======================================================结束===============================================");
            br.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return  "数据库连接异常，请确认参数";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "执行结果："+ pu.getDbResultPath()+File.separator+sqlname+"_"+username+"_excute.log";
    }

    private static Connection getConnectionDW( String url, String username, String password){
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void excuteDDLSQL(Connection conn,String sqlname,String username,String sql)  {
        try {
            Statement stat = conn.createStatement();
            //DDL
            int b = stat.executeUpdate(sql);
            excute_log(sqlname,username,sql,change_color_text("执行成功","green"));
            logger.info("执行成功");
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
            excute_log(sqlname,username,sql, change_color_text("java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在","red"));
            logger.info( "java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            e.printStackTrace();
        } catch (java.sql.SQLException e){
            e.printStackTrace();
            excute_log(sqlname,username,sql, change_color_text("java.sql.SQLException: ORA-01430: 表中已存在要添加的列","red"));
            logger.info( "java.sql.SQLException: ORA-01430: 表中已存在要添加的列");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void excuteDMLSQL(Connection conn,String sqlname,String username,String sql) throws Exception {
        try {
            Statement stat = conn.createStatement();
            //DML
            int a =stat.executeUpdate(sql);//a位有几行数据
            String result = a +change_color_text(" row affected","blue");
            System.out.println(result);
            excute_log(sqlname,username,sql,result);
            logger.info(result);
        } catch (SQLSyntaxErrorException e) {
            excute_log(sqlname,username,sql, change_color_text("java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在","red"));
            logger.info("java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

    private static void excuteDQLSQL(Connection conn,String sqlname,String username, String sql) throws Exception {
        try {
            Statement stat = conn.createStatement();
            //DQL
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                int columns = rs.getMetaData().getColumnCount();
                for (int i=1;i<columns;i++){
                    System.out.print (rs.getString(i) +"\t");
                }
            }
        } catch (SQLSyntaxErrorException e) {
            excute_log(sqlname,username,sql, "java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

    private static void excute_log(String sqlname,String username,String sql,String result){
        String filename =pu.getDbResultPath()+File.separator+sqlname+"_"+username+"_excute.log";
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File(filename);
            FileWriter fw = new FileWriter(f,true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sql+"\r\n");
            pw.println(result+"\r\n");
            pw.println("======================================================================================================\r\n");
            pw.flush();
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String change_color_text(String s, String color){
        return "<label style=\"color:"+color+";\">" + s + "</label>";
    }

    private static String excuteDMLSQL(Connection conn,String sql) throws Exception {
        String once_excute_DML_log="";
        try {
            Statement stat = conn.createStatement();
            //DML
            int a =stat.executeUpdate(sql);//a位有几行数据
            String result = a +" row affected";
            System.out.println(result);
            once_excute_DML_log = record_excute_log(sql,result);
            logger.info(result);
        } catch (SQLSyntaxErrorException e) {
            once_excute_DML_log = record_excute_log(sql, "java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            logger.info("java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
        return once_excute_DML_log;
    }
    private static String excuteDDLSQL(Connection conn,String sql)  {
        String once_excute_DDL_log = "";
        try {
            Statement stat = conn.createStatement();
            //DDL
            int b = stat.executeUpdate(sql);
            once_excute_DDL_log = record_excute_log(sql,"执行成功");
            logger.info("执行成功");
        } catch (SQLSyntaxErrorException e) {
            once_excute_DDL_log = record_excute_log(sql, "java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            logger.info( "java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            e.printStackTrace();
        } catch (java.sql.SQLException e){
            logger.info( "java.sql.SQLException: ORA-01430: 表中已存在要添加的列");
            once_excute_DDL_log = record_excute_log(sql, "java.sql.SQLException: ORA-01430: 表中已存在要添加的列");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return once_excute_DDL_log;
    }
    private static String record_excute_log(String sql,String result){
        logs = logs + sql+"\r\n"+result+"\r\n"+"======================================================================================================\r\n";
        return logs;
    }
    private static void write_excute_log(String sqlname,String username,String logs){
        String filename =pu.getDbResultPath()+File.separator+sqlname+"_"+username+"_excute.log";
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File(filename);
            FileWriter fw = new FileWriter(f,false);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(logs);
            pw.flush();
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String readSqlResult(String resultname){
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(resultname));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine
                result = result +s+"\n";
            }
        }catch (java.io.FileNotFoundException e){
            e.printStackTrace();
            return "当前执行结果文件不存在，请确认是否生成";
        }catch(Exception e){
            e.printStackTrace();
            return  "读取执行结果文件时系统异常，请联系管理员";
        }
        return  result;
    }

    public static void main(String[] args) {
    }
}

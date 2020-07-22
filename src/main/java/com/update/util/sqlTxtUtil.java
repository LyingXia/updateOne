package com.update.util;

import java.io.*;
import java.sql.*;

public class sqlTxtUtil {
    public static String readSQL(String filename,String url,String username,String password) {
        String sql = "";
//        String filename = "\\\\192.168.143.142\\03-产品开发\\0302-AutoPublish\\01-按上线日期发版\\20200206\\05-DataBase\\01-生产环境\\01_Payment_5.1.0_DDL.sql";
        filename = "\\\\192.168.143.142\\03-产品开发\\0302-AutoPublish\\00-按项目发版\\B686-InstitutionTxWhiteListUpdate\\DataBase\\Payment_DDL.sql";
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
                    System.out.println("===============================================");
                    Connection connection = getConnectionDW(url, username, password);
                    if (filename.contains("DDL")){
                        excuteDDLSQL(connection, sql.replace(";", ""));
                    }else if(filename.contains("DML")){
                        excuteDMLSQL(connection, sql.replace(";", ""));
                    }
                    sql = "";
                }
            }
            br.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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

    private static void excuteDDLSQL(Connection conn,String sql) throws Exception {
        try {
            Statement stat = conn.createStatement();
            //DDL
            boolean b = stat.execute(sql);
            if(b){
                excute_log(sql,"执行成功");
            }else{
                excute_log(sql,"执行失败");
            }
        } catch (SQLSyntaxErrorException e) {
            excute_log(sql, "java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

    private static void excuteDMLSQL(Connection conn,String sql) throws Exception {
        try {
            Statement stat = conn.createStatement();
            //DML
            int a =stat.executeUpdate(sql);//a位有几行数据
            String result = a +" row affected";
            System.out.println(result);
            excute_log(sql,result);
        } catch (SQLSyntaxErrorException e) {
            excute_log(sql, "java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

    private static void excuteDQLSQL(Connection conn,String sql) throws Exception {
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
            excute_log(sql, "java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

    private static void excute_log(String sql,String result){
        String filename ="C:\\Users\\Dell\\Desktop\\test.txt";
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File(filename);
            FileWriter fw = new FileWriter(f, true);
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

    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@172.31.2.2:1521:TESTDB";
        String userid = "PAYMENT_ZGH01";
        String password = "cfca1234";
        readSQL("",url,userid,password);
    }
}

package com.update.util;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;
import oracle.jdbc.driver.OracleDriver;

import java.io.File;

public class sqlUtil {
    public static void run_sql(String sql_path,String url,String userid,String password,String resutl_path){
        String[] a = sql_path.split(File.separator);
//        String[] a = sql_path.split("\\\\");
        String dbname = a[a.length-1];
        SQLExec sqlExec = new SQLExec();
        //设置数据库参数
        sqlExec.setDriver("oracle.jdbc.driver.OracleDriver");
//        sqlExec.setUrl("jdbc:oracle:thin:@172.31.2.2:1521:TESTDB");
        sqlExec.setUrl(url);
        sqlExec.setUserid(userid);
        sqlExec.setPassword(password);
        //要执行的脚本
        sqlExec.setSrc(new File(sql_path));
        //有出错的语句该如何处理
        sqlExec.setOnerror((SQLExec.OnError)(EnumeratedAttribute.getInstance(
                SQLExec.OnError.class, "abort")));
        sqlExec.setPrint(true); //设置是否输出
        //输出到文件 sql.out 中；不设置该属性，默认输出到控制台
        sqlExec.setOutput(new File(resutl_path+ File.separator + dbname + ".out"));
        sqlExec.setProject(new Project()); // 要指定这个属性，不然会出错
        sqlExec.execute();

    }
}

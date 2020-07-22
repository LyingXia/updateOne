import com.update.service.updateAllService;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import oracle.jdbc.driver.OracleDriver;

public class testMain {
    static String diverName="oracle.jdbc.driver.OracleDriver";
    static String url = "jdbc:oracle:thin:@192.168.90.11:1521:ucap";
    static String user="armyoa73015";
    static String pwd = "armyoa73015";
    static String fileName = "D:\\workspace\\huzhou\\jd-txt73015\\sql\\f_xxyr_xlcjdj.sql";
   /* public static void runSqlFile(){
        SQLExec sqlExec = new SQLExec();
        sqlExec.setDriver(diverName);
        sqlExec.setUrl(url);
        sqlExec.setUserid(user);
        sqlExec.setPassword(pwd);
        sqlExec.setSrc(new File(fileName));
        sqlExec.setEncoding("GBK");
        DelimiterType dt = new DelimiterType();
        dt.setValue("row");
        sqlExec.setDelimiterType(dt);
        sqlExec.setDelimiter("/");
        sqlExec.setKeepformat(true);
        sqlExec.setProject(new Project());
        sqlExec.execute();
    }*/
    public static void runSqlFile1() {
        try {
            Class.forName(diverName);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setAutoCommit(true);
            File file = new File(fileName);
            if (file.getName().endsWith(".sql")) {
                runner.setFullLineDelimiter(true);
                runner.setDelimiter("##");
                runner.setSendFullScript(false);
                runner.setAutoCommit(true);
                runner.setStopOnError(true);
                runner.runScript(new InputStreamReader(new FileInputStream(fileName), "GBK"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        updateAllService ua  = new updateAllService();
        String sql_path = "C:\\Users\\Dell\\Desktop\\test.sql";
        String userid = "PAYMENT_ZGH01";
        String password = "cfca1234";
        String a = ua.run_sql(sql_path,userid,password);
    }
}

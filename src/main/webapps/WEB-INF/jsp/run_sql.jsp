<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2020/2/19
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>sql文件执行</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" type="text/css" />
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/runSQL.js"></script>

</head>
<body>
<div id = "main">
    <div id = "title">
        <table align="center">
            <tr>
                <td>
                    svn文件夹地址
                </td>
                <td>
                    <select name="sql_file_select" id="sql_file_select">
                        <option select= "selected" value="Base">请选择目录</option>
                        <option value="Base">基线目录</option>
                        <option value="Pro">定版目录</option>
                        <option value="Branch">分支目录</option>
                    </select>
                    <select name="sql_files_dir" id="sql_files_dir">
                        <option select= "selected" value="Base">请选择目录</option>
                    </select>
                    <a href = "/updateOne/testTools/update"  >跳转到update页面</a>
                </td>
            </tr>
            <tr>
                <td>
                    数据库连接信息
                </td>
                <td>
                    <input type="text" value = "jdbc:oracle:thin:@172.31.2.2:1521:TESTDB" id="sql_url" class="databaseInfo"/>
                    <%--<input type="text" value= "请输入数据库用户名" id="sql_username" class="databaseInfo"/>--%>
                    <input type="text" value= "PAYMENT_***01" id="sql_username" class="databaseInfo"/>
                    <%--<input type="text" value= "请输入数据库密码" id="sql_password" class="databaseInfo"/>--%>
                    <input type="text" value= "cfca1234" id="sql_password" class="databaseInfo"/>
                </td>
            </tr>
        </table>
        <div name="button">
            <input type="submit" value="查看SQL文件" id="show_sql"/>
            <input type="submit" value="执行SQL文件" id="run_sql"/>
        </div>
    </div>
    <div id="SQLs">
        <p id = "select_checkbox" >
            <input type="checkbox"  id = "select_all">全选</input>
            <input type="checkbox"  id = "select_not">反选</input>
        </p>
        <div id ="sql_files"></div>
    </div>
    <div id ="results"></div>
</div>
<div id ="results_show">
    <input type="button"  id = "results_show_close" value="关闭">
</div>
</body>
</html>

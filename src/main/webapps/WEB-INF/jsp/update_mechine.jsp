<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>低配版部署环境</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" type="text/css" />
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/update.js"></script>
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
                        <%--<input type="text" value = "请输入文件夹,例如\\192.168.143.142\03-产品开发\0302-AutoPublish\00-按项目发版\B533-StaticCodeOptimizationV1.0" id="svn_file" class="svnInfo"/>--%>
                        <%--<input type="text" value = "\\192.168.143.142\03-产品开发\0302-AutoPublish\00-按项目发版\B633-BopOfflineGathering\" id="svn_file" class="svnInfo"/>--%>
                        <select name="file_select" id="file_select">
                            <option select= "selected" value="Base">请选择目录</option>
                            <option value="Base">基线目录</option>
                            <option value="Pro">定版目录</option>
                            <option value="Branch">分支目录</option>
                        </select>
                            <select name="files_dir" id="files_dir">
                                <option select= "selected" value="Base">请选择目录</option>
                            </select>
                            <a href = "/updateOne/testTools/runSQL"  >跳转到SQL页面</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        Linux服务器信息
                    </td>
                    <td>
                        <%--<input type="text" value = "172.31.2.126" id="linux_ip" class="linuxInfo"/>--%>
                            <select name="linux_ip" id="linux_ip">
                                <option select= "selected" value="Base">请选择ip</option>
                            </select>
                            <span>目前linux服务器信息写在文件中，只需要选择IP即可</span>
                        <%--<input type="text" value= "22" id="linux_port" class="linuxInfo"/>--%>
                        <%--<input type="text" value= "root" id="linux_user" class="linuxInfo"/>--%>
                        <%--<input type="text" value = "请输入服务器passwd" id="linux_pwd" class="linuxInfo"/>--%>
                        <%--<input type="text" value = "cfca1234" id="linux_pwd" class="linuxInfo"/>--%>
                    </td>
                </tr>
            </table>
            <div name="button">
                <input type="submit" value="查看svn目录" id="show_svn"/>
                <input type="submit" value="确认部署" id="run_update"/>
            </div>
        </div>
        <div id ="files">
            <p id = "select_checkbox" >
                <input type="checkbox"  id = "select_all">全选</input>
                <input type="checkbox"  id = "select_not">反选</input>
            </p>
            <div id ="wars"></div>
            <div id ="libs"></div>
        </div>
        <div id ="linux">
            <div id="linux_wars_none"></div>
            <div id="linux_libs_none"></div>
            <div id="linux_wars"></div>
            <div id="linux_libs"></div>
        </div>
    </div>
</body>
</html>
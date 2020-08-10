$('document').ready(function() {
    function getSQlList(file_select){
        $("#sql_files_dir option").remove()
        var requestData = {"file_dir":file_select}
        $.ajax({
            type: "post",
            data: requestData,
            url: "/updateOne/update/QueryFiles",
            dataType:"json",
            success: function (data) {
                var file = data["sqlFiles"];
                console.log(file.length);
                for (var i = file.length-1; i >=0; i--) {
                    console.log(file[i]);
                    $("#sql_files_dir").append("<option id =\" + file[i] +  \">" + file[i] + "</option>");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("获取下拉框文件信息失败-"+errorThrown);
            }
        });
    }

    function isValidURL(url){
        // var reg = /^jdbc:oracle:thin:@(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5]):\*$/
        var reg = /^jdbc:oracle:thin:@(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{4}|65[0-4]\d{2}|655[0-2]\d|6553[0-5]):[A-Za-z0-9]*$/
        return reg.test(url);

    }

    function run_sql(run_sqls){
        $.ajax({
            type: "post",
            url: "/updateOne/update/runSqlFile",
            data: run_sqls,
            dataType:"json",
            scriptCharset:"UTF-8",
            success: function (data) {
                resultFile = data["result_file"]
                if(resultFile ==""){
                    alert("请确认路径是否正确\n" +  svn_path);
                    return
                }
                else if(resultFile =="数据库连接异常，请确认参数"){
                    alert("数据库连接异常，请确认参数\n");
                    $("#results").append("<p>数据库连接异常，请确认参数</p>");
                    return
                }else {
                    $("#results").append("<p>" + resultFile + "=======<button  name = 'results_read' value='"+resultFile+"' >查看执行结果</button></p>");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("执行SQL文件异常"+errorThrown);
            }
        });
    }

    function results_read(resultFile){
        $.ajax({
            type: "post",
            url: "/updateOne/update/readSqlResult",
            data: resultFile,
            dataType:"json",
            scriptCharset:"UTF-8",
            success: function (data) {
                var result =  data["result"];
                if(result == "当前执行结果文件不存在，请确认是否生成"){
                    alert(result)
                    return
                }else if(result == "读取执行结果文件时系统异常，请联系管理员"){
                    alert(result)
                    return
                }else{
                    $("#results_show").show();
                    $("#results_show").append("<div>"+result+"</div>");
                    red_fault();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("读取执行结果异常"+errorThrown);
            }
        })
    }

    $("#results").on("click","button[name='results_read']",function(){
        var str =  $(this).val();
        var results = {};
        results["resultFile"] = str;
        results_read(results);
    })

    $("#results_show_close").click(function () {
        $("#results_show div").text('');
        $("#results_show").hide();
    })

    $("#sql_file_select").change(function () {
        value = $("#sql_file_select").val()
        getSQlList(value)
    })

    $("#show_sql").click(function () {
        $("#select_checkbox").show()
        $("#sql_files").text("")
        $("#show_sql").attr("disabled",true);
        var svn_path = $("#sql_files_dir").val();
        if(svn_path == null || svn_path == ""){
            alert("请输入正确的路径");
            return;
        }
        var data = {"svn_path":svn_path}
        alert("请稍后");
        $.ajax({
            type: "post",
            url: "/updateOne/update/showSqlFile",
            data: data,
            dataType:"json",
            scriptCharset:"UTF-8",
            success: function (data) {
                var sql_files = data["sqlFiles"];
                if(JSON.stringify(sql_files) =="{}"){
                    alert("请确认路径是否正确\n" +  svn_path);
                }
                for (key in sql_files) {
                    var sql_file_name  = sql_files[key];
                    $("#sql_files").append("<p><input type=\"checkbox\" name = 'sqlFiles_check' value='"+key+"'  />"+sql_file_name+"</p>");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("获取svn目录信息失败"+errorThrown);
            }
        });
        $("#show_sql").attr("disabled",false);
    })

    $("#select_all").click(function () {
        if($("#select_all").prop("checked") == true) {
            $("#select_not").prop("checked", false);
            $("#sql_files input[type='checkbox']").prop("checked", true);
        }else{
            $("#sql_files input[type='checkbox']").prop("checked", true);
        }
    })

    $("#select_not").click(function () {
        if($("#select_not").prop("checked") == true) {
            $("#select_all").prop("checked", false);
            var sql_files_checkbox = $("#sql_files input[type='checkbox']");
            sql_files_checkbox.each(function(){
                if ($(this).prop("checked") == true) {
                    $(this).prop("checked", false);
                }else{
                    $(this).prop("checked", true);
                }
            })
        }else{
            $("#sql_files checkbox").prop("checked", true);
        }
    })

    $("#run_sql").click(function(){
        if(!isValidURL($("#sql_url").val())){
            alert("请输入正确的数据库URL")
            return
        }
        var run_sqls = {};
        run_sqls["dbInfo"] = $("#sql_url").val() + "db_fgf"+$("#sql_username").val()+ "db_fgf"+$("#sql_password").val();
        $("#results").text('');
        $("#sql_files input:checkbox").each(function () {
            if ($(this).is(':checked')) {
                var str =  $(this).val()
                run_sqls["sql_path"] = str
                run_sql(run_sqls)
            }
        });

        if(run_sqls["sql_path"] == "{}" ){
            alert("请先选择要部署的模块！")
            return
        }
        alert("请稍后");

    })
})

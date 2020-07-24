$('document').ready(function(){
    function getFileList(file_select){
        $("#files_dir option").remove()
        var requestData = {"file_dir":file_select}
        $.ajax({
            type: "post",
            data: requestData,
            url: "/updateOne/update/files",
            dataType:"json",
            success: function (data) {
                var file = data["files"];
                console.log(file.length);
                for (var i = file.length-1; i >=0; i--) {
                    console.log(file[i]);
                    $("#files_dir").append("<option id =\" + file[i] +  \">" + file[i] + "</option>");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("获取下拉框文件信息失败-"+errorThrown);
            }
        });
    }
    $("#show_svn").click(function () {
        $("#select_checkbox").show()
        $("#show_svn").attr("disabled",true);
        var svn_path = $("#files_dir").val();
        if(svn_path == null || svn_path == ""){
            alert("请输入正确的路径");
            return;
        }
        var data = {"svn_path":svn_path}
        $("#wars").text('');
        $("#libs").text('');
        alert("请稍后");
        $.ajax({
            type: "post",
            url: "/updateOne/update/showSVN",
            data: data,
            dataType:"json",
            scriptCharset:"UTF-8",
            success: function (data) {
                var wars_versions = data["wars"];
                var libs_versions = data["libs"];
                if(JSON.stringify(wars_versions) =="{}"&& JSON.stringify(libs_versions)=="{}"){
                    alert("请确认路径是否正确\n" +  svn_path);
                }
                for (key in wars_versions) {
                    var version = wars_versions[key];
                    $("#wars").append("<p><input type=\"checkbox\" name = 'wars_check' value='"+key+"_"+version+"'  />"+key+"-------"+version+"</p>");
                }
                for (key in libs_versions) {
                    var version = libs_versions[key];
                    $("#libs").append("<p><input type=\"checkbox\" name = 'libs_check' value='"+key+"_"+version+"' />"+key+"-------"+version+"</p>");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("获取svn目录信息失败"+errorThrown);
            }
        });
        $("#show_svn").attr("disabled",false);
    })
    $("#run_update").click(function(){
        if(!isValidIP($("#linux_ip").val())){
            alert("请输入正确的服务器IP地址")
            return
        }
        var update_files = {};
        var update_wars = {};
        var update_libs = {};
        update_files["linux"] = $("#linux_ip").val() + "linux_fgf"+$("#linux_port").val()+ "linux_fgf"+$("#linux_user").val()+ "linux_fgf"+$("#linux_pwd").val();
        update_files["svn_path"] = $("#files_dir").val();
        $("#linux").text('');
        $("#wars input:checkbox").each(function () {
            if ($(this).is(':checked')) {
                var str =  $(this).val()
                update_wars = {}
                update_wars[str.substring(0,str.lastIndexOf("_"))] = str.substring(str.lastIndexOf("_")+1)
                update_files["wars"] = JSON.stringify(update_wars)
                updateWarsOne(update_files)
            }
        });
        $("#libs input:checkbox").each(function () {
            if ($(this).is(':checked')) {
                var str =  $(this).val()
                update_libs = {}
                update_libs[str.substring(0,str.lastIndexOf("_"))]=str.substring(str.lastIndexOf("_")+1)
                update_files["libs"] = JSON.stringify(update_libs)
                updateLibsOne(update_files)
            }
        });
        // $("#linux").append("<p id = \"case\">正在部署中 请稍后</p>")
        // $("#case").text("部署完成")
        update_files["wars"] = JSON.stringify(update_wars)
        update_files["libs"] = JSON.stringify(update_libs)
        if(update_files["wars"] == "{}" && update_files["libs"] == "{}" ){
            alert("请先选择要部署的模块！")
            return
        }
        alert("请稍后");
        // $.ajax({
        //     type: "post",
        //     url: "/updateOne/update/updateFew",
        //     data: update_files,
        //     dataType:"json",
        //     scriptCharset:"UTF-8",
        //     success: function (data) {
        //         var wars_update = data["wars"];
        //         var libs_update = data["libs"];
        //         if(JSON.stringify(wars_update) =="{}" && JSON.stringify(libs_update)=="{}"){
        //             alert("请确认路径是否正确\n" +  svn_path);
        //         }
        //         for (win_war_path in wars_update) {
        //             var lin_war_path = wars_update[win_war_path];
        //             if (lin_war_path.toString() == ""){
        //                 $("#linux").append("<p class='linux_none'>"+win_war_path+"=====未部署在linux服务器中</p>");
        //             }else {
        //                 $("#linux").append("<p>" + win_war_path + "============>" + lin_war_path + "</p>");
        //             }
        //         }
        //         for (win_lib_path in libs_update) {
        //             var lin_lib_path = libs_update[win_lib_path];
        //             if (lin_lib_path.toString()==""){
        //                 $("#linux").append("<p class='linux_none'>"+win_lib_path+"=====未部署在linux服务器中</p>");
        //             }else {
        //                 $("#linux").append("<p>" + win_lib_path + "============>" + lin_lib_path + "</p>");
        //             }
        //         }
        //     },
        //     error: function (XMLHttpRequest, textStatus, errorThrown) {
        //         alert("获取svn目录信息失败"+errorThrown);
        //     }
        // });
    })
    $("#file_select").change(function(){
        value = $("#file_select").val()
        getFileList(value)
    })
    $("#select_all").click(function () {
        if($("#select_all").prop("checked") == true) {
            $("#select_not").prop("checked", false);
            $("#wars input[type='checkbox']").prop("checked", true);
            $("#libs input[type='checkbox']").prop("checked", true);
        }else{
            $("#wars input[type='checkbox']").prop("checked", true);
            $("#libs input[type='checkbox']").prop("checked", true);
        }
    })
    $("#select_not").click(function () {
        if($("#select_not").prop("checked") == true) {
            $("#select_all").prop("checked", false);
            var wars_checkbox = $("#wars input[type='checkbox']");
            var libs_checkbox = $("#libs input[type='checkbox']");
            wars_checkbox.each(function(){
                if ($(this).prop("checked") == true) {
                    $(this).prop("checked", false);
                }else{
                    $(this).prop("checked", true);
                }
            })
            libs_checkbox.each(function(){
                if ($(this).prop("checked") == true) {
                    $(this).prop("checked", false);
                }else{
                    $(this).prop("checked", true);
                }
            })
        }else{
            $("#wars checkbox").prop("checked", true);
            $("#libs checkbox").prop("checked", true);
        }
    })
    function isValidIP(ip) {
        var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
        return reg.test(ip);
    }
    function updateWarsOne(update_files){
        $.ajax({
            type: "post",
            url: "/updateOne/update/updateWarsFew",
            data: update_files,
            dataType:"json",
            scriptCharset:"UTF-8",
            success: function (data) {
                var wars_update = data["wars"];
                if(JSON.stringify(wars_update) =="{}"){
                    alert("请确认路径是否正确\n" +  svn_path);
                }
                if(wars_update =="linux服务器连接失败，请确认参数"){
                    alert("linux服务器连接失败，请确认参数\n");
                    $("#linux").append("<p>linux服务器连接失败，请确认参数</p>");
                    return
                }
                for (win_war_path in wars_update) {
                    var lin_war_path = wars_update[win_war_path];
                    if (lin_war_path.toString() == ""){
                        $("#linux").append("<p class='linux_none'>"+win_war_path+"=====未部署在linux服务器中</p>");
                    }else {
                        $("#linux").append("<p>" + win_war_path + "============>" + lin_war_path + "</p>");
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("获取svn目录信息失败"+errorThrown);
            }
        });
    }
    function updateLibsOne(update_files){
        $.ajax({
            type: "post",
            url: "/updateOne/update/updateLibsFew",
            data: update_files,
            dataType:"json",
            scriptCharset:"UTF-8",
            success: function (data) {
                var libs_update = data["libs"];
                var wars_update = data["wars"];
                if(JSON.stringify(libs_update)=="{}"){
                    alert("请确认路径是否正确\n" +  svn_path);
                }
                if(wars_update =="linux服务器连接失败，请确认参数"){
                    return
                }
                for (win_war_path in libs_update) {
                    var lin_war_path = libs_update[win_war_path];
                    if (lin_war_path.toString() == ""){
                        $("#linux").append("<p class='linux_none'>"+win_war_path+"=====未部署在linux服务器中</p>");
                    }else {
                        $("#linux").append("<p>" + win_war_path + "============>" + lin_war_path + "</p>");
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $("#case").value("部署出现错误，请确认日志~")
                alert("获取svn目录信息失败"+errorThrown);
            }
        });
    }
})
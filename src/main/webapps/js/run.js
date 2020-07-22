$('document').ready(function(){
    //取得学校放入下拉框
    var screenWidth = document.body.clientWidth;
    var screenHeight = document.body.clientHeight;
    console.log(screenWidth)
    $("#operate").css("marginLeft",(screenWidth-302)/2+"px");
    $.ajax({
        type: "post",
        url: "/testApplication/test/files",
        contentType: "json",
        success: function (data) {
            var file = data["files"];
            console.log(file.length);
            for (var i = 0; i < file.length; i++) {
                console.log(file[i]);
                $("#files").append("<option id =" + file[i] +  ">" + file[i] + "</option>");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("获取下拉框文件信息失败-"+errorThrown);
        }
    });
    $("#go").click(function () {
        var url = $("#url").val();
        var contentType=$("#contentType").val();
        var filename=$("#files").val();
        if (url == "" || contentType =="" || filename == ""){
            alert("请输入或者选择正确的内容");
            return
        }
        var data = {"url":url,"contentType":contentType,"filename":filename}
        $("#showText").empty();
        // $("#show").append("<input type=\"button\" value=\"关闭\" id=\"closeShow\" onclick='close()'/>")
        $.ajax({
            type:"post",
            url:"/testApplication/test/runTest",
            async:false,
            data:data,
            dataType:"json",
            scriptCharset:"UTF-8",
            success:function (data) {
                $("#show").css("width",(screenWidth-300)+"px");
                $("#show").css("height",(screenHeight-40)+"px");
                $("#show").css("margin-left","150px");
                $("#show").css("background-color","grey");
                $("#show").css("z-index","100");
                $("#showText").append("<p> 您当前请求的地址是 : " + url + "</p>");
                $("#closeShow").css("visibility","visible");
                for (var key in data) {
                    // $("#show").css("visibility","visible");
                    $("#showText").append("<p> request :" + key + "</p>");
                    $("#showText").append("<p> response :" + JSON.stringify(data[key])+"" + "</p>");
                    $("#showText").append("<br />")
                    /*$("#requestShow").css("width",(screenWidth-300)+"px");
                    $("#requestShow").css("margin-left","150px");
                    $("#responseShow").css("width",(screenWidth-300)/2+"px");
                    $("#responseShow").css("margin-left",((screenWidth-300)/2+150)+"px");
                    $("#requestShow").append("<p>" + key + "</p>");
                    $("#responseShow").append("<p>" + data[key] + "</p>");*/
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("发送执行请求失败-"+errorThrown);
            }
        })

    })

    $("#closeShow").click(function () {
        console.log("进来了");
        $("#showText").empty();
        $("#show").css("background-color","white");
        $("#show").css("z-index","-1");
        $("#closeShow").css("visibility","hidden");
    })
})
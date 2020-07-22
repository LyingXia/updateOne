$('document').ready(function(){
    var screenWidth = document.body.clientWidth;
    console.log(screenWidth)
    $("#operate").css("marginLeft",(screenWidth-800)/2+"px");
    $("#appde_button").click(function () {
        var value = $("#input_value").val();
        var data = {"value":value};
        alert("APP解密功能暂时维护中");
        /* if(value == ""||value == null){
             alert("请输入需要进行操作的字符串");
         }else {
             $.ajax({
                 type: "post",
                 url: "/testApplication/crypt/appde",
                 data: data,
                 dataType:"text",
                 success: function (data) {
                     $("#output").empty();
                     $("#output").append("<p>您刚才进行了app解密</p>");
                 },
                 error: function (XMLHttpRequest, textStatus, errorThrown) {
                     alert("转换出错，请联系管理员" + errorThrown);
                 }
             });
         }*/
    })
    $("#appen_button").click(function () {
        var value = $("#input_value").val();
        var data = {"value":value}
        alert("APP加密功能暂时维护中")
        /*if(value == ""||value == null){
            alert("请输入需要进行操作的字符串");
        }else {
            $.ajax({
                type: "post",
                url: "/testApplication/crypt/appen",
                data: data,
                dataType:"text",
                success: function (data) {
                    console.log(data)
                    $("#output").empty();
                    $("#output").append("<p>您刚才进行了app加密</p>");
                    $("#output").append("<p>"+data+"</p>");
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("转换出错，请联系管理员" + errorThrown);
                }
            });
        }*/
    })
    $("#zt_lotde_button").click(function () {
        var value = $("#input_value").val();
        var data = {"value":value,"platform":"zt"}
        if(value == ""||value == null){
            alert("请输入需要进行操作的字符串");
        }else {
            $.ajax({
                type: "post",
                url: "/testApplication/crypt/lotde",
                data: data,
                dataType:"text",
                success: function (data) {
                    $("#output").empty();
                    $("#output").append("<p>您刚才进行了zt_lot解密</p>");
                    $("#output").append("<p>"+data+"</p>");
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("转换出错，请联系管理员" + errorThrown);
                }
            });
        }
    })
    $("#zt_loten_button").click(function () {
        var value = $("#input_value").val();
        var data = {"value":value,"platform":"zt"}
        if(value == ""||value == null){
            alert("请输入需要进行操作的字符串");
        }else {
            $.ajax({
                type: "post",
                url: "/testApplication/crypt/loten",
                data: data,
                dataType:"text",
                success: function (data) {
                    $("#output").empty();
                    $("#output").append("<p>您刚才进行了zt_lot加密</p>");
                    $("#output").append("<p>"+data+"</p>");
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("转换出错，请联系管理员" + errorThrown);
                }
            });
        }
    })
    $("#yc_lotde_button").click(function () {
        var value = $("#input_value").val();
        var data = {"value":value,"platform":"yc"}
        if(value == ""||value == null){
            alert("请输入需要进行操作的字符串");
        }else {
            $.ajax({
                type: "post",
                url: "/testApplication/crypt/lotde",
                data: data,
                dataType:"text",
                success: function (data) {
                    $("#output").empty();
                    $("#output").append("<p>您刚才进行了yc_lot解密</p>");
                    $("#output").append("<p>"+data+"</p>");
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("转换出错，请联系管理员" + errorThrown);
                }
            });
        }
    })
    $("#yc_loten_button").click(function () {
        var value = $("#input_value").val();
        var data = {"value":value,"platform":"yc"}
        if(value == ""||value == null){
            alert("请输入需要进行操作的字符串");
        }else {
            $.ajax({
                type: "post",
                url: "/testApplication/crypt/loten",
                data: data,
                dataType:"text",
                success: function (data) {
                    $("#output").empty();
                    $("#output").append("<p>您刚才进行了yc_lot加密</p>");
                    $("#output").append("<p>"+data+"</p>");
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("转换出错，请联系管理员" + errorThrown);
                }
            });
        }
    })
    $("#lotcom_button").click(function () {
        var value = $("#input_value").val();
        var data = {"value":value}
        if(value == ""||value == null){
            alert("请输入需要进行操作的字符串");
        }else {
            $.ajax({
                type: "post",
                url: "/testApplication/crypt/lotcom",
                data: data,
                dataType:"text",
                success: function (data) {
                    $("#output").empty();
                    $("#output").append("<p>您刚才进行了lot参数压缩</p>");
                    $("#output").append("<p>"+data+"</p>");
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("转换出错，请联系管理员" + errorThrown);
                }
            });
        }
    })
    $("#lotdecom_button").click(function () {
        var value = $("#input_value").val();
        var data = {"value":value}
        if(value == ""||value == null){
            alert("请输入需要进行操作的字符串");
        }else {
            $.ajax({
                type: "post",
                url: "/testApplication/crypt/lotdecom",
                data: data,
                dataType:"text",
                success: function (data) {
                    $("#output").empty();
                    $("#output").append("<p>您刚才进行了lot参数解压缩</p>");
                    $("#output").append("<p>"+data+"</p>");
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("转换出错，请联系管理员" + errorThrown);
                }
            });
        }
    })
})
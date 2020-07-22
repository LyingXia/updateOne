window.onload = function () {
    var selectFile = document.getElementById("selectFile");
    var uploadFile = document.getElementById("uploadFile");
    uploadFile.onclick = function(){
        var file = selectFile.files[0];
        //获取文件的filename，文件名
        var filename = selectFile.files[0].name;
        //获取文件的filepath，文件路径
        var filepath = selectFile.value;
        var form = new FormData();
        form.append("file", file);
        $.ajax({
            contentType:"multipart/form-data",
            url:"localhost:8080/testApplication/demo/test/uploadFile",
            type:"POST",
            data:form,
            dataType:"text",
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            success: function(result){
                alert(result);
            }
        });
    }
}
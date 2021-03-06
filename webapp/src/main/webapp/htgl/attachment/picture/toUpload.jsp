<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%>
<html>
<head>
</head>

<body>

<div class="wrap-container clearfix">
    <div class="column-content-detail">
        <div class="layui-upload">
            <button type="button" class="layui-btn layui-btn-normal" id="selectButton">请选择待上传的照片</button>
            <div class="layui-upload-list">
                <table class="layui-table">
                    <thead>
                        <tr>
                            <th>文件名</th>
                            <th>大小</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr></thead>
                    <tbody id="fileList"></tbody>
                </table>
            </div>
            <button type="button" class="layui-btn" id="uploadButton"><i class="layui-icon"></i>开始上传</button>
        </div>
    </div>
</div>

</body>

</html>
<script>
    layui.use('upload', function(){
        var $ = layui.jquery;
        var upload = layui.upload;

        //多文件列表
        var demoListView = $('#fileList');
        var uploadListIns = upload.render({
            elem: '#selectButton',
            url: '<%=ctxPath%>/htgl/attachmentController/uploadPicture',
            accept: 'file',
            exts: 'gif|png|jpg',
            size: 300 ,//限制文件大小，单位 KB
            multiple: true,
            auto: false,
            bindAction: '#uploadButton',
            choose: function(obj){
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                debugger;
                //读取本地文件
                obj.preview(function(index, file, result){
                    debugger;
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    demoListView.append(tr);
                });
            },
            done: function(res, index, upload){
                if(res.code == 0){ //上传成功
                    var tr = demoListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(''); //清空操作
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            },
            error: function(index, upload){
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });
    });
</script>

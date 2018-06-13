<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
    String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/zhb_layer.css" />
<script type="text/javascript" src="<%=ctxPath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/layer/layer.js"></script>


<div align="center">
    <input type="button" value="确 定" id="ajaxId">
</div>

<script type="text/javascript">
    $("#ajaxId").click(function(){
        debugger;
        var str = '<ul class="group-con clearfix">';
        str += '<li class="group-user data-id="0"">';
        str += "00";
        str += '</li>';
        for (var i=1; i<10; i++) {
            str += '<li class="group-user data-id="'+i+'">';
            str += i+""+i;
            str += '</li>';
        }
        str += '</ul>';
        layer.open({
            title: '点击删除联系人',
            type: 1,
            area: '630px',
            content: str, //这里content是一个普通的String
            btn: ['确定', '取消'],
            success: function(layero, index){
                /*$('.layui-layer-content').on('click','.group-user', function () {
                    var _this = $(this),
                        id = _this.attr('data-id'),
                        name = _this.text(),
                        _thisObj = {'flag':'deletgu','value':id,'name':name,'num':1,'dataType':$("#dataType").val()};
                    if (_this.hasClass('selected')) {
                        _this.removeClass('selected');
                    } else {
                        _this.addClass('selected');
                    }
                });*/
            },
            yes: function(index, layero){
                layer.closeAll();
            },
            btn2: function(index, layero){
                layer.closeAll();
            }
        });
    });

</script>
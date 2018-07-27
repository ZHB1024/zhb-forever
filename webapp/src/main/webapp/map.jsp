<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String ctxPath = request.getContextPath();
%>

<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="http://libs.baidu.com/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=jDGp5syRiVXRSC2cxWjoG6cc6iB5BQ5D"></script>
    <title>浏览器定位</title>
    <style type="text/css">
        html{height:100%}
        body{height:100%;margin:0px;padding:0px}
        #container{height:100%}
    </style>
</head>
<body>
<!-- <div>
    <span id="load_geolocation">点击获取位置</span>
</div> -->

<div id="container">
</div>
</body>
</html>

<script type="text/javascript">
    var map = new BMap.Map("container");          // 创建地图实例
    map.centerAndZoom(new BMap.Point(116.331398,39.897445), 12); // 初始化地图，设置中心点坐标和地图级别
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    map.addControl(new BMap.NavigationControl());
    var opts = {offset: new BMap.Size(150, 5),anchor:BMAP_ANCHOR_BOTTOM_RIGHT}
    map.addControl(new BMap.ScaleControl(opts));
    map.addControl(new BMap.OverviewMapControl());
    map.addControl(new BMap.MapTypeControl());
    map.setCurrentCity("北京"); // 仅当设置城市信息时，MapTypeControl的切换功能才能可用

    /* var mapStyle={  style : "midnight" }
    map.setMapStyle(mapStyle); */

    var myStyleJson=[
        {
            "featureType": "road",
            "elementType": "geometry.stroke",
            "stylers": {
                "color": "#ff0000"
            }
        }];
    map.setMapStyle({styleJson: myStyleJson });

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            alert('您的位置：'+r.point.lng+','+r.point.lat);
        }
        else {
            alert('failed'+this.getStatus());
        }
    });



    /* $(function(){
          $("#load_geolocation").click(function(ev){
              $(ev.currentTarget).text("正在获取位置......");
              //创建百度地图控件
              var geolocation = new BMap.Geolocation();
              geolocation.getCurrentPosition(function(r){
                  debugger
                  if(this.getStatus() == BMAP_STATUS_SUCCESS){
                      //以指定的经度与纬度创建一个坐标点
                      var pt = new BMap.Point(r.point.lng,r.point.lat);
                      //创建一个地理位置解析器
                      var geoc = new BMap.Geocoder();
                      geoc.getLocation(pt, function(rs){//解析格式：城市，区县，街道
                          var addComp = rs.addressComponents;
                          $(ev.currentTarget).text(addComp.city + ", " + addComp.district + ", " + addComp.street);
                      });
                  }
                  else {
                      $(ev.currentTarget).text('定位失败');
                  }
              },{enableHighAccuracy: true})//指示浏览器获取高精度的位置，默认false
          });
    }); */
</script>

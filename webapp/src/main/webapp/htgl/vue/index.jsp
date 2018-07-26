<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%>
<html>
<head>
  <title></title>

    <script type="text/javascript" src="<%=ctxPath%>/js/jquery-3.3.1.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>

    <link rel="stylesheet" type="text/css" href=""/>

</head>

<body>

<div id="app-6">
    <p>{{ message }}</p>
    <input v-model="message">
</div>

<div id="app-7">
    <ol>
        <!--
          现在我们为每个 todo-item 提供 todo 对象
          todo 对象是变量，即其内容可以是动态的。
          我们也需要为每个组件提供一个“key”，稍后再
          作详细解释。
        -->
        <todo-item
            v-for="item in groceryList"
            v-bind:todo="item"
            v-bind:key="item.id">
        </todo-item>
    </ol>
</div>

</body>

</html>

<script type="text/javascript">
    var app6 = new Vue({
        el: '#app-6',
        data: {
            message: 'Hello Vue!'
        }
    });

    Vue.component('todo-item', {
        props: ['todo'],
        template: '<li>{{ todo.text }}</li>'
    })

    var app7 = new Vue({
        el: '#app-7',
        data: {
            groceryList: [
                { id: 0, text: '蔬菜' },
                { id: 1, text: '奶酪' },
                { id: 2, text: '随便其它什么人吃的东西' }
            ]
        }
    })
</script>

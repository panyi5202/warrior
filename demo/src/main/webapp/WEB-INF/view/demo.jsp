<%@ page import="com.warrior.demo.Demo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>demo</title>
</head>
<body>
<%
    Demo demo = (Demo)request.getAttribute("demo");
%>
名称：<%= demo.getName()%>
描述：<%=demo.getDesc()%>
</body>
</html>

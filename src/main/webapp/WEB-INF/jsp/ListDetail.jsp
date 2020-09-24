<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "model.User,java.util.ArrayList,model.Store" %>

<% 
   	User user = (User) session.getAttribute("user");
	ArrayList<Store> storeList  = (ArrayList<Store>) session.getAttribute("storeList");
	String listName = (String) session.getAttribute("listName");
%>        
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> リスト詳細</title>
</head>
<body>
<h3><%=listName %> リスト詳細</h3>

<%for(Store store : storeList){ %>
<a href = "<%= store.getUrl()%>" target = ”_blank”><%= store.getName()%></a><br>
<%} %>
</body>
</html>
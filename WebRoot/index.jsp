<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  		<title>利用filter演示自动登录演示</title>
  </head>
  
  <body>
  	<h2>这是主页</h2>
  	<c:if test="${!empty sessionScope.error }">
  		${sessionScope.error }
  		<c:remove var="error" scope="session"/>
  	</c:if>
  	
  	
  	<c:if test="${empty sessionScope.user }" var="boo">
	  	<form action="<c:url value='/LoginServlet'/>	" method="post">
	  		name:<input type="text" name="name" />
	  		pwd:<input type="text" name="pwd" /><br/>
	  		自动登录：
	  		<input type="radio" name="time" value="0" checked="checked" />不自动登录<br/>
	  		<input type="radio" name="time" value="1"  />一天内自动登录<br/>
	  		<input type="radio" name="time" value="7"  />七天内自动登录<br/>
	  		<input type="submit" value="登录"/>
	  	</form>
  	</c:if>
  	<c:if test="${!boo }">
  		${user.name }，欢迎你!
  		<a href=" <c:url value='/imgs/6.jpg'/>	">看美女图去啦</a>
  		<a href="<c:url value='/CancleAutoLoginServlet'/>">取消自动登录</a>
  		<!-- 取消自动登录其实就是将cookie的保存设置为0 -->
  		
  	</c:if>
  </body>
</html>

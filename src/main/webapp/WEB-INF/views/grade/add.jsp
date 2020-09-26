<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add New Grade</title>
</head>
<body>

	<h1>Add New Grade</h1>

	<form:form action="${pageContext.request.contextPath }/grade/add"
		modelAttribute="grade">
		<p>Grade : <form:input  path="grade"/></p>
		 <br>

		<input type="submit" name="submit" value="Add Grade">
	</form:form>

</body>
</html>
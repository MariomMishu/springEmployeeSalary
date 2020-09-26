<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Show All Grade</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Grade</th>
			
			<th>Actions</th>
		</tr>
		<c:forEach items="${grades }" var="grade">
			<tr>
				<th>${ grade.id }</th>
				<th>${ grade.grade }</th>
				
				<th><a href="edit?id=${ grade.id }">Edit</a>
				<a href="delete?id=${ grade.id }">Delete</a></th>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
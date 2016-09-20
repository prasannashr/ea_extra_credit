<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tasks</title>
</head>
<body>
	TaskList:
	<table border=1>
		<thead>
			<th>Detail</th>
			<th>End date</th>
			<th>Status</th>
		</thead>
		<tbody>
			<c:forEach items="${taskList}" var="task">
				<tr>
					<td>${task.detail}</td>
					<td>${task.endDate}</td>
					<td>${task.status}</td>
					<td><a href="">Resources</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form:form commandName="task">
		Detail: <form:input path="detail"/><br/>
		EndDate: <form:input path="endDate"/><br/>
		<input type="submit" value="Save" />
	</form:form>
	<a href="projects">Home</a>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>
</head>
<body>
	<h2>Welcome ${pageContext.request.userPrincipal.name}</h2>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div>
			<a href="projects_add">Add project</a>
		</div>
	</sec:authorize>
	
	<table border="1">
		<thead>
			<th>Name</th>
			<th>Description</th>
			<th>Location</th>
			<th>StartDate</th>
			<th>EndDate</th>
			<th>Status</th>
		</thead>
		<tbody>
			<c:forEach items="${projectList}" var="project">
				<tr>
					<td>${project.name}</td>
					<td>${project.description}</td>
					<td>${project.location}</td>
					<td>${project.startDate}</td>
					<td>${project.endDate}</td>
					<td>${project.status}</td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<td><a href="tasks?projectId=${project.id}">Tasks</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
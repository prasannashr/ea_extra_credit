<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Project</title>
</head>
<body>
	<form:form action="projects_add" commandName="project">
		<table>
			<tr>
				<td>Name</td>
				<td><form:input path="name"/></td>
				<td><form:errors path="name"></form:errors>
			</tr>
			<tr>
				<td>Description</td>
				<td><form:input path="description"/></td>
				<td><form:errors path="description"></form:errors>
			</tr>
			<tr>
				<td>Location</td>
				<td><form:input path="location"/></td>
				<td><form:errors path="location"></form:errors>
			</tr>
			<tr>
				<td>Start Date</td>
				<td><form:input path="startDate"/></td>
				<td><form:errors path="startDate"></form:errors>
			</tr>
			<tr>
				<td>End Date</td>
				<td><form:input path="endDate"/></td>
				<td><form:errors path="endDate"></form:errors>
			</tr>
		</table>
		<input type="submit" value="Save" />
	</form:form>
</body>
</html>
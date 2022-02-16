<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Subject</title>
<link rel="stylesheet" type="text/css" href="Css.css">
<style>
table,th,td {
	border: 1px solid black;
}
</style>
</head>
<body>
	<img src="img\baldr.png" class="top_icon">
	<%-- Content Add/Edit logic --%>
	<div class="message_margin">
		<c:if test="${requestScope.error ne null}">
			<strong class="error"><c:out value="${requestScope.error}"></c:out></strong>
		</c:if>
		<c:if test="${requestScope.success ne null}">
			<strong class="success"><c:out value="${requestScope.success}"></c:out></strong>
		</c:if>
		<c:url value="/addContent" var="addURL"></c:url>
		<c:url value="/editContent" var="editURL"></c:url>
	</div>

	<%-- Edit Request --%>
	<c:if test="${requestScope.person ne null}">
		<form action='<c:out value="${editURL}"></c:out>' method="post">
			ID: <input type="text" value="${requestScope.content.id}"
				readonly="readonly" name="id"><br>
			Temp: <input type="text" value="${requestScope.content.temp}" name="temp"><br>
			Group: <input type="text" value="${requestScope.content.group}" name="group"><br>
			<input type="submit" value="Edit Content">
			Time: <input type="text" value="${requestScope.content.time}"
				readonly="" name="id">
		</form>
	</c:if>

	<%-- Add Request --%>
	<c:if test="${requestScope.content eq null}">
		<form action='<c:out value="${addURL}"></c:out>' method="post">
			Temp: <input type="text" name="temp"><br>
			Group: <input type="text" name="group"><br>
			<input type="submit" value="Add Content" class="btm_margin">
		</form>
	</c:if>

	<%-- Contents List Logic --%>
	<c:if test="${not empty requestScope.contents}">
		<table>
			<tbody>
				<tr>
					<th>ID</th>
					<th>Temp</th>
					<th>Group</th>
					<th>Time</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
				<c:forEach items="${requestScope.contents}" var="content">
					<c:url value="/editContent" var="editURL">
						<c:param name="id" value="${content.id}"></c:param>
					</c:url>
					<c:url value="/deleteContent" var="deleteURL">
						<c:param name="id" value="${content.id}"></c:param>
					</c:url>
					<tr>
						<td><c:out value="${content.id}"></c:out></td>
						<td><c:out value="${content.temp}"></c:out></td>
						<td><c:out value="${content.group}"></c:out></td>
						<td><c:out value="${content.time}"></c:out></td>
						<td><a
							href='<c:out value="${editURL}" escapeXml="true"></c:out>'>Edit</a></td>
						<td><a
							href='<c:out value="${deleteURL}" escapeXml="true"></c:out>'>Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>
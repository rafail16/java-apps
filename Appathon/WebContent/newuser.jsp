<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<style>
table, th, td {
  border: 1px solid black;
  border-spacing: 2px;
}
</style>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Signup</title>
<%
	Object name = session.getAttribute("username");
%>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({dateFormat: 'dd/mm/yy'});
	});
</script>
</head>
<body>
	<form method="post" action="newuser">
		<table>
			<tr>
				<td>Username</td>
				<%
					if (name != null) {
				%>
				<td><input type="text" name="username" value="<%=(String)name%>" /></td>
				<%
					} else {
				%>
				<td><input type="text" name="username"/></td>
				<%
					}
				%>
			</tr>
			<tr>
				<td>Name</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Surname</td>
				<td><input type="text" name="surname" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td>Date</td>
				<td><input type="text" id="datepicker" name="date"></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center"><input type="submit" value="Register" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
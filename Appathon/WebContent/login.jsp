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
<title>Login</title>
</head>
<body>
	<h3>Welcome. Login to your profile.</h3>
	<form method="post" action="login">
		<table>
			<tr>
				<td>Username</td>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center"><input type="submit"
					value="Login" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
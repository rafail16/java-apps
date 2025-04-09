<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Home Page</title>
</head>
<style>
.horizontal button {
	padding: 5px 5px;
	float: left;
}
</style>
<body>
	<h1>Welcome to the home page!</h1>
	<form method="post" action="myhomepage">
		<div class="horizontal" style="width: 100%">
			<button style="width: 50%" name="pageupdate">Page Update</button>
			<button style="width: 50%" name="products">Products</button>
		</div>
		<div class="horizontal" style="width: 100%">
			<button style="width: 50%" name="basket">Basket</button>
			<button style="width: 50%" name="logout">Logout</button>
		</div>
	</form>
</body>
</html>
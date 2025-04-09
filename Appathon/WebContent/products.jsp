<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="dao.Operations"%>
<%@ page import="java.util.*"%>
<%@ page import="models.Product"%>

<html>
<style>
table, th, td {
  border: 1px solid black;
  border-spacing: 4px;
}

h1 {
	display: table;
	width: 100%;
	margin: 0;
}

h1>span {
	text-align: left;
	display: table-cell;
}

h1>a {
	text-align: right;
	display: table-cell;
}

.tooltip {
	position: relative;
	display: inline-block;
}

.tooltip .tooltiptext {
	visibility: hidden;
	width: 140px;
	background-color: black;
	color: #fff;
	text-align: center;
	border-radius: 4px;
	padding: 5px 0;
	/* Position the tooltip */
	position: absolute;
	z-index: 1;
	top: -5px;
	left: 120%;
}

.tooltip:hover .tooltiptext {
	visibility: visible;
}
</style>

<head>
<title>Products</title>
</head>
<body>
	<form method="post" action="products">
		<h1>
			<span>Products</span> <a><button name="basket">Basket</button></a>
		</h1>
		<table style="width: 100%;">
			<tr>
				<th width="25%">Image</th>
				<th width="20%">Name</th>
				<th width="15%">Price</th>
				<th width="15%">Quantity</th>
				<th width="15%">To Cart</th>
			</tr>
			<%
				try {
				List<Product> products = new ArrayList<Product>();
				Operations operations = new Operations();
				products = operations.selectProducts();
				for (int i = 0; i < products.size(); i++) {
					String id = products.get(i).getId();
					String name = products.get(i).getName();
					String image = products.get(i).getImage();
					String price = products.get(i).getPrice();
					double number = Math.round(Double.parseDouble(price)*124);
					String vax = String.valueOf(number/100);
			%>
			<tr>
				<td style="text-align: center"><img
					src="images/<%=image%>.jpg" width="100" height="100"></td>
				<td style="text-align: center"><%=name%></td>
				<td style="text-align: center"><div class="tooltip">
						<%=price%> <span class="tooltiptext"><%="with Greece's 24% VAT "+vax%></span>
					</div></td>
				<td style="text-align: center"><input type="text"
					size="1" value="1" name="<%=id%>" /></td>
				<td style="text-align: center"><input type="submit"
					name="<%=name%>" value="Add to Cart" /></td>
			</tr>
			<%
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			%>
		</table>
	</form>
</body>
</html>
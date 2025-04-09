<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>
<%@ page import="models.Product"%>
<%@ page import="models.Country"%>
<%@ page import="dao.Operations"%>
<html>
<style>
table, th, td {
  border: 1px solid black;
  border-spacing: 2px;
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

.normalInput {
	text-align: center;
	pointer-events: none;
	font-weight: bold;
}

.redInput {
	text-align: center;
	pointer-events: none;
	color: red;
	font-weight: bold;
}
</style>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Basket</title>
</head>
<body>
	<form method="post" action="basket">
		<h1>
			<span>Basket</span> <a><button name="conBuy">Continue
					Buying</button></a>
		</h1>
		<table style="width: 100%;">
			<tr>
				<th width="20%">Image</th>
				<th width="20%">Name</th>
				<th width="16%">Price</th>
				<th width="14%">Quantity</th>
				<th width="18%">Total no vat</th>
				<th width="12%">Remove</th>
			</tr>
			<%
				List<Product> cart = new ArrayList<Product>();
			Object objCartBean = session.getAttribute("cart");
			if (objCartBean != null) {
				cart = (ArrayList<Product>) objCartBean;
			} else {
				cart = new ArrayList<Product>();
			}
			double fin = 1.24, sum = 0.0;
			String color = null;
			Object vat = session.getAttribute("vat"), clr = session.getAttribute("color");
			if (vat != null) {
				fin = Double.parseDouble(vat.toString());
			}
			if (clr != null)
				color = "red";
			for (int i = 0; i < cart.size(); i++) {
				double total = Double.parseDouble(cart.get(i).getPrice()) * cart.get(i).getQuantity();
				sum += total;
			%>
			<tr>
				<td style="text-align: center"><img
					src="images/<%=cart.get(i).getImage()%>.jpg" width="100"
					height="100"></td>
				<td style="text-align: center"><%=cart.get(i).getName()%></td>
				<td style="text-align: center"><%=cart.get(i).getPrice()%></td>
				<td style="text-align: center"><%=cart.get(i).getQuantity()%></td>
				<td style="text-align: center"><%=total%></td>
				<td style="text-align: center"><input type="submit"
					name="<%=cart.get(i).getName()%>" value="Delete" /></td>
			</tr>
			<%
				}
			session.setAttribute("sum", sum);
			%>
			<tr>
				<th colspan="3" style="text-align: center">Total Sum</th>
				<%
					if (color == "red") {
					double temp = Math.round(sum * fin * 80);
					temp = temp / 100;
				%>
				<th colspan="3" style="text-align: center"><input type="text"
					name="sums" id="sum" value="<%=temp%>" readonly class="redInput" /></th>
				<%
					} else {
					double temp = Math.round(sum * fin * 100);
					temp = temp / 100;
				%>
				<th colspan="3" style="text-align: center"><input type="text"
					name="sums" id="sum" value="<%=temp%>" readonly class="normalInput" /></th>
				<%
					}
				%>
			</tr>
			<tr>
				<th colspan="2">Enter Voucher Code Here:</th>
				<th colspan="3" style="text-align: center"><input type="text"
					name="voucher" value="voucher"></th>
				<th><input type="submit" name="checkVoucher" value="Check" /></th>
			</tr>
		</table>
	<h3>
		<input type="hidden" id="helping" value="<%=color%>"> <label
			for="country">Choose your Country:</label> <select name="country"
			id="country" onchange="fun()">
			<%
				List<Country> country = new ArrayList<Country>();
			if (session.getAttribute("countries") != null) {
				country = (ArrayList<Country>) session.getAttribute("countries");
			} else {
				Operations op = new Operations();
				country = op.selectCountries();
				session.setAttribute("countries", country);
			}
			fin = Math.round((fin - 1) * 100 * 1000);
			fin = fin / 1000;
			for (int i = 0; i < country.size(); i++) {
				if (fin == country.get(i).getVat()) {
			%>
			<option value=<%=country.get(i).getVat() / 100.0 + 1%> selected><%=country.get(i).getCountry()%></option>
			<%
				} else {
			%>
			<option value=<%=country.get(i).getVat() / 100.0 + 1%>><%=country.get(i).getCountry()%></option>
			<%
				}
			}
			%>
		</select>		
	</h3>
		<input type="submit" name="buy" value="Proceed to Purchase" />
	</form>
	
	<script type="text/javascript">
		function fun() {
			var x = document.getElementById("country").value;
			var y = ${sum};
			if (document.getElementById("helping").value == "red")
				document.getElementById("sum").value = Math.round(y * x * 80) / 100;
			else
				document.getElementById("sum").value = Math.round(y * x * 100) / 100;
		}
	</script>
</body>
</html>
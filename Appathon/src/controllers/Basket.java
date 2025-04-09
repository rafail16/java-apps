package controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

import models.Product;
import dao.Operations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

@WebServlet("/basket")
public class Basket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Basket() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sumVat = request.getParameter("sums");
		String sum = session.getAttribute("sum").toString();
		Object col = session.getAttribute("color");
		double vat = 0, noVat = 0, total = 0;
		if (sumVat != null) {
			vat = Double.parseDouble(sumVat);
			total = vat;
			noVat = Double.parseDouble(sum);
			if (col != null)
				vat = vat / 0.8;
			if (vat != 0) {
				vat = Math.round((vat / noVat) * 100000);
				session.setAttribute("vat", vat / 100000);
			}
		}
		if (request.getParameter("conBuy") != null) {
			response.sendRedirect("products.jsp");
		} else if (request.getParameter("checkVoucher") != null) {
			String voucher = request.getParameter("voucher");
			if (voucher.equals("studentdiscount")) {
				session.setAttribute("color", "red");
				response.sendRedirect("basket.jsp");
			} else {
				response.sendRedirect("basket.jsp");
			}
		} else if (request.getParameter("buy") != null) {
			Object user = session.getAttribute("username");
			if (user != null) {
				String username = user.toString();
				if (noVat == 0) {
					JOptionPane.showMessageDialog(null, "You are trying to purchase 0 items.", "No items",
							JOptionPane.WARNING_MESSAGE);
					response.sendRedirect("basket.jsp");
				} else {
					Operations order = new Operations();
					try {
						String folder = getServletContext().getRealPath(File.separator + "orders");
						Path path = Paths.get(folder);
						if (!Files.exists(path)) {
							Files.createDirectory(path);
						}
						int id = order.insertOrder(username, total);
						FileWriter myWriter = new FileWriter(
								folder + File.separator + "order" + String.valueOf(id) + username + ".txt");
						myWriter.write(
								"User: " + username + " succesfully purchased the next products with total cost of: "
										+ String.valueOf(total) + System.lineSeparator());
						Object objCartBean = session.getAttribute("cart");
						List<Product> cart = null;
						if (objCartBean != null) {
							cart = (ArrayList<Product>) objCartBean;
						} else {
							cart = new ArrayList<Product>();
						}
						for (int i = 0; i < cart.size(); i++) {
							Operations element = new Operations();
							element.insertElement(id, Integer.parseInt(cart.get(i).getId()), cart.get(i).getQuantity());
							if (cart.get(i).getQuantity() == 1)
								myWriter.write(String.valueOf(cart.get(i).getQuantity()) + " copy");
							else
								myWriter.write(String.valueOf(cart.get(i).getQuantity()) + " copies");
							myWriter.write(" of product with id: " + cart.get(i).getId() + System.lineSeparator());
						}
						myWriter.close();
						System.out.println("Successfully wrote to the file.");
						Enumeration<String> names = session.getAttributeNames();
						while (names.hasMoreElements()) {
							String attrname = (String) names.nextElement();
							if (attrname != "username") {
								session.removeAttribute(attrname);
							}
						}
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					response.sendRedirect("myhomepage.jsp");
				}
			} else {
				Object[] options = { "Login", "New User", "Stay Here" };
				int option = JOptionPane.showOptionDialog(null, "You need to login before purchasing", "NOT CONNECTED",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				if (option == 0)
					response.sendRedirect("login.jsp");
				else if (option == 1)
					response.sendRedirect("newuser.jsp");
				else
					response.sendRedirect("basket.jsp");
			}
		} else {
			Object objCartBean = session.getAttribute("cart");
			List<Product> cart = null;
			if (objCartBean != null) {
				cart = (ArrayList<Product>) objCartBean;
			} else {
				cart = new ArrayList<Product>();
			}
			for (int i = 0; i < cart.size(); i++) {
				if (request.getParameter(cart.get(i).getName()) != null)
					cart.remove(i);
			}
			session.setAttribute("cart", cart);
			response.sendRedirect("basket.jsp");
		}
	}
}

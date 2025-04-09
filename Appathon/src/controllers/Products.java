package controllers;

import java.io.IOException;
import java.util.*;

import models.Product;
import dao.Operations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/products")
public class Products extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Products() {
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
		if (request.getParameter("basket") != null) {
			response.sendRedirect("basket.jsp");
		} else {
			try {
				boolean found = false;
				HttpSession session = request.getSession();
				List<Product> cart = null;
				List<Product> products = new ArrayList<Product>();
				Operations operation = new Operations();
				products = operation.selectProducts();
				for (int i = 0; i < products.size(); i++) {
					String id = products.get(i).getId();
					String name = products.get(i).getName();
					String image = products.get(i).getImage();
					String price = products.get(i).getPrice();
					if (request.getParameter(name) != null) {
						int quantity = Integer.parseInt(request.getParameter(id));
						Object objCartBean = session.getAttribute("cart");
						if (objCartBean != null) {
							cart = (ArrayList<Product>) objCartBean;
						} else {
							cart = new ArrayList<Product>();
						}
						for (int j = 0; j< cart.size(); j++) {
							if (Integer.parseInt(cart.get(j).getId()) == Integer.parseInt(id)) {
								cart.get(j).setQuantity(quantity+cart.get(j).getQuantity());
								quantity = quantity+cart.get(j).getQuantity();
								found = true;
								break;
							}
						}
						if(!found) cart.add(new Product(id, name, image, price, quantity));
						session.setAttribute("cart", cart);
						response.sendRedirect("products.jsp");
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}
}

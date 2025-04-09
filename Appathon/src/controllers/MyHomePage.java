package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/myhomepage")
public class MyHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyHomePage() {
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
		if (request.getParameter("logout") != null) {
			request.setAttribute("message", "Logout");
		    HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("login.jsp");
		} else if (request.getParameter("pageupdate") != null) {
			request.setAttribute("message", "Page Update");
			response.sendRedirect("pageupdate.jsp");
		} else if (request.getParameter("products") != null) {
			request.setAttribute("message", "Products");
			response.sendRedirect("products.jsp");
		} else if (request.getParameter("basket") != null) {
			request.setAttribute("message", "Basket");
			response.sendRedirect("basket.jsp");
		}
	}
}

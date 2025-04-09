package controllers;

import dao.Operations;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

@WebServlet("/pageupdate")
public class PageUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Operations operations;

	public void init() {
		operations = new Operations();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageUpdate() {
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
		String username = (String) session.getAttribute("username");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String date = request.getParameter("date");
		if ((name == "" || surname == "" || date == "") && username!=null) {
			request.setAttribute("message", "Empty Attributes");
			JOptionPane.showMessageDialog(null, "You need to fill the form");
			response.sendRedirect("pageupdate.jsp");
		} else {
			try {
				
				if (operations.updateUser(name, surname, date, username)) {
					JOptionPane.showMessageDialog(null, "Your data have been updated");
					response.sendRedirect("myhomepage.jsp");

				} else {
					JOptionPane.showMessageDialog(null, "You need to login first to update data");
					response.sendRedirect("login.jsp");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}

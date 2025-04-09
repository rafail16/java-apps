package controllers;

import models.User;
import dao.Operations;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
/**
 * Servlet implementation class session
 */

@WebServlet("/newuser")
public class NewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Operations operations;
	private User user;

	public void init() {
		operations = new Operations();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewUser() {
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
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String date = request.getParameter("date");
		String password = request.getParameter("password");
		user = new User(username, name, surname, date, password);
		if (username == "" || name == "" || surname == "" || date == "" || password == "") {
			request.setAttribute("message", "Empty Attributes");
			JOptionPane.showMessageDialog(null, "You need to fill the form");
			response.sendRedirect("newuser.jsp");
		}
		else {
			try {
				if (!operations.existingUser(username)) {
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
					try {
						operations.insertUser(user);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					response.sendRedirect("myhomepage.jsp");

				} else {
					request.setAttribute("message", "Username already exists");
					JOptionPane.showMessageDialog(null, "Username already exists");
					response.sendRedirect("newuser.jsp");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}

}


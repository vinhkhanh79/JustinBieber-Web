package edu.poly.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.poly.dao.UserDAO;
import edu.poly.entity.Users;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet({
	"/profile/index",
	"/profile/change-name",
	"/profile/change-password",
	"/profile/change-email"
})
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO dao = new UserDAO();

    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("profile", true);
		request.getRequestDispatcher("/views/user/user.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		request.setAttribute("profile", true);
		if(uri.contains("/profile/change-name")) {
			changeName(request, response);
		}
		else if(uri.contains("/profile/change-password")) {
			changePassword(request, response);
		}
		else if(uri.contains("/profile/change-email")) {
			changeEmail(request, response);
		}
		request.getRequestDispatcher("/views/user/user.jsp").forward(request, response);
	}
	
	protected boolean checkPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Users user = (Users) request.getSession().getAttribute("user");
		return (user.getPassword().equals(request.getParameter("password")))?true:false;
	}
	
	protected void changeName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		if(name.equals("") || password.equals(""))
			request.setAttribute("error_changeName", "Data empty!");
		else {
			if(!checkPassword(request, response))
				request.setAttribute("error_changeName", "Password invalid!");
			else {
				Users user = (Users) request.getSession().getAttribute("user");
				user.setFullname(name);
				dao.update(user);
				request.setAttribute("success_changeName", "Change success!");
			}
		}
	}
	
	protected void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oldPass = request.getParameter("password");
		String newPass = request.getParameter("newPass");
		if(oldPass.equals("") || newPass.equals(""))
			request.setAttribute("error_changePassword", "Data empty!");
		else {
			if(!checkPassword(request, response))
				request.setAttribute("error_changePassword", "Password invalid!");
			else {
				Users user = (Users) request.getSession().getAttribute("user");
				user.setPassword(newPass);
				dao.update(user);
				request.setAttribute("success_changePassword", "Change success!");
			}
		}
	}
	
	protected void changeEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if(email.equals("") || password.equals(""))
			request.setAttribute("error_changeEmail", "Data empty!");
		else if(!email.matches("\\w+@\\w+(\\.\\w+){1,2}"))
			request.setAttribute("error_changeName", "Email invalid!");
		else {
			if(!checkPassword(request, response))
				request.setAttribute("error_changeEmail", "Password invalid!");
			else {
				Users user = (Users) request.getSession().getAttribute("user");
				user.setEmail(email);
				dao.update(user);
				request.setAttribute("success_changeEmail", "Change success!");
			}
		}
	}
}

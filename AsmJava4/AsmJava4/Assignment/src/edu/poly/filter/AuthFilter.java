package edu.poly.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.poly.entity.Users;

@WebFilter({
	"/admin/*",
	"/profile/*",
	"/favorite/*",
	"/like",
	"/share",
	"/unlike",
	"/watch-product/comment"
})
public class AuthFilter implements HttpFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String uri = req.getRequestURI();
		Users user = (Users) req.getSession().getAttribute("user");
		String error = "";
		if(user == null) {
			error = resp.encodeURL("Vui lòng đăng nhập!");
			resp.sendRedirect("/Assignment/login/index?error="+error);
		}
		else if(!user.getAdmin() && uri.contains("/admin/")){
			error = resp.encodeURL("Vui lòng đăng nhập với vai trò Admin!");
			resp.sendRedirect("/Assignment/login/index?error="+error);
		}
		else {
			chain.doFilter(req, resp);
		}
	}

}

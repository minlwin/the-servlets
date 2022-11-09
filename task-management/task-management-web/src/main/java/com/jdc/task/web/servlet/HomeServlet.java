package com.jdc.task.web.servlet;

import java.io.IOException;

import com.jdc.task.model.dto.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/member/home", loadOnStartup = 1)
public class HomeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getSession().getAttribute("loginUser") instanceof Account login) {
			getServletContext().getRequestDispatcher("/views/home-%s.jsp".formatted(login.role().name().toLowerCase()))
				.forward(req, resp);
			return;
		}
		
		req.logout();
		resp.sendRedirect(getServletContext().getContextPath());
	}

}

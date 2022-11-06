package com.jdc.task.web.servlet;

import java.io.IOException;
import java.util.List;

import com.jdc.task.model.dao.AccountDao;
import com.jdc.task.model.db.AccountDb;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/login", "/logout" }, loadOnStartup = 1)
public class SecurityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AccountDao accountDao;

	@Override
	public void init() throws ServletException {
		accountDao = new AccountDb();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			if ("/login".equals(req.getServletPath())) {
				var username = req.getParameter("username");
				var password = req.getParameter("password");

				req.login(username, password);

				req.setAttribute("loginUser", accountDao.findByEmail(username));
			} else {
				req.logout();
			}
			resp.sendRedirect(req.getServletContext().getContextPath());
		} catch (Exception e) {
			req.setAttribute("errors", List.of("Please check Login Information."));
			getServletContext().getRequestDispatcher("/views/login.jsp").forward(req, resp);
		}
	}
}

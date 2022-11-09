package com.jdc.task.web.servlet;

import java.io.IOException;

import com.jdc.task.model.TaskAppException;
import com.jdc.task.model.dao.AccountDao;
import com.jdc.task.model.db.AccountDb;
import com.jdc.task.model.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/member/password", loadOnStartup = 1)
public class PasswordServlet extends HttpServlet{
	
	private AccountDao accountDao;
	
	@Override
	public void init() throws ServletException {
		accountDao = new AccountDb();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/views/change-password.jsp")
			.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var memberId = StringUtils.parseInt(req.getParameter("memberId"));
		var oldPass = req.getParameter("oldPass");
		var newPass = req.getParameter("newPass");
		
		try {
			accountDao.changePass(memberId, oldPass, newPass);
			resp.sendRedirect(getServletContext().getContextPath().concat("/member/home"));
		} catch (TaskAppException e) {
			req.setAttribute("errors", e.getMessages());
			getServletContext().getRequestDispatcher("/views/change-password.jsp").forward(req, resp);
		}
		
	}
}

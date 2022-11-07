package com.jdc.task.web.servlet;

import java.io.IOException;

import com.jdc.task.model.TaskAppException;
import com.jdc.task.model.dao.AccountDao;
import com.jdc.task.model.db.AccountDb;
import com.jdc.task.model.dto.form.AccountForm;
import com.jdc.task.model.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {
		"/manager/account/edit",
		"/member/account"
}, loadOnStartup = 1)
public class AccountServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private AccountDao accountDao;
	
	@Override
	public void init() throws ServletException {
		accountDao = new AccountDb();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var path = req.getServletPath();
		
		var page = switch (path) {
		case "/manager/account/edit": {
			
			var id = StringUtils.parseInt(req.getParameter("id"));
			if(id > 0) {
				req.setAttribute("dto", accountDao.findById(id));
			}
			
			yield "account-edit";
		}
		case "/member/account": {
			
			var role = req.getParameter("role");
			var name = req.getParameter("name");
			
			var list = accountDao.search(StringUtils.parseRole(role), name);
			req.setAttribute("list", list);
			
			yield "account-list";
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		};
		
		getServletContext().getRequestDispatcher("/views/%s.jsp".formatted(page)).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			var id = req.getParameter("id");
			var name = req.getParameter("name");
			var email = req.getParameter("email");
			var role = req.getParameter("role");
			var password = req.getParameter("password");
			
			if(StringUtils.isNotEmpty(id)) {
				accountDao.update(Integer.parseInt(id), AccountForm.forUpdate(name, StringUtils.parseRole(role), email));
			} else {
				accountDao.create(new AccountForm(name, StringUtils.parseRole(role), email, password));
			}
			
			resp.sendRedirect(req.getContextPath().concat("/member/account"));
			
		} catch (TaskAppException e) {
			req.setAttribute("errors", e.getMessages());
			getServletContext().getRequestDispatcher("/views/account-edit.jsp").forward(req, resp);
		}
	}
}

package com.jdc.task.web.servlet;

import java.io.IOException;

import com.jdc.task.model.TaskAppException;
import com.jdc.task.model.dao.AccountDao;
import com.jdc.task.model.dao.ProjectDao;
import com.jdc.task.model.db.AccountDb;
import com.jdc.task.model.db.ProjectDb;
import com.jdc.task.model.dto.Account.Role;
import com.jdc.task.model.dto.form.ProjectForm;
import com.jdc.task.model.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {
		"/manager/project/edit",
		"/member/project"
}, loadOnStartup = 1)
public class ProjectServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private ProjectDao projectDao;
	private AccountDao accountDao;
	
	@Override
	public void init() throws ServletException {
		accountDao = new AccountDb();
		projectDao = new ProjectDb();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var id = StringUtils.parseInt(req.getParameter("id"));
		var owner = req.getParameter("owner");
		var name = req.getParameter("name");
		var date = StringUtils.parseDate(req.getParameter("date"));
		var finished = StringUtils.parseBoolean(req.getParameter("finished"));
		
		if(id > 0) {
			req.setAttribute("dto", projectDao.findById(id));
		}
		
		var path = req.getServletPath();
		
		var page = switch (path) {
		case "/manager/project/edit": {
			req.setAttribute("managers", accountDao.search(Role.Manager, null));
			yield "project-edit";
		}
		case "/member/project": {
			
			if(id > 0) {
				yield "project-details";
			}
			
			var list = projectDao.search(owner, name, date, finished);
			req.setAttribute("list", list);
			yield "project-list";
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + path);
		};
		
		getServletContext().getRequestDispatcher("/views/%s.jsp".formatted(page)).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			var id = StringUtils.parseInt(req.getParameter("id"));
			var name = req.getParameter("name");
			var owner = StringUtils.parseInt(req.getParameter("owner"));
			var description = req.getParameter("description");
			var start = StringUtils.parseDate(req.getParameter("start"));
			var finished = StringUtils.parseBoolean(req.getParameter("finished"));
			
			var form = new ProjectForm(name, owner, description, start, finished);
			
			if(id == 0) {
				id = projectDao.create(form);
			} else {
				projectDao.update(id, form);
			}
			
			resp.sendRedirect(req.getContextPath().concat("/member/project?id=%d".formatted(id)));
			
		} catch (TaskAppException e) {
			req.setAttribute("errors", e.getMessages());
			getServletContext().getRequestDispatcher("/views/project-edit.jsp").forward(req, resp);
		}
		
	}

}

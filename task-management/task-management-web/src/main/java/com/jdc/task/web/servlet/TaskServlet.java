package com.jdc.task.web.servlet;

import java.io.IOException;

import com.jdc.task.model.TaskAppException;
import com.jdc.task.model.dao.AccountDao;
import com.jdc.task.model.dao.ProjectDao;
import com.jdc.task.model.dao.TaskDao;
import com.jdc.task.model.db.AccountDb;
import com.jdc.task.model.db.ProjectDb;
import com.jdc.task.model.db.TaskDb;
import com.jdc.task.model.dto.Account.Role;
import com.jdc.task.model.dto.form.TaskForm;
import com.jdc.task.model.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {
		"/manager/task/edit",
		"/member/task/edit",
		"/member/task"
}, loadOnStartup = 1)
public class TaskServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private TaskDao taskDao;
	private ProjectDao projectDao;
	private AccountDao accountDao;
	
	@Override
	public void init() throws ServletException {
		taskDao = new TaskDb();
		projectDao = new ProjectDb();
		accountDao = new AccountDb();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var id = StringUtils.parseInt(req.getParameter("id"));
		
		var status = StringUtils.parseStatus(req.getParameter("status"));
		var owner = req.getParameter("owner");
		var from = StringUtils.parseDate(req.getParameter("from"));
		var to = StringUtils.parseDate(req.getParameter("to"));
		
		if(id > 0) {
			req.setAttribute("dto", taskDao.findById(id));
		}
		
		var path = req.getServletPath();
		
		var page = switch (path) {
		case "/manager/task/edit", "/member/task/edit": {
			req.setAttribute("projects", projectDao.search(null, null, null, false));
			req.setAttribute("members", accountDao.search(Role.Member, null));
			yield "task-edit";
		}
		case "/member/task": {
			if(id > 0) {
				yield "task-details";
			}
			req.setAttribute("list", taskDao.search(status, owner, from, to));
			yield "task-list";
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
			var project = StringUtils.parseInt(req.getParameter("project"));
			var owner = StringUtils.parseInt(req.getParameter("owner"));
			var status = StringUtils.parseStatus(req.getParameter("status"));
			var from = StringUtils.parseDate(req.getParameter("from"));
			var to = StringUtils.parseDate(req.getParameter("to"));
			var remark = req.getParameter("remark");
			
			var form = new TaskForm(name, project, owner, from, to, status, remark);
			if(id == 0) {
				id = taskDao.create(form);
			} else {
				taskDao.update(id, form);
			}
			
			resp.sendRedirect(req.getContextPath().concat("/member/task?id=%d".formatted(id)));
			
		} catch (TaskAppException e) {
			req.setAttribute("errors", e.getMessages());
			getServletContext().getRequestDispatcher("/views/project-edit.jsp").forward(req, resp);
		}

	}

}

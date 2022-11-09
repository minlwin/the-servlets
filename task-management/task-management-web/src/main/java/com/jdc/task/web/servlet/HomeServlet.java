package com.jdc.task.web.servlet;

import java.io.IOException;
import java.util.List;

import com.jdc.task.model.dao.ProjectDao;
import com.jdc.task.model.dao.TaskDao;
import com.jdc.task.model.db.ProjectDb;
import com.jdc.task.model.db.TaskDb;
import com.jdc.task.model.dto.Account;
import com.jdc.task.model.dto.Task.Status;
import com.jdc.task.model.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/member/home", loadOnStartup = 1)
public class HomeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private ProjectDao projectDao;
	private TaskDao taskDao;
	
	@Override
	public void init() throws ServletException {
		projectDao = new ProjectDb();
		taskDao = new TaskDb();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getSession().getAttribute("loginUser") instanceof Account login) {
			
			var projectSummary = projectDao.findOwnedProjectSummary(login.id());
			var taskSummary = taskDao.getOwnedTasks(login.id());
			
			req.setAttribute("projectSummary",  projectSummary);
			req.setAttribute("taskSummary", taskSummary);

			var projectItems = projectDao.findOwnedProjectItems(login.id());
			var statusItems = List.of(Status.values());
			
			req.setAttribute("projectItems",  projectItems);
			req.setAttribute("statusItems", statusItems);
			
			var project = StringUtils.parseInt(req.getParameter("project"));
			var status = StringUtils.parseStatus(req.getParameter("status"));
			
			if(project == 0 && !projectItems.isEmpty()) {
				project = projectItems.get(0).id();
			}
			
			if(status == null) {
				status = statusItems.get(0);
			}
			
			req.setAttribute("project",  project);
			req.setAttribute("status",  status);
			
			req.setAttribute("list",  taskDao.findProjectTasksForOwner(project, login.id(), status));

			getServletContext().getRequestDispatcher("/views/home.jsp")
				.forward(req, resp);
			return;
		}
		
		req.logout();
		resp.sendRedirect(getServletContext().getContextPath());
	}

}

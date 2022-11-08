package com.jdc.task.model.dao;

import java.time.LocalDate;
import java.util.List;

import com.jdc.task.model.dto.Task;
import com.jdc.task.model.dto.Task.Status;
import com.jdc.task.model.dto.form.TaskForm;

/**
 * Data Access Object for Task
 * 
 * @author minlwin
 *
 */
public interface TaskDao {

	/**
	 * Insert Task Data
	 * 
	 * @param form Task Data
	 * 
	 * @return Generated Primary Key
	 */
	int create(TaskForm form);
	
	/**
	 * Update Task Data
	 * 
	 * @param id Primary Key
	 * @param form Task data
	 */
	void update(int id, TaskForm form);
	
	/**
	 * Find Task data with Primary Key
	 * 
	 * @param id Primary Key
	 * 
	 * @return Task Data
	 */
	Task findById(int id);
	
	/**
	 * Search task Data
	 * 
	 * @param status Status of Task
	 * @param owner Owner Name (Like Search)
	 * @param from Date From
	 * @param to Date To
	 * 
	 * @return Task Data
	 */
	List<Task> search(Status status, String owner, LocalDate from, LocalDate to);
	
	/**
	 * Find Tasks for specific project
	 * 
	 * @param projectId Project ID
	 * 
	 * @return Task Data
	 */
	List<Task> findProjectTasks(int projectId);
	
	/**
	 * Find Tasks for specific project and specific task owner
	 * 
	 * @param projectId
	 * @param ownerId
	 * @return
	 */
	List<Task> findProjectTasksForOwner(int projectId, int ownerId);
	
}

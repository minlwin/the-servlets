package com.jdc.task.model.dao;

import java.time.LocalDate;
import java.util.List;

import com.jdc.task.model.dto.Project;
import com.jdc.task.model.dto.ProjectItem;
import com.jdc.task.model.dto.SummaryData;
import com.jdc.task.model.dto.form.ProjectForm;

/**
 * Data Access Object for Project Table
 * 
 * @author minlwin
 *
 */
public interface ProjectDao {

	/**
	 * Insert Project Data
	 * 
	 * @param form Project Data
	 * 
	 * @return Generated Primary Key
	 */
	int create(ProjectForm form);
	
	/**
	 * Update Project Data
	 * 
	 * @param id Primary Key
	 * @param form Project Data
	 */
	void update(int id, ProjectForm form);
	
	/**
	 * Find Project by Primary Key
	 * 
	 * @param id Primary Key
	 * 
	 * @return Project Data
	 */
	Project findById(int id);
	
	/**
	 * Search Project Data
	 * 
	 * @param ownerName Like Search with Owner Name
	 * @param projectName Like Search with Project Name
	 * @param date Target Date between Start and End Date
	 * @param finished Finished Flag
	 * 
	 * @return Project Data
	 */
	List<Project> search(String ownerName, String projectName, LocalDate date, Boolean finished);
	
	/**
	 * Find Owned Project for member
	 * 
	 * @param taskOwnerId
	 * @return
	 */
	List<ProjectItem> findOwnedProjectItems(int taskOwnerId);
	
	/**
	 * Find Project summary for members 
	 * 
	 * @param taskOwnerId
	 * @return
	 */
	List<SummaryData> findOwnedProjectSummary(int taskOwnerId);
}

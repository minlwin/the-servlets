<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task | Project Details</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

<c:url var="commoncss" value="/resources/css/application.css"></c:url>
<link rel="stylesheet" href="${ commoncss }" />

</head>
<body>

	<jsp:include page="/includes/navigation.jsp">
		<jsp:param value="project" name="active"/>
	</jsp:include>

	<div class="container mt-4">
		<h3><i class="bi bi-puzzle"></i> Project Details</h3>
		
		<div class="card my-3">
		
			<div class="card-header">
				<h5 class="card-title">Project Information</h5>
			</div>
			
			<div class="card-body">
				<div class="row">
					<div class="col">
						<label class="form-label">Code Name</label>
						<span class="form-control">${ dto.name() }</span>
					</div>
					<div class="col">
						<label class="form-label">Project Manager</label>
						<span class="form-control">${ dto.ownerName() }</span>
					</div>
					<div class="col">
						<label class="form-label">Start Date</label>
						<span class="form-control">${ dto.start() }</span>
					</div>
					<div class="col">
						<label class="form-label">Status</label>
						<span class="form-control">${ dto.finished() ? 'Finish' : 'On Going' }</span>
					</div>
				</div>
				
				<div class="row mt-3">
					<div class="col">
						<label class="form-label">Description</label>
						<div class="form-control">
							${ dto.description() }
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<c:if test="${ loginUser.manager }">
			<div>
				
				<c:url value="/manager/project/edit" var="edit">
					<c:param name="id" value="${ dto.id() }"></c:param>
				</c:url>
				<a href="${ edit }" class="btn btn-primary mr-2">
					<i class="bi bi-pencil"></i> Edit Information
				</a>
				
				<c:url value="/manager/task/edit" var="addTask">
					<c:param name="projectId" value="${ dto.id() }"></c:param>
				</c:url>
				<a href="${ addTask }" class="btn btn-danger">
					<i class="bi bi-plus-lg"></i> Add Task
				</a>
			</div>	
		</c:if>
		
		<c:if test="${ not empty taskList }">
		
			<div class="card mt-3">
				<div class="card-header">
					<h5 class="card-title">Task List</h5>
				</div>
				
				<div class="card-body">
					<table class="table table-strpied">
						<thead>
							<tr>
								<th>ID</th>
								<th>Project</th>
								<th>Task</th>
								<th>Owner</th>
								<th>Start</th>
								<th>End</th>
								<th>Status</th>
								<th>Remark</th>
								<c:if test="${ loginUser.manager }">
									<th></th>
								</c:if>
							</tr>
						</thead>
						<tbody>
						
							<c:forEach items="${ taskList }" var="item">
								
								<tr class="align-middle">
									<td>${ item.id() }</td>
									<td>${ item.projectName() }</td>
									<td>${ item.taskName() }</td>
									<td>${ item.taskOwnerName() }</td>
									<td>${ item.dateFrom() }</td>
									<td>${ item.dateTo() }</td>
									<td>${ item.status() }</td>
									<td>${ item.remark() }</td>
									<c:if test="${ loginUser.manager }">
										<td>
											<c:url value="/manager/task/edit" var="editTask">
												<c:param name="id" value="${ item.id() }"></c:param>
												<c:param name="projectId" value="${ item.projectId() }"></c:param>
											</c:url>
											<a href="${ editTask }" class="btn btn-link">
												<i class="bi bi-pencil"></i>
											</a>
										</td>
									</c:if>
								</tr>
							
							</c:forEach>
						
						</tbody>
					
					</table>
				
				</div>			
			</div>		
		</c:if>
		
	</div>

</body>
</html>
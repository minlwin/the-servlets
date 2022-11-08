<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task | Edit Task</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

<c:url var="commoncss" value="/resources/css/application.css"></c:url>
<link rel="stylesheet" href="${ commoncss }" />

</head>
<body>

	<jsp:include page="/includes/navigation.jsp">
		<jsp:param value="task" name="active"/>
	</jsp:include>

	<div class="container mt-4">
		<h3><i class="bi bi-pencil"></i> ${ empty dto ? 'Add New' : 'Edit' } Task</h3>
		
		<div class="row mt-4">
		
			<!-- Project Information -->
			<div class="col-4">
				<div class="card">
				
					<div class="card-header">
						<h5 class="card-title">Project Information</h5>
					</div>
					<div class="card-body">
					
						<div class="mb-3">
							<label class="form-label">Project Name</label>
							<span class="form-control">${ project.name() }</span>
						</div>
					
						<div class="mb-3">
							<label class="form-label">Project Manager</label>
							<span class="form-control">${ project.ownerName() }</span>
						</div>

						<div class="mb-3">
							<label class="form-label">Project Start Date</label>
							<span class="form-control">${ project.start() }</span>
						</div>

						<div class="mb-3">
							<label class="form-label">Project State</label>
							<span class="form-control">${ project.finished() ? 'Finish' : 'On Going' }</span>
						</div>
					
					</div>
					
				</div>

			</div>

			<!-- Task Edit Form -->
			<div class="col">
			
				<div class="card">
					<div class="card-header">
						<h5>Task Information</h5>
					</div>
					
					<div class="card-body">
						<!-- ID -->
						<input type="hidden" name="id" value="${ empty dto ? 0 : dto.id() }" />
						
						<!-- Project ID -->
						<input type="hidden" name="projectId" value="${ empty project ? 0 : project.id() }" />
						
						<!-- Task Name -->
						<div class="mb-3">
							<label class="form-label">Task Name</label>
							<input type="text" name="name" placeholder="Enter Task Name" class="form-control" 
								value="${ not empty param.name ? param.name : not empty dto.taskName()  ? dto.taskName() : '' }"/>
						</div>
						
						<!-- Task Owner -->
						<div class="mb-3">
							<label class="form-label">Task Owner</label>
							<select name="ownerId" class="form-select">
								<option value="">Select Member</option>
								<c:forEach var="item" items="${ members }">
									<option value="${ item.id() }"
										${ param.ownerId eq item.id() or dto.taskOwnerId() eq item.id() ? 'selected' : '' }>${ item.name() }</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="row mb-3">
							<!-- Task Start Date -->
							<div class="col">
								<label class="form-label">Start Date</label>
								<input type="date" name="from" class="form-control" placeholder="Enter Start Date"
									value="${ not empty param.from ? param.from : not empty dto.dateFrom() ? dto.dateFrom() : '' }" />
							</div>							

							<!-- Task End Date -->
							<div class="col">
								<label class="form-label">End Date</label>
								<input type="date" name="to" class="form-control" placeholder="Enter End Date" 
									value="${ not empty param.to ? param.to : not empty dto.dateTo() ? dto.dateTo() : '' }" />
							</div>							
							
							<!-- Status -->
							<div class="col">
								<label class="form-label">Status</label>
								<select name="status" class="form-select">
									<option value="">Select Status</option>
									<option value="Open" ${ param.status eq 'Open' or dto.status() eq 'Open' ? 'selected' : '' }>Open</option>
									<option value="Started" ${ param.status eq 'Started' or dto.status() eq 'Started' ? 'selected' : '' }>Started</option>
									<option value="Late" ${ param.status eq 'Late' or dto.status() eq 'Late' ? 'selected' : '' }>Late</option>
									<option value="Finished" ${ param.status eq 'Finished' or dto.status() eq 'Finished' ? 'selected' : '' }>Finished</option>
								</select>
							</div>
						
						</div>
						
						<!-- Remark -->
						<div class="mb-3">
							<label class="form-label">Remark</label>
							<textarea name="remark" class="form-control" cols="30" rows="4">${ not empty param.remark ? param.remark : not empty dto.remark() ? dto.remark() : '' }</textarea>
						</div>

						<c:if test="${ not empty errors }">
							<ul class="alert alert-warning">
								<c:forEach var="error" items="${ errors }">
									<li>${ error }</li>
								</c:forEach>
							</ul>
						</c:if>

						<div class="mt-2">
							<button class="btn btn-primary">
								<i class="bi bi-save"></i> Save
							</button>
						</div>

					</div>
				</div>
				
			</div>

		</div>
	</div>


</body>
</html>
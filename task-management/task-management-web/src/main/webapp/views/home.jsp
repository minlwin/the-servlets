<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta charset="UTF-8">
<title>Task | Manager Home</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

<c:url var="commoncss" value="/resources/css/application.css"></c:url>
<link rel="stylesheet" href="${ commoncss }" />

<c:url var="homeJs" value="/resources/js/manager-home.js"></c:url>
<script src="${ homeJs }"></script>

</head>

<body>

	<jsp:include page="/includes/navigation.jsp">
		<jsp:param value="home" name="active"/>
	</jsp:include>

	<div class="container mt-3">
		
		<h3 class="mb-3"><i class="bi bi-house"></i> Member Home</h3>
		
		<div class="row">
			
			<!-- Member Profile -->
			<div class="col-3">
				<div class="card mb-3">
					<div class="card-header">
						<i class="bi bi-person"></i> Profile
					</div>
					
					<div class="card-body">
						<app:spaceBetween label="Name" value="${ loginUser.name() }"></app:spaceBetween>						
						<app:spaceBetween label="Role" value="${ loginUser.role() }"></app:spaceBetween>						
						<app:spaceBetween label="Email" value="${ loginUser.email() }"></app:spaceBetween>						
						<app:spaceBetween label="Entry Date" value="${ loginUser.entryDate() }"></app:spaceBetween>						
					</div>
				</div>
				
				<div class="card mb-3">
					<div class="card-header">
						<i class="bi bi-puzzle"></i> My Projects
					</div>
					
					<div class="card-body">
						<c:forEach var="item" items="${ projectSummary }">
							<app:spaceBetween label="${ item.type() }" value="${ item.value() }"></app:spaceBetween>						
						</c:forEach>
					</div>
				</div>

				<div class="card">
					<div class="card-header">
						<i class="bi bi-list"></i> My Tasks
					</div>
					
					<div class="card-body">
						<c:forEach var="item" items="${ taskSummary }">
							<app:spaceBetween label="${ item.type() }" value="${ item.value() }"></app:spaceBetween>						
						</c:forEach>
					</div>
				</div>
			</div>
			
			<!-- Search Tasks -->
			<div class="col">
				
				<div class="card mb-3">
					<div class="card-header">
						<i class="bi bi-search"></i> Search Tasks
					</div>
					
					<div class="card-body">
						<!-- Search Form -->
						<form class="row">
							<div class="col-4">
								<label class="form-label">Project</label>
								<select name="project" class="form-select">
									<c:forEach var="prj" items="${ projectItems }">
										<option value="${ prj.id() }" ${ requestScope.project eq prj.id() ? 'selected' : '' }>${ prj.name() }</option>									
									</c:forEach>
								</select>
							</div>
							
							<div class="col-auto">
								<label class="form-label">Status</label>
								<select name="status"  class="form-select">
									<c:forEach var="status" items="${ statusItems }">
										<option value="${ status }" ${ requestScope.status eq status ? 'selected' : '' }>${ status }</option>
									</c:forEach>
								</select>	
							</div>
							
							<div class="col-auto form-btns">
								<button class="btn btn-primary">
									<i class="bi bi-search"></i> Search
								</button>
							</div>
						</form>
					</div>
				</div>
				
				<!-- Task Table -->
				
				<div class="card">
					<div class="card-header">
						<i class="bi bi-list"></i> Search Result
					</div>
					
					<div class="card-body">
						
						<c:choose>
							<c:when test="${ empty list }">
								<h6>There is no data.</h6>
							</c:when>
							
							<c:otherwise>
								<table class="table table-striped">
									
									<thead>
										<tr>
											<th>ID</th>
											<th>Task</th>
											<th>Start</th>
											<th>End</th>
											<th>Status</th>
											<th>Remark</th>
										</tr>
									</thead>
									
									<tbody>
									
										<c:forEach items="${ list }" var="item">
											
											<tr>
												<td>${ item.id() }</td>
												<td>${ item.taskName() }</td>
												<td>${ item.dateFrom() }</td>
												<td>${ item.dateTo() }</td>
												<td>${ item.status() }</td>
												<td>${ item.remark() }</td>
											</tr>
										
										</c:forEach>
									
									</tbody>
									
								</table>							
							</c:otherwise>
						</c:choose>
					
					</div>
				</div>
				
			</div>
		
		</div>
	
	</div>

</body>
</html>
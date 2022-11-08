<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task | Search</title>

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
		<h3><i class="bi bi-list"></i> Task Management</h3>
		
		<form class="row my-3">
			<div class="col-auto">
				<label class="form-label">Status</label>
				<select name="status" class="form-select">
					<option value="">All Status</option>
					<option value="false" ${ param.status eq 'Open'  ? 'selected' : '' }>Open</option>
					<option value="true" ${ param.status eq 'Started' ? 'selected' : '' }>Started</option>
					<option value="true" ${ param.status eq 'Late' ? 'selected' : '' }>Late</option>
					<option value="true" ${ param.status  eq 'Finished' ? 'selected' : '' }>Finished</option>
				</select>
			</div>
			
			<div class="col-auto">
				<label class="form-label">Date From</label>
				<input type="date" class="form-control" name="from" value="${ param.from }" />
			</div>

			<div class="col-auto">
				<label class="form-label">Date To</label>
				<input type="date" class="form-control" name="to" value="${ param.to }" />
			</div>

			<div class="col-4">
				<label class="form-label">Project Name</label>
				<input type="text" class="form-control" name="owner" placeholder="Search Task Owner Name" value="${ param.owner }" />
			</div>
			
			<div class="col-auto form-btns">
				<button class="btn btn-primary">
					<i class="bi bi-search"></i> Search
				</button>
			</div>
		</form>	
		
		<c:if test="${ not empty list }">
			
			<table class="table table-striped bg-light">
				
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
					</tr>
				</thead>
				
				<tbody>
				
					<c:forEach items="${ list }" var="item">
						
						<tr>
							<td>${ item.id() }</td>
							<td>${ item.projectName() }</td>
							<td>${ item.taskName() }</td>
							<td>${ item.taskOwnerName() }</td>
							<td>${ item.dateFrom() }</td>
							<td>${ item.dateTo() }</td>
							<td>${ item.status() }</td>
							<td>${ item.remark() }</td>
						</tr>
					
					</c:forEach>
				
				</tbody>
				
			</table>
		
		</c:if>	
		
	</div>


</body>
</html>
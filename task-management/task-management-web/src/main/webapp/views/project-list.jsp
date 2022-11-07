<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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
		<h3><i class="bi bi-puzzle"></i> Project Management</h3>

		<form class="row my-3">
			<div class="col-auto">
				<label class="form-label">Status</label>
				<select name="finished" class="form-select">
					<option value="">All Status</option>
					<option value="false" ${ param.finished eq 'false'  ? 'selected' : '' }>Running</option>
					<option value="true" ${ param.finished eq 'true' ? 'selected' : '' }>Finished</option>
				</select>
			</div>
			
			<div class="col-auto">
				<label class="form-label">Date From</label>
				<input type="date" class="form-control" name="date" value="${ param.date }" />
			</div>

			<div class="col-4">
				<label class="form-label">Project Name</label>
				<input type="text" class="form-control" name="name" placeholder="Search Member Name" value="${ param.name }" />
			</div>
			
			<div class="col-auto form-btns">
				<button class="btn btn-primary">
					<i class="bi bi-search"></i> Search
				</button>
				
				<c:if test="${ loginUser.manager }">
					<c:url var="addNew" value="/manager/project/edit"></c:url>
					<a href="${ addNew }" class="btn btn-danger me-2">
						<i class="bi bi-plus-lg"></i> Add New			
					</a>
				</c:if>
			</div>
		</form>	
		
		<c:if test="${ not empty list }">
			
			<table class="table table-striped bg-light">
				
				<thead>
					<tr>
						<th>Id</th>
						<th>Name</th>
						<th>Project Manager</th>
						<th>Start Date</th>
						<th>Description</th>
						<th>Status</th>
						<c:if test="${ loginUser.manager }">
							<th></th>
						</c:if>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${ list }" var="item">
						
						<tr class="align-middle">
							<td>${ item.id() }</td>
							<td>${ item.name() }</td>
							<td>${ item.ownerName() }</td>
							<td>${ item.start() }</td>
							<td>${ item.description() }</td>
							<td>${ item.finished() ? 'Finished' : 'On Going' }</td>
							<c:if test="${ loginUser.manager }">
								<td>
									<c:url var="details" value="/member/project">
										<c:param name="id" value="${ item.id() }"></c:param>
									</c:url>
									<a href="${ details }" class="btn btn-link">
										<i class="bi bi-send"></i>
									</a>
								</td>
							</c:if>
						</tr>
					
					</c:forEach>
				</tbody>
				
			</table>
		
		</c:if>	
	</div>

</body>
</html>
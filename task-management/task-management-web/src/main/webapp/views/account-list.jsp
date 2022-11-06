<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
		<jsp:param value="member" name="active"/>
	</jsp:include>
	
	<div class="container mt-4">
		<h3><i class="bi bi-people"></i> Member Management</h3>
		
		<form class="row my-3">
			<div class="col-auto">
				<label class="form-label">Role</label>
				<select name="role" class="form-select">
					<option value="">All Roles</option>
					<option value="Manager" ${ param.role eq 'Manager' ? 'selected' : '' }>Manager</option>
					<option value="Member" ${ param.role eq 'Member' ? 'selected' : '' }>Member</option>
				</select>
			</div>
			
			<div class="col-4">
				<label class="form-label">Member Name</label>
				<input type="text" class="form-control" name="name" placeholder="Search Member Name" value="${ param.name }" />
			</div>
			
			<div class="col-auto form-btns">
				<button class="btn btn-primary">
					<i class="bi bi-search"></i> Search
				</button>
				
				<button class="btn btn-danger me-2">
					<i class="bi bi-plus-lg"></i> Add New			
				</button>
			</div>
		</form>
		
		<c:if test="${ not empty list }">
			<table class="table table-striped bg-light">
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Role</th>
						<th>Email</th>
						<th>Entry Date</th>
						<th></th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="item" items="${ list }">
						<tr>
							<td>${ item.id() }</td>
							<td>${ item.name() }</td>
							<td>${ item.role() }</td>
							<td>${ item.email() }</td>
							<td>${ item.entryDate() }</td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
		</c:if>
		
		
	</div>
	

</body>
</html>
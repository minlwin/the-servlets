<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task | Edit Member</title>

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
		<h3><i class="bi bi-pencil"></i> ${ param.id ? 'Edit' : 'Add New' } Member</h3>
		
		<div class="row mt-4">
		
			<div class="col-8">
			
				<div class="card">
					<div class="card-body">

						<form method="post" >
							<input type="hidden" name="id" value="${ param.id }" />
							
							
							<div class="mb-3">
								<label class="form-label">Name</label>
								<input type="text" class="form-control" placeholder="Enter Name" name="name" value="${ not empty param.name ? param.name : not empty dto.name() ? dto.name() : '' }" />		
							</div>
							
							<div class="mb-3">
								<label class="form-label">Role</label>
								<select name="role" class="form-select">
									<option value="">Select Role</option>
									<option value="Manager" ${ param.role eq 'Manager' or dto.role() eq 'Manager' ? 'selected' : '' }>Manager</option>
									<option value="Member" ${ param.role eq 'Member' or dto.role() eq 'Member' ? 'selected' : '' }>Member</option>
								</select>
							</div>
							
							<div class="mb-3">
								<label class="form-label">Email Address</label>
								<input type="email" class="form-control" placeholder="Enter Email Address" name="email" value="${ not empty param.email ? param.email : not empty dto.email() ? dto.email() : '' }" />		
							</div>
			
							<c:if test="${ empty param.id }">
								<div class="mb-3">
									<label class="form-label">Password</label>
									<input type="password" class="form-control" placeholder="Enter Password" name="password" />		
								</div>
							</c:if>
							
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
						</form>
					
					</div>
				</div>
			
			</div>
		</div>
		
	</div>

</body>
</html>
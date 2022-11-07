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
		<h3><i class="bi bi-pencil"></i> ${ empty dto ? 'Add New' : 'Edit' } Project</h3>
		
		<div class="row mt-4">
			<div class="col-8">
				<div class="card">
					<div class="card-body">
						
						<form method="post">
							
							<input type="hidden" name="id" value="${ dto.id() }" />
							
							<!-- Project Name -->
							<div class="mb-3">
								<label class="form-label">Project Name</label>
								<input type="text" name="name" placeholder="Enter Project Name" class="form-control"
									value="${ not empty param.name ? param.name : not empty dto.name() ? dto.name() : '' }" />
							</div>
							
							<!-- Project Manager -->
							<div class="mb-3">
								<label class="form-label">Project Manager</label>
								<select name="owner" class="form-select">
									<option value="0">Select PM</option>
									<c:forEach items="${ managers }" var="item">
										<option value="${ item.id() }"
											${ param.owner eq item.id() or dto.ownerId() eq item.id() ? 'selected' : '' } >${ item.name() }</option>
									</c:forEach>
								</select>
							</div>
							
							<!-- Start Date -->
							<div class="mb-3">
								<label class="form-label">Start Date</label>
								<input type="date" name="start" placeholder="Enter Start Date" class="form-control"
									value="${ not empty param.start ? param.start : not empty dto.start() ? dto.start() : '' }" />
							</div>
							
							<!-- Status -->
							<div class="mb-3">
								<label class="form-label">Select Status</label>
								<select name="finished" class="form-select">
									<option value="false" ${ param.finished eq 'false' or not dto.finished() ? 'selected' : '' }>On Going</option>
									<option value="true" ${ param.finished eq 'true' or dto.finished() ? 'selected' : '' }>Finished</option>
								</select>
							</div>
							
							<!-- Description -->
							<div class="mb-3">
								<label class="form-label">Description</label>
								<textarea name="description" class="form-control" cols="30" rows="4">${ not empty param.description ? param.description : not empty dto.description() ? dto.description() : '' }</textarea>
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
						</form>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	

</body>
</html>
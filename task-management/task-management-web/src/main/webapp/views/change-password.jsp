<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Task | Change Password</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

<c:url var="commoncss" value="/resources/css/application.css"></c:url>
<link rel="stylesheet" href="${ commoncss }" />

</head>

<body>

	<jsp:include page="/includes/navigation.jsp">
		<jsp:param value="home" name="active"/>
	</jsp:include>
	
	<div class="container mt-4">
	
		<div class="row">
			<div class="col-4">
				<form method="post" >
					<h3>Change Password</h3>
					
					<input type="hidden" name="memberId" value="${ loginUser.id() }" />			
				
					<div class="mb-2">
						<label class="form-label">Old Password</label>
						<input class="form-control" type="password" placeholder="Enter Old Password" name="oldPass" required="required" />
					</div>
					
					<div class="mb-2">
						<label class="form-label">Old Password</label>
						<input class="form-control" type="password" placeholder="Enter Old Password" name="newPass" required="required" />
					</div>
		
					<c:if test="${ not empty errors }">
						<ul class="alert alert-warning">
							<c:forEach var="error" items="${ errors }">
								<li>${ error }</li>
							</c:forEach>
						</ul>
					</c:if>
		
					<div class="mt-4">
						<button type="submit" class="btn btn-outline-primary px-4">
							<i class="bi bi-gear"></i> Change Password
						</button>
					</div>
			
				</form>					
			</div>
		</div>
	
	</div>		


</body>
</html>
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

	<jsp:include page="/includes/navigation.jsp"></jsp:include>


	<div class="container mt-4">
		<h3>Member Login</h3>
		
		<c:url value="/login" var="login"></c:url>
		<form class="loginForm" action="${ login }" method="post" >
			
			<div class="mb-2">
				<label class="form-label">Email</label>
				<input class="form-control" type="email" placeholder="Enter Email" name="username" required="required" />
			</div>
		
			<div class="mb-2">
				<label class="form-label">Password</label>
				<input class="form-control" type="password" placeholder="Enter Password" name="password" required="required" />
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
					<i class="bi bi-key me-2"></i> Login
				</button>
			</div>
	
		</form>
	</div>

</body>
</html>
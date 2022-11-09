<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="navbar navbar-expand navbar-dark bg-primary">
	
	<div class="container">
		<c:url var="home" value="/"></c:url>
		<c:url var="memberHome" value="/member/home"></c:url>
		
		<a href="${ empty pageContext.request.remoteUser ? home : memberHome }" class="navbar-brand">
			<i class="bi bi-stack"></i> Task Manager
		</a>
		
		<ul class="navbar-nav">
			
			<c:choose>
				<c:when test="${ empty pageContext.request.remoteUser }">
					<li class="nav-item">
						<c:url var="login" value="/login"></c:url>
						<a href="${ login }" class="nav-link">
							<i class="bi bi-key"></i> Sign In
						</a>
					</li>
				</c:when>	
				
				<c:otherwise>
					<li class="nav-item">
						<c:url var="task" value="/member/task"></c:url>
						<a href="${ task }" class="nav-link ${ param.active eq 'task' ? 'active' : '' }">
							<i class="bi bi-list"></i> Tasks
						</a>
					</li>
					<li class="nav-item">
						<c:url var="project" value="/member/project"></c:url>
						<a href="${ project }" class="nav-link ${ param.active eq 'project' ? 'active' : '' }">
							<i class="bi bi-puzzle"></i> Projects
						</a>
					</li>
					<li class="nav-item">
						<c:url var="member" value="/member/account"></c:url>
						<a href="${ member }" class="nav-link  ${ param.active eq 'member' ? 'active' : '' }">
							<i class="bi bi-people"></i> Members
						</a>
					</li>
					
					<li class="nav-item dropdown">
						<a href="#" class="nav-link dropdown-toggle ${ param.active eq 'home' ? 'active' : '' }" 
							role="button" data-bs-toggle="dropdown" aria-expanded="false">
							<i class="bi bi-person"></i> ${ loginUser.name() }
						</a>
						
						<ul class="dropdown-menu">
							<li>
								<c:url value="/member/password" var="changePass"></c:url>
								<a href="${ changePass }" class="dropdown-item">
									<i class="bi bi-gear"></i> Change Password
								</a>
							</li>
							<li>
								<a href="#" id="logoutBtn" class="dropdown-item">
									<i class="bi bi-lock"></i> Sign Out
								</a>
							</li>
						</ul>
					</li>
					
				</c:otherwise>	
			</c:choose>
		</ul>
	</div>
</div>

<c:url var="logout" value="/logout"></c:url>
<form id="logoutForm" action="${ logout }" method="post" class="d-none"></form>

<c:url var="appJs" value="/resources/js/application.js"></c:url>
<script src="${ appJs }"></script>

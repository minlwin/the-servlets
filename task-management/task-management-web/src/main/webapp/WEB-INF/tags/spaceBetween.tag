<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="label" required="true" type="java.lang.String" %>
<%@ attribute name="value" required="true" type="java.lang.String" %>

<div class="d-flex justify-content-between">
	<label>${ label }</label>
	<span>${ value }</span>
</div>
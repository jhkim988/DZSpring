<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>베스트 셀러 목록</h1>
<c:forEach var="item" items="${ goods }">
<div style="border: 1px solid black">
	<c:choose>
		<c:when test="${ item.img != null }">
			<img src=<c:url value="/goods/thumbnail/${ item.img }"/>/>	
		</c:when>
		<c:otherwise>
			<img src=<c:url value="/goods/thumbnail/default"/>/>
		</c:otherwise>
	</c:choose>
	<p>${ item.title }</p>
</div>
</c:forEach>
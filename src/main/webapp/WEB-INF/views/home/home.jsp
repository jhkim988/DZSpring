<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form id="searchForm">
	<label>
		<select id="searchType">
			<option value="title" class="str">제목</option>
			<option value="category" class="str">카테고리</option>
			<option value="author" class="str">저자</option>
		</select>
	</label>
	<input id="searchText" type="text"/>
	<input type="submit" value="검색"/>
</form>

<ul id="suggest">
	<li style="display: none; border: 1px solid black">
		<img src="#"/>
		<p class="title"></p>
		<p class="author"></p>
	</li>
</ul>

<h3>베스트 셀러</h3>
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
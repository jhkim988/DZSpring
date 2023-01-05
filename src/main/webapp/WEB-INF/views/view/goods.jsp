<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>${ goods.title }</h1>
<c:choose>
	<c:when test="${ goods.img != null }">
		<img src=<c:url value="/goods/thumbnail/${ goods.img }"/>/>
	</c:when>
	<c:otherwise>
		<img src=<c:url value="/goods/thumbnail/default"/>/>
	</c:otherwise>
</c:choose>

<p>${ goods.author }</p>
<p>${ goods.publisher }</p>
<p>${ goods.price }</p>
<p>${ goods.publishedAt }</p>
<p>${ goods.totalPage }</p>
<p>${ goods.statusCode }</p>
<p>${ goods.intro }</p>
<p>${ goods.authorIntro }</p>
<p>${ goods.publisherIntro }</p>
<p>${ goods.recommendation }</p>

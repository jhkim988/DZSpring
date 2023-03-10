<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src=<c:url value="/resources/js/common/side.js"/>></script>
<ul>
	<c:choose>
		<c:when test="${member != null}">
			<!-- TODO: Logout JS -->
			<li><button id="logout">로그아웃</button></li>
			<li><a href=<c:url value="/view/member"/>>회원 정보 상세보기</a></li>
			<li><a href=<c:url value="/view/orderList"/>>주문 정보 보기</a></li>
			<li><a href=<c:url value="/view/cart"/>>장바구니</a></li>
			<c:if test="${member.authority == 99}">
				<li>----- 관리자 -----</li>
				<li><a href=<c:url value="/admin/member"/>>회원 관리</a></li>
				<li><a href=<c:url value="/admin/page"/>>페이지 권한 관리</a></li>
				<li><a href=<c:url value="/admin/goods"/>>상품 관리</a></li>
				<li><a href=<c:url value="/admin/order"/>>주문 관리</a>
				<li><a href=<c:url value="/admin/qna"/>>Q&A 관리</a></li>
			</c:if>
		</c:when>
		<c:otherwise>
			<li><a href=<c:url value='/form/loginForm'/>>로그인</a></li>		
		</c:otherwise>
	</c:choose>
</ul>

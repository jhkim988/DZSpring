<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul>
	<c:choose>
		<c:when test="${member != null}">
			<!-- TODO: Logout JS -->
			<li><button>로그아웃</button></li>
			<li><a href=<c:url value="/view/member"/>>회원 정보 상세보기</a></li>
			<li>주문 정보 보기</li>
			<li><a href=<c:url value="/view/cart"/>>장바구니</a></li>
			<c:if test="${member.authority == 99}">
				<li><a href=<c:url value="/admin/member"/>>회원 관리</a></li>
				<li><a href=<c:url value="/admin/page"/>>페이지 권한 관리</a></li>
				<li><a href=<c:url value="/admin/goods"/>>상품 관리</a></li>
			</c:if>
		</c:when>
		<c:otherwise>
			<li><a href=<c:url value='/form/loginForm'/>>로그인</a></li>		
		</c:otherwise>
	</c:choose>
</ul>

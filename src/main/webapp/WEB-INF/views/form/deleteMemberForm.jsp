<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test='${member != null}'>
	${member}
		<form>
			<label>
				<input id="pwd" type="password" placeholder="비밀번호">
			</label>
			<input id="deleteButton" type="submit" value="삭제"/>			
		</form>
		<a href=<c:url value="/member/viewMember"/>><button>뒤로 가기</button></a>
	</c:when>
	<c:otherwise>
		<h1>권한 없음</h1>
	</c:otherwise>
</c:choose>
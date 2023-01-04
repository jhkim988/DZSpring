<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<table>
		<tr>
			<td class="key">아이디:</td>
			<td class="value">${member.id}</td>
		</tr>
		<tr>
			<td class="key">이름:</td>
			<td class="value">${member.name}</td>
		</tr>
		<tr>
			<td class="key">이메일:</td>
			<td class="value">${member.email}</td>
		</tr>
		<tr>
			<td class="key">연락처:</td>
			<td class="value">${member.phone}</td>
		</tr>
		<tr>
			<td class="key">회원 가입 일자:</td>
			<td class="value">${member.createdAt}</td>
		</tr>
		<tr>
			<td class="key">최근 활동 일자:</td>
			<td class="value">${member.updatedAt}</td>
		</tr>
		<tr>
			<td class="key">권한:</td>
			<td class="value">${member.authority}</td>
		</tr>
	</table>
	<footer>
		<a href=<c:url value="/form/updateMemberForm"/>>회원 정보 수정</a>
		<a href=<c:url value="/member/deleteMemberForm"/>>회원 탈퇴</a>
		<a href=<c:url value="/"/>>뒤로 가기</a>
	</footer>
</body>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<table>
		<tbody>
			<tr>
				<td>아이디</td>
				<td id="id">${ manage_member.id }</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="text" id="pwd" value='${manage_member.pwd}' /></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" id="name" value='${manage_member.name}' /></td>
			</tr>
			<tr>
				<td>성별</td>
				<td><select id="sex">
						<option value="M">남자</option>
						<option value="F">여자</option>
				</select></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="text" id="email"
					value='${manage_member.email}' /></td>
			</tr>
			<tr>
				<td>연락처</td>
				<td><input type="text" id="phone"
					value='${manage_member.phone}' /></td>
			</tr>
			<tr>
				<td>생일</td>
				<td><input type="date" id="birth"
					value='${manage_member.birth }' /></td>
			</tr>
			<tr>
				<td>회원가입일자</td>
				<td>${ manage_member.createdAt }</td>
			</tr>
			<tr>
				<td>최근활동일자</td>
				<td>${ manage_member.updatedAt }</td>
			</tr>
			<tr>
				<td>권한</td>
				<td><input type="number" id="authority"
					value='${manage_member.authority}' /></td>
			</tr>
			</tr>
		</tbody>
	</table>
	<button id="updateButton">수정</button>
</body>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<table>
		<thead>
			<tr>
				<th>아이디</th>
				<th>비밀번호</th>
				<th>이름</th>
				<th>성별</th>
				<th>이메일</th>
				<th>연락처</th>
				<th>생일</th>
				<th>회원 가입 일자</th>
				<th>최근 활동 일자</th>
				<th>권한 레벨</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td id="id">${ manage_member.id }</td>
				<td id="pwd">${ manage_member.pwd }</td>
				<td id="name">${ manage_member.name }</td>
				<td id="sex">${ manage_member.sex }</td>
				<td id="email">${ manage_member.email }</td>
				<td id="phone">${ manage_member.phone }</td>
				<td id="birth">${ manage_member.birth }</td>
				<td id="createdAt">${ manage_member.createdAt }</td>
				<td id="updatedAt">${ manage_member.updatedAt }</td>
				<td id="authority">${ manage_member.authority }</td>
			</tr>
			<tr>
				<td>${ manage_member.id }</td>
				<td><input type="text" id="pwd" value='${manage_member.pwd}'/></td>
				<td><input type="text" id="name" value='${manage_member.name}'/></td>
				<td>
					<select id="text">
						<option value="M">남자</option>
						<option value="F">여자</option>
					</select>
				<input type="text" id="sex" value='${manage_member.sex}'/></td>
				<td><input type="text" id="email" value='${manage_member.email}'/></td>
				<td><input type="text" id="phone" value='${manage_member.phone}'/></td>
				<td><input type="date" id="birth" value='${manage_member.birth }'/></td>
				<td>${ manage_member.createdAt }</td>
				<td>${ manage_member.updatedAt }</td>
				<td><input type="number" id="authority" value='${manage_member.authority}'/></td>
			</tr>
		</tbody>
	</table>
	<button id="updateButton">수정</button>
</body>
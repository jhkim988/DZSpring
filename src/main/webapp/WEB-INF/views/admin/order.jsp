<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="searchForm">
	<select id="searchType">
		<option value="all">전체보기</option>
		<option value="orderId">주문번호</option>
		<option value="memberId">회원 아이디</option>
	</select>
	<input id="search" type="text" placeholder="검색어"/>
	<input type="submit" value="검색"/>
</form>

<table>
	<thead>
		<tr>
			<th>주문번호</th>
			<th>회원 아이디</th>
			<th>수취인 이름</th>
			<th>수취인 연락처</th>
			<th>배송 주소</th>
			<th>결제 방법</th>
			<th>총 가격</th>
			<th>배송 상태</th>
			<th>주문 날짜</th>
			<th></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr class="order" style="display:none;">
			<td class="id">주문번호</td>
			<td class="memberId">회원 아이디</td>
			<td class="receiverName">수취인 이름</td>
			<td class="receiverPhone">수취인 연락처</td>
			<td class="address">배송 주소</td>
			<td class="payMethod">결제 방법</td>
			<td class="totalPrice">총 가격</td>
			<td class="status">배송 상태</td>
			<td class="createdAt">주문 날짜</td>
			<td class="view"><button class="btn btn-success">상세보기</button></td>
			<td class="update"><button class="btn btn-warning">수정</button></td>
		</tr>
	</tbody>
</table>
<button id="more" class="btn">더 보기</button>
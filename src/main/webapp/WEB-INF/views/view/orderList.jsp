<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input type="hidden" id="memberId" value="${ member.id }"/>
<table>
	<thead>
		<tr>
			<th>주문번호</th>
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
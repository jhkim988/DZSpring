<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table>
	<thead>
		<tr>
			<th></th>
			<th>상품이미지</th>
			<th>제목</th>
			<th>저자</th>
			<th>출판사</th>
			<th>가격</th>
			<th>개수</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="row" items="${ goods }" varStatus="stat">
			<tr>
				<td><input type="checkbox" data-cartId="${ cart[stat.index].id }"></td>
				<td>
					<c:choose>
						<c:when test="${ row.img != null }">
							<img src=<c:url value="/goods/thumbnail/${ row.img }"/>/>	
						</c:when>
						<c:otherwise>
							<img src=<c:url value="/goods/thumbnail/default"/>/>
						</c:otherwise>
					</c:choose>
				</td>
				<td>${ row.title }</td>
				<td>${ row.author }</td>
				<td>${ row.publisher }</td>
				<td>${ row.price }</td>
				<td><input type="number" value="${ cart[stat.index].quantity }"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<input id="receiverName" type="text" placeholder="수취인 이름"/>
<input id="receiverPhone" type="text" placeholder="수취인 연락처"/>
<input id="address" type="text" placeholder="수취인 주소"/>
<input id="payMethod" type="text" placeholder="결제 방법"/>
<button id="insertOrder" class="btn btn-success">결제</button>

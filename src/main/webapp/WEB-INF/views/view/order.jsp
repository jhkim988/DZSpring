<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="orderList">
	<c:forEach var="item" items="${ orderItems }">
		<div class="card mb-3" style="max-width: 1000px;">
			<div class="row g-0">
				<div class="col-md-1">
					<c:choose>
						<c:when test="${ item.stored_name != null }">
							<img src=<c:url value='/goods/thumbnail/${ item.stored_name }'/> class="img-fluid rounded-start" style="max-height: 100%">	
						</c:when>
						<c:otherwise>
							<img src=<c:url value='/goods/thumbnail/default'/> class="img-fluid rounded-start" style="max-height: 100%">	
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-6">
					<div class="card-body">
						<h5 class="card-title title">${ item.title }</h5>
						<p class="card-text info">${ item.author } | ${ item.publisher }</p>
					</div>
				</div>
				<div class="col-md-2">
					<p class="cart-text price">${ item.price }</p>
					<p class="card-text quantity">${ item.quantity }</p>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<div>
	<p>주문자: ${ order.memberId }</p>
	<p>수취인: ${ order.receiverName }</p>
	<p>수취인 연락처: ${ order.receiverPhone }</p>
	<p>주소: ${ order.address }</p>
	<p>결제 방법: ${ order.payMethod }</p>
	<p>총 가격: ${ order.totalPrice }</p>
	<p>주문 상태: ${ order.status }</p>
	<p>주문 날짜: ${ order.createdAt }</p>
</div>

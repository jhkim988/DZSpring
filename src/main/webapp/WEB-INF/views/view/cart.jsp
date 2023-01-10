<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h3>장바구니</h3>
<div id="cartList">
	<div class="card mb-3" style="max-width: 1000px;">
		<div class="row g-0">
			<div class="col-md-1">
				<input type="checkbox" checked/>
			</div>
			<div class="col-md-1">
				<img src="#" class="img-fluid rounded-start" style="max-height: 100%">
			</div>
			<div class="col-md-6">
				<div class="card-body">
					<h5 class="card-title title"></h5>
					<p class="card-text author"></p>
				</div>
			</div>
			<div class="col-md-2">
				<p class="cart-text price"></p>
				<p class="card-text quantity"></p>
			</div>
			<div class="col-md-2">
				<button type="button" class="btn btn-danger delete">삭제</button>
			</div>
		</div>
	</div>
</div>
<div>
	<div id="totalPrice"></div>
	<button class="btn btn-success" id="insertOrder">구매</button>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input type="hidden" id="goodsId" value="${ goods.id }"/>
<h1>${ goods.title }</h1>
<c:choose>
	<c:when test="${ goods.img != null }">
		<img src=<c:url value="/goods/file/${ goods.img }"/> style="width: 100%"/>
	</c:when>
	<c:otherwise>
		<img src=<c:url value="/goods/file/default"/> style="width: 100%"/>
	</c:otherwise>
</c:choose>

<p>${ goods.author }</p>
<p>${ goods.publisher }</p>
<p>${ goods.price }</p>
<p>${ goods.publishedAt }</p>
<p>${ goods.totalPage }</p>
<p>${ goods.statusCode }</p>
<p>${ goods.intro }</p>
<p>${ goods.authorIntro }</p>
<p>${ goods.publisherIntro }</p>
<p>${ goods.recommendation }</p>

<input type="number" id="quantity" placeholder="개수"/>
<button id="insertCart">장바구니 담기</button>
<button id="insertOrder">구매하기</button>

<h5>상품 리뷰</h5>
<div>
	<div>상품 리뷰 댓글 1</div>
	<div>상품 리뷰 댓글 2</div>
	<div>상품 리뷰 댓글 3</div>
	<div>상품 리뷰 댓글 4</div>
</div>
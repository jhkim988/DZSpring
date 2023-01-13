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

<h5>QnA</h5>
<table>
	<thead>
		<tr>
			<th>번호</th>
			<th>유형</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
	</thead>
	<tbody>
		<tr class="qna" style="display:none;">
			<td class="id"></td>
			<td class="type"></td>
			<td class="title"></td>
			<td class="memberId"></td>
			<td class="createdAt"></td>
			<td><button class="updateButton btn btn-warning">수정</button></td>
			<td><button class="deleteButton btn btn-danger">삭제</button></td>
		</tr>
	</tbody>
</table>
<div class="buttonList">
	<button id="first">&lt;&lt;</button>
	<button id="prev">&lt;</button>
	<div id="numBtnList">
		<button data-page="1">1</button>
		<button data-page="2">2</button>
		<button data-page="3">3</button>
		<button data-page="4">4</button>
		<button data-page="5">5</button>
	</div>
	<button id="next">&gt;</button>
	<button id="last">&gt;&gt;</button>
</div>
<form id="qnaForm">
	<input id="title" type="text" placeholder="제목"/>
	<select id="type">
		<option value="제품문의">제품문의</option>
		<option value="배송문의">배송문의</option>
		<option value="기타">기타</option>
	</select>
	<textarea id="content" placeholder="문의글 작성"></textarea>
	<input type="submit" value="등록"/>
</form>

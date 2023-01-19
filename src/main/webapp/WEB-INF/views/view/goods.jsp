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
<form id="qnaForm">
	<input id="form_title" type="text" placeholder="제목"/>
	<select id="form_type">
		<option value="제품문의">제품문의</option>
		<option value="배송문의">배송문의</option>
		<option value="기타">기타</option>
	</select>
	<textarea id="form_content" placeholder="문의글 작성"></textarea>
	<input type="submit" value="등록"/>
</form>
<table>
	<thead>
		<tr>
			<th>번호</th>
			<th>유형</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
	</thead>
	<tbody>
		<tr class="qna" style="display:none;">
			<td class="id"></td>
			<td class="type"></td>
			<td class="title"></td>
			<td class="memberId"></td>
			<td class="createdAt"></td>
		</tr>
	</tbody>
</table>
<div id="buttonWrapper">
    <button class="firstButtonSet">&lt;&lt;</button>
    <button class="prevButtonSet">&lt;</button>
    <span class="pageButtonSet" data-numbutton="5" data-totalcount="100">
        <button class="pageButton" data-page="1">1</button>
        <button class="pageButton" data-page="2">2</button>
        <button class="pageButton" data-page="3">3</button>
        <button class="pageButton" data-page="4">4</button>
        <button class="pageButton" data-page="5">5</button>
    </span>
    <button class="nextButtonSet">&gt;</button>
    <button class="lastButtonSet">&gt;&gt;</button>
</div>
<div id="qnaView" style="display:none;">
	제목: <h5 id="title"></h5>
	유형: <p id="type"></p>
	작성자: <p id="memberId"></p>
	작성일: <p id="createdAt"></p>
	<p id="content"></p>
	<button id="updateFormButton" class="btn btn-warning">수정</button>
	<button id="deleteButton" class="btn btn-danger">삭제</button>
	<div id="answerList">
		<div class="answer">
		<h5>답변</h5>
			제목: <h5 class="title"></h5>
			유형: <p class="type"></p>
			작성자: <p class="memberId"></p>
			작성일: <p class="createdAt"></p>
			<p class="content"></p>
		</div>
	</div>
</div>
<form id="qnaUpdateForm" style="display:none;">
	<input name="title" type="text" placeholder="제목"/>
		<select name="type">
		<option value="제품문의">제품문의</option>
		<option value="배송문의">배송문의</option>
		<option value="기타">기타</option>
	</select>
	<textarea name="content"></textarea>
	<button id="updateButton" class="btn btn-warning">수정</button>
	<button id="cancel" class="btn btn-danger">취소</button>
</form>
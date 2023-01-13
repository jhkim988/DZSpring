<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="qnaSearchForm">
	<select id="searchType">
		<option value="all">전체 검색</option>
		<option value="id">글 번호</option>	
		<option value="goodsId">상품번호</option>
		<option value="type">유형</option>
		<option value="title">제목</option>
		<option value="memberId">회원 아이디</option>
	</select>
	<input type="text" id="value" placeholder="검색어"/>
	<input type="submit" value="검색"/>
</form>
<table>
	<thead>
		<tr>
			<th>번호</th>
			<th>유형</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr class="qna" style="display:none;">
			<td class="id"></td>
			<td class="type"></td>
			<td class="title"></td>
			<td class="memberId"></td>
			<td class="createdAt"></td>
			<td><button class="btn btn-danger deleteButton">삭제</button></td>
		</tr>
	</tbody>
</table>
<button id="more">더 보기</button>
<div id="qnaView" style="display:none;">
	제목: <h5 id="title"></h5>
	유형: <p id="type"></p>
	작성자: <p id="memberId"></p>
	작성일: <p id="createdAt"></p>
	<p id="content"></p>
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
<form id="answerForm" style="display:none;">
	<input id="form_title" type="text" placeholder="제목"/><br/>
	<input id="form_type" value="분류: 답변" readOnly /><br/>
	<textarea id="form_content" placeholder="답변 작성"></textarea><br/>
	<input type="submit" value="등록"/>
</form>
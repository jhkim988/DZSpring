<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="goodsInsertForm">
	<input type="hidden" id="id" value="${ goods.id }"/>
	<input type="text" id="title" placeholder="상품명" value="${ goods.title }"/>
	<input type="text" id="category" placeholder="카테고리" value="${ goods.category }"/>
	<input type="text" id="author" placeholder="저자" value="${ goods.author }"/>
	<input type="text" id="publisher" placeholder="출판사" value="${ goods.publisher}"/>
	<input type="number" id="price" placeholder="가격" value="${ goods.price }"/>
	<input type="date" id="publishedAt" placeholder="출판 날짜" value="${ goods.publishedAt }"/>
	<input type="number" id="totalPage" placeholder="페이지 수" value="${ goods.totalPage }"/>
	<input type="text" id="statusCode" placeholder="상태코드" value="${ goods.statusCode }"/>
	<input type="text" id="intro" placeholder="책 소개" value="${ goods.intro }"/>
	<input type="text" id="authorIntro" placeholder="작가 소개" value="${ goods.authorIntro}"/>
	<input type="text" id="publisherIntro" placeholder="출판사 소개" value="${ goods.publisherIntro }"/>
	<input type="text" id="recommendation" placeholder="추천사" value="${ goods.recommendation }"/>
	<input type="hidden" id="img" name="img" value="${ goods.img }"/>
	<input type="submit" value="수정"/>
</form>
<div id="thumbnailView">
	<c:if test="${ goods.img != null }">
		<img src=<c:url value="/goods/thumbnail/${ goods.img }"/>/>
	</c:if>
</div>
<form id="fileUpload">
	<input type="file" id="file" name="file"/>
</form>

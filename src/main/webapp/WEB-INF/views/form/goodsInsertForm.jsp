<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="goodsInsertForm">
	<input type="text" id="title" placeholder="상품명"/>
	<input type="text" id="category" placeholder="카테고리"/>
	<input type="text" id="author" placeholder="저자"/>
	<input type="text" id="publisher" placeholder="출판사"/>
	<input type="number" id="price" placeholder="가격"/>
	<input type="date" id="publishedAt" placeholder="출판 날짜"/>
	<input type="number" id="totalPage" placeholder="페이지 수"/>
	<input type="text" id="statusCode" placeholder="상태코드"/>
	<input type="text" id="intro" placeholder="책 소개"/>
	<input type="text" id="authorIntro" placeholder="작가 소개"/>
	<input type="text" id="publisherIntro" placeholder="출판사 소개"/>
	<input type="text" id="recommendation" placeholder="추천사"/>
	<input type="hidden" id="img" name="img"/>
	<input type="submit" value="등록"/>
</form>
<div id="thumbnailView"></div>
<form id="fileUpload">
	<input type="file" id="file" name="file"/>
</form>

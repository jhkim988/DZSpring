<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="searchForm">
	<label> <select id="searchType">
			<option value="title" class="str">제목</option>
			<option value="category" class="str">카테고리</option>
			<option value="author" class="str">저자</option>
	</select>
	</label> <input id="searchText" type="text" /> <input type="submit" value="검색" />
</form>

<ul id="suggest">
	<li style="display: none; border: 1px solid black"><img src="#" />
		<p class="title"></p>
		<p class="author"></p></li>
</ul>

<div id="searchResultList">
	<h3 class="searchKeyword"></h3>
	<div class="card-group">
		<div class="card" style="width: 12rem;">
			<img src="#" class="card-img-top" />
			<div class="card-body">
				<h5 class="card-title"></h5>
				<p class="card-text"></p>
				<a href="#" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</div>
</div>
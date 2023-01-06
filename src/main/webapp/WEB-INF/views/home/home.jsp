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

<div id="suggest">
	<div class="card mb-3" style="max-width: 1000px;">
		<div class="row g-0">
			<div class="col-md-4">
				<img src="#" class="img-fluid rounded-start" style="max-height: 100%">
			</div>
			<div class="col-md-8">
				<div class="card-body">
					<h5 class="card-title title"></h5>
					<p class="card-text author"></p>
				</div>
			</div>
		</div>
	</div>
</div>

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
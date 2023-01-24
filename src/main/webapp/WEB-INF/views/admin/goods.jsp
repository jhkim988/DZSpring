<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<a href=<c:url value="/admin/goods"/>><button>상품 검색</button></a>
<a href=<c:url value="/form/admin/insertGoodsForm"/>><button>상품 등록</button></a>
	<form>
		<label>
			<select id="searchType">
				<option value="all" class="str">검색조건</option>
				<option value="category" class="str">카테고리</option>
				<option value="title" class="str">제목</option>
				<option value="author" class="str">저자</option>
				<option value="publisher" class="str">출판사</option>
				<option value="price" class="number">가격</option>
				<option value="publishedAt" class="date">출판일자</option>
				<option value="page" class="number">페이지 수</option>
				<option value="code" class="str">책 상태코드</option>
				<option value="createdAt" class="date">상품등록날짜</option>
			</select>
		</label>
		<label>
			<input id="first" type="text"/>
		</label>
		<label>
			<input id="second" type="hidden"/>
		</label>
		<input type="submit" id="searchButton" value="검색">
	</form>
	<table border="1px solid black">
		<thead>
			<tr>
				<th>섬네일</th>
				<th>아이디</th>
				<th>카테고리</th>
				<th>제목</th>
				<th>저자</th>
				<th>출판사</th>
				<th>가격</th>
				<th>출판일자</th>
				<th>페이지 수</th>
				<th>책 상태코드</th>
				<th>상품등록날짜</th>
				<th>수정</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<tr class="row" style="display:none">
				<td class='thumbnail'><img src=<c:url value="/goods/thumbnail/@{img}"/>/></td>
				<td class='id'>@{id}</td>
				<td class='category'>@{category}</td>
				<td class='title'>@{title}</td>
				<td class='author'>@{author}</td>
				<td class='publisher'>@{publisher}</td>
				<td class='price'>@{price}</td>
				<td class='publishedAt'>@{publishedAt}</td>
				<td class='page'>@{totalPage}</td>
				<td class='code'>@{statusCode}</td>
				<td class='createdAt'>@{createdAt}</td>
				<td class='updateFormButton'><a href=<c:url value='/form/admin/updateGoodsForm/@{id}'/>>수정</a></td>
				<td class='deleteButton'><a href="#" data-id="@{id}">삭제</a></td>
			</tr>
		</tbody>
	</table>
	<button id="more" data-request=''>더 보기</button>
</body>
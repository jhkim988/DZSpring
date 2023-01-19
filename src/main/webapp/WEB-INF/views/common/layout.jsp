<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
	crossorigin="anonymous">
<link rel="stylesheet" href=<c:url value='/resources/'/><tiles:getAsString name="css"/> />
<script type="text/javascript" src=<c:url value='/resources/js/common/TagReplacer.js'/>></script>
<script type="text/javascript" src=<c:url value='/resources/'/><tiles:getAsString name="javascript"/>></script>
</head>
<body>
	<input type="hidden" id="context" value=<c:url value="/"/>>
	<div class="container text-center">
		<div class="row">
			<header class="col">
				<tiles:insertAttribute name="header"></tiles:insertAttribute>
			</header>
		</div>
		<div class="row">
			<main class="col-10">
				<tiles:insertAttribute name="body"></tiles:insertAttribute>
			</main>
			<aside class="col-2">
				<tiles:insertAttribute name="side"></tiles:insertAttribute>
			</aside>
		</div>
		<div class="row">
			<footer>
				<tiles:insertAttribute name="footer"></tiles:insertAttribute>
			</footer>
		</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
<link rel="stylesheet" href=<c:url value='/resources/'/><tiles:getAsString name="css"/>/>
<script type="text/javascript" src=<c:url value='/resources/'/><tiles:getAsString name="javascript"/>></script>
</head>
<body>
	<input type="hidden" id="context" value=<c:url value="/"/>>
	<header id="header" style="width: 1200px">
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
	</header>
	<div style="width: 1200px">
		<tiles:insertAttribute name="search"></tiles:insertAttribute>
	</div>
	<main style="width: 1000px">
		<tiles:insertAttribute name="body"></tiles:insertAttribute>
	</main>
	<aside id="side" style="width: 200px">
		<tiles:insertAttribute name="side"></tiles:insertAttribute>
	</aside>
	<footer id="footer" style="width: 1200px">
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>
	</footer>
</body>
</html>
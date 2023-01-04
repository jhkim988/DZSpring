<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form>
	<section>
		<h2 id="infoTitle">1. 정보 입력</h2>
		<div id="infoInput">
			<label> <input type="text" placeholder="이름" id="name"
				class='required' />
			</label> <label> <input type="text" placeholder="아이디" id="id"
				class='required' data-mapping='markid' />
			</label>
			<mark id="markid"></mark>
			<label> <input type="password" placeholder="비밀번호" id="pwd"
				class='required' data-mapping='markpwd' />
			</label>
			<mark id="markpwd"></mark>

			<label> <input type="password" placeholder="비밀번호 재입력"
				id="pwdchk" class='required' data-mapping='markpwdchk' />
			</label>
			<mark id="markpwdchk"></mark>

			<label> <select id="phonefirst" class='required'
				data-mapping='markphone'>
					<option selected>휴대폰 번호 선택</option>
					<option>010</option>
					<option>011</option>
					<option>0130</option>
					<option>016</option>
					<option>017</option>
					<option>018</option>
					<option>019</option>
			</select> - <input type="number" id="phonemid" class="phone required"
				data-mapping='markphone'> - <input type="number"
				id="phonelast" class="phone required" data-mapping='markphone'>
				<mark id="markphone">휴대폰 번호를 입력해주세요</mark>
			</label> <label> <input id="email" type="text" placeholder="이메일"
				class='required' data-mapping='markemail'> @ <input
				id="emailHost" type="hidden" class='required'
				data-mapping='markemail' /> <select id="emailSelect"
				data-mapping='markemail'>
					<option selected>선택</option>
					<option value="naver.com">naver.com</option>
					<option value="hanmail.net">hanmail.net</option>
					<option value="daum.net">daum.net</option>
					<option value="nate.com">nate.com</option>
					<option value="gmail.com">gmail.com</option>
					<option value="hotmail.com">hotmail.com</option>
					<option value="emailManual">직접입력</option>
			</select>
			</label>
			<mark id="markemail">이메일 주소를 입력해주세요.</mark>
		</div>
		<h2 id="termTitle">2. 약관 동의</h2>
		<div id="termCheck">
			<label> <input type="checkbox" id="termAll" /> 전체 동의
			</label>
			<hr />
			<label> <input type="checkbox" class="term required" />
				DZSpring 이용약관 <small>(필수)</small>
			</label> <label> <input type="checkbox" class="term required" />
				전자금융거래 이용약관 <small>(필수)</small>
			</label> <label> <input type="checkbox" class="term required" /> 개인정보
				수집 및 이용 <small>(필수)</small>
			</label> <label> <input type="checkbox" class="term optional" /> 개인정보
				제3자(판매자) 제공 <small>(선택)</small>
			</label> <label> <input type="checkbox" class="term optional" /> 위치정보
				이용약관 <small>(선택)</small>
			</label> <label> <input type="checkbox" class="term optional" /> 혜택
				알림 이메일, 문자, 앱, 푸시 수신 <small>(선택)</small>
			</label>
		</div>
	</section>
	<footer>
		<input type="submit" class="button" id="register" value="회원가입">
		<button class="button" id="cancel">취소</button>
	</footer>
</form>
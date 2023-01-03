<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a href=<c:url value="/form/findIdForm"/>><button>아이디 찾기</button></a>
<button>비밀번호 재설정</button>

<section id="idCheck">
	<form>
		<p>아이디 확인 후 비밀번호를 재설정 할 수 있습니다.</p>
		<label><input id="id" type="text" placeholder="아이디" /></label> <input
			id="checkId" type="submit" value="확인" />
	</form>
</section>
<section id="changePwd">
	<header>
		<label><input type="checkbox" name="phone" id="phone"
			class="findIdMethod" data-mapping="findIdByPhone"> 휴대폰으로 찾기</label> <label><input
			type="checkbox" name="email" id="email" class="findIdMethod"
			data-mapping="findIdByEmail"> 이메일로 찾기</label> <label><input
			type="checkbox" name="phoneCerti" id="phoneCerti"
			class="findIdMethod" data-mapping="findIdByPhoneCerti"> 휴대폰
			본인인증으로 찾기</label> <label><input type="checkbox" name="ipinCerti"
			id="ipinCerti" class="findIdMethod" data-mapping="findIdByIpinCerti">
			아이핀 본인인증으로 찾기</label>
	</header>
	<form id="findIdByPhone" data-type="phone">
		<label> <input class="first" type="text" placeholder="이름" />
		</label> <label> <input class="second" type="number"
			placeholder="휴대폰 번호(숫자만 입력)" />
		</label> <input class="findPwdSubmit" type="submit" value="비밀번호 찾기" />
	</form>
	<form id="findIdByEmail" data-type="email">
		<label> <input class="first" type="text" placeholder="이름" />
		</label> <label> <input class="second" type="text"
			placeholder="이메일 주소(@포함)" />
		</label> <input class="findPwdSubmit" type="submit" value="비밀번호 찾기" />
	</form>
	<form id="findIdByPhoneCerti" data-type="phoneCerti">
		<p>안전한 통신사 본인인증(PASS)에서 인증합니다. (PASS간편인증 또는 문자인증으로 본인인증 가능)</p>
		<button class="unsupported">비밀번호 찾기</button>
	</form>
	<form id="findIdByIpinCerti" data-type="ipinCerti">
		<p>아이핀 인증이 완료된 후에, 해당 명의(이름)로 가입된 아이디를 찾습니다.</p>
		<button class="unsupported">비밀번호 찾기</button>
	</form>
	<form id="authenticationForm">
		<input id="auth" type="text" placeholder="인증번호"/>
		<input type="submit" value="인증하기"/>
	</form>
</section>
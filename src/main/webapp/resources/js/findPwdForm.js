const main = () => {
	let certiId = null;

	const id = document.querySelector("#id");
	const checkId = document.querySelector("#checkId");
	const checkBoxes = document.querySelector("header");

	checkId.addEventListener('click', async e => {
		e.preventDefault();
		const req = {
			type: "id"
			, value: id.value
		};
		const response = await fetch(`${context.value}member/hasMember`, {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify(req)
		});

		const json = await response.json();
		if (json.data) {
			certiId = id.value;
			checkBoxes.style.display = 'block';
		} else {
			alert(`존재하지 않습니다.`);
		}

	});
	const methodsObj = {
		findIdByPhone
		, findIdByEmail
		, findIdByPhoneCerti
		, findIdByIpinCerti
	}
	const pwdMethods = document.querySelectorAll("#changePwd > header > label > input");
	pwdMethods.forEach(tag => {
		tag.addEventListener("click", e => {
			pwdMethods.forEach(other => {
				other.checked = false
				methodsObj[other.dataset.mapping].style.display = 'none';
			});
			e.target.checked = true;
			methodsObj[e.target.dataset.mapping].style.display = 'block';
		});
	});

	document.querySelectorAll(".findPwdSubmit").forEach(tag => {
		tag.addEventListener("click", async e => {
			e.preventDefault();
			const parent = e.target.parentElement;
			const first = parent.querySelector(".first").value;
			const second = parent.querySelector(".second").value;
			const requestJSON = {
				type: parent.dataset.type
				, id: certiId
				, name: first
				, value: second
			}
			console.log(requestJSON);
			const response = await fetch(`${context.value}member/tryInitPwd`, {
				method: "POST"
				, headers: {
					"Content-Type": "application/json;charset=utf-8"
				}
				, body: JSON.stringify(requestJSON)
			});
			const json = await response.json();
			alert(json.message);
		})
	});

	document.querySelectorAll(".unsupported").forEach(btn => {
		btn.addEventListener("click", e => {
			e.preventDefault();
			alert(`아직 지원하지 않는 기능입니다.`);
		})
	});
	
	authenticationForm.addEventListener("submit", async e => {
		e.preventDefault();
		const response = await fetch(`${context.value}member/initPwdAuth`, {
			method: `POST`
			, headers: {
				'Content-Type': "application/json;charset=utf8"
			}
			, body: JSON.stringify({
				id: certiId
				, auth: auth.value
			})
		});
		const json = await response.json();
		if (json.data.result) {
			alert(`초기화 된 비밀번호는 ${json.data.initPwd} 입니다.`);
		} else {
			alert(`인증번호와 일치하지 않습니다.`)
		}
	});
}

window.onload = main;
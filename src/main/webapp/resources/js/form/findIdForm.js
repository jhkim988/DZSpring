const main = () => {
	const findIdMethod = document.querySelectorAll(".findIdMethod");
	const forms = {
		findIdByPhone: document.querySelector("#findIdByPhone")
		, findIdByEmail: document.querySelector("#findIdByEmail")
		, findIdByPhoneCerti: document.querySelector("#findIdByPhoneCerti")
		, findIdByIpinCerti: document.querySelector("#findIdByIpinCerti")
	}
		
	findIdMethod.forEach(item => {
		item.addEventListener('click', (e) => {
			const target = e.target;
			findIdMethod.forEach(i => {
				i.checked = false;
				const form = forms[i.dataset.mapping];
				form.style.display = 'none';
				form.querySelectorAll('input:not([type=submit])').forEach(x => x.value='');
			});
			target.checked = true;
			forms[target.dataset.mapping].style.display = 'block';
		});
	});
	
	const formArr = document.querySelectorAll('form');
	formArr.forEach(item => {
		item.addEventListener('submit', async e => {
			e.preventDefault();
			const target = e.target;
			const response = await fetch(`${context.value}member/findId`, {
				method: 'POST'
				, headers: {
					'Content-Type': 'application/json;utf-8'
				}
				, body: JSON.stringify({
					method: target.dataset.method
					, name: target.querySelector(".first").value
					, value: target.querySelector(".second").value
				})
			});
			const json = await response.json();
			if (json.data.result) {
				alert(`아이디는 ${json.data.id} 입니다.`);
			} else {
				alert(`일치하는 정보가 없습니다.`);
			}
		});
	});
	
	const unsupporteds = document.querySelectorAll(".unsupported");
	unsupporteds.forEach(item => {
		item.addEventListener('click', e => {
			e.preventDefault();
			alert("아직 지원하지 않는 기능입니다.");	
		});
	});
}

window.onload = main;
const main = () => {
	const getValue = () => ({
		id: id.value
		, pwd: pwd.value
		, remember: remember.checked
	});
	
	loginForm.addEventListener("submit", async e => {
		e.preventDefault();
		const response = await fetch(`${context.value}member/login`, {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify(getValue())
		});	
		const json = await response.json();
		alert(json.message);
		if (response.status == 200) {
			location.href = json.url;
		}
	})
	
}

window.onload = main;
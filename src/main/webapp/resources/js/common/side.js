const side = () => {
	logout.addEventListener("click", async e => {
		const response = await fetch(`${context.value}member/logout`, { method: `POST` });
		const json = await response.json();
		if (json.data) {
			alert(`๋ก๊ทธ์์!`);
			location.href = json.url;
		}
	})
}
window.addEventListener("load", side);
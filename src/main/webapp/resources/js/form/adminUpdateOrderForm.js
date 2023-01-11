const main = () => {
	updateOrderForm.addEventListener("submit", async e => {
		const response = fetch(``, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				
			})
		});
	});
}
window.onload = main;
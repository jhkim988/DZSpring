const main = () => {
	updateOrderForm.addEventListener("submit", async e => {
		e.preventDefault();
		const response = await fetch(`${context.value}order`, {
			method: `PUT`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				id: orderId.value
				, receiverName: receiverName.value
				, receiverPhone: receiverPhone.value
				, address: address.value
				, payMethod: payMethod.value
				, status: document.querySelector("#status").value
			})
		});
		
		const json = await response.json();
		if (json.data) {
			location.href = json.url;
		}
	});
}
window.onload = main;
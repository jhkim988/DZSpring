const main = () => {
	insertOrder.addEventListener("click", async e => {
		const checkedCarts = document.querySelectorAll("input[type='checkbox']:checked");
		const response = await fetch(`${context.value}order`, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				list: Array.prototype.slice.call(checkedCarts).map(node => node.dataset.cartid)
				, receiverName: receiverName.value
				, receiverPhone: receiverPhone.value
				, address: address.value
				, payMethod: payMethod.value
			})
		});
		const json = await response.json();
		if (json.data) {
			alert(`결제 완료`);
			location.href = json.url;
		}
	});
}

window.onload = main;
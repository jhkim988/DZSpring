const main = () => {
	insertCart.addEventListener("click", async e => {
		const response = await fetch(`${context.value}cart`, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				goodsId: goodsId.value
				, quantity: quantity.value
			})
		});
		const json = await response.json();
		if (json.data && confirm(`장바구니로 이동하시겠습니까?`)) {
			location.href = `${json.data.url}`;
		}
	});
}
window.onload = main;
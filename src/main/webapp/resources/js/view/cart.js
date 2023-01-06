const main = async () => {
	const cartItem = cartList.querySelector(".card").cloneNode(true);
	cartList.replaceChildren();
	
	const response = await fetch(`${context.value}cart/`, {
		method: `GET`
		, headers: {
			'Content-Type': 'application/json;charset=utf-8'
		}
	});
	
	const json = await response.json();
	const carts = json.data.cartList;
	const goods = json.data.goodsList;
	for (let i = 0; i < carts.length; i++) {
		const copy = cartItem.cloneNode(true);
		goods[i].img = goods[i].img || `default`;
		copy.querySelector("img").src = `${context.value}goods/thumbnail/${goods[i].img}`;
		copy.querySelector(".title").textContent = goods[i].title;
		copy.querySelector(".author").textContent = goods[i].author;
		copy.querySelector(".quantity").textContent = `수량: ${carts[i].quantity} 개`;
		copy.querySelector(".delete").addEventListener("click", async e => {
			e.preventDefault();
			const response = await fetch(`${context.value}cart/${carts[i].id}`, {
				method: `DELETE`
				, headers: {
					'Content-Type': `application/json;charset=utf-8`
				}
			});
			const json = await response.json();
			if (json.data) {
				cartList.removeChild(copy);
			}
		});
		cartList.appendChild(copy);
	}
}
window.onload = main;
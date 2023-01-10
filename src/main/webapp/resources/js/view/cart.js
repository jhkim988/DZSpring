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
		copy.querySelector("input[type='checkbox']").dataset.id = carts[i].id;
		copy.querySelector("img").src = `${context.value}goods/thumbnail/${goods[i].img}`;
		copy.querySelector(".title").textContent = goods[i].title;
		copy.querySelector(".author").textContent = goods[i].author;
		copy.querySelector(".price").textContent = `${goods[i].price} 원`;
		copy.querySelector(".price").dataset.value = goods[i].price;
		copy.querySelector(".quantity").textContent = `수량: ${carts[i].quantity} 개`;
		copy.querySelector(".quantity").dataset.value = carts[i].quantity;
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
			checkBoxObserver.update();
		});
		cartList.appendChild(copy);
	}
	
	const checkBoxObserver = {
		list: cartList.querySelectorAll(".card")
		, update: () => {
			checkBoxObserver.list = cartList.querySelectorAll(".card");
			let sum = Array.prototype.slice.call(checkBoxObserver.list)
				.filter(node => node.querySelector("input[type='checkbox']").checked)
				.map(node => parseInt(node.querySelector(".price").dataset.value) * parseInt(node.querySelector(".quantity").dataset.value))
				.reduce((a, b) => a+b);
			totalPrice.textContent = `합계: ${sum} 원`
		}
	}
	document.querySelectorAll("input[type='checkbox']").forEach(node => {
		node.addEventListener("change", e => {
			checkBoxObserver.update();
		})
	});
	checkBoxObserver.update();
	
	insertOrder.addEventListener("click", async e => {
		e.preventDefault();
		const response = await fetch(`${context.value}order/cartTo`, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`				
			}
			, body: JSON.stringify({
				list: Array.prototype.slice.call(document.querySelectorAll("input[type='checkbox']:checked"))
											.map(node => node.dataset.id)
			})
		});
		const json = await response.json();
		if (json.data) {
			location.href = json.url;
		}
	});	
}
window.onload = main;
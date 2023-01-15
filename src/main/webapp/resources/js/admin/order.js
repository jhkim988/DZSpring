const main = async () => {
	let type = `all`, value = null, lastId = null;
	const tbody = document.querySelector("tbody");
	const orderRow = document.querySelector("tbody > .order").cloneNode(true);
	tbody.replaceChildren();
	
	const fetchOrder = async (obj) => {
		const urlParam = new URLSearchParams(obj);
		let url = `${context.value}order?${urlParam}`;
		const response = await fetch(url, {	method: `GET` });
		return await response.json();
	}

	const detailOrderEvent = e => {
		const orderId = e.target.parentElement.parentElement.dataset.id;
		location.href = `${context.value}view/order/${orderId}`
	}
	
	const updateOrderEvent = e => {
		const orderId = e.target.parentElement.parentElement.dataset.id;
		location.href = `${context.value}form/admin/updateOrderForm/${orderId}`;
	}

	const makeTag = (origin, data) => {
		const copy = origin.cloneNode(true);
		copy.dataset.id = data.id;
		copy.style.display = "table-row";
		for (let key in data) {
			copy.querySelector(`.${key}`).textContent = data[key];
		}
		copy.querySelector(".view > button").addEventListener("click", detailOrderEvent);
		copy.querySelector(".update > button").addEventListener("click", updateOrderEvent);
		return copy;
	}
	
	const json = await fetchOrder({ type: "all" });
	json.data.forEach(data => tbody.appendChild(makeTag(orderRow, data)));
	lastId = json.data[json.data.length-1].id;

	searchForm.addEventListener("submit", async e => {
		e.preventDefault();
		type = searchType.value;
		value = search.value;
		tbody.replaceChildren();
		const json = await fetchOrder({ type, value });
		json.data.forEach(data => tbody.appendChild(makeTag(orderRow, data)));
		lastId = json.data[json.data.length-1].id;
	});
	
	more.addEventListener("click", async e => {
		const json = await fetchOrder({ type, value, lastId });
		json.data.forEach(data => tbody.appendChild(makeTag(orderRow, data)));
		lastId = json.data[json.data.length-1].id;
	});
}
window.onload = main;
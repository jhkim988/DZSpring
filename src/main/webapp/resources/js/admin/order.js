const main = async () => {
	let type = `all`, value = null;
	const tbody = document.querySelector("tbody");
	const orderRow = document.querySelector("tbody > .order").cloneNode(true);
	const orderItemRow = document.querySelector("tbody > .orderItem").cloneNode(true);
	tbody.replaceChildren();
	
	const fetchOrder = async (obj) => {
		const urlParam = new URLSearchParams(obj);
		let url = `${context.value}order?${urlParam}`;
		const response = await fetch(url, {	method: `GET` });
		return await response.json();
	}

	const makeTag = (origin, data) => {
		const copy = origin.cloneNode(true);
		copy.style.display = "table-row";
		for (let key in data) {
			copy.querySelector(`.${key}`).textContent = data[key];
		}
		return copy;
	}
	
	const json = await fetchOrder({ type: "all" });
	json.data.forEach(data => tbody.appendChild(makeTag(orderRow, data)));

	searchForm.addEventListener("submit", async e => {
		e.preventDefault();
		type = searchType.value;
		value = search.value;
		tbody.replaceChildren();
		const json = await fetchOrder({ type, value });
		json.data.forEach(data => tbody.appendChild(makeTag(orderRow, data)));
	});
	
	more.addEventListener("click", async e => {
		const json = await fetchOrder({ type, value });
		json.data.forEach(data => tbody.appendChild(makeTag(orderRow, data)));
	});
}
window.onload = main;
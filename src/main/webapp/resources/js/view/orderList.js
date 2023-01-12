const main = async () => {
	let lastId = null;
	const tbody = document.querySelector(`tbody`);
	const trTag = document.querySelector(`tbody .order`).cloneNode(true);
	const fetchOrder = async (lastId) => {
		const param = new URLSearchParams({
			type: 'memberId'
			, value: memberId.value
		});
		if (lastId) param.set('lastId', lastId);
		let url = `${context.value}order?${param}`;
		const response = await fetch(url, { method: `GET`});
		const json = await response.json();
		if (json.data.length > 0) lastId = json.data[json.data.length-1].id
		return json;
	}
	
	const moveViewOrder = e => {
		const orderId = e.target.parentElement.parentElement.dataset.orderId;
		location.href = `${context.value}view/order/${orderId}`
	}
	
	const moveUpdateOrder = e => {
		const orderId = e.target.parentElement.parentElement.dataset.orderId;
		location.href = `${context.value}form/orderUpdateForm/${orderId}`
	}
	
	const makeTrTag = data => {
		const copy = trTag.cloneNode(true);
		copy.dataset.orderId = data.id;
		insertInfoIntoTag(copy, data);
		copy.style.display = `table-row`;
		copy.querySelector(".view > button").addEventListener("click", moveViewOrder);
		copy.querySelector(".update > button").addEventListener("click", moveUpdateOrder);
		return copy;
	}
	
	const insertInfoIntoTag = (tag, data) => {
		for (let key in data) {
			if (!tag.querySelector(`.${key}`)) continue;
			tag.querySelector(`.${key}`).textContent = data[key];
		}
	}
	
	const appendTbody = (node) => tbody.appendChild(node);
	
	let json = await fetchOrder();
	json.data.forEach(node => appendTbody(makeTrTag(node)));	

	
	more.addEventListener("click", async e => {
		appendTbody(makeTrTag(await fetchOrder(lastId)));
	});
}
window.onload = main;
const main = () => {
	const tbody = document.querySelector("tbody");
	const firstQuery = document.querySelector("#first");
	const secondQuery = document.querySelector("#second");
	const searchType = document.querySelector("#searchType");
	const more = document.querySelector("button[id=more]");
	const row = tbody.querySelector("tr").cloneNode(true);
	
	let last = null;
	
	searchType.addEventListener("change", e => {
		let target = e.target;
		target = target.querySelector(`option[value=${target.value}]`);
		if (target.getAttribute('class') == 'str') {
			firstQuery.type ="text";
			secondQuery.type = "hidden";
			secondQuery.value = null;
			firstQuery.value = '';
			secondQuery.value = '';
		} else if (target.getAttribute('class') == 'date') {
			firstQuery.type = "date";
			secondQuery.type = "date";
		} else if (target.getAttribute('class') == 'number') {
			firstQuery.type = "number";
			secondQuery.type = "number";			
		} else {
			alert("유효하지 않은 값입니다");
			e.preventDefault();
			return false;
		}
		firstQuery.value = '';
		secondQuery.value = '';
	});
	
	const searchCallback = async e => {
		e.preventDefault();
		tbody.replaceChildren();
		const searchRequestJSON = {
			all: { }
			, category: {
				value: firstQuery.value || null
			}
			, title: {
				value: firstQuery.value || null
			}
			, author: {
				value: firstQuery.value || null
			}
			, publisher: {
				value: firstQuery.value || null
			}
			, price: {
				lo: firstQuery.value || null
				, hi: secondQuery.value || null
			}
			, publishedAt: {
				from: firstQuery.value || null
				, to : secondQuery.value || null
			}
			, page: {
				lo: firstQuery.value || null
				, hi: secondQuery.value || null
			}
			, code: {
				value: firstQuery.value || null
			}
			, createdAt: {
				from: firstQuery.value || null
				, to : secondQuery.value || null
			}
		};
		
		const response = await fetch(`${context.value}goods/search`, {
			method: `POST`
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({
				method: searchType.value
				, value: searchRequestJSON[searchType.value]
			})
		});
		
		if (!response.ok) {
			alert("네트워크 오류");
			return false;
		}
		
		// draw:
		const json = await response.json();
		tbody.replaceChildren();
		json.data.forEach(x => tbody.appendChild(makeTRTag(x)));
		if (json.data.length > 0) {
			last = json.data[json.data.length-1];
		}
	}
	
	const searchButton = document.querySelector("#searchButton");
	searchButton.addEventListener("click", searchCallback);
	
	more.addEventListener("click", async e => {
		const moreRequestBody = {
			all: { lastId: last.id }
			, category: { lastId: last.id, lastCategory: last.category, value: firstQuery.value || null }
			, title: { lastId: last.id, lastTitle: last.title, value: firstQuery.value || null}
			, author: { lastId: last.id, lastAuthor: last.author, value: firstQuery.value || null }
			, publisher: { lastId: last.id, lastPublisher: last.publisher, value: firstQuery.value || null}
			, price: { lastId: last.id, lastPrice: last.price, lo: firstQuery.value || null, hi: secondQuery.value || null}
			, publishedAt: { lastId: last.id, lastPublishedAt: last.publishedAt, from: firstQuery.value || null, to: secondQuery.value || null}
			, page: { lastId: last.id, lastPage: last.page, lo: firstQuery.value || null, hi: secondQuery.value || null }
			, statusCode: { lastId: last.id, lastStatusCode: last.statusCode, value: firstQuery.value || null }
			, createdAt: { lastId: last.id, lastCreatedAt: last.createdAt, from: firstQuery.value || null, to: secondQuery.value || null }
		};
		const response = await fetch(`${context.value}goods/search`, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				method: searchType.value
				, value: moreRequestBody[searchType.value] 
			})
		});
		if (!response.ok) {
			alert("네트워크 오류");
			return false;
		}

		const json = await response.json();
		json.data.forEach(x => tbody.appendChild(makeTRTag(x)));
		if (json.data.length > 0) {
			last = json.data[json.data.length-1];
		}
	});
	
	const deleteGoods = async e => {
		e.preventDefault();
		alert(e.target.dataset.id);
		const response = await fetch(`${context.value}goods/${e.target.dataset.id}`, {
			method: 'DELETE'
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
		});
		const json = await response.json();
		if (json.status) {
			alert(json.message);
			tbody.removeChild(e.target.parentNode.parentNode);
		}
	}
	
	const makeTRTag = goods => {
		const copy = row.cloneNode(true);
		copy.querySelector('.id').textContent = goods.id;
		copy.querySelector('.category').textContent = goods.category;
		copy.querySelector('.title').textContent = goods.title;
		copy.querySelector('.author').textContent = goods.author;
		copy.querySelector('.publisher').textContent = goods.publisher;
		copy.querySelector('.price').textContent = goods.price;
		copy.querySelector('.publishedAt').textContent = goods.publishedAt;
		copy.querySelector('.page').textContent = goods.page;
		copy.querySelector('.code').textContent = goods.code;
		copy.querySelector('.createdAt').textContent = goods.createdAt;
		copy.querySelector('.updateFormButton > a').href = `${context.value}form/goodsUpdateForm/${goods.id}`;
		const deleteATag = copy.querySelector('.deleteButton > a');
		deleteATag.dataset.id = goods.id;
		deleteATag.addEventListener("click", deleteGoods);
		copy.style.display = "table-row";
		return copy;
	}
}

window.onload = main;
const main = () => {
	let prev = null;
	let last = null;
	const suggestList = suggest.querySelector(".card").cloneNode(true);
	searchText.addEventListener("keyup", async e => {
		if (searchText.value == '' || prev == searchText.value) {
			suggest.replaceChildren();
			return;
		}
		const response = await fetch(`${context.value}goods/search`, {
			method: `POST`
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({
				method: searchType.value
				, value: {
					value: searchText.value
				}
			})
		});
		const json = await response.json();
		suggest.replaceChildren();
		json.data.forEach(d => {
			const copy = suggestList.cloneNode(true);
			d.img = d.img || `default`;
			copy.querySelector("img").src = `${context.value}goods/thumbnail/${d.img}`
			copy.querySelector(".title").textContent = d.title;
			copy.querySelector(".author").textContent = d.author;
			copy.style.display = "flex"
			copy.addEventListener("click", () => {
				location.href = `${context.value}view/goods/${d.id}`
			})
			suggest.appendChild(copy);
		});
		prev = searchText.value;
	});
	
	
	const searchKeywordCopy = searchResultList.querySelector(".searchKeyword").cloneNode();
	const cardGroupCopy = searchResultList.querySelector(".card-group").cloneNode();
	const cardCopy = searchResultList.querySelector(".card").cloneNode(true);

	searchForm.addEventListener("submit", async e => {
		e.preventDefault();
		suggest.replaceChildren();
		searchResultList.replaceChildren();
		const response = await fetch(`${context.value}goods/search`, {
			method: `POST`
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({
				method: searchType.value
				, value: {
					value: searchText.value
				}
			})
		});
		const json = await response.json();
		const keyword = searchKeywordCopy.cloneNode();
		keyword.textContent = `검색어: ${searchText.value}`
		searchResultList.appendChild(keyword);
		if (json.data.length > 0) last = json.data[json.data.length-1];
		makeCardGroup(json.data).forEach(card => searchResultList.appendChild(card));
	});
	
	(async () => {
		suggest.replaceChildren();
		searchResultList.replaceChildren();
		const response = await fetch(`${context.value}goods/search`, {
			method: `POST`
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({
				method: `statusCode`
				, value: {
					value: `bestSeller`
				}
			})
		});
		const json = await response.json();
		const keyword = searchKeywordCopy.cloneNode();
		keyword.textContent = `베스트셀러`
		searchResultList.appendChild(keyword);
		makeCardGroup(json.data).forEach(card => searchResultList.appendChild(card));
	})();
	
	const makeCardGroup = (dataArr) => {
		let ret = [];
		let group = cardGroupCopy.cloneNode();
		dataArr.forEach(d => {
			if (group.childElementCount >= 5) {
				ret.push(group);
				group = cardGroupCopy.cloneNode();
			}
			d.img = d.img || `default`;
			const card = cardCopy.cloneNode(true);
			card.querySelector("img").src = `${context.value}goods/thumbnail/${d.img}`
			card.querySelector(".card-title").textContent = d.title;
			card.querySelector(".card-text").textContent = `${d.author} | ${d.publisher} | ${d.price} 원`;
			card.querySelector("a").href = `${context.value}view/goods/${d.id}`
			group.appendChild(card)
		});
		if (group.childElementCount > 0) ret.push(group);
		return ret;
	}
	
	window.addEventListener("scroll", e => {
    	if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 1) {
			console.log("HERE");
    	}
	});
}
window.onload = main;
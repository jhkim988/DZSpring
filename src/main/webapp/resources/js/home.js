const main = async () => {
	const goodsListFetch = async (obj) => {
		const param = new URLSearchParams(obj);
		const response = await fetch(`${context.value}goods/search?${param}`)
		return await response.json();
	}
	
	let isMain = true;
	let prev = null;
	let last = {
		lastId: ''
		, lastCategory: ''
		, lastTitle: ''
		, lastAuthor: ''
		, lastStatusCode: ''
	};
	
	const suggestList = suggest.querySelector(".card").cloneNode(true);
	
	searchText.addEventListener("keyup", async e => {
		if (searchText.value == '' || prev == searchText.value) {
			suggest.replaceChildren();
			return;
		}
		const json = await goodsListFetch({ searchType: searchType.value, value: searchText.value });
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
	
	const init = async () => {
		suggest.replaceChildren();
		searchResultList.replaceChildren();
		const keyword = searchKeywordCopy.cloneNode();
		keyword.textContent = `베스트셀러`
		searchResultList.appendChild(keyword);
	}
	init();
	
	searchForm.addEventListener("submit", async e => {
		e.preventDefault();
		isMain = false;
		suggest.replaceChildren();
		searchResultList.replaceChildren();
		const json = await goodsListFetch({ searchType: searchType.value, value: searchText.value });
		if (json.data.length > 0) {
			let tmp = json.data[json.data.length-1];
			last.lastId = tmp.id;
			last.lastCategory = tmp.category;
			last.lastTitle = tmp.title;
			last.lastAuthor = tmp.author;
			last.lastStatusCode = tmp.statusCode;
		}
		makeCardGroup(json.data).forEach(card => searchResultList.appendChild(card));
	});
	
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
	const scrollEvent = async () => {
    	if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 1) {
			const param = isMain ? { searchType: 'statusCode', value: 'bestseller', ...last } : { searchType: searchType.value, value: searchText.value, ...last }
			const json = await goodsListFetch(param);
    		makeCardGroup(json.data).forEach(card => searchResultList.appendChild(card));		
    		if (json.data.length > 0) {
				let tmp = json.data[json.data.length-1];
				last.lastId = tmp.id;
				last.lastCategory = tmp.category;
				last.lastTitle = tmp.title;
				last.lastAuthor = tmp.author;
				last.lastStatusCode = 'bestseller';
			}
			console.log(json);
    	}
	}
	window.addEventListener("scroll", scrollEvent);
	await scrollEvent();
	
}
window.addEventListener("load", main);
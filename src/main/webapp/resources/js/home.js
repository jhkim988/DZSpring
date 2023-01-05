const main = () => {
	const suggestList = suggest.querySelector("li").cloneNode(true);
	searchText.addEventListener("keyup", async e => {
		if (searchText.value == '') {
			suggest.replaceChild();
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
			copy.style.display = "list-item"
			suggest.appendChild(copy);
		});
	});
	
	searchForm.addEventListener("submit", async e => {
		e.preventDefault();
		
	});
}

window.onload = main;
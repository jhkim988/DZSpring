const main = () => {
	const formParser = nodes => {
		const ret = { };
		[...nodes].forEach(node => {
			ret[node.id] = node.value;
		});
		return ret;
	}
	goodsInsertForm.addEventListener("submit", async e => {
		e.preventDefault();
		const response = await fetch(`${context.value}goods`, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify(formParser(e.target))
		});
		if (!response.ok) {
			alert(`등록 실패`);
			return false;
		}
		
		const json = await response.json();
		if (json.data) {
			location.href = json.url;			
		}
	});
	
	file.addEventListener("change", async e => {
		e.preventDefault();
		const response = await fetch(`${context.value}goods/image`, {
			method: `POST`
			, body: new FormData(fileUpload)
		});

		if (!response.ok) {
			alert("업로드 실패");
			return;
		}
		
		const json = await response.json();
		thumbnailView.replaceChildren();
		thumbnailView.append(imgTag(json.data[0]));
		img.value = json.data[0];
	});
	const imgTag = src => {
		const img = document.createElement("img");
		img.src = `${context.value}goods/thumbnail/${src}`;
		return img;
	}
}
window.onload = main;
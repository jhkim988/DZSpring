const main = async () => {
	const tbody = document.querySelector("tbody");
	const row = tbody.querySelector(".qna").cloneNode(true);
	
	// delete
	const deleteQna = async e => {
		const id = e.target.parentElement.parentElement.dataset.id;
		const response = await fetch(`${context.value}qna/${id}`, { method: `DELETE` });
		const json = await response.json();
		if (json.data) {
			alert(`삭제`);
			tbody.removeChild(e.target.parentElement.parentElement);
		}
	}

	// view qna
	const answer = answerList.querySelector(".answer").cloneNode(true);
	const viewQna = async e => {
		qnaView.style.display = 'inline-block';
		answerForm.style.display = 'inline-block';
		const id = e.currentTarget.dataset.id;
		const goodsId = e.currentTarget.dataset.goodsId;
		const response = await fetch(`${context.value}qna/view/${id}`, { method: `GET`});
		const json = await response.json();
		qnaView.childNodes.forEach(node => {
			if (!json.data.view[node.id]) return;
			node.textContent = json.data.view[node.id];
		});
		answerList.replaceChildren();
		json.data.answers.forEach(dataAns => {
			const copy = answer.cloneNode(true);
			copy.childNodes.forEach(node => {
				if (node.nodeType != Node.ELEMENT_NODE || !dataAns[node.getAttribute("class")]) return;
				node.textContent = dataAns[node.getAttribute("class")];
			});
			answerList.appendChild(copy);
		});
		answerForm.dataset.goodsId = goodsId;
		answerForm.dataset.parentId = id;
	}

	// answer
	answerForm.addEventListener("submit", async e => {
		e.preventDefault();
		const response = await fetch(`${context.value}qna`, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				goodsId: answerForm.dataset.goodsId
				, type: form_type.value
				, title: form_title.value
				, content: form_content.value
				, parentId: answerForm.dataset.parentId
			})
		});
		const json = await response.json();
		if (json.data) {
			alert("답변 등록");
		} else {
			alert("실패");
		}
	});
	
	// search
	const qnaList = async (obj) => {
		const param = new URLSearchParams(obj);
		const response = await fetch(`${context.value}qna/noOffset?${param}`, { method: `GET` });
		const json = await response.json();
		json.data.forEach(data => tbody.appendChild(qnaListDraw(data)));
		if (json.data.length > 0) more.dataset.lastId = json.data[json.data.length-1].id
		return json;
	}
	const qnaListDraw = data => {
		const copy = row.cloneNode(true);
		for (let key in data) {
			if (!copy.querySelector(`.${key}`)) continue;
			copy.querySelector(`.${key}`).textContent = data[key];
		}
		copy.addEventListener("click", viewQna);
		copy.querySelector(".deleteButton").addEventListener("click", deleteQna);
		copy.dataset.id = data.id;
		copy.dataset.goodsId = data.goodsId;
		copy.style.display = "table-row";
		return copy;
	}
	more.addEventListener("click", async e => {
		const lastId = e.target.dataset.lastId;
		await qnaList({ searchType: searchType.value, value: value.value, lastId });
	})
	await qnaList({ searchType: `all` });
	qnaSearchForm.addEventListener("submit", async e => {
		e.preventDefault();
		tbody.replaceChildren();
		await qnaList({ searchType: searchType.value, value: value.value })
	});
}
window.onload = main;
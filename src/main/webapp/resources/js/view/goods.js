const main = async () => {
	// add cart
	insertCart.addEventListener("click", async e => {
		const response = await fetch(`${context.value}cart`, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				goodsId: goodsId.value
				, quantity: quantity.value
			})
		});
		const json = await response.json();
		if (json.data) {
			if (confirm(`장바구니로 이동하시겠습니까?`)) {
				location.href = `${json.data.url}`;				
			}
		} else {
			alert(`일시적 오류`);
		}
	});
	
	// add order
	insertOrder.addEventListener("click", async e => {
		alert(`장바구니에 추가 후 구입화면으로 넘어갑니다.`);
		const response = await fetch(`${context.value}order/goodsViewTo`, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				goodsId: goodsId.value
				, quantity: quantity.value
			})
		});
		const json = await response.json();
		if (json.data) {
			location.href = json.url;
		}
	});
	
	// qna
	const tbody = document.querySelector("tbody");
	
	// Qna 글 조회
	const answer = answerList.querySelector(".answer").cloneNode(true);
	const qnaViewEvent = async e => {
		qnaView.style.display = "inline-block";
		qnaUpdateForm.style.display="none"
		const id = e.currentTarget.dataset.id;
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
				console.log(node.nodeType);
				if (node.nodeType != Node.ELEMENT_NODE || !dataAns[node.getAttribute("class")]) return;
				console.log(node.getAttribute("class"));
				node.textContent = dataAns[node.getAttribute("class")];
			});
			answerList.appendChild(copy);
		});
		qnaView.dataset.id = id;
		qnaUpdateForm.dataset.id = id;
	}
	
	// Qna 수정 Form
	updateFormButton.addEventListener("click",  async e => {
		const id = e.target.parentElement.dataset.id;
		const response = await fetch(`${context.value}qna/view/${id}`, { method: `GET`});
		const json = await response.json();
		qnaUpdateForm.childNodes.forEach(node => {
			console.log(node.name);
			if (!json.data[node.name]) return;
			node.value = json.data[node.name];
		})
		qnaView.style.display="none";
		qnaUpdateForm.style.display = "inline-block";
	});
	
	// Qna 수정
	updateButton.addEventListener("click", async e => {
		e.preventDefault();
		const response = await fetch(`${context.value}qna`, {
			method:`PUT`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				id: qnaUpdateForm.dataset.id
				, title: qnaUpdateForm.querySelector("input[name='title']").value
				, type: qnaUpdateForm.querySelector("select[name='type']").value
				, content: qnaUpdateForm.querySelector("textarea[name='content']").value
			})
		});
		const json = await response.json();
		if (json.data) {
			alert(`수정됐습니다.`);
			cancel.click();
			first.click();
		}
	});
	
	// Qna 삭제
	deleteButton.addEventListener("click", async e => {
		e.preventDefault();
		const id = e.target.parentElement.dataset.id;
		const response = await fetch(`${context.value}qna/${id}`, { method: `DELETE`});
		const json = await response.json();
		if (json.data) {
			alert(`삭제됐습니다.`);
			first.click();
			cancel.click();
		} else {
			alert(`삭제 실패`);
		}
	});
	
	cancel.addEventListener("click", e => {
		e.preventDefault();
		qnaView.style.display="none";
		qnaUpdateForm.style.display="none";
	});
	
	// pagination
	let crnt = 1, lo = 1, hi = 5, maxPage = 0, totalCount = 0;
	const qnaRow = document.querySelector(".qna").cloneNode(true);
	const listQna = async (param) => {
		const urlSearchParam = new URLSearchParams(param);
		let url = `${context.value}qna?${urlSearchParam}`;
		const response = await fetch(url, { method: `GET` });
		const json = await response.json();
		totalCount = json.data.total;
		maxPage = parseInt(Math.ceil(totalCount/5));
		tbody.replaceChildren();
		json.data.list.forEach(data => {
			const trTag = makeTrTag(data);
			tbody.appendChild(trTag);
		});
		return json;
	}
	const makeTrTag = data => {
		const copy = qnaRow.cloneNode(true);
		copy.dataset.id = data.id;
		for (let key in data) {
			if (!copy.querySelector(`.${key}`)) continue;
			copy.querySelector(`.${key}`).textContent = data[key];
		}
		copy.addEventListener("click", qnaViewEvent);
		copy.style.display = `table-row`;
		return copy;
	}
	const changeCurrentButtonColor = () => {
		numBtnList.querySelectorAll("button").forEach(btn => {
			if (btn.dataset.page == crnt) {
				btn.style.color = 'red';
			} else {
				btn.style.color = 'black';
			}
		});
	}
	await listQna({ goodsId: goodsId.value, page: 1 });
	hi = Math.min(hi, maxPage);
	changeCurrentButtonColor();
	
	first.addEventListener("click", async e => {
		await listQna({ goodsId: goodsId.value, page: 1});
		crnt = 1, lo = 1, hi = Math.min(5, maxPage);
		changeBtnSet();
	});
	last.addEventListener("click", async e => {
		await listQna({ goodsId: goodsId.value, page: maxPage});
		crnt = maxPage; hi = maxPage; lo = (Math.ceil(maxPage/5)-1)*5+1;
		changeBtnSet();
	});
	prev.addEventListener("click", async e => {
		if (lo <= 1 || crnt != lo) {
			crnt = lo;
			await listQna({ goodsId: goodsId.value, page: crnt });
		} else {
			crnt = lo-1;
			await listQna({ goodsId: goodsId.value, page: crnt });
			lo -= 5, hi = lo+4;
		}
		changeBtnSet();
	});
	next.addEventListener("click", async e => {
		if (hi >= maxPage || crnt != hi) {
			crnt = hi;
			await listQna({ goodsId: goodsId.value, page: crnt });
		} else {
			crnt = hi+1;
			await listQna({ goodsId: goodsId.value, page: crnt });
			lo += 5, hi = Math.min(lo+4, maxPage);
		}
		changeBtnSet();
	});
	numBtnList.querySelectorAll("button").forEach(btn => {
		btn.addEventListener("click", async e => {
			await listQna({ goodsId: goodsId.value, page: e.target.dataset.page });
			crnt = e.target.dataset.page;
			changeCurrentButtonColor();
		});	
	});
	const changeBtnSet = () => {
		let nIter = 0;
		numBtnList.querySelectorAll("button").forEach(btn => {
			btn.dataset.page = lo + nIter;
			btn.textContent = lo + nIter;
			if (lo + nIter > maxPage) {
				btn.style.display = "none";
			} else {
				btn.style.display = "inline-block";
			}
			nIter++;
		});	
		changeCurrentButtonColor();
	}

	// Q&A 글 작성
	qnaForm.addEventListener("submit", async e => {
		e.preventDefault();
		const response = await fetch(`${context.value}qna`, {
			method: `POST`
			, headers: {
				'Content-Type': `application/json;charset=utf-8`	
			}
			, body: JSON.stringify({
				goodsId: goodsId.value
				, type: form_type.value
				, title: form_title.value
				, content: form_content.value
			})
		});
		const json = await response.json();
		if (json.data) {
			alert(`등록됐습니다.`);
			first.click();
		}
	});
}
window.onload = main;
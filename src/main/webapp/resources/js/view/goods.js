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
	const updateForm = async e => {
		
	}
	const deleteQna = async e => {
		e.preventDefault();
		const id = e.target.parentElement.parentElement.dataset.id;
		const response = await fetch(`${context.value}qna/${id}`, { method: `DELETE`});
		const json = await response.json();
		if (json.data) {
			alert(`삭제됐습니다.`);
			search();
		} else {
			alert(`삭제 실패`);
		}
	}
	
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
		copy.querySelector(".updateButton").addEventListener("click", updateForm);
		copy.querySelector(".deleteButton").addEventListener("click", deleteQna);
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
		if (lo <= 1) return;
		crnt = lo-1;
		await listQna({ goodsId: goodsId.value, page: crnt });
		lo -= 5, hi = lo+4;
		changeBtnSet();
	});
	next.addEventListener("click", async e => {
		console.log(`lo: ${lo}, hi: ${hi}, crnt: ${crnt}, maxPage: ${maxPage}`);
		if (hi >= maxPage) return;
		crnt = hi+1;
		await listQna({ goodsId: goodsId.value, page: crnt });
		lo += 5, hi = Math.min(lo+4, maxPage);
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
				, type: type.value
				, title: title.value
				, content: content.value
			})
		});
		const json = await response.json();
		if (json.data) {
			alert(`등록됐습니다.`);
		}
	});
}
window.onload = main;
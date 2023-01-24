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
				if (node.nodeType != Node.ELEMENT_NODE || !dataAns[node.getAttribute("class")]) return;
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
	const qnaRow = tbody.querySelector(".qna");
	const pageButtonSet = buttonWrapper.querySelector(".pageButtonSet");
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
	
	const changeCurrentButtonColor = (crnt) => {
		pageButtonSet.querySelectorAll("button").forEach(btn => {
			if (btn.dataset.page == crnt) {
				btn.style.color = 'red';
			} else {
				btn.style.color = 'black';
			}
		});
	}
	const fetchAndDraw = async page => {
		const param = new URLSearchParams({ goodsId: goodsId.value, page });
		let url = `${context.value}qna?${param}`;
		const response = await fetch(url, { method: `GET` });
		const json = await response.json();
		tbody.replaceChildren();
		json.data.list.forEach(data => tbody.appendChild(makeTrTag(data)));
		changeCurrentButtonColor(page);
		pageButtonSet.dataset.totalcount = json.data.total;
	}
	fetchAndDraw(1);
	new Pagination(buttonWrapper, fetchAndDraw);
	

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

class Pagination {
    constructor (buttonWrapper, movePageCallback) {
        this.buttonWrapper = buttonWrapper;
        this.movePageCallback = movePageCallback
        this.numButton = parseInt(buttonWrapper.querySelector(".pageButtonSet").dataset.numbutton);
        this.lo = 1;
        this.hi = this.numButton;
        this.crnt = 1;
        this.initButtonWrapper();
    }

    initButtonWrapper() {
        this.buttonWrapper.querySelector(".firstButtonSet").addEventListener("click", async () => {
            this.movePage(1);
            this.moveButtonSet();
        });
        this.buttonWrapper.querySelector(".prevButtonSet").addEventListener("click", async () => {
            this.movePage(this.lo-1);
            this.moveButtonSet();
        });
        this.buttonWrapper.querySelector(".nextButtonSet").addEventListener("click", async () => {
            this.movePage(this.hi+1);
            this.moveButtonSet();
        });
        this.buttonWrapper.querySelector(".lastButtonSet").addEventListener("click", async () => {
            this.movePage(this.getMaxPage());
            this.moveButtonSet();
        });
        this.buttonWrapper.querySelectorAll(".pageButtonSet .pageButton").forEach(btn => {
            btn.addEventListener("click", e => {
                this.movePage(e.target.dataset.page);
            });
        });
    }

    async movePage(page) {
        if (page < 1) {
            this.movePage(1);
            return;
        } else if (page > this.getMaxPage()) {
            this.movePage(this.getMaxPage());
            return;
        }
        this.movePageCallback(page);
        this.crnt = page;
        this.lo = parseInt((page-1)/this.numButton)*this.numButton + 1;
        this.hi = Math.min(this.lo + this.numButton - 1, this.getMaxPage());
    }

    moveButtonSet() {
        let p = this.lo;
        buttonWrapper.querySelectorAll(`.pageButtonSet .pageButton`).forEach(btn => {
            if (p <= this.getMaxPage()) {
                btn.style.display = `inline-block`;
                btn.dataset.page = p;
                btn.textContent = p;
            } else {
                btn.style.display = `none`;
            }
            p++;
        });

    }
    getCurrentPage() { return this.crnt; }
    getMaxPage() {
        this.totalCount = parseInt(buttonWrapper.querySelector(".pageButtonSet").dataset.totalcount);
        return this.maxPage = parseInt(this.totalCount/this.numButton);
    }
}

window.onload = main;
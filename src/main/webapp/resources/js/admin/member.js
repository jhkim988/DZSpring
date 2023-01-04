const main = () => {
	const tbody = document.querySelector("tbody");
	const firstQuery = document.querySelector("#first");
	const secondQuery = document.querySelector("#second");
	const searchType = document.querySelector("#searchType");
	const more = document.querySelector("button[id=more]");
	const row = tbody.querySelector("tr").cloneNode(true);
	
	const last = {
		lastId: ``
		, lastEmail: ``
		, lastPhone: ``
		, lastName: ``
		, lastCreatedAt: ``
		, lastUpdatedAt: ``
	}
	
	searchType.addEventListener("change", e => {
		let target = e.target;
		target = target.querySelector(`option[value=${target.value}]`);
		if (target.getAttribute('class') == 'str') {
			firstQuery.type ="text";
			secondQuery.type = "hidden";
			secondQuery.value = null;
		} else if (target.getAttribute('class') == 'date') {
			firstQuery.type = "date";
			secondQuery.type = "date";
		} else {
			alert("유효하지 않은 값입니다");
			e.preventDefault();
			return false;
		}
	});
	
	const searchCallback = async e => {
		e.preventDefault();
		tbody.replaceChildren();
		const searchRequestJSON = {
			all: { }
			, id: {
				value: firstQuery.value
			}
			, name: {
				value: firstQuery.value
			}
			, email: {
				value: firstQuery.value
			}
			, phone: {
				value: firstQuery.value
			}
			, createdAt: {
				from: `${firstQuery.value} 00:00:00.0`
				, to: `${secondQuery.value} 00:00:00.0`
			}
			, updatedAt: {
				from: `${firstQuery.value} 00:00:00.0`
				,to : `${secondQuery.value} 00:00:00.0`
			}
			, authority: {
				value: firstQuery.value
			}
		};
		
		const response = await fetch(`${context.value}admin/memberSearch`, {
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
			const lastMember = json.data[json.data.length-1];
			last.lastId = lastMember.id;
			last.lastName = lastMember.name;
			last.lastPhone = lastMember.phone;
			last.lastEmail = lastMember.email;
			last.lastCreatedAt = lastMember.createdAt;
			last.lastUpdatedAt = lastMember.updatedAt;
		}
	}
	
	const searchButton = document.querySelector("#searchButton");
	searchButton.addEventListener("click", searchCallback);
	
	more.addEventListener("click", async e => {
		const moreRequestBody = {
			all: { lastId: last.lastId }
			, email: { lastEmail: last.lastEmail, value: firstQuery.value }
			, id: { lastId: last.lastId, value: firstQuery.value }
			, name: { lastName: last.lastName, lastId: last.lastId, value: firstQuery.value }
			, phone: { lastPhone: last.lastPhone, value: firstQuery.value }
			, authority: { lastId: last.lastId, value: firstQuery.value }
			, createdAt: { lastId: last.lastId, lastCreatedAt: last.lastCreatedAt, from: firstQuery.value, to: secondQuery.value }
			, updatedAt: { lastId: last.lastId, lastUpdatedAt: last.lastUpdatedAt, from: firstQuery.value, to: secondQuery.value }
		};
		const response = await fetch(`${context.value}/admin/memberSearch`, {
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
			const lastMember = json.data[json.data.length-1];
			last.lastId = lastMember.id;
			last.lastName = lastMember.name;
			last.lastPhone = lastMember.phone;
			last.lastEmail = lastMember.email;
			last.lastCreatedAt = lastMember.createdAt;
			last.lastUpdatedAt = lastMember.updatedAt;
		}
	});
	
	const deleteMember = async e => {
		e.preventDefault();
		alert(e.target.dataset.id);
		const response = await fetch(`${context.value}admin/deleteMember`, {
			method: 'POST'
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				id: e.target.dataset.id
			})
		});
		const json = await response.json();
		if (json.status) {
			alert(json.message);
			tbody.removeChild(e.target.parentNode.parentNode);
		}
	}
	
	const makeTRTag = member => {
		const copy = row.cloneNode(true);
		copy.querySelector('.id').textContent = member.id;
		copy.querySelector('.pwd').textContent = member.pwd;
		copy.querySelector('.name').textContent = member.name;
		copy.querySelector('.email').textContent = member.email;
		copy.querySelector('.phone').textContent = member.phone;
		copy.querySelector('.address').textContent = member.address;
		copy.querySelector('.createdAt').textContent = member.createdAt;
		copy.querySelector('.updatedAt').textContent = member.updatedAt;
		copy.querySelector('.authority').textContent = member.authority;
		copy.querySelector('.updateFormButton > a').href = `${context.value}/admin/updateMemberForm?id=${member.id}`;
		const deleteATag = copy.querySelector('.deleteButton > a');
		deleteATag.dataset.id = member.id;
		deleteATag.addEventListener("click", deleteMember);
		copy.style.display = "table-row";
		return copy;
	}
}

window.onload = main;
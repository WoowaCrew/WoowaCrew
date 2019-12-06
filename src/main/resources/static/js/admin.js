function admin() {
    const approveWaitListButton = document.getElementById('approve-wait-list-button')
    const content = document.getElementById('disapprove-list')
    const origin = window.location.origin

    const signRequestForm = (user) =>
        `<div class="user-info">
                <div class="user-oauth-id">${user.oauthId}</div>
                <input class="user-id" type="hidden" value=${user.id}>
                <div class="user-nickname">${user.nickname}</div>
                <div class="degree-select-box">
                    <select class="degree">
                        <option value="0" selected="selected">크루 아님</option>
                        <option value="1">1기</option>
                    </select>
                </div>
                <div class="role-select-box">
                    <select class="role">
                        <option value="ROLE_PRECOURSE" selected="selected">프리코스</option>
                        <option value="ROLE_CREW">크루</option>
                        <option value="ROLE_COACH">코치</option>
                        <option value="ROLE_ADMIN">관리자</option>
                    </select>
                </div>
                <button class="approve-button">승인</button>
            </div>`

    function loadUser() {
        fetch(origin + "/api/users/disapprove", {
            method: 'GET'
        }).then(response => response.json())
            .then(users => {
                content.innerHTML = ""
                users.forEach(user => {
                    content.insertAdjacentHTML("beforeend", signRequestForm(user))
                })
            })
            .catch(error => alert('오류가 발생했습니다.'));
    }

    function approve(e) {
        const target = e.target

        if (target.className === "approve-button") {
            const userDiv = target.parentNode
            const degreeDiv = userDiv.querySelector(".degree")
            const roleDiv = userDiv.querySelector(".role")

            const degree = degreeDiv.options[degreeDiv.selectedIndex].value
            const role = roleDiv.options[roleDiv.selectedIndex].value
            const userId = userDiv.querySelector(".user-id").value

            console.log(userDiv.querySelector(".user-id"))
            console.log(userId);

            const data = new Object()
            data.role = role
            data.degreeNumber =degree

            fetch(origin + "/api/users/" + userId + "/approve", {
                method: 'PUT',
                body: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                alert("정상적으로 승인 되었습니다.")
                userDiv.remove()
            }).catch(error => alert("에러가 발생했습니다."))
        }
    }

    content.addEventListener("click", approve)
    approveWaitListButton.addEventListener("click", loadUser)
}

admin()
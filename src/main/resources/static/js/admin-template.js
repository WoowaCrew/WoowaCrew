const AdminTemplates = (() => {
  class Templates {
    signRequestListTemplate(user) {
      return `<div class="info-content" id="user-${user.id}">
                <div class="info-cell">${user.oauthId}</div>
                <input class="user-id" type="hidden" value=${user.id}>
                <div class="info-cell margin-left-10">${user.nickname}</div>
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
                <button class="approve-button" onclick="AdminApp.approveUser(${user.id})">승인</button>
            </div>`
    }

    userInfoTitle() {
      return `<div class="info-title-id cell">아이디</div>
                <div class="info-title-nickname cell">닉네임</div>
                <div class="info-title-degree cell">기수</div>
                <div class="info-title-role cell">권한</div>`
    }

    degreeInfoTitle() {
      return `<div class="info-title-id cell">아이디</div>
                <div class="info-title-degree cell">기수</div> `
    }

    degreeListTemplate(degree) {
      return `<div class="info-content" id="degree-${degree.id}">
                <div class="info-cell">${degree.id}</div>
                <div class="info-cell margin-left-10">${degree.number}</div>
            </div>`
    }
  }

  return new Templates()
})()
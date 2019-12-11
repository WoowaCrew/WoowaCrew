const AdminTemplates = (() => {
  class Templates {
    signRequestListTemplate(user) {
      return `<div class="user-info" id="user-${user.id}">
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
                <button class="approve-button" onclick="AdminApp.approveUser(${user.id})">승인</button>
            </div>`
    }

    userInfoTitle() {
      return  `<div class="user-info-title-id">아이디</div>
                <div class="user-info-title-nickname">닉네임</div>
                <div class="user-info-title-degree">기수</div>
                <div class="user-info-title-role">권한</div>`
    }
  }

  return new Templates()
})()
"use strict";

const AdminApp = (() => {
  const BASE_URL = window.location.origin

  class AdminService {
    async showSignRequestList() {
      const infoTitle = document.getElementById('info-title')
      const disapproveList = document.getElementById('disapprove-list')

      fetch(BASE_URL + "/api/users/disapprove", {
        method: 'GET'
      }).then(response => response.json())
        .then(users => {
          infoTitle.innerHTML = ''
          disapproveList.innerHTML = ''
          infoTitle.insertAdjacentHTML("afterbegin", AdminTemplates.userInfoTitle())
          users.forEach(user => {
            disapproveList.insertAdjacentHTML("beforeend", AdminTemplates.signRequestListTemplate(user))
          })
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    async approveUser(userId) {
      const userInfo = document.getElementById("user-" + userId)
      const degreeDiv = userInfo.querySelector(".degree")
      const roleDiv = userInfo.querySelector(".role")

      const degree = degreeDiv.options[degreeDiv.selectedIndex].value
      const role = roleDiv.options[roleDiv.selectedIndex].value

      const data = new Object()
      data.role = role
      data.degreeNumber = degree

      fetch(BASE_URL + "/api/users/" + userId + "/approve", {
        method: 'PUT',
        body: JSON.stringify(data),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(response => {
        alert("정상적으로 승인 되었습니다.")
        userInfo.remove()
      }).catch(error => alert("에러가 발생했습니다."))
    }
  }


  class Controller {
    constructor(adminService) {
      this.adminService = adminService
    }

    showSignRequestList() {
      this.adminService.showSignRequestList()
    }

    approveUser(userId) {
      this.adminService.approveUser(userId)
    }
  }

  return new Controller(new AdminService())
})()
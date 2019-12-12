"use strict";

const AdminApp = (() => {
  const BASE_URL = window.location.origin

  class AdminService {
    async showSignRequestList() {
      const infoTitle = document.getElementById('info-title')
      const infoContent = document.getElementById('info-content')

      fetch(BASE_URL + "/api/users/disapprove", {
        method: 'GET'
      }).then(response => response.json())
        .then(users => {
          infoTitle.innerHTML = ''
          infoContent.innerHTML = ''
          infoTitle.insertAdjacentHTML("afterbegin", AdminTemplates.userInfoTitle())
          users.forEach(user => {
            infoContent.insertAdjacentHTML("beforeend", AdminTemplates.signRequestListTemplate(user))
          })
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    async approveUser(userId) {
      const userInfo = document.getElementById("user-" + userId)
      const degreeDiv = userInfo.querySelector(".woowacrew.degree")
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

    async showApprovedUser() {
      const infoTitle = document.getElementById('info-title')
      const infoContent = document.getElementById('info-content')
      fetch(origin + "/api/users/approve", {
        method: 'GET'
      }).then(response => response.json())
        .then(users => {
          infoTitle.innerHTML = ''
          infoContent.innerHTML = ''
          infoTitle.insertAdjacentHTML("afterbegin", AdminTemplates.userInfoTitle())
          users.forEach(user => {
            let template = AdminTemplates.approvedUserListTemplate(user);
            let element = this.selectRole(template, user.userRole)
            infoContent.insertAdjacentElement("beforeend", element)
          })
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    selectRole(template, userRole) {
      let element = document.createElement('div')
      element.innerHTML = template;
      const roleSelectedBox = element.querySelector('.role')
      const options = roleSelectedBox.options
      for (const option of options) {
        if (option.value === userRole) {
          option.selected = true
        }
      }
      return element
    }

    async showDegrees() {
      const infoTitle = document.getElementById('info-title')
      const infoContent = document.getElementById('info-content')

      fetch(BASE_URL + "/api/degrees", {
        method: 'GET'
      }).then(response => response.json())
        .then(degrees => {
          infoTitle.innerHTML = ''
          infoContent.innerHTML = ''
          infoTitle.insertAdjacentHTML("afterbegin", AdminTemplates.degreeInfoTitle())
          degrees.forEach(degree => {
            infoContent.insertAdjacentHTML("beforeend", AdminTemplates.degreeListTemplate(degree))
          })
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    activeButton(leftBar) {
      const activeButton = document.getElementsByClassName("left-bar-active")[0]
      if (activeButton != null) {
        activeButton.classList.remove("left-bar-active")
      }
      leftBar.classList.add("left-bar-active")
    }
  }


  class Controller {
    constructor(adminService) {
      this.adminService = adminService
    }

    showSignRequestList() {
      this.adminService.showSignRequestList()
      const leftBar = document.getElementById('approve-wait-list-button');
      this.adminService.activeButton(leftBar)
    }

    approveUser(userId) {
      this.adminService.approveUser(userId)
    }

    showApprovedUser() {
      this.adminService.showApprovedUser()
      const leftBar = document.getElementById('approve-complete-list-button');
      this.adminService.activeButton(leftBar)
    }

    showDegrees() {
      this.adminService.showDegrees()
      const leftBar = document.getElementById('degree-manage-button');
      this.adminService.activeButton(leftBar)
    }
  }

  return new Controller(new AdminService())
})()
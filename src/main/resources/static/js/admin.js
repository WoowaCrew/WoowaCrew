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
      const data = this.getDegreeAndRole(userId)

      fetch(BASE_URL + "/api/users/" + userId + "/approve", {
        method: 'PUT',
        body: JSON.stringify(data),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(response => {
        alert("정상적으로 승인 되었습니다.")
        const userInfo = document.getElementById("user-" + userId)
        userInfo.remove()
      }).catch(error => alert("에러가 발생했습니다."))
    }

    async updateUserAuthority(userId) {
      const data = this.getDegreeAndRole(userId)
      fetch(BASE_URL + "/api/users/" + userId + "/approve", {
        method: 'PUT',
        body: JSON.stringify(data),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(response => {
        alert("정상적으로 승인 되었습니다.")
      }).catch(error => alert("에러가 발생했습니다."))
    }

    getDegreeAndRole(userId) {
      const userInfo = document.getElementById("user-" + userId)
      const degreeDiv = userInfo.querySelector(".degree")
      const roleDiv = userInfo.querySelector(".role")

      const degree = degreeDiv.options[degreeDiv.selectedIndex].value
      const role = roleDiv.options[roleDiv.selectedIndex].value

      const data = new Object()
      data.role = role
      data.degreeNumber = degree

      return data
    }

    async showApprovedUser() {
      fetch(origin + "/api/users/approve", {
        method: 'GET'
      }).then(response => response.json())
        .then(users => this.renderUserList(users))
        .catch(error => alert('오류가 발생했습니다.'));
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

    async showDetailUsersOfDegree(degreeId) {
      fetch(BASE_URL + "/api/degrees/" + degreeId + "/users", {
        method: 'GET'
      }).then(response => response.json())
        .then(users => this.renderUserList(users))
        .catch(error => alert('오류가 발생했습니다.'));
    }

    renderUserList(users) {
      const infoTitle = document.getElementById('info-title')
      const infoContent = document.getElementById('info-content')
      infoTitle.innerHTML = ''
      infoContent.innerHTML = ''
      infoTitle.insertAdjacentHTML("afterbegin", AdminTemplates.userInfoTitle())
      users.forEach(user => {
        let template = AdminTemplates.approvedUserListTemplate(user)
        let elementTemplate = document.createElement('div')
        elementTemplate.innerHTML = template
        this.selectRole(elementTemplate, user.userRole)
        this.selectDegree(elementTemplate, user.degreeResponseDto.degreeNumber)
        infoContent.insertAdjacentElement("beforeend", elementTemplate)
      })
    }

    selectRole(template, userRole) {
      const roleSelectedBox = template.querySelector('.role')
      const options = roleSelectedBox.options
      for (const option of options) {
        if (option.value === userRole) {
          option.selected = true
        }
      }
      return template
    }

    selectDegree(template, degreeNumber) {
      const degreeSelectedBox = template.querySelector('.degree')
      const options = degreeSelectedBox.options
      for (const option of options) {
        if (parseInt(option.value) === degreeNumber) {
          option.selected = true
        }
      }
    }

    activeButton(leftBar) {
      const activeButton = document.getElementsByClassName("left-bar-active")[0]
      if (activeButton != null) {
        activeButton.classList.remove("left-bar-active")
      }
      leftBar.classList.add("left-bar-active")
    }

    showAddFeedForm() {
      const infoTitle = document.getElementById('info-title')
      const infoContent = document.getElementById('info-content')
      infoTitle.innerHTML=''
      infoContent.innerHTML=''
      infoContent.insertAdjacentHTML("beforeend", AdminTemplates.addFeedForm())
    }

    async addFeedSource() {
      const sourceUrl = document.getElementById('source-url').value
      const description = document.getElementById('description').value
      if(sourceUrl === '') {
        alert("주소를 입력해주세요.")
        exit()
      }
      if(description === '') {
        alert("설명을 입력해 주세요.")
        exit()
      }
      const formData = new FormData()
      formData.append('sourceUrl', sourceUrl)
      formData.append('description', description)
      fetch(BASE_URL + "/api/feeds", {
        method: 'POST',
        body: formData
      }).then(response => response.json())
        .then(feedResponse => {
          alert('업로드 성공')
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }
  }


  class Controller {
    constructor(adminService) {
      this.adminService = adminService
    }

    showSignRequestList() {
      this.adminService.showSignRequestList()
      const leftBar = document.getElementById('approve-wait-list-button')
      this.adminService.activeButton(leftBar)
    }

    approveUser(userId) {
      this.adminService.approveUser(userId)
    }

    updateUserAuthority(userId) {
      this.adminService.updateUserAuthority(userId)
    }

    showApprovedUser() {
      this.adminService.showApprovedUser()
      const leftBar = document.getElementById('approve-complete-list-button')
      this.adminService.activeButton(leftBar)
    }

    showDegrees() {
      this.adminService.showDegrees()
      const leftBar = document.getElementById('degree-manage-button')
      this.adminService.activeButton(leftBar)
    }

    showDetailUsersOfDegree(degreeId) {
      this.adminService.showDetailUsersOfDegree(degreeId)
    }

    showAddFeedForm() {
      this.adminService.showAddFeedForm()
      const leftBar = document.getElementById('rss-add-button')
      this.adminService.activeButton(leftBar)
    }

    addFeedSource() {
      this.adminService.addFeedSource()
    }
  }

  return new Controller(new AdminService())
})()
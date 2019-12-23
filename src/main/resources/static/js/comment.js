"use strict";

const CommentListApp = (() => {
  const BASE_URL = window.location.origin

  class CommentService {
    createComment() {
      const commentContentDiv = document.getElementById('comment-content')
      if (commentContentDiv.value === "") {
        alert("메세지를 입력해주세요.")
        return;
      }
      const articleId = document.getElementById('article-id').textContent
      const commentList = document.getElementById('comment-list')
      const formData = new FormData()
      formData.append('content', commentContentDiv.value)

      fetch(BASE_URL + "/api/articles/" + articleId + "/comments", {
        method: 'POST',
        body: formData

      }).then(response => response.json())
        .then(comment => {
            commentContentDiv.value = ''
            commentList.insertAdjacentHTML("beforeend", CommentTemplates.commentListTemplate(comment))
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    deleteComment(event) {
      const commentDiv = event.target.closest(".comment")
      const articleId = document.getElementById('article-id').textContent
      const commentId = commentDiv.getElementsByClassName("comment-id")[0].value

      fetch(BASE_URL + "/api/articles/" + articleId + "/comments/" + commentId, {
        method: 'DELETE',
      }).then(response => {
        if(response.ok){
          commentDiv.remove()
          alert("삭제 성공!")
        }
        throw new Error(response)
      })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    updateComment(event) {
      const commentDiv = event.target.closest(".comment")
      const commentContentDiv = commentDiv.getElementsByClassName("comment-content")[0]
      const commentEditFormDiv = commentDiv.getElementsByClassName("comment-edit-form")[0]
      const articleId = document.getElementById('article-id').textContent
      const commentId = commentDiv.getElementsByClassName("comment-id")[0].value
      const commentUpdateContent = commentEditFormDiv.getElementsByTagName("textarea")[0].value

      if (commentUpdateContent === "") {
        alert("메시지를 입력해주세요.")
        return;
      }

      const formData = new FormData()
      formData.append('updateContent', commentUpdateContent)

      fetch(BASE_URL + "/api/articles/" + articleId + "/comments/" + commentId, {
        method: 'PUT',
        body: formData
      }).then(response => {
        if (response.ok) {
          response.json()
        }
        throw new Error(response)
      }).then(comment => {
          commentContentDiv.textContent = comment.content
          this.cancelUpdateCommentForm(event);
        }).catch(error => alert('오류가 발생했습니다.'));
    }

    showUpdateCommentForm(event) {
      const commentDiv = event.target.closest(".comment")
      const commentContentDiv = commentDiv.getElementsByClassName("comment-content")[0]
      const commentUpdateFormDiv = commentDiv.getElementsByClassName("comment-edit-form")[0]
      const commentButtons = commentDiv.getElementsByClassName("comment-common-button-group")[0]
      const commentEditButtons = commentDiv.getElementsByClassName("comment-edit-button-group")[0]

      const commentUpdateForm = commentUpdateFormDiv.getElementsByTagName("textarea")[0];
      commentUpdateForm.value = commentContentDiv.textContent

      commentContentDiv.style.display = "none"
      commentButtons.style.display = "none"

      commentUpdateFormDiv.style.display = "flex"
      commentEditButtons.style.display = "flex"
    }

    cancelUpdateCommentForm(event) {
      const commentDiv = event.target.closest(".comment")
      const commentContentDiv = commentDiv.getElementsByClassName("comment-content")[0]
      const commentUpdateFormDiv = commentDiv.getElementsByClassName("comment-edit-form")[0]
      const commentButtons = commentDiv.getElementsByClassName("comment-common-button-group")[0]
      const commentEditButtons = commentDiv.getElementsByClassName("comment-edit-button-group")[0]

      commentUpdateFormDiv.style.display = "none"
      commentEditButtons.style.display = "none"

      commentContentDiv.style.display = "flex"
      commentButtons.style.display = "flex"
    }


    async showComments() {
      const commentList = document.getElementById('comment-list')
      const articleId = document.getElementById('article-id').textContent

      fetch(BASE_URL + "/api/articles/" + articleId + "/comments", {
        method: 'GET'
      }).then(response => response.json())
        .then(comments => {
          commentList.innerHTML = ''
          comments.forEach(comment => {
            commentList.insertAdjacentHTML("beforeend", CommentTemplates.commentListTemplate(comment))
          })
        })
        .catch(error => {
          console.log(error)
          alert('comment 오류가 발생했습니다.')
        });
    }
  }


  class Controller {
    constructor(commentService) {
      this.commentService = commentService
    }

    showComments() {
      this.commentService.showComments()
    }

    createComment() {
      this.commentService.createComment()
    }

    showUpdateCommentForm(event) {
      this.commentService.showUpdateCommentForm(event)
    }

    cancelUpdateCommentForm(event) {
      this.commentService.cancelUpdateCommentForm(event)
    }

    updateComment(event) {
      this.commentService.updateComment(event)
    }

    deleteComment(event) {
      this.commentService.deleteComment(event);
    }
  }

  return new Controller(new CommentService())
})()

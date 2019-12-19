"use strict";

const CommentListApp = (() => {
  const BASE_URL = window.location.origin

  class CommentService {
    createComment() {
      const commentContent = document.getElementById('comment-content').value
      const articleId = document.getElementById('article-id').textContent
      const commentList = document.getElementById('comment-list')
      const formData = new FormData()
      formData.append('content', commentContent)

      fetch(BASE_URL + "/api/articles/" + articleId + "/comments", {
        method: 'POST',
        body: formData

      }).then(response => response.json())
        .then(comment => {
            commentList.insertAdjacentHTML("beforeend", CommentTemplates.commentListTemplate(comment))
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    async showComments() {
      const commentList = document.getElementById('comment-list')
      const articleId = document.getElementById('article-id').textContent

      fetch(BASE_URL + "/api/articles/" + articleId + "/comments", {
        method: 'GET'
      }).then(response => response.json())
        .then(comments => {
          console.log(comments)
          return comments
        })
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
  }

  return new Controller(new CommentService())
})()

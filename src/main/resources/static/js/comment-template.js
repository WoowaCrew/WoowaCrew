const CommentTemplates = (() => {
  class Templates {
    commentListTemplate(comment) {
      const convertTime = (time) => {
        if (time !== null) {
          return time.split('T')[0] + " " + time.split('T')[1]
        }
        return time
      }

      return `
      <div class="comment">
          <input hidden value=${comment.id}>
          <div class="comment-content">
              ${comment.content}
          </div>
          <div>
              <span class="comment-author">${comment.userNickName}</span>
              <span class="comment-create-date-time">${convertTime(comment.createDateTime)}</span>
          </div>
      </div>
      `
    }
  }

  return new Templates()
})()
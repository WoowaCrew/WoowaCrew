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
          <div class="comment-content">${comment.content}</div>
          <div class="comment-edit-form"><textarea></textarea></div>
          <div class="comment-info">
            <div>
              <span class="comment-author">${comment.userNickName}</span>
              <span class="comment-create-date-time">${convertTime(comment.createDateTime)}</span>
            </div>
              <div class="comment-common-button-group">
                <div class="comment-delete-button">삭제</div>
                <div class="comment-edit-button" onclick="CommentListApp.showUpdateCommentForm(event)">수정</div>
              </div>
              <div class="comment-edit-button-group">
                <div class="comment-cancel-button" onclick="CommentListApp.cancelUpdateCommentForm(event)">취소</div>
                <div class="comment-update-button" onclick="CommentListApp.showUpdateCommentForm(event)">수정</div>
              </div>
          </div>
      </div>
      `
    }
  }

  return new Templates()
})()
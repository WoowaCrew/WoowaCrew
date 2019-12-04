function article() {
  const body = document.getElementsByTagName("BODY")[0]

  const articleForm = (article) =>
      `<div class="article-info">
          <div id="article-header">
              <div class="article-title">
                  <div class="title">${article.title}</div>
                  <div class="userInfo">${article.userResponseDto.userId}</div>
              </div>
              <div class="article-button-group">
                <div class="article-edit-button"><i class="fa fa-edit"></i></div>
                <div class="article-delete-button"><i class="fa fa-3x fa-trash"></i></div>
              </div>
          </div>
          <div id="viewerSection" class="content"></div>
      </div>`

  const articleUrl = window.location.href.split("/")
  const articleId = articleUrl[articleUrl.length - 1]
  const origin = window.location.origin

  fetch(origin + "/api/articles/" + articleId, {
    method: 'GET'
  }).then(response => response.json())
      .then(article => {
        body.insertAdjacentHTML("beforeend", articleForm(article))
        const viewer = tui.Editor.factory({
          el: document.querySelector('#viewerSection'),
          viewer: true,
          height: '100%',
          initialValue: article.content
        })
      })
      .catch(error => {
        console.log(error)
        alert('오류가 발생했습니다.')
      });
}

article()

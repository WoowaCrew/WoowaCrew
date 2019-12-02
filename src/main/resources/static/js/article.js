function article() {
  const body = document.getElementsByTagName("BODY")[0]

  const articleFrom = (article) =>
      `<div class="article-info">
          <div id="article-header">
              <div class="title">${article.title}</div>
              <div class="user">
                  <div class="userInfo">${article.userDto.userId}</div>
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
        body.insertAdjacentHTML("beforeend", articleFrom(article))
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

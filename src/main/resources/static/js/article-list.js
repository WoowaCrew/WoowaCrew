function articleList() {
  const articleList = document.getElementById('article-list')
  const articleCreateIcon = document.getElementById('article-create-icon')

  const origin = window.location.origin

  const convertTime = (time) => {
       return time.split('T')[0]
  }

  const articleListForm = (article) =>
      `<div class="article-info">
        <div class="article-info-article-id">
            <div id="article-info-article-id-content" value=${article.id}>
                ${article.id}
            </div>
        </div>
        <div class="article-info-title">${article.title}</div>
        <div class="article-info-created-date">${convertTime(article.createdDate)}</div>
        <div class="article-info-userInfo">${article.userResponseDto.nickname}</div>
        <div class="article-info-views">
            <div class="article-info-views-content">
                <i class="fa fa-eye"></i>
                1
            </div>
        </div>
      </div>`

  articleList.addEventListener('mouseover', function (e) {
    const node = e.target.parentNode
    if (node.className === 'article-info') {
      this.style.cursor = 'pointer'
    }
  })

  articleList.addEventListener('click', function (e) {
    const node = e.target.parentNode
    if (node.className === 'article-info') {
      const articleId = document.getElementById('article-info-article-id-content').getAttribute('value')
      window.location.href = origin + '/articles/' + articleId
    }
  })

  articleCreateIcon.addEventListener('click', function (e) {
      window.location.href = origin + '/article/new'
  })

  fetch(origin + "/api/articles", {
    method: 'GET'
  }).then(response => response.json())
      .then(articles => {
        articles.forEach(article => {
          articleList.insertAdjacentHTML("beforeend", articleListForm(article))
        })
      })
      .catch(error => alert('오류가 발생했습니다.'));
}

articleList()
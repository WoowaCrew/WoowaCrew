function articleList() {
  document.getElementById('article-list').addEventListener('mouseover', function (e) {
    const node = e.target.parentNode
    if (node.className === 'article-info') {
      this.style.cursor = 'pointer'
    }
  })
  document.getElementById('article-list').addEventListener('click', function (e) {
    const node = e.target.parentNode
    if (node.className === 'article-info') {
      const url = window.location.origin
      const articleId = node.getElementsByClassName('article-id')[0].value
      window.location.href = url + '/articles/' + articleId
    }
  })

  const articleList = document.getElementById('article-list')

  const articleListFrom = (article) =>
     `<div class="article-info">
        <input class="article-id" type="hidden" value="${article.id}"/>
        <div class="title">${article.title}</div>
        <div class="userInfo">${article.userDto.userId}</div>
        <div class="created-date">${article.createdDate}</div>
    </div>`

  fetch("http://localhost:8080/api/articles", {
    method: 'GET'
  }).then(response => response.json())
      .then(articles => {
        articles.forEach(article => {
          articleList.insertAdjacentHTML("beforeend", articleListFrom(article))
        })
      })
      .catch(error => alert('오류가 발생했습니다.'));
}

articleList()
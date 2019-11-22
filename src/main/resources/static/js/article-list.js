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
}

articleList()
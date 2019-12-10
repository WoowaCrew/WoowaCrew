function articleList() {
  const articleList = document.getElementById('article-list')
  const articleCreateIcon = document.getElementById('article-create-icon')
  const pageBar = document.getElementById('page-bar')

  const origin = window.location.origin

  const convertTime = (time) => {
    return time.split('T')[0]
  }

  const prevBarForm = '<div class="prev-bar">이전</div>'
  const nextBarForm = '<div class="next-bar">다음</div>'
  const pageBarForm = (numberOfPage) =>
    `<div class="page-number">${numberOfPage}</div>`
  const selectedPageBarForm = (numberOfPage) =>
    `<div class="page-number selected-page">${numberOfPage}</div>`

  const articleListForm = (article) =>
    `<div class="article-info">
        <div class="article-info-article-id">
            <div class="article-info-article-id-content" value=${article.id}>
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

  fetch(origin + "/api/articles", {
    method: 'GET'
  }).then(response => response.json())
    .then(articleResponse => {
      renderPageBar(articleResponse.pageNumber, articleResponse.totalPages)
      articleResponse.articles.forEach(article => {
        articleList.insertAdjacentHTML("beforeend", articleListForm(article))
      })
    })
    .catch(error => alert('오류가 발생했습니다.'));

  function renderPageBar(pageNumber, totalPages) {
    pageBar.innerHTML = ''

    const startPageNumber = (pageNumber - 5) > 0 ? pageNumber - 5 : 1
    if (startPageNumber !== 1) {
      pageBar.insertAdjacentHTML("beforeend", prevBarForm)
    }
    const lastPageNumber = (pageNumber + 5) <= totalPages ? pageNumber + 5 : totalPages
    for (let numberOfPage = startPageNumber; numberOfPage <= lastPageNumber; numberOfPage++) {
      if (numberOfPage === pageNumber) {
        pageBar.insertAdjacentHTML("beforeend", selectedPageBarForm(numberOfPage))
      } else {
        pageBar.insertAdjacentHTML("beforeend", pageBarForm(numberOfPage))
      }
    }
    if (lastPageNumber !== totalPages) {
      pageBar.insertAdjacentHTML("beforeend", nextBarForm)
    }
  }

  articleList.addEventListener('mouseover', function (e) {
    const node = e.target.parentNode
    if (node.className === 'article-info') {
      this.style.cursor = 'pointer'
    }
  })

  articleList.addEventListener('click', function (e) {
    const node = e.target.parentNode
    if (node.className === 'article-info') {
      const articleId = node.querySelector('.article-info-article-id-content').getAttribute('value')
      window.location.href = origin + '/articles/' + articleId
    }
  })


  pageBar.addEventListener('click', function (e) {
    const node = e.target
    if (node.className === 'page-number') {
      const pageNumber = node.innerText
      getArticle(pageNumber)
    }
    if (node.className === 'next-bar') {
      const pageNumbers = node.parentElement.getElementsByClassName('page-number')
      const pageNumber = pageNumbers[pageNumbers.length - 1].innerText
      console.log(pageNumber)
      getArticle(parseInt(pageNumber) + 1)
    }
    if (node.className === 'prev-bar') {
      const pageNumber = node.parentElement.getElementsByClassName('page-number')[0].innerText
      getArticle(pageNumber - 1)
    }
  })

  function getArticle(pageNumber) {
    fetch(origin + "/api/articles?page=" + pageNumber, {
      method: 'GET'
    }).then(response => response.json())
      .then(articleResponse => {
        renderPageBar(articleResponse.pageNumber, articleResponse.totalPages)
        articleList.innerHTML = ''
        articleResponse.articles.forEach(article => {
          articleList.insertAdjacentHTML("beforeend", articleListForm(article))
        })
      })
      .catch(error => alert('오류가 발생했습니다.'));
  }

  articleCreateIcon.addEventListener('click', function (e) {
    window.location.href = origin + '/article/new'
  })

}

articleList()
"use strict";

const AnonymousArticleListApp = (() => {
  const BASE_URL = window.location.origin

  class AnonymousArticleService {
    async showAnonymousArticles(numberOfPage) {
      const articleList = document.getElementById('article-list')

      fetch(BASE_URL + "/api/articles/anonymous/approved?page=" + numberOfPage, {
        method: 'GET'
      }).then(response => response.json())
        .then(articleResponse => {
          this.renderPageBar(articleResponse.pageNumber, articleResponse.totalPages)
          articleList.innerHTML = ''
          articleResponse.articles.forEach(article => {
            articleList.insertAdjacentHTML("beforeend", AnonymousArticleTemplates.articleListTemplate(article))
          })
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    static showDetailAnonymousArticle(articleId) {
      window.location.href = BASE_URL + '/articles/anonymous/' + articleId
    }

    static showAnonymousArticleCreatePage() {
      window.location.href = BASE_URL + '/articles/anonymous/new'
    }

    renderPageBar(pageNumber, totalPages) {
      const pageBar = document.getElementById('page-bar')

      pageBar.innerHTML = ''

      const startPageNumber = (pageNumber - 5) > 0 ? pageNumber - 5 : 1
      if (startPageNumber !== 1) {
        pageBar.insertAdjacentHTML("beforeend", AnonymousArticleTemplates.prevBarTemplate(startPageNumber - 1))
      }
      const lastPageNumber = (pageNumber + 5) <= totalPages ? pageNumber + 5 : totalPages
      for (let numberOfPage = startPageNumber; numberOfPage <= lastPageNumber; numberOfPage++) {
        if (numberOfPage === pageNumber) {
          pageBar.insertAdjacentHTML("beforeend", AnonymousArticleTemplates.selectedPageBarTemplate(numberOfPage))
        } else {
          pageBar.insertAdjacentHTML("beforeend", AnonymousArticleTemplates.pageBarTemplate(numberOfPage))
        }
      }
      if (lastPageNumber !== totalPages) {
        pageBar.insertAdjacentHTML("beforeend", AnonymousArticleTemplates.nextBarTemplate(parseInt(lastPageNumber) + 1))
      }
    }
  }


  class Controller {
    constructor(articleService) {
      this.articleService = articleService
    }

    showAnonymousArticles(numberOfPage) {
      this.articleService.showAnonymousArticles(numberOfPage)
    }

    showDetailAnonymousArticle(articleId) {
      AnonymousArticleService.showDetailAnonymousArticle(articleId)
    }

    showAnonymousArticleCreatePage() {
      AnonymousArticleService.showAnonymousArticleCreatePage()
    }
  }

  return new Controller(new AnonymousArticleService())
})()
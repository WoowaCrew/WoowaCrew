"use strict";

const CrewArticleListApp = (() => {
  const BASE_URL = window.location.origin

  class ArticleService {
    async showArticles(numberOfPage) {
      const articleList = document.getElementById('article-list')

      fetch(BASE_URL + "/api/articles/crew?page=" + numberOfPage, {
        method: 'GET'
      }).then(response => response.json())
        .then(articleResponse => {
          this.renderPageBar(articleResponse.pageNumber, articleResponse.totalPages)
          articleList.innerHTML = ''
          articleResponse.articles.forEach(article => {
            articleList.insertAdjacentHTML("beforeend", ArticleTemplates.articleListTemplate(article))
          })
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    static showDetailArticle(articleId) {
      window.location.href = BASE_URL + '/articles/crew/' + articleId
    }

    static showArticleEditPage() {
      window.location.href = BASE_URL + '/article/crew/new'
    }

    renderPageBar(pageNumber, totalPages) {
      const pageBar = document.getElementById('page-bar')

      pageBar.innerHTML = ''

      const startPageNumber = (pageNumber - 5) > 0 ? pageNumber - 5 : 1
      if (startPageNumber !== 1) {
        pageBar.insertAdjacentHTML("beforeend", ArticleTemplates.prevBarTemplate(startPageNumber - 1))
      }
      const lastPageNumber = (pageNumber + 5) <= totalPages ? pageNumber + 5 : totalPages
      for (let numberOfPage = startPageNumber; numberOfPage <= lastPageNumber; numberOfPage++) {
        if (numberOfPage === pageNumber) {
          pageBar.insertAdjacentHTML("beforeend", ArticleTemplates.selectedPageBarTemplate(numberOfPage))
        } else {
          pageBar.insertAdjacentHTML("beforeend", ArticleTemplates.pageBarTemplate(numberOfPage))
        }
      }
      if (lastPageNumber !== totalPages) {
        pageBar.insertAdjacentHTML("beforeend", ArticleTemplates.nextBarTemplate(parseInt(lastPageNumber) + 1))
      }
    }

    searchByNumberOfPage(numberOfPage) {
      const editBarDiv = document.getElementById("edit-bar")
      const selectBoxDiv = editBarDiv.getElementsByClassName("search-type")[0]
      const selectedValue = selectBoxDiv.selectedOptions[0].value
      const userInputData = editBarDiv.getElementsByClassName("search-form")[0].value

      this.searchByTypeAndNumberOfPageAndContent(selectedValue, numberOfPage, userInputData);
    }

    searchByNumberOfPageAndContent(numberOfPage, content) {
      const editBarDiv = document.getElementById("edit-bar")
      const selectBoxDiv = editBarDiv.getElementsByClassName("search-type")[0]
      const searchType = selectBoxDiv.selectedOptions[0].value

      this.searchByTypeAndNumberOfPageAndContent(searchType, numberOfPage, content);
    }

    searchByTypeAndNumberOfPageAndContent(type, numberOfPage, content) {
      const articleList = document.getElementById('article-list')

      fetch(BASE_URL + "/api/articles/crew/search?page=" + numberOfPage + "&type=" + type + "&content=" + content,{
        method: 'GET'
      }).then(response => response.json())
          .then(articleResponse => {
            this.renderPageBar(articleResponse.pageNumber, articleResponse.totalPages)
            articleList.innerHTML = ''
            articleResponse.articles.forEach(article => {
              articleList.insertAdjacentHTML("beforeend", ArticleSearchTemplates.articleListTemplate(article))
            })
          })
          .catch(error => {
            console.log(error)
            alert('오류가 발생했습니다.')
          });
    }
  }


  class Controller {
    constructor(articleService) {
      this.articleService = articleService
    }

    showArticles(numberOfPage) {
      this.articleService.showArticles(numberOfPage)
    }

    showDetailArticle(articleId) {
      ArticleService.showDetailArticle(articleId)
    }

    showArticleEditPage() {
      ArticleService.showArticleEditPage()
    }

    searchByEnterKey(event, numberOfPage) {
      if (event.keyCode === 13) {
        this.searchByNumberOfPage(numberOfPage);
      }
    }

    searchByNumberOfPage(numberOfPage) {
      this.articleService.searchByNumberOfPage(numberOfPage);
    }

    searchByNumberOfPageAndContent(numberOfPage, content) {
      this.articleService.searchByNumberOfPageAndContent(numberOfPage, content)
    }
  }

  return new Controller(new ArticleService())
})()
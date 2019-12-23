"use strict";

const FeedApp = (() => {
  const BASE_URL = window.location.origin

  class FeedService {
    async showFeeds(numberOfPage) {
      const articleList = document.getElementById('article-list')

      fetch(BASE_URL + "/api/feed-articles?page=" + numberOfPage, {
        method: 'GET'
      }).then(response => response.json())
        .then(feedResponse => {
          this.renderPageBar(feedResponse.pageNumber, feedResponse.totalPages)
          articleList.innerHTML = ''
          feedResponse.articles.forEach(feed => {
            let template = FeedTemplates.feedListTemplate(feed)
            articleList.insertAdjacentHTML("beforeend", template)
          })
        })
        .catch(error => alert('오류가 발생했습니다.'));
    }

    renderPageBar(pageNumber, totalPages) {
      const pageBar = document.getElementById('page-bar')

      pageBar.innerHTML = ''

      const startPageNumber = (pageNumber - 5) > 0 ? pageNumber - 5 : 1
      if (startPageNumber !== 1) {
        pageBar.insertAdjacentHTML("beforeend", FeedTemplates.prevBarTemplate(startPageNumber - 1))
      }
      const lastPageNumber = (pageNumber + 5) <= totalPages ? pageNumber + 5 : totalPages
      for (let numberOfPage = startPageNumber; numberOfPage <= lastPageNumber; numberOfPage++) {
        if (numberOfPage === pageNumber) {
          pageBar.insertAdjacentHTML("beforeend", FeedTemplates.selectedPageBarTemplate(numberOfPage))
        } else {
          pageBar.insertAdjacentHTML("beforeend", FeedTemplates.pageBarTemplate(numberOfPage))
        }
      }
      if (lastPageNumber !== totalPages) {
        pageBar.insertAdjacentHTML("beforeend", FeedTemplates.nextBarTemplate(parseInt(lastPageNumber) + 1))
      }
    }
  }


  class Controller {
    constructor(feedService) {
      this.feedService = feedService
    }

    showFeeds(numberOfPage) {
      this.feedService.showFeeds(numberOfPage)
    }
  }

  return new Controller(new FeedService())
})()
"use strict";

const FeedSearchApp = (() => {
    const BASE_URL = window.location.origin

    class FeedSearchService {
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

            fetch(`${BASE_URL}/api/feed-articles/search?page=${numberOfPage}&type=${type}&content=${content}`,{
                method: 'GET'
            }).then(response => response.json())
                .then(articleResponse => {
                    this.renderPageBar(articleResponse.pageNumber, articleResponse.totalPages)
                    articleList.innerHTML = ''
                    articleResponse.articles.forEach(article => {
                        console.log("test : " + article.title)
                        articleList.insertAdjacentHTML("beforeend", FeedTemplates.feedListTemplate(article))
                    })
                })
                .catch(error => {
                    console.log(error)
                    alert('오류가 발생했습니다.')
                });
        }
    }


    class Controller {
        constructor(feedSearchService) {
            this.feedSearchService = feedSearchService
        }

        showArticles(numberOfPage) {
            this.feedSearchService.showArticles(numberOfPage)
        }

        searchByEnterKey(event, numberOfPage) {
            const enterKeyCode = 13;
            if (event.keyCode === enterKeyCode) {
                this.searchByNumberOfPage(numberOfPage);
            }
        }

        searchByNumberOfPage(numberOfPage) {
            this.feedSearchService.searchByNumberOfPage(numberOfPage);
        }

        searchByNumberOfPageAndContent(numberOfPage, content) {
            this.feedSearchService.searchByNumberOfPageAndContent(numberOfPage, content)
        }
    }

    return new Controller(new FeedSearchService())
})()
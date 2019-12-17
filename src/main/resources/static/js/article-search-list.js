"use strict";

const ArticleSearchListApp = (() => {
    const BASE_URL = window.location.origin

    class ArticleSearchService {
        renderPageBar(pageNumber, totalPages) {
            const pageBar = document.getElementById('page-bar')

            pageBar.innerHTML = ''

            const startPageNumber = (pageNumber - 5) > 0 ? pageNumber - 5 : 1
            if (startPageNumber !== 1) {
                pageBar.insertAdjacentHTML("beforeend", ArticleSearchTemplates.prevBarTemplate(startPageNumber - 1))
            }
            const lastPageNumber = (pageNumber + 5) <= totalPages ? pageNumber + 5 : totalPages
            for (let numberOfPage = startPageNumber; numberOfPage <= lastPageNumber; numberOfPage++) {
                if (numberOfPage === pageNumber) {
                    pageBar.insertAdjacentHTML("beforeend", ArticleSearchTemplates.selectedPageBarTemplate(numberOfPage))
                } else {
                    pageBar.insertAdjacentHTML("beforeend", ArticleSearchTemplates.pageBarTemplate(numberOfPage))
                }
            }
            if (lastPageNumber !== totalPages) {
                pageBar.insertAdjacentHTML("beforeend", ArticleSearchTemplates.nextBarTemplate(parseInt(lastPageNumber) + 1))
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
            const formData = new FormData();
            formData.append("content", content)

            fetch(BASE_URL + "/api/articles/search?type=" + type + "&page=" + numberOfPage, {
                method: 'POST',
                body: formData
            }).then(response => response.json())
                .then(articleResponse => {
                    this.renderPageBar(articleResponse.pageNumber, articleResponse.totalPages)
                    articleList.innerHTML = ''
                    articleResponse.articles.forEach(article => {
                        articleList.insertAdjacentHTML("beforeend", ArticleSearchTemplates.articleListTemplate(article))
                    })
                })
                .catch(error => alert('오류가 발생했습니다.'));
        }
    }


    class Controller {
        constructor(articleSearchService) {
            this.articleSearchService = articleSearchService
        }

        searchByEnterKey(event, numberOfPage) {
            if (event.keyCode === 13) {
                this.searchByNumberOfPage(numberOfPage);
            }
        }

        searchByNumberOfPage(numberOfPage) {
            this.articleSearchService.searchByNumberOfPage(numberOfPage);
        }

        searchByNumberOfPageAndContent(numberOfPage, content) {
            this.articleSearchService.searchByNumberOfPageAndContent(numberOfPage, content)
        }
    }

    return new Controller(new ArticleSearchService())
})()
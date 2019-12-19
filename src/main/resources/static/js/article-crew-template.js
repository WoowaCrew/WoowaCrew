const ArticleTemplates = (() => {
  class Templates {
    articleListTemplate(article) {
      const convertTime = (time) => {
        if (time !== null) {
          return time.split('T')[0]
        }
        return time
      }

      return `
      <div class="article-info" onclick="CrewArticleListApp.showDetailArticle(${article.id})">
        <div class="article-info-article-id">
            <div class="article-info-article-id-content">
                ${article.id}
            </div>
        </div>
        <div class="article-info-title">${article.title}</div>
        <div class="article-info-created-date">${convertTime(article.createdDate)}</div>
        <div class="article-info-userInfo">${article.userResponseDto.nickname}</div>
        <div class="article-info-views">
            <div class="article-info-views-content">
                <i class="fa fa-eye"></i>
            </div>
        </div>
      </div>
      `
    }

    selectedPageBarTemplate(numberOfPage) {
      return `<div class="page-number selected-page" onclick="CrewArticleListApp.showArticles(${numberOfPage})">${numberOfPage}</div>`
    }


    pageBarTemplate(numberOfPage) {
      return `<div class="page-number" onclick="CrewArticleListApp.showArticles(${numberOfPage})">${numberOfPage}</div>`
    }

    nextBarTemplate(numberOfPage) {
      return `<div class="next-bar" onclick="CrewArticleListApp.showArticles(${numberOfPage})">다음</div>`
    }

    prevBarTemplate(numberOfPage) {
      return `<div class="prev-bar" onclick="CrewArticleListApp.showArticles(${numberOfPage})">이전</div>`
    }
  }

  return new Templates()
})()
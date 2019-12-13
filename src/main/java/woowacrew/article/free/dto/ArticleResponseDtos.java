package woowacrew.article.free.dto;

import java.util.List;

public class ArticleResponseDtos {
    public static final int INDEX_PARAMETER = 1;
    private int pageNumber;
    private int totalPages;
    private List<ArticleResponseDto> articles;

    private ArticleResponseDtos() {
    }

    public ArticleResponseDtos(int pageNumber, int totalPages, List<ArticleResponseDto> articles) {
        this.pageNumber = pageNumber + INDEX_PARAMETER;
        this.totalPages = totalPages;
        this.articles = articles;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ArticleResponseDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleResponseDto> articles) {
        this.articles = articles;
    }
}

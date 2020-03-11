package woowacrew.article.anonymous.dto;

import java.util.List;

public class AnonymousArticleResponseDtos {
    public static final int INDEX_PARAMETER = 1;

    private int pageNumber;
    private int totalPages;
    private List<AnonymousArticleResponseDto> articles;

    private AnonymousArticleResponseDtos() {
    }

    public AnonymousArticleResponseDtos(int pageNumber, int totalPages, List<AnonymousArticleResponseDto> articles) {
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

    public List<AnonymousArticleResponseDto> getArticles() {
        return articles;
    }

    public void setArticles(List<AnonymousArticleResponseDto> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "AnonymousArticleResponseDtos{" +
                "pageNumber=" + pageNumber +
                ", totalPages=" + totalPages +
                ", articles=" + articles +
                '}';
    }
}

package woowacrew.feed.dto;

import java.util.List;

public class FeedArticleResponseDtos {
    public static final int INDEX_PARAMETER = 1;
    private int pageNumber;
    private int totalPages;
    private List<FeedArticleResponseDto> articles;

    private FeedArticleResponseDtos() {
    }

    public FeedArticleResponseDtos(int pageNumber, int totalPages, List<FeedArticleResponseDto> articles) {
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

    public List<FeedArticleResponseDto> getArticles() {
        return articles;
    }

    public void setArticles(List<FeedArticleResponseDto> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "FeedArticleResponseDtos{" +
                "pageNumber=" + pageNumber +
                ", totalPages=" + totalPages +
                ", articles=" + articles +
                '}';
    }
}

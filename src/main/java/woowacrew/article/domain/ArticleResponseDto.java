package woowacrew.article.domain;

import woowacrew.user.domain.UserContext;

import java.util.Date;

public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserContext userContext;
    private Date createdDate;
    private Date lastModifiedDate;

    private ArticleResponseDto() {
    }

    public ArticleResponseDto(Long id, String title, String content, UserContext userContext, Date createdDate, Date lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userContext = userContext;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
}

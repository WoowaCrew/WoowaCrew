package woowacrew.article.domain;

import woowacrew.user.domain.UserResponseDto;

import java.util.Date;

public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserResponseDto userResponseDto;
    private Date createdDate;
    private Date lastModifiedDate;

    private ArticleResponseDto() {
    }

    public ArticleResponseDto(Long id, String title, String content, UserResponseDto userResponseDto, Date createdDate, Date lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userResponseDto = userResponseDto;
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

    public UserResponseDto getUserResponseDto() {
        return userResponseDto;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
}

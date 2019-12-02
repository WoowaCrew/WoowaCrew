package woowacrew.article.domain;

import woowacrew.user.domain.UserDto;

import java.util.Date;

public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserDto userDto;
    private Date createdDate;
    private Date lastModifiedDate;

    private ArticleResponseDto() {
    }

    public ArticleResponseDto(Long id, String title, String content, UserDto userDto, Date createdDate, Date lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
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

    public UserDto getUserDto() {
        return userDto;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
}

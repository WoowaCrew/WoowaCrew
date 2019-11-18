package woowacrew.article.domain;

import woowacrew.user.domain.UserDto;

import java.util.Date;

public class ArticleResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final UserDto userDto;
    private final Date createdDate;
    private final Date lastModifiedDate;

    public ArticleResponse(Long id, String title, String content, UserDto userDto, Date createdDate, Date lastModifiedDate) {
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

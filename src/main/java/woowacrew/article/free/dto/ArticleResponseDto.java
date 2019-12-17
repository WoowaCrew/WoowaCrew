package woowacrew.article.free.dto;

import woowacrew.user.dto.UserResponseDto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserResponseDto userResponseDto;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private ArticleResponseDto() {
    }

    public ArticleResponseDto(Long id, String title, String content, UserResponseDto userResponseDto, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleResponseDto that = (ArticleResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(userResponseDto, that.userResponseDto) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, userResponseDto, createdDate, lastModifiedDate);
    }
}

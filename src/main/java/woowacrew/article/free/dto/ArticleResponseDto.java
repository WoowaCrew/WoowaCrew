package woowacrew.article.free.dto;

import woowacrew.user.dto.UserResponseDto;

import java.time.LocalDate;

public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserResponseDto userResponseDto;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

    private ArticleResponseDto() {
    }

    public ArticleResponseDto(Long id, String title, String content, UserResponseDto userResponseDto, LocalDate createdDate, LocalDate lastModifiedDate) {
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }
}

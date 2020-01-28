package woowacrew.comment.dto;

import woowacrew.user.dto.UserResponseDto;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private Long id;
    private UserResponseDto userResponseDto;
    private String content;
    private LocalDateTime createDateTime;

    public CommentResponseDto() {
    }

    public CommentResponseDto(Long id, UserResponseDto userResponseDto, String content, LocalDateTime createDateTime) {
        this.id = id;
        this.userResponseDto = userResponseDto;
        this.content = content;
        this.createDateTime = createDateTime;
    }

    public Long getId() {
        return id;
    }

    public UserResponseDto getUserResponseDto() {
        return userResponseDto;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    @Override
    public String toString() {
        return "CommentResponseDto{" +
                "id=" + id +
                ", userResponseDto=" + userResponseDto +
                ", content='" + content + '\'' +
                ", createDateTime=" + createDateTime +
                '}';
    }
}

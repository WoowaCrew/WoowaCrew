package woowacrew.comment.dto;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private Long id;
    private String userNickName;
    private String content;
    private LocalDateTime createDateTime;

    public CommentResponseDto() {
    }

    public CommentResponseDto(Long id, String userNickName, String content, LocalDateTime createDateTime) {
        this.id = id;
        this.userNickName = userNickName;
        this.content = content;
        this.createDateTime = createDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }
}

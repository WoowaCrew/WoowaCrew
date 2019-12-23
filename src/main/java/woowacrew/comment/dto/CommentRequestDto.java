package woowacrew.comment.dto;

public class CommentRequestDto {
    private String content;

    private CommentRequestDto() {
    }

    public CommentRequestDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

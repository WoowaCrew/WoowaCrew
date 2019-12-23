package woowacrew.comment.dto;

public class CommentUpdateDto {
    private String updateContent;

    private CommentUpdateDto() {
    }

    public CommentUpdateDto(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUpdateContent() {
        return updateContent;
    }
}

package woowacrew.comment.dto;

public class CommentUpdateDto {
    private Long commentId;
    private String updateContent;

    public CommentUpdateDto(Long commentId, String updateContent) {
        this.commentId = commentId;
        this.updateContent = updateContent;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getUpdateContent() {
        return updateContent;
    }
}

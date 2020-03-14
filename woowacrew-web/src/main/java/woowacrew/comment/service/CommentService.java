package woowacrew.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.comment.domain.Comment;
import woowacrew.comment.dto.CommentRequestDto;
import woowacrew.comment.dto.CommentResponseDto;
import woowacrew.comment.dto.CommentUpdateDto;
import woowacrew.comment.utils.CommentConverter;
import woowacrew.user.dto.UserContext;

import java.util.List;

@Transactional
@Service
public class CommentService {
    private CommentInternalService commentInternalService;

    public CommentService(CommentInternalService commentInternalService) {
        this.commentInternalService = commentInternalService;
    }

    public CommentResponseDto save(Long articleId, CommentRequestDto commentRequestDto, UserContext userContext) {
        Comment comment = commentInternalService.save(articleId, commentRequestDto, userContext);
        return CommentConverter.toDto(comment);
    }

    public List<CommentResponseDto> findAll(Long articleId) {
        List<Comment> comments = commentInternalService.findAll(articleId);
        return CommentConverter.toDtos(comments);
    }

    public CommentResponseDto update(Long commentId, CommentUpdateDto commentUpdateDto, UserContext userContext) {
        return CommentConverter.toDto(commentInternalService.update(commentId, commentUpdateDto, userContext));
    }

    public void delete(Long commentId, UserContext userContext) {
        commentInternalService.delete(commentId, userContext);
    }
}

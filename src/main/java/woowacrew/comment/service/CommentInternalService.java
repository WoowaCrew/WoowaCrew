package woowacrew.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.comment.domain.Comment;
import woowacrew.comment.domain.CommentRepository;
import woowacrew.comment.dto.CommentRequestDto;
import woowacrew.comment.dto.CommentUpdateDto;
import woowacrew.comment.service.exception.NotFoundCommentException;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

import java.util.List;

@Service
public class CommentInternalService {
    private CommentRepository commentRepository;
    private ArticleInternalService articleInternalService;
    private UserInternalService userInternalService;

    public CommentInternalService(CommentRepository commentRepository, ArticleInternalService articleInternalService, UserInternalService userInternalService) {
        this.commentRepository = commentRepository;
        this.articleInternalService = articleInternalService;
        this.userInternalService = userInternalService;
    }

    public Comment save(Long articleId, CommentRequestDto commentRequestDto, UserContext userContext) {
        Article article = articleInternalService.findById(articleId);
        User user = userInternalService.findById(userContext.getId());

        return commentRepository.save(new Comment(user, commentRequestDto.getContent(), article));
    }

    @Transactional(readOnly = true)
    public List<Comment> findAll(Long articleId) {
        Article article = articleInternalService.findById(articleId);
        return commentRepository.findAllByArticle(article);
    }

    public Comment update(Long commentId, CommentUpdateDto commentUpdateDto, UserContext userContext) {
        Comment comment = findById(commentId);
        User user = userInternalService.findById(userContext.getId());
        comment.update(user, commentUpdateDto.getUpdateContent());

        return comment;
    }

    @Transactional(readOnly = true)
    protected Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
    }

    public void delete(Long commentId, UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        Comment comment = findById(commentId);

        if (comment.isAuthor(user)) {
            commentRepository.delete(comment);
            return;
        }
        throw new MisMatchUserException();
    }
}

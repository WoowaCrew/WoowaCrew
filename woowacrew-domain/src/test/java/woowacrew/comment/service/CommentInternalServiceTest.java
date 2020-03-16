package woowacrew.comment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.comment.domain.Comment;
import woowacrew.comment.domain.CommentRepository;
import woowacrew.comment.dto.CommentRequestDto;
import woowacrew.comment.dto.CommentUpdateDto;
import woowacrew.common.service.FieldSetter;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentInternalServiceTest {

    @InjectMocks
    private CommentInternalService commentInternalService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserInternalService userInternalService;

    @Mock
    private ArticleInternalService articleInternalService;

    private User author = new User("zino", new Degree());
    private Article article = new Article("title", "content", author);
    private UserContext userContext = new UserContext(1L, "test", "test", UserRole.ROLE_CREW);
    private Comment comment = new Comment(author, "testContent", article);

    @Test
    void 저장_테스트() {
        given(articleInternalService.findById(1L)).willReturn((Article) article);
        given(userInternalService.findById(1L)).willReturn(author);
        given(commentRepository.save(comment)).willReturn(comment);
        CommentRequestDto commentRequestDto = new CommentRequestDto("testContent");

        assertThat(commentInternalService.save(1L, commentRequestDto, userContext).getContent()).isEqualTo("testContent");
    }

    @Test
    void 조회_articleId_테스트() {
        given(articleInternalService.findById(1L)).willReturn((Article) article);
        given(commentRepository.findAllByArticle(article)).willReturn(Collections.singletonList(comment));

        List<Comment> comments = commentInternalService.findAll(1L);

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getContent()).isEqualTo("testContent");
    }

    @Test
    void 업데이트_테스트() {
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
        given(userInternalService.findById(1L)).willReturn(author);
        FieldSetter.set(comment, "id", 1L);
        CommentUpdateDto commentUpdateDto = new CommentUpdateDto("update content");

        Comment updatedComment = commentInternalService.update(1L, commentUpdateDto, userContext);

        assertThat(comment).isEqualTo(updatedComment);
        assertThat(updatedComment.getId()).isEqualTo(1L);
        assertThat(updatedComment.getContent()).isEqualTo("update content");
    }

    @Test
    void 삭제_테스트() {
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
        given(userInternalService.findById(1L)).willReturn(author);

        assertDoesNotThrow(() -> commentInternalService.delete(1L, userContext));
    }
}
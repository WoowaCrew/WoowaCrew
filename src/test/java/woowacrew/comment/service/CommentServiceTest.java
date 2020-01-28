package woowacrew.comment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.article.free.domain.Article;
import woowacrew.comment.domain.Comment;
import woowacrew.comment.dto.CommentRequestDto;
import woowacrew.comment.dto.CommentResponseDto;
import woowacrew.comment.dto.CommentUpdateDto;
import woowacrew.common.service.FieldSetter;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentInternalService commentInternalService;

    private CommentRequestDto commentRequestDto = new CommentRequestDto("textContent");
    private User author = new User("zino", new Degree());
    private Article article = new Article("title", "content", author);
    private UserContext userContext = new UserContext(1L, "test", "test", UserRole.ROLE_CREW);
    private Comment comment = new Comment(author, commentRequestDto.getContent(), article);
    private Comment comment2 = new Comment(author, commentRequestDto.getContent(), article);


    @Test
    void Comment_생성_테스트() {
        given(commentInternalService.save(1L, commentRequestDto, userContext)).willReturn(comment);
        CommentResponseDto commentResponseDto = commentService.save(1L, commentRequestDto, userContext);

        assertThat(commentResponseDto.getUserResponseDto().getNickname()).isEqualTo(author.getNickname());
        assertThat(commentResponseDto.getContent()).isEqualTo(commentRequestDto.getContent());
    }

    @Test
    void Comment_ArticleId로_조회_테스트() {
        FieldSetter.set(article, "id", 1L);
        given(commentInternalService.findAll(article.getId())).willReturn(Arrays.asList(comment, comment2));
        List<CommentResponseDto> commentResponseDtos = commentService.findAll(article.getId());

        assertThat(commentResponseDtos.size()).isEqualTo(2);
    }

    @Test
    void Comment_업데이트_테스트() {
        FieldSetter.set(comment, "id", 1L);
        CommentUpdateDto commentUpdateDto = new CommentUpdateDto("updatedContent");
        Comment updatedComment = new Comment(author, commentUpdateDto.getUpdateContent(), article);
        given(commentInternalService.update(comment.getId(), commentUpdateDto, userContext)).willReturn(updatedComment);

        CommentResponseDto commentResponseDto = commentService.update(comment.getId(), commentUpdateDto, userContext);

        assertThat(commentResponseDto.getContent()).isEqualTo(commentUpdateDto.getUpdateContent());
    }
}
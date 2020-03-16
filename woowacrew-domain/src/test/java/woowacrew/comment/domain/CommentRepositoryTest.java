package woowacrew.comment.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import woowacrew.JpaTestConfiguration;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.domain.ArticleRepository;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.domain.DegreeRepository;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.domain.exception.DegreeBoundException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
class CommentRepositoryTest {
    @Autowired
    private DegreeRepository degreeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void Article이_삭제되면_그에따른_comment도_삭제된다() {
        Degree degree = degreeRepository.findByDegreeNumber(0)
                .orElseThrow(DegreeBoundException::new);
        User author = userRepository.save(new User("oauthId", degree));
        Article article = articleRepository.save(new Article("title", "content", author));
        Comment comment = commentRepository.save(new Comment(author, "content", article));

        articleRepository.deleteById(article.getId());
        articleRepository.flush();

        assertThat(commentRepository.existsById(comment.getId())).isFalse();
    }
}
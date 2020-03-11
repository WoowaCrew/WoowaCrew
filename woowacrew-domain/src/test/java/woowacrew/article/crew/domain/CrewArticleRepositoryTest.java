package woowacrew.article.crew.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import woowacrew.JpaTestConfiguration;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.service.exception.NotExistUserException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
class CrewArticleRepositoryTest {
    @Autowired
    private CrewArticleRepository crewArticleRepository;
    @Autowired
    private UserRepository userRepository;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 20);
    }

    @Test
    void 기수에_맞는_글을_작성하는지_테스트() {
        User author = userRepository.findById(1L).orElseThrow(NotExistUserException::new);
        Page<CrewArticle> crewArticles = crewArticleRepository.findAllByBasicArticleFormAuthorDegree(author.getDegree(), pageable);

        assertThat(crewArticles.getTotalElements()).isEqualTo(1);
    }
}
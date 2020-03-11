package woowacrew.article.anonymous.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.domain.AnonymousArticleRepository;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleUpdateDto;
import woowacrew.article.anonymous.exception.MismatchPasswordException;
import woowacrew.article.anonymous.utils.AnonymousArticleConverter;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.common.service.FieldSetter;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnonymousArticleInternalServiceTest {

    @Mock
    private AnonymousArticleRepository anonymousArticleRepository;

    @InjectMocks
    private AnonymousArticleInternalService anonymousArticleInternalService;

    @Test
    @DisplayName("익명 게시글 저장")
    void save() {
        String title = "title";
        String content = "content";
        String signingKey = "password";
        AnonymousArticleRequestDto anonymousArticleRequestDto
                = new AnonymousArticleRequestDto(title, content, signingKey);
        AnonymousArticle anonymousArticle =
                AnonymousArticleConverter.toEntity(anonymousArticleRequestDto);

        when(anonymousArticleRepository.save(any(AnonymousArticle.class))).thenReturn(anonymousArticle);

        AnonymousArticle savedAnonymousArticle
                = anonymousArticleInternalService.save(anonymousArticleRequestDto);

        assertThat(savedAnonymousArticle.getTitle()).isEqualTo(title);
        assertThat(savedAnonymousArticle.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("익명 게시글 목록 조회")
    void findAll() {
        int articleSize = 10;
        List<AnonymousArticle> articles = createAnonymousArticles(articleSize);
        Pageable pageable =
                PageRequest.of(1, AnonymousArticleInternalService.DEFAULT_ANONYMOUS_ARTICLE_PAGE_SIZE);

        when(anonymousArticleRepository.findAll(pageable)).thenReturn(new PageImpl<>(articles));

        Page<AnonymousArticle> actualArticles = anonymousArticleInternalService.findAll(pageable);

        assertThat(actualArticles.getTotalElements()).isEqualTo(articleSize);
    }

    @Test
    @DisplayName("승인되지 않은 게시글 목록 조회")
    void findUnapprovedArticles() {
        int articleSize = 10;
        List<AnonymousArticle> articles = createAnonymousArticles(articleSize);
        Pageable pageable =
                PageRequest.of(1, AnonymousArticleInternalService.DEFAULT_ANONYMOUS_ARTICLE_PAGE_SIZE);

        when(anonymousArticleRepository.findAll(pageable)).thenReturn(new PageImpl<>(articles));

        Page<AnonymousArticle> actualArticles = anonymousArticleInternalService.findAll(pageable);

        assertThat(actualArticles.getTotalElements()).isEqualTo(articleSize);
    }

    @Test
    @DisplayName("익명 게시글 승인")
    void approve() {
        AnonymousArticle anonymousArticle = new AnonymousArticle("title", "content", "password");
        FieldSetter.set(anonymousArticle, "id", 1L);

        when(anonymousArticleRepository.findById(1L)).thenReturn(Optional.of(anonymousArticle));

        AnonymousArticle approvedAnonymousArticle = anonymousArticleInternalService.approve(1L);

        assertThat(approvedAnonymousArticle.isApproved()).isTrue();
    }

    @Test
    @DisplayName("일치하는 비밀번호로 익명 게시글 수정")
    void updateWithMatchPassword() {
        AnonymousArticleUpdateDto anonymousArticleUpdateDto =
                new AnonymousArticleUpdateDto("new title", "new content", "password");

        AnonymousArticle anonymousArticle = new AnonymousArticle("title", "content", "password");
        FieldSetter.set(anonymousArticle, "id", 1L);

        when(anonymousArticleRepository.findById(1L)).thenReturn(Optional.of(anonymousArticle));

        AnonymousArticle updatedAnonymousArticle = anonymousArticleInternalService.update(1L, anonymousArticleUpdateDto);

        assertThat(updatedAnonymousArticle.getTitle()).isEqualTo("new title");
        assertThat(updatedAnonymousArticle.getContent()).isEqualTo("new content");
    }

    @Test
    @DisplayName("불일치하는 비밀번호로 익명 게시글 수정")
    void updateWithMismatchPassword() {
        AnonymousArticleUpdateDto anonymousArticleUpdateDto =
                new AnonymousArticleUpdateDto(
                        "new title",
                        "new content",
                        "invalid password"
                );

        AnonymousArticle anonymousArticle = new AnonymousArticle("title", "content", "password");
        FieldSetter.set(anonymousArticle, "id", 1L);

        when(anonymousArticleRepository.findById(1L)).thenReturn(Optional.of(anonymousArticle));

        assertThrows(MismatchPasswordException.class,
                () -> anonymousArticleInternalService.update(1L, anonymousArticleUpdateDto));
    }

    @Test
    @DisplayName("일치하는 비밀번호로 익명 게시글 삭제")
    void deleteWithMatchPassword() {
        AnonymousArticle anonymousArticle = new AnonymousArticle("title", "content", "password");
        FieldSetter.set(anonymousArticle, "id", 1L);

        when(anonymousArticleRepository.findById(1L)).thenReturn(Optional.of(anonymousArticle));

        assertDoesNotThrow(() -> anonymousArticleInternalService.delete(1L, "password"));
    }

    @Test
    @DisplayName("불일치하는 비밀번호로 익명 게시글 삭제")
    void deleteWithMismatchPassword() {
        AnonymousArticle anonymousArticle = new AnonymousArticle("title", "content", "password");
        FieldSetter.set(anonymousArticle, "id", 1L);

        when(anonymousArticleRepository.findById(1L)).thenReturn(Optional.of(anonymousArticle));

        assertThrows(MismatchPasswordException.class,
                () -> anonymousArticleInternalService.delete(1L, "invalid password"));
    }

    @Test
    @DisplayName("익명 게시글 일치하는 비밀번호로 확인")
    void checkWithMatchPassword() {
        AnonymousArticle anonymousArticle = new AnonymousArticle("title", "content", "password");
        FieldSetter.set(anonymousArticle, "id", 1L);

        when(anonymousArticleRepository.findById(1L)).thenReturn(Optional.of(anonymousArticle));

        assertDoesNotThrow(() -> anonymousArticleInternalService.checkSigningKey(1L, "password"));
    }

    @Test
    @DisplayName("익명 게시글 불일치하는 비밀번호로 확인")
    void checkWithMismatchPassword() {
        AnonymousArticle anonymousArticle = new AnonymousArticle("title", "content", "password");
        FieldSetter.set(anonymousArticle, "id", 1L);

        when(anonymousArticleRepository.findById(1L)).thenReturn(Optional.of(anonymousArticle));

        assertThrows(MismatchPasswordException.class,
                () -> anonymousArticleInternalService.checkSigningKey(1L, "invalid password"));
    }

    private List<AnonymousArticle> createAnonymousArticles(int numberOfArticle) {
        String signingKey = "password";
        List<AnonymousArticle> articles = new ArrayList<>();
        for (int i = 0; i < numberOfArticle; i++) {
            articles.add(new AnonymousArticle(String.valueOf(i), String.valueOf(i), signingKey));
        }
        return articles;
    }

    @Test
    void 정상적으로_게시물을_가져온다() {
        SearchSpec<AnonymousArticle> searchSpec = SearchSpec.init("anonymousArticleTitle", "test", new SearchType[]{SearchType.ANONYMOUS_ARTICLE_TITLE, SearchType.ANONYMOUS_ARTICLE_TITLE_WITH_CONTENT});
        PageRequest pageable = PageRequest.of(0, 20);

        anonymousArticleInternalService.findAllByIsApproved(searchSpec, pageable);

        verify(anonymousArticleRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void 유효하지_않은_페이지수로_인해_게시물을_가져오지_못한다() {
        SearchSpec<AnonymousArticle> searchSpec = SearchSpec.init("anonymousArticleTitle", "test", new SearchType[]{SearchType.ANONYMOUS_ARTICLE_TITLE, SearchType.ANONYMOUS_ARTICLE_TITLE_WITH_CONTENT});
        PageRequest pageable = PageRequest.of(0, 19);

        assertThrows(InvalidPageRequstException.class, () -> anonymousArticleInternalService.findAllByIsApproved(searchSpec, pageable));
    }
}
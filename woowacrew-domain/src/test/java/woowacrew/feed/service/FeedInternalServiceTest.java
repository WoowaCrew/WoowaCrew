package woowacrew.feed.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.feed.domain.*;
import woowacrew.feed.dto.FeedSourceRequestDto;
import woowacrew.feed.dto.FeedSourceUpdateRequestDto;
import woowacrew.feed.exception.AlreadyExistSourceUrlException;
import woowacrew.feed.exception.NotFoundFeedSourceException;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedInternalServiceTest {
    @Mock
    private FeedArticleRepository feedArticleRepository;
    @Mock
    private FeedSourceRepository feedSourceRepository;
    @InjectMocks
    private FeedInternalService feedInternalService;

    private FeedSource feedSource;
    private FeedArticles feedArticles;

    @BeforeEach
    void setUp() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        feedSource = new FeedSource(sourceUrl, "description");
        feedArticles = feedSource.createFeedArticles();
    }

    @Test
    void 정상xml을_입력했을_때_제대로_저장하는지_테스트() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        FeedSourceRequestDto feedSourceRequestDto = new FeedSourceRequestDto(sourceUrl, "테스트용 xml");

        when(feedSourceRepository.existsBySourceUrl(any())).thenReturn(false);
        when(feedArticleRepository.saveAll(anyList())).thenReturn(new ArrayList<>());
        when(feedSourceRepository.save(any(FeedSource.class))).thenReturn(feedSource);

        FeedSource savedFeedSource = feedInternalService.registerFeedSource(feedSourceRequestDto);
        assertThat(savedFeedSource.getSourceUrl()).isEqualTo(feedSource.getSourceUrl());
    }

    @Test
    void 이미_존재하는_url은_저장하지_않는다() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        FeedSourceRequestDto feedSourceRequestDto = new FeedSourceRequestDto(sourceUrl, "테스트용 xml");

        when(feedSourceRepository.existsBySourceUrl(any())).thenReturn(true);

        assertThrows(AlreadyExistSourceUrlException.class, () -> feedInternalService.registerFeedSource(feedSourceRequestDto));
    }

    @Test
    void 요청한_페이지_사이즈가_Default가_아니면_에러를_발생() {
        Pageable pageable = PageRequest.of(0, 30);
        assertThrows(InvalidPageRequstException.class, () -> feedInternalService.findAllFeedArticles(pageable));
    }

    @Test
    void Page리스트_리턴_테스트() {
        Pageable pageable = PageRequest.of(0, 20);
        List<FeedArticle> articles = Arrays.asList(new FeedArticle("title", "link", LocalDateTime.now(), feedSource));
        when(feedArticleRepository.findAll(pageable)).thenReturn(new PageImpl<>(articles));

        Page<FeedArticle> feedArticles = feedInternalService.findAllFeedArticles(pageable);
        assertThat(feedArticles.getTotalElements()).isEqualTo(1);
    }

    @Test
    void 피드게시글_업데이트_테스트() {
        when(feedSourceRepository.findAll()).thenReturn(Collections.singletonList(feedSource));
        when(feedArticleRepository.findByFeedSource(any(FeedSource.class))).thenReturn(new ArrayList<>());
        when(feedArticleRepository.saveAll(anyList())).thenReturn(feedArticles.getFeedArticles());

        List<FeedArticle> feedArticles = feedInternalService.updateFeedArticles();
        assertThat(feedArticles.size()).isEqualTo(3);
    }

    @Test
    void 정상_FeedSource_description_업데이트_테스트() {
        String updatedDescription = "updatedDescription";
        FeedSourceUpdateRequestDto requestDto = new FeedSourceUpdateRequestDto(updatedDescription);
        Long feedSourceId = 1L;

        when(feedSourceRepository.findById(feedSourceId)).thenReturn(Optional.ofNullable(feedSource));

        FeedSource updatedFeedSource = feedInternalService.updateFeedSourceDescription(feedSourceId, requestDto);

        assertThat(updatedFeedSource.getDescription()).isEqualTo(updatedDescription);
    }

    @Test
    void 존재하지_않는_FeedSource_description_업데이트_시_에러를_발생하는지_테스트() {
        String updatedDescription = "updatedDescription";
        FeedSourceUpdateRequestDto requestDto = new FeedSourceUpdateRequestDto(updatedDescription);
        Long feedSourceId = 1L;

        when(feedSourceRepository.findById(feedSourceId)).thenReturn(Optional.ofNullable(null));

        assertThrows(NotFoundFeedSourceException.class,
                () -> feedInternalService.updateFeedSourceDescription(feedSourceId, requestDto));
    }

    @Test
    void FeedSource_list를_불러오는지_테스트() {
        List<FeedSource> feedSources = Collections.singletonList(feedSource);

        when(feedSourceRepository.findAll()).thenReturn(feedSources);

        List<FeedSource> actualFeedSources = feedInternalService.findAllFeedSources();

        assertThat(actualFeedSources.size()).isEqualTo(1);
    }

    @Test
    void delete_FeedSource_ById() {
        Long feedSourceId = 1L;
        feedInternalService.deleteFeedSource(feedSourceId);
        verify(feedSourceRepository, times(1)).deleteById(feedSourceId);
    }

    @Test
    void 정상적으로_검색한다() {
        SearchSpec<FeedArticle> searchSpec = SearchSpec.init("feedTitle", "test", new SearchType[]{SearchType.FEED_TITLE});
        Specification<FeedArticle> specification = searchSpec.getSpecification();
        PageRequest pageable = PageRequest.of(0, 20);

        feedInternalService.findAll(specification, pageable);
        verify(feedArticleRepository, times(1)).findAll(specification, pageable);
    }

    @Test
    void pageable_사이즈로_인해_검색을_실패한다() {
        SearchSpec<FeedArticle> searchSpec = SearchSpec.init("feedTitle", "test", new SearchType[]{SearchType.FEED_TITLE});
        Specification<FeedArticle> specification = searchSpec.getSpecification();
        PageRequest pageable = PageRequest.of(0, 19);

        assertThrows(InvalidPageRequstException.class, () -> feedInternalService.findAll(specification, pageable));
    }
}
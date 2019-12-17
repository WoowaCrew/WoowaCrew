package woowacrew.feed.service;

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
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedArticleRepository;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.domain.FeedSourceRepository;
import woowacrew.feed.dto.FeedSourceDto;
import woowacrew.feed.exception.AlreadyExistSourceUrlException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedInternalServiceTest {
    @Mock
    private FeedArticleRepository feedArticleRepository;
    @Mock
    private FeedSourceRepository feedSourceRepository;
    @InjectMocks
    private FeedInternalService feedInternalService;

    private static final FeedSource feedSource = new FeedSource("source", "description");
    private static final FeedArticle feedArticle = new FeedArticle("title", "link", LocalDateTime.now(), feedSource);

    @Test
    void 정상xml을_입력했을_때_제대로_저장하는지_테스트() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        FeedSourceDto feedSourceDto = new FeedSourceDto(sourceUrl, "테스트용 xml");

        when(feedSourceRepository.existsBySourceUrl(any())).thenReturn(false);
        when(feedArticleRepository.saveAll(anyList())).thenReturn(new ArrayList<>());
        when(feedSourceRepository.save(any(FeedSource.class))).thenReturn(feedSource);

        FeedSource savedFeedSource = feedInternalService.registerFeedSource(feedSourceDto);
        assertThat(savedFeedSource.getSourceUrl()).isEqualTo(feedSource.getSourceUrl());
    }

    @Test
    void 이미_존재하는_url은_저장하지_않는() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        FeedSourceDto feedSourceDto = new FeedSourceDto(sourceUrl, "테스트용 xml");

        when(feedSourceRepository.existsBySourceUrl(any())).thenReturn(true);

        assertThrows(AlreadyExistSourceUrlException.class, () -> feedInternalService.registerFeedSource(feedSourceDto));
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
    void 업데이트_테스트() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        FeedSource feedSource = new FeedSource(sourceUrl, "테스트용 url");
        when(feedSourceRepository.findAll()).thenReturn(Arrays.asList(feedSource));
        when(feedArticleRepository.existsByLink(any())).thenReturn(false);
        when(feedArticleRepository.save(any(FeedArticle.class))).thenReturn(feedArticle);

        List<FeedArticle> feedArticles = feedInternalService.updateFeed();
        assertThat(feedArticles.size()).isEqualTo(3);
    }
}
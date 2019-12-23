package woowacrew.feed.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.dto.*;
import woowacrew.feed.utils.FeedConverter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FeedServiceTest {
    @Mock
    private FeedInternalService feedInternalService;
    @InjectMocks
    private FeedService feedService;

    @Test
    void FeedSource가_정상적으로_저장됐다면_RegisterDto를_리턴한다() {
        String url = "url";
        String description = "description";
        FeedSourceRequestDto feedSourceRequestDto = new FeedSourceRequestDto(url, description);
        FeedSource feedSource = FeedConverter.toFeedSource(feedSourceRequestDto);

        when(feedInternalService.registerFeedSource(feedSourceRequestDto)).thenReturn(feedSource);

        FeedSourceResponseDto actualRegisterDto = feedService.registerFeedSource(feedSourceRequestDto);

        assertThat(actualRegisterDto.getSourceUrl()).isEqualTo(url);
        assertThat(actualRegisterDto.getDescription()).isEqualTo(description);
    }

    @Test
    void findAll() {
        FeedArticle feedArticle = new FeedArticle("title", "link", LocalDateTime.now(), new FeedSource("source", "description"));
        Pageable pageable = PageRequest.of(0, 20);
        when(feedInternalService.findAllFeedArticles(pageable)).thenReturn(new PageImpl<>(Arrays.asList(feedArticle)));

        FeedArticleResponseDtos feedArticles = feedService.findAllFeedArticles(pageable);

        assertThat(feedArticles.getPageNumber()).isEqualTo(1);
        assertThat(feedArticles.getTotalPages()).isEqualTo(1);
    }

    @Test
    void updateFeedArticles() {
        String title = "title";
        FeedArticle feedArticle = new FeedArticle(title, "link", LocalDateTime.now(), new FeedSource("source", "description"));
        when(feedInternalService.updateFeedArticles()).thenReturn(Collections.singletonList(feedArticle));

        List<FeedArticleResponseDto> feedArticleResponseDtos = feedService.updateFeed();

        assertThat(feedArticleResponseDtos.size()).isEqualTo(1);
        assertThat(feedArticleResponseDtos.get(0).getTitle()).isEqualTo(title);
    }

    @Test
    void FeedSource_description_업데이트_시_ResponsedDto가_정상적으로_리턴되는지_테스트() {
        Long feedSourceId = 1L;
        FeedSourceUpdateRequestDto updateRequestDto = new FeedSourceUpdateRequestDto("updatedDescription");
        String source = "source";
        String updatedDescription = "updatedDescription";
        FeedSource updatedFeedSource = new FeedSource(source, updatedDescription);

        when(feedInternalService.updateFeedSourceDescription(feedSourceId, updateRequestDto)).thenReturn(updatedFeedSource);

        FeedSourceResponseDto responseDto = feedService.updateFeedSourceDescription(feedSourceId, updateRequestDto);
        assertThat(responseDto.getSourceUrl()).isEqualTo(source);
        assertThat(responseDto.getDescription()).isEqualTo(updatedDescription);
    }

    @Test
    void FeedSources들을_ResponseDto로_잘_변환하는지_테스트() {
        String sourceUrl = "source";
        FeedSource feedSource = new FeedSource(sourceUrl, "description");
        List<FeedSource> feedSources = Collections.singletonList(feedSource);

        when(feedInternalService.findAllFeedSources()).thenReturn(feedSources);

        List<FeedSourceResponseDto> responseDtos = feedService.findAllFeedSources();

        assertThat(responseDtos.size()).isEqualTo(1);
        assertThat(responseDtos.get(0).getSourceUrl()).isEqualTo(sourceUrl);
    }

    @Test
    void delete_feedSource_테스트() {
        feedService.deleteFeedSource(1L);

        verify(feedInternalService, times(1)).deleteFeedSource(1L);
    }
}
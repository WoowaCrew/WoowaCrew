package woowacrew.article.anonymous.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.domain.AnonymousArticleRepository;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.utils.AnonymousArticleConverter;

@Service
@Transactional
public class AnonymousArticleInternalService {
    private final AnonymousArticleRepository anonymousArticleRepository;

    public AnonymousArticleInternalService(AnonymousArticleRepository anonymousArticleRepository) {
        this.anonymousArticleRepository = anonymousArticleRepository;
    }

    public AnonymousArticle save(AnonymousArticleRequestDto anonymousArticleRequestDto) {
        AnonymousArticle anonymousArticle =
                AnonymousArticleConverter.anonymousArticleRequestDtoToArticle(anonymousArticleRequestDto);
        return anonymousArticleRepository.save(anonymousArticle);
    }
}

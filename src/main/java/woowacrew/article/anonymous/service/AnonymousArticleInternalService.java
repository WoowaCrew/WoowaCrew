package woowacrew.article.anonymous.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.domain.AnonymousArticleRepository;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleUpdateDto;
import woowacrew.article.anonymous.exception.NotFoundAnonymousArticleException;
import woowacrew.article.anonymous.utils.AnonymousArticleConverter;
import woowacrew.article.free.exception.InvalidPageRequstException;

import java.util.List;

@Service
@Transactional
public class AnonymousArticleInternalService {
    public static final int DEFAULT_ANONYMOUS_ARTICLE_PAGE_SIZE = 20;

    private final AnonymousArticleRepository anonymousArticleRepository;

    public AnonymousArticleInternalService(AnonymousArticleRepository anonymousArticleRepository) {
        this.anonymousArticleRepository = anonymousArticleRepository;
    }

    public AnonymousArticle save(AnonymousArticleRequestDto anonymousArticleRequestDto) {
        AnonymousArticle anonymousArticle =
                AnonymousArticleConverter.toEntity(anonymousArticleRequestDto);
        return anonymousArticleRepository.save(anonymousArticle);
    }

    @Transactional(readOnly = true)
    public Page<AnonymousArticle> findAll(Pageable pageable) {
        checkPageSize(pageable);

        return anonymousArticleRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public AnonymousArticle findById(Long anonymousArticleId) {
        return anonymousArticleRepository
                .findById(anonymousArticleId)
                .orElseThrow(NotFoundAnonymousArticleException::new);
    }

    @Transactional(readOnly = true)
    public Page<AnonymousArticle> findByIsApproved(Pageable pageable, boolean isApproved) {
        checkPageSize(pageable);
        return anonymousArticleRepository.findByIsApproved(isApproved, pageable);
    }

    @Transactional(readOnly = true)
    public List<AnonymousArticle> findByIsApproved(boolean isApproved) {
        return anonymousArticleRepository.findByIsApproved(isApproved);
    }

    private void checkPageSize(Pageable pageable) {
        if (pageable.getPageSize() != DEFAULT_ANONYMOUS_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }
    }

    public AnonymousArticle update(Long anonymousArticleId, AnonymousArticleUpdateDto anonymousArticleUpdateDto) {
        AnonymousArticle anonymousArticle = findById(anonymousArticleId);
        anonymousArticle.update(
                anonymousArticleUpdateDto.getTitle(),
                anonymousArticleUpdateDto.getContent(),
                anonymousArticleUpdateDto.getPassword());
        return anonymousArticle;
    }

    public AnonymousArticle approve(Long anonymousArticleId) {
        AnonymousArticle anonymousArticle = findById(anonymousArticleId);
        anonymousArticle.approve();
        return anonymousArticle;
    }

    public void delete(Long anonymousArticleId, String password) {
        AnonymousArticle anonymousArticle = findById(anonymousArticleId);

        anonymousArticle.checkPassword(password);

        anonymousArticleRepository.delete(anonymousArticle);
    }
}

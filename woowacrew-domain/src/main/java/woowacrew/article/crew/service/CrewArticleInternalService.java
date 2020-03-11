package woowacrew.article.crew.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.crew.domain.CrewArticle;
import woowacrew.article.crew.domain.CrewArticleRepository;
import woowacrew.article.crew.exception.CrewArticleAccessDenyException;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.article.free.exception.NotFoundArticleException;
import woowacrew.degree.domain.Degree;
import woowacrew.search.domain.SearchSpec;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

@Service
@Transactional
public class CrewArticleInternalService {
    public static final int DEFAULT_ARTICLE_PAGE_SIZE = 20;

    private CrewArticleRepository crewArticleRepository;
    private UserInternalService userInternalService;

    @Autowired
    public CrewArticleInternalService(CrewArticleRepository crewArticleRepository, UserInternalService userInternalService) {
        this.crewArticleRepository = crewArticleRepository;
        this.userInternalService = userInternalService;
    }

    public CrewArticle save(ArticleRequestDto articleRequestDto, UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        CrewArticle article = new CrewArticle(articleRequestDto.getTitle(), articleRequestDto.getContent(), user);

        return crewArticleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public CrewArticle findById(long articleId, UserContext userContext) {
        User author = userInternalService.findById(userContext.getId());
        CrewArticle crewArticle = crewArticleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
        if (crewArticle.isAccessible(author)) {
            return crewArticle;
        }
        throw new CrewArticleAccessDenyException();
    }

    @Transactional(readOnly = true)
    public Page<CrewArticle> findAllByCrew(UserContext userContext, Pageable pageable) {
        if (pageable.getPageSize() != DEFAULT_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }
        User author = userInternalService.findById(userContext.getId());

        return crewArticleRepository.findAllByBasicArticleFormAuthorDegree(author.getDegree(), pageable);
    }

    public CrewArticle update(ArticleUpdateDto articleUpdateDto, UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        CrewArticle article = findById(articleUpdateDto.getArticleId(), userContext);

        article.update(user, articleUpdateDto.getTitle(), articleUpdateDto.getContent());

        return article;
    }

    public void delete(Long articleId, UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        CrewArticle article = findById(articleId, userContext);

        article.checkAuthor(user);

        crewArticleRepository.delete(article);
    }

    @Transactional(readOnly = true)
    public void checkAuthor(Long articleId, UserContext userContext) {
        CrewArticle article = findById(articleId, userContext);
        User user = userInternalService.findById(userContext.getId());

        article.checkAuthor(user);
    }

    @Transactional(readOnly = true)
    public Page<CrewArticle> findSearchedArticles(SearchSpec<CrewArticle> searchSpec, Pageable pageable, UserContext userContext) {
        if (pageable.getPageSize() != DEFAULT_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }

        User author = userInternalService.findById(userContext.getId());
        Degree degree = author.getDegree();
        Specification<CrewArticle> specification = searchSpec.and("degree", degree.getDegreeNumber());
        return crewArticleRepository.findAll(specification, pageable);
    }
}

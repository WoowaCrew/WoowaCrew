package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.domain.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleSearchSpec implements SearchSpec {
    private static final List<SearchType> ARTICLE_SEARCH_TYPES;

    static {
        List<SearchType> articleSearchTypes = new ArrayList<>();
        articleSearchTypes.add(SearchType.TITLE);
        articleSearchTypes.add(SearchType.TITLE_WITH_CONTENT);
        articleSearchTypes.add(SearchType.AUTHOR);
        ARTICLE_SEARCH_TYPES = Collections.unmodifiableList(articleSearchTypes);
    }

    private Specification<Article> specification;

    public ArticleSearchSpec(String type, String content) {
        SearchType searchType = SearchSpecFactory.createSearchType(ARTICLE_SEARCH_TYPES, type);

        this.specification = SearchSpecFactory.createSpecification(searchType, content);
    }

    @Override
    public Specification<Article> getSpecification() {
        return specification;
    }
}

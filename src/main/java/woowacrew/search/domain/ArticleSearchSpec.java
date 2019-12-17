package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.domain.Article;
import woowacrew.search.exception.NotMatchArticleSearchKeyException;

import javax.persistence.criteria.Path;
import java.util.Optional;

public class ArticleSearchSpec {

    private static final String ARTICLE_FORM = "articleForm";
    private static final String TITLE = "title";

    public enum ArticleSearchKey {
        TITLE("title"),
        TITLE_WITH_CONTENT("titleWithContent"),
        AUTHOR("author");

        private String value;

        ArticleSearchKey(String value) {
            this.value = value;
        }

        private static Optional<ArticleSearchKey> find(String value) {
            for (ArticleSearchKey articleSearchKey : values()) {
                if (articleSearchKey.value.equals(value)) {
                    return Optional.of(articleSearchKey);
                }
            }
            return Optional.empty();
        }
    }

    public static Specification<Article> getSpecification(String type, String content) {
        ArticleSearchKey articleSearchKey = ArticleSearchKey.find(type)
                .orElseThrow(NotMatchArticleSearchKeyException::new);

        return getSpecification(articleSearchKey, content);
    }

    private static Specification<Article> getSpecification(ArticleSearchKey articleSearchKey, String content) {
        if (articleSearchKey.equals(ArticleSearchKey.TITLE)) {
            return createSpecification(createPattern(content), ARTICLE_FORM, TITLE);
        }
        throw new NotMatchArticleSearchKeyException();
    }

    private static String createPattern(String content) {
        return "%" + content + "%";
    }

    private static Specification<Article> createSpecification(String pattern, String... fieldPaths) {
        return (Specification<Article>) (root, query, builder) -> {
            Path<String> field = null;
            for (String fieldPath : fieldPaths) {
                field = field.get(fieldPath);
            }
            return builder.like(field, pattern);
        };
    }
}

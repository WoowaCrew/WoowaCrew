package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.domain.Article;
import woowacrew.search.exception.NotMatchArticleSearchKeyException;

import java.util.Optional;

public class ArticleSearchSpec {

    private static final String ARTICLE_FORM = "articleForm";
    private static final String TITLE = "title";
    private static final String AUTHOR = "user";
    private static final String NICKNAME = "nickname";

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

        return getSpecification(articleSearchKey, createPattern(content));
    }

    private static String createPattern(String content) {
        return "%" + content + "%";
    }

    private static Specification<Article> getSpecification(ArticleSearchKey articleSearchKey, String pattern) {
        if (articleSearchKey.equals(ArticleSearchKey.TITLE)) {
            return createSpecification(pattern, ARTICLE_FORM, TITLE);
        }
        if (articleSearchKey.equals(ArticleSearchKey.AUTHOR)) {
            return createSpecification(pattern, AUTHOR, NICKNAME);
        }
        throw new NotMatchArticleSearchKeyException();
    }

    private static Specification<Article> createSpecification(String pattern, String firstFieldPath, String secondFieldPath) {
        return (Specification<Article>) (root, query, builder) -> builder.like(root.get(firstFieldPath).get(secondFieldPath), pattern);
    }
}

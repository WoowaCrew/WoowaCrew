package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.domain.Article;
import woowacrew.search.exception.NotMatchArticleSearchKeyException;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;

public class ArticleSearchSpec {

    private static final String ARTICLE_FORM_TO = "articleForm";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    private static final String AUTHOR_TO = "user";
    private static final String NICKNAME = "nickname";

    public enum ArticleSearchKey {
        TITLE("title"),
        TITLE_WITH_CONTENT("titleWithContent"),
        AUTHOR("author"),
        INVALID_TYPE("");

        private String value;

        ArticleSearchKey(String value) {
            this.value = value;
        }

        private static ArticleSearchKey find(String value) {
            return Arrays.stream(values())
                    .filter(articleSearchKey -> articleSearchKey.value.equals(value))
                    .findAny()
                    .orElse(INVALID_TYPE);
        }

        public boolean match(ArticleSearchKey articleSearchKey) {
            return this.equals(articleSearchKey);
        }
    }

    public static Specification<Article> getSpec(String type, String content) {
        ArticleSearchKey articleSearchKey = ArticleSearchKey.find(type);
        String pattern = createPattern(content);

        return createSpecBy(articleSearchKey, pattern);
    }

    private static String createPattern(String content) {
        return "%" + content + "%";
    }

    private static Specification<Article> createSpecBy(ArticleSearchKey articleSearchKey, String pattern) {
        if (articleSearchKey.match(ArticleSearchKey.TITLE)) {
            return createSpecBy(pattern, ARTICLE_FORM_TO, TITLE);
        }
        if (articleSearchKey.match(ArticleSearchKey.AUTHOR)) {
            return createSpecBy(pattern, AUTHOR_TO, NICKNAME);
        }
        if (articleSearchKey.match(ArticleSearchKey.TITLE_WITH_CONTENT)) {
            return createSpecByTitleOrContent(pattern);
        }
        throw new NotMatchArticleSearchKeyException();
    }

    private static Specification<Article> createSpecBy(String pattern, String firstFieldPath, String secondFieldPath) {
        return (Specification<Article>) (root, query, builder) -> builder.like(root.get(firstFieldPath).get(secondFieldPath), pattern);
    }

    private static Specification<Article> createSpecByTitleOrContent(String pattern) {
        return (Specification<Article>) (root, query, builder) -> {
            Predicate containTitle = builder.like(root.get(ARTICLE_FORM_TO).get(TITLE), pattern);
            Predicate containAuthor = builder.like(root.get(ARTICLE_FORM_TO).get(CONTENT), pattern);
            return builder.or(containTitle, containAuthor);
        };
    }
}

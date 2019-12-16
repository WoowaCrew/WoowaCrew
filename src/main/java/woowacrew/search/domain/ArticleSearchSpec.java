package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.domain.Article;
import woowacrew.search.exception.NotMatchArticleSearchKeyException;

import java.util.Optional;

public class ArticleSearchSpec {

    private static final String ARTICLE_FORM = "articleForm";
    private static final String TITLE = "title";

    public enum ArticleSearchKey {
        TITLE("title"),
        TITLE_CONTENT("title&content"),
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

    public static Specification<Article> getSpecification(String searchKey, String content) {
        ArticleSearchKey articleSearchKey = ArticleSearchKey.find(searchKey)
                .orElseThrow(NotMatchArticleSearchKeyException::new);

        return getSpecification(articleSearchKey, content);
    }

    private static Specification<Article> getSpecification(ArticleSearchKey articleSearchKey, String content) {
        if (articleSearchKey.equals(ArticleSearchKey.TITLE)) {
            return title(content);
        }
        throw new NotMatchArticleSearchKeyException();
    }

    private static Specification<Article> title(String content) {
        return (Specification<Article>) (root, query, builder) -> {
            String pattern = "%" + content + "%";
            return builder.like(root.get(ARTICLE_FORM).get(TITLE), pattern);
        };
    }
}

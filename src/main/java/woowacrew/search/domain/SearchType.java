package woowacrew.search.domain;

import woowacrew.search.exception.NotExistSearchTypeException;

import java.util.Arrays;
import java.util.List;

public enum SearchType {
    TITLE("title", FieldPath.TITLE_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM),
    TITLE_WITH_CONTENT("titleWithContent", FieldPath.TITLE_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM, FieldPath.CONTENT_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM),
    AUTHOR("author", FieldPath.NICKNAME_OF_AUTHOR_OF_BASIC_ARTICLE_FORM),
    FEED_TITLE("feedTitle", FieldPath.TITLE),
    FEED_DESCRIPTION("feedDescription", FieldPath.DESCRIPTION_OF_FEED_SOURCE),
    ANONYMOUS_ARTICLE_TITLE("anonymousArticleTitle", FieldPath.TITLE_OF_ARTICLE_FORM),
    ANONYMOUS_ARTICLE_TITLE_WITH_CONTENT("anonymousArticleTitleWithContent", FieldPath.TITLE_OF_ARTICLE_FORM, FieldPath.CONTENT_OF_ARTICLE_FORM),
    IS_APPROVED("isApproved", FieldPath.IS_APPROVED),
    DEGREE("degree", FieldPath.DEGREE_NUMBER_OF_DEGREE_OF_AUTHOR_OF_BASIC_ARTICLE_FORM);

    private String type;
    private List<FieldPath> fieldPaths;

    SearchType(String type, FieldPath... fieldPaths) {
        this.type = type;
        this.fieldPaths = Arrays.asList(fieldPaths);
    }

    public static SearchType find(String type) {
        return Arrays.stream(values())
                .filter(searchType -> searchType.type.equals(type))
                .findAny()
                .orElseThrow(NotExistSearchTypeException::new);
    }

    public List<FieldPath> getFieldPaths() {
        return this.fieldPaths;
    }
}

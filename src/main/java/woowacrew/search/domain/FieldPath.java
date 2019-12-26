package woowacrew.search.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum FieldPath {
    IS_APPROVED("isApproved"),
    TITLE("title"),
    TITLE_OF_ARTICLE_FORM("articleForm", "title"),
    TITLE_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM("basicArticleForm", "articleForm", "title"),
    CONTENT_OF_ARTICLE_FORM("articleForm", "content"),
    CONTENT_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM("basicArticleForm", "articleForm", "content"),
    NICKNAME_OF_AUTHOR_OF_BASIC_ARTICLE_FORM("basicArticleForm", "author", "nickname"),
    DESCRIPTION_OF_FEED_SOURCE("feedSource", "description"),
    DEGREE_NUMBER_OF_DEGREE_OF_AUTHOR_OF_BASIC_ARTICLE_FORM("basicArticleForm", "author", "degree", "degreeNumber");

    private static final int ROOT_INDEX = 0;
    private static final int SUB_PATH_START_INDEX = 1;

    private List<String> fieldPath;

    FieldPath(String... fieldPath) {
        this.fieldPath = Arrays.asList(fieldPath);
    }

    public List<String> value() {
        return this.fieldPath;
    }

    public String getRootPath() {
        return this.fieldPath.get(ROOT_INDEX);
    }

    public List<String> getSubPaths() {
        return Collections.unmodifiableList(fieldPath.subList(SUB_PATH_START_INDEX, fieldPath.size()));
    }
}

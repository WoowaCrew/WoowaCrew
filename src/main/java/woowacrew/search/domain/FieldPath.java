package woowacrew.search.domain;

public enum FieldPath {
    TITLE_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM("basicArticleForm", "articleForm", "title"),
    CONTENT_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM("basicArticleForm", "articleForm", "content"),
    NICKNAME_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM("basicArticleForm", "author", "nickname");

    private String[] fieldPath;

    FieldPath(String... fieldPath) {
        this.fieldPath = fieldPath;
    }

    public String[] value() {
        return this.fieldPath;
    }
}

package woowacrew.search.domain;

public enum FieldPath {
    BASIC_ARTICLE_FORM_TO_ARTICLE_FORM_TO_TITLE("basicArticleForm", "articleForm", "title"),
    BASIC_ARTICLE_FORM_TO_ARTICLE_FORM_TO_CONTENT("basicArticleForm", "articleForm", "content"),
    BASIC_ARTICLE_FORM_TO_AUTHOR_TO_NICKNAME("basicArticleForm", "author", "nickname");

    private String[] fieldPath;

    FieldPath(String... fieldPath) {
        this.fieldPath = fieldPath;
    }

    public String[] value() {
        return this.fieldPath;
    }
}

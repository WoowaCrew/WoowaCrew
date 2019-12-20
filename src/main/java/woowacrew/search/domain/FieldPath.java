package woowacrew.search.domain;

public enum FieldPath {
    BASIC_ARTICLE_FORM_OF_ARTICLE_FORM_OF_TITLE("basicArticleForm", "articleForm", "title"),
    BASIC_ARTICLE_FORM_OF_ARTICLE_FORM_OF_CONTENT("basicArticleForm", "articleForm", "content"),
    BASIC_ARTICLE_FORM_OF_AUTHOR_OF_NICKNAME("basicArticleForm", "author", "nickname");

    private String[] fieldPath;

    FieldPath(String... fieldPath) {
        this.fieldPath = fieldPath;
    }

    public String[] value() {
        return this.fieldPath;
    }
}

package woowacrew.search.domain;

import woowacrew.search.exception.NotExistSearchTypeException;

import java.util.Arrays;
import java.util.List;

public enum SearchType {
    TITLE("title", FieldPath.TITLE_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM),
    AUTHOR("author", FieldPath.NICKNAME_OF_AUTHOR_OF_BASIC_ARTICLE_FORM),
    TITLE_WITH_CONTENT("titleWithContent", FieldPath.TITLE_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM, FieldPath.CONTENT_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM),
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

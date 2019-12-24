package woowacrew.search.domain;

import woowacrew.search.exception.NotExistSearchTypeException;

import java.util.Arrays;

public enum SearchType {
    TITLE("title", FieldPath.TITLE_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM),
    AUTHOR("author", FieldPath.NICKNAME_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM),
    TITLE_WITH_CONTENT("titleWithContent", FieldPath.TITLE_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM, FieldPath.CONTENT_OF_ARTICLE_FORM_OF_BASIC_ARTICLE_FORM),
    DEGREE("degree", FieldPath.DEGREE_NUMBER_OF_DEGREE_OF_AUTHOR_OF_BASIC_ARTICLE_FORM);

    private String type;
    private String[][] fieldPaths;

    SearchType(String type, FieldPath... fieldPaths) {
        this.type = type;
        this.fieldPaths = init(fieldPaths);
    }

    private String[][] init(FieldPath[] searchValues) {
        String[][] fieldPaths = new String[searchValues.length][];
        for (int i = 0; i < searchValues.length; i++) {
            fieldPaths[i] = searchValues[i].value();
        }
        return fieldPaths;
    }

    public static SearchType find(String type) {
        return Arrays.stream(values())
                .filter(searchType -> searchType.type.equals(type))
                .findAny()
                .orElseThrow(NotExistSearchTypeException::new);
    }

    public String[][] getFieldPaths() {
        return this.fieldPaths;
    }
}

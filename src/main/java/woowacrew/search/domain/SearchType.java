package woowacrew.search.domain;

import woowacrew.search.exception.NotExistSearchTypeException;

import java.util.Arrays;

public enum SearchType {
    TITLE("title", FieldPath.BASIC_ARTICLE_FORM_TO_ARTICLE_FORM_TO_TITLE),
    AUTHOR("author", FieldPath.BASIC_ARTICLE_FORM_TO_AUTHOR_TO_NICKNAME),
    TITLE_WITH_CONTENT("titleWithContent", FieldPath.BASIC_ARTICLE_FORM_TO_ARTICLE_FORM_TO_TITLE, FieldPath.BASIC_ARTICLE_FORM_TO_ARTICLE_FORM_TO_CONTENT);

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

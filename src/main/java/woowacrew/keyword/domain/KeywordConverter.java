package woowacrew.keyword.domain;

public class KeywordConverter {
    private KeywordConverter() {
    }

    public static KeywordResponseDto keywordToKeywordResponseDto(Keyword keyword) {
        return new KeywordResponseDto(keyword.getId(), keyword.getContent(), keyword.getViews());
    }

    public static Keyword keywordDtoToKeyword(KeywordRequestDto keywordRequestDto) {
        return new Keyword(keywordRequestDto.getContent());
    }
}

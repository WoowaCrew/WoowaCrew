package woowacrew.keyword.domain;

public class KeywordConverter {
    public static KeywordResponse keywordToKeywordResponseDto(Keyword keyword) {
        return new KeywordResponse(keyword.getId(), keyword.getContent(), keyword.getViews());
    }

    public static Keyword keywordDtoToKeyword(KeywordDto keywordDto) {
        return new Keyword(keywordDto.getContent());
    }
}

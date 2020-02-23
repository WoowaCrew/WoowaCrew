package woowacrew.keyword.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class KeywordConverter {
    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=";
    private static final String REDIRECT = "redirect:";
    private static final String UTF_8 = "UTF-8";

    private KeywordConverter() {
    }

    public static KeywordResponseDto keywordToKeywordResponseDto(Keyword keyword) {
        return new KeywordResponseDto(keyword.getId(), keyword.getContent(), keyword.getViews());
    }

    public static Keyword keywordDtoToKeyword(KeywordRequestDto keywordRequestDto) {
        return new Keyword(keywordRequestDto.getContent());
    }

    public static String toRedirectUri(String content) throws UnsupportedEncodingException {
        return REDIRECT + GOOGLE_SEARCH_URL + URLEncoder.encode(content, UTF_8);
    }
}

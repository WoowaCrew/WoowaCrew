package woowacrew.keyword.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.keyword.domain.*;
import woowacrew.keyword.exception.NotFoundKeyword;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class KeywordService {

    private KeywordRepository keywordRepository;

    @Autowired
    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Async
    public KeywordResponseDto save(KeywordRequestDto keywordRequestDto) {
        Keyword keyword = createKeyword(keywordRequestDto.getContent());

        keyword.increaseViews();
        return KeywordConverter.keywordToKeywordResponseDto(keyword);
    }

    private Keyword createKeyword(String content) {
        Optional<Keyword> maybeKeyword = Optional.ofNullable(keywordRepository.findByContent(content));

        return maybeKeyword.orElseGet(() -> keywordRepository.save(new Keyword(content)));
    }

    @Transactional(readOnly = true)
    public Keyword findById(Long id) {
        Optional<Keyword> maybeKeyword = keywordRepository.findById(id);

        return maybeKeyword.orElseThrow(NotFoundKeyword::new);
    }

    @Transactional(readOnly = true)
    public List<KeywordResponseDto> keywordRank() {
        return keywordRepository.findTop10ByOrderByViewsDesc()
                .stream()
                .map(KeywordConverter::keywordToKeywordResponseDto)
                .collect(Collectors.toList());
    }

    public KeywordResponseDto increaseViews(Long id) {
        Keyword keyword = findById(id);

        keyword.increaseViews();
        return KeywordConverter.keywordToKeywordResponseDto(keyword);
    }
}

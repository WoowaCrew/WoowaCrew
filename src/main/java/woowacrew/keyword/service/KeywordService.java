package woowacrew.keyword.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.keyword.domain.Keyword;
import woowacrew.keyword.domain.KeywordRepository;
import woowacrew.keyword.exception.NotFoundKeyword;

import java.util.List;
import java.util.Optional;

@Service
public class KeywordService {

    private KeywordRepository keywordRepository;

    @Autowired
    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Async
    @Transactional
    public long save(String content) {
        Keyword keyword = createKeyword(content);

        keyword.addViews();
        return keyword.getId();
    }

    private Keyword createKeyword(String content) {
        Optional<Keyword> maybeKeyword = Optional.ofNullable(keywordRepository.findByContent(content));

        return maybeKeyword.orElseGet(() -> keywordRepository.save(new Keyword(content)));
    }

    public Keyword findById(Long id) {
        Optional<Keyword> maybeKeyword = keywordRepository.findById(id);

        return maybeKeyword.orElseThrow(NotFoundKeyword::new);
    }

    public List<Keyword> keywordRank() {
        return keywordRepository.findTop10ByOrderByViewsDesc();
    }
}

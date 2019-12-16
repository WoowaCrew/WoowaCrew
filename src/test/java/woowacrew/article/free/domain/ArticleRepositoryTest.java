package woowacrew.article.free.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void Id와_시간이_정상적으로_반환되는지_확인() {
        Article article = new Article("title", "content", null);
        Article actualArticle = articleRepository.save(article);

        assertThat(actualArticle.getId()).isNotNull();
        assertThat(actualArticle.getCreatedDate()).isNotNull();
    }

    @Test
    void 정상적으로_해당_pageable로_게시글들을_찾는() {
        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(0, 3, sort);

        assertTrue(articleRepository.findAll(pageable).getContent().size() != 0);
    }

    @Test
    void 정상적으로_specification과_pageable로_게시물들을_찾는() {
        Specification<Article> articleSpecification = (Specification<Article>) (root, query, builder) -> {
            String pattern = "%delete%";
            return builder.like(root.get("articleForm").get("title"), pattern);
        };

        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(0, 3, sort);

        assertTrue(articleRepository.findAll(articleSpecification, pageable).getContent().size() != 0);
    }
}
package woowacrew.article.free.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import woowacrew.JpaTestConfiguration;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
@EnableJpaAuditing
class ArticleRepositoryTest {
    private static final SearchType[] ALLOWED_SEARCH_TYPES = {SearchType.TITLE, SearchType.TITLE_WITH_CONTENT, SearchType.AUTHOR};

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
    void 정상적으로_해당_pageable로_게시글들을_찾는다() {
        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(0, 3, sort);

        assertTrue(articleRepository.findAll(pageable).getContent().size() != 0);
    }

    @Test
    void 정상적으로_제목으로_게시물들을_찾는다() {
        String inputData = "test";
        SearchSpec<Article> searchSpec = SearchSpec.init("title", inputData, ALLOWED_SEARCH_TYPES);
        Specification<Article> specification = searchSpec.getSpecification();

        Pageable pageable = PageRequest.of(0, 20);

        List<Article> articles = articleRepository.findAll(specification, pageable).getContent();
        assertTrue(articles.size() != 0);
        articles.forEach(article -> assertTrue(article.getTitle().contains(inputData)));
    }

    @Test
    void 정상적으로_제목과_내용으로_게시물들을_찾는다() {
        String inputData = "test";
        SearchSpec<Article> searchSpec = SearchSpec.init("titleWithContent", inputData, ALLOWED_SEARCH_TYPES);
        Specification<Article> specification = searchSpec.getSpecification();

        Pageable pageable = PageRequest.of(0, 20);

        List<Article> articles = articleRepository.findAll(specification, pageable).getContent();
        assertTrue(articles.size() != 0);
        articles.forEach(article ->
                assertTrue(article.getTitle().contains(inputData) || article.getContent().contains(inputData)));
    }

    @Test
    void 정상적으로_작성자로_게시물들을_찾는다() {
        String inputData = "woowacrew";
        SearchSpec<Article> searchSpec = SearchSpec.init("author", inputData, ALLOWED_SEARCH_TYPES);
        Specification<Article> specification = searchSpec.getSpecification();

        Pageable pageable = PageRequest.of(0, 20);

        List<Article> articles = articleRepository.findAll(specification, pageable).getContent();
        assertTrue(articles.size() != 0);
        articles.forEach(article -> assertTrue(article.getAuthor().getNickname().contains(inputData)));
    }
}
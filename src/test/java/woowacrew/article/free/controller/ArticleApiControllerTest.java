package woowacrew.article.free.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.article.free.service.ArticleService;
import woowacrew.degree.dto.DegreeResponseDto;
import woowacrew.security.SecurityContextSupport;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;
import woowacrew.user.dto.UserResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacrew.common.document.ApiDocumentUtils.getDocumentRequest;
import static woowacrew.common.document.ApiDocumentUtils.getDocumentResponse;
import static woowacrew.common.document.SnippetUtils.articleResponseDtoSnippets;
import static woowacrew.common.document.SnippetUtils.articleResponseDtosSnippets;

@WebMvcTest(controllers = ArticleApiController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "woowacrew.security.*")})
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
public class ArticleApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleService articleService;

    private UserContext userContext;

    @BeforeEach
    void setUp() {
        userContext = new UserContext(1L, "12345", "woowa", UserRole.ROLE_CREW);
        SecurityContextSupport.updateContext(userContext);
    }

    @Test
    void list() throws Exception {
        List<ArticleResponseDto> articles = new ArrayList<>();
        articles.add(generateArticleResponseDto(1L, "title1", "content1"));
        articles.add(generateArticleResponseDto(2L, "title2", "content2"));
        articles.add(generateArticleResponseDto(3L, "title3", "content3"));

        ArticleResponseDtos articleResponseDtos = new ArticleResponseDtos(1, 1, articles);

        given(articleService.findAll(any())).willReturn(articleResponseDtos);

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andDo(document("article/list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        articleResponseDtosSnippets));
    }

    @Test
    void show() throws Exception {
        ArticleResponseDto articleResponseDto = generateArticleResponseDto(1L, "title", "content");

        given(articleService.findById(1L)).willReturn(articleResponseDto);

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/articles/{articleId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("article/show",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("articleId").description("조회할 게시글 id")
                        ),
                        articleResponseDtoSnippets));
    }

    @Test
    void createArticle() throws Exception {
        String title = "title";
        String content = "content";

        ArticleRequestDto articleRequestDto = new ArticleRequestDto(title, content);
        ArticleResponseDto articleResponseDto = generateArticleResponseDto(1L, title, content);

        given(articleService.save(articleRequestDto, userContext)).willReturn(articleResponseDto);

        mockMvc.perform(
                post("/api/articles")
                        .param("title", title)
                        .param("content", content))
                .andExpect(status().isCreated())
                .andDo(document("article/create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        articleResponseDtoSnippets));
    }

    @Test
    void updateArticle() throws Exception {
        String newTitle = "new title";
        String newContent = "new content";

        ArticleUpdateDto articleUpdateDto = new ArticleUpdateDto(1L, newTitle, newContent);
        ArticleResponseDto articleResponseDto = generateArticleResponseDto(1L, newTitle, newContent);

        given(articleService.update(articleUpdateDto, userContext)).willReturn(articleResponseDto);

        mockMvc.perform(
                RestDocumentationRequestBuilders.put("/api/articles/{articleId}", 1)
                        .param("articleId", "1")
                        .param("title", newTitle)
                        .param("content", newContent))
                .andExpect(status().isOk())
                .andDo(document("article/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("articleId").description("수정할 게시글 id")
                        ),
                        articleResponseDtoSnippets));
    }

    @Test
    void deleteArticle() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/api/articles/{articleId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("article/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("articleId").description("삭제할 게시글 id")
                        )));

        verify(articleService).delete(1L, userContext);
    }

    private UserResponseDto generateUserResponseDto() {
        LocalDate birthdayDate = LocalDate.of(2019, 1, 1);
        UserResponseDto userResponseDto = new UserResponseDto(
                userContext.getId(),
                userContext.getOauthId(),
                userContext.getNickname(),
                birthdayDate);
        userResponseDto.setUserRole(userContext.getRole());
        userResponseDto.setDegreeResponseDto(new DegreeResponseDto(1L, 1));
        return userResponseDto;
    }

    private ArticleResponseDto generateArticleResponseDto(Long id, String title, String content) {
        UserResponseDto userResponseDto = generateUserResponseDto();
        LocalDateTime createdDate = LocalDateTime.of(2019, 5, 7, 12, 0);
        LocalDateTime lastModifiedDate = LocalDateTime.of(2019, 5, 7, 12, 0);

        return new ArticleResponseDto(id, title, content, userResponseDto, createdDate, lastModifiedDate);
    }
}
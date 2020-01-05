package woowacrew.keyword.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchControllerTest {

    @LocalServerPort
    private String port;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void 검색어를_정상적으로_저장한다() {
        webTestClient.post()
                .uri("/search?content=test")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("google.com"))
                .expectBody()
                .consumeWith(document("search/save",
                        requestParameters(
                                parameterWithName("content").description("검색 내용")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("검색 엔진(구글)")
                        )
                ));
    }

    @Test
    void 조회수_순으로_순위를_보여준다() {
        webTestClient.get()
                .uri("/search/rank")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String responseBody = new String(Objects.requireNonNull(response.getResponseBody()), StandardCharsets.UTF_8);
                    assertThat(responseBody).contains("최다 조회수 A")
                            .contains("최다 조회수 B")
                            .contains("최다 조회수 C");
                });
    }

    @Test
    void 검색어_조회수를_증가시키고_검색한다() {
        webTestClient.post()
                .uri("/search/1")
                .exchange()
                .expectHeader()
                .value("Location", Matchers.containsString("google.com"));
    }
}
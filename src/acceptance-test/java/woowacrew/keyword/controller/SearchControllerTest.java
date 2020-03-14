package woowacrew.keyword.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import woowacrew.common.controller.CommonTestController;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class SearchControllerTest extends CommonTestController {

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
    void 검색어_조회수를_증가시키고_검색한다() {
        webTestClient.post()
                .uri("/search/1")
                .exchange()
                .expectHeader()
                .value("Location", Matchers.containsString("google.com"));
    }
}
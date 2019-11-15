package woowacrew.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.utils.configuration.WebMvcConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebMvcConfig.class)
public class CommonTestController {
    @Autowired
    private WebTestClient webTestClient;

    protected String getLoginCookie() {
        return webTestClient.post()
                .uri("/test/login")
                .exchange()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }
}

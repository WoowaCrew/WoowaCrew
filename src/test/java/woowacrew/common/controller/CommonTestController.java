package woowacrew.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommonTestController {
    private static String cookie;

    @Autowired
    private WebTestClient webTestClient;

    protected String getLoginCookie() {
        if (cookie == null) {
            cookie = webTestClient.post()
                    .uri("/test/login")
                    .exchange()
                    .returnResult(String.class)
                    .getResponseHeaders()
                    .getFirst("Set-cookie");
        }
        return cookie;
    }
}

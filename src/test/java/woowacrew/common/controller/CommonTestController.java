package woowacrew.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommonTestController {
    private static String cookie;

    @Autowired
    private WebTestClient webTestClient;

    protected String loginWithUser() {
        if (cookie == null) {
            webTestClient.get()
                    .uri("/oauth/github?role=ROLE_USER")
                    .exchange()
                    .expectBody()
                    .consumeWith(response -> {
                        List<ResponseCookie> jsessionid = response.getResponseCookies().get("JSESSIONID");
                        cookie = jsessionid.get(0).getName() + "=" + jsessionid.get(0).getValue();

                    });
        }
        return cookie;
    }

    protected String loginWithAdmin() {
        if (cookie == null) {
            webTestClient.get()
                    .uri("/oauth/github?role=ROLE_ADMIN")
                    .exchange()
                    .expectBody()
                    .consumeWith(response -> {
                        List<ResponseCookie> jsessionid = response.getResponseCookies().get("JSESSIONID");
                        cookie = jsessionid.get(0).getName() + "=" + jsessionid.get(0).getValue();

                    });
        }
        return cookie;
    }
}

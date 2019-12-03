package woowacrew.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommonTestController {
    @Autowired
    private WebTestClient webTestClient;

    protected String loginWithUser() {
        return loginWith("USER");
    }

    protected String loginWithAdmin() {
        return loginWith("ADMIN");
    }

    private String loginWith(String role) {
        final String[] cookie = new String[1];

        webTestClient.get()
                .uri("/oauth/github?role=ROLE_" + role)
                .exchange()
                .expectBody()
                .consumeWith(response -> {
                    List<ResponseCookie> jsessionid = response.getResponseCookies().get("JSESSIONID");
                    cookie[0] = jsessionid.get(0).getName() + "=" + jsessionid.get(0).getValue();

                });

        return cookie[0];
    }
}

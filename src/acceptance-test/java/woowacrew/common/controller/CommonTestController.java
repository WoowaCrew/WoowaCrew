package woowacrew.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.user.domain.UserRole;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommonTestController {
    @Autowired
    private WebTestClient webTestClient;

    protected String loginWithPrecourse() {
        return loginWith("precousre", UserRole.ROLE_PRECOURSE.toString());
    }

    protected String loginWithCrew() {
        return loginWith("crew", UserRole.ROLE_CREW.toString());
    }

    protected String loginWithCrew(String oauthId) {

        return loginWith("crew_" + oauthId, UserRole.ROLE_CREW.toString());
    }

    protected String loginWithCoach() {
        return loginWith("coach", UserRole.ROLE_COACH.toString());
    }

    protected String loginWithAdmin() {
        return loginWith("admin", UserRole.ROLE_ADMIN.toString());
    }

    private String loginWith(String oauthId, String role) {
        final String[] cookie = new String[1];

        webTestClient.get()
                .uri("/oauth/github?role=" + role + "&oauthId=" + oauthId)
                .exchange()
                .expectBody()
                .consumeWith(response -> {
                    List<ResponseCookie> jsessionid = response.getResponseCookies().get("JSESSIONID");
                    cookie[0] = jsessionid.get(0).getName() + "=" + jsessionid.get(0).getValue();

                });

        return cookie[0];
    }
}

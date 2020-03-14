package woowacrew.common.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseCookie;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.user.domain.UserRole;

import java.util.List;
import java.util.UUID;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CommonTestController {
    @LocalServerPort
    private String port;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(documentationConfiguration(restDocumentation))
                .build();
    }

    @Autowired
    protected WebTestClient webTestClient;

    protected String loginWithPrecourse() {
        return loginWith(UUID.randomUUID().toString(), UserRole.ROLE_PRECOURSE.toString());
    }

    protected String loginWithCrew() {
        return loginWith(UUID.randomUUID().toString(), UserRole.ROLE_CREW.toString());
    }

    protected String loginWithCrew(String oauthId) {

        return loginWith(oauthId, UserRole.ROLE_CREW.toString());
    }

    protected String loginWithCoach() {
        return loginWith(UUID.randomUUID().toString(), UserRole.ROLE_COACH.toString());
    }

    protected String loginWithAdmin() {
        return loginWith(UUID.randomUUID().toString(), UserRole.ROLE_ADMIN.toString());
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

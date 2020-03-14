package woowacrew.degree.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import woowacrew.common.controller.CommonTestController;
import woowacrew.degree.dto.DegreeWithUserCountResponseDto;
import woowacrew.user.dto.UserResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class AdminDegreeApiControllerTest extends CommonTestController {

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void 관리자가_아니면_접근이_거부된다() {
        String cookie = loginWithCrew();
        webTestClient.get()
                .uri("/api/degrees")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }

    @Test
    void 기수별_유저_수_리턴_테스트() {
        String cookie = loginWithAdmin();
        List<DegreeWithUserCountResponseDto> response = webTestClient.get()
                .uri("/api/degrees")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(DegreeWithUserCountResponseDto.class)
                .consumeWith(document("degree/read",
                        responseFields(
                                fieldWithPath("[].id").description("기수 별 아이디"),
                                fieldWithPath("[].degreeNumber").description("기수"),
                                fieldWithPath("[].userCount").description("기수 별 사람 수"))
                ))
                .returnResult()
                .getResponseBody();

        DegreeWithUserCountResponseDto course1 = response.get(1);
        assertThat(course1.getUserCount()).isNotZero();
    }

    @Test
    void 기수별_유저_목록_리턴_테스트() {
        String cookie = loginWithAdmin();
        List<UserResponseDto> response = webTestClient.get()
                .uri("/api/degrees/1/users")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(UserResponseDto.class)
                .consumeWith(document("degree/list",
                        responseFields(
                                fieldWithPath("[].id").description("유저 아이디"),
                                fieldWithPath("[].oauthId").description("Oauth 아이디"),
                                fieldWithPath("[].nickname").description("유저 닉네임"),
                                fieldWithPath("[].birthday").description("유저 생일").optional(),
                                fieldWithPath("[].userRole").description("유저 권한"),
                                fieldWithPath("[].degreeResponseDto.*").description("유저 기수")
                        )))
                .returnResult()
                .getResponseBody();

        assertThat(response.size()).isNotZero();
    }
}
package woowacrew.comment.controller;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.comment.dto.CommentResponseDto;
import woowacrew.common.controller.CommonTestController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

public class CommentApiControllerTest extends CommonTestController {

    @Test
    void 댓글_생성_테스트() {
        String cookie = loginWithCrew();
        CommentResponseDto commentResponseDto = webTestClient.post()
                .uri("/api/articles/1/comments")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("content", "testContent"))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CommentResponseDto.class)
                .consumeWith(document("comment/create",
                        requestParameters(
                                parameterWithName("content").description("댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("id").description("댓글 아이디"),
                                fieldWithPath("userResponseDto.id").description("유저 아이디"),
                                fieldWithPath("userResponseDto.oauthId").ignored(),
                                fieldWithPath("userResponseDto.nickname").description("유저 닉네임"),
                                fieldWithPath("userResponseDto.birthday").description("유저 생일"),
                                fieldWithPath("userResponseDto.userRole").description("유저 권한"),
                                fieldWithPath("userResponseDto.degreeResponseDto.*").description("유저 기수"),
                                fieldWithPath("content").description("댓글 내용"),
                                fieldWithPath("createDateTime").description("댓글 생성 시간")
                        )
                ))
                .returnResult()
                .getResponseBody();

        assertThat(commentResponseDto).isNotNull();
    }

    // TODO: 2020/01/05 documentation api call
    @Test
    void 댓글_조회_테스트() {
        String cookie = loginWithCrew();

        List<CommentResponseDto> dtos = webTestClient.get()
                .uri("/api/articles/1/comments")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(dtos.size()).isEqualTo(2);
    }

    @Test
    void 댓글_수정_테스트() {
        String cookie = loginWithCrew("1234");
        String commentId = "1";
        String updateContent = "update Content";

        CommentResponseDto commentResponseDto = webTestClient.put()
                .uri("/api/articles/1/comments/" + commentId)
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("updateContent", updateContent))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentResponseDto.class)
                .consumeWith(document("comment/update",
                        requestParameters(
                                parameterWithName("updateContent").description("수정한 댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("id").description("댓글 아이디"),
                                fieldWithPath("userResponseDto.id").description("유저 아이디"),
                                fieldWithPath("userResponseDto.oauthId").ignored(),
                                fieldWithPath("userResponseDto.nickname").description("유저 닉네임"),
                                fieldWithPath("userResponseDto.birthday").description("유저 생일"),
                                fieldWithPath("userResponseDto.userRole").description("유저 권한"),
                                fieldWithPath("userResponseDto.degreeResponseDto.*").description("유저 기수"),
                                fieldWithPath("content").description("수정한 댓글 내용"),
                                fieldWithPath("createDateTime").description("수정한 댓글 생성 시간")
                        )
                ))
                .returnResult()
                .getResponseBody();

        assertThat(commentResponseDto).isNotNull();
        assertThat(commentResponseDto.getId()).isEqualTo(Long.parseLong(commentId));
        assertThat(commentResponseDto.getContent()).isEqualTo(updateContent);
    }

    @Test
    void 댓글_삭제_테스트() {
        String cookie = loginWithCrew("1234");
        Long commentId = 2L;

        webTestClient.delete()
                .uri("/api/articles/1/comments/" + commentId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("comment/delete"));
    }
}

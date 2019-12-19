package woowacrew.comment.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.comment.dto.CommentResponseDto;
import woowacrew.common.controller.CommonTestController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentApiControllerTest extends CommonTestController {

    @Autowired
    private WebTestClient webTestClient;

//    "/api/articles/{articleId}/comments"

    @Test
    void 댓글_생성_테스트() {
        String cookie = loginWithCrew();
        CommentResponseDto commentResponseDto = webTestClient.post()
                .uri("/api/articles/1/comments")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("articleId", "1")
                        .with("content", "testContent"))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CommentResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(commentResponseDto).isNotNull();
    }

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
                .uri("/api/articles/1/comments")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("commentId", commentId)
                        .with("updateContent", updateContent))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentResponseDto.class)
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
                .expectStatus().isOk();
    }
}

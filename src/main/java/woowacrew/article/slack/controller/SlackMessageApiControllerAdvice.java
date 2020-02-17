package woowacrew.article.slack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import woowacrew.article.slack.exception.CreateSlackMessageFailException;
import woowacrew.article.slack.exception.NotFoundRecentlySlackMessageException;

@ControllerAdvice
public class SlackMessageApiControllerAdvice {
    @ExceptionHandler(NotFoundRecentlySlackMessageException.class)
    public ResponseEntity<Void> notFound() {
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(CreateSlackMessageFailException.class)
    public ResponseEntity<Void> createSlackMessageFail() {
        return ResponseEntity.badRequest().build();
    }
}
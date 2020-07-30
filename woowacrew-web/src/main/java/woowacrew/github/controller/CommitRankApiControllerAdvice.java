package woowacrew.github.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import woowacrew.github.exception.NotFoundMyTodayCommitRankException;

@ControllerAdvice
public class CommitRankApiControllerAdvice {

    @ExceptionHandler(NotFoundMyTodayCommitRankException.class)
    public ResponseEntity<Void> notFoundMyCommitRank() {
        return ResponseEntity.badRequest().build();
    }
}

package woowacrew.user.domain.exception;

public class NotExistNicknameException extends RuntimeException {
    public NotExistNicknameException() {
        super("닉네임 값은 필수로 존재 해야 합니다.");
    }
}

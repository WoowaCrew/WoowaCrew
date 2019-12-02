package woowacrew.user.controller;

public class ErrorMessage {

    private String message;

    public ErrorMessage() {
    }

    private ErrorMessage(String message) {
        this.message = message;
    }

    public static ErrorMessage badRequest(Exception exception) {
        return new ErrorMessage(exception.getMessage());
    }

    public String getMessage() {
        return message;
    }
}

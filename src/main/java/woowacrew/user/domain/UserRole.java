package woowacrew.user.domain;

public enum UserRole {
    ROLE_PRECOURSE,
    ROLE_CREW,
    ROLE_COACH,
    ROLE_ADMIN;

    public boolean matchAdmin() {
        return this == ROLE_ADMIN;
    }
}

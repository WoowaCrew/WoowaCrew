package woowacrew.user.domain;

public enum UserRole {
    ROLE_PRECOURSE("PRECOURSE"),
    ROLE_CREW("CREW"),
    ROLE_COACH("COACH"),
    ROLE_ADMIN("ADMIN");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public boolean isApproved() {
        return this != ROLE_PRECOURSE;
    }

    public boolean matchAdmin() {
        return this == ROLE_ADMIN;
    }

    public String getRoleName() {
        return roleName;
    }
}

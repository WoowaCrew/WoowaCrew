package woowacrew.user.dto;

import woowacrew.user.domain.UserRole;

public class UserApproveDto {
    private UserRole role;
    private int degreeNumber;

    public UserApproveDto() {
    }

    public UserApproveDto(UserRole role, int degreeNumber) {
        this.role = role;
        this.degreeNumber = degreeNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public int getDegreeNumber() {
        return degreeNumber;
    }
}

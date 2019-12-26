package woowacrew.user.dto;

import woowacrew.user.domain.UserRole;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserApproveDto that = (UserApproveDto) o;
        return degreeNumber == that.degreeNumber &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, degreeNumber);
    }

    @Override
    public String toString() {
        return "UserApproveDto{" +
                "role=" + role +
                ", degreeNumber=" + degreeNumber +
                '}';
    }
}

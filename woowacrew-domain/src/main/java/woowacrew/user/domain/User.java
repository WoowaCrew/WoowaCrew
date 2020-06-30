package woowacrew.user.domain;

import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.exception.ForbiddenUserException;
import woowacrew.user.domain.exception.NotExistNicknameException;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    private String githubId;

    private String nickname;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_DEGREE"))
    private Degree degree;

    private User() {
    }

    public User(String oauthId, Degree degree) {
        this(oauthId, UserRole.ROLE_PRECOURSE, degree);
    }

    public User(String oauthId, UserRole role, Degree degree) {
        this.oauthId = oauthId;
        this.role = role;
        this.degree = degree;
    }

    public User(String oauthId, String githubId, Degree degree) {
        this.oauthId = oauthId;
        this.githubId = githubId;
        this.role = UserRole.ROLE_PRECOURSE;
        this.degree = degree;
    }

    public void updateUserInfo(String nickname, LocalDate birthday) {
        validNickname(nickname);

        this.nickname = nickname;
        this.birthday = birthday;
    }

    private void validNickname(String nickname) {
        if (nickname == null || "".equals(nickname)) {
            throw new NotExistNicknameException();
        }
    }

    public boolean isSameUser(User user) {
        return this.equals(user);
    }

    public boolean isSameDegree(User requestAuthor) {
        return degree.equals(requestAuthor.degree);
    }

    public boolean isUpcomingBirthday(Month month) {
        return this.birthday != null && this.birthday.getMonth() == month;
    }

    public boolean isBirthday(LocalDate today) {
        return isUpcomingBirthday(today.getMonth()) &&
                this.birthday.getDayOfMonth() == today.getDayOfMonth();
    }

    public void updateByAdmin(User user, UserRole role, Degree degree) {
        if (!user.role.matchAdmin()) {
            throw new ForbiddenUserException();
        }

        updateRole(role);
        updateDegree(degree);
    }

    private void updateRole(UserRole role) {
        this.role = role;
    }

    private void updateDegree(Degree degree) {
        this.degree = degree;
    }

    public boolean isApproved() {
        return this.role.isApproved();
    }

    public Long getId() {
        return id;
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getGithubId() { return githubId; }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public UserRole getRole() {
        return role;
    }

    public Degree getDegree() {
        return degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + oauthId + '\'' +
                '}';
    }
}

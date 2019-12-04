package woowacrew.user.domain;

import woowacrew.user.domain.exception.ForbiddenUserException;
import woowacrew.user.domain.exception.NotExistNicknameException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String url;

    private String nickname;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_USER_DEGREE"))
    private Degree degree = new Degree();

    private User() {
    }

    public User(String userId, String url) {
        this(userId, url, UserRole.ROLE_PRECOURSE);
    }

    public User(String userId, String url, UserRole role) {
        this.userId = userId;
        this.url = url;
        this.role = role;
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

    public void updateRole(User admin, UserRole roleCrew, int updateDegree) {
        if (!admin.role.matchAdmin()) {
            throw new ForbiddenUserException();
        }

        this.role = roleCrew;
        degree.update(updateDegree);
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }

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
                ", userId='" + userId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

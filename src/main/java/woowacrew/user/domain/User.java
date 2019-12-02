package woowacrew.user.domain;

import woowacrew.user.domain.exception.NotExistNicknameException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private User() {
    }

    public User(String userId, String url) {
        this.userId = userId;
        this.url = url;
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

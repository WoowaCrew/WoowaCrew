package woowacrew.github.domain;

import woowacrew.user.domain.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class GithubCommit {

    private static final int FIRST_DAY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private LocalDate date;
    private Integer point;

    private GithubCommit() {
    }

    public GithubCommit(User user, LocalDate date, int point) {
        validateDate(date);
        this.user = user;
        this.date = date;
        this.point = point;
    }

    private void validateDate(LocalDate date) {
        if (date.getDayOfMonth() != FIRST_DAY) {
            throw new RuntimeException();
        }
    }

    public boolean isSameUser(User user) {
        return this.user.isSameUser(user);
    }

    public int getPoint() {
        return point;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubCommit that = (GithubCommit) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GithubCommit{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", point=" + point +
                '}';
    }
}

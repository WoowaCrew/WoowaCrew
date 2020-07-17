package woowacrew.github.domain;

import woowacrew.user.domain.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class GithubCommit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private LocalDate date;
    private Integer point;

    public GithubCommit(User user, LocalDate date, Integer point) {
        validateDate(date);
        this.user = user;
        this.date = date;
        this.point = point;
    }

    private void validateDate(LocalDate date) {
        if (date.getDayOfMonth() != 1) {
            throw new RuntimeException();
        }
    }
}

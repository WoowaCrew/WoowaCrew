package woowacrew.user.domain;

import woowacrew.user.domain.exception.DegreeBoundException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Degree {
    public static final int NONE_DEGREE = 0;
    public static final int MAX_BOUND = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number = NONE_DEGREE;

    public void update(int updateDegree) {
        if (updateDegree < NONE_DEGREE || updateDegree > MAX_BOUND) {
            throw new DegreeBoundException();
        }

        this.number = updateDegree;
    }

    public Long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Degree degree = (Degree) o;
        return Objects.equals(id, degree.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Degree{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }
}

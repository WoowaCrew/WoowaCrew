package woowacrew.degree.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.user.domain.exception.DegreeBoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DegreeTest {

    private Degree degree;

    @BeforeEach
    void setUp() {
        degree = new Degree();
    }

    @Test
    @DisplayName("기수는 NONE_DEGREE(0) 값을 가진채로 생성한다.")
    void create() {
        assertThat(degree.getDegreeNumber()).isEqualTo(Degree.NONE_DEGREE);
    }

    @Test
    @DisplayName("기수를 수정한다.")
    void update() {
        degree.update(1);

        assertThat(degree.getDegreeNumber()).isEqualTo(1);
    }

    @Test
    @DisplayName("수정할 기수가 범위에 벗어나면 예외를 발생한다.")
    void update_ifUpdateDegreeIsOutOfBound_thenDegreeBoundException() {
        assertThatThrownBy(() -> degree.update(-1))
                .isInstanceOf(DegreeBoundException.class);
        assertThatThrownBy(() -> degree.update(Degree.MAX_BOUND + 1))
                .isInstanceOf(DegreeBoundException.class);
    }


}
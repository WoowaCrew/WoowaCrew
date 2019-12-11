package woowacrew.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DegreeJpaTest {
    @Autowired
    private DegreeRepository degreeRepository;

    @Test
    void findAll() {
        List<Degree> degrees = degreeRepository.findAll();
        assertThat(degrees.size()).isNotZero();
    }
}

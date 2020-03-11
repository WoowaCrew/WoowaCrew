package woowacrew.degree.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import woowacrew.JpaTestConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
public class DegreeRepositoryTest {
    @Autowired
    private DegreeRepository degreeRepository;

    @Test
    void findAll() {
        List<Degree> degrees = degreeRepository.findAll();
        assertThat(degrees.size()).isNotZero();
    }
}

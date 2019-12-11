package woowacrew.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.user.domain.Degree;
import woowacrew.user.domain.DegreeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DegreeInternalServiceTest {
    @Mock
    private DegreeRepository degreeRepository;

    @InjectMocks
    private DegreeInternalService degreeInternalService;

    @Test
    void findAll() {
        List<Degree> degrees = new ArrayList<>();
        degrees.add(new Degree());
        Degree degree = new Degree();
        degree.update(1);
        degrees.add(degree);

        when(degreeRepository.findAll()).thenReturn(degrees);

        assertThat(degreeInternalService.findAll().size()).isEqualTo(2);
    }
}
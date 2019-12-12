package woowacrew.degree.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.dto.DegreeResponseDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DegreeServiceTest {
    @Mock
    private DegreeInternalService degreeInternalService;

    @InjectMocks
    private DegreeService degreeService;

    @Test
    void findAll() {
        List<Degree> degrees = new ArrayList<>();
        degrees.add(new Degree());
        Degree degree = new Degree();
        degree.update(1);
        degrees.add(degree);

        when(degreeInternalService.findAll()).thenReturn(degrees);

        List<DegreeResponseDto> degreeResponseDtos = degreeService.findAll();

        assertThat(degreeResponseDtos.size()).isEqualTo(2);
    }
}
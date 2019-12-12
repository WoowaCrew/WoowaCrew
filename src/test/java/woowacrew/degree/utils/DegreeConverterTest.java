package woowacrew.degree.utils;

import org.junit.jupiter.api.Test;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.dto.DegreeResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

class DegreeConverterTest {
    @Test
    void DegreeToResponseDto() {
        Degree degree = new Degree();
        DegreeResponseDto degreeResponseDto = DegreeConverter.degreeToReponseDto(degree);

        assertThat(degreeResponseDto.getNumber()).isEqualTo(Degree.NONE_DEGREE);
    }
}
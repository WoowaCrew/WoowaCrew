package woowacrew.degree.utils;

import org.junit.jupiter.api.Test;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.dto.DegreeWithUserCountResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

class DegreeConverterTest {
    @Test
    void DegreeToResponseDto() {
        Degree degree = new Degree();
        DegreeWithUserCountResponseDto degreeWithUserCountResponseDto = DegreeConverter.degreeToWithUserCountReponseDto(degree, 0);

        assertThat(degreeWithUserCountResponseDto.getNumber()).isEqualTo(Degree.NONE_DEGREE);
        assertThat(degreeWithUserCountResponseDto.getNumberOfUser()).isEqualTo(0);
    }
}
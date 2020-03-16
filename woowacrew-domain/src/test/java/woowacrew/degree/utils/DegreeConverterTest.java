package woowacrew.degree.utils;

import org.junit.jupiter.api.Test;
import woowacrew.common.service.FieldSetter;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.dto.DegreeResponseDto;
import woowacrew.degree.dto.DegreeWithUserCountResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

class DegreeConverterTest {
    @Test
    void degreeToWithUserCountReponseDto() {
        Degree degree = new Degree();
        DegreeWithUserCountResponseDto degreeWithUserCountResponseDto = DegreeConverter.degreeToWithUserCountResponseDto(degree, 0);

        assertThat(degreeWithUserCountResponseDto.getDegreeNumber()).isEqualTo(Degree.NONE_DEGREE);
        assertThat(degreeWithUserCountResponseDto.getUserCount()).isEqualTo(0);
    }

    @Test
    void degreeToResponseDto() {
        Degree degree = new Degree();
        FieldSetter.set(degree, "id", 1L);
        DegreeResponseDto degreeResponseDto = DegreeConverter.toDto(degree);

        assertThat(degreeResponseDto.getId()).isEqualTo(1L);
        assertThat(degreeResponseDto.getDegreeNumber()).isEqualTo(Degree.NONE_DEGREE);
    }
}
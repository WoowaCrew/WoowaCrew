package woowacrew.user.utils;

import org.junit.jupiter.api.Test;
import woowacrew.user.domain.Degree;
import woowacrew.user.dto.DegreeResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

class DegreeConverterTest {
    @Test
    void DegreeToResponseDto() {
        Degree degree = new Degree();
        DegreeResponseDto degreeResponseDto = DegreeConverter.degreeToReponseDto(degree);

        assertThat(degreeResponseDto.getNumber()).isEqualTo(Degree.NONE_DEGREE);
    }
}
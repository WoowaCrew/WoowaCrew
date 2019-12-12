package woowacrew.degree.utils;

import org.modelmapper.ModelMapper;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.dto.DegreeResponseDto;

public class DegreeConverter {
    public static DegreeResponseDto degreeToReponseDto(Degree degree) {
        return new ModelMapper().map(degree, DegreeResponseDto.class);
    }
}

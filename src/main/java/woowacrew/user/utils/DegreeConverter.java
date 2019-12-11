package woowacrew.user.utils;

import org.modelmapper.ModelMapper;
import woowacrew.user.domain.Degree;
import woowacrew.user.dto.DegreeResponseDto;

public class DegreeConverter {
    public static DegreeResponseDto degreeToReponseDto(Degree degree) {
        return new ModelMapper().map(degree, DegreeResponseDto.class);
    }
}

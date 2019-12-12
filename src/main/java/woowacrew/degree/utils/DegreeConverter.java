package woowacrew.degree.utils;

import org.modelmapper.ModelMapper;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.dto.DegreeResponseDto;
import woowacrew.degree.dto.DegreeWithUserCountResponseDto;

public class DegreeConverter {
    public static DegreeWithUserCountResponseDto degreeToWithUserCountReponseDto(Degree degree, int userCount) {
        DegreeWithUserCountResponseDto responseDto = new ModelMapper().map(degree, DegreeWithUserCountResponseDto.class);
        responseDto.setUserCount(userCount);
        return responseDto;
    }

    public static DegreeResponseDto degreeToResponseDto(Degree degree) {
        return new ModelMapper().map(degree, DegreeResponseDto.class);
    }
}

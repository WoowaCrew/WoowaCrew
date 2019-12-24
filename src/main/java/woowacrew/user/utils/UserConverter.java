package woowacrew.user.utils;

import org.modelmapper.ModelMapper;
import woowacrew.degree.utils.DegreeConverter;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;
import woowacrew.user.dto.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    private UserConverter() {
    }

    public static UserContext userToUserContextDto(User user) {
        return new ModelMapper().map(user, UserContext.class);
    }

    public static UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto responseDto = new ModelMapper().map(user, UserResponseDto.class);
        responseDto.setDegreeResponseDto(DegreeConverter.degreeToResponseDto(user.getDegree()));
        return responseDto;
    }

    public static List<UserResponseDto> usersToUserResponseDtos(List<User> users) {
        return users.stream()
                .map(UserConverter::userToUserResponseDto)
                .collect(Collectors.toList());
    }
}

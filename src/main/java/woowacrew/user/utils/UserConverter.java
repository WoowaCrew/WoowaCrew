package woowacrew.user.utils;

import org.modelmapper.ModelMapper;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;
import woowacrew.user.dto.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public static UserContext userToUserContextDto(User user) {
        return new ModelMapper().map(user, UserContext.class);
    }

    public static UserResponseDto userToUserResponseDto(User user) {
        return new ModelMapper().map(user, UserResponseDto.class);
    }

    public static List<UserResponseDto> usersToUserResponseDtos(List<User> users) {
        return users.stream()
                .map(UserConverter::userToUserResponseDto)
                .collect(Collectors.toList());
    }
}

package woowacrew.user.domain;

import org.modelmapper.ModelMapper;

public class UserConverter {
    public static UserContext userToUserContextDto(User user) {
        return new ModelMapper().map(user, UserContext.class);
    }

    public static UserResponseDto userToUserResponseDto(User user) {
        return new ModelMapper().map(user, UserResponseDto.class);
    }
}

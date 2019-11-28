package woowacrew.user.domain;

import java.util.Objects;

public class UserConverter {
    public static UserDto userToUserDto(User user) {
        if (Objects.isNull(user.getUserId()) || Objects.isNull(user.getUrl())) {
            throw new IllegalArgumentException("아이디나 url은 null이 될 수 없습니다.");
        }
        return new UserDto(user.getUserId(), user.getUrl());
    }

    public static UserResponseDto userToUserResponseDto(User user) {
        return new UserResponseDto(user.getUserId(), user.getNickname(), user.getBirthday());
    }
}

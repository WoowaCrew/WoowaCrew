package woowacrew.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserConverter;
import woowacrew.user.domain.UserResponseDto;
import woowacrew.user.domain.UserUpdateDto;

import java.time.LocalDate;

@Service
@Transactional
public class UserService {

    private UserInternalService userInternalService;

    public UserService(UserInternalService userInternalService) {
        this.userInternalService = userInternalService;
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userInternalService.findById(id);
        return UserConverter.userToUserResponseDto(user);
    }

    public UserResponseDto update(Long id, UserUpdateDto userUpdateDto) {
        String nickname = userUpdateDto.getNickname();
        LocalDate birthday = BirthdayConverter.convert(userUpdateDto.getBirthday());

        User user = userInternalService.findById(id);
        user.updateUserInfo(nickname, birthday);

        return UserConverter.userToUserResponseDto(user);
    }
}

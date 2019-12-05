package woowacrew.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.user.domain.*;

import java.time.LocalDate;
import java.util.List;

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

    public List<UserResponseDto> findApprovedUser() {
        List<User> approvedUsers = userInternalService.findByRoleNotIn(UserRole.ROLE_PRECOURSE);

        return UserConverter.usersToUserResponseDtos(approvedUsers);
    }

    public List<UserResponseDto> findDisapprovedUser() {
        List<User> disapprovedUsers = userInternalService.findByRole(UserRole.ROLE_PRECOURSE);

        return UserConverter.usersToUserResponseDtos(disapprovedUsers);
    }
}

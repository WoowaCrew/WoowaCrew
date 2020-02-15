package woowacrew.user.service;

import org.springframework.stereotype.Service;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.*;
import woowacrew.user.utils.BirthdayConverter;
import woowacrew.user.utils.UserConverter;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class UserService {

    private UserInternalService userInternalService;

    public UserService(UserInternalService userInternalService) {
        this.userInternalService = userInternalService;
    }

    public UserResponseDto findById(Long userId) {
        User user = userInternalService.findById(userId);
        return UserConverter.toDto(user);
    }

    public List<UserResponseDto> findApprovedUser() {
        List<User> approvedUsers = userInternalService.findByRoleNotIn(UserRole.ROLE_PRECOURSE);

        return UserConverter.toDtos(approvedUsers);
    }

    public List<UserResponseDto> findDisapprovedUser() {
        List<User> disapprovedUsers = userInternalService.findByRole(UserRole.ROLE_PRECOURSE);

        return UserConverter.toDtos(disapprovedUsers);
    }

    public List<UpcomingBirthdayUserResponseDto> findUpcomingBirthday() {
        Month month = LocalDate.now().getMonth();
        List<User> users = userInternalService.findUpcomingBirthdayBy(month);
        return UserConverter.toBirthdayDtos(users);
    }

    public UserResponseDto update(Long userId, UserUpdateDto userUpdateDto) {
        String nickname = userUpdateDto.getNickname();
        LocalDate birthday = BirthdayConverter.toEntity(userUpdateDto.getBirthday());

        User updatedUser = userInternalService.update(userId, nickname, birthday);
        return UserConverter.toDto(updatedUser);
    }

    public void approveUserFor(Long userId, UserContext userContext, UserApproveDto userApproveDto) {
        userInternalService.approveUserFor(userId, userContext, userApproveDto);
    }
}

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
    public UserResponseDto findById(Long userId) {
        User user = userInternalService.findById(userId);
        return UserConverter.userToUserResponseDto(user);
    }

    public UserResponseDto update(Long userId, UserUpdateDto userUpdateDto) {
        String nickname = userUpdateDto.getNickname();
        LocalDate birthday = BirthdayConverter.convert(userUpdateDto.getBirthday());

        User user = userInternalService.findById(userId);
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

    public void approveUserFor(Long userId, UserContext userContext, UserApproveDto userApproveDto) {
        User user = userInternalService.findById(userId);
        User admin = userInternalService.findById(userContext.getId());
        Degree degree = userInternalService.findDegreeByNumber(userApproveDto.getDegreeNumber());

        user.updateByAdmin(admin, userApproveDto.getRole(), degree);
    }
}

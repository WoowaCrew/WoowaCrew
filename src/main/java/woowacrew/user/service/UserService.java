package woowacrew.user.service;

import org.springframework.stereotype.Service;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserConverter;
import woowacrew.user.domain.UserResponseDto;

@Service
public class UserService {

    private UserInternalService userInternalService;

    public UserService(UserInternalService userInternalService) {
        this.userInternalService = userInternalService;
    }

    public UserResponseDto findById(Long id) {
        User user = userInternalService.findById(id);
        return UserConverter.userToUserResponseDto(user);
    }
}

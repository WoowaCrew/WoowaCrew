package woowacrew.oauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import woowacrew.user.domain.*;

@Service
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    private UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto save(UserContext userContext) {
        User user = userRepository.findByUserId(userContext.getUserId())
                .orElseGet(() -> {
                    log.debug("{} : 신규 유저 생성", userContext.getUserId());
                    User newUser = new User(userContext.getUserId(), userContext.getUrl());
                    return userRepository.save(newUser);
                });

        return UserConverter.userToUserResponseDto(user);
    }
}

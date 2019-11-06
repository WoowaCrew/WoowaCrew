package woowacrew.oauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserDto;
import woowacrew.user.domain.UserRepository;

@Service
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    private UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto save(UserDto userDto) {
        if (!userRepository.existsByUserId(userDto.getUserId())) {
            log.debug("{} : 신규 유저 생성", userDto.getUserId());
            User user = new User(userDto.getUserId(), userDto.getUrl());
            userRepository.save(user);
        }
        return userDto;
    }
}

package woowacrew.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.service.exception.NotExistUserException;

@Service
@Transactional
public class UserInternalService {
    private UserRepository userRepository;

    public UserInternalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(NotExistUserException::new);
    }
}

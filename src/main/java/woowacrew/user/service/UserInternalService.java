package woowacrew.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.domain.UserRole;
import woowacrew.user.service.exception.NotExistUserException;

import java.util.List;

@Service
@Transactional
public class UserInternalService {
    private UserRepository userRepository;

    public UserInternalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findByOauthId(String oauthId) {
        return userRepository.findByOauthId(oauthId)
                .orElseThrow(NotExistUserException::new);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(NotExistUserException::new);
    }

    public List<User> findByRoleNotIn(UserRole role) {
        return userRepository.findByRoleNotIn(role);
    }

    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
}

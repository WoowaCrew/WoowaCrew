package woowacrew.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.user.domain.*;
import woowacrew.user.domain.exception.DegreeBoundException;
import woowacrew.user.service.exception.NotExistUserException;

import java.util.List;

@Service
@Transactional
public class UserInternalService {
    private DegreeRepository degreeRepository;
    private UserRepository userRepository;

    public UserInternalService(DegreeRepository degreeRepository, UserRepository userRepository) {
        this.degreeRepository = degreeRepository;
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

    public List<User> findByRoleAndNicknameNotNull(UserRole role) {
        return userRepository.findByRoleAndNicknameNotNull(role);
    }

    public Degree findDegreeByNumber(int numberOfDegree) {
        return degreeRepository.findByNumber(numberOfDegree)
                .orElseThrow(DegreeBoundException::new);
    }
}

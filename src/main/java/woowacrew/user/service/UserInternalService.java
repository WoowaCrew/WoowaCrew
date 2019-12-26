package woowacrew.user.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.service.DegreeInternalService;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserApproveDto;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.exception.NotExistUserException;

import java.time.LocalDate;
import java.util.List;

@Service
@EnableCaching
@Transactional(readOnly = true)
public class UserInternalService {
    private UserRepository userRepository;
    private DegreeInternalService degreeInternalService;

    public UserInternalService(UserRepository userRepository, DegreeInternalService degreeInternalService) {
        this.userRepository = userRepository;
        this.degreeInternalService = degreeInternalService;
    }

    public User findByOauthId(String oauthId) {
        return userRepository.findByOauthId(oauthId)
                .orElseThrow(NotExistUserException::new);
    }

    @Cacheable(value = "user", key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(NotExistUserException::new);
    }

    public List<User> findByRoleNotIn(UserRole role) {
        return userRepository.findByRoleNotInAndNicknameNotNull(role);
    }

    public List<User> findByRole(UserRole role) {
        return userRepository.findByRoleAndNicknameNotNull(role);
    }

    public List<User> findByDegreeId(Long degreeId) {
        return userRepository.findByDegreeIdAndNicknameNotNull(degreeId);
    }

    public int countByDegreeId(Long degreeId) {
        return userRepository.countByDegreeIdAndNicknameNotNull(degreeId);
    }

    @Transactional
    @CacheEvict(value = "user", key = "#userId")
    public void approveUserFor(Long userId, UserContext userContext, UserApproveDto userApproveDto) {
        User user = findById(userId);
        User admin = findById(userContext.getId());
        Degree degree = degreeInternalService.findDegreeByNumber(userApproveDto.getDegreeNumber());

        user.updateByAdmin(admin, userApproveDto.getRole(), degree);
    }

    @Transactional
    @CacheEvict(value = "user", key = "#userId")
    public User update(Long userId, String nickname, LocalDate birthday) {
        User user = findById(userId);
        user.updateUserInfo(nickname, birthday);

        return userRepository.save(user);
    }
}

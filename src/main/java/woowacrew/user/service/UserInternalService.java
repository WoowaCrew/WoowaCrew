package woowacrew.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.user.domain.*;
import woowacrew.user.dto.UserApproveDto;
import woowacrew.user.service.exception.NotExistUserException;

import java.time.LocalDate;
import java.util.List;

@Service
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

    @Transactional
    public void approveUserFor(Long userId, UserContext userContext, UserApproveDto userApproveDto) {
        User user = findById(userId);
        User admin = findById(userContext.getId());
        Degree degree = degreeInternalService.findDegreeByNumber(userApproveDto.getDegreeNumber());

        user.updateByAdmin(admin, userApproveDto.getRole(), degree);
    }

    @Transactional
    public User update(Long userId, String nickname, LocalDate birthday) {
        User user = findById(userId);
        user.updateUserInfo(nickname, birthday);

        return userRepository.save(user);
    }
}

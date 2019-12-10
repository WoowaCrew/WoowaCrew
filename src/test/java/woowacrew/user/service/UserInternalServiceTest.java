package woowacrew.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.user.domain.Degree;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInternalServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserInternalService userInternalService;

    @Test
    void findByOauthId() {
        String userId = "userId";
        User user = new User(userId, new Degree());
        when(userRepository.findByOauthId(userId)).thenReturn(Optional.ofNullable(user));

        User expectUser = userInternalService.findByOauthId(userId);

        assertThat(expectUser.getOauthId()).isEqualTo(userId);
    }
}
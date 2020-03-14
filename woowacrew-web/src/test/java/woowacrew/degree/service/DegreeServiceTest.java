package woowacrew.degree.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.dto.DegreeWithUserCountResponseDto;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserResponseDto;
import woowacrew.user.service.UserInternalService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DegreeServiceTest {
    @Mock
    private UserInternalService userInternalService;

    @Mock
    private DegreeInternalService degreeInternalService;

    @InjectMocks
    private DegreeService degreeService;

    @Test
    void findAllWithUserCount() {
        List<Degree> degrees = new ArrayList<>();
        degrees.add(new Degree());
        Degree degree = new Degree();
        degree.update(1);
        degrees.add(degree);

        when(degreeInternalService.findAll()).thenReturn(degrees);
        when(userInternalService.countByDegreeId(any())).thenReturn(1);

        List<DegreeWithUserCountResponseDto> degreeWithUserCountResponseDtos = degreeService.findAllWithUserCount();

        assertThat(degreeWithUserCountResponseDtos.size()).isEqualTo(2);
        assertThat(degreeWithUserCountResponseDtos.get(0).getUserCount()).isEqualTo(1);
    }

    @Test
    void findUserByDegreeId() {
        List<User> users = new ArrayList<>();
        users.add(new User("1", new Degree()));

        when(userInternalService.findByDegreeId(any())).thenReturn(users);

        List<UserResponseDto> actualUsers = degreeService.findUserByDegreeId(1L);

        assertThat(actualUsers.size()).isEqualTo(1);
    }
}
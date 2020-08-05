package woowacrew.github.domain;

import org.junit.jupiter.api.Test;
import woowacrew.user.domain.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GithubCommitTest {

    @Test
    void 정상적으로_GitubCommit을_생성한다() {
        User mockUser = mock(User.class);
        LocalDate date = LocalDate.of(2020, 6, 1);
        GithubCommit result = new GithubCommit(mockUser, date, 300);

        assertNotNull(result);
    }

    @Test
    void 날짜가_1일이_아니면_예외가_발생한다() {
        assertThrows(RuntimeException.class, () -> {
            User mockUser = mock(User.class);
            LocalDate date = LocalDate.of(2020, 6, 2);
            new GithubCommit(mockUser, date, 300);
        });
    }

    @Test
    void 같은_유저인지_확인한다() {
        User mockUser = mock(User.class);
        GithubCommit githubCommit = new GithubCommit(mockUser, LocalDate.of(2020, 6, 1), 300);

        when(mockUser.isSameUser(any(User.class))).thenReturn(true);

        assertTrue(githubCommit.isSameUser(mockUser));
    }
}
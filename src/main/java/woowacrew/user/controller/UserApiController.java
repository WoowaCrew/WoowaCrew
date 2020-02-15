package woowacrew.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.user.dto.UpcomingBirthdayUserResponseDto;
import woowacrew.user.dto.UserContext;
import woowacrew.user.dto.UserResponseDto;
import woowacrew.user.service.UserService;

import java.util.List;

@RestController
public class UserApiController {

    private UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/info")
    public ResponseEntity<UserResponseDto> show(UserContext userContext) {
        return ResponseEntity.ok(userService.findById(userContext.getId()));
    }

    @GetMapping("/api/user/birthday")
    public ResponseEntity<List<UpcomingBirthdayUserResponseDto>> upcomingBirthday() {
        return ResponseEntity.ok(userService.findUpcomingBirthday());
    }
}

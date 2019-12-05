package woowacrew.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.user.domain.UserResponseDto;
import woowacrew.user.service.UserService;

import java.util.List;

@RestController
public class UserApiController {

    private UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> approveUserList() {
        List<UserResponseDto> users = userService.findApprovedUser();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/api/users/disapprove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> disapproveUserList() {
        List<UserResponseDto> users = userService.findDisapprovedUser();

        return ResponseEntity.ok(users);
    }
}

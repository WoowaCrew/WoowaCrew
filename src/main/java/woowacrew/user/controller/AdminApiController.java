package woowacrew.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import woowacrew.user.domain.UserApproveDto;
import woowacrew.user.domain.UserContext;
import woowacrew.user.domain.UserResponseDto;
import woowacrew.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AdminApiController {

    private UserService userService;

    public AdminApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> approveUserList() {
        List<UserResponseDto> users = userService.findApprovedUser();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/disapprove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> disapproveUserList() {
        List<UserResponseDto> users = userService.findDisapprovedUser();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity approve(@PathVariable Long id, UserContext userContext, @RequestBody UserApproveDto userApproveDto) {
        userService.approveUserFor(id, userContext, userApproveDto);

        return ResponseEntity.ok().build();
    }
}

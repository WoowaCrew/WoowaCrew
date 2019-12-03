package woowacrew.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserContext;
import woowacrew.utils.annotation.AuthenticationUser;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/userPage")
    @PreAuthorize("hasRole('USER')")
    public String user(@AuthenticationUser User user) {
        return "userPage";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "admin";
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserContext> hello(@AuthenticationUser UserContext userContext) {
        return ResponseEntity.ok(userContext);
    }

    @GetMapping("/accessdeny")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String accessDeny(@AuthenticationUser UserContext userContext) {
        //Todo: User의 Role을 체크해서 그에 맞는 정보를 리턴해 준다.
        /*Todo:
           1. 승인 대기 상태
           2. 회원정보 미입력 상태
           3. 이 외의 요청은 모두 권한이 없어서 그럼
         */
        return "index";
    }
}

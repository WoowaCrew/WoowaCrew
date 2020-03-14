package woowacrew.oauth.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.domain.DegreeRepository;
import woowacrew.security.SecurityContextSupport;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

//임시 권한 변경을 위한 controller
@Controller
public class TempController {
    private final UserRepository userRepository;
    private final DegreeRepository degreeRepository;

    public TempController(UserRepository userRepository, DegreeRepository degreeRepository) {
        this.userRepository = userRepository;
        this.degreeRepository = degreeRepository;
    }

    @PostMapping("/login/temp")
    public RedirectView loginAdmin(String id) {
        if ("1234".equals(id)) {
            Degree degree = degreeRepository.findByDegreeNumber(0).get();
            User user = new User("1", UserRole.ROLE_ADMIN, degree);
            User savedUser = userRepository.findByOauthId(user.getOauthId())
                    .orElseGet(() -> userRepository.save(user));
            SecurityContextSupport.updateContext(new ModelMapper().map(savedUser, UserContext.class));
        }
        return new RedirectView("/");
    }

    @GetMapping("/login/form")
    public String loginForm() {
        return "tempLoginForm";
    }
}

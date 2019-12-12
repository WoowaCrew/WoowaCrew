package woowacrew.oauth.controller;

import woowacrew.degree.domain.Degree;
import woowacrew.degree.domain.DegreeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.security.SecurityContextSupport;
import woowacrew.user.domain.*;
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

    @GetMapping("/role/precourse")
    public RedirectView precourse(UserContext userContext) {
        userContext.setRole(UserRole.ROLE_PRECOURSE);
        SecurityContextSupport.updateContext(userContext);
        return new RedirectView("/");
    }

    @GetMapping("/role/crew")
    public RedirectView crew(UserContext userContext) {
        userContext.setRole(UserRole.ROLE_CREW);
        SecurityContextSupport.updateContext(userContext);
        return new RedirectView("/");
    }

    @GetMapping("/role/coach")
    public RedirectView coach(UserContext userContext) {
        userContext.setRole(UserRole.ROLE_COACH);
        SecurityContextSupport.updateContext(userContext);
        return new RedirectView("/");
    }

    @GetMapping("/role/admin")
    public RedirectView admin(UserContext userContext) {
        userContext.setRole(UserRole.ROLE_ADMIN);
        SecurityContextSupport.updateContext(userContext);
        return new RedirectView("/");
    }

    @PostMapping("/login/temp")
    public RedirectView loginAdmin(String id) {
        if ("1234".equals(id)) {
            Degree degree = degreeRepository.findByNumber(0).get();
            User user = new User("1", UserRole.ROLE_ADMIN, degree);
            userRepository.findByOauthId(user.getOauthId()).orElse(userRepository.save(user));
            SecurityContextSupport.updateContext(new ModelMapper().map(user, UserContext.class));
        }
        return new RedirectView("/");
    }

    @GetMapping("/login/form")
    public String loginForm() {
        return "tempLoginForm";
    }
}

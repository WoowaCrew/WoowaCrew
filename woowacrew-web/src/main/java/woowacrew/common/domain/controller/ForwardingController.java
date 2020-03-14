package woowacrew.common.domain.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ForwardingController implements ErrorController {

    @RequestMapping(value = "/error", method = {GET, POST})
    public String redirectRoot() {
        return "index";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

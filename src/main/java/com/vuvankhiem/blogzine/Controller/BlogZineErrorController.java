package com.vuvankhiem.blogzine.Controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BlogZineErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request,
                              Exception ex) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int err_code = Integer.parseInt(String.valueOf(status));

            if (err_code == HttpStatus.NOT_FOUND.value())
                return "us/err-page/404";

            if (err_code == HttpStatus.FORBIDDEN.value())
                return "us/err-page/403";

            if(err_code == HttpStatus.INTERNAL_SERVER_ERROR.value())
                return "us/err-page/500";
        }
        return "error";

    }

}

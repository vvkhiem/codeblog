package com.vuvankhiem.blogzine.Controller.user;

import com.vuvankhiem.blogzine.Common.Common;
import com.vuvankhiem.blogzine.DTO.PostDTO;
import com.vuvankhiem.blogzine.Entity.Post;
import com.vuvankhiem.blogzine.Entity.Subscriber;
import com.vuvankhiem.blogzine.Service.user.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController extends Common {

    static List<Post> st_postList;
    static String st_code;
    static Subscriber st_subscriber;
    static String tb = "";

    @Autowired
    @Qualifier("homeServiceImpl")
    HomeService homeService;

    @Autowired
    JavaMailSender javaMailSender;

    @GetMapping(value = {"/", "trang-chu"})
    public String homePage(Model model,
                           @ModelAttribute("subscriber") Subscriber subscriber,
                           HttpSession session) {

        String message = "";
        st_postList = homeService.getAllRandomPost();
        model.addAttribute("tb", tb);
        model.addAttribute("posts", homeService.getPostByPage(st_postList, 5, 5));
        model.addAttribute("postMostView", homeService.getTop15PostMostViewed());
        model.addAttribute("postSlides", homeService.getPostSildes());
        model.addAttribute("postLastest", homeService.getTop8PostLastest());
        session.setAttribute("categories", homeService.getAllCategories());
        session.setAttribute("tags", homeService.getAllTag());
        session.setAttribute("top4PostsLastest", homeService.getTop4PostLastest());
        tb = "";
        return "us/index";
    }

    @ResponseBody
    @GetMapping("/api/load-more/{index}")
    public List<PostDTO> loadMore(@PathVariable(value = "index") int index) {
        return homeService.getPostByPage(st_postList, index, 5);
    }

    @PostMapping("/subscriber")
    public String subscribe(@Valid @ModelAttribute("subscriber") Subscriber subscriber,
                            BindingResult bindingResult,
                            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors
                 ) {
                tb = error.getDefaultMessage();
            }
            return "redirect:/";
        }
        if (homeService.existsSubscriberBySubscriberEmail(subscriber.getSubscriberEmail())) {
            tb = "Tài khoản của bạn đã được đăng kí trước đó";
            return "redirect:/";
        }

        st_subscriber = subscriber;
        st_code = super.randomCode();

        String confirm_link = "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/subscriber/verify/" + st_code;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(subscriber.getSubscriberEmail());
        message.setSubject("Xác thực tài khoản đăng kí nhận tin từ BlogZine");
        message.setText("Click link : " + confirm_link + " để hoàn tất quá trình đăng kí nhận tin cho tài khoản Email của bạn.");
        javaMailSender.send(message);
        tb = "Để hoàn tất việc đăng kí. Bạn cần phải xác thực tài khoản của bạn trong Gmail.";
        return "redirect:/";

    }

    @GetMapping("/subscriber/verify/{code}")
    public String verifySubscriber(@PathVariable String code) {
        if (!st_code.equals(code)) {
            tb = "Xác thực tài khoản thất bại";
            return "redirect:/";
        }
        homeService.saveSubscriber(st_subscriber);
        return "redirect:/";
    }

}

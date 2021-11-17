package com.vuvankhiem.blogzine.Controller;

import com.restfb.types.User;
import com.vuvankhiem.blogzine.Common.Common;
import com.vuvankhiem.blogzine.Common.Outh2.Facebook.RestFB;
import com.vuvankhiem.blogzine.Common.Outh2.Google.GooglePojo;
import com.vuvankhiem.blogzine.Common.Outh2.Google.RestGG;
import com.vuvankhiem.blogzine.Common.SecurityUtil;
import com.vuvankhiem.blogzine.DTO.AccountDTO;
import com.vuvankhiem.blogzine.DTO.AccountDTO_;
import com.vuvankhiem.blogzine.Entity.Account;
import com.vuvankhiem.blogzine.Service.user.HandleAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Random;

@Controller
public class HandleAuthenticationController extends Common {

    static String st_code;
    static AccountDTO st_account;
    static boolean check = true;
    static AccountDTO_ st_account_;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HandleAuthenticationService handleAuthenticationService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    RestGG restGg;

    @Autowired
    RestFB restFb;

    /*----------------------------------------------------------REGISTER-------------------------------------------------------------------*/
    //Register page
    @GetMapping("/dang-ki")
    public String signUpPage(@ModelAttribute("account") AccountDTO account,
                             Model model) {
        model.addAttribute("isLogin", false);
        return "us/signIn_signUp_page";
    }

    //Register an account
    @PostMapping("/dang-ki")
    public String addUser(@Valid @ModelAttribute("account") AccountDTO account,
                          BindingResult bindingResult,
                          Model model,
                          HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isLogin", false);
            return "us/signIn_signUp_page";
        }
        String confirm_password = request.getParameter("confirm_password");
        boolean checkExistUsername = handleAuthenticationService.existsAccount(account.getUsername());
        boolean chechExistEmail = handleAuthenticationService.existsAccount(account.getEmail());
        if (chechExistEmail) {
            model.addAttribute("err", "Tài khoản của bạn đã được đăng kí trước đó");
            model.addAttribute("isLogin", false);
            return "us/signIn_signUp_page";
        }
        if (checkExistUsername) {
            model.addAttribute("err", "Username đã tồn tại");
            model.addAttribute("isLogin", false);
            return "us/signIn_signUp_page";
        }
        if (!confirm_password.equals(account.getPassword())) {
            model.addAttribute("err", "Mật khẩu không khớp");
            model.addAttribute("isLogin", false);
            return "us/signIn_signUp_page";
        }
        st_code = super.randomCode();
        if (check == false) {
            model.addAttribute("tb", "Vui lòng xác thực tài khoản của bạn trong Gmail.");
            model.addAttribute("isLogin", false);
            return "us/signIn_signUp_page";
        }
        st_account = account;
        check = false;
        String confirm_link = "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/dang-ki/xac-thuc/" + st_code;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(account.getEmail());
        message.setSubject("Xác minh tài khoản BlogZine");
        message.setText("Bạn đang thực hiện quá trình đăng kí tài khoản tại BlogZine. Click vào liên kết dưới đây để hoàn tất quá trình đăng kí." + "\nClick link : " + confirm_link);
        javaMailSender.send(message);
        model.addAttribute("tb", "Thông tin tài khoản của bạn đã được hệ thông lưu lại. Để hoàn tất quá trình đăng kí, bạn cần phải truy cập vào gmail để hoàn tất quá trình xác thực");
        model.addAttribute("isLogin", true);
        return "us/signIn_signUp_page";
    }

    //verify new account
    @GetMapping("/dang-ki/xac-thuc/{code}")
    public String verifyNewAccount(@PathVariable String code) {
        if (code.equals(st_code)) {
            Random random = new Random();
            String arr[] = {"/us/assets/images/avatar/actor.png", "/us/assets/images/avatar/actress.png"};
            Account account = new Account();
            account.setAvatar(arr[random.nextInt(2)]);
            account.setUsername(st_account.getUsername());
            account.setPassword(passwordEncoder.encode(st_account.getPassword()));
            account.setEmail(st_account.getEmail());
            account.setFullName(st_account.getFullName());
            handleAuthenticationService.saveAccount(account);
            st_code = null;
            st_account = null;
            check = true;
            return "redirect:/dang-nhap";
        }
        return "redirect:/";
    }

    /*----------------------------------------------------------LOGIN-------------------------------------------------------------------*/

    //Login page
    @GetMapping("/dang-nhap")
    public String loginPage(@ModelAttribute("account") AccountDTO account,
                            Model model,
                            @RequestParam(name = "err", required = false) String err) {
        if (err != null) {
            model.addAttribute("err", "Tên hoặc mật khẩu đăng nhập sai.");
        }
        model.addAttribute("isLogin", true);
        return "us/signIn_signUp_page";
    }

    // Login with Google account
    @GetMapping("/dang-nhap-google")
    public String loginWithGoogle( @ModelAttribute("account") AccountDTO acc,
                                   @RequestParam(name = "code", required = false) String code,
                                   Model model) throws IOException {
        String token = restGg.getToken(code);
        GooglePojo googlePojo = restGg.getUserInfo(token);
        String auth_provider = "GOOGLE";
        String email = googlePojo.getEmail();
        Account account = handleAuthenticationService.getAccountByUsernameOrEmail(email, auth_provider);
        if (account == null) {
            Account newAccount = new Account();
            newAccount.setEmail(email);
            newAccount.setAuth_provider(auth_provider);
            newAccount.setFullName(googlePojo.getName());
            newAccount.setAvatar(googlePojo.getPicture());
            newAccount.setUsername(googlePojo.getId());
            handleAuthenticationService.saveAccount(newAccount);
            account = handleAuthenticationService.getAccountByUsernameOrEmail(email, auth_provider);
        } else {
            if (!account.isActive()) {
                model.addAttribute("isLogin", true);
                model.addAttribute("err", "Tài khoản của bạn hiện thời đang bị khóa");
                return "us/signIn_signUp_page";
            }
        }
        UserDetails userDetails = restGg.userDetails(account);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return "redirect:/login-success";
    }

    //Login with Facebook account//
    @GetMapping("/dang-nhap-facebook")
    public String loginWithFacebook(
            @ModelAttribute("account") AccountDTO account,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(required = false) String error_code,
            @RequestParam(required = false) String error_message,
            Model model) throws IOException {

        if (error_code != null) {
            model.addAttribute("isLogin", true);
            model.addAttribute("err", error_message);
            return "us/signIn_signUp_page";
        }

        String token = restFb.getToken(code);
        User user = restFb.getUserInfo(token);
        String usernameOrEmail = user.getEmail() == null ? user.getId() : user.getEmail();
        String auth_provider = "FACEBOOK";
        String avatar = "https://graph.facebook.com/v3.0/" + user.getId() + "/picture?type=large";
        Account acc = handleAuthenticationService.getAccountByUsernameOrEmail(usernameOrEmail, auth_provider);
        if (acc == null) {
            Account newAccount = new Account();
            if (user.getEmail() != null) {
                newAccount.setEmail(user.getEmail());
            }
            newAccount.setAvatar(avatar);
            newAccount.setAuth_provider(auth_provider);
            newAccount.setFullName(user.getName());
            newAccount.setUsername(user.getId());
            handleAuthenticationService.saveAccount(newAccount);
            acc = handleAuthenticationService.getAccountByUsernameOrEmail(usernameOrEmail, auth_provider);
        } else {
            acc.setAvatar(avatar);
            if (!acc.isActive()) {
                model.addAttribute("isLogin", true);
                model.addAttribute("err", "Tài khoản của bạn hiện thời đang bị khóa");
                return "us/signIn_signUp_page";
            }
        }
        UserDetails userDetails = restFb.userDetails(acc);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return "redirect:login-success";
    }

    //Login success
    @GetMapping("/login-success")
    public String loginSuccess(HttpSession session, HttpServletRequest request) {
        session.setAttribute("avatarUser", SecurityUtil.getPrincipal().getAvatar());
        session.setAttribute("fullNameUser", SecurityUtil.getPrincipal().getFullName());
        session.setAttribute("accountID", SecurityUtil.getPrincipal().getAccountId());
        if (request.isUserInRole("ROLE_ADMIN"))
            return "redirect:/admin/";
        return "redirect:/";
    }

    /*----------------------------------------------------------FORGOT PASSWORD-------------------------------------------------------------------*/
    @GetMapping("/quen-mat-khau")
    public String forgotPasswordPage(@ModelAttribute("account") AccountDTO_ account, Model model) {
        return "us/forgot-password";
    }

    @PostMapping("/quen-mat-khau")
    public String changePassword(@Valid @ModelAttribute("account") AccountDTO_ account,
                                 BindingResult bindingResult,
                                 Model model,
                                 HttpServletRequest request) {

        String confirm_pass = request.getParameter("confirm_password");
        Account acc = handleAuthenticationService.getAccountByUsernameOrEmail(account.getEmail(), "WEB");
        if (acc == null) {
            model.addAttribute("err", "Tài khoản của bạn chưa được đăng kí trước đó");
            return "us/forgot-password";
        }
        if (bindingResult.hasErrors()) {
            return "us/forgot-password";
        }
        if (!account.getPassword().equals(confirm_pass)) {
            model.addAttribute("err", "Mật khẩu xác thực bạn nhập không khớp !");
            return "us/forgot-password";
        }
        if (check == false) {
            model.addAttribute("err", "Vui lòng xác thực tài khoản của bạn trong Gmail.");
            return "us/signIn_signUp_page";
        }
        st_account_ = account;
        st_code = super.randomCode();
        check = false;
        String confirm_link = "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/quen-mat-khau/xac-thuc/" + st_code;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(account.getEmail());
        message.setSubject("Xác minh tài khoản BlogZine");
        message.setText("Bạn đang thực hiện quá trình thay đổi mật khẩu cho tài khoản tại BlogZine. Click vào liên kết dưới đây để hoàn tất quá trình thay đổi mật khẩu." + "\nClick link : " + confirm_link);
        javaMailSender.send(message);
        return "us/forgot-password";
    }

    @GetMapping("/quen-mat-khau/xac-thuc/{code}")
    public String verifyAccount(@ModelAttribute("account") AccountDTO_ account,
                                @PathVariable String code,
                                Model model) {
        if (!code.equals(st_code)) {
            model.addAttribute("err", "Xác thực Email thất bại !");
            return "us/forgot-password";
        }
        Account acc = handleAuthenticationService.getAccountByUsernameOrEmail(st_account_.getEmail(), "WEB");
        acc.setPassword(passwordEncoder.encode(st_account_.getPassword()));
        handleAuthenticationService.saveAccount(acc);
        return "redirect:/dang-nhap";
    }

}

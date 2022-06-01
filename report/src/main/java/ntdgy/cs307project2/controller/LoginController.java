package ntdgy.cs307project2.controller;

import ntdgy.cs307project2.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    final DatabaseController databaseController;

    public LoginController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @PostMapping("api/user/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model) throws IOException {
        if (databaseController.login(username, password)) {
            session.setAttribute("loginUser", username);
            var uuid = new Cookie("uuid", LoginService.getCookie(username));
            var name = new Cookie("username", username);
            uuid.setMaxAge(24 * 60 * 60);
            name.setMaxAge(24 * 60 * 60);
            uuid.setPath("/");
            name.setPath("/");
            response.addCookie(uuid);
            response.addCookie(name);
            response.sendRedirect(request.getContextPath() + "/main.html");
            return "index";
        } else {
            model.addAttribute("msg", "用户名错误");
            return "index";
        }
    }

    @PostMapping("api/user/signup")
    public String signup(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("invitecode") String inviteCode,
            HttpSession session,
            Model model) {
        if (databaseController.signup(username, password,inviteCode)) {
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        } else {
            model.addAttribute("msg", "用户名错误");
            return "index";
        }
    }


}

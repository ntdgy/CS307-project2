package ntdgy.cs307project2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;

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
            Model model) {
        if (databaseController.login(username, password)) {
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
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

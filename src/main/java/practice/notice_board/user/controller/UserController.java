package practice.notice_board.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.notice_board.user.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // register.html 렌더링
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(
        @RequestParam(name = "username") String username,
        @RequestParam(name = "password") String password,
        @RequestParam(name = "email") String email
    ) {
        userService.registerUser(username, password, email);
        return "redirect:/users/login"; // 회원가입 후 로그인 페이지로 이동
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.html 렌더링
    }

    // 로그인 처리 - spring security가 알아서 처리
    /*
    @PostMapping("/login")
    public String login(
        @RequestParam(name = "username") String username,
        @RequestParam(name = "password") String password,
        HttpSession session,
        Model model
    ) {
        Optional<User> user = userService.login(username, password);
        if (user.isPresent()) {
            session.setAttribute("loggedInUser", user.get()); // 세션에 저장
            return "redirect:/posts"; // 로그인 성공 시 게시글 목록으로 이동
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }
    */

    // 로그아웃 처리 - spring security가 알아서 처리
    /*
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 삭제
        return "redirect:/posts"; // 로그아웃 후 게시글 목록으로 이동
    }
    */
}
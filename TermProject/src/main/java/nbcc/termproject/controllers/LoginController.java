package nbcc.termproject.controllers;

import jakarta.servlet.http.HttpSession;
import nbcc.termproject.entities.UserLogin;
import nbcc.termproject.repositories.UserLoginRepository;
import nbcc.termproject.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static nbcc.termproject.constants.AttributeConstants.LOGIN_TOKEN_KEY;
import static nbcc.termproject.constants.AttributeConstants.MESSAGE_KEY;


@Controller
public class LoginController {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;

    public LoginController(UserRepository userRepository, UserLoginRepository userLoginRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,  Model model, HttpSession session){

        try{

            var user = userRepository.getUsersByUserNameAndPassword(username,password);

            if(user != null){

                var token = UUID.randomUUID().toString();
                var login = new UserLogin(user.getId(), token);
                userLoginRepository.save(login);
                session.setAttribute(LOGIN_TOKEN_KEY, token);

                return "redirect:/home";

            }else{
                model.addAttribute(MESSAGE_KEY, "Invalid user name or password");
                return "login/login";
            }

        }catch (Exception ex){
            model.addAttribute(MESSAGE_KEY,"Something went wrong");
            System.out.println(ex.getMessage());
            return "login/login";
        }

    }

    @PostMapping("/logout")
    public String logout(Model model, HttpSession session){

        var token = String.valueOf(session.getAttribute(LOGIN_TOKEN_KEY));

        session.removeAttribute(LOGIN_TOKEN_KEY);
        userLoginRepository.deleteById(token);

        return "redirect:/home";
    }
}

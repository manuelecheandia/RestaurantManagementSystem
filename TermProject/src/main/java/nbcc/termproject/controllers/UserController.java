package nbcc.termproject.controllers;

import jakarta.validation.Valid;
import nbcc.termproject.entities.User;
import nbcc.termproject.login.LoginInfo;
import nbcc.termproject.repositories.UserLoginRepository;
import nbcc.termproject.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static nbcc.termproject.constants.AttributeConstants.MESSAGE_KEY;
import static nbcc.termproject.constants.AttributeConstants.USER_KEY;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;

    private final LoginInfo loginInfo;

    public UserController(UserRepository userRepository, UserLoginRepository userLoginRepository, LoginInfo loginInfo) {
        this.userRepository = userRepository;

        this.userLoginRepository = userLoginRepository;
        this.loginInfo = loginInfo;
    }
    @GetMapping("/register")
    public String register(Model model){
        try{
            var user = loginInfo.getLoggedInUser();
            if(user == null){
                model.addAttribute("user", new User());
                return "user/registration";
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            model.addAttribute(USER_KEY, "Something went wrong");
            return "index";
        }
        return "index";
    }


    @PostMapping("/register")
    public String register(@Valid User user, BindingResult br, Model model){

        if(!br.hasErrors()){

            userRepository.save(user);

            return "redirect:/home";

        } else {
            model.addAttribute(MESSAGE_KEY, "missing required fields");
            return "user/registration";
        }
    }
}

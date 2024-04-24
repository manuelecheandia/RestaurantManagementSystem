package nbcc.termproject.controllers;

import jakarta.servlet.http.HttpSession;
import nbcc.termproject.login.LoginInfo;
import nbcc.termproject.repositories.UserLoginRepository;
import nbcc.termproject.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static nbcc.termproject.constants.AttributeConstants.USER_KEY;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    private final UserLoginRepository userLoginRepository;

    private final LoginInfo loginInfo;

    public HomeController(UserRepository userRepository, UserLoginRepository userLoginRepository, LoginInfo loginInfo) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.loginInfo = loginInfo;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model, HttpSession session){

        try{

            var user = loginInfo.getLoggedInUser();

            if(user != null){
                model.addAttribute(USER_KEY, user);
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            model.addAttribute(USER_KEY, "Something went wrong");
            return "index";
        }
        return "index";


    }
}

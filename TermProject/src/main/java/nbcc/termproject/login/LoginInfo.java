package nbcc.termproject.login;


import jakarta.servlet.http.HttpSession;
import nbcc.termproject.entities.User;
import nbcc.termproject.repositories.UserLoginRepository;
import nbcc.termproject.repositories.UserRepository;
import org.springframework.stereotype.Component;

import static nbcc.termproject.constants.AttributeConstants.LOGIN_TOKEN_KEY;

@Component
public class LoginInfo {

    private final HttpSession session;

    private final UserRepository userRepository;

    private final UserLoginRepository userLoginRepository;


    public LoginInfo(HttpSession session, UserRepository userRepository, UserLoginRepository userLoginRepository) {
        this.session = session;
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
    }

    public User getLoggedInUser(){
        var tokenAttribute = session.getAttribute(LOGIN_TOKEN_KEY);
        var token = String.valueOf(tokenAttribute);

        var login = userLoginRepository.getUserLoginByToken(token);

        if(login != null){
            var user = userRepository.findById(login.getUserId());

            if(user.isPresent()){
                return user.get();
            }
        }
        return null;
    }
}
package nbcc.termproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class UserLogin {

    @Id
    private String token;

    private int userId;

    @CreationTimestamp
    private LocalDateTime loginTime;

    public UserLogin() {
    }

    public UserLogin(int userId, String token) {
        this();
        this.userId = userId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String loginToken) {
        this.token = loginToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
}

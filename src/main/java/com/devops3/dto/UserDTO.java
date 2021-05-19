package com.devops3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserDTO {

    long id;
    private String username;
    private boolean loggedIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime lastLogin;

    public UserDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }

    public static final class UserDTOBuilder {
        long id;
        private String username;
        private boolean loggedIn;
        private LocalDateTime lastLogin;

        private UserDTOBuilder() {
        }

        public static UserDTOBuilder anUserDTO() {
            return new UserDTOBuilder();
        }

        public UserDTOBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder withLoggedIn(boolean loggedIn) {
            this.loggedIn = loggedIn;
            return this;
        }

        public UserDTOBuilder withLastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public UserDTO build() {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userDTO.setUsername(username);
            userDTO.setLoggedIn(loggedIn);
            userDTO.setLastLogin(lastLogin);
            return userDTO;
        }
    }
}

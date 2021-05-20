package com.devops3.controller;

import com.devops3.dto.Data;
import com.devops3.dto.EntityDTO;
import com.devops3.dto.UserDTO;
import com.devops3.exception.ExistingUserException;
import com.devops3.exception.AccessException;
import com.devops3.exception.UserNotFoundException;
import com.devops3.model.Status;
import com.devops3.model.User;
import com.devops3.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users/register")
    public ResponseEntity<EntityDTO> registerUser(@RequestBody User newUser) {

        if (newUser.getUsername().length() > 0 && newUser.getPassword().length() > 0) {
            List<User> foundUsers = userRepository.findByUsername(newUser.getUsername());

            if (foundUsers.isEmpty()) {
                newUser = userRepository.save(newUser);

                UserDTO userDTO = UserDTO.UserDTOBuilder.anUserDTO().withId(newUser.getId()).withLoggedIn(false).withUsername(newUser.getUsername()).build();
                Data d = Data.DataBuilder.aData().build();
                d.addUser(userDTO);

                EntityDTO<UserDTO> dto = new EntityDTO<>();
                dto.setStatus(Status.SUCCESS);
                dto.addData(d);
                dto.setResponseCode(HttpStatus.CREATED.value());

                logger.info(userDTO.toString());

                return new ResponseEntity<>(dto, HttpStatus.CREATED);

            } else throw new ExistingUserException("User is already registered.");
        } else throw new RuntimeException("Username & password should not be empty");


    }

    @PostMapping("/users/login")
    public ResponseEntity<EntityDTO> loginUser(@RequestBody User user) {

        List<User> foundUsers = userRepository.findByUsername(user.getUsername());

        if (!foundUsers.isEmpty()) {
            User loggedInUser = foundUsers.get(0);

            if (loggedInUser.isLoggedIn())
                throw new AccessException("The user is already logged in.");

            loggedInUser.setLoggedIn(true);
            loggedInUser.setLastLogin(LocalDateTime.now());
            userRepository.save(loggedInUser);

            UserDTO userDTO = UserDTO.UserDTOBuilder.anUserDTO()
                    .withUsername(loggedInUser.getUsername())
                    .withId(loggedInUser.getId())
                    .withLoggedIn(loggedInUser.isLoggedIn())
                    .withLastLogin(loggedInUser.getLastLogin()).build();

            Data d = Data.DataBuilder.aData().build();
            d.addUser(userDTO);
            //d.addUser(UserDTO.UserDTOBuilder.anUserDTO().withUsername("admin").withId(9999).withLoggedIn(false).build());

            EntityDTO<UserDTO> dto = new EntityDTO<>();
            dto.setStatus(Status.SUCCESS);
            dto.addData(d);
            dto.setResponseCode(HttpStatus.OK.value());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

        throw new UserNotFoundException("User not found!");
    }

    @PostMapping("/users/logout")
    public ResponseEntity<EntityDTO> logUserOut(@RequestBody User user) {

        List<User> foundUsers = userRepository.findByUsername(user.getUsername());

        if (!foundUsers.isEmpty()) {
            User loggedInUser = foundUsers.get(0);
            if (loggedInUser.isLoggedIn()) {
                loggedInUser.setLoggedIn(false);
                userRepository.save(loggedInUser);

                EntityDTO dto = new EntityDTO();
                dto.setStatus(Status.SUCCESS);
                dto.setResponseCode(HttpStatus.OK.value());

                return new ResponseEntity<>(dto, HttpStatus.OK);

            } else throw new AccessException("User is not logged in. Cannot log him/her out");
        }

        throw new UserNotFoundException("User not found exception");
    }

    @DeleteMapping("/users/all")
    public Status deleteUsers() {
        userRepository.deleteAll();
        return Status.SUCCESS;
    }
}

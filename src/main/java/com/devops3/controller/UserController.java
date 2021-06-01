package com.devops3.controller;

import com.devops3.dto.Data;
import com.devops3.dto.EntityDTO;
import com.devops3.dto.ErrorDTO;
import com.devops3.dto.UserDTO;
import com.devops3.exception.EmptyFieldException;
import com.devops3.exception.ExistingUserException;
import com.devops3.exception.AccessException;
import com.devops3.exception.UserNotFoundException;
import com.devops3.model.Status;
import com.devops3.model.User;
import com.devops3.service.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New user registered!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EntityDTO.class))}),
            @ApiResponse(responseCode = "409", description = "Account already exists",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "204", description = "Passed an empty response body",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping("register")
    public ResponseEntity<EntityDTO> registerUser(@RequestBody(required = true) User newUser) {

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
        } else throw new EmptyFieldException("Username & password should not be empty");


    }

    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EntityDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "406", description = "User already logged in",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping("login")
    public ResponseEntity<EntityDTO> loginUser(@RequestBody(required = true) User user) {

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

    @Operation(summary = "Logout user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged user out succesfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EntityDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "406", description = "Cannot logout user, because he/she is not logged in",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping("logout")
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

}

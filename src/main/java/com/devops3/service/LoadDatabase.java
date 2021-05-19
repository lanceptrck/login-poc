package com.devops3.service;


import com.devops3.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private List<User> initialUsers = new ArrayList<>();

    public void initializeUsers() {
        initialUsers.add(new User("patrick", "1234"));
        initialUsers.add(new User("yong", "1234"));
        initialUsers.add(new User("mav", "1234"));
    }

    @Bean
    CommandLineRunner initUserDatabase(UserRepository repository) {
        initializeUsers();
        return args -> {
            for (User u : initialUsers) {
                log.info("Preloading " + repository.save(u));
            }
        };
    }


}
